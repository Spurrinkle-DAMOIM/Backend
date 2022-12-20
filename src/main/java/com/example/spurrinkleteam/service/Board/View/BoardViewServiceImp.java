package com.example.spurrinkleteam.service.Board.View;

import com.example.spurrinkleteam.entity.Board.Board;
import com.example.spurrinkleteam.entity.Board.BoardReply;
import com.example.spurrinkleteam.repository.Board.BoardReplyRepository;
import com.example.spurrinkleteam.repository.Board.BoardRepository;
import com.example.spurrinkleteam.repository.CalendarRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.LivenessState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BoardViewServiceImp implements BoardViewService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BoardRepository BoardRepo;
    @Autowired
    private BoardReplyRepository BoardReplyRepo;
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public Page<Board> boardMainView(String uni, int page) {
        Sort sort1 = Sort.by("date").descending();
        Pageable pageable = PageRequest.of(page -1,10,sort1);
        Page<Board> boardList = BoardRepo.findAllByCategory(uni, pageable);
//        System.out.println(boardList.getContent().get(0).setReplyCnt());

        for(int i = 0; i < boardList.getContent().size(); i++) {
            int cnt = 0;
            List<BoardReply> reply = BoardReplyRepo.findAllByBoardID(boardList.getContent().get(i).getId());
            for (int k = 0; k < reply.size(); k++) {
                cnt++;
                for (int j = 0; j < reply.get(k).getReReply().size(); j++) {
                    cnt++;
                }
            }
            boardList.getContent().get(i).setReplyCnt(cnt);
        }
//        List<JSONObject> jsArr = boardCnt(boardList);
        return boardList;
    }

    @Override
    public Page<Board> myBoard(String id, int page) {
        Sort sort1 = Sort.by("date").descending();
        Pageable pageable = PageRequest.of(0,10, sort1);
        return BoardRepo.findAllByAuthor(id, pageable);
    }

    @Override
    public List<Board> boardPopu(String uni) {
        System.out.println("인기순으로 정렬");

        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, -7); // 빼고 싶다면 음수 입력

        Date date = new Date(cal1.getTimeInMillis());

        Sort sort1 = Sort.by("likes").descending();
        Pageable pageable = PageRequest.of(0,5,sort1);

        Query query = new Query();
        query.addCriteria(where("category").is(uni)
                .andOperator(where("date").gte(date).lte(new Date())));
        query.with(pageable);

        List<Board> list = mongoOperations.find(query, Board.class);

        return list;
    }
    @Override
    public List<Board> LatestPost(String uni) {
        Sort sort1 = Sort.by("date").descending();
        Pageable pageable = PageRequest.of(0,10, sort1);

        Query query = new Query();
        query.addCriteria(where("category").is(uni));
        query.with(pageable);

        List<Board> list = mongoOperations.find(query, Board.class);

        for(int i = 0; i < list.size(); i++) {
            list.get(i).setReplyCnt(replyCnt(list.get(i).getId()));
        }

        return list;
    }

    public int replyCnt(String id) {
        List<BoardReply> reply = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("boardID").is(id));
            reply = mongoTemplate.find(query, BoardReply.class, "board-replys");
            return reply.size();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return reply.size();
        }
    }

    @Override
    public Optional<Board> view(String id) {
        Optional<Board> board = null;
        try {
            board = BoardRepo.findById(id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return board;
        }
        return board;
    }

    @Override
    public List<BoardReply> replySearch(String id) {
        List<BoardReply> reply = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("boardID").is(id));
            reply = mongoTemplate.find(query, BoardReply.class, "board-replys");
            return reply;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return reply;
        }
    }

    @Override
    public Page<Board> search(ObjectNode search, String uni, int page) {
        try {
            System.out.println("search: " + search);
            String text = search.get("searchBoard").asText();
            System.out.println("text: " + text);

            Sort sort1 = Sort.by("date").descending();
            Pageable pageable = PageRequest.of(page-1,10,sort1);

            Query query = new Query();
            query.addCriteria(Criteria.where("category").regex(uni));
            query.addCriteria(Criteria.where("title").regex(text));
            query.with(pageable);
            List<Board> list = mongoOperations.find(query, Board.class);

            Page<Board> boardList = PageableExecutionUtils.getPage(list, pageable,
                    () -> mongoOperations.count(Query.of(query).limit(-1).skip(-1), Board.class));
            System.out.println(boardList);
            for(int i = 0; i < boardList.getContent().size(); i++) {
                int cnt = 0;
                List<BoardReply> reply = BoardReplyRepo.findAllByBoardID(boardList.getContent().get(i).getId());
                for (int k = 0; k < reply.size(); k++) {
                    cnt++;
                    for (int j = 0; j < reply.get(k).getReReply().size(); j++) {
                        cnt++;
                    }
                }
                boardList.getContent().get(i).setReplyCnt(cnt);
            }
            return boardList;

//        List<JSONObject> jsArr = boardCnt(boardList);

//            Query query = new Query();
//            query.limit(10);
//            query.addCriteria(Criteria.where("category").regex(uni));
//            query.addCriteria(Criteria.where("title").regex(text));
//            List<Board> board = mongoTemplate.find(query, Board.class);
//            return boardCnt(board);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public List<JSONObject> boardCnt(List<Board> boardList){
        List<JSONObject> jsArr = new ArrayList<JSONObject>();
        for(int i = 0; i < boardList.size(); i++) {
            Board board = boardList.get(i);
            String id = board.getId();
            int cnt = 0;
            JSONObject jsobj = new JSONObject();
            jsobj.put("id", id);
            jsobj.put("content", board.getContent());
            jsobj.put("likes", board.getLikes());
            jsobj.put("title", board.getTitle());
            List<BoardReply> reply = BoardReplyRepo.findAllByBoardID(id);
            for (int k = 0; k < reply.size(); k++) {
                cnt++;
                for (int j = 0; j < reply.get(k).getReReply().size(); j++) {
                    cnt++;
                }
            }
            jsobj.put("date", board.getDate());
            jsobj.put("replyCnt", cnt);
            jsArr.add(jsobj);
        }
        return jsArr;
    }
}