package com.pkostrzenski.orange.services;

import com.pkostrzenski.orange.models.TimeInterval;
import com.pkostrzenski.orange.models.UserCalendar;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Override
    public List<String[]> getPossibleMeetingDates(
            UserCalendar firstCalendar,
            UserCalendar secondCalendar,
            String meetingDuration
    ) throws IllegalArgumentException {

        Long meetingDurationInMilis;
        try {
            meetingDurationInMilis = new SimpleDateFormat("HH:mm").parse(meetingDuration).getTime();
        } catch (ParseException e){
            throw new IllegalArgumentException("Incorrect meeting duration!");
        }
        if(firstCalendar.isIncorrect() || secondCalendar.isIncorrect())
            throw new IllegalArgumentException("Provided calendars are incorrect (eg. overlaping meetings)");
        
        List<String[]> strings = new LinkedList<>();
        for(TimeInterval interval: extractFreeTimePeriods(firstCalendar))
            strings.add(new String[] {
                    new SimpleDateFormat("HH:mm").format(new Date(interval.getStart())),
                    new SimpleDateFormat("HH:mm").format(new Date(interval.getEnd()))
            });

        return strings;
    }

    private List<TimeInterval> extractFreeTimePeriods(UserCalendar calendar){
        return extractFreeTimePeriods(calendar, 0);
    }

    private List<TimeInterval> extractFreeTimePeriods(UserCalendar calendar, long minimalPeriodDuration){
        List<Long> timePoints = new LinkedList<>();

        // build timeline
        timePoints.add(calendar.getWorkingHours().getStart());
        for(TimeInterval meeting: calendar.getPlannedMeetings()){
            timePoints.add(meeting.getStart());
            timePoints.add(meeting.getEnd());
        }
        timePoints.add(calendar.getWorkingHours().getEnd());

        // extract time periods
        List<TimeInterval> freePeriods = new LinkedList<>();
        for(int i = 0; i < timePoints.size()-1; i += 2)
            if(timePoints.get(i+1) - timePoints.get(i) > minimalPeriodDuration)
                freePeriods.add(
                    new TimeInterval(timePoints.get(i), timePoints.get(i+1))
                );

        return freePeriods;
    }
}
