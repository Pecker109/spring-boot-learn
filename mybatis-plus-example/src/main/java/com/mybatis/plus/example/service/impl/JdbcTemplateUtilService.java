package com.mybatis.plus.example.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.sql.ResultSetMetaData;
import java.util.*;

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

    /**
     * 只查询一列数据类型对象。用于只有一行查询结果的数据
     *
     * @param sql    sql
     * @param params 替换掉 ?
     * @param clz    Integer.class,Float.class,Double.Class,Long.class,Boolean.class,Char.class,Byte.class,Short.class
     * @return Object
     */
    public Object queryOneColumnForSingletonRow(@NotNull String sql, @Nullable Object[] params, Class clz) {
        Object result;
        if (params != null && params.length > 0) {
            result = jdbcTemplate.queryForObject(sql, params, clz);
        } else {
            result = jdbcTemplate.queryForObject(sql, clz);
        }
        return result;
    }

    /**
     * 查询返回实体对象集合
     *
     * @param sql    sql语句
     * @param params 填充sql问号占位符数
     * @param clz    实体对象类型
     * @return
     */
    public List queryForObjectList(@NotNull String sql, Object[] params, final Class clz) {
        final List list = new ArrayList();
        jdbcTemplate.query(sql, params, rs -> {
            try {
                List<String> columnNames = new ArrayList<>();
                ResultSetMetaData meta = rs.getMetaData();
                int num = meta.getColumnCount();
                for (int i = 0; i < num; i++) {
                    columnNames.add(meta.getColumnLabel(i + 1).toLowerCase().trim());
                }
                Method[] methods = clz.getMethods();
                Set<String> fieldSet = new HashSet<>();
                for (Method method : methods) {
                    if (method.getName().trim().startsWith("set")) {
                        String f = method.getName().trim().substring(3);
                        f = (f.charAt(0) + "").toLowerCase().trim() + f.substring(1);
                        fieldSet.add(f);
                    }
                }
                do {
                    Object obj;
                    obj = clz.getConstructor().newInstance();
                    for (int i = 0; i < num; i++) {
                        Object objVal = rs.getObject(i + 1);
                        if (fieldSet.contains(columnNames.get(i))) {
                            BeanUtils.copyProperty(obj, columnNames.get(i), objVal);
                        }
                    }
                    list.add(obj);
                } while (rs.next());
            } catch (Exception e) {
                log.error("JdbcTemplateUtilService 处理结果集异常", e);
                throw new RuntimeException("JdbcTemplateUtilService 处理结果集异常");
            }
        });

        return list;
    }

    /**
     * 查询返回List<Map<String,Object>>格式数据,每一个Map代表一行数据，列名为key
     *
     * @param sql    sql语句
     * @param params 填充问号占位符数
     * @return
     */
    public List<Map<String, Object>> queryForMaps(@NotNull String sql, @Nullable Object[] params) {

        if (params != null && params.length > 0) {
            return jdbcTemplate.queryForList(sql, params);
        }
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 查询分页（MySQL数据库）
     *
     * @param sql     终执行查询的语句
     * @param params  填充sql语句中的问号占位符数
     * @param page    想要第几页的数据
     * @param pageNum 每页显示多少条数
     * @param clz     要封装成的实体元类型
     * @return pageList对象
     */
    public PageInfo queryByPageForMySQL(@NotNull String sql, @Nullable Object[] params, int page, int pageNum, @Nullable Class clz) {
        PageInfo pageInfo = new PageInfo();
        String rowSql = "select count(*) from (" + sql + ") temp";   //查询总行数sql
        int pages = 0;   //总页数
        int rows = (Integer) queryOneColumnForSingletonRow(rowSql, params, Integer.class);  //查询总行数
        //判断页数,如果是页大小的整数倍就为rows/pageRow如果不是整数倍就为rows/pageRow+1
        if (rows % pageNum == 0) {
            pages = rows / pageNum;
        } else {
            pages = rows / pageNum + 1;
        }
        //查询第page页的数据sql语句
        if (page <= 1) {
            sql += " limit 0," + pageNum;
        } else {
            sql += " limit " + ((page - 1) * pageNum) + "," + pageNum;
        }
        //查询第page页数据
        List list = null;
        if (clz != null) {
            list = queryForObjectList(sql, params, clz);
        } else {
            list = queryForMaps(sql, params);
        }

        pageInfo.setList(list);
        pageInfo.setPageSize(page);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPages(pages);
        pageInfo.setTotal(rows);

        return pageInfo;
    }



}
