package com.fmph.diplomovka.model;

import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.SortedSet;

@Entity
@Table(name = "trips")
public class Trip implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "day_type", nullable=false)
    @Enumerated(EnumType.STRING)
    private DayType dayType;

    @Column(name = "low_floor", nullable = false)
    private Boolean lowFloor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn
    private Route route;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    @SortNatural
    private SortedSet<StopTime> stopTimes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayType getDayType() {
        return dayType;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }

    public Boolean getLowFloor() {
        return lowFloor;
    }

    public void setLowFloor(Boolean lowFloor) {
        this.lowFloor = lowFloor;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public SortedSet<StopTime> getStopTimes() {
        return stopTimes;
    }

    public void setStopTimes(SortedSet<StopTime> stopTimes) {
        this.stopTimes = stopTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return id.equals(trip.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
