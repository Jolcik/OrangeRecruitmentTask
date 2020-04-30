package com.pkostrzenski.orange.models;

public class TimeInterval {

    private String start;
    private String end;

    public TimeInterval() { }

    public TimeInterval(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
