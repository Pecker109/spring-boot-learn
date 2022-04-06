package com.example.demo;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-02-26
 */
public class BaseTypeSizeTest {
    public static void main(String[] args) {
        //byte类型所占的字节数求法
        System.out.println("byte的二进制位数为：" + Byte.SIZE);
        System.out.println("byte所占的字节数为：" + Byte.BYTES);
        //一个字节占8个二进制位        
        //short类型所占的字节数求法
        System.out.println("short的二进制位数为：" + Short.SIZE);
        System.out.println("short所占的字节数为：" + Short.BYTES);
        //int类型所占的字节数求法
        System.out.println("int的二进制位数为：" + Integer.SIZE);
        System.out.println("int所占的字节数为：" + Integer.BYTES);
        System.out.println("int 最大值为: " + Integer.MAX_VALUE);
        //long类型所占的字节数求法
        System.out.println("long的二进制位数为：" + Long.SIZE);
        System.out.println("long所占的字节数为：" + Long.BYTES);
        //float类型所占的字节数求法
        System.out.println("float的二进制位数为：" + Float.SIZE);
        System.out.println("float所占的字节数为：" + Float.BYTES);
        //double类型所占的字节数求法
        System.out.println("double的二进制位数为：" + Double.SIZE);
        System.out.println("double所占的字节数为：" + Double.BYTES);
        //char类型所占的字节数求法
        System.out.println("char的二进制位数为：" + Character.SIZE);
        System.out.println("char所占的字节数为：" + Character.BYTES);

    }
}
