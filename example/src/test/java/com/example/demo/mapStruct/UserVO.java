package com.example.demo.mapStruct;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-03-22
 */
@Data
@Accessors(chain = true)
public class UserVO {
    private Long id;
    private String username;
    private String password;
    private Integer gender;
    private LocalDate birthday;
    private String createTime;
    private List<UserConfig> config;
    private String test; // 测试字段
    @Data
    public static class UserConfig {
        private String field1;
        private Integer field2;
    }
}
