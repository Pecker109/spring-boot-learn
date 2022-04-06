package com.mybatis.plus.example.service.impl;

import com.mybatis.plus.example.mapper.UserMapper;
import com.mybatis.plus.example.service.UserService;
import com.mybatis.plus.example.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Pecker
 * @Description 事务测试
 * @since 2021-12-17
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insert() {
        userMapper.insert(EntityUtil.newUserDO());
    }


}
