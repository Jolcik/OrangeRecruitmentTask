package com.pkostrzenski.orange.models;

import java.text.SimpleDateFormat;

public class TimeInterval {

    static final long INCORRECT_TIME_FORMAT = -1;

    private long start;
    private long end;

    public TimeInterval() { }

    public TimeInterval(String start, String end) {
        try {
            this.start = new SimpleDateFormat("HH:mm").parse(start).getTime();
            this.end = new SimpleDateFormat("HH:mm").parse(end).getTime();
        } catch (Exception e){
            this.start = TimeInterval.INCORRECT_TIME_FORMAT;
            this.end = TimeInterval.INCORRECT_TIME_FORMAT;
        }
    }

    public TimeInterval(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public boolean isCorrect(){
        return start == TimeInterval.INCORRECT_TIME_FORMAT
                || end == TimeInterval.INCORRECT_TIME_FORMAT;
    }
}
