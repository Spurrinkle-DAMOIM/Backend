package com.example.spurrinkleteam.entity.Moim.Vote;

import com.example.spurrinkleteam.entity.User;
import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document(collection = "vote")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Vote {

    @Id
    private String id;
    private String meetName;
    private String host;    // 투표 생성자
    private String title;   // 투표제목
    private ArrayList<String> list;    // 투표 항목
    private ArrayList<JSONObject> onList;  // 투표한 사람, 투표 항목
    private String date;
    private Boolean onOff;  // 마감 유무
    private User user;

    @Builder
    public Vote(String id, String meetName, String host, String title,
                ArrayList<String> list, ArrayList<JSONObject> onList, String date, Boolean onOff,
                User user){
        this.id = id;
        this.meetName = meetName;
        this.host = host;
        this.title = title;
        this.list = list;
        this.onList = onList;
        this.date = date;
        this.onOff = onOff;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
