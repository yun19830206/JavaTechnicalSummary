package com.cloud.aiassistant.formdesign.service;

import com.cloud.aiassistant.core.utils.PageHelperUtils;
import com.cloud.aiassistant.enums.formdesign.TableColumnEnum;
import com.cloud.aiassistant.formdesign.dao.*;
import com.cloud.aiassistant.formdesign.pojo.*;
import com.cloud.aiassistant.pojo.common.CommonSuccessOrFail;
import com.cloud.aiassistant.pojo.common.PageParam;
import com.cloud.aiassistant.pojo.common.PageResult;
import com.cloud.aiassistant.pojo.formdesign.TableColumnConfig;
import com.cloud.aiassistant.pojo.formdesign.TableConfig;
import com.cloud.aiassistant.pojo.user.User;
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
        User user=(User) session.getAttribute(User.SESSION_KEY_USER);
        formDataQueryDTO.setUserId(user.getId());
        //TODO 这里没有做选择的业务查询条件，太复杂，因为业务条件 可能是 大于，可能是等于，也可能是like。 用Map做不到，要封装一个对象：properName,propertyValue,queryType

        PageInfo<Map<String,Object>> tableResultPage = PageHelper.startPage(formDataPageParam.getPageNum(),formDataPageParam.getPageSize())
                .doSelectPageInfo(() -> formDataDao.selectMyFormDataAndAuthToMeData(formDataQueryDTO));

        //将外键引用其他表的ID字段，扩展到展示字段
        if(null != tableResultPage.getList() && tableResultPage.getList().size() > 0 ) {
            this.extendTableDataForeignValue(formDesignVO.getTableColumnConfigList(),tableResultPage.getList());
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
        User user=(User) session.getAttribute(User.SESSION_KEY_USER);
        formDataQueryDTO.setUserId(user.getId());

        List<Map<String, Object>> dataList = formDataDao.selectMyFormDataAndAuthToMeData(formDataQueryDTO);
        //将外键引用其他表的ID字段，扩展到展示字段
        if(null != dataList && dataList.size() > 0 ) {
            this.extendTableDataForeignValue(formDesignVO.getTableColumnConfigList(),dataList);
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
        FormDesignVO formDesignVO = formDesignService.getFormDesignVOById(formRowDataDTO.getTableId());
        //2:根据表单配置数据类型和值，校验数据合法性
        CommonSuccessOrFail commonSuccessOrFail = this.validateFormData(formDesignVO,formRowDataDTO);
        if(CommonSuccessOrFail.CODE_ERROR == commonSuccessOrFail.getResultCode()){
            return commonSuccessOrFail ;
        }
        //3:设置正确的默认属性(创建人、创建时间、租户ID)
        formRowDataDTO = this.setFormOneRowDataDefaultValue(formRowDataDTO);

        //4:保存数据
        formDataDao.insertFormOneRowData(formRowDataDTO);

        return CommonSuccessOrFail.success("新增成功");
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
            if (TableColumnConfig.DEFAULT_COLUMN_TENANT_ID.equals(oneColumnValue.getColumnName())) {
                iterator.remove();
            }
        }
        User user=(User) session.getAttribute(User.SESSION_KEY_USER);
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

    /** 根据表单配置、表单字段配置，将 外键引用的字段 扩充展示 引用表的展示字段 */
    private void extendTableDataForeignValue(List<TableColumnConfig> tableColumnConfigList, List<Map<String, Object>> formDataList) {
        //定义外键引用父表的Map<本表属性名,本属性引用表查询DTO>
        Map<String,FormDataQueryDTO> parentFormDataQueryDTOMap = new HashMap<>();
        //定义外键引用父表主键ID对应的展示字段值Map<本表属性名,Map<引用表主键ID,引用表展示字段>>
        Map<String,Map<Long,String>> extendValueMap = new HashMap<>();
        //定义本数据中 所有引用外键表的Map<tableDesingId,FormDesignVO>
        Map<Long,FormDesignVO> foreignFormDesignVOMap = new HashMap<>();
        User user=(User) session.getAttribute(User.SESSION_KEY_USER);

        //1：遍历本设计表字段 获得外键字段，并构建外键FormDataQueryDTO,依次放入parentFormDataQueryDTOMap，extendValueMap，foreignFormDesignVOMap
        tableColumnConfigList.forEach(tableColumnConfig -> {
            if(null != tableColumnConfig.getFkValue() && tableColumnConfig.getFkValue() > 0 && TableColumnEnum.COLUMN_FOREIGN_KEY == tableColumnConfig.getColType()){
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
            String foreignColunmName = this.getDisplayColumnNameFromColumnConfigList(formDesignVO.getTableColumnConfigList());

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

    /** 根据一个表单所有字段配置的List，获得本表单的被外键引用的展示字段 */
    private String getDisplayColumnNameFromColumnConfigList(List<TableColumnConfig> tableColumnConfigList) {
        if(null == tableColumnConfigList ){
            return "";
        }
        for(TableColumnConfig tableColumnConfig : tableColumnConfigList){
            if(tableColumnConfig.getDisplayColumn() == 1){
                return tableColumnConfig.getEnglishName();
            }
        }
        return "";
    }



}
