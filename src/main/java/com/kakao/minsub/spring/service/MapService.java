package com.kakao.minsub.spring.service;

import java.util.Map;

public interface MapService {
   Map register(String type, Map<String, Object> requestBody);
   
   Map registerQuick(Map<String, Object> requestBody);
   
   Map alarmList();
   
   Map now(Map<String, Object> requestBody);
}
