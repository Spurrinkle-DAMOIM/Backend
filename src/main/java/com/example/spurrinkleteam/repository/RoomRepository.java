package com.example.spurrinkleteam.repository;

import com.example.spurrinkleteam.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoomRepository extends MongoRepository<ChatRoom,String> {

    //    ChatRoom2 findByUserList(List<String> userList);
    Page<ChatRoom> findByuserIdIn(String userId, Pageable pageable);

    ChatRoom findByUserId(List<String> userId);

    ChatRoom findByUserIdAndWhether(List<String> userId, boolean whether);

}

