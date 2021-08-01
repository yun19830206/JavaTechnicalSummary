package com.cloud.experience.jdkknowledge.hashmap;

import com.cloud.experience.jdkknowledge.pojo.AjaxResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Random;

/**
 * 模拟HashMap在高并发场景下导致CPU过高的情况
 * @author ChengYun
 * @date 2019/12/5  Vesion 1.0
 */
@RequestMapping("/hashmap/")
@RestController
public class HashMapCPUHigh {

    private HashMap<String,String> questionMap = new HashMap<>();
    private Random r = new Random();

    @RequestMapping("/cpuhight")
    public AjaxResponse askQuestion(String question){
        question=question+ r.nextInt(30);
        if(null != questionMap.get(question)){
            return AjaxResponse.success(questionMap.get(question));
        }else{
            String answer = "答案"+question;
            questionMap.put(question,answer);
            return AjaxResponse.success(answer);
        }
    }
}
