package com.fmph.diplomovka.raptor.results;

import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.model.StopArea;
import com.fmph.diplomovka.raptor.DataStructure;
import com.fmph.diplomovka.raptor.dataStructure.models.Action;
import com.fmph.diplomovka.raptor.dataStructure.models.ActionType;
import com.fmph.diplomovka.raptor.dataStructure.models.StopTimeObject;
import com.fmph.diplomovka.raptor.dataStructure.models.SubrouteTrip;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import com.fmph.diplomovka.raptor.dataStructure.models.Transfer;
import com.fmph.diplomovka.raptor.dataStructure.models.TripSegment;
import com.fmph.diplomovka.raptor.results.models.RoundTimeAction;
import com.fmph.diplomovka.raptor.results.models.StopArrivalTime;
import com.fmph.diplomovka.raptor.results.models.TimeRound;
import com.fmph.diplomovka.services.models.SearchParams;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RaptorResults {

  private int ROUND = 0;

  private Map<Stop, TimeRound> bestArrivals = new HashMap<>();
  private Map<Integer, List<StopArrivalTime>> roundArrivals = new HashMap<>();
  private Map<Stop, List<RoundTimeAction>> roundActions = new HashMap<>();
  private Map<StopArea, Time> stopAreaBestArrivals = new HashMap<>();

  public RaptorResults(Map<Stop, Time> originStopTimes, DataStructure dataStructure) {
    roundArrivals.put(ROUND, new ArrayList<>());
    for (Stop stop : dataStructure.getDataStructureModel().getStopSubroutes().keySet()) {
      bestArrivals
          .put(stop, new TimeRound(originStopTimes.getOrDefault(stop, new Time().getMaxTime()), 0));
      roundArrivals.get(ROUND).add(
          new StopArrivalTime(stop, originStopTimes.getOrDefault(stop, new Time().getMaxTime())));
    }
  }

  public void addRound() {
    ROUND++;
    List<StopArrivalTime> thisRoundArrivalTimes = new ArrayList<>();
    List<StopArrivalTime> previousRoundArrivalTimes = roundArrivals.get(ROUND - 1);
    for (StopArrivalTime sat : previousRoundArrivalTimes) {
      thisRoundArrivalTimes.add(new StopArrivalTime(sat.getStop(), sat.getArrivalTime()));
    }
    roundArrivals.put(ROUND, thisRoundArrivalTimes);
  }

  public Time prevArrival(Stop stop) {
    Optional<StopArrivalTime> stopAT = roundArrivals.get(ROUND - 1).stream()
        .filter(sat -> sat.getStop().equals(stop)).findAny();
    if (stopAT.isEmpty()) {
      return new Time().getMaxTime();
    }
    return stopAT.get().getArrivalTime();
  }

  public Time bestArrival(Stop stop) {
    return bestArrivals.get(stop).getTime();
  }

  public Time bestTargetArrival(SearchParams searchParams) {
    StopArea endStopArea = searchParams.getEndStops().iterator().next().getStopArea();
    if (!stopAreaBestArrivals.containsKey(endStopArea)) {
      stopAreaBestArrivals.put(endStopArea, new Time().getMaxTime());
    }
    return stopAreaBestArrivals.get(endStopArea);
  }

  public void setTrip(SubrouteTrip trip, int startStopIndex, int endStopIndex, boolean isToday,
      Time actualTime) {
    Time time = isToday
        ? getEndStopTimeWithDelay(trip, endStopIndex)
        : trip.getStopTimeObjects()[endStopIndex].getTime();
    Stop endStop = trip.getStopTimeObjects()[endStopIndex].getStop();
    Stop startStop = trip.getStopTimeObjects()[startStopIndex].getStop();
    timeInRoundForStopGetBetter(endStop, time);
    manageBestTimeForStopArea(endStop.getStopArea(), time);
    bestArrivals.put(endStop, new TimeRound(time, ROUND));
    addNewTripRoundAction(trip, startStopIndex, endStopIndex, time, startStop, endStop, isToday,
        actualTime);
  }

  public void setTransfer(Transfer transfer, Time time) {
    Stop endStop = transfer.getDestinationStop();
    timeInRoundForStopGetBetter(endStop, time);
    manageBestTimeForStopArea(endStop.getStopArea(), time);
    bestArrivals.put(endStop, new TimeRound(time, ROUND));
    addNewTransferRoundAction(transfer, endStop, time);
  }

  private void manageBestTimeForStopArea(StopArea endStopArea, Time time) {
    if (!stopAreaBestArrivals.containsKey(endStopArea)
        || time.isBefore(stopAreaBestArrivals.get(endStopArea))) {
      stopAreaBestArrivals.put(endStopArea, time);
    }
  }

  public void setTripWhenEqual(SubrouteTrip trip, int startStopIndex, int endStopIndex,
      boolean isToday, Time actualTime) {
    Time time = isToday
        ? getEndStopTimeWithDelay(trip, endStopIndex)
        : trip.getStopTimeObjects()[endStopIndex].getTime();
    Stop endStop = trip.getStopTimeObjects()[endStopIndex].getStop();
    Stop startStop = trip.getStopTimeObjects()[startStopIndex].getStop();
    addNewTripRoundAction(trip, startStopIndex, endStopIndex, time, startStop, endStop, isToday,
        actualTime);
  }

  public void setTransferWhenEqual(Transfer transfer, Time time) {
    Stop endStop = transfer.getDestinationStop();
    addNewTransferRoundAction(transfer, endStop, time);
  }

  private Time getEndStopTimeWithDelay(SubrouteTrip trip, int endStopIndex) {
    StopTimeObject endStop = trip.getStopTimeObjects()[endStopIndex];
    return new Time(endStop.getTime()).addMinutes(endStop.getDelay());
  }

  private void addNewTripRoundAction(SubrouteTrip trip, int startStopIndex, int endStopIndex,
      Time time, Stop startStop, Stop endStop, boolean isToday, Time actualTime) {
    TripSegment tripSegment = new TripSegment(startStopIndex, endStopIndex, trip, isToday,
        actualTime);
    Action action = new Action(ActionType.TRIP, null, tripSegment, startStop, endStop);
    addNewRoundAction(endStop, new RoundTimeAction(ROUND, action, time));
  }

  private void addNewTransferRoundAction(Transfer transfer, Stop endStop, Time time) {
    Action action = new Action(ActionType.WALKING, transfer.getDuration(), null,
        transfer.getOriginStop(), endStop);
    addNewRoundAction(endStop, new RoundTimeAction(ROUND, action, time));
  }

  private void addNewRoundAction(Stop stop, RoundTimeAction roundTimeAction) {
    if (!roundActions.containsKey(stop)) {
      roundActions.put(stop, new ArrayList<>());
    }
    roundActions.get(stop).add(roundTimeAction);
  }

  private void timeInRoundForStopGetBetter(Stop stop, Time time) {
    for (StopArrivalTime sat : roundArrivals.get(ROUND)) {
      if (sat.getStop().equals(stop)) {
        sat.setArrivalTime(time);
        break;
      }
    }
  }

  public boolean previousRoundBestArrivalTimeReachedBySomeTrip(Stop stop) {
    if (!roundActions.containsKey(stop)) {
      return true;
    }
    TimeRound bestTimeRound = bestArrivals.get(stop);
    return roundActions.get(stop).stream()
        .anyMatch(ra -> ra.getRound().equals(ROUND - 1)
            && ra.getArrivalTime().equals(bestTimeRound.getTime())
            && ra.getAction().getActionType().equals(ActionType.TRIP));
  }

  public void print() {
    System.out.println("ROUND: " + ROUND);
    System.out.println("roundArrivals");
    for (Map.Entry<Integer, List<StopArrivalTime>> list : roundArrivals.entrySet()) {
      for (StopArrivalTime sat : list.getValue()) {
        System.out.println(
            list.getKey().toString() + ", " + sat.getStop().getId().toString() + ", " + sat
                .getArrivalTime().toString());
      }
    }
    System.out.println("bestArrivals");
    for (Map.Entry<Stop, TimeRound> st : bestArrivals.entrySet()) {
      System.out.println(
          st.getKey().getId() + "," + st.getValue().getTime() + "," + st.getValue().getRound());
    }
    System.out.println("roundActions");
    for (Map.Entry<Stop, List<RoundTimeAction>> sra : roundActions.entrySet()) {
      String text = "";
      text += sra.getKey().getId() + ", ";
      for (RoundTimeAction list : sra.getValue()) {
        text += list.getRound() + ",";
        text += list.getAction().getActionType() + ",";
        text += list.getArrivalTime() + ",";
        if (list.getAction().getActionType().equals(ActionType.TRIP)) {
          text += list.getAction().getStartStop().getId();
          text += ",";
          text += list.getAction().getTripSegment().getSubrouteTrip().getId();
        } else {
          text += list.getAction().getStartStop().getId();
          text += " (" + list.getAction().getWalkingTime() + ")";
          text += ",";
        }
        text += ", ";
      }
      System.out.println(text);
    }
  }

  public void printRoundActionsForStop(Stop stop) {
    String text = "";
    text += stop.getId() + ", ";
    for (RoundTimeAction list : roundActions.get(stop)) {
      text += list.getRound() + ",";
      text += list.getAction().getActionType() + ",";
      text += list.getArrivalTime() + ",";
      if (list.getAction().getActionType().equals(ActionType.TRIP)) {
        text += list.getAction().getStartStop().getId();
        text += ",";
        text += list.getAction().getTripSegment().getSubrouteTrip().getId();
      } else {
        text += list.getAction().getStartStop().getId();
        text += " (" + list.getAction().getWalkingTime() + ")";
        text += ",";
      }
      text += ", ";
    }
    System.out.println(text);
  }

  public Map<Stop, List<RoundTimeAction>> getRoundActions() {
    return roundActions;
  }

  public int getRound() {
    return ROUND;
  }

}





