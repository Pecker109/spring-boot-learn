package com.mybatis.plus.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mybatis.plus.example.model.UserDO;
import org.springframework.stereotype.Repository;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-11-24
 */
@Repository
public interface UserMapper extends BaseMapper<UserDO> {
}
