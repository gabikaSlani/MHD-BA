package com.fmph.diplomovka.raptor.dataStructure;


import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.Stop;

import java.util.List;
import java.util.Map;

public class DataStructureModel {

    private List<TimeObject> stopTimes;
    private Map<Long, Integer[]> routes;
    private List<Stop> routeStops;
    private List<Integer> transfers;
    private Map<Long, Integer[]> stops;
    private List<Route> stopRoutes;

    public DataStructureModel(List<TimeObject> stopTimes, Map<Long, Integer[]> routes, List<Stop> routeStops, List<Integer> transfers,  Map<Long, Integer[]> stops, List<Route> stopRoutes) {
        this.stopTimes = stopTimes;
        this.routes = routes;
        this.routeStops = routeStops;
        this.transfers = transfers;
        this.stops = stops;
        this.stopRoutes = stopRoutes;
    }

    public DataStructureModel() {
    }

    public List<TimeObject> getStopTimes() {
        return stopTimes;
    }

    public void setStopTimes(List<TimeObject> stopTimes) {
        this.stopTimes = stopTimes;
    }

    public  Map<Long, Integer[]> getRoutes() {
        return routes;
    }

    public void setRoutes( Map<Long, Integer[]> routes) {
        this.routes = routes;
    }

    public List<Stop> getRouteStops() {
        return routeStops;
    }

    public void setRouteStops(List<Stop> routeStops) {
        this.routeStops = routeStops;
    }

    public List<Integer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Integer> transfers) {
        this.transfers = transfers;
    }

    public  Map<Long, Integer[]> getStops() {
        return stops;
    }

    public void setStops(Map<Long, Integer[]> stops) {
        this.stops = stops;
    }

    public List<Route> getStopRoutes() {
        return stopRoutes;
    }

    public void setStopRoutes(List<Route> stopRoutes) {
        this.stopRoutes = stopRoutes;
    }
}
