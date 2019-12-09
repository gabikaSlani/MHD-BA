package com.fmph.diplomovka.services.models;

import com.fmph.diplomovka.model.Stop;

import java.util.List;

public class Path {

    private Stop startStop;
    private Stop endStop;
    private Integer distance;
    private Integer duration;
    private String date;
    private List<Action> actions;

    public Path(Stop startStop, Stop endStop, Integer distance, Integer duration, String date, List<Action> actions) {
        this.startStop = startStop;
        this.endStop = endStop;
        this.distance = distance;
        this.duration = duration;
        this.date = date;
        this.actions = actions;
    }


    public Stop getStartStop() {
        return startStop;
    }

    public void setStartStop(Stop startStop) {
        this.startStop = startStop;
    }

    public Stop getEndStop() {
        return endStop;
    }

    public void setEndStop(Stop endStop) {
        this.endStop = endStop;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
