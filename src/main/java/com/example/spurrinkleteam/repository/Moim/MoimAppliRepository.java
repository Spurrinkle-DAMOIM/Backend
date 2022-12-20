package com.example.spurrinkleteam.repository.Moim;

import com.example.spurrinkleteam.entity.Moim.MoimApplicant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MoimAppliRepository extends MongoRepository<MoimApplicant, String> {
    List<MoimApplicant> findByMeetName(String meetName);
    MoimApplicant findByUserIdAndMeetName(String userId, String meetName);
}