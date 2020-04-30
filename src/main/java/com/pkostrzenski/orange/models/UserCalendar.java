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

    public boolean isIncorrect(){
        return isAnyMeetingIncorrect() || workingHours.isIncorrect() || isCollisionInMeetingHours();
    }

    private boolean isAnyMeetingIncorrect(){
        for(TimeInterval meeting: plannedMeetings)
            if(meeting.isIncorrect())
                return true;

        return false;
    }

    private boolean isCollisionInMeetingHours(){
        for(int i = 0; i < plannedMeetings.size() - 1; ++i)
            if(plannedMeetings.get(i).getEnd() > plannedMeetings.get(i+1).getStart())
                return true;

        return false;
    }
}
