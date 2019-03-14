package com.cloud.aiassistant.formdesign.web;

import com.cloud.aiassistant.formdesign.pojo.FormDataQueryDTO;
import com.cloud.aiassistant.formdesign.service.FormDataService;
import com.cloud.aiassistant.pojo.common.AjaxResponse;
import com.cloud.aiassistant.pojo.common.PageParam;
import com.cloud.aiassistant.pojo.common.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 表单数据 查询、新增 Controller
 * @author ChengYun
 * @date 2019/3/14  Vesion 1.0
 */
@RestController
@Slf4j
@RequestMapping("/aiassistant/formdata")
public class FormDataController {

    @Autowired
    private FormDataService formDataService ;

    /** 7：[管理端]获得我能查看的表单数据(权限见赋权功能):我创建的 和 赋权给我的:  带分页(配置查询条件暂时不做)   */
    @RequestMapping("/get/myformpagedata")
    public AjaxResponse<PageResult<List<Map<String,Object>>>> getFormData(@RequestBody PageParam<FormDataQueryDTO> formDataPageParam){
        PageResult formDataPageResult = formDataService.getFormDataByFormId(formDataPageParam);
        return AjaxResponse.success(formDataPageResult);
    }

    /** 8：[移动PC公用]根据表单设计主键ID，与数据主键ID的List,  获得明细数据。 */
    @RequestMapping("/get/formdatabyid")
    public AjaxResponse getFormDataByTableIdAndDataIdList(){
        // formDataService.getFormDataByTableIdAndDataIdList();
        return AjaxResponse.success("");
    }


}

