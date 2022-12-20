package com.example.spurrinkleteam.entity.TimeTable;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "time-tables-subjects")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class TimeTableSubject {
    @Id
    private String id;
    private String subName;
    private String professor;
    private String place;
    private ArrayList<String> days;

    @Builder
    public TimeTableSubject(String id, String subName, String professor, String place, ArrayList<String> days) {
        this.id = id;
        this.subName = subName;
        this.professor = professor;
        this.place = place;
        this.days = days;
    }
}
