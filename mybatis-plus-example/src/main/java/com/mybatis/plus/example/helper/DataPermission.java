package com.mybatis.plus.example.helper;

import lombok.Data;

import java.util.List;

@Data
public class DataPermission {

    /**
     * 过滤数据类型
     */
    private String type;

    /**
     * 可见数据用户列表
     */
    List<String> userIds;

    /**
     * 需要动态拼接查询字段
     */
    private List<String> expressionItem;
}
