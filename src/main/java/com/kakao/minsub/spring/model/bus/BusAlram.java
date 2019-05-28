package com.kakao.minsub.spring.model.bus;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusAlram {
    String busStopId;
    String busNumber;
    int timer;
    int startTime;
    LocalTime startLocalTime;
    
    @Builder
    public BusAlram(String busStopId, String busNumber, int timer, int startTime, LocalTime startLocalTime) {
        this.busStopId = busStopId;
        this.busNumber = busNumber;
        this.timer = timer;
        this.startTime = startTime;
        this.startLocalTime = startLocalTime;
    }
}
