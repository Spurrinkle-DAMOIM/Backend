package com.example.spurrinkleteam.service.Timer;

import com.example.spurrinkleteam.dto.Timer.Subject;
import com.example.spurrinkleteam.entity.Timer.TimerTime;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public interface TimerService {
    abstract ArrayList<Object> userCheck(JsonNode user);
    abstract ArrayList<String> addSub(JsonNode sub);
    abstract ArrayList<String> modiSub(JsonNode sub);
    abstract ArrayList<String> delSub(JsonNode sub);
    abstract String day(TimerTime timer, String id);   // 날짜에 따라서 timers-times ID를 times의 timer에 업데이트
    abstract String time(Subject obj, String id);   // 공부한 과목과 시간을 날짜에 따라 데이터 업데이트(timers-times)
    abstract TimerTime userTimeCheck(String id, String day);
    abstract void createTimer(String id);
}
