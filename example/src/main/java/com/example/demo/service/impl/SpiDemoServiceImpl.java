package com.example.demo.service.impl;

import com.example.demo.annotation.DoneTime;
import com.example.demo.service.SpiDemoService;
import org.springframework.stereotype.Service;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-03-03
 */
@Service
public class SpiDemoServiceImpl implements SpiDemoService {
    @DoneTime
    @Override
    public void work() {
        System.out.println("SPI ,do work ....");
    }
}
