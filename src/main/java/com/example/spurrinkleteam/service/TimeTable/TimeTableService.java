package com.example.spurrinkleteam.service.TimeTable;

import com.example.spurrinkleteam.dto.TimeTable.TimeTableDTO;
import com.example.spurrinkleteam.dto.TimeTable.TimeTableDayDTO;
import com.example.spurrinkleteam.dto.TimeTable.TimeTableSubjectDTO;
import com.example.spurrinkleteam.entity.TimeTable.TimeTableDay;
import com.example.spurrinkleteam.entity.TimeTable.TimeTableSubject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public interface TimeTableService {
    abstract String addTime(TimeTableDayDTO timeTableDayDTO);
    abstract ArrayList<JSONObject> addSub(String id, String date,
                                        TimeTableSubjectDTO subject);
    abstract ArrayList<TimeTableSubject> searchSemester(String id, String date);
    abstract ArrayList<TimeTableDay> searchTime(ObjectNode data);
}
