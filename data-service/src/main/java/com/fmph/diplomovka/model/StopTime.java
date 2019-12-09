package com.fmph.diplomovka.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "stop_times")
public class StopTime implements Comparable<StopTime>, Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    private Stop stop;

    @Id
    @ManyToOne
    @JoinColumn
    private Trip trip;

    @Column(name = "time", nullable = false)
    private String time;

    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StopTime stopTime = (StopTime) o;
        return stop.equals(stopTime.stop) &&
                trip.equals(stopTime.trip) &&
                time.equals(stopTime.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stop, trip, time);
    }

    @Override
    public int compareTo(StopTime o) {
        return time.compareTo(o.time);
    }
}
