package com.lxt.demo;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class HelloMain {
    private static final Logger log = LoggerFactory.getLogger(HelloMain.class);

    public static void main(String[] args) {
        String env = System.getenv("ENV_TYPE");
        log.info("current ENV_TYPE: "+env);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //原先想写在main方法里的内容，转而写在这里
                while(true) {
                    log.info(HelloUtil.sayHello());
                    log.info(HelloUtil.showContact());
                    //HelloUtil.showContact();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        log.error("sleep interrupted",e);
                    }
                }
            }
        }).start();
    }
}
