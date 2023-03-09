package com.example.demo.leetcode;

/**
 * 双指针算法
 */
public class TwoPoint {
    /**
     * 双指针算法,一般看到有序数组就可以考虑下是否可以用双指针算法.
     * 双指针算法说白了两个变量,一变量保存一个值.利用这两个变量与所求 target 进行匹配的过程.
     * 快慢指针:SortArrayDuplicates#removeDuplicates(),快指针一直往前加,慢指针符合条件才往前加
     * 快慢指针:LinkCycle#hasCycleByTwoPoint(),快指针比一次往前移动两段位移,慢指针一次移动一次
     * 总分指针:ArrayCenterIndex#pivotIndex().总指针符合递减,分指针递增
     * 左右指针:TwoSum#twoPoint().左指针从数组第0 位往后移,右指针从数组最后一位往前移.
     * 前后指针:Fibonacci#calByTwoPoint().前指针和后指针以相同的频率向前移动.其实也是个滑动窗口算法
     * 滑动指针:MergeSortArray#mergeByTwoPoint1()两个指针没有快慢之分,只有符合条件才去移动指针
     * 滑动指针:SubArrayMaxArg#getMaxArg()间隔 k,两指针同时移动
     */
}
