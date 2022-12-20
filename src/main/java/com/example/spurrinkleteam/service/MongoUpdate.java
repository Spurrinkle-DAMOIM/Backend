package com.example.spurrinkleteam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class MongoUpdate {
    @Autowired
    MongoTemplate mongoTemplate;

    public Query mongoQuery(String where, String find){
        Query query = new Query();
        query.addCriteria(Criteria.where(where).is(find));

        return query;
    }
    public String mongoUpdate(Query query, Update update, String collection){
        try{
            mongoTemplate.updateFirst(query, update, collection);
            return "success";
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return "fail";
        }
    }
}
