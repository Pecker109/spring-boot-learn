package com.example.demo.chain;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-01-14
 */
public class Client {

    public static void main(String[] args) {
        Handler director = new Director("张三");
        Handler manager = new Manager("李四");
        Handler topManager = new TopManager("王五");

        // 创建责任链
        director.setNextHandler(manager);
        manager.setNextHandler(topManager);

        // 发起请假申请
        boolean result1 = director.process(new LeaveRequest("小旋锋", 1));
        System.out.println("最终结果：" + result1 + "\n");

        boolean result2 = director.process(new LeaveRequest("小旋锋", 4));
        System.out.println("最终结果：" + result2 + "\n");

        boolean result3 = director.process(new LeaveRequest("小旋锋", 8));
        System.out.println("最终结果：" + result3 + "\n");
    }


}
