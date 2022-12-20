package com.example.spurrinkleteam.service.Board.View;

import com.example.spurrinkleteam.entity.Board.Board;
import com.example.spurrinkleteam.entity.Board.BoardReply;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BoardViewService {
    abstract Page<Board> boardMainView(String uni, int page);
    abstract Page<Board> myBoard(String id, int page);
    abstract List<Board> boardPopu(String uni);
    abstract List<Board> LatestPost(String uni);
    abstract Optional<Board> view(String id);
    abstract List<BoardReply> replySearch(String id);
    abstract Page<Board> search(ObjectNode search, String uni, int page);
}