package com.mybatis.plus.example.service.impl;

import com.mybatis.plus.example.mapper.NodeMapper;
import com.mybatis.plus.example.mapper.UserMapper;
import com.mybatis.plus.example.service.NodeService;
import com.mybatis.plus.example.service.PropagationService;
import com.mybatis.plus.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Pecker
 * @Description 事务测试
 * @since 2021-12-17
 */
@Service
public class PropagationServiceImpl implements PropagationService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    NodeMapper nodeMapper;

    @Autowired
    UserService userService;

    @Autowired
    NodeService nodeService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void propagationTest() {

        /**
         * 事务传播特性:
         * PROPAGATION_REQUIRED：默认,如果不存在外层事务，就主动创建事务；否则使用外层事务.(外层有就用外层,外层没有就新建事务)
         * PROPAGATION_SUPPORTS：如果不存在外层事务，就不开启事务；否则使用外层事务.(外层有就有,没有就不用)
         * PROPAGATION_MANDATORY：如果不存在外层事务，就抛出异常；否则使用外层事务.(外层有就用,没有就抛异常)
         * PROPAGATION_REQUIRES_NEW：总是主动开启事务；如果存在外层事务，就将外层事务挂起
         * PROPAGATION_NOT_SUPPORTED：总是不开启事务；如果存在外层事务，就将外层事务挂起
         * PROPAGATION_NEVER：总是不开启事务；如果存在外层事务，则抛出异常
         * PROPAGATION_NESTED：如果不存在外层事务，就主动创建事务；否则创建嵌套的子事务
         *
         */

        /*
         * 方式一: 三个事务全都开启,传播特性 Propagation.REQUIRED ==>  具有原子性
         * 方式二: 外层事务关闭,
         * 内层一个开启事务,传播特性Propagation.REQUIRED,一个关闭事务(关闭的抛异常) ==> 不具有原子性,只对标注@Transactional 的接口会回滚
         * 方式三: 外层事务关闭,
         * 内层事务全都开启,传播特性 Propagation.SUPPORTS,事务都不生效
         * 方式四: 外层事务关闭,
         * 内层事务全都开启,传播特性 Propagation.MANDATORY,抛异常
         * 方式五: 外层事务 传播特性Propagation.REQUIRED
         * 内层事务第一个传播特性Propagation.REQUIRED,第二个传播特性Propagation.REQUIRES_NEW,外层抛异常 ==> REQUIRES_NEW就很吊,自己开启自己的事务,不随外层的回滚
         * 方式六: 外层事务 传播特性Propagation.REQUIRED
         * 内层事务第一个传播特性Propagation.REQUIRED,第二个传播特性Propagation.NOT_SUPPORTED,外层抛异常,第二个抛异常 ==> NOT_SUPPORTED也很吊,外层爱啥啥,反正老子关闭事务
         * 方式七: 外层事务 传播特性Propagation.REQUIRED
         * 内层事务第一个传播特性Propagation.REQUIRED,第二个传播特性Propagation.NEVER,第二个抛异常 ==> NEVER更吊,外层都不能有,有了就抛异常
         *
         * REQUIRED 和 REQUIRES_NEW 比较: 外层不存在事务都是可以创建事务,当外层存在事务,REQUIRED 是使用外层事务,REQUIRES_NEW不随外层事务回滚二回滚,只有自己的逻辑异常才会回滚
         */

        userService.insert();
        nodeService.insert();

        //throw new RuntimeException();

    }


    public void userInsertRequire() {
    }

    public void nodeInsertRequire() {
    }
}
