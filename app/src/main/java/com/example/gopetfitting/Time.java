package com.example.gopetfitting;

import java.util.Arrays;
import java.util.List;

public class Time {
    private int hour;
    private int minute;
    private int second;

    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public List<Integer> getTime() {
        return Arrays.asList(hour, minute, second);
    }
}
