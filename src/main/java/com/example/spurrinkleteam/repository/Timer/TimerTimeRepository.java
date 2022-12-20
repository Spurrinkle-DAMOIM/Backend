package com.example.spurrinkleteam.repository.Timer;

import com.example.spurrinkleteam.entity.Timer.Timer;
import com.example.spurrinkleteam.entity.Timer.TimerTime;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TimerTimeRepository extends MongoRepository<TimerTime,String> {
    TimerTime findByUserIdAndDay(String userId, String day);
    List<TimerTime> findAllByDay(String day);
}

