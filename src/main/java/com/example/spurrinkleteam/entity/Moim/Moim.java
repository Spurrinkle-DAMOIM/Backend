package com.example.spurrinkleteam.entity.Moim;

import lombok.*;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Moim")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Moim {

    @Id
    private String meetName;
    private String contestName;
    private List<String> user;
    private int userCnt;
    private String meetIntro;
    private String leader;
    private String uniName;
    private List<String> applicants;
    private ArrayList<JSONObject> photos;
    private String img;
    private String chattingID;
    private String todoId;
    private String calId;
    private String timerId;
    private String category;
    private int nowuser;

    @Builder
    public Moim(String meetName, String contestName, List<String> user, int userCnt, String meetIntro,
                String leader,String uniName, List<String> applicants,
                ArrayList<JSONObject> photos, String img, String chattingID,
                String todoId, String calId, String timerId, String category, int nowuser){

        this.meetName = meetName;
        this.contestName = contestName;
        this.user = user;
        this.userCnt = userCnt;
        this.meetIntro = meetIntro;
        this.leader = leader;
        this.uniName = uniName;
        this.applicants = applicants;
        this.photos = photos;
        this.img = img;
        this.chattingID = chattingID;
        this.todoId = todoId;
        this.calId = calId;
        this.timerId = timerId;
        this.category = category;
        this.nowuser = nowuser;
    }

}