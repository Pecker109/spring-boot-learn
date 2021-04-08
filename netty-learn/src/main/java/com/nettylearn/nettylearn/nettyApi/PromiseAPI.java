package com.nettylearn.nettylearn.nettyApi;

import io.netty.util.concurrent.*;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-03-24
 */
public class PromiseAPI {
    public static void main(String[] args) {
        // 构造线程池
        EventExecutor executor = new DefaultEventExecutor();

        // 创建 DefaultPromise 实例
        Promise promise = new DefaultPromise(executor);

        // 下面给这个 promise 添加两个 listener
        // 当前线程(Future)在调用 setSuccess(result) 或 setFailure(t) 成功后会回调 listeners 的回调函数
        promise.addListener(new GenericFutureListener<Future<Integer>>() {
            @Override
            public void operationComplete(Future future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("任务结束，结果：" + future.get());
                } else {
                    System.out.println("任务失败，异常：" + future.cause());
                }
            }
        }).addListener(new GenericFutureListener<Future<Integer>>() {
            @Override
            public void operationComplete(Future future) throws Exception {
                System.out.println("任务结束，balabala...");
            }
        });

        // 提交任务到线程池，五秒后执行结束，设置执行 promise 的结果
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                // 设置 promise 的结果
                // promise.setFailure(new RuntimeException());
                // 标记该 future 成功及设置其执行结果，并且会通知所有的 listeners
                promise.setSuccess(123456);
                System.out.println("promise.setSuccess(123456) 执行完毕");
            }
        });

        // main 线程阻塞等待执行结果
        try {
            System.out.println("main 线程阻塞等待执行结果...");
            promise.sync();
            Thread.sleep(200);
            System.out.println("promise.sync() 执行结束");
        } catch (InterruptedException e) {
        }
    }
}
