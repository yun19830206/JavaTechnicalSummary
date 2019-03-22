package com.cloud.aiassistant.utils;

import com.cloud.aiassistant.formdesign.pojo.FormDesignVO;
import com.cloud.aiassistant.pojo.formdesign.*;

import java.util.ArrayList;
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

    /** 根据一个表单所有字段配置的List，获得本表单的被外键引用的展示字段 */
    public static String getDisplayColumnNameFromColumnConfigList(List<TableColumnConfig> tableColumnConfigList) {
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

    /** 根据展示字段 与 外键表的全部数据， 获得简要数据格式 */
    public static List<SimpleTableData> getTableSimpleDataList(List<Map<String, Object>> columnValueList, String parentDisplayColumnName) {
        if(null == parentDisplayColumnName || parentDisplayColumnName.length()<1) {
            return null ;
        }
        if(null == columnValueList || columnValueList.size()<1){
            return null ;
        }

        List<SimpleTableData> simpleTableDataList = new ArrayList<>();

        columnValueList.forEach(map ->{
            Long id = Long.parseLong(map.get("id").toString());
            String displayValue = (String) map.get(parentDisplayColumnName);
            simpleTableDataList.add(new SimpleTableData(id,displayValue));
        });

        return simpleTableDataList ;
    }
}
