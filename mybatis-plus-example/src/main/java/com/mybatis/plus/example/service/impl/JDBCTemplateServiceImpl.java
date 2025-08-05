//package com.mybatis.plus.example.service.impl;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.mybatis.plus.example.service.JDBCTemplateService;
//import org.apache.ibatis.session.SqlSession;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.SqlSessionUtils;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Properties;
//
///**
// * @author Pecker
// * @Description JDBCTemplate 测试
// * @since 2021-12-22
// */
//@Service
//public class JDBCTemplateServiceImpl implements JDBCTemplateService {
//
//    @Resource
//    protected SqlSessionTemplate sqlSessionTemplate;
//
//    @Override
//    public void getConfig() {
//        getNativeSqlSession().getConfiguration().getDefaultFetchSize();
//    }
//
//    @Override
//    public void select() {
//        DruidDataSource drs = (DruidDataSource) getNativeSqlSession().getConfiguration().getEnvironment().getDataSource();//前面实现各有不同主要从配置bean中获取DruidDataSource
//        int activeCount = drs.getActiveCount();//当前连接数
//        System.out.println(activeCount);
//
//        String sql = "select * from node";
//        for (int i = 0; i < 10; i++) {
//            runQuery(sql);
//        }
//
//        System.out.println(drs.getConnectCount());
//        System.out.println(drs.getCloseCount());
//
//    }
//
//    public void runQuery(String sql) {
//        try (Connection connection = getNativeSqlSession().getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            try (ResultSet rs = preparedStatement.executeQuery()) {
//                while (rs.next()) {
//                    // ... do something with result set
//                    System.out.println(rs.getString("name"));
//                }
//            }
//        } catch (SQLException e) {
//            // ... handle SQL exception
//        }
//
//        //try {
//        //    Connection connection = getNativeSqlSession().getConnection();
//        //    PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        //    ResultSet rs = preparedStatement.executeQuery();
//        //    while (rs.next()) {
//        //        // ... do something with result set
//        //        //System.out.println(rs.getString("name"));
//        //    }
//        //} catch (SQLException e) {
//        //    e.printStackTrace();
//        //}
//
//    }
//
//
//    protected SqlSession getNativeSqlSession() {
//        return SqlSessionUtils.getSqlSession(sqlSessionTemplate.getSqlSessionFactory(),
//                sqlSessionTemplate.getExecutorType(),
//                sqlSessionTemplate.getPersistenceExceptionTranslator());
//    }
//
//
//    protected void closeNativeSqlSession(SqlSession sqlSession) {
//        SqlSessionUtils.closeSqlSession(sqlSession, sqlSessionTemplate.getSqlSessionFactory());
//    }
//}
