package com.example.demo.multiThread;

/**
 * @author Pecker
 * @Description 卖票模型
 * @since 2020-04-21
 */
public class SellTicketThreat implements Runnable {
    int ticket = 10;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (this.ticket > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "卖票,ticket == " + this.ticket--);
                } else {
                    System.out.println("**** 票卖光了 ****");
                    break;
                }
            }
        }
    }

    public static class Buy {
        public static void main(String[] args) {
            SellTicketThreat sellThreat = new SellTicketThreat();
            new Thread(sellThreat, "窗口 A").start();
            new Thread(sellThreat, "窗口 B").start();
            new Thread(sellThreat, "窗口 C").start();
        }
    }
}
