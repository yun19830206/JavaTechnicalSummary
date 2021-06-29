package com.cloud.stagging.module.knowledge.web;

import com.cloud.stagging.module.knowledge.service.IClassService;
import com.cloud.stagging.module.knowledge.service.impl.ClassServiceImpl;
import com.cloud.stagging.pojo.common.AjaxResponse;
import com.cloud.stagging.pojo.knowledge.SpClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 知识分类Controller
 * @author ChengYun
 * @date 2020/3/14  Vesion 1.0
 */
@Slf4j
@RestController
@RequestMapping("/class/")
public class ClassController {

    private final IClassService classService ;

    @Autowired
    public ClassController(IClassService classService) {
        this.classService = classService;
    }

    /**
     * 根据分类ID,获得分类详情DO
     * @param classId 分类主键ID
     * @return 分类详情DO
     */
    @RequestMapping(value = "/v1/get", method = RequestMethod.POST)
    public AjaxResponse getOneClassById(Integer classId){
        SpClass spClass = classService.getOneClassById(classId);
        return AjaxResponse.success(spClass);
    }

    /**
     * 新建分类
     * @param spClass 分类DO
     * @return 分类详情DO
     */
    @RequestMapping(value = "/v1/create", method = RequestMethod.POST)
    public AjaxResponse create(@RequestBody SpClass spClass){
        SpClass addedClass = classService.create(spClass);
        return AjaxResponse.success(addedClass);
    }

}
