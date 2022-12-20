package com.example.spurrinkleteam.service.Board.Delete;

import com.example.spurrinkleteam.entity.Board.BoardReply;
import com.example.spurrinkleteam.dto.Board.reReply;
import com.example.spurrinkleteam.repository.Board.BoardReplyRepository;
import com.example.spurrinkleteam.repository.Board.BoardRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class BoardDeleteServiceImp implements BoardDeleteService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BoardRepository BoardRepo;
    @Autowired
    private BoardReplyRepository BoardReplyRepo;


    @Override
    public String delete(String id) {
        try {
            System.out.println("마지막 확인");
//            System.out.println(delImgs[0]);
            BoardRepo.deleteById(id);
            Query query = new Query();
            query.addCriteria(Criteria.where("boardID").is(id));
            //업데이트 할 항목 정의


            mongoTemplate.remove(query, "board-replys");
            return "success";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "fail";
        }
    }

    @Override
    public String replyDelete(String id) {
        try {
            BoardReplyRepo.deleteById(id);
            return "success";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "fail";
        }
    }

    @Override
    public String reReplyDelete(reReply reply, String id) {
        try {
            System.out.println(reply.getId() + "    "  + id);
            Optional<BoardReply> boardReply = BoardReplyRepo.findById(id);
            for(int i = 0; i < boardReply.get().getReReply().size(); i++){
                reReply reRe = (reReply) boardReply.get().getReReply().get(i);
                if(reply.getId() == reRe.getId()){
                    boardReply.get().getReReply().remove(i);
                }
            }
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
            //업데이트 할 항목 정의
            Update update = new Update();
            update.set("reReply", boardReply.get().getReReply());

            mongoTemplate.updateFirst(query, update, "board-replys");
            return "success";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "fail";
        }
    }
}
