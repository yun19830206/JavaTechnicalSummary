package com.cloud.aiassistant.business.crm.web;

import com.cloud.aiassistant.business.crm.pojo.CrmCustomerVO;
import com.cloud.aiassistant.business.crm.service.CrmBusinessService;
import com.cloud.aiassistant.pojo.common.AjaxResponse;
import com.cloud.aiassistant.pojo.common.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CRM业务数据Controller
 * @author ChengYun
 * @date 2019/3/24  Vesion 1.0
 */
@RestController
@Slf4j
@RequestMapping("aiassistant/business/")
public class CrmBusinessController {

    @Autowired
    private CrmBusinessService crmBusinessService ;

    /** 业务-1：获得我的客户信息：客户、联系人、项目  */
    @RequestMapping("/crm/get/customer")
    public AjaxResponse<CrmCustomerVO> getCrmCustomerVO(Long customerId){
        if(null == customerId || customerId < 1){
            return AjaxResponse.failed(null,"缺少参数");
        }
        CrmCustomerVO crmCustomerVO = crmBusinessService.getCrmCustomerVO(customerId);
        return AjaxResponse.success(crmCustomerVO);
    }
}
