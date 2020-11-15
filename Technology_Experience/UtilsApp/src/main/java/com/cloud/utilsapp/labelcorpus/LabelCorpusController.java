package com.cloud.utilsapp.labelcorpus;

import com.cloud.utilsapp.common.AjaxResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标签与语料 关系Controller
 * @author ChengYun
 * @date 2020/10/27  Vesion 1.0
 */
@RestController
@RequestMapping("/labelcorpus")
public class LabelCorpusController {
    
    private final ILabelCorpusService labelCorpusService ;

    public LabelCorpusController(ILabelCorpusService labelCorpusService) {
        this.labelCorpusService = labelCorpusService;
    }

    @RequestMapping(value = "/create")
    public AjaxResponse<LabelCorpus> createLabelCorpusRelation(@RequestBody LabelCorpus labelCorpus){
        labelCorpusService.createLabelCorpusRelation(labelCorpus);
        return AjaxResponse.success(labelCorpus);
    }

}
