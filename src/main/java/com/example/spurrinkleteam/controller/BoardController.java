package com.example.spurrinkleteam.controller;

import com.example.spurrinkleteam.entity.Board.Board;
import com.example.spurrinkleteam.entity.Board.BoardReply;
import com.example.spurrinkleteam.dto.Board.reReply;
import com.example.spurrinkleteam.service.Board.Delete.BoardDeleteService;
import com.example.spurrinkleteam.service.Board.Update.BoardUpdateService;
import com.example.spurrinkleteam.service.Board.View.BoardViewService;
import com.example.spurrinkleteam.service.Board.Write.BoardWriteService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardWriteService boardWrite;
    @Autowired
    BoardViewService boardView;
    @Autowired
    BoardUpdateService boardUpdate;
    @Autowired
    BoardDeleteService boardDelete;

    @PostMapping(value = "/write")
    public ArrayList<String> boardWrite(@RequestBody Board board){
        return boardWrite.write(board);
    }

//    @PostMapping(value = "/write/{id}/img")
//    public String boardWriteImg(@RequestParam("img") List<MultipartFile> img, @PathVariable String id, HttpServletRequest req){
//        return boardWrite.writeImg(img, id, req);
//    }

    @PostMapping(value = "/write/{id}/img")
    public String boardWriteImg(@RequestBody Board board, @PathVariable String id){
        System.out.println("이미지 받음 : "+board.getImg()[0]);
        System.out.println("받은 아이디 : " + id);
//        return boardWrite.writeImg(img, id, req);
        return boardWrite.setImg(board.getImg(),id);
    }
    @PostMapping(value = "/{id}/replyWrite")
    public String boardReply(@RequestBody BoardReply reply) {
        return boardWrite.replyWrite(reply);
    }
    @PostMapping(value = "/{id}/reReplyWrite")
    public String boardReplyWrite(@RequestBody reReply reply, @PathVariable String id) {
        return boardWrite.reReplyWrite(reply, id);
    }



    @GetMapping("/{uni}/main/{page}")
    public Page<Board> boardMain(@PathVariable("uni") String uni, @PathVariable("page") int page){
        return boardView.boardMainView(uni, page);
    }
    @GetMapping("/{uni}/popu")
    public List<Board> boardPopu(@PathVariable String uni){
        return boardView.boardPopu(uni);
    }
    @PostMapping(value = "/{id}")
    public Optional<Board> boardView(@PathVariable String id) {
        return boardView.view(id);
    }
    @PostMapping(value = "/{id}/replySearch")
    public List<BoardReply> boardReplySearch(@PathVariable String id) {
        return boardView.replySearch(id);
    }
    @PostMapping(value = "/{uni}/search/{page}")
    public Page<Board> boardSearch(@RequestBody ObjectNode search, @PathVariable String uni, @PathVariable int page) {
        return boardView.search(search, uni, page);
    }
    @GetMapping(value="/{id}/myBoard")
    public Page<Board> myBoard(@PathVariable String id, @RequestParam int page){
        return boardView.myBoard(id, page);
    }



    @PostMapping(value = "/{id}/update")
    public ArrayList<String> boardUpdate(@RequestBody Board board, @PathVariable String id) {
        return boardUpdate.update(board, id);
    }
    @PostMapping(value = "/{id}/like")
    public Optional<Board> like(@PathVariable String id, @RequestBody ObjectNode user){ return boardUpdate.like(id, user); }
    @PostMapping(value = "/{id}/reply/like")
    public String replyLike(@PathVariable String id, @RequestBody ObjectNode user){ return boardUpdate.replyLike(id, user); }
    @PostMapping(value = "/{id}/reReply/like")
    public String reReplyLike(@PathVariable String id, @RequestBody ObjectNode user){ return boardUpdate.reReplyLike(id, user); }
    @PostMapping(value = "/{id}/updateImg")
    public ArrayList<String> boardUpdateImg(@RequestBody ObjectNode board, @PathVariable String id) {
        return boardUpdate.updateImg(board, id);
    }
    @PostMapping(value = "/{id}/reply/update")
    public String boardReplyUpdate(@RequestBody BoardReply reply) {
        return boardUpdate.replyUpdate(reply);
    }
    @PostMapping(value = "/{id}/reReply/update")
    public String board_reReplyUpdate(@RequestBody reReply reply, @PathVariable String id) {
        return boardUpdate.reReplyUpdate(reply, id);
    }



    @PostMapping(value = "/{id}/delete")
    public String boardDelete(@PathVariable String id) {
        return boardDelete.delete(id);
    }
    @PostMapping(value = "/{id}/reply/delete")
    public String boardReplyDelete(@PathVariable String id) {
        return boardDelete.replyDelete(id);
    }
    @PostMapping(value = "/{id}/reReply/delete")
    public String boardReReplyDelete(@RequestBody reReply reply, @PathVariable String id) {
        return boardDelete.reReplyDelete(reply, id);
    }
    @GetMapping("/{uni}/latestPost")
    public List<Board> boardLatestPost(@PathVariable String uni){
        return boardView.LatestPost(uni);
    }
}