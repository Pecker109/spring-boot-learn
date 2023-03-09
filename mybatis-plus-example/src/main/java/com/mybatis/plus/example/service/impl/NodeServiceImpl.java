package com.mybatis.plus.example.service.impl;

import com.mybatis.plus.example.mapper.NodeMapper;
import com.mybatis.plus.example.service.NodeService;
import com.mybatis.plus.example.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Pecker
 * @Description 事务测试
 * @since 2021-12-17
 */
@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    NodeMapper nodeMapper;

    @Override
    // @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void insert() {
        nodeMapper.insert(EntityUtil.newNodeDO());

        //throw new RuntimeException();
    }
}
