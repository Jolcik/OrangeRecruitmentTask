package com.pkostrzenski.orange.services;

import com.pkostrzenski.orange.models.UserCalendar;

import java.util.List;

public interface CalendarService {
    List<String[]> getPossibleMeetingDates(
            UserCalendar firstCalendar,
            UserCalendar secondCalendar,
            String meetingDuration
    ) throws IllegalArgumentException;
}
