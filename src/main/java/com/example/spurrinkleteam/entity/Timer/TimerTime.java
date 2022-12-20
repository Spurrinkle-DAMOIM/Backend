package com.example.spurrinkleteam.entity.Timer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.json.simple.JSONArray;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "timers-times")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class TimerTime {
    @Id
    private String id;
    private String userId;
    private String day;
    private JSONArray subjects;
    private int total;

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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public JSONArray getSubjects() {
        return subjects;
    }

    public void setSubjects(JSONArray subjects) {
        this.subjects = subjects;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}