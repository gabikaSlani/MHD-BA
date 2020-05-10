package com.fmph.diplomovka.raptor.dataStructure.models;

import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.Stop;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Subroute implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private Integer length;
    private Map<Long, List<SubrouteTrip>> subrouteTrips = new HashMap<>();
    private List<Stop> stops;
    private Route route;

    private boolean direction;

    public Subroute() {
    }

    public Subroute(String id, Integer length, Route route, List<Stop> stops, boolean direction) {
        this.id = id;
        this.length = length;
        this.route = route;
        this.stops = stops;
        this.direction = direction;
    }

    public Subroute(String id, Integer length, Route route, List<Stop> stops, Map<Long, List<SubrouteTrip>> subrouteTrips, boolean direction) {
        this.id = id;
        this.length = length;
        this.route = route;
        this.stops = stops;
        this.subrouteTrips = subrouteTrips;
        this.direction = direction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public Map<Long, List<SubrouteTrip>> getSubrouteTrips() {
        return subrouteTrips;
    }

    public void setSubrouteTrips(Map<Long, List<SubrouteTrip>> subrouteTrips) {
        this.subrouteTrips = subrouteTrips;
    }


    public boolean isDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subroute subroute = (Subroute) o;
        return Objects.equals(id, subroute.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
