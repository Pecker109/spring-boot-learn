package com.mybatis.plus.example.controller;

import com.mybatis.plus.example.service.BatchInvokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pecker
 * @Description 事务测试 Controller
 * @since 2024-04-28
 */
@RestController
public class BatchInvokeController {

    /**
     *
     */
    @Autowired
    BatchInvokeService batchInvokeService;

    @GetMapping("/batch1")
    public Integer batch1() {
        return batchInvokeService.batch1();
    }

}
