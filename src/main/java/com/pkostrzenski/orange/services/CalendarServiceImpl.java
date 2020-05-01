package com.pkostrzenski.orange.services;

import com.pkostrzenski.orange.models.TimeInterval;
import com.pkostrzenski.orange.models.UserCalendar;
import com.pkostrzenski.orange.utils.DateConverter;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
            meetingDurationInMilis = DateConverter.fromStringToLong(meetingDuration);
        } catch (ParseException e){
            throw new IllegalArgumentException("Incorrect meeting duration!");
        }
        if(firstCalendar.isIncorrect() || secondCalendar.isIncorrect())
            throw new IllegalArgumentException("Provided calendars are incorrect (eg. overlaping meetings)");

        List<TimeInterval> freePeriodsInFirstCalendar = extractFreeTimePeriods(firstCalendar, meetingDurationInMilis);
        List<TimeInterval> freePeriodsInSecondCalendar = extractFreeTimePeriods(secondCalendar, meetingDurationInMilis);


        return findPossibleMeetingHours(freePeriodsInFirstCalendar, freePeriodsInSecondCalendar, meetingDurationInMilis)
                .stream()
                .map(timeInterval -> new String[]{
                        DateConverter.fromLongToString(timeInterval.getStart()),
                        DateConverter.fromLongToString(timeInterval.getEnd())
                })
                .collect(Collectors.toList());
    }

    private List<TimeInterval> extractFreeTimePeriods(UserCalendar calendar){
        return extractFreeTimePeriods(calendar, 0L);
    }

    private List<TimeInterval> extractFreeTimePeriods(UserCalendar calendar, Long minimalPeriodDuration){
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
            if(timePoints.get(i+1) - timePoints.get(i) >= minimalPeriodDuration)
                freePeriods.add(
                        new TimeInterval(timePoints.get(i), timePoints.get(i + 1))
                );
        return freePeriods;
    }

    private List<TimeInterval> findPossibleMeetingHours(
            List<TimeInterval> firstFreeHours,
            List<TimeInterval> secondFreeHours,
            Long minimalDuration
    ){
        List<TimeInterval> allHours = new LinkedList<>();
        allHours.addAll(firstFreeHours);
        allHours.addAll(secondFreeHours);

        Collections.sort(allHours);
        List<TimeInterval> possibleMeetingHours = new LinkedList<>();
        for(int i = 0; i < allHours.size()-1; ++i)
            if(allHours.get(i).getEnd() - allHours.get(i+1).getStart() >= minimalDuration)
                possibleMeetingHours.add(new TimeInterval(allHours.get(i+1).getStart(), allHours.get(i).getEnd()));

        return possibleMeetingHours;
    }
}
