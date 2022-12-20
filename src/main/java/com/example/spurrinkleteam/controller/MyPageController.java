package com.example.spurrinkleteam.controller;
;
import com.example.spurrinkleteam.dto.CalendarDTO;
import com.example.spurrinkleteam.entity.Calendar;
import com.example.spurrinkleteam.service.CalendarService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/myPage")
public class MyPageController {
    @Autowired
    CalendarService calendarService;

    @PostMapping("/calendar")
    public Calendar rend(@RequestBody CalendarDTO cal) {
        return calendarService.rend(cal);
    }

    @PostMapping("/calendar/add")
    public Calendar add(@RequestBody CalendarDTO cal) {
        return calendarService.calendaradd(cal);
    }

    @PostMapping("/calendar/remove")
    public Calendar remove(@RequestBody CalendarDTO cal) {
        return calendarService.remove(cal);
    }
}