package com.example.spurrinkleteam.repository;

import com.example.spurrinkleteam.entity.Calendar;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CalendarRepository extends MongoRepository<Calendar,String> {
}

