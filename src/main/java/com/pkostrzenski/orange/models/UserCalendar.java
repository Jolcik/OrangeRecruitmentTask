package com.pkostrzenski.orange.models;

import java.util.List;

public class UserCalendar {

    private TimeInterval workingHours;
    private List<TimeInterval> plannedMeetings;

    public UserCalendar() { }

    public UserCalendar(TimeInterval workingHours, List<TimeInterval> plannedMeetings) {
        this.workingHours = workingHours;
        this.plannedMeetings = plannedMeetings;
    }

    public TimeInterval getWorkingHours() {
        return workingHours;
    }

    public List<TimeInterval> getPlannedMeetings() {
        return plannedMeetings;
    }
}
