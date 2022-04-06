package com.example.demo.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CreatObjectScheduled {

    //@Scheduled(cron = "*/30 * * * * ?")
    public void TopK() {
        for (int i = 0; i < 1000; i++) {
            log.info("分配内存.........");
            byte[] allocation = new byte[1024];
        }
    }
}
