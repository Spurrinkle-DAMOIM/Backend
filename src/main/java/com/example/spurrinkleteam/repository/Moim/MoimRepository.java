package com.example.spurrinkleteam.repository.Moim;

import com.example.spurrinkleteam.entity.Moim.Moim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MoimRepository extends MongoRepository<Moim, String> {
    Moim findByMeetName(String meetName);
    Page<Moim> findAllByMeetName(String meetName, Pageable pageable);
    Page<Moim> findAllByUserIn(String user, Pageable pageable);
}