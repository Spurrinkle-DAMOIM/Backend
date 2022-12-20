package com.example.spurrinkleteam.service.Vote;

import com.example.spurrinkleteam.dto.Moim.Vote.VoteDTO;
import com.example.spurrinkleteam.entity.Moim.Vote.Vote;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface VoteService {
    abstract String saveVote(VoteDTO vote);   // 투표 생성
    abstract Page<Vote> getVoteList(String meet, int page);   // 투표리스트

    abstract String userVote(String id, ObjectNode vote);   // 투표하기

    abstract String voteOff(ObjectNode vote); // 투표 종료
    abstract Optional<Vote> getVote(String voteId);   // 투표 상세
    abstract String voteDelete(ObjectNode vote);
}
