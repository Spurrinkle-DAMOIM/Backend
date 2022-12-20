package com.example.spurrinkleteam.entity;

import lombok.*;
import org.json.simple.JSONArray;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "calendars")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Calendar{
    @Id
    private String user;
    private JSONArray days;

    @Builder
    public Calendar(String user, JSONArray days) {
        this.user = user;
        this.days = days;
    }
}
