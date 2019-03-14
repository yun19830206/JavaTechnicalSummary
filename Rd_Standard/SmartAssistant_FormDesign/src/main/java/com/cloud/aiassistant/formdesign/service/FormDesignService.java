package com.cloud.aiassistant.formdesign.service;

import com.cloud.aiassistant.formdesign.dao.TableColumnConfigMapper;
import com.cloud.aiassistant.formdesign.dao.TableConfigMapper;
import com.cloud.aiassistant.formdesign.dao.TableDisplayConfigMapper;
import com.cloud.aiassistant.formdesign.dao.TableQueryConfigMapper;
import com.cloud.aiassistant.formdesign.pojo.FormDesignVO;
import com.cloud.aiassistant.formdesign.utils.FormDesignUtils;
import com.cloud.aiassistant.pojo.formdesign.TableColumnConfig;
import com.cloud.aiassistant.pojo.formdesign.TableConfig;
import com.cloud.aiassistant.pojo.formdesign.TableDisplayConfig;
import com.cloud.aiassistant.pojo.formdesign.TableQueryConfig;
import com.cloud.aiassistant.pojo.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 表单设计器(表单配置、字段配置、查询条件配置、展示结果配置) Service
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
    private HttpSession session;

    /** 获得我创建的表单配置数据 */
    public List<TableConfig> getMyCreateFormDesign() {
        User user=(User) session.getAttribute(User.SESSION_KEY_USER);
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
        User user=(User) session.getAttribute(User.SESSION_KEY_USER);
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


}
