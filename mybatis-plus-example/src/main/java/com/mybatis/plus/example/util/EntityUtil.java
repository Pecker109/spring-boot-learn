package com.mybatis.plus.example.util;

import com.mybatis.plus.example.model.NodeDO;
import com.mybatis.plus.example.model.UserDO;

import java.time.LocalDateTime;

/**
 * @author Pecker
 * @Description 实体 Util
 * @since 2021-12-17
 */
public class EntityUtil {
    public static UserDO newUserDO() {
        return UserDO.builder()
                .age(18)
                .name(LocalDateTime.now().toString())
                .email("www.baidu.com")
                .build();
    }

    public static NodeDO newNodeDO() {
        return NodeDO.builder()
                .name(LocalDateTime.now().toString()).build();
    }
}
