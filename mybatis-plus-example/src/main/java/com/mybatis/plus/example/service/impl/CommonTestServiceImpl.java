package com.mybatis.plus.example.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatis.plus.example.service.CommonTestService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

/**
 * @author Pecker
 * @Description JDBCTemplate 测试
 * @since 2021-12-22
 */
@Service
public class CommonTestServiceImpl implements CommonTestService {


    @SneakyThrows
    @Override
    public String test1(String flag) {
        if (StrUtil.equals(flag, "sleep")) {
            Thread.sleep(5000);
        }
        return flag;
    }
}
