package com.nettylearn.nettylearn.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-02-27
 */
public class NettyByteBuf {

    public static void main(String[] args) {
        /*
         * 创建byteBuf对象，该对象内部包含一个字节数组byte[10]
         * 通过 readerIndex 和 writerIndex 和 capacity，将buffer分成三个区域
         * 已经读取的区域：[0,readerIndex)
         * 可读取的区域：[readerIndex,writerIndex)
         * 可写的区域: [writerIndex,capacity)
         */

        ByteBuf byteBuf = Unpooled.buffer(1);
        System.out.println("初始容量为 1 , byteBuf= " + byteBuf);

        for (int i = 0; i < 8; i++) {
            byteBuf.writeByte(i);
        }
        System.out.println("写入 8 字节,byteBuf= " + byteBuf);

        for (int i = 0; i < 5; i++) {
            System.out.println(byteBuf.getByte(i));
        }
        System.out.println("getByte() 只后,注意是getByte, byteBuf =" + byteBuf);

        for (int i = 0; i < 5; i++) {
            System.out.println(byteBuf.readByte());
        }
        System.out.println("readByte() 之后, byteBuf =" + byteBuf);


        //用 Unpooled 工具类创建 ByteBuf
        ByteBuf byteBuf2 = Unpooled.copiedBuffer("hello,Netty!", CharsetUtil.UTF_8);
        //使用相关的方法
        if (byteBuf2.hasArray()) {
            byte[] content = byteBuf2.array();
            //将 content 转成字符串
            System.out.println("将 ByteBuf 转成字符串 : " + new String(content, CharsetUtil.UTF_8));
            System.out.println("ByteBuf2 : " + byteBuf2);

            System.out.println("获取数组[0]这个位置的字符'h'的 ascii 码" + byteBuf2.getByte(0)); // 获取数组0这个位置的字符h的ascii码，h=104

            int len = byteBuf2.readableBytes(); //可读的字节数
            System.out.println("获取可读字节数 : " + len);

            //使用for取出各个字节
            System.out.println("逐个读取各个字节...");
            for (int i = 0; i < len; i++) {
                System.out.println((char) byteBuf2.getByte(i));
            }

            //范围读取
            System.out.println("范围读取 [0,6]" + byteBuf2.getCharSequence(0, 6, CharsetUtil.UTF_8));
            System.out.println("范围读取 [6,12]" + byteBuf2.getCharSequence(6, 6, CharsetUtil.UTF_8));
        }
    }

}
