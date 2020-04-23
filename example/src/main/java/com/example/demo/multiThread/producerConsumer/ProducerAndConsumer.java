package com.example.demo.multiThread.producerConsumer;


/**
 * @author Pecker
 * @Description 生产者消费者模型, 这是个没有任何同步处理的示例, 会存在的问题:不是生产者生产一个消费者去消费一个;存在重复成产,重复消费问题
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

    public Message() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
                this.msg.setTitle("产品 A");
                this.msg.setContent("配件 A");
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.msg.setTitle("产品 B");
                this.msg.setContent("配件 B");
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
            System.out.println("消费者消费:" + this.msg.getTitle() + "======>" + this.msg.getContent());
        }
    }
}
