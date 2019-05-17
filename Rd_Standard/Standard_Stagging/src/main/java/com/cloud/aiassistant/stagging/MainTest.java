package com.cloud.aiassistant.stagging;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cloud.aiassistant.stagging.pojo.AjaxResponse;
import com.cloud.aiassistant.stagging.pojo.User;

/**
 * @author ChengYun
 * @date 2019/4/25  Vesion 1.0
 */
public class MainTest {


    public static void main(String[] args) {
        MainTest mainTest = new MainTest();
        mainTest.testJson2String();
    }

    private void testJson2String() {
        User userAjax = new User("程云",36);
        AjaxResponse<User> successUserAjax = AjaxResponse.success(userAjax, "创建成功");
        System.out.println(JSONObject.toJSONString(successUserAjax));
        System.out.println(JSONObject.toJSONString(successUserAjax, SerializerFeature.WriteNullStringAsEmpty));
        System.out.println(JSONObject.toJSONString(successUserAjax, SerializerFeature.WriteMapNullValue));
        System.out.println(JSONObject.toJSONString(successUserAjax, SerializerFeature.WriteNonStringKeyAsString));
        System.out.println(JSONObject.toJSONString(successUserAjax, SerializerFeature.WriteNonStringValueAsString));



    }



}
