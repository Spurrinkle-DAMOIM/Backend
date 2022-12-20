package com.example.spurrinkleteam.entity;

//public enum Status {
//    JOIN,
//    MESSAGE,
//    LEAVE
//}

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.springframework.data.annotation.Id;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
public class ChatRoom {
    @Id
    private String roomId;

    private List<Chat>  chats;
    private String name;

    private List<String> userId;
    private boolean whether;    //false -> 1 : 1 채팅,  true -> 1 : N 채팅
    private LocalDateTime createDate;
    private LocalDateTime lastDate;

    private Set<WebSocketSession> sessions = new HashSet<>();
    //WebSocketSession은 Spring에서 Websocket Connection이 맺어진 세션

    public String createRoomId(){
        return UUID.randomUUID().toString();
    }

    @Builder
    public ChatRoom(List<Chat>  chats, String roomId, String name, List<String> userId, boolean whether, LocalDateTime createDate,LocalDateTime lastDate) {
        this.chats = chats;
        this.roomId = roomId;
        this.name = name;
        this.userId = userId;
        this.whether = whether;
        this.createDate = createDate;
        this.lastDate = lastDate;
    }
}