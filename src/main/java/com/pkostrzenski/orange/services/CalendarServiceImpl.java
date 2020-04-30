package com.pkostrzenski.orange.services;

import com.pkostrzenski.orange.models.UserCalendar;
import java.util.Collections;
import java.util.List;

public class CalendarServiceImpl implements CalendarService {

    @Override
    public List<String[]> getPossibleMeetingDates(
            UserCalendar firstCalendar,
            UserCalendar secondCalendar,
            String meetingDuration
    ) throws IllegalArgumentException {
        return Collections.emptyList();
    }
}
