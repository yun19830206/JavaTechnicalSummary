package com.cloud.aiassistant.formdesign.service;

import com.cloud.aiassistant.core.utils.SessionUserUtils;
import com.cloud.aiassistant.enums.formdesign.TableColumnTypeEnum;
import com.cloud.aiassistant.formdata.dao.FormDataMapper;
import com.cloud.aiassistant.formdata.pojo.FormDataQueryDTO;
import com.cloud.aiassistant.formdesign.dao.*;
import com.cloud.aiassistant.formdesign.pojo.FormDesignVO;
import com.cloud.aiassistant.pojo.formdesign.*;
import com.cloud.aiassistant.utils.FormDesignUtils;
import com.cloud.aiassistant.pojo.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

    @Autowired
    private FormDataMapper forDataDao ;

    /** 缓存所有表单的配置数据VO，此类数据接口调用很多，但是修改很少，特别适合缓存。 注意配置修改的时候要remove */
    private Map<Long,FormDesignVO> tableDesignVoCacheNMap = new ConcurrentHashMap<>();

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
        //1:获得表单配置数据
        FormDesignVO formDesignVO = this.getFormDesignConfigWithoutExtendData(formid);
        //2:获得表单外键引用资源的所有数据，以便录入数据 和 查询条件方便使用
        if(null != formDesignVO && null != formDesignVO.getTableColumnConfigList() && formDesignVO.getTableColumnConfigList().size()>0){
            Map<String,List<SimpleTableData>> foreignKeyValues = this.getTableDesignForerignKeyValues(formDesignVO.getTableColumnConfigList());
            formDesignVO.setForeignKeyValues(foreignKeyValues);
        }

        return formDesignVO ;

    }

    /** 根据表单设计ID，获得表单配置详细VO,先从缓存获取，获得不到再从数据库获得。
     *  适合仅需要配置数据，不需要父级资源明细的配置
     */
    public FormDesignVO getFormDesignConfigWithoutExtendData(Long formid) {

        FormDesignVO formDesignVO = tableDesignVoCacheNMap.get(formid);
        if(null == formDesignVO){
            //获得表单配置数据，表单字段配置数据，表单查询条件数据，表单展示结果数据，进行拼接成FormDesignVO
            TableConfig tableConfig = tableConfigDao.selectByPrimaryKey(formid);
            List<TableColumnConfig> tableColumnConfigList = tableColumnConfigDao.selectByFormId(formid);
            //默认在字段配置数据中，增加一个创建人配置数据
            tableColumnConfigList.add(createDefaultCreateUserConlmnConfig(tableConfig));
            List<TableQueryConfig> tableQueryConfigList = tableQueryConfigDao.selectByFormId(formid);
            List<TableDisplayConfig> tableDisplayConfigs = tableDisplayConfigDao.selectByFormId(formid);
            //默认在展示字段中，将创建人展示出来
            tableDisplayConfigs.add(0,createDefaultCreateUserDisplayColumn(tableColumnConfigList.get(tableColumnConfigList.size()-1)));
            formDesignVO = FormDesignUtils.transTableDesignConfigToFormDesignVO(tableConfig, tableColumnConfigList, tableQueryConfigList, tableDisplayConfigs);
            tableDesignVoCacheNMap.put(formid,formDesignVO);
        }
        return formDesignVO ;
    }


    /** 根据本表单的字段配置信息，获得本表单所有外键引用的值List */
    private Map<String,List<SimpleTableData>> getTableDesignForerignKeyValues(List<TableColumnConfig> tableColumnConfigList) {
        //1：遍历每个字段的配置，找到外键引用的字段名 和 引用表的ID  Map<ColumnName,tableDesignId>
        Map<String,Long> foreignColumnTableIdMap = new HashMap<>();
        tableColumnConfigList.forEach(tableColumnConfig -> {
            if(TableColumnTypeEnum.COLUMN_FOREIGN_KEY == tableColumnConfig.getColType()){
                foreignColumnTableIdMap.put(tableColumnConfig.getEnglishName(),tableColumnConfig.getFkValue());
            }
        });
        if(foreignColumnTableIdMap.size()<1){ return null ;}

        //2：根据tableDesignId,查询到此tableDesignId的displayColumnName,再找到对应<数据ID,展示字段ID>的List
        Map<Long,List<SimpleTableData>> tableSimpleTableDataMap = new HashMap<>();
        foreignColumnTableIdMap.forEach((columnName,tableDesignId) -> {
            //获得外键表的展示字段
            List<TableColumnConfig> parenetTableColumnConfigList =  getFormDesignConfigWithoutExtendData(tableDesignId).getTableColumnConfigList();
            String parentDisplayColumnName = FormDesignUtils.getDisplayColumnNameFromColumnConfigList(parenetTableColumnConfigList);
            //获得外键表的全部数据
            FormDesignVO parentFormDesignVO = getFormDesignConfigWithoutExtendData(tableDesignId);
            FormDataQueryDTO parentFormDataQueryDTO = new FormDataQueryDTO();
            parentFormDataQueryDTO.setTableId(tableDesignId);
            parentFormDataQueryDTO.setTableName(parentFormDesignVO.getTableConfig().getEnglishName());
            User user = SessionUserUtils.getUserFromSession(session);
            parentFormDataQueryDTO.setUserId(user.getId());
            List<Map<String, Object>> columnValueList = forDataDao.selectMyFormDataAndAuthToMeData(parentFormDataQueryDTO);
            //根据展示字段 与 外键表的全部数据， 获得简要数据格式
            List<SimpleTableData> tableSimpleTableDataList = FormDesignUtils.getTableSimpleDataList(columnValueList,parentDisplayColumnName);
            tableSimpleTableDataMap.put(tableDesignId,tableSimpleTableDataList);
        });

        //3: 组装1和2，形成结果数据
        Map<String,List<SimpleTableData>> foreignColumnValueMap = new HashMap<>();
        foreignColumnTableIdMap.forEach((columnName,tableDesignId) -> {
            foreignColumnValueMap.put(columnName,tableSimpleTableDataMap.get(tableDesignId));
        });

        return foreignColumnValueMap ;
    }

    /**
     * 获得所有表单菜单数据：我创建的和赋权给我的(第二组菜单 表单数据)
     * @return Set<TableConfig>
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
     * @return void
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
     * @return List<TableDesignAuth>
     */
    public List<TableDesignAuth> listAuthDisignTable(Long tableId) {
        if(null == tableId || tableId < 0){
            return new ArrayList<>();
        }

        return tableDesignAuthDao.selectByTableId(tableId);
    }

    /** 刷新服务器缓存中表单配置数据，以便于前端没做表单设计页面，直接在数据库修改配置情况下 缓存数据过旧的情况 */
    public void reflashFormdesignCache() {
        tableDesignVoCacheNMap = new ConcurrentHashMap<>();
    }

    /** 给tableConfig 创建一个默认的创建人熟悉配置 */
    private TableColumnConfig createDefaultCreateUserConlmnConfig(TableConfig tableConfig) {
        TableColumnConfig createUserNameColumnConfig = new TableColumnConfig();
        createUserNameColumnConfig.setChineseName("创建人");
        createUserNameColumnConfig.setColLength(32);
        createUserNameColumnConfig.setColSeq(99);
        createUserNameColumnConfig.setColType(TableColumnTypeEnum.COLUMN_SIGN_LINE_TEXT);
        createUserNameColumnConfig.setDisplayColumn(0);
        createUserNameColumnConfig.setEmpty(1);
        createUserNameColumnConfig.setEnglishName("create_user_name");
        createUserNameColumnConfig.setTableId(tableConfig.getId());
        createUserNameColumnConfig.setUniqued(0);
        createUserNameColumnConfig.setTenantId(tableConfig.getTenantId());
        createUserNameColumnConfig.setId(99L);
        return createUserNameColumnConfig ;
    }

    /** 创建默认创建人展示字段 */
    private TableDisplayConfig createDefaultCreateUserDisplayColumn(TableColumnConfig tableColumnConfig) {
        TableDisplayConfig tableDisplayConfig = new TableDisplayConfig();
        tableDisplayConfig.setTableColumnConfig(tableColumnConfig);
        tableDisplayConfig.setTableColumn(tableColumnConfig.getId());
        tableDisplayConfig.setDisplaySeq(0);
        tableDisplayConfig.setId(99L);
        tableDisplayConfig.setTableId(tableColumnConfig.getTableId());
        tableDisplayConfig.setTenantId(tableColumnConfig.getTenantId());

        return tableDisplayConfig;
    }
}


