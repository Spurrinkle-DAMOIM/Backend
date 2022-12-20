package com.example.spurrinkleteam.entity.Board;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.util.Date;

@Document(collection = "boards")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Board {
    @Id
    private String id;
    private String title;
    private String content;
    private String author;
    private String[] img;
    private int likes;
    private String[] likesUser;
    private String category;
    private int replyCnt;
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date date = new Date();
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String[] getImg() {
        return img;
    }

    public void setImg(String[] img) {
        this.img = img;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getReplyCnt() {
        return replyCnt;
    }

    public void setReplyCnt(int replyCnt) {
        this.replyCnt = replyCnt;
    }
}