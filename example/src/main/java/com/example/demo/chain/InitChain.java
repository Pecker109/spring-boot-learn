package com.example.demo.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-01-14
 */
@Configuration
public class InitChain {

    @Autowired
    private List<AbstractChain> abstractChainList;

    @PostConstruct
    public void initChain() {
        //排序
        Collections.sort(abstractChainList, AnnotationAwareOrderComparator.INSTANCE);

        //设置 next 参数
        int size = abstractChainList.size();
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                abstractChainList.get(i).setNext(null);
            } else {
                abstractChainList.get(i).setNext(abstractChainList.get(i + 1));
            }
        }
    }
}
