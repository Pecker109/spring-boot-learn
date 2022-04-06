package com.mybatis.plus.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProductName: Hundsun HEP
 * @Package: com.mybatis.plus.example.model
 * @Description：note
 * @Author： wanghf
 * @CreateDate： 2021/12/17 10:03 上午
 * @UpdateUser： wanghf
 * @Date： 2021/12/17 10:03 上午
 * @UpdateRemark：The modified content
 * @Version 1.0
 * <p>
 * Copyright © 2021 Hundsun Technologies Inc. All Rights Reserved
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("node")
public class NodeDO {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     *
     */
    private String name;
}
