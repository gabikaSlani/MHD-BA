package com.fmph.diplomovka.raptor.dataStructure;

import com.fmph.diplomovka.model.DayType;

public class TimeObject {

    private Long time;
    private Integer delay;
    private DayType dayType;


    public TimeObject(Long time, Integer delay, DayType dayType) {
        this.time = time;
        this.delay = delay;
        this.dayType = dayType;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public DayType getDayType() {
        return dayType;
    }

    public void setDayType(DayType dayTypes) {
        this.dayType = dayTypes;
    }
}