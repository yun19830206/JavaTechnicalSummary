package com.cloud.stagging.core.utils;

import java.security.MessageDigest;

/**
 * 加解密工具类
 * @author ChengYun
 * @date 2019/3/9  Vesion 1.0
 */
public class EncryptionUtils {

    private EncryptionUtils(){}

    /**
     * 对用户插入的参数密码进行MD5加密
     * @param originString
     * @return String
     * @throws Exception
     */
    public static String getMD5EncryptString(String originString) throws Exception{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] originByteArray = originString.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(originByteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static void main(String[] args) {
        try {
            System.out.println(EncryptionUtils.getMD5EncryptString("123123"));
            System.out.println(System.currentTimeMillis());
            Thread.sleep(5000);
            System.out.println(System.currentTimeMillis());
            System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
