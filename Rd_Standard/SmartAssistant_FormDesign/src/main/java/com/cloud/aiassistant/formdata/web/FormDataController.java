package com.cloud.aiassistant.formdata.web;

import com.cloud.aiassistant.formdata.pojo.FormDataJudgeDuplicateQueryDTO;
import com.cloud.aiassistant.formdata.pojo.FormDataQueryDTO;
import com.cloud.aiassistant.formdata.pojo.FormRowDataDTO;
import com.cloud.aiassistant.formdata.service.FormDataService;
import com.cloud.aiassistant.pojo.common.AjaxResponse;
import com.cloud.aiassistant.pojo.common.CommonSuccessOrFail;
import com.cloud.aiassistant.pojo.common.PageParam;
import com.cloud.aiassistant.pojo.common.PageResult;
import com.cloud.aiassistant.pojo.formdata.TableDataAuth;
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

    /** [赋权功能--数据权限]将我创建的表的数据，付给别人：可看、可改 */
    @RequestMapping("/auth/tabledata")
    public AjaxResponse authTableData(@RequestBody List<TableDataAuth> tableDataAuthList){
        if(null == tableDataAuthList || tableDataAuthList.size()<1){
            return AjaxResponse.failed(null,"授权数据为空");
        }

        try{
            formDataService.authTableData(tableDataAuthList);
        }catch (Exception e){
            log.error("表单数据授权失败："+e.getMessage());
            e.printStackTrace();
            return AjaxResponse.failed("授权失败");
        }
        return AjaxResponse.success(null,"授权成功");
    }


    /** [已赋权数据--表单数据]获得当前表单数据，将我表单数据已经赋权给其他人员的信息 */
    @RequestMapping("/list/authtabledata")
    public AjaxResponse ListAuthTableData(Long tableId){
        if(null == tableId || tableId < 0){
            return AjaxResponse.failed("表单ID为空");
        }

        List<TableDataAuth> tableDataAuthList = formDataService.listAuthTableData(tableId);
        return AjaxResponse.success(tableDataAuthList);
    }

}

