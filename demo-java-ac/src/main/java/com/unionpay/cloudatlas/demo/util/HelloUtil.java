package com.lxt.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class HelloUtil {
    private static final Logger log = LoggerFactory.getLogger(HelloUtil.class);

    public static String sayHello() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("greeting.txt")));
        String greeting = "";
        try {
            greeting = bufferedReader.readLine();
        } catch (IOException e) {
            log.error("read hello line error",e);
        }
        return greeting;
    }

    public static String showContact() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("contact.csv")));
        String newLine = "";
        StringBuilder wholeText = new StringBuilder();
        int num = 0;
        try {
            while((newLine = bufferedReader.readLine())!=null){
                String[] info = newLine.split(",");
                wholeText.append("######## "+(++num)+"\n"
                             +"姓名："+info[0]+"\n"
                             +"电话："+info[1]+"\n\n"
                );
            }
        } catch (IOException e) {
            log.error("read contact book error",e);
        }
        return wholeText.toString();
    }
}
