package com.cloud.aiassistant.formdesign.service;

import com.cloud.aiassistant.core.utils.PageHelperUtils;
import com.cloud.aiassistant.formdesign.dao.*;
import com.cloud.aiassistant.formdesign.pojo.FormDataQueryDTO;
import com.cloud.aiassistant.formdesign.pojo.FormDesignVO;
import com.cloud.aiassistant.pojo.common.PageParam;
import com.cloud.aiassistant.pojo.common.PageResult;
import com.cloud.aiassistant.pojo.user.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

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
    public PageResult<List<Map<String,Object>>> getFormDataByFormId(PageParam<FormDataQueryDTO> formDataPageParam) {

        FormDataQueryDTO formDataQueryDTO = formDataPageParam.getDto() ;

        //1：获得表单配置的详细数据后，拼装FormDataQueryDTO
        FormDesignVO formDesignVO = formDesignService.getFormDesignVOById(formDataQueryDTO.getTableId());
        if(null == formDesignVO || null == formDesignVO.getTableConfig() || null == formDesignVO.getTableQueryConfigList()){ return null; }
        formDataQueryDTO.setTableName(formDesignVO.getTableConfig().getEnglishName());
        User user=(User) session.getAttribute(User.SESSION_KEY_USER);
        formDataQueryDTO.setUserId(user.getId());
        //TODO 这里没有做选择的业务查询条件，太复杂，因为业务条件 可能是 大于，可能是等于，也可能是like。 用Map做不到，要封装一个对象：properName,propertyValue,queryType

        PageInfo<List<Map<String,Object>>> tableResultPage = PageHelper.startPage(formDataPageParam.getPageNum(),formDataPageParam.getPageSize())
                .doSelectPageInfo(() -> formDataDao.selectFormDataByFormDataQueryDTO(formDataQueryDTO));

        return PageHelperUtils.getPageResult(tableResultPage) ;
    }



}
