package com.example.spurrinkleteam.dto.Moim;

import lombok.Data;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
public class MoimDTO {


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
    private String TodoId;
    private String calId;
    private String timerId;
    private String category;
    private int nowuser;

    public String getMeetName() {
        return meetName;
    }

    public void setMeetName(String meetName) {
        this.meetName = meetName;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public List<String> getUser() {
        return user;
    }

    public void setUser(List<String> user) {
        this.user = user;
    }

    public int getUserCnt() {
        return userCnt;
    }

    public void setUserCnt(int userCnt) {
        this.userCnt = userCnt;
    }

    public String getMeetIntro() {
        return meetIntro;
    }

    public void setMeetIntro(String meetIntro) {
        this.meetIntro = meetIntro;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public List<String> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<String> applicants) {
        this.applicants = applicants;
    }

    public ArrayList<JSONObject> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<JSONObject> photos) {
        this.photos = photos;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getChattingID() {
        return chattingID;
    }

    public void setChattingID(String chattingID) {
        this.chattingID = chattingID;
    }

    public String getTodoId() {
        return TodoId;
    }

    public void setTodoId(String todoId) {
        TodoId = todoId;
    }

    public String getCalId() {
        return calId;
    }

    public void setCalId(String calId) {
        this.calId = calId;
    }

    public String getTimerId() {
        return timerId;
    }

    public void setTimerId(String timerId) {
        this.timerId = timerId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNowuser() {
        return nowuser;
    }

    public void setNowuser(int nowuser) {
        this.nowuser = nowuser;
    }
}