package com.cloud.aiassistant.business.crm.service;

import com.alibaba.excel.metadata.Sheet;
import com.cloud.aiassistant.business.crm.dao.CrmBusinessMapper;
import com.cloud.aiassistant.business.crm.pojo.CrmCustSheet;
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
import com.cloud.aiassistant.pojo.common.PageResult;
import com.cloud.aiassistant.pojo.formdesign.TableColumnConfig;
import com.cloud.aiassistant.pojo.user.User;
import com.cloud.aiassistant.pojo.wxsdh.WxPushMessageResonseVO;
import com.cloud.aiassistant.user.dao.UserMapper;
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
    private CrmBusinessMapper crmBusinessDao ;

    @Autowired
    private HttpSession session ;

    @Autowired
    private UserMapper userDao ;


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
        if(customerDataList.size() == 0 ) {
            return customerDataList ;
        }

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

        //第一位加上主键ID
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

        //最后一位加上创建日期(tableColumnConfigList里面已经有create_time了，所以不需要了，注释掉)
//        Map<String,Object> createDateTime = new HashMap<>();
//        createDateTime.put("create_time",mutiColumnValueMap.get("create_time"));
//        returnOneKeyValueMapList.add(createDateTime);

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

    /**
     * 获得所有CRM销售人员。
     * @return
     */
    public List<User> ListCrmUserList() {
        List<User> crmUserList = new ArrayList<>();

        //获得所有人员信息
        List<User> userList = userDao.listTenantAllUser(200L);
        if(null == userList || userList.size()<1){
            return crmUserList ;
        }

        //绑定过WX的用户，统一给其推送消息
        userList.forEach(user ->{
            //user.getCreateUser().equals(1L)代表是销售人员
            if(user.getCreateUser().equals(1L) ){
                user.setPassword("******");
                crmUserList.add(user);
            }
        });
        return crmUserList;
    }

    /** 获得项目下面的拜访记录信息。 */
    public List<List<Map<String,Object>>> ListProjectVisitList(Long projectId) {

        //2:项目信息：一个Map<属性名,属性值>代表一个属性和值，List<Map>代表完整的一个CRM客户对象。List<List>代表一个CRM客户会存在多个项目的情况
        List<List<Map<String,Object>>> visitList = new ArrayList<>();
        List<Long> projectIdList = crmBusinessDao.selectVisitIdListByProjectId(crmVisitTableName,projectId,visitProjectFkName);
        if(null != projectIdList && projectIdList.size()>0){
            projectIdList.forEach(visitId ->{
                visitList.add(this.getOneKeyValueTableData(visitId,visitTableDesignId));
            });
        }

        return visitList;
    }

    /**
     * CRM客户表、项目表、联系人表、拜访记录表 4个资源ExcelSheet信息
     * @param customerQueryDto 查询条件
     * @return
     */
    public List<CrmCustSheet> exportCrmCustomer(FormDataQueryDTO customerQueryDto) {
        List<CrmCustSheet> crmCustSheetList = new ArrayList<>();

        //1:根据条件 组装CRM客户Sheet和Data
        User user = SessionUserUtils.getUserFromSession(session);
        customerQueryDto.setUserId(user.getId());
        List<Map<String,Object>> crmCustomerList = crmBusinessDao.selectCrmCustomerListByCondition(customerQueryDto);
        crmCustSheetList.add(getCrmCustomerSheet(crmCustomerList));

        //2:根据客户IdList 组装CRM项目Sheet和Data
        List<Long> custIdList = getCustomerIdList(crmCustomerList);
        if(null == custIdList || custIdList.size()<1) {
            return crmCustSheetList;
        }
        List<Map<String,Object>> crmProjectList = crmBusinessDao.selectCrmProjectListByCustIdList(custIdList);
        crmCustSheetList.add(getCrmProjectSheet(crmProjectList));


        //3:根据客户IdList 组装CRM联系人Sheet和Data
        List<Map<String,Object>> crmPersionList = crmBusinessDao.selectCrmPersionListByCustIdList(custIdList);
        crmCustSheetList.add(getCrmPersionSheet(crmPersionList));

        //4:根据项目IdList 组装CRM拜访记录Sheet和Data
        List<Long> projectIdList = getProjectIdList(crmProjectList);
        if(null == custIdList || custIdList.size()<1) {
            return crmCustSheetList;
        }
        List<Map<String,Object>> crmvisitList = crmBusinessDao.selectCrmVisitListByCustIdList(projectIdList);
        crmCustSheetList.add(getCrmVisitSheet(crmvisitList));
        return crmCustSheetList ;
    }

    /** 根据CRM拜访记录的List，组装导出excel需要的VO */
    private CrmCustSheet getCrmVisitSheet(List<Map<String, Object>> crmvisitList) {
        CrmCustSheet visitSheet = new CrmCustSheet();

        //1：定义Visit表的属性名 作为表头
        List<List<String>> visitSheetHeaderList = getVisitSheetHeaderList();
        Sheet visitSheetHead = new Sheet(4, 1);
        visitSheetHead.setSheetName("CRM拜访记录");
        visitSheetHead.setHead(visitSheetHeaderList);
        visitSheet.setSheetHead(visitSheetHead);

        //2：组装Visit表的数据Data
        List<List<Object>> visitSheetDataList = new ArrayList<>();
        if(crmvisitList != null && crmvisitList.size()>0){
            crmvisitList.forEach(oneDataMap ->{
                List<Object> oneRow = new ArrayList<>();
                oneRow.add(oneDataMap.get("id"));
                oneRow.add(oneDataMap.get("project_name"));
                oneRow.add(oneDataMap.get("user_name"));
                oneRow.add(oneDataMap.get("visit_type"));
                oneRow.add(oneDataMap.get("visit_text"));
                oneRow.add(oneDataMap.get("create_user"));
                oneRow.add(oneDataMap.get("create_time"));
                oneRow.add(oneDataMap.get("update_time"));
                visitSheetDataList.add(oneRow);
            });
        }
        visitSheet.setSheetData(visitSheetDataList);

        return visitSheet ;
    }

    /** 根据Crm项目表的结果，获得CRM项目Id的List */
    private List<Long> getProjectIdList(List<Map<String, Object>> crmProjectList) {
        List<Long> projectIdList = new ArrayList<>();
        if(crmProjectList != null && crmProjectList.size()>0){
            crmProjectList.forEach(oneDataMap ->{
                projectIdList.add(Long.parseLong(oneDataMap.get("id")+""));
            });
        }
        return projectIdList;
    }

    /** 根据CRM联系表的List，组装导出excel需要的VO */
    private CrmCustSheet getCrmPersionSheet(List<Map<String, Object>> crmPersionList) {
        CrmCustSheet persionSheet = new CrmCustSheet();

        //1：定义Project表的属性名 作为表头
        List<List<String>> persionSheetHeaderList = getPersionSheetHeaderList();
        Sheet persionSheetHead = new Sheet(3, 1);
        persionSheetHead.setSheetName("CRM联系人");
        persionSheetHead.setHead(persionSheetHeaderList);
        persionSheet.setSheetHead(persionSheetHead);

        //2：组装Project表的数据Data
        List<List<Object>> persionSheetDataList = new ArrayList<>();
        if(crmPersionList != null && crmPersionList.size()>0){
            crmPersionList.forEach(oneDataMap ->{
                List<Object> oneRow = new ArrayList<>();
                oneRow.add(oneDataMap.get("id"));
                oneRow.add(oneDataMap.get("company_name"));
                oneRow.add(oneDataMap.get("user_name"));
                oneRow.add(oneDataMap.get("user_sex"));
                oneRow.add(oneDataMap.get("department"));
                oneRow.add(oneDataMap.get("position"));
                oneRow.add(oneDataMap.get("phone_number"));
                oneRow.add(oneDataMap.get("qq_wx"));
                oneRow.add(oneDataMap.get("email"));
                oneRow.add(oneDataMap.get("address"));
                oneRow.add(oneDataMap.get("age"));
                oneRow.add(oneDataMap.get("birthday"));
                oneRow.add(oneDataMap.get("hobby"));
                oneRow.add(oneDataMap.get("create_user"));
                oneRow.add(oneDataMap.get("create_time"));
                oneRow.add(oneDataMap.get("update_time"));
                persionSheetDataList.add(oneRow);
            });
        }
        persionSheet.setSheetData(persionSheetDataList);

        return persionSheet ;
    }

    /** 根据CRM项目表的List，组装导出excel需要的VO */
    private CrmCustSheet getCrmProjectSheet(List<Map<String, Object>> crmProjectList) {
        CrmCustSheet projectSheet = new CrmCustSheet();

        //1：定义Project表的属性名 作为表头
        List<List<String>> projectSheetHeaderList = getProjectSheetHeaderList();
        Sheet projectSheetHead = new Sheet(2, 1);
        projectSheetHead.setSheetName("CRM项目表");
        projectSheetHead.setHead(projectSheetHeaderList);
        projectSheet.setSheetHead(projectSheetHead);

        //2：组装Project表的数据Data
        List<List<Object>> customerSheetDataList = new ArrayList<>();
        if(crmProjectList != null && crmProjectList.size()>0){
            crmProjectList.forEach(oneDataMap ->{
                List<Object> oneRow = new ArrayList<>();
                oneRow.add(oneDataMap.get("id"));
                oneRow.add(oneDataMap.get("company_name"));
                oneRow.add(oneDataMap.get("project_name"));
                oneRow.add(oneDataMap.get("use_range"));
                oneRow.add(oneDataMap.get("use_department"));
                oneRow.add(oneDataMap.get("use_channel"));
                oneRow.add(oneDataMap.get("compet_vendor"));
                oneRow.add(oneDataMap.get("buy_system"));
                oneRow.add(oneDataMap.get("pre_company"));
                oneRow.add(oneDataMap.get("success_probability"));
                oneRow.add(oneDataMap.get("money"));
                oneRow.add(oneDataMap.get("start_date"));
                oneRow.add(oneDataMap.get("receive_date"));
                oneRow.add(oneDataMap.get("contract_amount"));
                oneRow.add(oneDataMap.get("deploy_method"));
                oneRow.add(oneDataMap.get("project_statu"));
                oneRow.add(oneDataMap.get("create_user"));
                oneRow.add(oneDataMap.get("create_time"));
                oneRow.add(oneDataMap.get("update_time"));
                customerSheetDataList.add(oneRow);
            });
        }
        projectSheet.setSheetData(customerSheetDataList);

        return projectSheet ;
    }

    /** 根据Crm客户表的结果，获得CRM客户Id的List */
    private List<Long> getCustomerIdList(List<Map<String, Object>> crmCustomerList) {
        List<Long> customerIdList = new ArrayList<>();
        if(crmCustomerList != null && crmCustomerList.size()>0){
            crmCustomerList.forEach(oneDataMap ->{
                customerIdList.add(Long.parseLong(oneDataMap.get("id")+""));
            });
        }
        return customerIdList;
    }


    /** 根据CRM客户表的List，组装导出excel需要的VO */
    private CrmCustSheet getCrmCustomerSheet(List<Map<String, Object>> crmCustomerList) {
        CrmCustSheet customerSheet = new CrmCustSheet();

        //1：定义Customer表的属性名 作为表头
        List<List<String>> customerSheetHeaderList = getCustomerSheetHeaderList();
        Sheet customerSheetHead = new Sheet(1, 1);
        customerSheetHead.setSheetName("CRM客户表");
        customerSheetHead.setHead(customerSheetHeaderList);
        customerSheet.setSheetHead(customerSheetHead);

        //2：组装Customer表的数据Data
        List<List<Object>> customerSheetDataList = new ArrayList<>();
        if(crmCustomerList != null && crmCustomerList.size()>0){
            crmCustomerList.forEach(oneDataMap ->{
                List<Object> oneRow = new ArrayList<>();
                oneRow.add(oneDataMap.get("id"));
                oneRow.add(oneDataMap.get("company_name"));
                oneRow.add(oneDataMap.get("industry_drop"));
                oneRow.add(oneDataMap.get("employ_number"));
                oneRow.add(oneDataMap.get("where_from"));
                oneRow.add(oneDataMap.get("customer_level"));
                oneRow.add(oneDataMap.get("customer_status"));
                oneRow.add(oneDataMap.get("create_user"));
                oneRow.add(oneDataMap.get("create_time"));
                oneRow.add(oneDataMap.get("update_time"));
                customerSheetDataList.add(oneRow);
            });
        }
        customerSheet.setSheetData(customerSheetDataList);

        return customerSheet ;

    }

    /** 创建Customer表的属性名 作为表头  */
    public List<List<String>> getCustomerSheetHeaderList() {
        List<List<String>> customerSheetHeaderList = new ArrayList<>();

        List<String> headerId = new ArrayList<>();
        headerId.add("主键ID");
        customerSheetHeaderList.add(headerId);

        List<String> companyName = new ArrayList<>();
        companyName.add("公司法定名称");
        customerSheetHeaderList.add(companyName);

        List<String> industryDrop = new ArrayList<>();
        industryDrop.add("行业");
        customerSheetHeaderList.add(industryDrop);

        List<String> employNumber = new ArrayList<>();
        employNumber.add("人员规模");
        customerSheetHeaderList.add(employNumber);

        List<String> whereFrom = new ArrayList<>();
        whereFrom.add("客户来源");
        customerSheetHeaderList.add(whereFrom);

        List<String> customerLevel = new ArrayList<>();
        customerLevel.add("客户等级");
        customerSheetHeaderList.add(customerLevel);

        List<String> customerStatus = new ArrayList<>();
        customerStatus.add("客户阶段");
        customerSheetHeaderList.add(customerStatus);

        List<String> createUser = new ArrayList<>();
        createUser.add("创建人");
        customerSheetHeaderList.add(createUser);

        List<String> createTime = new ArrayList<>();
        createTime.add("创建日期");
        customerSheetHeaderList.add(createTime);

        List<String> updateTime = new ArrayList<>();
        updateTime.add("更新日期");
        customerSheetHeaderList.add(updateTime);

        return customerSheetHeaderList;
    }

    /** 创建Project表的属性名 作为表头  */
    public List<List<String>> getProjectSheetHeaderList() {
        List<List<String>> projectSheetHeaderList = new ArrayList<>();

        List<String> headerId = new ArrayList<>();
        headerId.add("主键ID");
        projectSheetHeaderList.add(headerId);

        List<String> companyName = new ArrayList<>();
        companyName.add("公司法定名称");
        projectSheetHeaderList.add(companyName);

        List<String> projectName = new ArrayList<>();
        projectName.add("项目名称");
        projectSheetHeaderList.add(projectName);

        List<String> useRange = new ArrayList<>();
        useRange.add("使用范围");
        projectSheetHeaderList.add(useRange);

        List<String> useDepartment = new ArrayList<>();
        useDepartment.add("使用部门");
        projectSheetHeaderList.add(useDepartment);

        List<String> useChannel = new ArrayList<>();
        useChannel.add("对接渠道");
        projectSheetHeaderList.add(useChannel);

        List<String> competVendor = new ArrayList<>();
        competVendor.add("竞对厂商");
        projectSheetHeaderList.add(competVendor);

        List<String> buySystem = new ArrayList<>();
        buySystem.add("业务系统构成");
        projectSheetHeaderList.add(buySystem);

        List<String> preCompany = new ArrayList<>();
        preCompany.add("前任合作厂商");
        projectSheetHeaderList.add(preCompany);

        List<String> successProbability = new ArrayList<>();
        successProbability.add("成功概率");
        projectSheetHeaderList.add(successProbability);

        List<String> money = new ArrayList<>();
        money.add("项目预算(万元)");
        projectSheetHeaderList.add(money);

        List<String> startDate = new ArrayList<>();
        startDate.add("项目开始时间");
        projectSheetHeaderList.add(startDate);

        List<String> receiveDate = new ArrayList<>();
        receiveDate.add("项目验收时间");
        projectSheetHeaderList.add(receiveDate);


        List<String> contractAmount = new ArrayList<>();
        contractAmount.add("合同金额(万元)");
        projectSheetHeaderList.add(contractAmount);

        List<String> deployMethod = new ArrayList<>();
        deployMethod.add("部署方式");
        projectSheetHeaderList.add(deployMethod);

        List<String> projectStatu = new ArrayList<>();
        projectStatu.add("项目状态");
        projectSheetHeaderList.add(projectStatu);

        List<String> createUser = new ArrayList<>();
        createUser.add("创建人");
        projectSheetHeaderList.add(createUser);

        List<String> createTime = new ArrayList<>();
        createTime.add("创建日期");
        projectSheetHeaderList.add(createTime);

        List<String> updateTime = new ArrayList<>();
        updateTime.add("更新日期");
        projectSheetHeaderList.add(updateTime);

        return projectSheetHeaderList;
    }

    /** 创建联系人Persion表的属性名 作为表头  */
    public List<List<String>> getPersionSheetHeaderList() {
        List<List<String>> persionSheetHeaderList = new ArrayList<>();

        List<String> headerId = new ArrayList<>();
        headerId.add("主键ID");
        persionSheetHeaderList.add(headerId);

        List<String> companyName = new ArrayList<>();
        companyName.add("公司法定名称");
        persionSheetHeaderList.add(companyName);

        List<String> userName = new ArrayList<>();
        userName.add("姓名");
        persionSheetHeaderList.add(userName);

        List<String> userSex = new ArrayList<>();
        userSex.add("性别");
        persionSheetHeaderList.add(userSex);

        List<String> department = new ArrayList<>();
        department.add("部门");
        persionSheetHeaderList.add(department);

        List<String> position = new ArrayList<>();
        position.add("职位");
        persionSheetHeaderList.add(position);

        List<String> phoneNumber = new ArrayList<>();
        phoneNumber.add("电话手机");
        persionSheetHeaderList.add(phoneNumber);

        List<String> qqWx = new ArrayList<>();
        qqWx.add("QQ微信");
        persionSheetHeaderList.add(qqWx);

        List<String> email = new ArrayList<>();
        email.add("邮箱");
        persionSheetHeaderList.add(email);

        List<String> address = new ArrayList<>();
        address.add("地址");
        persionSheetHeaderList.add(address);

        List<String> age = new ArrayList<>();
        age.add("年龄");
        persionSheetHeaderList.add(age);

        List<String> birthday = new ArrayList<>();
        birthday.add("生日");
        persionSheetHeaderList.add(birthday);

        List<String> hobby = new ArrayList<>();
        hobby.add("爱好");
        persionSheetHeaderList.add(hobby);

        List<String> createUser = new ArrayList<>();
        createUser.add("创建人");
        persionSheetHeaderList.add(createUser);

        List<String> createTime = new ArrayList<>();
        createTime.add("创建日期");
        persionSheetHeaderList.add(createTime);

        List<String> updateTime = new ArrayList<>();
        updateTime.add("更新日期");
        persionSheetHeaderList.add(updateTime);

        return persionSheetHeaderList;
    }

    public List<List<String>> getVisitSheetHeaderList() {
        List<List<String>> visitSheetHeaderList = new ArrayList<>();

        List<String> headerId = new ArrayList<>();
        headerId.add("主键ID");
        visitSheetHeaderList.add(headerId);

        List<String> projectName = new ArrayList<>();
        projectName.add("项目名称");
        visitSheetHeaderList.add(projectName);

        List<String> userName = new ArrayList<>();
        userName.add("联系人");
        visitSheetHeaderList.add(userName);

        List<String> visitType = new ArrayList<>();
        visitType.add("拜访类型");
        visitSheetHeaderList.add(visitType);

        List<String> visitText = new ArrayList<>();
        visitText.add("拜访记录");
        visitSheetHeaderList.add(visitText);

        List<String> createUser = new ArrayList<>();
        createUser.add("创建人");
        visitSheetHeaderList.add(createUser);

        List<String> createTime = new ArrayList<>();
        createTime.add("创建日期");
        visitSheetHeaderList.add(createTime);

        List<String> updateTime = new ArrayList<>();
        updateTime.add("更新日期");
        visitSheetHeaderList.add(updateTime);

        return visitSheetHeaderList;
    }
}
