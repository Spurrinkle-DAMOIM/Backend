package com.example.spurrinkleteam.controller;

import com.example.spurrinkleteam.dto.Timer.Subject;
import com.example.spurrinkleteam.entity.Timer.TimerTime;
import com.example.spurrinkleteam.service.Timer.TimerService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/timer")
public class TimerController {
    @Autowired
    private TimerService timerService;

    @PostMapping(value = "/userCheck")
    public ArrayList<Object> userCheck(@RequestBody JsonNode user){
        return timerService.userCheck(user);
    }
    @PostMapping(value = "/addSub")
    public ArrayList<String> addSub(@RequestBody JsonNode sub){
        return timerService.addSub(sub);
    }
    @PostMapping(value = "/modiSub")
    public ArrayList<String> modiSub(@RequestBody JsonNode sub){
        return timerService.modiSub(sub);
    }
    @PostMapping(value = "/delSub")
    public ArrayList<String> delSub(@RequestBody JsonNode sub){
        return timerService.delSub(sub);
    }
    @PostMapping(value = "/{id}/day")
    public String day(@RequestBody TimerTime timer, @PathVariable String id){
        return timerService.day(timer, id);
    }
    @PostMapping(value = "/{id}/time")
    public String time(@RequestBody Subject obj, @PathVariable String id){
        return timerService.time(obj, id);
    }
    @GetMapping("/{id}/{day}")
    public TimerTime userTimeCheck(@PathVariable("id") String id, @PathVariable("day") String day){
        return timerService.userTimeCheck(id, day);
    }
}
