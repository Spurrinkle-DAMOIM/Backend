package com.example.spurrinkleteam.service.Board.Write;

import com.example.spurrinkleteam.entity.Board.Board;
import com.example.spurrinkleteam.entity.Board.BoardReply;
import com.example.spurrinkleteam.dto.Board.reReply;
import com.example.spurrinkleteam.entity.User;
import com.example.spurrinkleteam.repository.Board.BoardReplyRepository;
import com.example.spurrinkleteam.repository.Board.BoardRepository;
import com.example.spurrinkleteam.repository.UserRepository;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardWriteServiceImp implements BoardWriteService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BoardRepository BoardRepo;
    @Autowired
    private BoardReplyRepository BoardReplyRepo;
    @Autowired
    private UserRepository UserRepo;

    @Override
    public ArrayList<String> write(Board board) {
        try{
            Optional<User> user = UserRepo.findById(board.getAuthor());
            board.setCategory(user.get().getUniName());
            BoardRepo.save(board);
            ArrayList<String> arr = new ArrayList<>();
            arr.add(board.getId()); arr.add(board.getCategory());
            return arr;
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String writeImg(List<MultipartFile> img, String id, HttpServletRequest req) {
        try{
            // 파일명을 업로드 한 날짜로 변환하여 저장
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            ArrayList<String> files = new ArrayList<String>();
            int cnt = 0;
            File folder = new File("src/main/frontend/public/img/board");
            if(!folder.exists()){
                folder.mkdirs();
            }
            for(MultipartFile image : img) {
                File destination = new File(current_date +id+ cnt);
                image.transferTo(destination);
                files.add(current_date +id+ cnt);
                cnt++;
            }


            Optional<Board> board = BoardRepo.findById(id);
            ObjectId _id = new ObjectId(id);
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(_id));
            //업데이트 할 항목 정의
            Update update = new Update();
            update.set("img", files);

            mongoTemplate.updateFirst(query, update, "boards");

            Optional<User> user = UserRepo.findById(board.get().getAuthor());

            return user.get().getUniName();
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            return "fail";
        }
    }

    @Override
    public String setImg(String[] imgList, String id){
        Optional<Board> board = BoardRepo.findById(id);
        ObjectId _id = new ObjectId(id);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(_id));
        //업데이트 할 항목 정의
        Update update = new Update();
        update.set("img", imgList);

        mongoTemplate.updateFirst(query, update, "boards");

        Optional<User> user = UserRepo.findById(board.get().getAuthor());

        return user.get().getUniName();
    }

    @Override
    public String reReplyWrite(reReply reply, String id) {
        try {
            if(reply.getContent() == null && reply.getAuthor() == null){
                return "fail";
            }
            Optional<BoardReply> boardReply = BoardReplyRepo.findById(id);
            System.out.println("길이: " + boardReply.get().getReReply().size());
            if(boardReply.get().getReReply().size() == 0){
                reply.setId(0);
            }
            for(int i = 0; i < boardReply.get().getReReply().size(); i++){
                if(i == boardReply.get().getReReply().size()-1){
                    reReply reRe = (reReply) boardReply.get().getReReply().get(i);
                    reply.setId(reRe.getId()+1);
                }
            }
            ObjectId _id = new ObjectId(id);
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(_id));
            //업데이트 할 항목 정의
            Update update = new Update();
            JSONArray arr = boardReply.get().getReReply();
            arr.add(reply);
            update.set("reReply", arr);

            mongoTemplate.updateFirst(query, update, "board-replys");
            return reply.getContent();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "fail";
        }
    }

    @Override
    public String replyWrite(BoardReply reply) {
        try {
            System.out.println("Reply: " + reply.getBoardID());
            BoardReplyRepo.save(reply);
            return "success";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "fail";
        }
    }
}
