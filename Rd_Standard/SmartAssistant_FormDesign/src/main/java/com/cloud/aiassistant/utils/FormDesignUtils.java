package com.cloud.aiassistant.utils;

import com.cloud.aiassistant.formdesign.pojo.FormDesignVO;
import com.cloud.aiassistant.pojo.formdesign.TableColumnConfig;
import com.cloud.aiassistant.pojo.formdesign.TableConfig;
import com.cloud.aiassistant.pojo.formdesign.TableDisplayConfig;
import com.cloud.aiassistant.pojo.formdesign.TableQueryConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表单设计工具类
 * @author ChengYun
 * @date $time $date Vesion 1.0 Version 1
 */
public class FormDesignUtils {

    private FormDesignUtils(){}

    /**
     * 根据一个资源的表单设计、表单字段设计List、表单查询条件设计List、表单展示结果List 组装成表单设计大VO
     * @param tableConfig  表单设计
     * @param tableColumnConfigList 表单字段设计List
     * @param tableQueryConfigList  表单查询条件设计List
     * @param tableDisplayConfigList 表单展示结果List
     * @return 组装成表单设计大VO
     */
    public static FormDesignVO transTableDesignConfigToFormDesignVO(TableConfig tableConfig, List<TableColumnConfig> tableColumnConfigList,
                                                                    List<TableQueryConfig> tableQueryConfigList, List<TableDisplayConfig> tableDisplayConfigList){
        FormDesignVO formDesignVO = new FormDesignVO();
        formDesignVO.setTableConfig(tableConfig);
        formDesignVO.setTableColumnConfigList(tableColumnConfigList);

        //转换表单字段配置存储方法，便于表单展示结果 与 查询条件 引用
        Map<Long,TableColumnConfig> tableColumnConfigMap = new HashMap<>();
        if(null != tableColumnConfigList && tableColumnConfigList.size()>0){
            tableColumnConfigList.forEach(tableColumnConfig -> tableColumnConfigMap.put(tableColumnConfig.getId(),tableColumnConfig));
        }

        if(null != tableQueryConfigList && tableQueryConfigList.size()>0){
            tableQueryConfigList.forEach(tableQueryConfig -> tableQueryConfig.setTableColumnConfig(tableColumnConfigMap.get(tableQueryConfig.getTableColumn())));
        }

        if(null != tableDisplayConfigList && tableDisplayConfigList.size()>0){
            tableDisplayConfigList.forEach(tableDisplayConfig -> tableDisplayConfig.setTableColumnConfig(tableColumnConfigMap.get(tableDisplayConfig.getTableColumn())));
        }

        formDesignVO.setTableDisplayConfigList(tableDisplayConfigList);
        formDesignVO.setTableQueryConfigList(tableQueryConfigList);

        return formDesignVO ;
    }
}
