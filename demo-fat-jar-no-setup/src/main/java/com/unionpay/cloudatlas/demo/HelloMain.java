package com.lxt.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class HelloMain {
    private static final Logger log = LoggerFactory.getLogger(HelloMain.class);

    public static void main(String[] args) {
        new HelloMain().start();
    }
    //AC:start:调这里, 用于起应用, 返回boolean表示启动成功/失败
    public boolean start() {
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
        return true;
    }
    //AC:check:调这里, 用于检验应用状态, 返回boolean表示通过/未通过检验
    public boolean check() {
        return true;
    }
    //AC:audit:调这里, 用于审计应用状态, 返回Map<String, String>将状态显示在ACConsole上
    public Map<String, String> audit() {
        Map<String, String> map = new HashMap();

        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(currentTime);
        map.put("appName", "HelloDemo");
        map.put("ts", formatter.format(date) + "");
        map.put("tps", "5");
        map.put("eff", "6");
        map.put("sucCnt", "7");
        map.put("filCnt", "8");
        map.put("appId", "100001");
        map.put("other", "test");
        return map;
    }
    //AC:stop:调这里, 用于停应用, 返回boolean表示停止成功/失败
    public boolean stop() {
        try{
            //如需释放资源、收尾工作等，写在这里
        }
        catch (Exception e){
            log.error("stop with failure",e);
            return false;
        }
        return true;
    }
}
