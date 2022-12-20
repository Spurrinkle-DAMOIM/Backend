package com.example.spurrinkleteam.dto;

import org.json.simple.JSONArray;

public class TodoDTO {
    private String userId;  //회원 아이디
    private JSONArray lists;    //todolist

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public JSONArray getLists() {
        return lists;
    }

    public void setLists(JSONArray lists) {
        this.lists = lists;
    }
}
