package com.example.spurrinkleteam.service.Board.Write;

import com.example.spurrinkleteam.entity.Board.Board;
import com.example.spurrinkleteam.entity.Board.BoardReply;
import com.example.spurrinkleteam.dto.Board.reReply;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public interface BoardWriteService {
    abstract ArrayList<String> write(Board board);
    abstract String writeImg(List<MultipartFile> img, String id, HttpServletRequest req);
    abstract String reReplyWrite(reReply reply, String id);
    abstract String replyWrite(BoardReply reply);
    abstract String setImg(String[] imgList, String id);
}
