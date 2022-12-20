package com.example.spurrinkleteam.entity;


import lombok.*;
import org.json.simple.JSONArray;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "todos")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Todo {
    private String userId;  //회원 아이디
    private JSONArray lists;    //todolist

    @Builder
    public Todo(String userId, JSONArray lists) {
        this.userId = userId;
        this.lists = lists;
    }
}
