package com.example.spurrinkleteam.repository.TimeTable;

import com.example.spurrinkleteam.entity.TimeTable.TimeTableSubject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimeTableSubjectReopository extends MongoRepository<TimeTableSubject, String> {
}
