package com.mybatis.plus.example.plugins;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Pecker
 * @Description 自定义 mybatis 插件,实现数据权限过滤
 * @since 2021-11-24
 */
@Slf4j
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
@Component
public class DataPermissionPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        System.out.println("插件拓展点-Executor代理 1....");
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];
        Executor executor = (Executor) invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;
        //由于逻辑关系，只会进入一次
        if (args.length == 4) {
            //4 个参数时
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            //6 个参数时
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }

        Configuration configuration = ms.getConfiguration();
        //BoundSql permissionBoundSql = new BoundSql(configuration, permissionSql, boundSql.getParameterMappings(), parameter);
        // 第一次 SELECT count(0) FROM user (where ...) -->
        // 第二次 SELECT id,name,age,email FROM user limit ?,?  -->

        String orgSql = boundSql.getSql();
        log.info("原始 sql: {}", orgSql);
        // sql 是否查 count,也可以定义注解指定查询类型是分页还是count
        boolean isCount = orgSql.toLowerCase().contains("count");

        String permissionSql = StringUtils.EMPTY;
        if (isCount) {
            permissionSql = countSqlRemould(orgSql);
        } else {
            permissionSql = pageSqlRemould(orgSql);
        }
        BoundSql permissionBoundSql = new BoundSql(configuration, permissionSql, boundSql.getParameterMappings(), parameter);

        log.info("权限 sql: {}", permissionSql);
        return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, permissionBoundSql);
    }

    public static List<Integer> ids = CollectionUtil.newArrayList(1, 2, 3, 4);


    /**
     * 改造 count 查询
     *
     * @param sql 原始 sql
     * @return 改造后 SQL
     */
    private String countSqlRemould(String sql) {
        if (CollectionUtil.isNotEmpty(ids)) {
            if (StrUtil.containsIgnoreCase(sql, "where")) {
                sql = sql.concat(" and ").concat("id in").concat(joinId(ids));
            } else {
                sql = sql.concat(" where ").concat("id in").concat(joinId(ids));
            }
        }
        return sql;
    }

    /**
     * 改造分页sql，添加权限
     *
     * @param sql 原始 sql
     * @return 改造 sql
     */
    private String pageSqlRemould(String sql) {

        if (CollectionUtil.isNotEmpty(ids)) {
            String fromPre = StrUtil.subPre(sql, StrUtil.indexOfIgnoreCase(sql, "from") + 4).concat("(");
            String limitPre = StrUtil.subPre(sql, StrUtil.indexOfIgnoreCase(sql, "limit"));
            sql = fromPre.concat(limitPre).concat(")as temp where id in").concat(joinId(ids)).concat("limit ?,?");
        }
        return sql;
    }

    /**
     * 拼接sql函数in的参数
     *
     * @param ids 权限数组
     * @return sql
     */
    private String joinId(List<Integer> ids) {
        StringBuilder result = new StringBuilder();
        if (ids == null || ids.size() == 0) {
            return StrUtil.EMPTY;
        }
        result.append("(");
        for (Integer id : ids) {
            result.append(id).append(",");
        }
        result.replace(result.lastIndexOf(","), result.lastIndexOf(",") + 1, ")");
        return result.toString();
    }


    public static void main(String[] args) {
        String sql = "SELECT id,name,age,email FROM user limit ?,?";
        String limitPre = StrUtil.subPre(sql, StrUtil.indexOfIgnoreCase(sql, "limit"));
        System.out.println(limitPre);
    }

}