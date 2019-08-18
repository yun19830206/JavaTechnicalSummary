package com.cloud.aiassistant.business.crm.pojo;

import com.alibaba.excel.metadata.Sheet;
import lombok.Data;

import java.util.List;

/**
 * CRM4张客服表公用的 EasyExcel数据VO，包括 sheet与Data
 * @author ChengYun
 * @date 2019/6/5  Vesion 1.0
 */
@Data
public class CrmCustSheet {

    /** 一个Sheet页的head，里面放的List<List<String>> 里面一层只有一个数据 */
    Sheet sheetHead ;

    /** 一个Sheet页的Data */
    List<List<Object>> sheetData ;

}
