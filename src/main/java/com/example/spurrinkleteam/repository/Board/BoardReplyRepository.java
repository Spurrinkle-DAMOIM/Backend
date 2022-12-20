package com.example.spurrinkleteam.repository.Board;

import com.example.spurrinkleteam.entity.Board.BoardReply;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BoardReplyRepository extends MongoRepository<BoardReply,String> {
     List<BoardReply> findAllByBoardID(String boardID);
}

