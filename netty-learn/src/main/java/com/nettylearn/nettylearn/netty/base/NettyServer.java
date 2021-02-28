package com.nettylearn.nettylearn.netty.base;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Pecker
 * @Description 基于 Netty 实现的服务端
 * @since 2021-02-27
 */
public class NettyServer {
    public static void main(String[] args) {

        /*
         * 创建 Netty 实现的服务端步骤:
         * 1.创建两个线程组:处理客户端连接事件的 bossGroup 和客户端业务处理的 workerGroup
         * 2.创建服务端的启动对象 ServerBootstrap
         * 3.配置启动对象参数(关键步骤)
         * 4.绑定服务端端口,生成 ChannelFuture
         * 5.各种关闭
         */


        /*
         * EventLoopGroup 继承自 Executor, 创建两个线程组
         * bossGroup : 用于处理所有的客户端连接事件
         * workerGroup : 用于处理所有的客户端读写事件
         */

        // 1.创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);

        try {
            // 2.创建服务端的启动对象 ServerBootstrap
            ServerBootstrap bootstrap = new ServerBootstrap();

            // 3.配置启动对象参数
            bootstrap.group(bossGroup, workerGroup)
                    // 使用NioServerSocketChannel作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    // 初始化服务器连接队列大小，服务端处理客户端连接请求是顺序处理的,所以同一时间只能处理一个客户端连接。
                    // 多个客户端同时来的时候,服务端将不能处理的客户端连接请求放在队列中等待处理
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 创建通道初始化对象，设置初始化参数，在 SocketChannel 建立起来之前执行
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 对 workerGroup 的 SocketChannel 设置处理器
                            ch.pipeline().addLast(new MyNettyServerHandler());
                        }
                    });
            // 至此,Netty 服务已经启动,但是尚未监听任何端口信息,也没有启动 Socket 服务
            System.out.println("netty server start。。");

            // 4.绑定服务端端口,生成 ChannelFuture
            // 绑定一个端口并且同步, 生成了一个ChannelFuture异步对象，通过isDone()等方法可以判断异步事件的执行情况
            // 启动服务器(并绑定端口)，bind是异步操作，sync方法是等待异步操作执行完毕
            ChannelFuture channelFuture = bootstrap.bind(9000).sync();

            // 给 ChannelFuture 注册监听器，监听我们关心的事件
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("监听端口9000成功");
                    } else {
                        System.out.println("监听端口9000失败");
                    }
                }
            });

            // 5.各种关闭
            // 等待服务端监听端口关闭，closeFuture是异步操作
            // 通过sync方法同步等待通道关闭处理完毕，这里会阻塞等待通道关闭完成，内部调用的是Object的wait()方法
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
