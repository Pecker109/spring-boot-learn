package com.pecker.demo.learn.multiThread.producerConsumerSyn;


/**
 * @author Pecker
 * @Description 生产者消费者模型, 解决存在的问题:不是生产者生产一个消费者去消费一个
 * 这种处理形式是在多线程开发过程中最原始的的处理方案,整个过程的等待,同步,唤醒都有开发者代码控制
 * @since 2020-04-22
 */
public class ProducerAndConsumer {

    public static void main(String[] args) {
        Message msg = new Message();
        new Thread(new Producer(msg)).start();
        new Thread(new Consumer(msg)).start();
    }
}


/**
 * @author Pecker
 * @Description 生产者生产 message,消费之消费 message
 * @since 2020-04-22
 */
class Message {
    private String title;
    private String content;
    /**
     * 标识生产或消费的形式:true ==> 允许生产,不允许消费,false ==>允许消费,不允许生产
     */
    private Boolean flag = true;

    public Message() {
    }

    /**
     * 同一时间也只允许一个消费者消费
     *
     * @return
     */
    public synchronized String get() {
        //还未生产,需要等待
        if (flag) {
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            return this.title + "===>" + this.content;
        } finally {
            //继续生产
            flag = true;
            //唤醒等待线程
            super.notify();
        }
    }


    /**
     * 生产Message 为临界区,保证同一时间只有同一个生产者生产
     *
     * @param title   title
     * @param content content
     */
    public synchronized void set(String title, String content) {
        if (!this.flag) {
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.title = title;
        this.content = content;
        //已经生产过了
        this.flag = false;
        //唤醒等待线程去读
        super.notify();
    }
}

/**
 * @author Pecker
 * @Description 生产者模型
 * @since 2020-04-22
 */
class Producer implements Runnable {
    private Message msg;

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.msg.set("产品 A", "配件 A");
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.msg.set("产品 B", "配件 B");
            }
        }
    }

    public Producer(Message msg) {
        this.msg = msg;
    }
}


/**
 * @author Pecker
 * @Description 消费者模型
 * @since 2020-04-22
 */
class Consumer implements Runnable {

    private Message msg;

    public Consumer(Message msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("消费者消费:" + this.msg.get());
        }
    }
}
