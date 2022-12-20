package com.example.spurrinkleteam.service.Board.Update;

import com.example.spurrinkleteam.entity.Board.Board;
import com.example.spurrinkleteam.entity.Board.BoardReply;
import com.example.spurrinkleteam.dto.Board.reReply;
import com.example.spurrinkleteam.repository.Board.BoardReplyRepository;
import com.example.spurrinkleteam.repository.Board.BoardRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
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
public class BoardUpdateServiceImp implements BoardUpdateService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BoardRepository BoardRepo;
    @Autowired
    private BoardReplyRepository BoardReplyRepo;


    @Override
    public ArrayList<String> update(Board board, String id) {
        try {
            ObjectId _id = new ObjectId(id);
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(_id));
            //업데이트 할 항목 정의
            Update update = new Update();
            update.set("title", board.getTitle());
            update.set("author", board.getAuthor());
            update.set("content", board.getContent());
            update.set("category", board.getCategory());

            mongoTemplate.updateFirst(query, update, "boards");

            ArrayList<String> arr = new ArrayList<>();
            Optional<Board> b = BoardRepo.findById(id);
            arr.add(b.get().getId()); arr.add(b.get().getCategory());
            return arr;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    @Override
    public Optional<Board> like(String id, ObjectNode user ) {
        try {
            Optional<Board> board = BoardRepo.findById(id);
            String[] userList = board.get().getLikesUser();
            ArrayList<String> newList = new ArrayList<>(Arrays.asList(userList));
            String userId = String.valueOf(user.get("id")).replaceAll("\\\"","");

            ObjectId _id = new ObjectId(id);
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(_id));
            //업데이트 할 항목 정의
            Update update = new Update();

            if(Arrays.asList(userList).contains(userId)){   // 좋아요 누른 사람 목록 중에 내가 있는가
                update.set("likes", board.get().getLikes()-1);
                newList.remove(userId);
            }else{
                update.set("likes", board.get().getLikes()+1);
                newList.add(userId);
            }

            update.set("likesUser", newList);
            mongoTemplate.updateFirst(query, update, "boards");
            board = BoardRepo.findById(id);
            return board;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    @Override
    public String replyLike(String id, ObjectNode user ) {
        try {
            Optional<BoardReply> board = BoardReplyRepo.findById(id);

            String[] userList = board.get().getLikesUser();

            ArrayList<String> newList = new ArrayList<>(Arrays.asList(userList));
            String userId = String.valueOf(user.get("id")).replaceAll("\\\"","");

            ObjectId _id = new ObjectId(id);
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(_id));
            //업데이트 할 항목 정의
            Update update = new Update();

            if(Arrays.asList(userList).contains(userId)){   // 좋아요 누른 사람 목록 중에 내가 있는가
                update.set("likes", board.get().getLikes()-1);
                newList.remove(userId);
            }else{
                update.set("likes", board.get().getLikes()+1);
                newList.add(userId);
            }
            update.set("likesUser", newList);

            mongoTemplate.updateFirst(query, update, "board-replys");
            board = BoardReplyRepo.findById(id);
            return board.get().getBoardID();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "fail";
        }
    }
    @Override
    public String reReplyLike(String id, ObjectNode user ) {
        try {
            Optional<BoardReply> board = BoardReplyRepo.findById(id);

            String reRe = String.valueOf(user.get("reRE")).replaceAll("\\\"","");

            JSONArray reReplyList = board.get().getReReply();
            reReply arr = (reReply) reReplyList.get(Integer.parseInt(reRe));
            String[] userList = arr.getLikesUser();

            ArrayList<String> newList = new ArrayList<>(Arrays.asList(userList));
            String userId = String.valueOf(user.get("id")).replaceAll("\\\"","");

            ObjectId _id = new ObjectId(id);
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(_id));
            //업데이트 할 항목 정의
            Update update = new Update();

            reReply likesArr = (reReply) reReplyList.get(Integer.parseInt(reRe));
            if(Arrays.asList(userList).contains(userId)){   // 좋아요 누른 사람 목록 중에 내가 있는가
                likesArr.setLikes(likesArr.getLikes()-1);
                newList.remove(userId);
            }else{
                likesArr.setLikes(likesArr.getLikes()+1);
                newList.add(userId);
            }
            String[] newUserList = newList.toArray(new String[newList.size()]);

            arr.setLikesUser(newUserList);
            update.set("reReply", reReplyList);

            mongoTemplate.updateFirst(query, update, "board-replys");
            board = BoardReplyRepo.findById(id);
            return board.get().getBoardID();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "fail";
        }
    }
    @Override
    public ArrayList<String> updateImg(ObjectNode board, String id) {
        try {
            Optional<Board> oriBoard = BoardRepo.findById(id);
            String[] img = oriBoard.get().getImg();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode arr  =  objectMapper.readTree(board.get("delImg").traverse());
            Iterator<Map.Entry<String, JsonNode>> fields = arr.fields();

            List<String> list = new ArrayList<>();
            for(int i=0; i<img.length;i++){
                while (i<arr.size()){
//                    Map.Entry<String, JsonNode> field = fields.next();
                    if(img[i].equals(String.valueOf(arr.get(i)).replaceAll("\\\"",""))){
                        Path filePath = Paths.get("/Users/dorry/IdeaProjects/Spurrinkle/src/main/frontend/public/img/board/"+img[i]);
                        Files.deleteIfExists(filePath);
                        img[i] = null;
                    }else{list.add(String.valueOf(arr.get(i)));}
                    break;
                }
            }

            ObjectId _id = new ObjectId(id);
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(_id));
            //업데이트 할 항목 정의
            Update update = new Update();
            update.set("title", String.valueOf(board.get("title")).replaceAll("\\\"",""));  // 오브젝트 노드 벨류값 가져오기.
            update.set("author", String.valueOf(board.get("author")).replaceAll("\\\"",""));
            update.set("content", String.valueOf(board.get("content")).replaceAll("\\\"",""));
            update.set("img", list);  // delImg 배열 안에 있는 파일들 삭제하고, db도 수정
            update.set("category", String.valueOf(board.get("category")).replaceAll("\\\"",""));

            mongoTemplate.updateFirst(query, update, "boards");

            ArrayList<String> arr2 = new ArrayList<>();
            arr2.add(oriBoard.get().getId()); arr2.add(oriBoard.get().getCategory());
            return arr2;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String replyUpdate(BoardReply reply) {
        try {
            ObjectId _id = new ObjectId(reply.getId());
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(_id));
            //업데이트 할 항목 정의
            Update update = new Update();
            update.set("content", reply.getContent());

            mongoTemplate.updateFirst(query, update, "board-replys");
            return reply.getContent();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "fail";
        }
    }

    @Override
    public String reReplyUpdate(reReply reply, String id) {
        try {
            Optional<BoardReply> boardReply = BoardReplyRepo.findById(id);

            for(int i = 0; i < boardReply.get().getReReply().size(); i++){
                reReply reRe = (reReply) boardReply.get().getReReply().get(i);
                if(reply.getId() == reRe.getId()){
                    reRe.setContent(reply.getContent());
                }
            }

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
            //업데이트 할 항목 정의
            Update update = new Update();
            update.set("reReply", boardReply.get().getReReply());

            mongoTemplate.updateFirst(query, update, "board-replys");
            return reply.getContent();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "fail";
        }
    }
}
