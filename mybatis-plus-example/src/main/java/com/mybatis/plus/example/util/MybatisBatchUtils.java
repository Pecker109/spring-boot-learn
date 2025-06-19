package com.mybatis.plus.example.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.BiFunction;

/**
 * 该类用于提供批量处理 MyBatis 操作的工具方法
 */
@Slf4j
@Component
public class MybatisBatchUtils {

    // 每次处理的最大条目数
    private static final int BATCH_SIZE = 1000;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 对数据列表进行批量更新或插入操作。
     * <p>
     * 如果当前处于 Spring 管理的事务中，则该操作会在该事务中执行。
     * 如果当前没有处于 Spring 管理的事务中，则该操作会在新的事务中执行。
     * <p>
     * 工具类上如果没有标记事务注解，在 flushStatements 的时候会很慢，这是因为在Spring中，如果没有开启事务，每个数据库操作都将作为单独的事务来处理，这就意味着每个操作结束后都要进行提交。这导致了大量的数据库I/O操作，对每个操作都需要网络延迟和数据库的提交时间，这会影响性能。
     * <p>
     * 另外如果MySQL的JDBC驱动参数增加rewriteBatchedStatements=true可以显著提高批处理操作的性能，结合事务会更快。rewriteBatchedStatements 参数的作用是将一批插入拼接成 insert into xxx values (a),(b),(c)... 这样的一条语句形式，提高了性能
     *
     * @param data        需要被处理的数据列表
     * @param mapperClass MyBatis 的 Mapper 类
     * @param function    用于定义如何处理每一个数据项的函数
     * @return 影响的总行数
     */
    @Transactional(rollbackFor = Exception.class)
    public <T, U, R> int batchUpdateOrInsert(List<T> data, Class<U> mapperClass, BiFunction<T, U, R> function) {
        if (data == null || data.isEmpty() || mapperClass == null) {
            throw new IllegalArgumentException("Invalid input parameters. Cannot be null or empty");
        }

        SqlSessionHolder sqlSessionHolder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sqlSessionFactory);
        boolean transaction = TransactionSynchronizationManager.isSynchronizationActive();
        if (sqlSessionHolder != null) {
            SqlSession sqlSession = sqlSessionHolder.getSqlSession();
            //原生无法支持执行器切换，当存在批量操作时，会嵌套两个session的，优先commit上一个session
            //按道理来说，这里的值应该一直为false。
            sqlSession.commit(!transaction);
        }

        int totalAffectedRows = 0;

        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        if (!transaction) {
            log.warn("SqlSession [" + batchSqlSession + "] Transaction not enabled");
        }

        try {
            // 获得一个新的Mapper对象才能生效
            U mapper = batchSqlSession.getMapper(mapperClass);

            for (int i = 0; i < data.size(); i++) {
                function.apply(data.get(i), mapper);

                // 每处理 BATCH_SIZE 条数据，或者已经处理到数据列表的最后一项，就执行一次刷新
                if (i != 0 && (i % BATCH_SIZE == 0 || i == data.size() - 1)) {
                    batchSqlSession.flushStatements();
                }

                totalAffectedRows++;
            }
            batchSqlSession.flushStatements();
            // 如果当前没有处于 Spring 管理的事务中，那么这个 commit 就会执行。否则，这个 commit 是无效的，将由 Spring 来执行实际的 commit。
            batchSqlSession.commit(!transaction);
        } catch (Exception e) {
            batchSqlSession.rollback();
            throw e;
        } finally {
            batchSqlSession.close();
        }

        return totalAffectedRows;
    }
}