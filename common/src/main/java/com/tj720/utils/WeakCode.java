package com.tj720.utils;

import java.io.*;
import java.util.Properties;

/**
 * @Author: 程荣凯
 * @Date: 2019/1/9 15:56
 */
public class WeakCode {
    public static void main(String[] args) {
        String path = "WeakCode.txt";
        File file = new File(path);
        if (!file.exists()){
            System.out.println(1111);
        }
        try {

            String bb = WeakCode.class.getResource("/").getPath().replaceFirst("/", "");
//            Properties properties = new Properties();
//            properties.load(in);
//            String aa = properties.getProperty("webProject.path");
            System.out.println();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(bb+path));
            while(bufferedReader.readLine()!=null){
                System.out.println(bufferedReader.readLine());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public WeakCode() {
        String is = this.getClass().getResource("/").getPath();
    }
}
