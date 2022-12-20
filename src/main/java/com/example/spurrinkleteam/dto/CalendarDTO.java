package com.example.spurrinkleteam.dto;

import org.json.simple.JSONArray;

import java.util.Date;

public class CalendarDTO {
    private String user;
    private JSONArray days;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public JSONArray getDays() {
        return days;
    }

    public void setDays(JSONArray days) {
        this.days = days;
    }

}
