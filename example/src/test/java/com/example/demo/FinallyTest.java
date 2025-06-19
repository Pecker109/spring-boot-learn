package com.example.demo;

public class FinallyTest {
    public static void main(String[] args) {

        /*
         * 捕获了异常会先执行 catch 里代码
         * 异常未捕获,则先执行 finally 逻辑,再抛出异常
         */
        try {
            System.out.println("业务逻辑");
            throw new RuntimeException();
        }
        //catch (Exception e) {
        //    System.out.println("异常捕获");
        //}
        finally {
            System.out.println("执行 Finally");
        }
    }
}
