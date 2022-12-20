package com.example.spurrinkleteam.service.Timer;

import com.example.spurrinkleteam.dto.Timer.Subject;
import com.example.spurrinkleteam.entity.Timer.Timer;
import com.example.spurrinkleteam.entity.Timer.TimerTime;
import com.example.spurrinkleteam.repository.Timer.TimerRepository;
import com.example.spurrinkleteam.repository.Timer.TimerTimeRepository;
import com.example.spurrinkleteam.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TimerServiceImp implements TimerService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserRepository UserRepo;
    @Autowired
    private TimerRepository TimerRepo;
    @Autowired
    private TimerTimeRepository TimerTimeRepo;

    @Override
    public ArrayList<Object> userCheck(JsonNode user) {
        try{
            String id = String.valueOf(user.get("id")).replaceAll("\\\"","");
            String day = String.valueOf(user.get("day")).replaceAll("\\\"","");
            Timer time = TimerRepo.findByTimerUser(id);
            ArrayList<Object> arr = new ArrayList<Object>();

            TimerTime timer = TimerTimeRepo.findByUserIdAndDay(id, day);
            if(timer != null){
                arr.add(timer);
                arr.add(UserRepo.findById(id));
                return arr;
            }
            arr.add("");
            arr.add(UserRepo.findById(id));
            return arr;
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<String> addSub(JsonNode sub) {
        try{
            String id = String.valueOf(sub.get("id")).replaceAll("\\\"","");
            String[] subArr = UserRepo.findById(id).get().getSubject();
            ArrayList<String> newSubArr = new ArrayList<>(Arrays.asList(subArr));
            newSubArr.add(String.valueOf(sub.get("sub")).replaceAll("\\\"",""));
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(id));
            //업데이트 할 항목 정의
            Update update = new Update();
            update.set("subject", newSubArr);

            mongoTemplate.updateFirst(query, update, "users");
            System.out.println("newSubArr: " + newSubArr);
            return newSubArr;
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<String> modiSub(JsonNode sub) {
        try{
            String id = String.valueOf(sub.get("id")).replaceAll("\\\"","");
            String[] subArr = UserRepo.findById(id).get().getSubject();
            ArrayList<String> newSubArr = new ArrayList<>(Arrays.asList(subArr));

            int idx = Integer.parseInt(String.valueOf(sub.get("index")));
            System.out.println("수정할 인덱스: " + idx);
            newSubArr.set(idx, String.valueOf(sub.get("content")).replaceAll("\\\"",""));

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(id));
            //업데이트 할 항목 정의
            Update update = new Update();
            update.set("subject", newSubArr);

            mongoTemplate.updateFirst(query, update, "users");

            return newSubArr;
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<String> delSub(JsonNode sub) {
        try{
            String id = String.valueOf(sub.get("id")).replaceAll("\\\"","");
            String[] subArr = UserRepo.findById(id).get().getSubject();
            ArrayList<String> newSubArr = new ArrayList<>(Arrays.asList(subArr));
            System.out.println("삭제할 인덱스: " + Integer.parseInt(String.valueOf(sub.get("index"))));
            newSubArr.remove(Integer.parseInt(String.valueOf(sub.get("index"))));

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(id));
            //업데이트 할 항목 정의
            Update update = new Update();
            update.set("subject", newSubArr);

            mongoTemplate.updateFirst(query, update, "users");

            return newSubArr;
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String day(TimerTime timer, String id) {
        try{
            System.out.println("ID: " + id + "     TIME: " + timer);
            Date nowDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //원하는 데이터 포맷 지정
            String strNowDate = simpleDateFormat.format(nowDate);

            Optional<Timer> nowTimer = Optional.ofNullable(TimerRepo.findByTimerUser(id));

            ArrayList<JSONObject> timerArr = nowTimer.get().getTimer();

            if(timerArr.size() != 0){
                String day = (String) timerArr.get(timerArr.size()-1).get("day");
                if(day.equals(strNowDate)){
                    System.out.println("return ID: " + (String) timerArr.get(timerArr.size()-1).get("timeID"));
                    return (String) timerArr.get(timerArr.size()-1).get("timeID");
                }
            }
            timer.setDay(strNowDate);
            TimerTimeRepo.save(timer);
            Optional<TimerTime> subTime = Optional.ofNullable(TimerTimeRepo.findByUserIdAndDay(id, strNowDate));

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(nowTimer.get().getId()));
            //업데이트 할 항목 정의
            Update update = new Update();

            JSONObject jso = new JSONObject();
            jso.put("day", timer.getDay());
            jso.put("timeID", subTime.get().getId());
            timerArr.add(jso);
            update.set("timer", timerArr);

            mongoTemplate.updateFirst(query, update, "timers");


            return subTime.get().getId();
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String time(Subject obj, String id) {
        try{
            Optional<TimerTime> time = TimerTimeRepo.findById(id);
            JSONArray timeArr = time.get().getSubjects();
            System.out.println("timeArr: " + timeArr);
            System.out.println("timeArrLen: " + timeArr.size());

            JSONObject jso = new JSONObject();

            jso.put("startTime" , obj.getStartTime());
            jso.put("endTime" , obj.getEndTime());

            int cnt = 0;
            int sub = 0;
            if(timeArr.size() != 0){
                for(int i = 0; i < timeArr.size(); i++){
                    System.out.println(timeArr.get(i));
                    System.out.println(timeArr.get(i).getClass().getName());
                    String sb = obj.getSubName();
                    sub++;
                    LinkedHashMap<String, Object> jsob = (LinkedHashMap<String, Object>) timeArr.get(i);
                    String subName = (String) jsob.get("subName");
                    System.out.println(sb + "    " + subName);
                    if(subName.equals(sb)){
                        System.out.println("-----똑같은 과목-----");
                        ArrayList<Object> newSubArr = (ArrayList<Object>) jsob.get("subArr");
                        int calTime = 0;
                        for(int j = 0; j < newSubArr.size(); j++){
                            LinkedHashMap<String, Object> newObj = (LinkedHashMap<String, Object>) newSubArr.get(j);
                            System.out.println("에러나나?");
                            int t = (int) newObj.get("time");
                            calTime += t;
                        }
                        jso.put("time" , obj.getTime()-calTime);
                        newSubArr.add(jso);
                        cnt++;
                    }
                }
            }
            ObjectId _id = new ObjectId(id);
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(_id));
            //업데이트 할 항목 정의
            Update update = new Update();

            JSONArray subArr = new JSONArray();
            if(cnt == 0){   // 해당 과목이 없다면
                jso.put("time" , obj.getTime());
                JSONObject jso1 = new JSONObject();
                subArr.add(jso);
                jso1.put("subName" , obj.getSubName());
                jso1.put("subArr" , subArr);
                timeArr.add(jso1);
                if(sub == 0){update.set("total", time.get().getTotal());}
            }
            if(sub != 0){
                int t = (int) jso.get("time");
                System.out.println(time.get().getTotal() + "    " + t);
                update.set("total", time.get().getTotal()+t);
            }
            update.set("subjects", timeArr);
            mongoTemplate.updateFirst(query, update, "timers-times");
            return "success";
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public TimerTime userTimeCheck(String id, String day) {
        try{
            return TimerTimeRepo.findByUserIdAndDay(id, day);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void createTimer(String id){
        Timer timer = Timer.builder()
                .timerUser(id)
                .timer(new ArrayList<JSONObject>())
                .build();
        TimerRepo.save(timer);
    }
}
