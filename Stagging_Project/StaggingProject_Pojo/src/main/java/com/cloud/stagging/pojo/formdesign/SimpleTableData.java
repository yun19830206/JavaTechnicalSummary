package com.cloud.stagging.pojo.formdesign;

import lombok.Data;

/**
 * 最简单的表单数据展示：ID，展示字段的值
 * @author ChengYun
 * @date 2019/3/21  Vesion 1.0
 */
@Data
public class SimpleTableData {

    /** 主键 */
    private Long id;

    /** 展示字段的值 */
    private String displayValue ;

    SimpleTableData(){}

    public SimpleTableData(Long id, String displayValue) {
        this.id = id;
        this.displayValue = displayValue;
    }
}
