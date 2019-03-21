package com.cloud.aiassistant.formdesign.web;

import com.cloud.aiassistant.formdesign.pojo.FormDesignVO;
import com.cloud.aiassistant.formdesign.service.FormDesignService;
import com.cloud.aiassistant.pojo.common.AjaxResponse;
import com.cloud.aiassistant.pojo.common.CommonSuccessOrFail;
import com.cloud.aiassistant.pojo.formdesign.TableConfig;
import com.cloud.aiassistant.pojo.formdesign.TableDesignAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 表单设计器(表单配置、字段配置、查询条件配置、展示结果配置)Controller
 * @author ChengYun
 * @date 2019/3/10  Vesion 1.0
 */
@RestController
@Slf4j
@RequestMapping("/aiassistant/formdesign")
public class FormDesignController {

    @Autowired
    private FormDesignService formDesignService ;

    /**
     * 获得我创建的表单配置数据
     * @return AjaxResponse
     */
    @RequestMapping("/get/mycreate")
    public AjaxResponse getMyCreateFormDesign(){
        List<TableConfig> myCreateTableConfiglist = formDesignService.getMyCreateFormDesign();
        return AjaxResponse.success(myCreateTableConfiglist);
    }

    /**
     * 获得设计表单主键ID，获得表单配置的详细信息
     * @return AjaxResponse
     */
    @RequestMapping("/get/formdesigndetail")
    public AjaxResponse getFormDesignVOById(Long formid){
        FormDesignVO formDesignVO = formDesignService.getFormDesignVOById(formid);
        return AjaxResponse.success(formDesignVO);
    }

    /** [管理端]获得所有表单菜单数据：我创建的和赋权给我的(第二组菜单 表单数据) */
    @RequestMapping("/get/myformdesign")
    public AjaxResponse getMyFormDesign(){
        Set<TableConfig> tableConfigSet = formDesignService.getMyFormDesign();
        return AjaxResponse.success(tableConfigSet);
    }

    /** [赋权功能--表单配置]将我创建的表单配置，付给其他人能管理数据 */
    @RequestMapping("/auth/designtable")
    public AjaxResponse authDesignTable(@RequestBody List<TableDesignAuth> tableDesignAuthList){
        if(null == tableDesignAuthList || tableDesignAuthList.size()<1){
            return AjaxResponse.failed(null,"授权数据为空");
        }
        try{
            formDesignService.authDesignTable(tableDesignAuthList);
        }catch (Exception e){
            log.error("表单配置授权失败："+e.getMessage());
            e.printStackTrace();
            return AjaxResponse.failed("授权失败");
        }
        return AjaxResponse.success(null,"授权成功");
    }

    /** [已赋权数据--表单配置]获得当前表单配置，已经赋权的人员信息 */
    @RequestMapping("/list/authdesigntable")
    public AjaxResponse listAuthDesignTable(Long tableId){
        if(null == tableId || tableId < 0){
            return AjaxResponse.failed("表单ID为空");
        }

        List<TableDesignAuth> tableDesignAuthList = formDesignService.listAuthDisignTable(tableId);
        return AjaxResponse.success(tableDesignAuthList);
    }


}
