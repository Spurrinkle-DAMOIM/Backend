package com.example.spurrinkleteam.entity.Timer;

import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "timers")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Timer {
    @Id
    private String id;  //아이디
    private String timerUser;
    private ArrayList<JSONObject> timer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimerUser() {
        return timerUser;
    }

    public void setTimerUser(String timerUser) {
        this.timerUser = timerUser;
    }

    public ArrayList<JSONObject> getTimer() {
        return timer;
    }

    public void setTimer(ArrayList<JSONObject> timer) {
        this.timer = timer;
    }

    @Builder
    public Timer(String timerUser, ArrayList<JSONObject> timer){
        this.timerUser = timerUser;
        this.timer = timer;
    }

}