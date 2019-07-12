package com.cloud.aiassistant.formdata.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询某个表单数据结果的 查询DTO
 * @author ChengYun
 * @date 2019/3/10  Vesion 1.0
 */
@Data
public class FormDataQueryDTO {

    /** [必填]待查询 配置表的ID */
    private Long tableId ;

    /** [必填]待查询 配置表名 */
    private String tableName ;

    /** [选填]待查询的数据IDList */
    private List<Long> dataIdList ;

    /** [必填]待查询 userId创建的数据 与 授权给 userId的数据 */
    private Long userId ;

    /** 实际选择的业务查询条件 */
    private List<Map<String,Object>> queryCondition ;

    public List<Long> getDataIdList() {
        if(null == dataIdList){
            dataIdList = new ArrayList<>();
        }
        return dataIdList;
    }

}
