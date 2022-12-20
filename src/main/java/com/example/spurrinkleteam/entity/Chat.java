package com.example.spurrinkleteam.entity;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Chat {
    private String senderName;
//    private String receiverName;
    private String roomId;
    private String message;
    private LocalDateTime date;
    private String img;
//    private Status status;

}
