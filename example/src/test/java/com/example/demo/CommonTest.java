package com.example.demo;

import cn.hutool.core.util.StrUtil;
import io.lettuce.core.output.StatusOutput;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * idea 破解方式连接:https://www.exception.site/essay/how-to-free-use-intellij-idea-2019-3
 * 修改了配置文件: /Users/apple/Library/Application Support/JetBrains/IntelliJIdea2021.3/idea.vmoptions 的方式
 * 添加了配置:-javaagent:/Users/apple/Applications/conf/idea/ja-netfilter/ja-netfilter.jar
 *
 * @author Pecker
 * @Description 类信息
 * @since 2020-10-12
 */
public class CommonTest {

    public static void main(String[] args) throws Exception {

        Map<Integer,Set<Integer>> map = new HashMap<>();
        Set<Integer> set = new HashSet<>();
        set.add(1);
        map.put(1,set);
        add(map);

        System.out.println(map.get(1));
        /*
        此卖家腹黑心坏,贪图小利,无信无德,约定好的看货,到地方后你不先拍单不给你回复消息,不给你验货,外面4级大风,室外温度-1度,体感温度-7度,这种天气让我等了40分钟,期间距离我上次发消息大概25分钟的时候,回复消息暗示我要先下单(卖家本人说法是委婉提醒),我立马回复她消息提出是否可以先查看商品对方却已经不再回复消息,又等了十几分钟后便不再等了,一个多小时后还是回复我要先下单再验货,这才确认是被戏耍了(一开始就觉得不对劲,只是默默告诉自己都有忙着不看手机的时候).该卖家商品上明确标注了"当面查看,二手物品,售出不退",却逼着你先下单,倒反天罡,实属下作.此卖家商品是否存在质量问题因没看到货不做评价,但是人品就是上述描述的情况,希望各位鱼友在与此卖家交易时小心谨慎,千万,千万,千万当心留意.更希望鱼友在与任何人进行所有交易时,先验货,再下单,否则只能吃亏是福.
         */

    }

    public static void add(Map<Integer,Set<Integer>> map){
        Set<Integer> set = map.get(1);
        set.add(2);
    }


}
