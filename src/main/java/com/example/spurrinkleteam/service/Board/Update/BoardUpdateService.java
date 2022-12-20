package com.example.spurrinkleteam.service.Board.Update;

import com.example.spurrinkleteam.entity.Board.Board;
import com.example.spurrinkleteam.entity.Board.BoardReply;
import com.example.spurrinkleteam.dto.Board.reReply;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Optional;

public interface BoardUpdateService {
    abstract ArrayList<String> update(Board board, String id);
    abstract Optional<Board> like(String id, ObjectNode user );
    abstract String replyLike(String id, ObjectNode user );
    abstract String reReplyLike(String id, ObjectNode user );
    abstract ArrayList<String> updateImg(ObjectNode board, String id);
    abstract String replyUpdate(BoardReply reply);
    abstract String reReplyUpdate(reReply reply, String id);
}
