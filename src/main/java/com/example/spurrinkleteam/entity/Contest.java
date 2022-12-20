package com.example.spurrinkleteam.entity;

import lombok.*;
import org.jsoup.select.Elements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;


@Document(collection = "contest")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Contest {

    @Id
    private String id;
    private String title;  //제목
    private String img; //사진
    private String supervise;    //주최자
    private String sponsor; //주관
    private String enter;//응모분야
    private String target;  //응모대상
    private String hostType;    //주최사 유형
    private String scale;   //시상 규모
    private String bonus;   //특전
    private String benefit; //1등 혜택
    private String body; //내용
    private Date startDate;
    private Date endDate;
    private String link;

    @Builder
    public Contest(String id, String title, String img, String supervise, String sponsor, String enter, String target,
                   String hostType, String scale, String bonus, String benefit, String body,
                   Date startDate, Date endDate,String link){
        this.id = id;
        this.title = title;
        this.img = img;
        this.supervise = supervise;
        this.sponsor = sponsor;
        this.enter = enter;
        this.target = target;
        this.hostType = hostType;
        this.scale = scale;
        this.bonus = bonus;
        this.benefit = benefit;
        this.body = body;
        this.startDate = startDate;
        this.endDate = endDate;
        this.link = link;
    }


}
