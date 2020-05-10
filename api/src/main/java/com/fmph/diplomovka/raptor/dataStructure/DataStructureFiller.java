package com.fmph.diplomovka.raptor.dataStructure;

import static java.util.stream.Collectors.toList;

import com.fmph.diplomovka.model.CalendarDate;
import com.fmph.diplomovka.model.FootPath;
import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.ServiceDay;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.model.StopTime;
import com.fmph.diplomovka.model.Trip;
import com.fmph.diplomovka.raptor.dataStructure.models.StopTimeObject;
import com.fmph.diplomovka.raptor.dataStructure.models.Subroute;
import com.fmph.diplomovka.raptor.dataStructure.models.SubrouteTrip;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import com.fmph.diplomovka.raptor.dataStructure.models.Transfer;
import com.fmph.diplomovka.raptor.dataStructure.sorters.SortSubrouteTrips;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class DataStructureFiller {

  public void fillDataStructure(Map<Long, List<Transfer>> stopTransfers,
      Map<Route, List<Subroute>> routeSubroutes, Map<Stop, List<String>> stopSubroutes,
      Map<String, Map<Long, Integer>> stopIndexInSubroute, List<Trip> allTrips,
      List<FootPath> allFootPaths, List<Stop> allStops, Map<String, Subroute> subroutesByIndex) {

    List<Subroute> subroutes = new ArrayList<>();

    System.out.println("Start filling data structure");
    long startTime = System.nanoTime();
    fillStopTransfers(stopTransfers, allFootPaths);
    fillSubroutes(subroutes, routeSubroutes, allTrips);
    fillStopSubroutes(subroutes, stopSubroutes, stopIndexInSubroute, allStops, subroutesByIndex);
    long stopTime = System.nanoTime();
    System.out
        .println("Finished filling data structure in " + (stopTime - startTime) + " seconds");
  }

  private void fillStopSubroutes(List<Subroute> subroutes, Map<Stop, List<String>> stopSubroutes,
      Map<String, Map<Long, Integer>> stopIndexInSubroute, List<Stop> allStops,
      Map<String, Subroute> subroutesByIndex) {
    List<Stop> stops = new ArrayList<>(allStops);
    for (Subroute subroute : subroutes) {
      List<Stop> subrouteStops = subroute.getStops();
      for (int i = 0; i < subrouteStops.size(); i++) {
        Stop stop = subrouteStops.get(i);
        stops.remove(stop);
        reduceAndAddSubrouteToSubroutesByIndex(subroute, subroutesByIndex);
        addSubrouteToStop(stopSubroutes, reduceStop(stop), subroute.getId());
        addStopIndexToSubroute(stopIndexInSubroute, stop, subroute, i);
      }
    }
    addStopsWithEmptyArrayOfSubroutes(stops, stopSubroutes);
  }

  private void reduceAndAddSubrouteToSubroutesByIndex(Subroute subroute,
      Map<String, Subroute> subroutesByIndex) {
    if (!subroutesByIndex.containsKey(subroute.getId())) {
      subroutesByIndex.put(subroute.getId(), reduceSubroute(subroute));
    }
  }

  private void addStopsWithEmptyArrayOfSubroutes(List<Stop> stops,
      Map<Stop, List<String>> stopSubroutes) {
    for (Stop stop : stops) {
      stopSubroutes.put(stop, new ArrayList<>());
    }
  }

  private void addStopIndexToSubroute(Map<String, Map<Long, Integer>> stopIndexInSubroute,
      Stop stop, Subroute subroute, int index) {
    if (stopIndexInSubroute.containsKey(subroute.getId())) {
      stopIndexInSubroute.get(subroute.getId()).put(stop.getId(), index);
    } else {
      Map<Long, Integer> subrouteStopIndex = new HashMap<>();
      subrouteStopIndex.put(stop.getId(), index);
      stopIndexInSubroute.put(subroute.getId(), subrouteStopIndex);
    }
  }

  private void addSubrouteToStop(Map<Stop, List<String>> stopSubroutes, Stop stop,
      String subrouteId) {
    addObject1ToListInMapIndexedByObject2(stopSubroutes, reduceStop(stop), subrouteId);
  }

  private void fillSubroutes(List<Subroute> subroutes, Map<Route, List<Subroute>> routeSubroutes,
      List<Trip> allTrips) {
    for (Trip trip : allTrips) {
      List<Stop> tripStops = trip.getStopTimes().stream().map(StopTime::getStop).collect(toList());
      Subroute subroute = getSubrouteIfExists(subroutes, tripStops);
      if (subroute == null) {
        subroute = createNewSubroute(trip, routeSubroutes, tripStops);
        subroutes.add(subroute);
        addSubrouteToRoute(subroute, routeSubroutes);
      }
      addTripToSubroute(subroute, trip);
    }
    sortSubroutes(subroutes);
  }


  private Subroute getSubrouteIfExists(List<Subroute> subroutes, List<Stop> tripStops) {
    for (Subroute subroute : subroutes) {
      if (tripStops.equals(subroute.getStops())) {
        return subroute;
      }
    }
    return null;
  }

  private Subroute createNewSubroute(Trip trip, Map<Route, List<Subroute>> routeSubroutes,
      List<Stop> tripStops) {
    String subrouteId = createSubrouteId(routeSubroutes, trip.getRoute());
    return new Subroute(subrouteId, tripStops.size(), trip.getRoute(), tripStops,
        trip.getDirection());
  }

  private void addSubrouteToRoute(Subroute subroute, Map<Route, List<Subroute>> routeSubroutes) {
    Route route = subroute.getRoute();
    addObject1ToListInMapIndexedByObject2(routeSubroutes, reduceRoute(route),
        reduceSubrouteRouteAndTrips(subroute));
    //or create Route with reduced trips. Use when creating shedule.
    //addObject1ToListInMapIndexedByObject2(routeSubroutes, reduceRouteTrips(route), reduceSubrouteRouteAndTrips(subroute));
  }

  private void addTripToSubroute(Subroute subroute, Trip trip) {
    SubrouteTrip subrouteTrip = convertTripToSubrouteTrip(trip);
    addObject1ToListInMapIndexedByObject2(subroute.getSubrouteTrips(), trip.getServiceDay().getId(),
        subrouteTrip);
  }

  private void sortSubroutes(List<Subroute> subroutes) {
    for (Subroute subroute : subroutes) {
      for (List<SubrouteTrip> subrouteTrips : subroute.getSubrouteTrips().values()) {
        subrouteTrips.sort(new SortSubrouteTrips());
      }
    }
  }

  private SubrouteTrip convertTripToSubrouteTrip(Trip trip) {
    return new SubrouteTrip(trip.getId(), createArrayOfStopTimeObjects(trip.getStopTimes()),
        trip.getServiceDay(), trip.getLowFloor(), trip.getRoute());
  }

  private StopTimeObject[] createArrayOfStopTimeObjects(List<StopTime> stopTimes) {
    return stopTimes.stream()
        .map(st -> new StopTimeObject(st.getStop(), new Time(st.getTime()), 0))
        .toArray(StopTimeObject[]::new);
  }

  private String createSubrouteId(Map<Route, List<Subroute>> routeSubroutes, Route route) {
    int countOfSubroutes = getSizeOfSubrouteListForRoute(routeSubroutes, route);
    return route.getId() + "_" + countOfSubroutes;
  }

  private int getSizeOfSubrouteListForRoute(Map<Route, List<Subroute>> routeSubroutes,
      Route route) {
    List<Subroute> subrouteList = routeSubroutes.get(route);
    if (subrouteList == null) {
      return 0;
    }
    return subrouteList.size();
  }

  private void fillStopTransfers(Map<Long, List<Transfer>> stopTransfers,
      List<FootPath> allFootPaths) {
    for (FootPath footPath : allFootPaths) {
      addTransferToStopTransfers(stopTransfers, createReducedTransfer(footPath));
    }
  }

  private void addTransferToStopTransfers(Map<Long, List<Transfer>> stopTransfers,
      Transfer transfer) {
    if (stopTransfers.containsKey(transfer.getOriginStop().getId())) {
      stopTransfers.get(transfer.getOriginStop().getId()).add(transfer);
    } else {
      List<Transfer> transfersForStop = new ArrayList<>();
      transfersForStop.add(transfer);
      stopTransfers.put(transfer.getOriginStop().getId(), transfersForStop);
    }
  }

  private <T, E> void addObject1ToListInMapIndexedByObject2(Map<T, List<E>> map, T key, E value) {
    if (map.containsKey(key)) {
      map.get(key).add(value);
    } else {
      List<E> listOfValues = new ArrayList<>();
      listOfValues.add(value);
      map.put(key, listOfValues);
    }
  }

  private Transfer createReducedTransfer(FootPath footPath) {
    Stop stopFrom = reduceStop(footPath.getFromStop());
    Stop stopTo = reduceStop(footPath.getToStop());
    return new Transfer(stopFrom, stopTo, footPath.getTime());
  }

  private Stop reduceStop(Stop stop) {
    Stop newStop = new Stop(stop.getId(), stop.getZone(), stop.getCoords(), stop.getStopArea(),
        stop.isOnRequest());
    newStop.getStopArea().setStops(null);
    newStop.getCoords().setStop(null);
    return newStop;
  }

  private Route reduceRoute(Route route) {
    return new Route(route.getId(), route.getName(), route.getMode(), null);
  }

  private Route reduceRouteTrips(Route route) {
    List<Trip> newTrips = new ArrayList<>();
    for (Trip trip : route.getTrips()) {
      List<StopTime> newStopTimes = new ArrayList<>();
      for (StopTime stopTime : trip.getStopTimes()) {
        newStopTimes.add(new StopTime(
            reduceStop(stopTime.getStop()),
            reduceTripWithOnlyId(stopTime.getTrip()),
            stopTime.getTime(),
            stopTime.getSequenceOrder()));
      }
      newTrips.add(new Trip(trip.getId(), trip.getLowFloor(), trip.getDirection(), newStopTimes,
          reduceServiceDay(trip.getServiceDay())));
    }
    return new Route(route.getId(), route.getName(), route.getMode(), newTrips);
  }

  private Trip reduceTripWithOnlyId(Trip trip) {
    return new Trip(trip.getId(), trip.getLowFloor(), trip.getDirection(), null, null);
  }

  private Subroute reduceSubrouteRouteAndTrips(Subroute subroute) {
    List<Stop> reducedStops = new ArrayList<>();
    for (Stop stop : subroute.getStops()) {
      reducedStops.add(reduceStop(stop));
    }
    return new Subroute(subroute.getId(), subroute.getLength(), null, reducedStops, null,
        subroute.isDirection());
  }

  private Subroute reduceSubroute(Subroute subroute) {
    List<Stop> reducedStops = new ArrayList<>();
    for (Stop stop : subroute.getStops()) {
      reducedStops.add(reduceStop(stop));
    }
    Map<Long, List<SubrouteTrip>> newSubrouteTripMap = new HashMap<>();
    for (Long serviceDayId : subroute.getSubrouteTrips().keySet()) {
      List<SubrouteTrip> newSubrouteTrips = new ArrayList<>();
      for (SubrouteTrip subrouteTrip : subroute.getSubrouteTrips().get(serviceDayId)) {
        newSubrouteTrips.add(reduceSubrouteTrip(subrouteTrip));
      }
      newSubrouteTripMap.put(serviceDayId, newSubrouteTrips);
    }
    return new Subroute(subroute.getId(), subroute.getLength(), reduceRoute(subroute.getRoute()),
        reducedStops, newSubrouteTripMap, subroute.isDirection());
  }

  public void reduceCalendarDates(List<CalendarDate> calendarDates) {
    for (CalendarDate calendarDate : calendarDates) {
      Set<ServiceDay> newServiceDays = new HashSet<>();
      for (ServiceDay serviceDay : calendarDate.getServiceDays()) {
        newServiceDays.add(reduceServiceDay(serviceDay));
      }
      calendarDate.setServiceDays(newServiceDays);
    }
  }

  private ServiceDay reduceServiceDay(ServiceDay serviceDay) {
    return new ServiceDay(serviceDay.getId(), serviceDay.getName(), null, null);
  }

  private SubrouteTrip reduceSubrouteTrip(SubrouteTrip subrouteTrip) {
    StopTimeObject[] reducedStopTimeObjects = new StopTimeObject[subrouteTrip
        .getStopTimeObjects().length];
    for (int i = 0; i < subrouteTrip.getStopTimeObjects().length; i++) {
      reducedStopTimeObjects[i] = reduceStopTimeObject(subrouteTrip.getStopTimeObjects()[i]);
    }
    return new SubrouteTrip(
        subrouteTrip.getId(),
        reducedStopTimeObjects,
        reduceServiceDay(subrouteTrip.getServiceDay()),
        subrouteTrip.isLowFloor(),
        reduceRoute(subrouteTrip.getRoute()));
  }

  private StopTimeObject reduceStopTimeObject(StopTimeObject stopTimeObject) {
    return new StopTimeObject(reduceStop(stopTimeObject.getStop()), stopTimeObject.getTime(),
        stopTimeObject.getDelay());
  }

}
