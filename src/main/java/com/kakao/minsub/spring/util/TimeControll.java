package com.kakao.minsub.spring.util;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by kakao on 2017. 7. 24..
 */
public class TimeControll {

    private static HashMap<String, Long> map = new HashMap<>();


    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void startCheckPoint(String key) {
        map.put(key, System.currentTimeMillis());
    }

    public static String endCheckPoint(String key) {
        if (!map.containsKey(key)) {
            return key + ": No start check Point!!";
        }
        return key + " 수행시간 " + (System.currentTimeMillis() - map.get(key));
    }
}
