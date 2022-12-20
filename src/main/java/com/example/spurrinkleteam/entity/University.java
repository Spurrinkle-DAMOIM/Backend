package com.example.spurrinkleteam.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "universities")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class University {

    @Id
    private String id;
    private String name;
    private String email;
    private String location;

    @Builder
    public University(String id, String name, String email, String location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.location = location;
    }
}