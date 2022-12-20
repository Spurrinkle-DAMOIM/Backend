package com.example.spurrinkleteam.service;

import com.example.spurrinkleteam.dto.TodoDTO;
import com.example.spurrinkleteam.entity.Calendar;
import com.example.spurrinkleteam.entity.Todo;
import com.example.spurrinkleteam.repository.TodoRepository;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    MongoOperations mongoOperations;

    public TodoDTO getList(TodoDTO todo){
        return todoRepository.findByUserId(todo.getUserId());
    }

    public void makeTodo(String userId){
        Todo todo = Todo.builder()
                .userId(userId)
                .lists(new JSONArray())
                .build();
        todoRepository.save(todo);
    }

    public void createTodo(TodoDTO todo){
        LinkedHashMap<?, ?> listtodo = (LinkedHashMap<?, ?>) todo.getLists().get(0);
        List<Document> array = new ArrayList<>();
        Update update = new Update();
        Document item = new Document();
        Criteria findTodo = where("userId").is(todo.getUserId());
        Criteria findList = where("lists").elemMatch(where("date").is(listtodo.get("date")));
        BasicQuery bQuery = new BasicQuery(findTodo.getCriteriaObject(), findList.getCriteriaObject());
        if(mongoOperations.findOne(bQuery, Todo.class).getLists() == null){  //현재 날짜가 없는 경우
            System.out.println("날짜 없음!!");
            Query bequery = new Query().addCriteria(where("userId").is(todo.getUserId()));
            item.put("date", listtodo.get("date"));
            item.put("todo", new ArrayList<>());
            array.add(item);
            update.push("lists").each(array);
            mongoTemplate.updateFirst(bequery, update, "todos");
        }
    }

    public Document insertTodo(TodoDTO todo){ //개인 사용자 todolist 저장 메서드
        List<Document> array = new ArrayList<>();
        Update update = new Update();
        Document item = new Document();
        LinkedHashMap<?, ?> listtodo = (LinkedHashMap<?, ?>) todo.getLists().get(0);
        LinkedHashMap<?, ?> todosec = (LinkedHashMap<?, ?>) ((LinkedHashMap<?, ?>) todo.getLists().get(0)).get("todo");
        System.out.println("추가 이후...");
        Query query = new Query().addCriteria(where("userId").is(todo.getUserId()).andOperator(where("lists").elemMatch(where("date").is(listtodo.get("date")))));
        item.put("id", todosec.get("id"));
        item.put("content", todosec.get("content"));
        item.put("checked", todosec.get("checked"));
        array.add(item);
        update.push("lists.$.todo").each(array);
        mongoTemplate.updateFirst(query, update, "todos");
        System.out.println("날짜 없음 확인!");

        return item;
    }

    public Todo searchTodo(TodoDTO todo) {
        System.out.println("검색 시작..." + todo);
        Criteria findTodo = where("userId").is(todo.getUserId());
        System.out.println(todo.getUserId());
        System.out.println("? : "+((LinkedHashMap<?, ?>)todo.getLists().get(0)).get("date"));
        Criteria findTodoList = where("lists").elemMatch(where("date").is(((LinkedHashMap<?, ?>)todo.getLists().get(0)).get("date")));

        BasicQuery query = new BasicQuery(findTodo.getCriteriaObject(), findTodoList.getCriteriaObject());
        System.out.println("되는건가?" + mongoOperations.findOne(query, Todo.class));
        return mongoOperations.findOne(query, Todo.class);
    }

    public void modifyTodo(TodoDTO todo){
        LinkedHashMap<?, ?> newtodo = (LinkedHashMap<?, ?>) todo.getLists().get(0);
//        LinkedHashMap<?, ?> todomodi = ((LinkedHashMap<?, ?>)newtodo.get("todo"));
        System.out.println(newtodo);
        searchTodo(todo);
//        where("userId").is(todo.getUserId()).andOperator(where("lists").elemMatch(where("date").is(listtodo.get("date")).
        mongoTemplate.updateFirst(
                new Query(where("lists.date").is(newtodo.get("date"))),
//                new Query(where("lists.todo.id").is(todomodi.get("id"))),
                new Update().set("lists.$.todo", newtodo.get("todo")),
                Todo.class
        );
        searchTodo(todo);
    }

    public void deleteTodo(TodoDTO todo) {

    }
}
