package com.ctt.web;

/**
 * @author HHF
 * @Description
 * @create 2020-07-26 下午 5:59
 */
public class Test {

    public String addStrings(String num1, String num2) {
        int len1 = num1.length();
        int len2 = num2.length();
        StringBuffer sb = new StringBuffer();
        int c = 0;
        while (len1 > 0 || len2 > 0){
            int i = len1 > 0 ? num1.charAt(len1 - 1) - '0' : 0;
            int j = len2 > 0 ? num1.charAt(len2 - 1) - '0' : 0;
            sb.append((i + j + c) % 10);
            c = (i + j + c) / 10;
            len1 -- ;
            len2 -- ;
        }
        if(c > 0){
            sb.append(c);
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        int a = 2;
        int b = 3;
        System.out.println(a | b);
    }

}
