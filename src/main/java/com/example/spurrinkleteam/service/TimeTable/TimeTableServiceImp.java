package com.example.spurrinkleteam.service.TimeTable;

import com.example.spurrinkleteam.dto.TimeTable.TimeTableDayDTO;
import com.example.spurrinkleteam.dto.TimeTable.TimeTableSubjectDTO;
import com.example.spurrinkleteam.entity.TimeTable.TimeTable;
import com.example.spurrinkleteam.entity.TimeTable.TimeTableDay;
import com.example.spurrinkleteam.entity.TimeTable.TimeTableSubject;
import com.example.spurrinkleteam.repository.TimeTable.TimeTableDayRepository;
import com.example.spurrinkleteam.repository.TimeTable.TimeTableRepository;
import com.example.spurrinkleteam.repository.TimeTable.TimeTableSubjectReopository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class TimeTableServiceImp implements TimeTableService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TimeTableRepository TimeTableRepo;
    @Autowired
    private TimeTableDayRepository TimeTableDayRepo;
    @Autowired
    private TimeTableSubjectReopository TimeTableSubjectRepo;
    @Autowired
    MongoOperations mongoOperations;

    @Override
    public ArrayList<JSONObject> addSub(String id, String date,
                                        TimeTableSubjectDTO subject) {
        System.out.println("======================");
        System.out.println("DayID: " + subject.getDays());
        TimeTableSubject timeTableSubject = TimeTableSubject.builder()
                .subName(subject.getSubName())
                .professor(subject.getProfessor())
                .place(subject.getPlace())
                .days(subject.getDays())
                .build();
        TimeTableSubjectRepo.save(timeTableSubject);
        System.out.println("ID: " + timeTableSubject.getId());

        Query query = new Query();
        query.addCriteria(where("userId").is(id));
        TimeTable semester = mongoTemplate.findOne(query, TimeTable.class, "time-tables");
        System.out.println("semester: " + semester);
        if(semester != null){
            Query query2 = new Query();
            query2.addCriteria(where("userId").is(id)
                    .andOperator(where("semesters").elemMatch(where("semester").is(date))));
            TimeTable semester2 = mongoTemplate.findOne(query2, TimeTable.class, "time-tables");
            System.out.println("semesters2: " + semester2);
            if(semester2 == null){
                JSONObject jso = new JSONObject();
                ArrayList<String> subjects = new ArrayList<String>();
                subjects.add(timeTableSubject.getId());
                jso.put("semester", date); jso.put("subjects", subjects);
                ArrayList<JSONObject> semesters = semester.getSemesters();
                System.out.println("semestersArr: " + semesters);
                semesters.add(jso);
                System.out.println("semestersArr22: " + semesters);
                System.out.println("semestersArr22: " + date + "    sub: " + subjects);

                Update update = new Update();

                update.set("semesters", semesters);

                mongoTemplate.updateFirst(query, update, "time-tables");
            }else{
                mongoTemplate.updateFirst(
                        new Query(where("userId").is(id).andOperator(where("semesters").elemMatch(where("semester").is(date)))),
                        new Update().push("semesters.$.subjects", timeTableSubject.getId()),
                        TimeTable.class
                );
            }
        }else{
            JSONObject jso = new JSONObject();
            ArrayList<String> subjects = new ArrayList<String>();
            subjects.add(timeTableSubject.getId());
            jso.put("semester", date); jso.put("subjects", subjects);
            ArrayList<JSONObject> semesters = semester.getSemesters();
            semesters.add(jso);

            Query query3 = new Query();
            query3.addCriteria(where("useId").is(id));

            Update update = new Update();

            update.set("semesters", semesters);

            mongoTemplate.updateFirst(query3, update, "time-tables");
        }

        return null;
    }

    @Override
    public String addTime(TimeTableDayDTO timeTableDayDTO) {

        TimeTableDay tableDays =
                TimeTableDayRepo.findByDayAndStartAndEnd(timeTableDayDTO.getDay(), timeTableDayDTO.getStart(), timeTableDayDTO.getEnd());
        System.out.println(tableDays);

        if(tableDays == null){
            TimeTableDay timeTableDay = TimeTableDay.builder()
                    .day(timeTableDayDTO.getDay())
                    .start(timeTableDayDTO.getStart())
                    .end(timeTableDayDTO.getEnd())
                    .build();
            TimeTableDayRepo.save(timeTableDay);

            return timeTableDay.getId();
        }
        return tableDays.getId();
    }

    @Override
    public ArrayList<TimeTableSubject> searchSemester(String id, String date) {
        TimeTable timeTable = TimeTableRepo.findByUserId(id);
        ArrayList<TimeTableSubject> arr = new ArrayList<>();

        for(int i = 0; i < timeTable.getSemesters().size(); i++) {
            String semester = (String) timeTable.getSemesters().get(i).get("semester");
            if(semester.equals(date+"학기")){
                ArrayList<String> arr2 = (ArrayList<String>) timeTable.getSemesters().get(i).get("subjects");
                for(int j = 0; j < arr2.size(); j++){
                    arr.add(TimeTableSubjectRepo.findById(arr2.get(j)).get());
                }
                break;
            }
        }
        return arr;
    }

    @Override
    public ArrayList<TimeTableDay> searchTime(ObjectNode data) {
        ArrayList<TimeTableDay> arr = new ArrayList<TimeTableDay>();
        for(int i = 0; i < data.get("days").size(); i++) {
            String time = String.valueOf(data.get("days").get(i)).replaceAll("\\\"","");
            arr.add(TimeTableDayRepo.findById(time).get());
        }
        return arr;
    }
}
