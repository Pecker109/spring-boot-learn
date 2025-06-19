package com.mybatis.plus.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mybatis.plus.example.mapper.UserMapper;
import com.mybatis.plus.example.model.UserDO;
import com.mybatis.plus.example.service.NodeService;
import com.mybatis.plus.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

/**
 * @author Pecker
 * @Description 事务测试
 * @since 2021-11-23
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    NodeService nodeService;
    /**
     * userMapper
     */
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public void select1() {
        System.out.println(("----- selectAll method test ------"));
        PageHelper.startPage(1, 10);
        Page<UserDO> userList = (Page<UserDO>) userMapper.selectList(null);
        System.out.println("total: " + userList.getTotal());
        userList.forEach(System.out::println);
    }

    @Override
    public void insert1() {
        // 正常插入
        UserDO userDo = UserDO.builder()
                .age(18)
                .name(LocalDateTime.now().toString())
                .email("www.baidu.com")
                .build();

        userMapper.insert(userDo);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insert2() {
        // 正常插入,抛异常
        UserDO userDo = UserDO.builder()
                .age(18)
                .name(LocalDateTime.now().toString())
                .email("www.baidu.com")
                .build();

        userMapper.insert(userDo);

        throw new RuntimeException("");
    }

    @Override
    //@Transactional(rollbackFor = RuntimeException.class)
    public void insert3() {

        /*
         * 场景 1
         * insert3()有 @Transactional 标记,insertDo 没有@Transactional 标记,内部调用 insertDo(),
         * 当 insert3() 抛异常,insertDo() 依然会回滚.
         * 但是,该种指定传播机制为 NEVER ,是不会回滚的,说明生效的事务是 insert3(),内部调用 insetDo() 无论什么样都不会被动态代理.
         *
         * 场景 2:
         *  insert3()没有@Transactional 标记,insertDo 有@Transactional 标记,内部调用 insertDo(),
         *  当 insert3() 抛异常, 不会被回滚
         *  当 insertDo 抛异常,也不会回滚
         *  即使 insertDo()是 public 的重写方法也不会回滚.
         *  隔离级别是REQUIRES_NEW 和 REQUIRED 都不会回滚.
         *  也就是说内部调用 insetDo() 无论什么样都不会被动态代理.
         *  常说的内部方法调用事务不会生效是这种方式.
         *  内部方法是指本实例内的内部方法.
         *  如果是内部方法调用其他实例的公共方法,事务是可以生效的,例如 TransactionController#insert4Private()
         */

        // 内部方法调用
        this.insertDo();
        //throw new RuntimeException("");
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insert4() {
        // 正常插入,内部方法抛异常
        UserDO userDo = UserDO.builder()
                .age(18)
                .name(LocalDateTime.now().toString())
                .email("www.baidu.com")
                .build();

        userMapper.insert(userDo);

        throwException();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insert5() {
        nodeService.insert();

        throwException();
    }

    // 内部方法调用:@Transactional(propagation = Propagation.NEVER)
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertDo() {
        UserDO userDo = UserDO.builder()
                .age(18)
                .name(LocalDateTime.now().toString())
                .email("www.baidu.com")
                .build();

        userMapper.insert(userDo);

        throw new RuntimeException("");
    }

    private void throwException() {
        throw new RuntimeException("");
    }

    @Autowired
    TransactionTemplate transactionTemplate;

    @Override
    public void manualTxInsert1() {
        /*
            TransactionTemplate 编程式事务内部也是调用TransactionManager 进行事务的提交和回滚
            execute()和executeWithoutResult()方法内部实现逻辑是一模一样的,只是execute() 是有返回值的方式进行数据库操作
            什么时候回滚:内部抛出任意异常即可回滚
            什么时候提交:方法没有异常 && 未调用过transactionStatus.setRollbackOnly()
         */
        transactionTemplate.execute(transactionStatus -> {
            nodeService.insert();
            // 验证抛出异常事务回滚,不抛异常事务正常提交
            throwException();
            return null;
        });
    }
}
