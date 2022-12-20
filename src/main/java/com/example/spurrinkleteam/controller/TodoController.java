package com.example.spurrinkleteam.controller;


import com.example.spurrinkleteam.dto.TodoDTO;
import com.example.spurrinkleteam.entity.Todo;
import com.example.spurrinkleteam.repository.TodoRepository;
import com.example.spurrinkleteam.service.TodoService;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoService todoService;

    @PostMapping("/getList")
    public Todo getList(@RequestBody TodoDTO todo) {
        System.out.println("todo 들어옴 : " + todo.getUserId());
        return todoService.searchTodo(todo);
    }

    @PostMapping("/insert")
    public Document insertTodo(@RequestBody TodoDTO todo){
        System.out.println("todoinsert 들어옴 : " + todo.getUserId());
        todoService.createTodo(todo);
        return todoService.insertTodo(todo);
    }

    @PostMapping("/remove")
    public String modifyTodo(@RequestBody TodoDTO todo){
        System.out.println("todomodify 들어옴 : " + todo.getUserId());

        todoService.modifyTodo(todo);
        return "T";
    }

}
