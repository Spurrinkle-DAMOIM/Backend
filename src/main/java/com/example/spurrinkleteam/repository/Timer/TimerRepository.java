package com.example.spurrinkleteam.repository.Timer;

import com.example.spurrinkleteam.entity.Timer.Timer;
import com.example.spurrinkleteam.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimerRepository extends MongoRepository<Timer,String> {

    Timer findByTimerUser(String timerUser);
}

