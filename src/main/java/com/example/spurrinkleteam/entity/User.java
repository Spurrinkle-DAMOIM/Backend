package com.example.spurrinkleteam.entity;

import lombok.*;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document(collection = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class User {
    @Id
    private String id;  //아이디
    private String pw;  //비밀번호
    private String name;    //이름
    private String email;   //이메일
    private String nickname;    //닉네임
    private String tel; //전화번호
    private String gender;  //성별
    private String uniName; //대학교 이름
    private String img; //프로필 이미지
    private Date date;
    private String[] subject;
    @Builder
    public User(String id, String pw, String name, String email, String nickname, String tel, String gender,String uniName, String img, String[] subject) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.tel = tel;
        this.gender = gender;
        this.uniName = uniName;
        this.img = img;
        this.subject = subject;
    }
    @Builder
    public User(String id, String pw, String name, String email, String nickname, String tel, String gender,String uniName, String img) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.tel = tel;
        this.gender = gender;
        this.uniName = uniName;
        this.img = img;
    }

}