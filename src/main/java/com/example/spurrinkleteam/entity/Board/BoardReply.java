package com.example.spurrinkleteam.entity.Board;

import lombok.*;
import org.json.simple.JSONArray;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "board-replys")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class BoardReply {
    @Id
    private String id;
    private String boardID;
    private String author;
    private String content;
    private JSONArray reReply;
    private int likes;
    private String[] likesUser;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date = new Date();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
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

    public void setContent(String content) {
        this.content = content;
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

    public JSONArray getReReply() {
        return reReply;
    }

    public void setReReply(JSONArray reReply) {
        this.reReply = reReply;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}