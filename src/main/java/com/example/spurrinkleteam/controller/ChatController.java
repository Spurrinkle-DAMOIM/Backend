package com.example.spurrinkleteam.controller;

import com.example.spurrinkleteam.entity.Chat;
import com.example.spurrinkleteam.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ChatService chatService;

//    @MessageMapping("/message") // /app/message
//    @SendTo("/chatroom/public")
//    public Message receivePublicMessage(@Payload Message message){
//        return message;
//    }




    //메세지를 전달해주는 역할
    @MessageMapping("/private-message")
    public Chat receivePrivateMessage(@Payload Chat chat){
        chatService.saveChat(chat);
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
        System.out.println(chat.getRoomId());
//        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private", message); // /user/name/private

        return chat;
    }
}
