package com.cloud.aiassistant.business.crm.web;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cloud.aiassistant.business.crm.pojo.CrmCustSheet;
import com.cloud.aiassistant.business.crm.pojo.CrmCustomerVO;
import com.cloud.aiassistant.business.crm.service.CrmBusinessService;
import com.cloud.aiassistant.business.crm.service.CrmPushMessageService;
import com.cloud.aiassistant.formdata.pojo.FormDataQueryDTO;
import com.cloud.aiassistant.pojo.common.AjaxResponse;
import com.cloud.aiassistant.pojo.common.PageParam;
import com.cloud.aiassistant.pojo.common.PageResult;
import com.cloud.aiassistant.pojo.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * CRM业务数据Controller
 * @author ChengYun
 * @date 2019/3/24  Vesion 1.0
 */
@RestController
@Slf4j
@RequestMapping("/aiassistant/business/")
public class CrmBusinessController {

    @Autowired
    private CrmBusinessService crmBusinessService ;

    @Autowired
    private CrmPushMessageService crmPushMessageService ;

    /** 业务-1：获得我的客户信息：客户、联系人、项目  */
    @RequestMapping("/crm/get/customer")
    public AjaxResponse<CrmCustomerVO> getCrmCustomerVO(Long customerId){
        if(null == customerId || customerId < 1){
            return AjaxResponse.failed(null,"缺少参数");
        }
        CrmCustomerVO crmCustomerVO = crmBusinessService.getCrmCustomerVO(customerId);
        return AjaxResponse.success(crmCustomerVO);
    }

    /** 根据拜访记录信息，更新CRM客户的更新时间 */
    @RequestMapping("/crm/updatecustomer")
    public AjaxResponse<CrmCustomerVO> updateCrmCustomerUpdateTime(){
        crmBusinessService.updateCrmCustomerUpdateTime();
        return AjaxResponse.success();
    }

    /** 给某个访客、推送一条消息 */
    @RequestMapping("/crm/pushmessage/wx/one")
    public AjaxResponse pushWXMessage(String openId, String message){
        if(null == openId || null == message || message.length()<1){
            return AjaxResponse.failed(null,"推送消息访客或者推送消息内容为空，不能完成推送消息操作。");
        }
        return AjaxResponse.success(crmPushMessageService.pushWXMessage(openId,message));
    }

    /** 给某个访客、推送一条消息 */
    @RequestMapping("/crm/pushmessage/wx/all")
    public AjaxResponse pushAllSalesAddVisitInfo(){
        crmPushMessageService.pushAllSalesAddVisitInfo();
        return AjaxResponse.success();
    }

    /** CRM一个客户转交给其他人 */
    @RequestMapping("/crm/trans")
    public AjaxResponse transCustomerToUser(Long customerId, Long toUserId){
        if(null == customerId || customerId < 0 || null == toUserId || toUserId < 0 ){
            return AjaxResponse.failed("核心参数缺失，请联系管理员");
        }
        crmBusinessService.transCustomerToUser(customerId,toUserId);
        return AjaxResponse.success(null,"转交成功");
    }

    /** 获得所有CRM销售人员。 */
    @RequestMapping("/crm/user/list")
    public AjaxResponse ListCrmUserList(){
        List<User> crmUserList = crmBusinessService.ListCrmUserList();
        return AjaxResponse.success(crmUserList,"获得CRM用户信息成功");
    }

    /** 获得项目下面的拜访记录信息。 */
    @RequestMapping("/crm/customer/visits")
    public AjaxResponse ListProjectVisitList(Long projectId){
        if(null == projectId || projectId.intValue() < 1){
            return AjaxResponse.failed(null,"参数不合法");
        }
        List<List<Map<String,Object>>> visitList = crmBusinessService.ListProjectVisitList(projectId);
        return AjaxResponse.success(visitList,"获得CRM用户信息成功");
    }

    /** 导出满足条件的CRM客户数据到Excel当中  */
    @RequestMapping("/crm/customer/export")
    public void exportCrmCustomer(@RequestBody FormDataQueryDTO customerQueryDto, HttpServletResponse response) throws IOException {
        List<CrmCustSheet> crmSheetList = crmBusinessService.exportCrmCustomer(customerQueryDto);

        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/x-excel;charset=utf-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("CrmDataExport.xlsx", "utf-8"));

        ExcelWriter writer = EasyExcelFactory.getWriter(out) ;
        crmSheetList.forEach(crmCustSheet -> {
            writer.write1(crmCustSheet.getSheetData(), crmCustSheet.getSheetHead());
        });
        writer.finish();

        out.flush();
    }

}
