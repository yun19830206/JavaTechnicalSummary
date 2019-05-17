package com.cloud.aiassistant.business.crm.service;

import com.cloud.aiassistant.business.crm.dao.CrmBusinessMapper;
import com.cloud.aiassistant.business.crm.pojo.CrmCustomerVO;
import com.cloud.aiassistant.core.exception.FormDesignBusinessException;
import com.cloud.aiassistant.core.utils.SessionUserUtils;
import com.cloud.aiassistant.core.wxsdk.WxApiComponent;
import com.cloud.aiassistant.formdata.dao.FormDataMapper;
import com.cloud.aiassistant.formdata.pojo.FormDataQueryDTO;
import com.cloud.aiassistant.formdata.service.FormDataService;
import com.cloud.aiassistant.formdesign.pojo.FormDesignVO;
import com.cloud.aiassistant.formdesign.service.FormDesignService;
import com.cloud.aiassistant.pojo.common.AjaxResponse;
import com.cloud.aiassistant.pojo.formdesign.TableColumnConfig;
import com.cloud.aiassistant.pojo.user.User;
import com.cloud.aiassistant.pojo.wxsdh.WxPushMessageResonseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CRM业务数据Service
 * @author ChengYun
 * @date 2019/3/24  Vesion 1.0
 */
@Service
@Transactional
public class CrmBusinessService {

    @Value("${crm.table.design.customer:1}")
    private Long customTableDesignId;

    @Value("${crm.table.design.project:2}")
    private Long projectTableDesignId;
    @Value("${crm.table.design.project.table.name:crm_project}")
    private String crmProjectTableName;
    @Value("${crm.table.design.project.customer.fkname:compange_name}")
    private String projectCustomerFkName;


    @Value("${crm.table.design.contact:3}")
    private Long contactTableDesignId;
    @Value("${crm.table.design.contact.table.name:crm_project_user}")
    private String crmContactTableName;
    @Value("${crm.table.design.contact.customer.fkname:compange_name}")
    private String contactCustomerFkName;


    @Value("${crm.table.design.visit:4}")
    private Long visitTableDesignId;
    @Value("${crm.table.design.visit.table.name:crm_project_visit}")
    private String crmVisitTableName;
    @Value("${crm.table.design.visit.project.fkname:project_name}")
    private String visitProjectFkName;

    @Autowired
    private FormDesignService formDesignService;

    @Autowired
    private FormDataService formDataService;

    @Autowired
    private FormDataMapper formDataDao ;

    @Autowired
    private CrmBusinessMapper crmBusinessDao ;

    @Autowired
    private HttpSession session ;



    /**
     * 根据客户唯一标识，获得我的客户信息：客户、联系人、项目
     * @param customerId
     * @return
     */
    public CrmCustomerVO getCrmCustomerVO(Long customerId) {
        //1:CRM客户信息：一个Map<属性名,属性值>代表一个属性和值，List<Map>代表完整一个CRM客户对象。以此规避属性顺序不对的情况
        List<Map<String,Object>> customer = this.getOneKeyValueTableData(customerId,customTableDesignId);

        //2:项目信息：一个Map<属性名,属性值>代表一个属性和值，List<Map>代表完整的一个CRM客户对象。List<List>代表一个CRM客户会存在多个项目的情况
        List<List<Map<String,Object>>> projectList = new ArrayList<>();
        List<Long> projectIdList = crmBusinessDao.selectProjectIdListByCustomerId(crmProjectTableName,customerId,projectCustomerFkName);
        if(null != projectIdList && projectIdList.size()>0){
            projectIdList.forEach(projectId ->{
                projectList.add(this.getOneKeyValueTableData(projectId,projectTableDesignId));
            });
        }

        //3:联系人信息：一个Map<属性名,属性值>代表一个属性和值,List<Map>代表完整的一个联系人信息。List<List>代表一个CRM客户会存在多个联系人的情况
        List<List<Map<String,Object>>> contactPeopleList = new ArrayList<>();
        List<Long> contactIdList = crmBusinessDao.selectContactIdListByCustomerId(crmContactTableName,customerId,contactCustomerFkName);
        if(null != contactIdList && contactIdList.size()>0){
            contactIdList.forEach(contactId ->{
                contactPeopleList.add(this.getOneKeyValueTableData(contactId,contactTableDesignId));
            });
        }

        CrmCustomerVO crmCustomerVO = new CrmCustomerVO();
        crmCustomerVO.setCustomer(customer);
        crmCustomerVO.setProjectList(projectList);
        crmCustomerVO.setContactPeopleList(contactPeopleList);
        return crmCustomerVO;
    }

    /**
     * 根据表数据主键ID、表设计主键ID，返回根据字段循序排列的结果：List<Map<String,Object>>，此处Map只有一个key/value
     * @param tableDataId 表数据主键ID
     * @param contactTableDesignId 表设计主键ID
     * @return List<Map<String,Object>>
     */
    private List<Map<String,Object>> getOneKeyValueTableData(Long tableDataId,Long contactTableDesignId) {
        //1:获得客户的字段配置信息
        FormDesignVO customerDesignVO = formDesignService.getFormDesignConfigWithoutExtendData(contactTableDesignId);

        User user = SessionUserUtils.getUserFromSession(session);
        //2:获得客户对应Id的值
        FormDataQueryDTO customerDataQueryDTO = new FormDataQueryDTO();
        customerDataQueryDTO.setTableId(customerDesignVO.getTableConfig().getId());
        customerDataQueryDTO.setTableName(customerDesignVO.getTableConfig().getEnglishName());
        customerDataQueryDTO.setUserId(user.getId());
        List<Long> dataIdList = new ArrayList<>();
        dataIdList.add(tableDataId);
        customerDataQueryDTO.setDataIdList(dataIdList);
        List<Map<String, Object>> customerDataList = formDataService.getFormDataByTableIdAndDataIdList(customerDataQueryDTO);

        //3:获得字段顺序 拼装到有序当中List
        return this.transMutiKeyMapToListMapWithColumnOrder(customerDesignVO.getTableColumnConfigList(),customerDataList.get(0));
    }

    /**
     * 将包含多个key\value的map(mutiColumnValueMap),根据字段序号(tableColumnConfigList),转换成Map只包含一个属性值的List
     * @param tableColumnConfigList 包含顺序的字段配置信息
     * @param mutiColumnValueMap  包含多个key\value的map
     * @return
     */
    private List<Map<String,Object>> transMutiKeyMapToListMapWithColumnOrder(List<TableColumnConfig> tableColumnConfigList, Map<String, Object> mutiColumnValueMap) {
        List<Map<String,Object>> returnOneKeyValueMapList = new ArrayList<>();

        //第一位加上主键ID，创建日期
        Map<String,Object> primaryKeyMap = new HashMap<>();
        primaryKeyMap.put("id",mutiColumnValueMap.get("id"));
        returnOneKeyValueMapList.add(primaryKeyMap);

        //tableColumnConfigList在数据库层返回的时候就是有序的，这样不需要排序了，直接根据columnName取值塞入即可。
        tableColumnConfigList.forEach(tableColumnConfig -> {
            Map<String,Object> oneKeyValueMap = new HashMap<>();
            String columnName = tableColumnConfig.getEnglishName();
            oneKeyValueMap.put(columnName,mutiColumnValueMap.get(columnName));
            returnOneKeyValueMapList.add(oneKeyValueMap);
        });

        //最后一位加上创建日期
        Map<String,Object> createDateTime = new HashMap<>();
        createDateTime.put("create_time",mutiColumnValueMap.get("create_time"));
        returnOneKeyValueMapList.add(createDateTime);

        return returnOneKeyValueMapList;
    }

    /** 根据拜访记录信息，更新CRM客户的更新时间 */
    public void updateCrmCustomerUpdateTime() {
        crmBusinessDao.updateCrmCustomerUpdateTime();
    }

    /** CRM一个客户转交给其他人 */
    public void transCustomerToUser(Long customerId, Long toUserId) {
        if(null == customerId || customerId < 0 || null == toUserId || toUserId < 0 ){
            throw new FormDesignBusinessException("核心参数缺失，请联系管理员");
        }

        //转移拜访记录数据
        crmBusinessDao.transVisitorToUser(customerId,toUserId);

        //转移联系人数据
        crmBusinessDao.transConnectorToUser(customerId,toUserId);

        //转移项目数据
        crmBusinessDao.transProjectToUser(customerId,toUserId);

        //转移客户数据
        crmBusinessDao.transCustomerToUser(customerId,toUserId);
    }
}
