package com.mybatis.plus.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mybatis.plus.example.mapper.UserMapper;
import com.mybatis.plus.example.model.UserDO;
import com.mybatis.plus.example.service.NodeService;
import com.mybatis.plus.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
}
