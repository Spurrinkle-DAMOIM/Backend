package com.example.spurrinkleteam.repository;

import com.example.spurrinkleteam.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{
    User findByEmail(String email);
    User findByNickname(String nickname);
    User findByIdAndPw(String id, String pw);

    User findByTel(String tel);
}
