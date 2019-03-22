package com.cloud.aiassistant.formdesign.pojo;

import com.cloud.aiassistant.pojo.formdesign.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 一个表单设计配置展示VO：表单、表单字段、表单查询、表单结果展示
 * @author ChengYun
 * @date $time $date Vesion 1.0 Version 1
 */
@Data
public class FormDesignVO {

    /** 表单配置数据 */
    private TableConfig tableConfig ;

    /** 本表单的所有配置属性字段 */
    private List<TableColumnConfig> tableColumnConfigList ;

    /** 本表单的查询条件配置集合 */
    private List<TableQueryConfig> tableQueryConfigList ;

    /** 本表单的查询结果展示列配置集合 */
    private List<TableDisplayConfig> tableDisplayConfigList ;

    /** 本表单外键引用的 字段 和 valueList 给查询 和 新建数据用 */
    private Map<String,List<SimpleTableData>> foreignKeyValues ;

}
