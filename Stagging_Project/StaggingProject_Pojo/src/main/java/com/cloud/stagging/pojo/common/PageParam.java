package com.cloud.stagging.pojo.common;

import lombok.Data;

/**
 * 分页的查询的DTO
 * @author ChengYun
 * @date 2018/12/19 Version 1
 */
@Data
public class PageParam<T> {

    /** 当前待查询页码，默认1 */
    private int pageNum = 1;

    /** 每页数量，默认20 */
    private int pageSize = 20;

    /** 具体查询的条件 */
    private T dto ;
}
