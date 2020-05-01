package com.pkostrzenski.orange.controllers;

import com.pkostrzenski.orange.controllers.request_models.PossibleMeetingDateRequest;
import com.pkostrzenski.orange.models.TimeInterval;
import com.pkostrzenski.orange.services.CalendarService;
import com.pkostrzenski.orange.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CalendarController {

    @Autowired
    CalendarService calendarService;

    @PostMapping("/api/possible-meeting-dates")
    public ResponseEntity<?> getPossibleMeetingDates(
            @Valid @RequestBody PossibleMeetingDateRequest data
    ){
        try {
            List<TimeInterval> possibleMeetingHours = calendarService.getPossibleMeetingDates(
                    data.getFirstCalendar(),
                    data.getSecondCalendar(),
                    data.getMeetingDuration()
            );
            return ResponseEntity.ok(
                possibleMeetingHours.stream()
                        .map(timeInterval -> new String[]{
                            DateConverter.fromLongToString(timeInterval.getStart()),
                            DateConverter.fromLongToString(timeInterval.getEnd())
                        })
                        .collect(Collectors.toList())
            );
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
