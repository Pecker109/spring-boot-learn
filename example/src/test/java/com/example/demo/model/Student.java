package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("userName")
    private String name;

    private Integer age;

    public int ageAdd(int age) {
        System.out.println("old age : " + age);
        return age + 1;
    }
}
