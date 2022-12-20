package com.example.spurrinkleteam.service;


import com.example.spurrinkleteam.dto.UserDTO;
import com.example.spurrinkleteam.entity.Chat;
import com.example.spurrinkleteam.entity.ChatRoom;
import com.example.spurrinkleteam.repository.RoomRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MongoTemplate mongoTemplate;


    public String createRoom(ChatRoom chatRoom){
        if(chatRoom.getRoomId() == null) {
            chatRoom.setRoomId(chatRoom.createRoomId());
        }
        ChatRoom room = ChatRoom.builder()
                .roomId(chatRoom.getRoomId())
                .chats(new ArrayList<Chat>())
                .name(chatRoom.getName())
                .userId(chatRoom.getUserId())
                .whether(chatRoom.isWhether())
                .createDate(LocalDateTime.now())
                .lastDate(LocalDateTime.now())
                .build();
        roomRepository.save(room);
        return chatRoom.getRoomId();
    }

    public Page<ChatRoom> searchRoom(String id, int page){   //채팅 리스트 확인
//        if(roomRepository.findByTagsIn())
//        return null;
        Sort sort1 = Sort.by("lastDate").descending();
        Pageable pageable = PageRequest.of(page -1,10,sort1);
        return roomRepository.findByuserIdIn(id, pageable);

    }

    public ChatRoom searchRoomUser(List<String> roomUser, boolean whether){ // 1:1채팅시 이미 채팅방 있는지 확인
        return roomRepository.findByUserIdAndWhether(roomUser,whether);
    }

    public void saveChat(Chat chat) {
        System.out.println(LocalDateTime.now());
        System.out.printf("??" + chat.getMessage());
        List<Document> array = new ArrayList<>();
        Update update = new Update();
        Document item = new Document();
        Query query = new Query().addCriteria(Criteria.where("_id").is(chat.getRoomId()));
        item.put("roomId" , chat.getRoomId());
        item.put("message", chat.getMessage());
        item.put("senderName", chat.getSenderName());
        item.put("date", LocalDateTime.now());
        array.add(item);
        update.push("chats").each(array);
        mongoTemplate.updateFirst(query, update, "chatRoom");
        updateLastDate(chat);
    }

    public void updateLastDate(Chat chat) {
        Criteria criteria = new Criteria("_id");
        criteria.is(chat.getRoomId());
        Query queryDate = new Query(criteria);

        Update updateDate = new Update();
        updateDate.set("lastDate", LocalDateTime.now());

        mongoTemplate.updateFirst(queryDate, updateDate, "chatRoom");
    }

    public List<Chat> getChatList(ChatRoom room){
        return roomRepository.findById(room.getRoomId()).get().getChats();
    }



}