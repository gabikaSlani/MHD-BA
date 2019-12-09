package com.fmph.diplomovka.services.models;

import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.model.StopTime;

import java.util.List;

public class TripSegment {

    private List<StopTime> stops;
    private Stop finalStop;
    private Route route;
    private Integer delay;
    private boolean lowFloor;

    public TripSegment(List<StopTime> stops, Stop finalStop, Route route, Integer delay, boolean lowFloor) {
        this.stops = stops;
        this.finalStop = finalStop;
        this.route = route;
        this.delay = delay;
        this.lowFloor = lowFloor;
    }

    public List<StopTime> getStops() {
        return stops;
    }

    public void setStops(List<StopTime> stops) {
        this.stops = stops;
    }

    public Stop getFinalStop() {
        return finalStop;
    }

    public void setFinalStop(Stop finalStop) {
        this.finalStop = finalStop;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public boolean isLowFloor() {
        return lowFloor;
    }

    public void setLowFloor(boolean lowFloor) {
        this.lowFloor = lowFloor;
    }
}
