package com.example.spurrinkleteam.entity.TimeTable;

import lombok.*;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "time-tables")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class TimeTable {
    @Id
    private String id;
    private String userId;
    private ArrayList<JSONObject> semesters;

    @Builder
    public TimeTable(String id, String userId, ArrayList<JSONObject> semesters) {
        this.id = id;
        this.userId = userId;
        this.semesters = semesters;
    }
}
