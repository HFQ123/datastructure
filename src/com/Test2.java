package com;

import java.io.IOException;

/**
 * @Created by hfq on 2020/4/12 18:54
 * @used to:
 * @return:
 */
public class Test2 {
    public static void test1() {

        System.out.println(NumberOf1Between1AndN_Solution(55));
    }
    public static int NumberOf1Between1AndN_Solution(int n) {
        String str;
        int count = 0;
        for(int i = 1 ; i<=n ;i++){
            str = i + "";
            if(str.contains("1"))
            {
                System.out.printf(str+",");
                count++;
            }
        }
        System.out.println();
        return count;
    }

    public static void main(String[] args) {
        test1();
    }

}
