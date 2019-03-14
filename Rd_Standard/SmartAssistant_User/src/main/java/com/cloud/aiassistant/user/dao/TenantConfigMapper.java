package com.cloud.aiassistant.user.dao;


import com.cloud.aiassistant.pojo.tenant.TenantConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TenantConfigMapper {

    TenantConfig selectByPrimaryKey(Long id);

    /** 根据租户唯一标识获得租户配置信息 */
    TenantConfig selectByTenantNum(String tenantNum);

}