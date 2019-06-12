package com.kakao.minsub.spring.model.bus;

import java.util.List;

public class KakaoMapBusStopResponse {
    public String id;
    public String name;
    public Integer x;
    public Integer y;
    public List<KakaoMapBusLine> lines;
}
