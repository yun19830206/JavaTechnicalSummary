package com.cloud.experience.jdk7gc;

import java.util.Random;

/**
 * Created by ChengYun on 2019/3/5 Version 1.0
 */
public class MainTest {

    private int numberCount = 0;

    public static void main(String[] args) {

        /** -------------Java获得时间戳的方法------------- */
        MainTest mainTest = new MainTest();
        for(int i=0; i<5000; i++){
            mainTest.doSub();
        }

    }

    /**  出题:3位数除1位数，第二位为0或者第三位数为0, 除数不能为零， 要求能被整除   */
    private void doSub() {
        Random random = new Random();
        //1: 第一位数 1-9取一个随机数
        int number1 = 1+random.nextInt(8+1);

        //2：第二位数 0-9 之间的随机数， 第三位数 0-9之间的随机数， 第二位 和 第三位 至少有一位是0
        int number2 = random.nextInt(9+1);
        int number3 = random.nextInt(9+1);
        while (number2 != 0 && number3 != 0) {
            number2 = random.nextInt(9+1);
            number3 = random.nextInt(9+1);
        }

        //3：除数 取 2-9取一个随机数， 上面的三位数 能整除 除数，打印，不能整除，再取一个随机数
        int number4 ;
        int beiChuShu = Integer.parseInt(number1+""+number2+""+number3);
        for(int i = 0 ; i<15; i++){
            number4 = 2+random.nextInt(7+1);
            if( beiChuShu%number4 == 0 ){
                numberCount ++ ;
                if(numberCount%5 != 0){
                    System.out.print(beiChuShu+"÷"+number4+"=        ");
                }else{
                    System.out.println(beiChuShu+"÷"+number4+"=        ");
                }
                break;
            }
        }
    }
}
