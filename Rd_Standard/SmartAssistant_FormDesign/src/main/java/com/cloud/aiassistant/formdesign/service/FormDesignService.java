package com.cloud.aiassistant.formdesign.service;

import com.cloud.aiassistant.core.utils.SessionUserUtils;
import com.cloud.aiassistant.formdesign.dao.*;
import com.cloud.aiassistant.formdesign.pojo.FormDesignVO;
import com.cloud.aiassistant.pojo.common.AjaxResponse;
import com.cloud.aiassistant.pojo.common.CommonSuccessOrFail;
import com.cloud.aiassistant.pojo.formdesign.*;
import com.cloud.aiassistant.utils.FormDesignUtils;
import com.cloud.aiassistant.pojo.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 表单设计器(表单配置、字段配置、查询条件配置、展示结果配置) Service
 * 注意：创建表单的时候：每个表单默认增加 create_user、create_time、tenant_id3个属性，提供内部私有方法获得
 * @author ChengYun
 * @date 2019/3/10  Vesion 1.0
 */
@Service
@Slf4j
@Transactional
public class FormDesignService {

    @Autowired
    private TableConfigMapper tableConfigDao ;

    @Autowired
    private TableColumnConfigMapper tableColumnConfigDao ;

    @Autowired
    private TableQueryConfigMapper tableQueryConfigDao ;

    @Autowired
    private TableDisplayConfigMapper tableDisplayConfigDao ;

    @Autowired
    private TableDesignAuthMapper tableDesignAuthDao ;

    @Autowired
    private HttpSession session;

    /** 获得我创建的表单配置数据 */
    public List<TableConfig> getMyCreateFormDesign() {
        User user = SessionUserUtils.getUserFromSession(session);
        List<TableConfig> myCreateTableConfigList = tableConfigDao.selectByUserId(user.getId());
        return myCreateTableConfigList;
    }

    /**
     * 获得设计表单主键ID，获得表单配置的详细信息
     * @return AjaxResponse
     */
    public FormDesignVO getFormDesignVOById(Long formid) {
        //获得表单配置数据，表单字段配置数据，表单查询条件数据，表单展示结果数据，进行拼接成FormDesignVO
        TableConfig tableConfig = tableConfigDao.selectByPrimaryKey(formid);

        List<TableColumnConfig> tableColumnConfigList = tableColumnConfigDao.selectByFormId(formid);

        List<TableQueryConfig> tableQueryConfigList = tableQueryConfigDao.selectByFormId(formid);

        List<TableDisplayConfig> tableDisplayConfigs = tableDisplayConfigDao.selectByFormId(formid);

        FormDesignVO formDesignVO = FormDesignUtils.transTableDesignConfigToFormDesignVO(tableConfig, tableColumnConfigList, tableQueryConfigList, tableDisplayConfigs);

        return formDesignVO ;

    }

    /**
     * 获得所有表单菜单数据：我创建的和赋权给我的(第二组菜单 表单数据)
     * @return
     */
    public Set<TableConfig> getMyFormDesign() {
        Set<TableConfig> myFormDesign = new HashSet<>();

        //获得我创建的,加入到总体结果当中
        User user = SessionUserUtils.getUserFromSession(session);
        List<TableConfig> myCreateTableConfigList = tableConfigDao.selectByUserId(user.getId());
        if (null != myCreateTableConfigList && myCreateTableConfigList.size() > 0){
            myCreateTableConfigList.forEach(tableConfig -> myFormDesign.add(tableConfig));
        }

        //获得赋权给我的
        List<TableConfig> autoToMeTalbeConfigList = tableConfigDao.selectAuthToMeTableConfig(user.getId());
        if (null != autoToMeTalbeConfigList && autoToMeTalbeConfigList.size() > 0){
            autoToMeTalbeConfigList.forEach(tableConfig -> myFormDesign.add(tableConfig));
        }

        return myFormDesign;
    }


    /**
     * [赋权功能--表单配置]将我创建的表单配置，付给其他人能管理数据
     * @param tableDesignAuthList
     * @return
     */
    public void authDesignTable(List<TableDesignAuth> tableDesignAuthList) {
        if(null == tableDesignAuthList || tableDesignAuthList.size()<1){
            return;
        }

        //1：TODO 权限判断，只有创建此表单配置的人才可以授权(表的创建者 与 当前用户一致)

        User user = SessionUserUtils.getUserFromSession(session);
        //2：填入必要数据，以便于数据保存完成
        tableDesignAuthList.forEach(tableDesignAuth -> {
            tableDesignAuth.setCreateUser(user.getId());
            tableDesignAuth.setTenantId(user.getTenantId());
        });

        //3：数据入库之前，先删除以前授权数据
        tableDesignAuthDao.deleteByTableId(tableDesignAuthList.get(0).getTableId());

        //4：授权数据入库
        tableDesignAuthDao.insertBatch(tableDesignAuthList);

    }

    /**
     * 获得当前表单配置，已经赋权的人员信息
     * @param tableId 当前表单配置ID
     * @return
     */
    public List<TableDesignAuth> listAuthDisignTable(Long tableId) {
        if(null == tableId || tableId < 0){
            return new ArrayList<>();
        }

        return tableDesignAuthDao.selectByTableId(tableId);
    }
}


