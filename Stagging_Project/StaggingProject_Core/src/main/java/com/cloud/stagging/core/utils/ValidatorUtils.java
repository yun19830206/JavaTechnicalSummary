package com.cloud.stagging.core.utils;

import com.cloud.stagging.core.exception.ParameterValidatorException;

import java.util.List;

/**
 * 进行参数校验静态工具类(一般用在Service层)
 * ->使用静态导入方法的方式使用，提高代码可读性
 * ->此工具类可以根据业务要求自行添加静态方法，以满足更多的业务场景
 * @author ChengYun
 * @date 2020/4/8  Vesion 1.0
 */
public class ValidatorUtils {

    /** 私有构造方法，禁止实例化本类 */
    private ValidatorUtils(){}

    /**
     * 验收字符串长度是否满足条件，不满足抛出ParameterValidatorException异常，满足什么都不做
     * @param inString 待校验的字符串
     * @param min   字符串必须满足的最少字符数
     * @param max   字符串必须满足的最大字符数
     * @param failString  验证失败时提供的错误描述
     */
    public static void validatorStringLength(String inString, int min, int max, String failString){
        if(null == inString){
            throw new ParameterValidatorException(failString);
        }
        if(inString.length() < min || inString.length() > max){
            throw new ParameterValidatorException(failString);
        }
    }

    /**
     * 验证数字枚举是否校验通过。不满足抛出ParameterValidatorException异常，满足什么都不做
     * @param validatedInt 待校验的数字
     * @param enumList  数字枚举值的list
     * @param failString  验证失败时提供的错误描述
     */
    public static void validatorIntEnum(Integer validatedInt, List<Integer> enumList, String failString){
        if(null == validatedInt || enumList.isEmpty()){
            throw new ParameterValidatorException(failString);
        }
        //注意这里不能使用enumList.forEach方式，forEach方式会导致不能return到本验证方法外，而是return到本lambda表达式的匿名方法外
        for (Integer oneInt : enumList){
            if(validatedInt.equals(oneInt)){
                return;
            }
        }
        throw new ParameterValidatorException(failString);
    }

}
