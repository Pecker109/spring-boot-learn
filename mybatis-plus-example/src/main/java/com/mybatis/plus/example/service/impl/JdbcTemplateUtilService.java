package com.mybatis.plus.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Pecker
 * @Description
 * @since 2021-12-17
 */
@Slf4j
@Validated
@Component
public class JdbcTemplateUtilService {

    @Autowired
    JdbcTemplate jdbcTemplate;


    // 字段映射缓存：类名 -> (列名小写 -> 字段)
    private static final Map<String, Map<String, Field>> fieldMappingCache = new HashMap<>();

    /**
     * 流式处理 + 反射映射
     *
     * @param sql           SQL查询语句
     * @param clazz         目标对象类型
     * @param batchSize     批次大小
     * @param batchConsumer 批处理消费者
     * @param <T>           目标对象类型
     */
    public <T> void streamAndProcess(String sql, Class<T> clazz, int batchSize, Consumer<List<T>> batchConsumer) {

        // 创建流式PreparedStatement
        PreparedStatementCreator psc = createStreamingStatementCreator(sql);

        // 处理结果集
        jdbcTemplate.query(psc, new ResultSetExtractor<Void>() {
            private final List<T> currentBatch = new ArrayList<>(batchSize);
            private long startTime = System.currentTimeMillis();

            @Override
            public Void extractData(ResultSet rs) throws SQLException {
                try {
                    // 初始化元数据和字段映射
                    ResultSetMetaData metaData = rs.getMetaData();
                    Map<String, Field> fieldMap = getFieldMapping(clazz, metaData);

                    // 处理每一行
                    while (rs.next()) {
                        // 映射为对象并添加到批次
                        T obj = mapRowToObject(rs, clazz, metaData, fieldMap);

                        // 达到批次大小时处理批次
                        currentBatch.add(obj);
                        if (currentBatch.size() >= batchSize) {
                            processBatch(currentBatch, batchConsumer);
                            currentBatch.clear();
                        }
                    }

                    // 处理最后一批
                    if (!currentBatch.isEmpty()) {
                        processBatch(currentBatch, batchConsumer);
                    }
                } finally {
                    // 需要手动关闭,防止异常情况 ResultSet 没被关闭导致服务端连接泄露
                    JdbcUtils.closeResultSet(rs);
                }
                return null;
            }
        });
    }

    /**
     * 创建流式PreparedStatementCreator
     */
    private PreparedStatementCreator createStreamingStatementCreator(String sql) {
        return connection -> {
            // 创建支持流式的PreparedStatement
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    // 游标只能往下移动,只能使用 rs.next()
                    ResultSet.TYPE_FORWARD_ONLY,
                    // 只读模式,不能通过结果集更新数据
                    ResultSet.CONCUR_READ_ONLY
            );
            // 配置流式参数
            configureStreamingParameters(connection, ps);
            return ps;
        };
    }

    /**
     * 配置流式查询参数
     */
    private void configureStreamingParameters(Connection con, PreparedStatement ps)
            throws SQLException {

        String dbType = getDatabaseType(con);

        int defaultBatchSize = 2000;
        switch (dbType.toLowerCase()) {
            case "mysql":
                // MySQL 通过设置`PreparedStatement`的fetchSize为`Integer.MIN_VALUE`，这样就会以流式的方式获取结果。MySQL的JDBC驱动会一行一行地读取数据
                ps.setFetchSize(Integer.MIN_VALUE);
                break;
            case "postgresql":
            case "oracle":
            case "sqlserver":
                ps.setFetchSize(defaultBatchSize);
                break;
            default:
                ps.setFetchSize(defaultBatchSize);
        }
    }

    /**
     * 获取数据库类型
     */
    private String getDatabaseType(Connection con) throws SQLException {
        String url = con.getMetaData().getURL().toLowerCase();

        if (url.contains(":mysql:")) return "mysql";
        if (url.contains(":postgresql:")) return "postgresql";
        if (url.contains(":oracle:")) return "oracle";
        if (url.contains(":sqlserver:") || url.contains(":microsoft:")) return "sqlserver";

        return "unknown";
    }

    /**
     * 获取字段映射
     */
    private <T> Map<String, Field> getFieldMapping(Class<T> clazz, ResultSetMetaData metaData)
            throws SQLException {

        String className = clazz.getName();
        Map<String, Field> fieldMap = fieldMappingCache.get(className);

        if (fieldMap == null) {
            synchronized (fieldMappingCache) {
                fieldMap = fieldMappingCache.get(className);
                if (fieldMap == null) {
                    fieldMap = createFieldMapping(clazz, metaData);
                    fieldMappingCache.put(className, fieldMap);
                }
            }
        }
        return fieldMap;
    }

    /**
     * 创建字段映射
     */
    private <T> Map<String, Field> createFieldMapping(Class<T> clazz, ResultSetMetaData metaData)
            throws SQLException {

        Map<String, Field> fieldMap = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            ReflectionUtils.makeAccessible(field);
            fieldMap.put(field.getName(), field);
        }

        return fieldMap;
    }

    /**
     * 将数据库列名转换为驼峰命名
     */
    private String toCamelCase(String columnName) {
        StringBuilder sb = new StringBuilder();
        boolean nextUpperCase = false;

        for (int i = 0; i < columnName.length(); i++) {
            char c = columnName.charAt(i);

            if (c == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    sb.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
        }

        return sb.toString();
    }

    /**
     * 将ResultSet行映射为对象
     */
    private <T> T mapRowToObject(ResultSet rs, Class<T> clazz,
                                 ResultSetMetaData metaData, Map<String, Field> fieldMap)
            throws SQLException {

        try {
            T obj = clazz.getDeclaredConstructor().newInstance();

            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnLabel(i).toLowerCase();
                String camelCase = toCamelCase(columnName);
                // 要保证 fieldMap 中字段与 sql 字段转换方式一致
                Field field = fieldMap.get(camelCase);

                if (field != null) {
                    FieldValueSetter.setFieldValue(obj, field, rs.getObject(i));
                }
            }

            return obj;
        } catch (Exception e) {
            throw new SQLException("Error mapping row to object: " + e.getMessage(), e);
        }
    }

    /**
     * 处理批次
     */
    private <T> void processBatch(List<T> batch, Consumer<List<T>> batchConsumer) {
        long startTime = System.currentTimeMillis();
        try {
            batchConsumer.accept(batch);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            System.out.printf("Processed batch of %,d items in %d ms%n",
                    batch.size(), duration);
        }
    }

}
