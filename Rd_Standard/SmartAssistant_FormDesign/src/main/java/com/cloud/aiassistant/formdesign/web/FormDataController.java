package com.cloud.aiassistant.formdesign.web;

import com.cloud.aiassistant.formdesign.pojo.FormDataJudgeDuplicateQueryDTO;
import com.cloud.aiassistant.formdesign.pojo.FormDataQueryDTO;
import com.cloud.aiassistant.formdesign.pojo.FormRowDataDTO;
import com.cloud.aiassistant.formdesign.service.FormDataService;
import com.cloud.aiassistant.pojo.common.AjaxResponse;
import com.cloud.aiassistant.pojo.common.CommonSuccessOrFail;
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
    public AjaxResponse<PageResult<Map<String,Object>>> getFormData(@RequestBody PageParam<FormDataQueryDTO> formDataPageParam){
        PageResult formDataPageResult = formDataService.getFormDataByFormId(formDataPageParam);
        return AjaxResponse.success(formDataPageResult);
    }

    /** 8：[移动PC公用]根据表单设计主键ID，与数据主键ID的List,  获得明细数据。 */
    @RequestMapping("/get/formdatabyid")
    public AjaxResponse getFormDataByTableIdAndDataIdList(@RequestBody FormDataQueryDTO formDataQueryDTO){
        List<Map<String, Object>> dataList = formDataService.getFormDataByTableIdAndDataIdList(formDataQueryDTO);
        return AjaxResponse.success(dataList);
    }

    /** 9：[移动PC公用] 根据FormDataJudgeDuplicateQueryDTO(表单ID，表名，字段名，值),判断数据是否重复 */
    @RequestMapping("/judge/formdata/duplicate")
    public AjaxResponse judgeFormColumnDataIsDuplicate(@RequestBody FormDataJudgeDuplicateQueryDTO formDataJudgeDuplicateQueryDTO){
        //重复返回True，不重复返回False
        Boolean isDuplicate = formDataService.judgeFormColumnDataIsDuplicate(formDataJudgeDuplicateQueryDTO);
        if(Boolean.TRUE.equals(isDuplicate)){
            return AjaxResponse.failed(null,"已经存在["+formDataJudgeDuplicateQueryDTO.getColumnValue()+"]数据");
        }else{
            return AjaxResponse.success();
        }
    }

    /** 10：[移动PC公用]录入一个表单数据。 */
    @RequestMapping("/add/formrowdata")
    public AjaxResponse addFormOneRowData(@RequestBody FormRowDataDTO formRowDataDTO){
        CommonSuccessOrFail commonSuccessOrFail = formDataService.addFormOneRowData(formRowDataDTO);
        if(CommonSuccessOrFail.CODE_ERROR == commonSuccessOrFail.getResultCode()){
            return AjaxResponse.failed(null,commonSuccessOrFail.getResultDesc());
        }else{
            return AjaxResponse.success(null,commonSuccessOrFail.getResultDesc());
        }

    }

}

