package com.example.spurrinkleteam.entity.TimeTable;

import lombok.*;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "time-tables-days")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class TimeTableDay {
    @Id
    private String id;
    private String day;
    private String start;
    private String end;

    @Builder
    public TimeTableDay(String id, String day, String start, String end) {
        this.id = id;
        this.day = day;
        this.start = start;
        this.end = end;
    }
}
