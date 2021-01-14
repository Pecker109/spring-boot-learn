package com.example.demo.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-01-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequest {
    private String name;    // 请假人姓名
    private int numOfDays;  // 请假天数
}
