package com.example.spurrinkleteam.dto.Timer;

import org.json.simple.JSONArray;

public class Subject {
    private String subName;
    private JSONArray subArr;
    private String startTime;
    private String endTime;
    private int time;
    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public JSONArray getSubArr() {
        return subArr;
    }

    public void setSubArr(JSONArray subArr) {
        this.subArr = subArr;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
