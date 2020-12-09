package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-10-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;

    private Integer age;

    private int ageAdd(int age) {
        System.out.println("old age : " + age);
        return age + 1;
    }
}
