package com.example.spurrinkleteam.dto.Board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class reReply {
    private int id;
    private String author;
    private String content;
    private String ReplyID;
    private int likes;
    private String[] likesUser;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date = new Date();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String[] getLikesUser() {
        return likesUser;
    }

    public void setLikesUser(String[] likesUser) {
        this.likesUser = likesUser;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReplyID() {
        return ReplyID;
    }

    public void setReplyID(String replyID) {
        ReplyID = replyID;
    }
}