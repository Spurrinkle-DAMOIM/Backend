package com.example.spurrinkleteam.repository;

import com.example.spurrinkleteam.dto.TodoDTO;
import com.example.spurrinkleteam.entity.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<Todo,String>{

    TodoDTO findByUserId(String userId);

}
