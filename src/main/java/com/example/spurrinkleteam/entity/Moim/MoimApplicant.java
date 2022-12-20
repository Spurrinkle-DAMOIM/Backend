package com.example.spurrinkleteam.entity.Moim;


import lombok.*;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "moim-applicants")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class MoimApplicant {
    @Id
    private String id;
    private String meetName;
    private String userId;
    private String content;
    private String date;

    @Builder
    public MoimApplicant(String id, String meetName, String userId, String content, String date) {
        this.id = id;
        this.userId = userId;
        this.meetName = meetName;
        this.content = content;
        this.date = date;
    }
}
