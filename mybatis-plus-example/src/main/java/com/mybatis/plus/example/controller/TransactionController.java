package com.mybatis.plus.example.controller;

import com.mybatis.plus.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pecker
 * @Description 事务测试 Controller
 * @since 2021-11-23
 */
@RestController
@RequestMapping("/tx")
public class TransactionController {

    /**
     *
     */
    @Autowired
    TransactionService transactionService;

    @GetMapping("/select1")
    public String select1() {
        transactionService.select1();
        return "success";
    }

    @GetMapping("/insert1")
    public String insert1() {
        transactionService.insert1();
        return "success";
    }

    @GetMapping("/insert2")
    public String insert2() {
        transactionService.insert2();
        return "success";
    }

    @GetMapping("/insert3")
    public String insert3() {
        transactionService.insert3();
        return "success";
    }

    @GetMapping("/insert4")
    public String insert4() {
        transactionService.insert4();
        return "success";
    }

    @GetMapping("/insert4Private")
    public String insert4Private() {
        insertPrivate();
        return "success";
    }

    private void insertPrivate() {
        transactionService.insert4();
    }

    @GetMapping("/insert5")
    public String insert5() {
        transactionService.insert5();
        return "success";
    }
}
