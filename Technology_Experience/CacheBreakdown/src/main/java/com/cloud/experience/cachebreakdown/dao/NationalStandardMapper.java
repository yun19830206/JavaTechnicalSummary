package com.cloud.experience.cachebreakdown.dao;

import com.cloud.experience.cachebreakdown.pojo.BookCategrouyCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 国标文档 目标、内容、说明、解读 数据库操作DAO
 * @author Cloud
 */
@Mapper
public interface NationalStandardMapper {

    /** 批量插入国标 目标、内容、说明 */
    List<BookCategrouyCount> selectBookCategrouyCountList();
}
