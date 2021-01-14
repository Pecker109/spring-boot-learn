package com.example.demo;

import java.util.Properties;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2020-12-11
 */
public class KafkaSendTest {
//    public static void main(String[] args) {
//
//        String message = "";
//
//        Properties p = new Properties();
//        p.put("bootstrap.servers", "saas-7.101:9092");
//        p.put("acks", "all");
//        p.put("metadata,fetch.timeout.ms", 30001);
//        p.put("retries", 0);
//        p.put("batch.size", 16384);
//        p.put("linger.ms", 1);
//        p.put("buffer.memory", 999999);
//        p.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        p.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        Producer producer = new KafkaProducer(p);
//
//        producer.send(new ProducerRecord("com.dbappsecurity.engine.status", message), callback);
//        producer.flush();
//    }
//
//    private final static Callback callback = (record, e) -> {
//        if (e != null) {
//            System.out.println("===============error===============");
//        }
//    };
}
