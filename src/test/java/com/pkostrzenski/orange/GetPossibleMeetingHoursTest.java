package com.pkostrzenski.orange;

import com.pkostrzenski.orange.models.TimeInterval;
import com.pkostrzenski.orange.models.UserCalendar;
import com.pkostrzenski.orange.services.CalendarService;
import com.pkostrzenski.orange.services.CalendarServiceImpl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import com.pkostrzenski.orange.utils.DateConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(JUnit4.class)
public class GetPossibleMeetingHoursTest {

    private CalendarService calendarService;

    @Before
    public void init() {
        calendarService = new CalendarServiceImpl();
    }

    // the case provided in an email
    @Test
    public void test_CalendarService_GetPossibleMeetingDates_Correct() {
        List<TimeInterval> possibleMeetingHours = calendarService.getPossibleMeetingHours(
                getExampleCorrectUserCalendar_1(),
                getExampleCorrectUserCalendar_2(),
                "0:30"
        );

        // in an email, the example data was not correct, the third meeting hours collide with first calendar
        assertThat(possibleMeetingHours.size(), is(2));
        try {
            assertThat(possibleMeetingHours.get(0).getStart(), equalTo(DateConverter.fromStringToLong("11:30")));
            assertThat(possibleMeetingHours.get(0).getEnd(), equalTo(DateConverter.fromStringToLong("12:00")));
            assertThat(possibleMeetingHours.get(1).getStart(), equalTo(DateConverter.fromStringToLong("15:00")));
            assertThat(possibleMeetingHours.get(1).getEnd(), equalTo(DateConverter.fromStringToLong("16:00")));
        } catch (ParseException e) { } // won't happen
    }

    @Test
    public void test_CalendarService_GetPossibleMeetingDates_Correct_DifferentMeetingDuration() {
        List<TimeInterval> possibleMeetingHours = calendarService.getPossibleMeetingHours(
                getExampleCorrectUserCalendar_1(),
                getExampleCorrectUserCalendar_2(),
                "0:45"
        );
        assertThat(possibleMeetingHours.size(), is(1));
        try {
            assertThat(possibleMeetingHours.get(0).getStart(), equalTo(DateConverter.fromStringToLong("15:00")));
            assertThat(possibleMeetingHours.get(0).getEnd(), equalTo(DateConverter.fromStringToLong("16:00")));
        } catch (ParseException e) { }
    }

    @Test
    public void test_CalendarService_GetPossibleMeetingDates_Correct_NoHours() {
        List<TimeInterval> possibleMeetingHours = calendarService.getPossibleMeetingHours(
                getExampleCorrectUserCalendar_1(),
                getExampleCorrectUserCalendar_2(),
                "1:30"
        );
        assertTrue(possibleMeetingHours.isEmpty());
    }

    @Test
    public void test_CalendarService_GetPossibleMeetingDates_Correct_NoMeetings() {
        UserCalendar calendar1 = new UserCalendar(new TimeInterval("10:00", "18:30"), Collections.emptyList());
        UserCalendar calendar2 = new UserCalendar(new TimeInterval("11:00", "20:30"), Collections.emptyList());
        List<TimeInterval> possibleMeetingHours = calendarService.getPossibleMeetingHours(calendar1, calendar2, "00:30");

        assertThat(possibleMeetingHours.size(), is(1));
        try {
            assertThat(possibleMeetingHours.get(0).getStart(), equalTo(DateConverter.fromStringToLong("11:00")));
            assertThat(possibleMeetingHours.get(0).getEnd(), equalTo(DateConverter.fromStringToLong("18:30")));
        } catch (ParseException e) { }
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_CalendarService_GetPossibleMeetingDates_Incorrect_OverlapingMeetings() {
        List<TimeInterval> meetings = new ArrayList<>();
        meetings.add(new TimeInterval("10:00", "11:00"));
        meetings.add(new TimeInterval("10:30", "12:00"));
        UserCalendar calendar = new UserCalendar(new TimeInterval("10:00", "18:30"), meetings);
        calendarService.getPossibleMeetingHours(calendar, getExampleCorrectUserCalendar_1(), "00:30");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_CalendarService_GetPossibleMeetingDates_Incorrect_IncorrectMeetingDuration_1() {
        List<TimeInterval> meetings = new ArrayList<>();
        UserCalendar calendar = new UserCalendar(new TimeInterval("10:00", "18:30"), meetings);
        calendarService.getPossibleMeetingHours(calendar, getExampleCorrectUserCalendar_1(), ":");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_CalendarService_GetPossibleMeetingDates_Incorrect_IncorrectMeetingDuration_2() {
        List<TimeInterval> meetings = new ArrayList<>();
        UserCalendar calendar = new UserCalendar(new TimeInterval("10:00", "18:30"), meetings);
        calendarService.getPossibleMeetingHours(calendar, getExampleCorrectUserCalendar_1(), "a:30");
    }

    private UserCalendar getExampleCorrectUserCalendar_1() {
        List<TimeInterval> meetings = new ArrayList<>();
        meetings.add(new TimeInterval("09:00", "10:30"));
        meetings.add(new TimeInterval("12:00", "13:00"));
        meetings.add(new TimeInterval("16:00", "18:30"));
        return new UserCalendar(
                new TimeInterval("09:00", "20:00"),
                meetings
        );
    }

    private UserCalendar getExampleCorrectUserCalendar_2() {
        List<TimeInterval> meetings = new ArrayList<>();
        meetings.add(new TimeInterval("10:00", "11:30"));
        meetings.add(new TimeInterval("12:30", "14:30"));
        meetings.add(new TimeInterval("14:30", "15:00"));
        meetings.add(new TimeInterval("16:00", "17:00"));
        return new UserCalendar(
                new TimeInterval("10:00", "18:30"),
                meetings
        );
    }


}
