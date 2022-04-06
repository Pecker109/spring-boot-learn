package com.mybatis.plus.example.helper;

import java.util.List;

/**
 * 数据权限实体
 */
public class DataPermissionHelper {

    private static ThreadLocal<DataPermission> localDataPermission = new ThreadLocal<>();

    private static void setDataPermission(DataPermission dataPermission) {
        localDataPermission.set(dataPermission);
    }

    public static DataPermission getDataPermission() {
        return localDataPermission.get();
    }

    public static void clearDataPermission() {
        localDataPermission.remove();
    }

    public static void startDataPermission(String type, List<String> userIds, List<String> expressionItem) {
        DataPermission dataPermission = new DataPermission();
        dataPermission.setType(type);
        dataPermission.setUserIds(userIds);
        dataPermission.setExpressionItem(expressionItem);
        setDataPermission(dataPermission);
    }

}
