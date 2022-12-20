package com.example.spurrinkleteam.dto.Moim.Vote;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class VoteDTO {
    private String id;
    private String meetName;
    private String host;    // 투표 생성자
    private String title;   // 투표제목
    private ArrayList<String> list;    // 투표 항목
    private ArrayList<JSONObject> onList;  // 투표한 사람, 투표 항목
    private String date;
    private Boolean onOff;  // 마감 유무

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeetName() {
        return meetName;
    }

    public void setMeetName(String meetName) {
        this.meetName = meetName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public ArrayList<JSONObject> getOnList() {
        return onList;
    }

    public void setOnList(ArrayList<JSONObject> onList) {
        this.onList = onList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getOnOff() {
        return onOff;
    }

    public void setOnOff(Boolean onOff) {
        this.onOff = onOff;
    }
}
