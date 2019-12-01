package com.cloud.experience.jdkknowledge.datatype;

import java.math.BigDecimal;

/**
 * 讲述基础Java8大基础数据类型 与 浮点型问题
 * @author ChengYun
 * @date 2019/10/23  Vesion 1.0
 */
public class BasicDataTypeAndFloadError {

    public static void main(String[] args) {
        BasicDataTypeAndFloadError basicDataTypeAndFloadError = new BasicDataTypeAndFloadError();

        /** 展示Java8大基础数据类型 */
        basicDataTypeAndFloadError.basicDataType();

        /** 浮点型计算出错问题 */
        basicDataTypeAndFloadError.floadError();
    }

    /** 浮点型计算出错问题 */
    private void floadError() {
        float f1 = 2.0f ;
        float f2 = 1.2f ;
        System.out.println("浮点型计算出错现象:f1(2.0f)-f2(1.2f)="+(f1-f2));

        BigDecimal b1 = new BigDecimal(Float.toString(f1));
        BigDecimal b2 = new BigDecimal(Float.toString(f2));
        /** BigDecimal b1 = new BigDecimal(f1);  这种方式创建BigDecimal还是会有问题 */
        float f3 = b1.subtract(b2).floatValue();
        System.out.println("采用BigDecimal技术:b1(2.0f)-b2(1.2f)="+f3);

    }

    /** 展示Java8大基础数据类型 */
    private void basicDataType() {
        /**
           类型	    默认值	占用存储空间/字节	          范围
         byte	     0	      1 	            -127~128(-2的7次方到2的7次方-1)
         short	     0	      2	                -32768~32767(-2的15次方到2的15次方-1)
         int	     0	      4	                -2147483648~2147483647(-2的31次方到2的31次方-1)
         long	     0	      8	                -9223372036854774808~9223372036854774807(-2的63次方到2的63次方-1)
         float	    0.0	      4	                3.402823e+38 ~ 1.401298e-45（e+38表示是乘以10的38次方，同样，e-45表示乘以10的负45次方）
         double	    0.0	      8	                1.797693e+308~ 4.9000000e-324
         char	     空	      2	                \u0000~\uFFFF
         boolean	false	---------------	    false、true

         ===============Java数据类型===============
         ----基本数据类型
         --------数值型
         ------------整数型:byte、 short、 int、 long
         ------------整数型:float、 double
         --------字符型:char
         --------布尔型:boolean
         ----引用数据类型
         --------类Class
         --------接口interface
         --------数组



         */


    }
}
