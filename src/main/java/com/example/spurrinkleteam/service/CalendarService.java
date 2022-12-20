package com.example.spurrinkleteam.service;

import com.example.spurrinkleteam.dto.CalendarDTO;
import com.example.spurrinkleteam.entity.Calendar;
import com.example.spurrinkleteam.repository.CalendarRepository;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class CalendarService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    MongoOperations mongoOperations;

    public Calendar rend(CalendarDTO calendarinfo) {
        try{
            System.out.println("rend calendar" + calendarinfo.getUser());
            Calendar calendar = calendarRepository.findById(calendarinfo.getUser()).get();
            System.out.println("calendar 보여줭 : " + calendar);
            if(calendar != null) {
                System.out.println("하이");
                return calendarSearch(calendar.getUser());
            } else {
                System.out.println("ㅂㅇ");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public Calendar calendaradd(CalendarDTO calendarinfo) {
        System.out.print("calendarService add : " + calendarinfo);
        Calendar calendar = Calendar.builder()
                .user(calendarinfo.getUser())
                .days(calendarinfo.getDays())
                .build();
        if (calendarRepository.findById(calendar.getUser()).isPresent()) {
            List<Document> array = new ArrayList<>();
            Update update = new Update();
            Document item = new Document();
            Query query = new Query().addCriteria(where("_id").is(calendar.getUser()));
            LinkedHashMap<String, String> newschedual = (LinkedHashMap<String, String>) calendar.getDays().get(0);
            item.put("id", UUID.randomUUID().toString());
            item.put("title", newschedual.get("title"));
            item.put("start", newschedual.get("start"));
            item.put("end", newschedual.get("end"));
            item.put("proc", false);
            array.add(item);
            update.push("days").each(array);
            mongoTemplate.updateFirst(query, update, "calendars");
        } else {
            calendarRepository.save(calendar);
        }
        return calendarSearch(calendar.getUser());
    }

    public Calendar calendarSearch(String id) {
        return calendarRepository.findById(id).get();
    }

    public Calendar remove(CalendarDTO calendarinfo) {
        System.out.println(calendarinfo.getUser());
        System.out.println(calendarinfo.getDays());
        Query query = new Query().addCriteria(where("_id").is(calendarinfo.getUser()));
        Update update = new Update();
        LinkedHashMap<String, String> newschedual = (LinkedHashMap<String, String>) calendarinfo.getDays().get(0);
        System.out.println(newschedual.get("title"));
        update.pull("days", new BasicDBObject("id", newschedual.get("id")));
        mongoTemplate.updateFirst(query, update, "calendars");

        Calendar calendar = calendarRepository.findById(calendarinfo.getUser()).get();
        return calendarSearch(calendar.getUser());
    }

    public void saveMeeting(CalendarDTO cal) {
        System.out.println("회의록 : " + cal);
        Calendar calendar = Calendar.builder()
                .user(cal.getUser())
                .days(cal.getDays())
                .build();
        if (calendarRepository.findById(calendar.getUser()).isPresent()) {
            List<Document> array = new ArrayList<>();
            Update update = new Update();
            Document item = new Document();
            Query query = new Query().addCriteria(where("_id").is(calendar.getUser()));
            LinkedHashMap<String, String> newschedual = (LinkedHashMap<String, String>) calendar.getDays().get(0);
            System.out.println("회의록 내용: " + newschedual.get("content"));
            item.put("id", UUID.randomUUID().toString());
            item.put("title", newschedual.get("title"));
            item.put("start", newschedual.get("start"));
            item.put("content", newschedual.get("content"));
            item.put("proc", true);
            array.add(item);
            update.push("days").each(array);
            mongoTemplate.updateFirst(query, update, "calendars");
        } else {
            calendarRepository.save(calendar);
        }
    }

    public Calendar getCalender(CalendarDTO calendar) {
        System.out.println("검색하는중 : " + calendar.getUser());
        LinkedHashMap<String, String> newschedual = (LinkedHashMap<String, String>) calendar.getDays().get(0);
        System.out.println("검색 아이디 : " + newschedual.get("id"));
        Criteria findIdCalendar = where("_id").is(calendar.getUser());
        Criteria findIdMeeting = where("days").elemMatch(where("id").is(newschedual.get("id")));
        BasicQuery query = new BasicQuery(findIdCalendar.getCriteriaObject(), findIdMeeting.getCriteriaObject());
        return mongoOperations.findOne(query, Calendar.class);
    }

    public Calendar getMeeting(CalendarDTO calendar) {
        LinkedHashMap<String, String> newschedual = (LinkedHashMap<String, String>) calendar.getDays().get(0);
        Criteria findIdCalendar = where("_id").is(calendar.getUser());
        Criteria findIdMeeting = where("days").elemMatch(where("id").is(newschedual.get("id")));
        BasicQuery query = new BasicQuery(findIdCalendar.getCriteriaObject(), findIdMeeting.getCriteriaObject());
        return mongoOperations.findOne(query, Calendar.class);
    }

    public Calendar getSaveMeeting(CalendarDTO calendar) {
        LinkedHashMap<String, String> newschedual = (LinkedHashMap<String, String>) calendar.getDays().get(0);
        mongoTemplate.updateMulti(
                new Query(where("days.id").is(newschedual.get("id"))),
                new Update().set("days.$.content", newschedual.get("content")),
                Calendar.class
        );
        return getCalender(calendar);
    }
}
