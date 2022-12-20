package com.example.spurrinkleteam.controller;

import com.example.spurrinkleteam.dto.TimeTable.TimeTableDTO;
import com.example.spurrinkleteam.dto.TimeTable.TimeTableDayDTO;
import com.example.spurrinkleteam.dto.TimeTable.TimeTableSubjectDTO;
import com.example.spurrinkleteam.entity.TimeTable.TimeTableDay;
import com.example.spurrinkleteam.entity.TimeTable.TimeTableSubject;
import com.example.spurrinkleteam.service.TimeTable.TimeTableServiceImp;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@AllArgsConstructor
@RequestMapping("/timeTable")
public class TimeTableController {
    @Autowired
    TimeTableServiceImp timeTable;

    @PostMapping(value = "/{id}/{date}")
    public ArrayList<JSONObject> addSub(@PathVariable("id") String id, @PathVariable("date") String date,
                                        @RequestBody TimeTableSubjectDTO subject){
        return timeTable.addSub(id, date, subject);
    }

    @PostMapping(value = "/days")
    public String addTime(@RequestBody TimeTableDayDTO timeTableDayDTO){
        return timeTable.addTime(timeTableDayDTO);
    }

    @GetMapping (value = "/{id}/{date}/searchSemester")
    public ArrayList<TimeTableSubject> searchSemester(@PathVariable("id") String id, @PathVariable("date") String date){
        return timeTable.searchSemester(id, date);
    }

    @PostMapping(value = "/searchTime")
    public ArrayList<TimeTableDay> searchTime(@RequestBody ObjectNode data){
        return timeTable.searchTime(data);
    }
}
