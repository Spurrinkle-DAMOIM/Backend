package com.example.spurrinkleteam.repository.Moim.Vote;


import com.example.spurrinkleteam.entity.Moim.Vote.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface VoteRepository extends MongoRepository<Vote, String> {

    List<Vote> findAllByMeetName(String meet);


    void deleteAllByMeetName(String meetName);

    Page<Vote> findAllByMeetName(String meet, Pageable pageable);

}