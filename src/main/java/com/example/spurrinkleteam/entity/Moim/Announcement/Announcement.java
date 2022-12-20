package com.example.spurrinkleteam.entity.Moim.Announcement;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "announcements")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Announcement {
    @Id
    private String id;
    private String meetName;
    private String title;
    private String userId;
    private String content;
    private String date;

    @Builder
    public Announcement(String id, String title, String meetName, String userId, String content, String date) {
        this.id = id;
        this.title = title;
        this.meetName = meetName;
        this.userId = userId;
        this.content = content;
        this.date = date;
    }
}
