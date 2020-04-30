package com.pkostrzenski.orange.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.pkostrzenski.orange.utils.DateConverter;

import java.text.SimpleDateFormat;

public class TimeInterval implements Comparable<TimeInterval>{

    static final Long INCORRECT_TIME_FORMAT = -1L;

    private Long start;
    private Long end;

    public TimeInterval() { }

    @JsonCreator
    public TimeInterval(String start, String end) {
        try {
            this.start = DateConverter.fromStringToLong(start);
            this.end = DateConverter.fromStringToLong(end);
        } catch (Exception e){
            this.start = TimeInterval.INCORRECT_TIME_FORMAT;
            this.end = TimeInterval.INCORRECT_TIME_FORMAT;
        }
    }

    public TimeInterval(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public Long getStart() {
        return start;
    }

    public Long getEnd() {
        return end;
    }

    public boolean isIncorrect(){
        return start.equals(TimeInterval.INCORRECT_TIME_FORMAT)
                || end.equals(TimeInterval.INCORRECT_TIME_FORMAT);
    }

    @Override
    public int compareTo(TimeInterval o) {
        return start.compareTo(o.start);
    }
}
