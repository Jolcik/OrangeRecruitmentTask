package com.pkostrzenski.orange.services;

import com.pkostrzenski.orange.models.TimeInterval;
import com.pkostrzenski.orange.models.UserCalendar;
import java.util.List;

public interface CalendarService {
    List<TimeInterval> getPossibleMeetingHours(
            UserCalendar firstCalendar,
            UserCalendar secondCalendar,
            String meetingDuration
    ) throws IllegalArgumentException;
}
