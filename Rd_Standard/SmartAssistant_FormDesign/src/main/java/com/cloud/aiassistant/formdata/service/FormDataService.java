package com.cloud.aiassistant.formdata.service;

import com.cloud.aiassistant.core.utils.PageHelperUtils;
import com.cloud.aiassistant.core.utils.SessionUserUtils;
import com.cloud.aiassistant.enums.formdesign.TableColumnTypeEnum;
import com.cloud.aiassistant.file.dao.FileMapper;
import com.cloud.aiassistant.formdata.dao.FormDataMapper;
import com.cloud.aiassistant.formdata.dao.TableDataAuthMapper;
import com.cloud.aiassistant.formdata.pojo.FormDataJudgeDuplicateQueryDTO;
import com.cloud.aiassistant.formdata.pojo.FormDataQueryDTO;
import com.cloud.aiassistant.formdata.pojo.FormRowDataDTO;
import com.cloud.aiassistant.formdata.pojo.OneColumnValue;
import com.cloud.aiassistant.formdesign.pojo.*;
import com.cloud.aiassistant.formdesign.service.FormDesignService;
import com.cloud.aiassistant.pojo.common.CommonSuccessOrFail;
import com.cloud.aiassistant.pojo.common.PageParam;
import com.cloud.aiassistant.pojo.common.PageResult;
import com.cloud.aiassistant.pojo.file.PublicFile;
import com.cloud.aiassistant.pojo.formdata.TableDataAuth;
import com.cloud.aiassistant.pojo.formdesign.TableColumnConfig;
import com.cloud.aiassistant.pojo.user.User;
import com.cloud.aiassistant.user.service.UserService;
import com.cloud.aiassistant.utils.FormDesignUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

/**
 * 表单数据 (增、删、改、差)Service
 * @author ChengYun
 * @date 2019/3/10  Vesion 1.0
 */
@Service
@Slf4j
@Transactional
public class FormDataService {

    @Autowired
    private FormDesignService formDesignService ;

    @Autowired
    private FormDataMapper formDataDao ;

    @Autowired
    private HttpSession session;

    @Autowired
    private TableDataAuthMapper tableDataAuthDao ;

    @Autowired
    private FileMapper fileDao ;

    @Autowired
    private UserService userService ;

    /**
     * 获得我能查看的表单数据(权限见赋权功能):我创建的 和 赋权给我的: 带分页
     * @param formDataPageParam PageResult
     */
    public PageResult<Map<String,Object>> getFormDataByFormId(PageParam<FormDataQueryDTO> formDataPageParam) {

        FormDataQueryDTO formDataQueryDTO = formDataPageParam.getDto() ;

        //1：获得表单配置的详细数据后，拼装FormDataQueryDTO
        FormDesignVO formDesignVO = formDesignService.getFormDesignVOById(formDataQueryDTO.getTableId());
        if(null == formDesignVO || null == formDesignVO.getTableConfig() || null == formDesignVO.getTableQueryConfigList()){ return null; }
        formDataQueryDTO.setTableName(formDesignVO.getTableConfig().getEnglishName());
        User user = SessionUserUtils.getUserFromSession(session);
        formDataQueryDTO.setUserId(user.getId());
        //TODO 这里没有做选择的业务查询条件，太复杂，因为业务条件 可能是 大于，可能是等于，也可能是like。 用Map做不到，要封装一个对象：properName,propertyValue,queryType

        PageInfo<Map<String,Object>> tableResultPage = PageHelper.startPage(formDataPageParam.getPageNum(),formDataPageParam.getPageSize())
                .doSelectPageInfo(() -> formDataDao.selectMyFormDataAndAuthToMeData(formDataQueryDTO));

        //将外键引用其他表的ID字段，扩展到展示字段
        if(null != tableResultPage.getList() && tableResultPage.getList().size() > 0 ) {
            this.extendTableData(formDesignVO.getTableColumnConfigList(),tableResultPage.getList());
        }

        return PageHelperUtils.getPageResult(tableResultPage) ;
    }

    /**
     * ]根据表单设计主键ID，与数据主键ID的List,  获得明细数据。
     * @param formDataQueryDTO  查询条件DTO
     */
    public List<Map<String, Object>> getFormDataByTableIdAndDataIdList(FormDataQueryDTO formDataQueryDTO) {

        //1：获得表单配置的详细数据后，拼装FormDataQueryDTO
        FormDesignVO formDesignVO = formDesignService.getFormDesignVOById(formDataQueryDTO.getTableId());
        if(null == formDesignVO || null == formDesignVO.getTableConfig() || null == formDesignVO.getTableQueryConfigList()){ return new ArrayList<>(); }
        formDataQueryDTO.setTableName(formDesignVO.getTableConfig().getEnglishName());
        User user = SessionUserUtils.getUserFromSession(session);
        formDataQueryDTO.setUserId(user.getId());

        List<Map<String, Object>> dataList = formDataDao.selectMyFormDataAndAuthToMeData(formDataQueryDTO);
        //将外键引用其他表的ID字段，扩展到展示字段
        if(null != dataList && dataList.size() > 0 ) {
            this.extendTableData(formDesignVO.getTableColumnConfigList(),dataList);
        }

        return dataList;
    }

    /**
     * 根据FormDataJudgeDuplicateQueryDTO(表单ID，表名，字段名，值),判断数据是否重复
     * @param formDataJudgeDuplicateQueryDTO 判断表达字段是否重复查询的DTO
     * @return 重复返回True，不重复返回False
     */
    public Boolean judgeFormColumnDataIsDuplicate(FormDataJudgeDuplicateQueryDTO formDataJudgeDuplicateQueryDTO) {
        List<Map<String, Object>> dataList = formDataDao.selectByTableColumnValue(formDataJudgeDuplicateQueryDTO);
        if(null != dataList && dataList.size()>0 ){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    /**
     * 新增一个表单数据
     * @param formRowDataDTO 一个表单的一行数据
     * @return CommonSuccessOrFail
     */
    public CommonSuccessOrFail addFormOneRowData(FormRowDataDTO formRowDataDTO) {
        //1:验证核心参数是否具备
        if(null == formRowDataDTO || null == formRowDataDTO.getTableId() || formRowDataDTO.getTableId()<1 || null == formRowDataDTO.getColumnValueList()){
            CommonSuccessOrFail.fail("参数错误");
        }
        FormDesignVO formDesignVO = formDesignService.getFormDesignConfigWithoutExtendData(formRowDataDTO.getTableId());
        //2:根据表单配置数据类型和值，校验数据合法性
        CommonSuccessOrFail commonSuccessOrFail = this.validateFormData(formDesignVO,formRowDataDTO);
        if(CommonSuccessOrFail.CODE_ERROR == commonSuccessOrFail.getResultCode()){
            return commonSuccessOrFail ;
        }
        //3:设置正确的默认属性(创建人、创建时间、租户ID)
        formRowDataDTO = this.setFormOneRowDataDefaultValue(formRowDataDTO);

        //4:去除前端传入的，可能会有FormRowDataDTO.columnValueList[0].columnValue 为空的情况
        this.clearnNullColumnData(formRowDataDTO);

        //5:保存数据
        formDataDao.insertFormOneRowData(formRowDataDTO);

        return CommonSuccessOrFail.success("新增成功");
    }

    /** 去除前端传入的，可能会有FormRowDataDTO.columnValueList[0].columnValue 为空的情况 */
    private void clearnNullColumnData(FormRowDataDTO formRowDataDTO) {
        List<OneColumnValue> columnValueList = formRowDataDTO.getColumnValueList();
        List<OneColumnValue> targetColumnValueList = new ArrayList<>();
        columnValueList.forEach(oneColumnValue -> {
            if(null != oneColumnValue.getColumnValue() && oneColumnValue.getColumnValue().length() > 0){
                targetColumnValueList.add(oneColumnValue);
            }
        });
        formRowDataDTO.setColumnValueList(targetColumnValueList);

    }

    /** 设置正确的默认属性(创建人、创建时间、租户ID)，先移出传入的，再加入默认的 */
    private FormRowDataDTO setFormOneRowDataDefaultValue(FormRowDataDTO formRowDataDTO) {
        Iterator<OneColumnValue> iterator = formRowDataDTO.getColumnValueList().iterator();
        while(iterator.hasNext()) {
            OneColumnValue oneColumnValue = iterator.next();
            if (TableColumnConfig.DEFAULT_COLUMN_CREATE_USER.equals(oneColumnValue.getColumnName())) {
                iterator.remove();
            }
            if (TableColumnConfig.DEFAULT_COLUMN_CREATE_TIME.equals(oneColumnValue.getColumnName())) {
                iterator.remove();
            }
            if (TableColumnConfig.DEFAULT_COLUMN_UPDATE_TIME.equals(oneColumnValue.getColumnName())) {
                iterator.remove();
            }
            if (TableColumnConfig.DEFAULT_COLUMN_TENANT_ID.equals(oneColumnValue.getColumnName())) {
                iterator.remove();
            }
            if(TableColumnConfig.DEFAULT_COLUMN_DISPLAY_CUREATE_USER_NAME.equals(oneColumnValue.getColumnName())){
                iterator.remove();
            }
        }
        User user = SessionUserUtils.getUserFromSession(session);
        formRowDataDTO.getColumnValueList().add(new OneColumnValue(TableColumnConfig.DEFAULT_COLUMN_CREATE_USER,user.getId()+""));
        formRowDataDTO.getColumnValueList().add(new OneColumnValue(TableColumnConfig.DEFAULT_COLUMN_CREATE_TIME, new Timestamp(System.currentTimeMillis()).toString()));
        formRowDataDTO.getColumnValueList().add(new OneColumnValue(TableColumnConfig.DEFAULT_COLUMN_TENANT_ID,user.getTenantId()+""));
        return formRowDataDTO;
    }

    /** 根据表单配置数据类型和值，校验数据合法性 */
    private CommonSuccessOrFail validateFormData(FormDesignVO formDesignVO, FormRowDataDTO formRowDataDTO) {
        //TODO
        return CommonSuccessOrFail.success("数据校验通过。");
    }

    /** 将外键引用的ID，文件引用的ID，扩展成 外键展示名称 与 文件展示名称 */
    private void extendTableData(List<TableColumnConfig> tableColumnConfigList, List<Map<String, Object>> formDataList) {
        //1：扩展 外键引用的ID扩展成 外键展示名称
        this.extendTableDataForeignValue(tableColumnConfigList,formDataList);
        //2: 扩展 文件引用的ID扩展成 文件展示名称
        this.extendTableDataFileValue(tableColumnConfigList,formDataList);
        //3: 增加创建热默认返回字段
        this.addCreateUserNameColumn(formDataList);
    }

    /** 在每一条返回数据中，增加创建人返回数据 */
    private void addCreateUserNameColumn(List<Map<String, Object>> formDataList) {
        formDataList.forEach(oneRowDataMap -> {
            Long createUserId = Long.parseLong(oneRowDataMap.get("create_user").toString());
            User user = userService.getUserById(createUserId);
            String userName = "N/A" ;
            if(null != user && null != user.getUserName()){
                userName = user.getUserName();
            }else{
                userName = user.getId()+"";
            }
            oneRowDataMap.put("create_user_name",userName);
        });
    }

    /** 根据表单配置、表单字段配置，将 文件ID扩展成ID与文件名称 */
    private void extendTableDataFileValue(List<TableColumnConfig> tableColumnConfigList, List<Map<String, Object>> formDataList) {

        //定义本表单配置数据的文件引用字段Set<FileColumnName>
        Set<String> fileColumnNameSet = new HashSet<>();
        //记录本表单数据所有引用文件数据Map<fileId,fileName>
        Map<Long,String> fileIdValueMap = new HashMap<>();

        User user = SessionUserUtils.getUserFromSession(session);

        //1：遍历本设计表字段 获得外键字段，并构建外键FormDataQueryDTO,依次放入parentFormDataQueryDTOMap，extendValueMap，foreignFormDesignVOMap
        tableColumnConfigList.forEach(tableColumnConfig -> {
            if(TableColumnTypeEnum.COLUMN_FILE == tableColumnConfig.getColType()){
                fileColumnNameSet.add(tableColumnConfig.getEnglishName());
            }
        });
        if(fileColumnNameSet.size()<1){ return;}

        //2：遍历 遍历formDataList,获得字段对应文件引用ID
        formDataList.forEach(formDataMap -> formDataMap.forEach((column, columnVal) ->{
            if(fileColumnNameSet.contains(column)){
                fileIdValueMap.put(Long.parseLong(columnVal.toString()),"");
            }
        }));
        if(fileIdValueMap.size()<1) {return;}

        //3：得到了文件引用的ID，依次获得每个文件ID的文件名, 放入到fileIdValueMap当中
        List<PublicFile> fileList = fileDao.listByIds(fileIdValueMap.keySet());
        fileList.forEach(file ->{
            Long fileId = file.getId();
            fileIdValueMap.put(file.getId(),file.getFileNameOriginal());
        });

        //4：遍历要扩展的结果数据formDataList，将外键字段的value，替换成Map<原Id,外键表的Display字段
        formDataList.forEach(oneRowMap ->{
            oneRowMap.forEach((columnName,colunmValue) ->{
                if(fileColumnNameSet.contains(columnName)){
                    Map<String,Object> extendMap = new HashMap<>();
                    extendMap.put("originValue",colunmValue);
                    extendMap.put("displayValue",fileIdValueMap.get(Long.parseLong(colunmValue.toString())));
                    oneRowMap.put(columnName,extendMap);
                }
            });
        });
    }

    /** 根据表单配置、表单字段配置，将 外键引用的字段 扩充展示 引用表的展示字段;  */
    private void extendTableDataForeignValue(List<TableColumnConfig> tableColumnConfigList, List<Map<String, Object>> formDataList) {
        //定义外键引用父表的Map<本表属性名,本属性引用表查询DTO>
        Map<String,FormDataQueryDTO> parentFormDataQueryDTOMap = new HashMap<>();
        //定义外键引用父表主键ID对应的展示字段值Map<本表属性名,Map<引用表主键ID,引用表展示字段>>
        Map<String,Map<Long,String>> extendValueMap = new HashMap<>();
        //定义本数据中 所有引用外键表的Map<tableDesingId,FormDesignVO>
        Map<Long,FormDesignVO> foreignFormDesignVOMap = new HashMap<>();
        User user = SessionUserUtils.getUserFromSession(session);

        //1：遍历本设计表字段 获得外键字段，并构建外键FormDataQueryDTO,依次放入parentFormDataQueryDTOMap，extendValueMap，foreignFormDesignVOMap
        tableColumnConfigList.forEach(tableColumnConfig -> {
            if(null != tableColumnConfig.getFkValue() && tableColumnConfig.getFkValue() > 0 && TableColumnTypeEnum.COLUMN_FOREIGN_KEY == tableColumnConfig.getColType()){
                FormDataQueryDTO oneFormDataQueryDTO = new FormDataQueryDTO();
                oneFormDataQueryDTO.setTableId(tableColumnConfig.getFkValue());
                oneFormDataQueryDTO.setUserId(user.getId());
                FormDesignVO foreignFormDesignVO = formDesignService.getFormDesignVOById(tableColumnConfig.getFkValue());
                oneFormDataQueryDTO.setTableName(foreignFormDesignVO.getTableConfig().getEnglishName());
                parentFormDataQueryDTOMap.put(tableColumnConfig.getEnglishName(),oneFormDataQueryDTO);
                extendValueMap.put(tableColumnConfig.getEnglishName(),new HashMap<>());
                foreignFormDesignVOMap.put(tableColumnConfig.getFkValue(),foreignFormDesignVO);
            }
        });
        if(parentFormDataQueryDTOMap.size()<1){ return;}

        //2：遍历 遍历formDataList,获得字段对应FormDataQueryDTO 里面的引用外键ID数据的List
        formDataList.forEach(formDataMap -> formDataMap.forEach((column, columnVal) ->{
            if(null != parentFormDataQueryDTOMap.get(column)){
                FormDataQueryDTO oneFormDataQueryDTO = parentFormDataQueryDTOMap.get(column);
                oneFormDataQueryDTO.getDataIdList().add(Long.parseLong(columnVal.toString()));
                extendValueMap.get(column).put(Long.parseLong(columnVal.toString()),"");
            }
        }));

        //3：得到了外键引用的字段、引用表ID和DataId，依次获得每个属性的全部展示字段value数据
        parentFormDataQueryDTOMap.forEach((columnName,foreignColumnFormDataQueryDTO) ->{
            FormDesignVO formDesignVO = foreignFormDesignVOMap.get(foreignColumnFormDataQueryDTO.getTableId());
            String foreignColunmName = FormDesignUtils.getDisplayColumnNameFromColumnConfigList(formDesignVO.getTableColumnConfigList());

            List<Map<String,Object>> oneFormDataListMap = formDataDao.selectMyFormDataAndAuthToMeData(foreignColumnFormDataQueryDTO);
            for(Map<String,Object> oneMap : oneFormDataListMap){
                extendValueMap.get(columnName).put(Long.parseLong(oneMap.get("id").toString()),(String)oneMap.get(foreignColunmName));
            }
        });

        //4：遍历要扩展的结果数据formDataList，将外键字段的value，替换成Map<原Id,外键表的Display字段
        formDataList.forEach(oneRowMap ->{
            oneRowMap.forEach((columnName,colunmValue) ->{
                if(null != extendValueMap.get(columnName) && null != extendValueMap.get(columnName).get(Long.parseLong(colunmValue.toString()))){
                    Map<String,Object> extendMap = new HashMap<>();
                    extendMap.put("originValue",colunmValue);
                    extendMap.put("displayValue",extendValueMap.get(columnName).get(Long.parseLong(colunmValue.toString())));
                    oneRowMap.put(columnName,extendMap);
                }
            });
        });
    }

    /** [赋权功能--数据权限]将我创建的表的数据，付给别人：可看、可改 */
    public void authTableData(List<TableDataAuth> tableDataAuthList) {
        if(null == tableDataAuthList || tableDataAuthList.size()<1){
            return;
        }

        //1：TODO 权限判断，只有创建此表单配置的人才可以授权(表的创建者 与 当前用户一致)

        User user = SessionUserUtils.getUserFromSession(session);
        //2：填入必要数据，以便于数据保存完成
        tableDataAuthList.forEach(tableDesignAuth -> {
            tableDesignAuth.setFromUser(user.getId());
            tableDesignAuth.setCreateUser(user.getId());
            tableDesignAuth.setTenantId(user.getTenantId());
        });

        //3：数据入库之前，先删除以前授权数据
        tableDataAuthDao.deleteByUserIdAndTableId(user.getId(), tableDataAuthList.get(0).getFromTable());

        //4：授权数据入库
        tableDataAuthDao.insertBatch(tableDataAuthList);
    }

    /** 获得当前表单数据，已经将我表单数据赋权给其他人员的信息 */
    public List<TableDataAuth> listAuthTableData(Long tableId) {
        if(null == tableId || tableId < 1){
            return new ArrayList<>();
        }

        User user = SessionUserUtils.getUserFromSession(session);
        return tableDataAuthDao.selectByUserIdAndTableId(user.getId(), tableId);
    }
}
