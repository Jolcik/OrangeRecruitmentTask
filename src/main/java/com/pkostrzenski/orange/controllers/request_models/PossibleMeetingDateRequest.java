package com.pkostrzenski.orange.controllers.request_models;

import com.pkostrzenski.orange.models.UserCalendar;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PossibleMeetingDateRequest {

    @NotBlank
    private String meetingDuration;
    @NotNull
    private UserCalendar firstCalendar;
    @NotNull
    private UserCalendar secondCalendar;

    public PossibleMeetingDateRequest() { }

    public PossibleMeetingDateRequest(String meetingDuration, UserCalendar firstCalendar, UserCalendar secondCalendar) {
        this.meetingDuration = meetingDuration;
        this.firstCalendar = firstCalendar;
        this.secondCalendar = secondCalendar;
    }

    public String getMeetingDuration() {
        return meetingDuration;
    }

    public UserCalendar getFirstCalendar() {
        return firstCalendar;
    }

    public UserCalendar getSecondCalendar() {
        return secondCalendar;
    }
}
