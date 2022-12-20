package com.example.spurrinkleteam.repository.TimeTable;

import com.example.spurrinkleteam.entity.TimeTable.TimeTableDay;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimeTableDayRepository extends MongoRepository<TimeTableDay, String> {
    TimeTableDay findByDayAndStartAndEnd(String day, String start, String end);
}
