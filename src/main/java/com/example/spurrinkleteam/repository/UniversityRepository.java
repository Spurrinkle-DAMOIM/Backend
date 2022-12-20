package com.example.spurrinkleteam.repository;

import com.example.spurrinkleteam.entity.University;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UniversityRepository extends MongoRepository<University, String>{
    University findByEmail(String email);
}
