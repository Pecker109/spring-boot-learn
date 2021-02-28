package com.nettylearn.nettylearn.bio;

import sun.nio.cs.UTF_32;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-02-27
 */
public class BIOClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", 9000);
        //向服务端发送数据
        socket.getOutputStream().write("HelloServer".getBytes(StandardCharsets.UTF_8));
        socket.getOutputStream().flush();
        System.out.println("向服务端发送数据结束");
        byte[] bytes = new byte[1024];
        //接收服务端回传的数据
        socket.getInputStream().read(bytes);
        System.out.println("接收到服务端的数据：" + new String(bytes));
        socket.close();
    }
}
