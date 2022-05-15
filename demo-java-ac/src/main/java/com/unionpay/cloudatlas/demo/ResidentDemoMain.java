package com.lxt.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lxt.demo.util.HelloUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class ResidentDemoMain {
    private static final Logger log = LoggerFactory.getLogger(ResidentDemoMain.class);

    public static void main(String[] args) {
        new ResidentDemoMain().start();
    }

    //该成员用于action示例，非必须
    private String message = "盘古开天地";

    //AC:start:调这里, 用于起应用, 返回boolean表示启动成功/失败
    //Since AC 3.0.0:该方法可以是阻塞式的
    public boolean start() {
        String env = System.getenv("ENV_TYPE");
        log.info("current ENV_TYPE: "+env);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //原先想写在main方法里的内容，转而写在这里
                while(true) {
                    log.info(HelloUtil.sayHello());
                    log.info("Now I believe in : "+message);

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
    //AC:audit:调这里, 用于审计应用状态, 返回Map<String, String>将状态显示在ACConsole、ACMonitor上
    public Map<String, String> audit() {
        Map<String, String> map = new HashMap();

        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(currentTime);
        map.put("appName", "ResidentDemoMain");
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
    //AC:isSuicide:每次audit时调这里, 用于判断应用是否可以自杀了, 返回boolean表示自杀/不自杀
    public boolean isSuicide() {
        return false;
    }

    //这里返回值可以是Boolean/String/Map<String,String>, 都将传回调用方
    public String action(String para) {
        try{
            log.info("I heard a Sine from above: "+para);
            message = para;
            return "生效了哦";
        } catch (Exception e) {
            return "有问题啊";
        }
    }
}
