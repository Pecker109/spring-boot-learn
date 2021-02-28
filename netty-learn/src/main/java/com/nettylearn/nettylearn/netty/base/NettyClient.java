package com.nettylearn.nettylearn.netty.base;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Pecker
 * @Description 基于 Netty 实现的客户端
 * @since 2021-02-27
 */
public class NettyClient {
    public static void main(String[] args) {
        /*
         * 创建 Netty 实现的客户端步骤:
         * 1.只需创建一个线程组:用作和服务端的业务处理
         * 2.创建客户端的启动对象 Bootstrap
         * 3.配置启动对象参数(关键步骤)
         * 4.启动客户端去连接服务器端
         * 5.各种关闭
         */

        // 1.只需创建一个线程组:用作和服务端的业务处理
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 2.创建客户端的启动对象 Bootstrap
            // 注意客户端使用的不是ServerBootstrap而是Bootstrap
            Bootstrap bootstrap = new Bootstrap();

            // 3.配置启动对象参数(关键步骤)
            bootstrap.group(group)
                    // 使用 NioSocketChannel 作为客户端的通道实现
                    // 注意这里也要区别于服务端的 NioServerSocketChannel
                    .channel(NioSocketChannel.class)
                    // 初始化通道
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 向 SocketChannel 设置处理器
                            ch.pipeline().addLast(new MyNettyClientHandler());
                        }
                    });

            // 至此,Netty 客户端已经启动,但是尚未连接服务器
            System.out.println("netty client start。。");

            // 4.启动客户端去连接服务器端
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 9000).sync();

            // 5.对通道关闭进行监听
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
