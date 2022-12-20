package com.example.spurrinkleteam.controller;

import com.example.spurrinkleteam.dto.Moim.Vote.VoteDTO;
import com.example.spurrinkleteam.entity.Moim.Vote.Vote;
import com.example.spurrinkleteam.service.User.UserService;
import com.example.spurrinkleteam.service.Vote.VoteService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    VoteService voteService;
    @Autowired
    UserService userService;

    @PostMapping("/save")
    public String setVote(@RequestBody VoteDTO vote){
        return voteService.saveVote(vote);
    }

    @GetMapping("/{meetName}/list")
    public Page<Vote> getVoteList(@PathVariable String meetName, @RequestParam int page){
        Page<Vote> vote  = voteService.getVoteList(meetName, page);
        for(int i = 0; i < vote.getContent().size(); i++){
            vote.getContent().get(i).setUser(userService.userIdCheck(vote.getContent().get(i).getHost()));
            System.out.println(i+"번째 : ");
        }
        return vote;
    }

    @PostMapping("/user/{id}/vote")
    public String userVote(@PathVariable String id, @RequestBody ObjectNode vote){
        return voteService.userVote(id, vote);
    }

    @PostMapping("/off")
    public void voteOff(@RequestBody ObjectNode vote){
        voteService.voteOff(vote);
    }

    @GetMapping("/{voteId}")
    public Optional<Vote> getVote(@PathVariable String voteId){
        return voteService.getVote(voteId);
    }

    @PostMapping("/delete")
    public void voteDelete(@RequestBody ObjectNode vote){
        voteService.voteDelete(vote);
    }
}
