package com.fmph.diplomovka.raptor.dataStructure;

import com.fmph.diplomovka.model.*;
import com.fmph.diplomovka.service.RouteDataService;
import com.fmph.diplomovka.service.StopDataService;
import com.fmph.diplomovka.service.TripDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DataStructureService {

    private final StopDataService stopDataService;
    private final RouteDataService routeDataService;
    private final TripDataService tripDataService;

    public DataStructureService(StopDataService stopDataService, RouteDataService routeDataService, TripDataService tripDataService) {
        this.stopDataService = stopDataService;
        this.routeDataService = routeDataService;
        this.tripDataService = tripDataService;
    }

    @Transactional
    public DataStructureModel createDataStructure() {
        List<TimeObject> stopTimes = new ArrayList<>();
        Map<Long, Integer[]> routes = new HashMap<>();
        List<Stop> routeStops = new ArrayList<>();
        List<Integer> transfers = new ArrayList<>();
        Map<Long, Integer[]> stops = new HashMap<>();
        List<Route> stopRoutes = new ArrayList<>();
        fillDataStructure(stopTimes, routes, routeStops);
        fillDataStructureWithTransfers(transfers, stops, stopRoutes);
        return new DataStructureModel(stopTimes, routes, routeStops, transfers, stops, stopRoutes);
    }

    private void fillDataStructure(List<TimeObject> stopTimes, Map<Long, Integer[]> routes, List<Stop> routeStops) {
        List<Route> routesList = routeDataService.getAll();
        int startRouteStopsIdx = 0;
        int startStopTimesIdx = 0;
        for (Route route : routesList) {
            List<Trip> tripsForRoute = route.getTrips();
            List<Stop> stopsForRoute;
            if(tripsForRoute.size() > 0) {
                stopsForRoute = stopDataService.getStopsForTrip(tripsForRoute.get(0));
            }
            else{
                stopsForRoute = new ArrayList<>();
            }
            routes.put(route.getId(), new Integer[]{startRouteStopsIdx, stopsForRoute.size(), startStopTimesIdx, tripsForRoute.size()});
            addStopsForRouteToRouteStops(stopsForRoute, routeStops);
            addTimesForRoute(tripsForRoute, stopTimes);
            startRouteStopsIdx += stopsForRoute.size();
            startStopTimesIdx += (stopsForRoute.size() * tripsForRoute.size());
        }
    }

    private void fillDataStructureWithTransfers(List<Integer> transfers, Map<Long, Integer[]> stops, List<Route> stopRoutes) {
        List<Stop> stopList = stopDataService.getAll();
        int startTransfers = 0;
        int startStopRoutes = 0;
        for(Stop stop: stopList) {
            List<Route> routesForStop = routeDataService.getAllWithStop(stop);
            stops.put(stop.getId(), new Integer[]{startTransfers, 0, startStopRoutes, routesForStop.size()});
            addRoutesForStopToStopRoutes(routesForStop, stopRoutes);
            startStopRoutes += routesForStop.size();
        }
    }

    private void addRoutesForStopToStopRoutes(List<Route> routes, List<Route> stopRoutes) {
        routes.forEach(route -> stopRoutes.add(route));
    }

    private void addStopsForRouteToRouteStops(List<Stop> stopsForRoute, List<Stop> routeStops) {
        stopsForRoute.forEach(stop -> routeStops.add(stop));
    }

    private void addTimesForRoute(List<Trip> tripsForRoute, List<TimeObject> stopTimes) {
        for (Trip trip : tripsForRoute) {
            for (StopTime stopTime : trip.getStopTimes()) {
                Long time = convertStringToTime(stopTime.getTime());
//                addStopTimeIfNotExists(stopTimes, time, trip.getDayType());
                stopTimes.add(createTimeObject(time, trip.getDayType()));
            }
        }
    }

//    private void addStopTimeIfNotExists(List<TimeObject> stopTimes, Time time, DayType dayType) {
//        List<TimeObject> existingStopTimes = stopTimes.stream().filter(st -> st.getTime().equals(time)).collect(Collectors.toList());
//        if(existingStopTimes.size() > 0) {
//            existingStopTimes.forEach(st -> addDateTypeToTimeObject(st, dayType));
//        }
//        stopTimes.add(createTimeObject(time, dayType));
//    }

//    private void addDateTypeToTimeObject(TimeObject timeObject, DayType dayType){
//        timeObject.getDayTypes().add(dayType);
//    }

    private TimeObject createTimeObject(Long time, DayType dayType) {
//        Set<DayType> dayTypes = new HashSet<>();
//        dayTypes.add(dayType);
        return new TimeObject(time, 0, dayType);
    }

    private Long convertStringToTime(String stringTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long ms = 0;
        try {
            ms = sdf.parse(stringTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        return new Time(ms);
        return ms;
    }

}
