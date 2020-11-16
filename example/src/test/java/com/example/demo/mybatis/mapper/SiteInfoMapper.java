package com.example.demo.mybatis.mapper;

import com.example.demo.mybatis.model.SiteInfo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SiteInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SiteInfo record);

    int insertSelective(SiteInfo record);

    SiteInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SiteInfo record);

    int updateByPrimaryKey(SiteInfo record);

    int updateBatch(List<SiteInfo> list);

    int updateBatchSelective(List<SiteInfo> list);

    int batchInsert(@Param("list") List<SiteInfo> list);
}