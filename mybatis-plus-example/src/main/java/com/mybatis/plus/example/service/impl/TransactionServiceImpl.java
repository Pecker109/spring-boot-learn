package com.mybatis.plus.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mybatis.plus.example.mapper.UserMapper;
import com.mybatis.plus.example.model.UserDO;
import com.mybatis.plus.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Pecker
 * @Description 事务测试
 * @since 2021-11-23
 */
@Service
public class TransactionServiceImpl implements TransactionService {

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
    @Transactional(rollbackFor = RuntimeException.class)
    public void insert3() {

        /*
         * insert3()有 @Transactional 标记,insertDo 没有@Transactional 标记,内部调用 insertDo(),
         * 当 insert3() 抛异常,insertDo() 依然会回滚.
         * 但是,该种指定传播机制为 NEVER ,是不会回滚的,说明生效的事务是 insert3(),内部调用 insetDo() 无论什么样都不会被动态代理.
         *
         * 另外一种情况:
         *  insert3()没有@Transactional 标记,insertDo 有@Transactional 标记,内部调用 insertDo(),当 insert3() 抛异常, 不会被回滚
         *  这时候 insertDo() 是不会被动态代理的,常说的内部方法调用事务不会生效是这种方式.
         */


        // 内部方法调用
        insertDo();

        throw new RuntimeException("");
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

    // 内部方法调用: @Transactional(propagation = Propagation.NEVER)
    public void insertDo() {
        UserDO userDo = UserDO.builder()
                .age(18)
                .name(LocalDateTime.now().toString())
                .email("www.baidu.com")
                .build();

        userMapper.insert(userDo);

        // throw new RuntimeException("");
    }

    private void throwException() {
        throw new RuntimeException("");
    }
}
