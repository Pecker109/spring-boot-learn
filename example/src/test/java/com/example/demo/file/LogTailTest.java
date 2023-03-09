package com.example.demo.file;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;

import java.io.RandomAccessFile;

public class LogTailTest {


    @SneakyThrows
    public static void main(String args[]) {
        init();
        while (true) {
            refresh();
            Thread.sleep(1000 * 5);
        }
    }

    static long pos;

    @SneakyThrows
    private static void init() {
        RandomAccessFile f = new RandomAccessFile("/Users/apple/Documents/project/spring-boot-learn/example/src/main/resources/cfg/application.yml", "r");
        pos = f.length() - 1;
    }

    @SneakyThrows
    private static void refresh() {
        RandomAccessFile f = new RandomAccessFile("/Users/apple/Documents/project/spring-boot-learn/example/src/main/resources/cfg/application.yml", "r");
        f.seek(pos);
        String nextLine = f.readLine();
        while (StrUtil.isNotBlank(nextLine)) {
            System.out.println("next line = " + nextLine);
            nextLine = f.readLine();
        }
        pos = f.length() - 1;
        System.out.println("pos = " + pos);
    }
}