package com.example.spurrinkleteam.dto.TimeTable;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class TimeTableDTO {
    private String id;
    private String userId;
    private ArrayList<JSONObject> semesters;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<JSONObject> getSemesters() {
        return semesters;
    }

    public void setSemesters(ArrayList<JSONObject> semesters) {
        this.semesters = semesters;
    }
}
