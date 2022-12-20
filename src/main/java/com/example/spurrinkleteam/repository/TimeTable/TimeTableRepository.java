package com.example.spurrinkleteam.repository.TimeTable;

import com.example.spurrinkleteam.entity.TimeTable.TimeTable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimeTableRepository extends MongoRepository<TimeTable, String> {
    TimeTable findByUserId(String userId);
}
