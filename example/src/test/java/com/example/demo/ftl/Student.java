package com.example.demo.ftl;

import java.io.Serializable;
import lombok.Data;

/**
* <p>
    * 学生信息
    * </p>
*
* @author 张三
* @since 2022-11-25
*/
@Data
public class Student implements Serializable {



    /**
    * 姓名
    */
    private String name;

    /**
    * 年龄
    */
    private Integer age;

}