package com.fmph.diplomovka.raptor;


import static java.util.stream.Collectors.toMap;

import com.fmph.diplomovka.TimeSimulator;
import com.fmph.diplomovka.model.ServiceDay;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.raptor.dataStructure.models.Subroute;
import com.fmph.diplomovka.raptor.dataStructure.models.SubrouteTrip;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import com.fmph.diplomovka.raptor.dataStructure.models.Transfer;
import com.fmph.diplomovka.raptor.raptorStructure.Queue;
import com.fmph.diplomovka.raptor.raptorStructure.TodayTripFinder;
import com.fmph.diplomovka.raptor.raptorStructure.TripFinder;
import com.fmph.diplomovka.raptor.results.RaptorResults;
import com.fmph.diplomovka.services.ServiceDayService;
import com.fmph.diplomovka.services.models.SearchParams;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class Algorithm {

  private final DataStructure dataStructure;
  private final ServiceDayService serviceDayService;
  private final TimeSimulator timeSimulator;

  public Algorithm(DataStructure dataStructure,
      ServiceDayService serviceDayService, TimeSimulator timeSimulator) {
    this.dataStructure = dataStructure;
    this.serviceDayService = serviceDayService;
    this.timeSimulator = timeSimulator;
  }

  public RaptorResults search(SearchParams searchParams) {
    Set<Stop> markedStops = initializeMarkedStops(searchParams);
    RaptorResults raptorResults = initializeRaptorResults(searchParams);
    while (markedStops.size() > 0 && raptorResults.getRound() <= searchParams
        .getMaxNumberOfTransfers()) {
      Set<Stop> improvedStops = new HashSet<>();
      raptorResults.addRound();
      traverseRoutes(raptorResults, improvedStops, searchParams, markedStops);
      if (!(searchParams.getActualLocation() && raptorResults.getRound() == 1)) {
        traverseTransfers(raptorResults, improvedStops, searchParams.getMaxTimeOfWalking(),
            markedStops, searchParams);
      }
      markedStops = improvedStops;
    }

    //raptorResults.print();
    return raptorResults;
  }

  private RaptorResults initializeRaptorResults(SearchParams searchParams) {
    return new RaptorResults(createOriginStopTimes(searchParams), dataStructure);
  }

  private void traverseRoutes(RaptorResults raptorResults, Set<Stop> improvedStops,
      SearchParams searchParams, Set<Stop> markedStops) {
    Queue queue = new Queue(dataStructure, markedStops);
    for (Map.Entry<Subroute, Stop> entry : queue.getQueue().entrySet()) {
      int boardingPoint = -1;
      SubrouteTrip trip = null;
      Subroute subroute = entry.getKey();
      Stop stopP = entry.getValue();

      int boardingStopIndex = dataStructure.getDataStructureModel().getStopIndexInSubroute()
          .get(subroute.getId()).get(stopP.getId());
      for (int pi = boardingStopIndex; pi < entry.getKey().getLength(); pi++) {
        Stop stopPi = subroute.getStops().get(pi);
        Time prevArrival = raptorResults.prevArrival(stopPi);
        if (trip != null) {
          Time arrivalTime = trip.getStopTimeObjects()[pi].getTime();
          if (arrivalTimeEarlierThanBestArrivalAndBestTargetArrival(arrivalTime, stopPi,
              raptorResults, searchParams)) {
            raptorResults.setTrip(trip, boardingPoint, pi, isToday(searchParams),
                timeSimulator.getActualTime());
            improvedStops.add(stopPi);
          } else if (arrivalTimeEqualsBestArrival(arrivalTime, stopPi, raptorResults)) {
            raptorResults.setTripWhenEqual(trip, boardingPoint, pi, isToday(searchParams),
                timeSimulator.getActualTime());
          }
        }
        if (prevArrival != null && (trip == null || previousArrivalEarlierThanArrivalTime(
            prevArrival, trip.getStopTimeObjects()[pi].getTime()))) {
          trip = findEarliestTrip(subroute, pi, prevArrival, searchParams, raptorResults);
          boardingPoint = pi;
        }
      }
    }
  }

  private SubrouteTrip findEarliestTrip(Subroute subroute, int stopIndex, Time prevArrival,
      SearchParams searchParams, RaptorResults raptorResults) {
    Time startingTime = getStartingTime(raptorResults, searchParams.getMinTimeForTransfer(),
        prevArrival);
    Set<ServiceDay> possibleServiceDays = serviceDayService
        .getPossibleServiceDays(searchParams.getDateFrom());
    if (isToday(searchParams)) {
      return getEarliestTodayTrip(subroute, stopIndex, startingTime, possibleServiceDays,
          searchParams.getOnlyLowFloor());
    }
    return getEarliestTrip(subroute, stopIndex, startingTime, possibleServiceDays,
        searchParams.getOnlyLowFloor());
  }

  private SubrouteTrip getEarliestTrip(Subroute subroute, int stopIndex, Time startingTime,
      Set<ServiceDay> possibleServiceDays, boolean onlyLowFloor) {
    TripFinder tripFinder = new TripFinder();
    return tripFinder
        .getEarliestTrip(subroute, stopIndex, startingTime, possibleServiceDays, onlyLowFloor);
  }

  private SubrouteTrip getEarliestTodayTrip(Subroute subroute, int stopIndex, Time startingTime,
      Set<ServiceDay> possibleServiceDays, boolean onlyLowFloor) {
    TodayTripFinder todayTripFinder = new TodayTripFinder();
    return todayTripFinder
        .findEarliestTrip(subroute, stopIndex, startingTime, possibleServiceDays, onlyLowFloor);
  }

  private Time getStartingTime(RaptorResults raptorResults, int minTimeForTransfer,
      Time arrivalTime) {
    return raptorResults.getRound() == 1
        ? arrivalTime
        : new Time(arrivalTime).addMinutes(minTimeForTransfer);
  }

  private void traverseTransfers(RaptorResults raptorResults, Set<Stop> improvedStops,
      int maxTimeOfWalking, Set<Stop> markedStops, SearchParams searchParams) {
    for (Stop stop : markedStops) {
      if (raptorResults.previousRoundBestArrivalTimeReachedBySomeTrip(stop)) {
        List<Transfer> transfers = dataStructure.getDataStructureModel().getStopTransfers()
            .get(stop.getId());
        if (transfers != null) {
          for (Transfer transfer : transfers) {
            if (transfer.getDuration() <= maxTimeOfWalking) {
              Stop endStop = transfer.getDestinationStop();
              Time arrivalTime = new Time(raptorResults.prevArrival(stop))
                  .addMinutes(transfer.getDuration());
              if (arrivalTimeEarlierThanBestArrivalAndBestTargetArrival(arrivalTime, endStop,
                  raptorResults, searchParams)) {
                raptorResults.setTransfer(transfer, arrivalTime);
                improvedStops.add(endStop);
              } else if (arrivalTimeEqualsBestArrival(arrivalTime, endStop,
                  raptorResults)) {
                raptorResults.setTransferWhenEqual(transfer, arrivalTime);
              }
            }
          }
        }
      }
    }
  }

  //zatial budeme brat do uvahy odchod po fromTime pre vsetky zaciatocne a konecne zastavky
  private Map<Stop, Time> createOriginStopTimes(SearchParams searchParams) {
    return searchParams.getStartStops().keySet().stream()
        .collect(toMap(
            stop -> stop,
            stop -> new Time(createFromTimeForStartStop(searchParams, stop)), (t1, t2) -> t1));
  }

  private Time createFromTimeForStartStop(SearchParams searchParams, Stop stop) {
    return new Time(searchParams.getTimeFrom()).addMinutes(searchParams.getStartStops().get(stop));
  }

  private boolean arrivalTimeEarlierThanBestArrivalAndBestTargetArrival(Time arrivalTime, Stop stop,
      RaptorResults raptorResults, SearchParams searchParams) {
    return arrivalTime.isBefore(raptorResults.bestArrival(stop))
        && arrivalTime.isBefore(raptorResults.bestTargetArrival(searchParams));
  }

  private boolean arrivalTimeEqualsBestArrival(Time arrivalTime, Stop stop,
      RaptorResults raptorResults) {
    return arrivalTime.equals(raptorResults.bestArrival(stop));
  }

  private boolean previousArrivalEarlierThanArrivalTime(Time prevArrival, Time arrivalTime) {
    return prevArrival.isBefore(arrivalTime);
  }

  //dat tie ktore su vo vyhladavani plus take zastavky ku ktorym sa viem dostat na menej ako maxTimeOfWalking.
  private Set<Stop> initializeMarkedStops(SearchParams searchParams) {
    return searchParams.getStartStops().keySet();
  }

  private boolean isToday(SearchParams searchParams) {
    LocalDate today = timeSimulator.getActualLocalDate();
    return searchParams.getDateFrom().equals(today);
  }

}
