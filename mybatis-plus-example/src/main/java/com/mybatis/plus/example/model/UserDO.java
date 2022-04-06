package com.mybatis.plus.example.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-11-24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class UserDO {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private Integer age;
    /**
     *
     */
    private String email;
}
