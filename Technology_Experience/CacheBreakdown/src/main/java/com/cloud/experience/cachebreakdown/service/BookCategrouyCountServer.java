package com.cloud.experience.cachebreakdown.service;

import com.cloud.experience.cachebreakdown.dao.NationalStandardMapper;
import com.cloud.experience.cachebreakdown.pojo.BookCategrouyCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 为了复现缓存击穿问题的Server
 * @author ChengYun
 * @date 2019/12/1  Vesion 1.0
 */
@Service
public class BookCategrouyCountServer {

    @Autowired
    private NationalStandardMapper nationalStandardMapper ;

    public List<BookCategrouyCount> getCategrouyCount(){
        return nationalStandardMapper.selectBookCategrouyCountList();
    }
}
