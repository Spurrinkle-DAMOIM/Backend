package com.example.spurrinkleteam.repository;

import com.example.spurrinkleteam.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDBRepository extends MongoRepository<User,String> {

}
