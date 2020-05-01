package com.pkostrzenski.orange.services;

import com.pkostrzenski.orange.models.TimeInterval;
import com.pkostrzenski.orange.models.UserCalendar;
import com.pkostrzenski.orange.utils.DateConverter;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.util.*;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Override
    public List<TimeInterval> getPossibleMeetingHours(
            UserCalendar firstCalendar,
            UserCalendar secondCalendar,
            String meetingDuration
    ) throws IllegalArgumentException {

        Long meetingDurationInMilis;
        try {
            meetingDurationInMilis = DateConverter.fromStringToLong(meetingDuration);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Incorrect meeting duration!");
        }
        if (firstCalendar.isIncorrect() || secondCalendar.isIncorrect())
            throw new IllegalArgumentException("Provided calendars are incorrect (eg. overlaping meetings)");

        List<TimeInterval> freeTimeIntervalsInFirstCalendar = extractFreeTimeIntervals(firstCalendar);
        List<TimeInterval> freeTimeIntervalsInSecondCalendar = extractFreeTimeIntervals(secondCalendar);

        return findPossibleMeetingHours(
                freeTimeIntervalsInFirstCalendar,
                freeTimeIntervalsInSecondCalendar,
                meetingDurationInMilis
        );
    }

    private List<TimeInterval> extractFreeTimeIntervals(UserCalendar calendar) {
        List<Long> timePoints = buildWorkdayTimeline(calendar);
        return findFreeTimeIntervalsInTimeline(timePoints);
    }

    private List<Long> buildWorkdayTimeline(UserCalendar calendar){
        List<Long> timePoints = new LinkedList<>();

        timePoints.add(calendar.getWorkingHours().getStart());
        for (TimeInterval meeting : calendar.getPlannedMeetings()) {
            timePoints.add(meeting.getStart());
            timePoints.add(meeting.getEnd());
        }
        timePoints.add(calendar.getWorkingHours().getEnd());

        return timePoints;
    }

    private List<TimeInterval> findFreeTimeIntervalsInTimeline(List<Long> timePoints){
        List<TimeInterval> freeTimeIntervals = new LinkedList<>();

        for (int i = 0; i < timePoints.size() - 1; i += 2)
            if (timePoints.get(i + 1) > timePoints.get(i))
                freeTimeIntervals.add(
                        new TimeInterval(timePoints.get(i), timePoints.get(i + 1))
                );

        return freeTimeIntervals;
    }

    private List<TimeInterval> findPossibleMeetingHours(
            List<TimeInterval> firstFreeHours,
            List<TimeInterval> secondFreeHours,
            Long minimalMeetingDuration
    ) {
        List<TimeInterval> allHours = new LinkedList<>();
        allHours.addAll(firstFreeHours);
        allHours.addAll(secondFreeHours);

        Collections.sort(allHours);
        List<TimeInterval> possibleMeetingHours = new LinkedList<>();

        for (int i = 0; i < allHours.size() - 1; ++i)
            if (allHours.get(i).getEnd() - allHours.get(i + 1).getStart() >= minimalMeetingDuration)
                possibleMeetingHours.add(
                        new TimeInterval(allHours.get(i + 1).getStart(), allHours.get(i).getEnd())
                );

        return possibleMeetingHours;
    }
}
