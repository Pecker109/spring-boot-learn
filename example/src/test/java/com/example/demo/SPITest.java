package com.example.demo;

import com.example.demo.service.SpiDemoService;

import java.util.ServiceLoader;

/**
 * @author Pecker
 * @Description Java SPI 机制
 * @since 2021-03-03
 */
public class SPITest {
    public static void main(String[] args) {
        ServiceLoader<SpiDemoService> services = ServiceLoader.load(SpiDemoService.class);
        services.forEach(SpiDemoService::work);
    }
}
