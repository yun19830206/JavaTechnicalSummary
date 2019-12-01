package com.cloud.stagging.pojo.common;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageSerializable;
import lombok.Data;

/**
 * 分页查询返回结果类
 * @author ChengYun
 * @date 2018/12/19 Version 1
 */
@Data
public class PageResult<T> extends PageSerializable<T>{

    private static final long serialVersionUID = -8706691129362904729L;

    private int pageNum ;
    private int pageSize ;
    private int pages ;

    public PageResult(PageInfo<T> pageinfo) {
        this.total = pageinfo.getTotal();
        this.list = pageinfo.getList();
        this.pageNum = pageinfo.getPageNum();
        this.pageSize = pageinfo.getPageSize();
        this.pages = pageinfo.getPages();
    };
}
