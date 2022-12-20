package com.example.spurrinkleteam.repository;

import com.example.spurrinkleteam.entity.Contest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CrawlingRepository extends MongoRepository<Contest, String> {
}
