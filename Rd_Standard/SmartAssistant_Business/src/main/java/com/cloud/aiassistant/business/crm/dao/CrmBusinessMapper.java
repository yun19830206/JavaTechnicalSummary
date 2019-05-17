package com.cloud.aiassistant.business.crm.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CRM业务层Dao
 * @author ChengYun
 * @date 2019/3/24  Vesion 1.0
 */
@Mapper
public interface CrmBusinessMapper {


    /** 根据crm客户表数据主键ID，项目表存储表名称，项目所属客户外键字段ID，获得项目表的ID List */
    List<Long> selectProjectIdListByCustomerId(@Param("crmProjectTableName")String crmProjectTableName,
                                               @Param("customerId") Long customerId,
                                               @Param("forergnKey") String forergnKey);

    /** 根据crm客户表数据主键ID，联系人存储表名称，联系人所属客户外键字段ID，获得联系人表的的ID List */
    List<Long> selectContactIdListByCustomerId(@Param("crmContactTableName") String crmContactTableName,
                                               @Param("customerId") Long customerId,
                                               @Param("contactCustomerFkName") String contactCustomerFkName);

    /** 根据拜访记录信息，更新CRM客户的更新时间 */
    void updateCrmCustomerUpdateTime();

    /** 转移拜访记录数据 */
    void transVisitorToUser(@Param("customerId")Long customerId, @Param("toUserId")Long toUserId);

    /** 转移联系人数据 */
    void transConnectorToUser(@Param("customerId")Long customerId, @Param("toUserId")Long toUserId);

    /** 转移项目数据 */
    void transProjectToUser(@Param("customerId")Long customerId, @Param("toUserId")Long toUserId);

    /** 转移客户数据 */
    void transCustomerToUser(@Param("customerId")Long customerId, @Param("toUserId")Long toUserId);
}
