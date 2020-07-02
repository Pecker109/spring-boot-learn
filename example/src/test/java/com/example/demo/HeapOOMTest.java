package com.example.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pecker
 * @Description java堆溢出实例
 * @since 2020-06-28
 * 原理：java的堆是用来存放对象实例的，所以我们只要做到以下三点就可以使堆溢出：
 * 1、限制堆的大小，不可扩展
 * 2、不断新建对象
 * 3、保持对象存活不被回收
 * 对应的，我们需要：
 * 1、改变JVM的启动参数，将堆的最小值和最大值设成一样，这样就可以避免堆自动扩展（其实不一样也可以）
 * 2、不断产生对象
 * 3、使用一个List来保存对象，保持对象存活
 *
 * JVM配置参数： -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 *
 */
public class HeapOOMTest {

    public static void main(String[] args) {
        // 此list实例会存放在堆内存中
        List<byte[]> list = new ArrayList<>();
        int i = 0;
        boolean flag = true;
        while (flag) {
            try {
                i++;
                // 每次增加一个1M大小的数组对象
                list.add(new byte[1024 * 1024]);
            } catch (Throwable e) {  // catch 捕获的是 Throwable，而不是 Exception。因为 OutOfMemoryError 不属于 Exception 的子类。
                e.printStackTrace();
                flag = false;
                // 记录次数
                System.out.println("count=" + i);
            }
        }

        // 不让进程结束，便于使用分析工具来查看内存情况
        try {
            Thread.sleep(24 * 60 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
