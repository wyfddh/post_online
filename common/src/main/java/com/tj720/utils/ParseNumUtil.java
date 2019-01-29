package com.tj720.utils;

/**
 * @author 杜昶
 * @Date: 2018/11/14 15:10
 */
public class ParseNumUtil {

    /**
     * 中文数字转int
     * @param chineseNumber
     * @return
     */
    public static int numberCnToInt(String chineseNumber) {
        int result = 0;
        //存放一个单位的数字如：十万
        int temp = 1;
        //判断是否有chArr
        int count = 0;
        char[] cnArr = new char[]{'一','二','三','四','五','六','七','八','九'};
        char[] chArr = new char[]{'十','百','千','万','亿'};
        for (int i = 0; i < chineseNumber.length(); i++) {
            //判断是否是chArr
            boolean b = true;
            char c = chineseNumber.charAt(i);
            //非单位，即数字
            for (int j = 0; j < cnArr.length; j++) {
                if (c == cnArr[j]) {
                    //添加下一个单位之前，先把上一个单位值添加到结果中
                    if(0 != count){
                        result += temp;
                        temp = 1;
                        count = 0;
                    }
                    // 下标+1，就是对应的值
                    temp = j + 1;
                    b = false;
                    break;
                }
            }
            //单位{'十','百','千','万','亿'}
            if(b){
                for (int j = 0; j < chArr.length; j++) {
                    if (c == chArr[j]) {
                        count++;
                        if (j == 0) {
                            temp *= 10;
                            break;
                        } else if (j == 1) {
                            temp *= 100;
                            break;
                        } else if (j == 2) {
                            temp *= 1000;
                            break;
                        } else if (j == 3) {
                            temp *= 10000;
                            break;
                        } else if (j == 4) {
                            temp *= 100000;
                            break;
                        }
                    }
                }
            }
            //遍历到最后一个字符
            if (i == chineseNumber.length() - 1) {
                result += temp;
            }
        }
        return result;
    }

    /**
     * 罗马数字转int
     * @param s
     * @return
     */
    public static int romanToInt(String s) {
        if (s.length() < 1) {
            return 0;
        }
        int result = 0;
        int current = 0;
        int pre = singleRomanToInt(s.charAt(0));
        int temp = pre;
        for (int i = 1; i < s.length(); i++) {
            current = singleRomanToInt(s.charAt(i));
            if (current == pre) {
                temp += current;
            } else if (current > pre){
                temp = current - temp;
            }
            else if (current < pre){
                result += temp;
                temp = current;
            }
            pre = current;
        }
        result += temp;
        return result;

    }
    /**
     * @param c single Roman
     * @return single number
     */
    public static int singleRomanToInt(char c){
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }
    /**
     * @param n - input single int
     * @param nth must start from 1; 1 <= nth <= 4
     * @return String single Roman
     */
    public static String singleDigitToRoman(int n, int nth){
        if (n == 0) {
            return "";
        }
        nth = 2 * nth - 1;  // nth must start from 1
        char singleRoman[] = {'I','V','X','L','C','D','M','Z','E'};  // never use 'Z' & 'E'
        StringBuilder rsb = new StringBuilder("");
        if (n <= 3) {
            for (int i = 0; i < n; i++) {
                rsb.append(singleRoman[nth-1]);
            }
            return rsb.toString();
        }
        if (n == 4) {
            rsb.append(singleRoman[nth-1]);
            rsb.append(singleRoman[nth]);
            return rsb.toString();
        }
        if (n == 5) {
            return singleRoman[nth] + "";
        }
        if (n >= 6 && n <= 8) {
            rsb.append(singleRoman[nth]);
            for (int i = 0; i < (n - 5); i++) {
                rsb.append(singleRoman[nth-1]);
            }
            return rsb.toString();
        }
        if (n == 9) {
            rsb.append(singleRoman[nth-1]);
            rsb.append(singleRoman[nth+1]);
            return rsb.toString();
        }
        return "ERROR!!!";
    }
    /**
     * @param num - input number within range 1 ~ 3999
     * @return String Roman number
     */
    public static String intToRoman(int num) {
        if (num < 1 || num > 3999) {
            return "ERROR input number is 1 ~ 3999";
        }
        int temp = num;
        String singleRoman[] = {"","","",""};
        StringBuilder result = new StringBuilder("");
        int digits = 0;    // 1 ~ 4
        while (temp != 0){
            temp = temp/10;
            digits++;
        }
        temp = num;
        int[] singleInt = new int[digits];
        for (int i = 0; i < digits; i++) {
            singleInt[i]  = temp%10;
            singleRoman[i] = singleDigitToRoman(temp%10,i+1);
            temp /= 10;
        }
        for (int i = digits-1; i >= 0; i--) {
            result.append(singleRoman[i]);
        }
        return result.toString();
    }
}
