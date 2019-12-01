package com.cloud.stagging.module.user.pojo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * CRM客户信息：客户、联系人、项目
 * @author ChengYun
 * @date 2019/3/24  Vesion 1.0
 */
@Data
public class CrmCustomerVO {

    /** CRM客户信息：一个Map<属性名,属性值>代表一个属性和值，List<Map>代表完整一个CRM客户对象。以此规避属性顺序不对的情况 */
    private List<Map<String,Object>> customer;

    /** 项目信息：一个Map<属性名,属性值>代表一个属性和值，List<Map>代表完整的一个CRM客户对象。List<List>代表一个CRM客户会存在多个项目的情况 */
    private List<List<Map<String,Object>>> projectList;

    /** 联系人信息：一个Map<属性名,属性值>代表一个属性和值,List<Map>代表完整的一个联系人信息。List<List>代表一个CRM客户会存在多个联系人的情况 */
    private List<List<Map<String,Object>>> contactPeopleList;
}
