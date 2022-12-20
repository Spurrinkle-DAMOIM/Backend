package com.example.spurrinkleteam.controller;

import com.example.spurrinkleteam.dto.UserDTO;
import com.example.spurrinkleteam.entity.Chat;
import com.example.spurrinkleteam.entity.ChatRoom;
import com.example.spurrinkleteam.service.ChatService;
import com.example.spurrinkleteam.service.User.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {

    @Autowired
    ChatService chatService;
    @Autowired
    UserService userService;


    //채팅방 목록 조회
    @PostMapping(value = "/rooms/{page}")
    public Page<ChatRoom> rooms(@PathVariable int page, @RequestBody UserDTO user){
//        log.info("# All Chat Rooms");
//        ModelAndView mv = new ModelAndView("chat/rooms");
//
//        mv.addObject("list", repository.findAllRooms());
//
//        return mv;

//        System.out.println("#### All Chat Rooms");
//        chatService.searchRoom(user);
//        return null;

        return chatService.searchRoom(user.getId(), page);


    }

    //채팅방 개설
//채팅방 개설
    @PostMapping(value = "/createRoom")
    public String create(@RequestBody ChatRoom chatRoom){
        System.out.println(chatRoom.getUserId());
        Collections.sort(chatRoom.getUserId());
        System.out.println(chatRoom.getUserId());
        chatRoom.setName(chatRoom.getUserId().get(0));
        ChatRoom isroom = chatService.searchRoomUser(chatRoom.getUserId(), chatRoom.isWhether()); //이미 방이 있는지 확인
        return isroom == null ? chatService.createRoom(chatRoom) : isroom.getRoomId();
    }
    //채팅방 조회
    @GetMapping("/searchRoom")
    public void getRoom(String roomId, Model model){

//        log.info("# get Chat Room, roomID : " + roomId);
//
//        model.addAttribute("room", repository.findRoomById(roomId));
    }
    //채팅내역 조회
    @PostMapping("/getChatList")
    public List<Chat> getChatList(@RequestBody ChatRoom room){
        System.out.println("채팅 리스트 함수");
        List<Chat> chat = chatService.getChatList(room);
        System.out.println("가져옴");
        for(int i=0; i<chat.size(); i++){
            chat.get(i).setImg(userService.userNicknameCheck(chat.get(i).getSenderName()).getImg());
            System.out.println(i + "번째 " + chat.get(i).getSenderName() + "의 사진 :" + chat.get(i).getImg());
        }
        return chat;
    }





}