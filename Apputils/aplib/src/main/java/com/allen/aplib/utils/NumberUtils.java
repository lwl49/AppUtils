package com.allen.aplib.utils;

/**
 * @author Allen
 * @date 2020/2/14 10:29
 * @describe
 */
public class NumberUtils {

    /**
     * 16进制转换10进制
     */
    public static int hexToInt(String hex) {
        int sum = 0;
        for (int i = 0; i < hex.length(); i++) {
            int m = hex.charAt(i);//将输入的十六进制字符串转化为单个字符
            int num = m >= 'A' ? m - 'A' + 10 : m - '0';//将字符对应的ASCII值转为数值
            sum += Math.pow(16, hex.length() - 1 - i) * num;
        }
        System.out.println(sum);
        return sum;
    }

    /**
     * 10进制转换16进制
     */
    public static String intToHex(int n) {
        //StringBuffer s = new StringBuffer();
        StringBuilder sb = new StringBuilder(8);
        String a;
        char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        while (n != 0) {
            sb = sb.append(b[n % 16]);
            n = n / 16;
        }
        a = sb.reverse().toString();
        return a;
    }
}
