package com.fmph.diplomovka.raptor;


import com.fmph.diplomovka.model.DayType;
import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.raptor.dataStructure.DataStructure;
import com.fmph.diplomovka.raptor.raptorStructure.RouteStop;
import com.fmph.diplomovka.raptor.raptorStructure.StopRoundKey;
import com.fmph.diplomovka.raptor.raptorStructure.StopTimeAndPrevStop;
import com.fmph.diplomovka.service.StopDataService;
import com.fmph.diplomovka.services.models.Path;
import com.fmph.diplomovka.services.models.SearchParams;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class Algorithm {

    private final DayService dayService;
    private final DataStructure dataStructure;
    private final StopDataService stopDataService;


    private Map<StopRoundKey, StopTimeAndPrevStop> earliestArrivals;
    private List<Stop> markedStops = new ArrayList<>();
    private List<RouteStop> queue;

    public Algorithm(DayService dayService, DataStructure dataStructure, StopDataService stopDataService) {
        this.dayService = dayService;
        this.dataStructure = dataStructure;
        this.stopDataService = stopDataService;
    }

    public Path returnShortestPath(SearchParams searchParams) {
        LocalDateTime searchedDateTime = getDateTimeFromMs(searchParams.getTime());
        Set<DayType> searchedDayTypes = dayService.getDayTypesBelongToDate(searchedDateTime.toLocalDate());
        Stop startStop = searchParams.getFromStop();
        Stop endStop = searchParams.getToStop();
        Long startTime =  searchParams.getTime();
        Integer numberOfRounds = searchParams.getMaxNumberOfTransfers();
        markedStops.add(startStop);
        initializeEarliestArrivals(numberOfRounds, stopDataService.getAll(), startStop, startTime);
        raptorAlgorithm(numberOfRounds, startTime, endStop);
        return buildPath();
    }

    private void initializeEarliestArrivals(Integer numberOfRounds, List<Stop> stops, Stop startStop, Long startTime) {
        for(Stop stop: stops) {
            for(int round = 0; round < numberOfRounds; round++) {
                StopRoundKey srk = new StopRoundKey(stop.getId(), round);
                StopTimeAndPrevStop st;
                if(stop.equals(startStop)) {
                    st = new StopTimeAndPrevStop(startTime, null);
                }
                else{
                    st = new StopTimeAndPrevStop(Long.MAX_VALUE, null);
                }
                earliestArrivals.put(srk, st);
            }
        }
    }

    private void raptorAlgorithm(Integer numberOfRounds, Long startTime, Stop finalStop) {
        for(int round = 0; round < numberOfRounds; round++) {
            accumulateRoutesServingMarkedStops();
            traverseRoutes(round, startTime, finalStop);
            if(markedStops.isEmpty()){
                return;
            }
        }
    }

    private void accumulateRoutesServingMarkedStops(){
        queue = new ArrayList<>();
        for(Stop markedStop: markedStops) {
            for (Route route : dataStructure.getDataStructureModel().getStopRoutes()) {
                Optional<RouteStop> existingRouteStopInQueue = getAllRouteStopsWithRoute(route);
                if(existingRouteStopInQueue.isPresent()) {
                    if(stop1BeforeStop2(markedStop, existingRouteStopInQueue.get().getStop(), route)){
                        queue.remove(existingRouteStopInQueue);
                        queue.add(new RouteStop(route, markedStop));
                    }
                } else {
                    queue.add(new RouteStop(route, markedStop));
                }
            }
            markedStops.remove(markedStop);
        }
    }

    private void traverseRoutes(int round, Long startTime, Stop finalStop) {
        for(RouteStop routeStop: queue){
            Integer[] array = dataStructure.getDataStructureModel().getRoutes().get(routeStop.getRoute());
            int startStopTimesId = array[2];
            int numberOfTripsForRoute = array[3];
            int numberOfStopsForRoute = array[1];
            Long startingTime;
            if(round == 0){
                startingTime = startTime;
            }
            else{
                startingTime = getEATimeForStopAndRound(routeStop.getStop().getId(), round - 1);
            }
            int stopId = getIdOfStopInRoute(routeStop.getRoute(), routeStop.getStop());
            int currentTripId = 0; //Tnaopak
            int currentStopOnTripId = -1; //p
            for(int i = startStopTimesId + stopId; i < numberOfStopsForRoute * numberOfTripsForRoute; i+=numberOfStopsForRoute ){
                Long time = dataStructure.getDataStructureModel().getStopTimes().get(i).getTime();
                if(time >= startingTime){
                    currentStopOnTripId = i;
                    break;
                }
                currentTripId++;
            }
            int tripId = currentTripId; //t
            //prechadzam p' zastavky v tripe t nasledujuce za zastavkou p
            for(int p = currentStopOnTripId; p < currentStopOnTripId + numberOfStopsForRoute; p++) {
                if(tripId != currentTripId && eaTimeGetBetterForTripAndStopAtRound(p, routeStop.getStop(), round, finalStop.getId())){
                    break;
                }
                else{
                    tripId = 0;
                }
            }
        }
    }

    private Path buildPath() {
        return null;
    }

    private boolean eaTimeGetBetterForTripAndStopAtRound(int stopTimesId, Stop stop, int round, long finalStopId){
        long earliestToFinalStop = earliestArrivals.get(new StopRoundKey(finalStopId, round)).getTime();
        long earliestToStop = earliestArrivals.get(new StopRoundKey(stop.getId(), round)).getTime();
        long arrivalAtStopInTrip = dataStructure.getDataStructureModel().getStopTimes().get(stopTimesId).getTime();
        return arrivalAtStopInTrip < Long.min(earliestToStop, earliestToFinalStop);
    }

    private Long getEATimeForStopAndRound(Long stopId, int round) {
        return earliestArrivals.get(new StopRoundKey(stopId, round)).getTime();
    }


    private Optional<RouteStop> getAllRouteStopsWithRoute(Route route){
        return queue.stream().filter(routeStop -> routeStop.getRoute().equals(route)).findFirst();
    }

    private LocalDateTime getDateTimeFromMs(Long ms) {
        return Instant.ofEpochMilli(ms).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private boolean stop1BeforeStop2(Stop stop1, Stop stop2, Route route){
        Integer[] array = dataStructure.getDataStructureModel().getRoutes().get(route.getId());
        int stopStartId = array[0];
        int numberOfStops = array[1];
        for(int i = stopStartId; i < stopStartId + numberOfStops; i++) {
            if(stopIsInRouteStopOnIndex(stop1, i)){
                return true;
            }
            else if(stopIsInRouteStopOnIndex(stop2, i)){
                return false;
            }
        }
        return false;
    }

    private boolean stopIsInRouteStopOnIndex(Stop stop, int i){
        return dataStructure.getDataStructureModel().getRouteStops().get(i).equals(stop);
    }

    private Integer getIdOfStopInRoute(Route route, Stop stop) {
        Integer[] array = dataStructure.getDataStructureModel().getRoutes().get(route.getId());
        int startStopInx = array[0];
        int numberOfStopsForRoute = array[1];
        int stopId = 0;
        for(int i = startStopInx; i < numberOfStopsForRoute + startStopInx; i++){
            if(dataStructure.getDataStructureModel().getRouteStops().get(i).equals(stop)){
                return stopId;
            }
            stopId++;
        }
        return stopId;
    }
}
