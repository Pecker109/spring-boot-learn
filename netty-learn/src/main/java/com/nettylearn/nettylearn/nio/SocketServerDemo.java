package com.nettylearn.nettylearn.nio;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-02-06
 */
@Slf4j
public class SocketServerDemo {

    public static void main(String[] args) throws Exception {

        /**
         * NIO 实现思想:
         * 1.事件驱动 : 事件驱动就是利用操作系统内核的特性来做 IO 处理.
         *      事件响应是操作系统内核做的事情,比如有客户端连接(accept),或者读写事件,
         *      操作系统就会把这些事件放到 rdList 集合中,
         *      Java 程序的 Selector#select() 方法会调用 Linux 的内核函数 select()函数,来获取这个rdList 中的所有事件.
         * 2.多路复用 : 一个线程解决所有连接请求,多个通路一个线程处理
         * 3.多路复用器 Selector: 可以把多路复用器理解为: 多路复用器维护着一个事件集合rdList , 不同类型的事件会存在同一个集合中
         * 4.多路复用器实现模型 : select、poll、epol
         * 5.Channel : NIO 编程思想就是通过处理不同的 Channel 来进行操作
         *      客户端连接: ServerSocketChannel
         *      读取客户端数据: SocketChannel
         */

        // ServerSocketChannel 绑定ServerSocket,来监听客户端 Socket
        // ServerSocketChannel 注册到多路复用器,并且只能注册 accept 事件
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        ServerSocket serverSocket = ssc.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(9000));

        // 多路复用器 selector
        // 不同的操作系统返回的多路复用器不同,Linux 内核会创建 ePoll 模型
        Selector selector = Selector.open();//文件描述符
        //注意: ServerSocketChannel 只能注册SelectionKey.OP_ACCEPT事件
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        try {
            while (true) {
                // 阻塞式等待需要处理的事件,注意这里等待的是事件,而且是需要处理的事件,而不是连接
                // select 方法底层实现是调用 Linux 的内核函数 select()函数,会从 rdList 中获取所有的事件
                // 事件响应:是操作系统内核做的事情,有 accept 事件,读写事件操作系统会把这些事件放到一个 rdList 集合中,Java 程序的selector.select() 方法就是获取这个rdList 中的所有事件.
                // java 程序对多路复用 IO 的支持也包括了阻塞模式和非阻塞模式两种。
                if (selector.select(100) == 0) {
                    //================================================
                    //      这里视业务情况，可以做一些然并卵的事情
                    //================================================
                    continue;
                }
                //这里会获得本次询问操作系统，所获取到的“所关心的事件”的事件类型(每一个通道都是独立的)
                Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();

                while (selectionKeys.hasNext()) {
                    SelectionKey readyKey = selectionKeys.next();
                    //已经处理的readyKey一定要移除。如果不移除，就会一直存在在selector.selectedKeys集合中
                    //待到下一次selector.select() > 0时，这个readyKey又会被处理一次
                    selectionKeys.remove();

                    SelectableChannel selectableChannel = readyKey.channel();
                    // 客户端连接 : accept 事件
                    if (readyKey.isValid() && readyKey.isAcceptable()) {
                        log.info("======channel通道已经准备好=======");
                        /*
                         * 当server socket channel通道已经准备好，就可以从server socket channel中获取socket channel了
                         * 拿到socket channel后，要做的事情就是马上到selector注册这个socket channel感兴趣的事情。
                         * 否则无法监听到这个socket channel到达的数据
                         * */
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectableChannel;
                        // SocketChannel 感兴趣的事件有: OP_READ  OP_WRITE  OP_CONNECT
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        registerSocketChannel(socketChannel, selector);

                    } else if (readyKey.isValid() && readyKey.isConnectable()) {
                        log.info("======socket channel 建立连接=======");
                    } else if (readyKey.isValid() && readyKey.isReadable()) {
                        log.info("======socket channel 数据准备完成，可以去读==读取=======");
                        readSocketChannel(readyKey);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            serverSocket.close();
        }
    }

    /**
     * 在 ServerSocketChannel 接收到一个新的 TCP 连接后,就会向程序返回一个新的 SocketChannel。
     * 但是这个新的 SocketChannel 并没有在selector“选择器/代理器”中注册，
     * 所以程序还没法通过 selector 通知这个 SocketChannel 的事件。
     * 于是我们拿到新的socket channel后，要做的第一个事情就是到selector“选择器/代理器”中注册这个 SocketChannel 感兴趣的事件
     *
     * @param socketChannel 新的socket channel
     * @param selector      selector“选择器/代理器”
     * @throws Exception
     */
    private static void registerSocketChannel(SocketChannel socketChannel, Selector selector) throws Exception {
        socketChannel.configureBlocking(false);
        //socket通道可以且只可以注册三种事件SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT
        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(2048));
    }

    /**
     * 这个方法用于读取从客户端传来的信息。
     * 并且观察从客户端过来的socket channel在经过多次传输后，是否完成传输。
     * 如果传输完成，则返回一个true的标记。
     */
    private static void readSocketChannel(SelectionKey readyKey) throws Exception {
        SocketChannel clientSocketChannel = (SocketChannel) readyKey.channel();
        //获取客户端使用的端口
        InetSocketAddress sourceSocketAddress = (InetSocketAddress) clientSocketChannel.getRemoteAddress();
        Integer resourcePort = sourceSocketAddress.getPort();

        //拿到这个socket channel使用的缓存区，准备读取数据
        //在后文，将详细讲解缓存区的用法概念，实际上重要的就是三个元素capacity,position和limit。
        ByteBuffer contextBytes = (ByteBuffer) readyKey.attachment();
        //将通道的数据写入到缓存区，注意是写入到缓存区。
        //由于之前设置了ByteBuffer的大小为2048 byte，所以可以存在写入不完的情况
        //没关系，我们后面来调整代码。这里我们暂时理解为一次接受可以完成
        int realLen = -1;
        try {
            realLen = clientSocketChannel.read(contextBytes);
        } catch (Exception e) {
            //这里抛出了异常，一般就是客户端因为某种原因终止了。所以关闭channel就行了
            log.error(e.getMessage());
            clientSocketChannel.close();
            return;
        }

        //如果缓存区中没有任何数据(但实际上这个不太可能，否则就不会触发OP_READ事件了)
        if (realLen == -1) {
            log.warn("====缓存区没有数据? ====");
            return;
        }

        //将缓存区从写状态切换为读状态(实际上这个方法是读写模式互切换)。
        //这是java nio框架中的这个socket channel的写请求将全部等待。
        contextBytes.flip();
        //注意中文乱码的问题，我个人喜好是使用URLDecoder/URLEncoder，进行解编码。
        //当然java nio框架本身也提供编解码方式，看个人咯
        byte[] messageBytes = contextBytes.array();
        String messageEncode = new String(messageBytes, "UTF-8");
        String message = URLDecoder.decode(messageEncode, "UTF-8");

        //如果收到了“over”关键字，才会清空buffer，并回发数据；
        //否则不清空缓存，还要还原buffer的“写状态”
        if (message.contains("over")) {
            //清空已经读取的缓存，并从新切换为写状态(这里要注意clear()和capacity()两个方法的区别)
            contextBytes.clear();
            log.info("端口:" + resourcePort + "客户端发来的信息======message : " + message);

            //======================================================
            //          当然接受完成后，可以在这里正式处理业务了        
            //======================================================

            //回发数据，并关闭channel
            ByteBuffer sendBuffer = ByteBuffer.wrap(URLEncoder.encode("回发处理结果", "UTF-8").getBytes());
            clientSocketChannel.write(sendBuffer);
            clientSocketChannel.close();
        } else {
            log.info("端口:" + resourcePort + "客户端信息还未接受完，继续接受======message : " + message);
            //这是，limit和capacity的值一致，position的位置是realLen的位置
            contextBytes.position(realLen);
            contextBytes.limit(contextBytes.capacity());
        }
    }
}
