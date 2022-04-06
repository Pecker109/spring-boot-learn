//package com.mybatis.plus.example.helper;
//
//import cn.hutool.core.util.ObjectUtil;
//import cn.hutool.core.util.StrUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.cache.CacheKey;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.session.Configuration;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//
//import java.util.Properties;
//
//@Intercepts(
//        {
//                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
//                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
//        }
//)
//@Slf4j
//public class DataPermissionInterceptor  implements Interceptor {
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        Object[] args = invocation.getArgs();
//        MappedStatement ms = (MappedStatement) args[0];
//        Object parameter = args[1];
//        RowBounds rowBounds = (RowBounds) args[2];
//        ResultHandler resultHandler = (ResultHandler) args[3];
//        Executor executor = (Executor) invocation.getTarget();
//        CacheKey cacheKey;
//        BoundSql boundSql;
//        //由于逻辑关系，只会进入一次
//        if(args.length == 4){
//            //4 个参数时
//            boundSql = ms.getBoundSql(parameter);
//            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
//        } else {
//            //6 个参数时
//            cacheKey = (CacheKey) args[4];
//            boundSql = (BoundSql) args[5];
//        }
//
//        //如果不为空需要进行数据权限过滤且非超级管理员
//        SysUserInfoEntity userInfoEntity = BaseContextHandler.getUserInfo(SysUserInfoEntity.class);
//        if (ObjectUtil.isNotNull(DataPermissionHelper.getDataPermission())
//                && ObjectUtil.isNotNull(userInfoEntity)
//                && StrUtil.equals(userInfoEntity.getUserType(), UserType.GROUP_ADMIN.getValue())) {
//            Configuration configuration = ms.getConfiguration();
//            String permissionSql = permissionSql(boundSql);
//            BoundSql permissionBoundSql = new BoundSql(configuration, permissionSql, boundSql.getParameterMappings(), parameter);
//            DataPermissionHelper.clearDataPermission();
//            return executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, cacheKey, permissionBoundSql);
//        }
//        DataPermissionHelper.clearDataPermission();
//        return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//
//    private String permissionSql(BoundSql boundSql) {
//        //如果有特殊的sql处理可根据DataPermission中的type路由到特定的处理方法
//        DataPermission dataPermission = DataPermissionHelper.getDataPermission();
//        return DataPermissionSqlHandle.getPermissionSql(boundSql);
//    }
//}
