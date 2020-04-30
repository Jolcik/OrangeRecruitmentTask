package com.pkostrzenski.orange.controllers;

import com.pkostrzenski.orange.controllers.request_models.PossibleMeetingDateRequest;
import com.pkostrzenski.orange.services.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CalendarController {

    @Autowired
    CalendarService calendarService;

    @PostMapping("/api/possible-meeting-dates")
    public ResponseEntity<?> getPossibleMeetingDates(
            @Valid @RequestBody PossibleMeetingDateRequest data
    ){
        try {
            return ResponseEntity.ok(
                calendarService.getPossibleMeetingDates(
                    data.getFirstCalendar(),
                    data.getSecondCalendar(),
                    data.getMeetingDuration()
                )
            );
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
