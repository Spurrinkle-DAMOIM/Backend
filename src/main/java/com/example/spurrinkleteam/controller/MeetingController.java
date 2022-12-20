package com.example.spurrinkleteam.controller;
import com.example.spurrinkleteam.dto.CalendarDTO;
import com.example.spurrinkleteam.entity.Calendar;
import com.example.spurrinkleteam.service.CalendarService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MeetingController {

    @Autowired
    CalendarService calendarService;

    @PostMapping("/meeting/proceedings/write")
    public String saveMeeting(@RequestBody CalendarDTO cal) {
        calendarService.saveMeeting(cal);
        return "T";
    }

    @PostMapping("/meeting/proceedings")
    public Calendar searchMeeting(@RequestBody CalendarDTO calendar) {
        return calendarService.getCalender(calendar);
    }

    @PostMapping("/meeting/proceedings/edit")
    public Calendar editMeeting(@RequestBody CalendarDTO calendar) {
        return calendarService.getMeeting(calendar);
    }

    @PostMapping("/meeting/proceedings/edit/save")
    public String editSaveMeeting(@RequestBody CalendarDTO calendar) {
        calendarService.getSaveMeeting(calendar);
        return "T";
    }
}