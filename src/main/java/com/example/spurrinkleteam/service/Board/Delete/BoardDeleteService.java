package com.example.spurrinkleteam.service.Board.Delete;

import com.example.spurrinkleteam.dto.Board.reReply;

import java.util.ArrayList;

public interface BoardDeleteService {
    abstract String delete(String id);
    abstract String replyDelete(String id);
    abstract String reReplyDelete(reReply reply, String id);
}
