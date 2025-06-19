package com.example.demo.multiThread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2024-10-15
 */
@Slf4j
public class CompletableFutureTest {

    public static void main(String[] args) {

        /*
        API	作用	适用场景
        supplyAsync/runAsync	创建异步任务	执行耗时操作（如IO、计算）
        thenApply/thenAccept	链式处理结果	数据转换、日志记录
        thenCompose	串联多个异步任务	依赖前序结果的异步操作
        thenCombine	合并两个任务结果	并行执行独立任务后合并
        allOf/anyOf	等待所有/任一任务完成	批量任务处理
        exceptionally/handle	异常处理	错误降级或日志记录
        orTimeout	设置超时	防止任务长时间阻塞
         */

        //supplyAsync();
        //runAsync();
        //runWithExecutor();
        //thenApply();
        //thenApplyAsync();
        //thenAccept();
        //thenRun();
        //thenCompose();
        //thenCombine();
        //allOf();
        //anyOf();
        //exceptionally();
        //handle();
        complete();
    }

    @SneakyThrows
    public static void supplyAsync() {
        /*
         * 异步执行有返回值的任务（使用默认线程池 ForkJoinPool.commonPool()）。
         * get() 方法会阻塞
         */
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            //Thread.sleep(1000);
            return "Hello, World!";
        });
        System.out.println(future.get());

        System.out.println("主线程执行...");
    }

    @SneakyThrows
    public static void runAsync() {
        /*
         * 异步执行无返回值的任务。
         */
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Task executed");
        });
        // 阻塞等待任务完成
        future.get();

        System.out.println("主线程执行...");
    }

    @SneakyThrows
    public static void runWithExecutor() {
        /*
         *  自定义线程池
         */
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("注意线程名称...");
            return "Custom Thread Pool";
        }, executor);
        log.info("主线程执行");
    }

    @SneakyThrows
    public static void thenApply() {
        /*
         * 结果处理（链式操作）
         * 执行thenApply()任务的线程和执行supplyAsync任务的是同一个线程
         */
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("第一步执行,注意线程名称...");
            return "Hello";
        }).thenApply(s -> {
            log.info("第二步执行,注意线程名称...");
            return s + " World!";
        }); // 同步操作
        System.out.println(future.get()); // 输出: Hello World!
        log.info("主线程执行,注意线程名称...");
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public static void thenApplyAsync() {
        /*
         * 结果处理（链式操作）
         * 不指定自定义 executor 的情况下前后为同一个线程,相当于同步
         * 指定自定义 executor 的情况下为不同的线程,但也是同步,后面的线程需要等待前一个线程执行完
         * 线程
         */
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            log.info("第一步执行,注意线程名称...");
            return "Hello";
        }).thenApplyAsync(s -> {
            log.info("第二步执行,注意线程名称...");
            return s + " Async  World!";
        }, executor);
        System.out.println(future.get()); // 输出: Hello World!
        log.info("主线程执行,注意线程名称...");
    }

    @SneakyThrows
    public static void thenAccept() {
        /*
         * 结果处理（链式操作）
         * thenAccept()消费结果,无返回值
         */
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            log.info("第一步执行,注意线程名称...");
            return "Hello";
        }).thenAccept(s -> {
            log.info("第二步执行,注意线程名称...");
            log.info(s + " World!");
        });
        System.out.println(future.get());
        log.info("主线程执行,注意线程名称...");
    }

    @SneakyThrows
    public static void thenRun() {
        /*
         * 结果处理（链式操作）
         * thenRun() 任务完成后执行操作（不依赖结果）。
         */
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            sleep(1000);
            log.info("第一步执行,注意线程名称...");
            log.info("Hello");
        }).thenRun(() -> {
            log.info("第二步执行,注意线程名称...");
            log.info("Task Completed!");
        });
        System.out.println(future.get());
        log.info("主线程执行,注意线程名称...");
    }

    @SneakyThrows
    public static void thenCompose() {
        /*
         * 串联两个任务（前一个任务结果作为下一个任务的输入）与 thenApply 类似
         */
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " Composed"));
        System.out.println(future.get()); // 输出: Hello Composed

        log.info("主线程执行,注意线程名称...");
    }

    @SneakyThrows
    public static void thenCombine() {
        /*
         * 合并两个独立任务的结果。
         * 两个任务是独立的任务,是不同的线程并发执行的,但是会同步等待两个任务的结果进行合并.
         * 合并任务应该是执行较晚的线程进行合并
         */
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            //sleep(1000);
            log.info("future1 执行...");
            return "Hello";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            log.info("future2 执行...");
            return " World";
        });
        CompletableFuture<String> combined = future1.thenCombine(future2, (s1, s2) ->
        {
            log.info("合并任务执行...");
            return s1 + s2;
        });
        System.out.println(combined.get()); // 输出: Hello World

        log.info("主线程执行,注意线程名称...");
    }


    @SneakyThrows
    public static void allOf() {
        /*
         * 等待所有任务完成。
         * 与 thenCombine 类似,只是不需要前面任务的返回结果
         */
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            log.info("task1 执行...");
            return "Task1";
        });
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            log.info("task2 执行...");
            return "Task2";
        });
        CompletableFuture<Void> all = CompletableFuture.allOf(task1, task2);
        all.thenRun(() -> {
            log.info("All tasks completed");
        });

        sleep(5000);
        log.info("主线程执行,注意线程名称...");
    }

    @SneakyThrows
    public static void anyOf() {
        /*
         * 任一任务完成即触发。
         * 获取最快的返回结果,但是最快的抛异常,其他的都完蛋
         */
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            sleep(200);
            return "Task1";
        });
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> "Task2");
        CompletableFuture<Object> any = CompletableFuture.anyOf(task1, task2);
        System.out.println(any.get()); // 输出: Task2（先完成的任务结果）

        log.info("主线程执行,注意线程名称...");
    }

    @SneakyThrows
    public static void exceptionally() {
        /*
         * 异常处理:
         * 捕获异常并返回默认值。
         */
        CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Error!");
        }).exceptionally(ex -> {
            log.error("捕获异常");
            return "";
        });
        System.out.println(future.get());

        log.info("主线程执行,注意线程名称...");
    }

    @SneakyThrows
    public static void handle() {
        /*
         * 无论成功或失败，统一处理结果和异常
         */
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "Hello";
        }).handle((result, ex) -> {
            return ex != null ? "Error" : result + " Handled";
        });
        System.out.println(future.get()); // 输出: Hello Handled


        log.info("主线程执行,注意线程名称...");
    }

    @SneakyThrows
    public static void complete() {
        /*
         * 手动完成任务（设置结果或异常）
         */
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("Manual Result");
        System.out.println(future.get()); // 输出: Manual Result

        log.info("主线程执行,注意线程名称...");
    }


}
