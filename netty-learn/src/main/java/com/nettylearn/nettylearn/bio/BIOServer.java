package com.nettylearn.nettylearn.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author Pecker
 * @Description BIO 服务端
 * @since 2021-02-27
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {

        //创建 SocketServer 监听端口
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            System.out.println("服务端开启,等待客户端连接...");
            //阻塞式接收客户端连接: accept()方法会一直阻塞直到有接收到客户端连接请求
            Socket clientSocket = serverSocket.accept();
            System.out.println("服务端接收到客户端请求...");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        handle(clientSocket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 处理客户端数据,读取客户端数据也会阻塞
     * @param clientSocket
     * @throws IOException
     */
    private static void handle(Socket clientSocket) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("处理客户端请求,准备 Read...");
        //阻塞式读取客户端数据,直到客户端发来数据
        int read = clientSocket.getInputStream().read();
        System.out.println("收到客户端数据,read 完毕...");
        if (read != -1){
            System.out.println("接收到客户端的数据：" + new String(bytes, 0, read, StandardCharsets.UTF_8));
        }
    }
}
