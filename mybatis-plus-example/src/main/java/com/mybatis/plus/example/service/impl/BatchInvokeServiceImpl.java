package com.mybatis.plus.example.service.impl;

import com.mybatis.plus.example.mapper.UserMapper;
import com.mybatis.plus.example.model.UserDO;
import com.mybatis.plus.example.service.BatchInvokeService;
import com.mybatis.plus.example.util.MybatisBatchUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pecker
 * @Description
 * @since 2024-04-28
 */
@Slf4j
@Service
public class BatchInvokeServiceImpl implements BatchInvokeService {

    @Autowired
    MybatisBatchUtils batchUtils;

    @Autowired
    UserMapper userMapper;

    @Override
    public Integer batch1() {
        UserDO user1 = UserDO.builder()
                .age(18)
                .name(LocalDateTime.now().toString())
                .email("www.baidu.com1")
                .build();

        UserDO user2 = UserDO.builder()
                .age(19)
                .name(LocalDateTime.now().toString())
                .email("www.baidu.com2")
                .build();

        List<UserDO> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);

        int batched = batchUtils.batchUpdateOrInsert(list, UserMapper.class, (item, mapper) -> mapper.insert(item));
        log.info("批量插入{}条", batched);
        return batched;
    }

    // todo 1.MP#saveBatch()
    // 2.查看rewriteBatchedStatements=true 下 sql
}
