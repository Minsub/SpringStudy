package com.kakao.minsub.spring.service.impl;

import com.google.common.collect.Maps;
import com.kakao.minsub.spring.model.bus.BusAlram;
import com.kakao.minsub.spring.model.bus.KakaoMapBusLine;
import com.kakao.minsub.spring.model.bus.KakaoMapBusStopResponse;
import com.kakao.minsub.spring.service.MapService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class MapServiceImpl implements MapService {
    private final Logger logger = LoggerFactory.getLogger(MapServiceImpl.class);
    
    private RestTemplate restTemplate;
    
    private static Map<String, BusAlram> registeredBusAlrams = Maps.newHashMap();
    
    private final static int TERM_MIN = 2;
    private final static String BUS_NUMBER = "390";
    
    public MapServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    
    @Scheduled(cron="*/20 * * * * *")
    public void scheduleTest() {
        if (!registeredBusAlrams.isEmpty()) {
            logger.debug("run schedule!!");
            registeredBusAlrams.keySet().forEach(type -> {
                final BusAlram busAlram = registeredBusAlrams.get(type);
                if (LocalTime.now().isAfter(busAlram.getStartLocalTime())) {
                    KakaoMapBusStopResponse busStopInfo = this.getBusStopInfo(busAlram.getBusStopId());
    
                    KakaoMapBusLine busLine = busStopInfo.lines.stream()
                            .filter(line -> line.name.equals(busAlram.getBusNumber()))
                            .findFirst().get();
    
                    if (busLine.arrival.arrivalTime > 0) {
                        int arrivalTime1 = busLine.arrival.arrivalTime / 60;
                        if (arrivalTime1 <= busAlram.getTimer() && arrivalTime1 >= busAlram.getTimer() - TERM_MIN) {
                            try {
                                String typeName = type.equals("gowork") ? "출근" : "퇴근";
                                StringBuilder sb = new StringBuilder();
                                sb.append(String.format("\uD83D\uDE80%s 버스(%s번)가 곧 도착합니다.\n", typeName, busAlram.getBusNumber()));
                                sb.append(generateBusArrivalInfo(busAlram.getBusStopId(), busLine));
                                this.sendMessage(sb.toString());
                            } catch(Exception e) {
                                logger.error("error in batch", e);
                                this.sendMessage("오류로 인해 알림이 중단되었습니다!");
                            } finally {
                                registeredBusAlrams.remove(type);
                            }
                        }
                    }
                }
            });
        }
    }

    
    @Override
    public Map register(String type, Map<String, Object> requestBody) {
        try {
            Map<String, Object> action = (Map<String, Object>)requestBody.get("action");
            Map<String, Object> params = (Map<String, Object>)action.get("detailParams");
    
            String startTimeValue = (String)((Map<String, Object>)params.get("startTime")).get("origin");
            int startTime = LocalTime.parse(startTimeValue, DateTimeFormatter.ofPattern("HH:mm:ss")).getMinute();
            int timer = Integer.valueOf(((Map<String, Object>)params.get("timer")).get("origin").toString().replace("분", ""));
    
            return register(type, startTime, timer);
        } catch (Exception e) {
            logger.error("ERROR!!!", e);
            return createSimpleText("등록 실패!!");
        }
    }
    
    @Override
    public Map registerQuick(Map<String, Object> requestBody) {
        try {
            String type = this.getStringByContexts(requestBody, "register_quick", "type");
            int timer = Integer.valueOf(this.getParamStringValue(requestBody, "time_set").replace("분", ""));
            return register(type, 0, timer);
        } catch (Exception e) {
            logger.error("error in register quick", e);
            return createSimpleText("등록 실패!!");
        }
    }
    
    @Override
    public Map alarmList() {
        if (registeredBusAlrams.isEmpty()) {
            return createSimpleText("등록된 버스 알림이 없습니다.");
        }
        String message = registeredBusAlrams.keySet().stream()
                .map(type -> {
                    final String typeName = type.equals("gowork") ? "출근" : "퇴근";
                    return "\uD83D\uDE80"+typeName + " 버스 타이머\n" + this.registerInfo(registeredBusAlrams.get(type))+"\n";
                })
                .reduce((a,b) -> a.concat(b))
                .orElse("등록된 버스 알림이 없습니다");
        return createSimpleText(message);
    }
    
    @Override
    public Map now(Map<String, Object> requestBody) {
        try {
            String type = this.getParamStringValue(requestBody, "type");
            final String typeName = type.equals("gowork") ? "출근" : "퇴근";
            final String busStopId = getBusStopId(type);
            KakaoMapBusStopResponse busStopInfo = this.getBusStopInfo(busStopId);
            KakaoMapBusLine busLine = busStopInfo.lines.stream()
                    .filter(line -> line.name.equals(BUS_NUMBER))
                    .findFirst().get();
    
            String message = String.format("\uD83D\uDE80%s 버스(%s번) 도착시간 현재정보!\n", typeName, BUS_NUMBER)
                    + this.generateBusArrivalInfo(busStopId, busLine);
            return createSimpleText(message);
        } catch (Exception e) {
            logger.error("error in now", e);
            return createSimpleText("오류가 발생했습니다.");
        }
    }
    
    private Map register(String type, int startTime, int timer) {
        try {
            final String typeName = type.equals("gowork") ? "출근" : "퇴근";
            final String busStopId = getBusStopId(type);
            
            LocalTime startLocalTime = LocalTime.now().plusMinutes(startTime);
            BusAlram alram = BusAlram.builder()
                    .busStopId(busStopId)
                    .busNumber(BUS_NUMBER)
                    .startTime(startTime)
                    .startLocalTime(startLocalTime)
                    .timer(timer)
                    .build();
            
            if (registeredBusAlrams.containsKey(type)) {
                registeredBusAlrams.put(type, alram);
                return createSimpleText(typeName + " 버스 타이머 등록정보가 수정되었습니다.\n" + this.registerInfo(alram));
            } else {
                registeredBusAlrams.put(type, alram);
                return createSimpleText(typeName + " 버스 타이머가 등록되었습니다.\n" + this.registerInfo(alram));
            }
        } catch (Exception e) {
            logger.error("ERROR!!!", e);
            return createSimpleText("등록 실패!!");
        }
    }
    
    private void sendMessage(String message) {
        final Map<String, Object> params = Maps.newHashMap();
        params.put("to", 7371);
        params.put("msg", message);
        final HttpEntity<Map<String, Object>> entity = new HttpEntity<>(params, buildHeaders());
        restTemplate.exchange("http://api.noti.daumkakao.io/send/group/kakaotalk", HttpMethod.POST, entity, Map.class);
    }
    
    private KakaoMapBusStopResponse getBusStopInfo(String busStopId) {
        HttpEntity<String> entity = new HttpEntity<>(buildHeaders());
        ResponseEntity<KakaoMapBusStopResponse> result = restTemplate.exchange("https://map.kakao.com/bus/stop.json?busstopid="+busStopId, HttpMethod.GET, entity, KakaoMapBusStopResponse.class);
        return result.getBody();
    }
    
    private String registerInfo(BusAlram busAlram) {
        String startTime = busAlram.getStartLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        return "알림시작 시간: " + startTime + "\n" +
                "타이머 시간: " + busAlram.getTimer() + "분 후 알림";
    }
    
    private String createNotiMessage(BusAlram busAlram) {
        return "";
    }
    
    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return httpHeaders;
    }
    
    private Map createSimpleText(String text) {
        Map<String, Object> output = Maps.newHashMap();
        Map<String, Object> outputBody = Maps.newHashMap();
        outputBody.put("text", text);
        output.put("simpleText", outputBody);
        return output;
    }
    
    private String generateBusArrivalInfo(String busStopId, KakaoMapBusLine busLine) {
        int arrivalTime1 = busLine.arrival.arrivalTime / 60;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("(기준시간: %s)\n", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))));
        sb.append(String.format(" 1st 버스: %s (%s개 남음)\n", getArrivalTimeText(arrivalTime1), busLine.arrival.busStopCount));
        if (busLine.arrival.arrivalTime2 > 0) {
            int arrival2 = busLine.arrival.arrivalTime2 / 60;
            sb.append(String.format(" 2nd 버스: %s (%s개 남음)\n", getArrivalTimeText(arrival2), busLine.arrival.busStopCount2));
        } else {
            sb.append(" 2nd 버스: 정보없음\n");
        }
        sb.append("https://m.map.kakao.com/actions/busStationInfo?busStopId="+busStopId);
        return sb.toString();
    }
    
    private String getArrivalTimeText(int arrivalTime) {
        if (arrivalTime == 0) {
            return "곧 도착";
        }
        return String.format("%s분 후 도착", arrivalTime);
    }
    
    private String getBusStopId(String type) {
        return type.equals("gowork") ? "BS73316" : "BS267796";
    }
    
    private String getParamStringValue(Map<String, Object> body, String key) {
        Map<String, Object> action = (Map<String, Object>)body.get("action");
        Map<String, Object> params = (Map<String, Object>)action.get("detailParams");
        return (String)((Map<String, Object>)params.get(key)).get("value");
    }
    
    private String getStringByContexts(Map<String, Object> body, String contextName, String key) {
        List<Map<String, Object>> contexts = (List<Map<String, Object>>)body.get("contexts");
        if (CollectionUtils.isEmpty(contexts)) {
            return "";
        }
        return contexts.stream()
                .filter(c -> c.get("name").toString().equals(contextName))
                .map(c -> {
                    Map<String, Object> params = (Map<String, Object>)c.get("params");
                    return (String)((Map<String, Object>)params.get(key)).get("resolvedValue");
                })
                .findFirst().get();
    }
}
