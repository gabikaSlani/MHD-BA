package com.fmph.diplomovka.services.models;

import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.raptor.dataStructure.models.Action;
import com.fmph.diplomovka.raptor.dataStructure.models.ActionType;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import com.fmph.diplomovka.raptor.dataStructure.models.TripSegment;
import java.time.LocalDate;
import java.util.List;

public class Path {

  private Stop startStop;
  private Stop endStop;
  private Integer duration;
  private LocalDate date;
  private List<Action> actions;
  private Time departureTime;
  private Time arrivalTime;

  public Path(List<Action> actions, Time arrivalTime, SearchParams searchParams) {
    this.actions = actions;
    this.arrivalTime = arrivalTime;
    setAttributesFromActions(actions, arrivalTime, searchParams);
  }

  public Stop getStartStop() {
    return startStop;
  }

  public void setStartStop(Stop startStop) {
    this.startStop = startStop;
  }

  public Stop getEndStop() {
    return endStop;
  }

  public void setEndStop(Stop endStop) {
    this.endStop = endStop;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public List<Action> getActions() {
    return actions;
  }

  public void setActions(List<Action> actions) {
    this.actions = actions;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Time getArrivalTime() {
    return arrivalTime;
  }

  public Time getDepartureTime() {
    return departureTime;
  }

  private void setAttributesFromActions(List<Action> actions, Time arrivalTime,
      SearchParams searchParams) {
    this.startStop = searchParams.getActualLocation()
        ? actions.get(1).getStartStop()
        : actions.get(0).getStartStop();
    this.endStop = actions.get(actions.size() - 1).getEndStop();
    this.date = searchParams.getDateFrom();
    this.departureTime = getDepartureTime(searchParams);
    this.duration = calculateDuration(this.departureTime, arrivalTime);
  }

  private Time getDepartureTime(SearchParams searchParams) {
    if (actions.get(0).getActionType().equals(ActionType.WALKING)) {
      if (actions.size() == 1) {
        return searchParams.getTimeFrom();
      } else {
        return calculateStartingTimeWhenFirstWalking();
      }
    }
    TripSegment firstTripSegment = actions.get(0).getTripSegment();
    return firstTripSegment.getBoardingTime();
  }

  private Time calculateStartingTimeWhenFirstWalking() {
    int walkingDuration = actions.get(0).getWalkingTime();
    Time firstTripDepartureTime = actions.get(1).getTripSegment().getBoardingTime();
    return new Time(firstTripDepartureTime).addMinutes(-walkingDuration);
  }

  private Integer calculateDuration(Time departureTime, Time arrivalTime) {
    return departureTime.minutesBetween(arrivalTime);
  }

  private int durationUntilFirstTrip(List<Action> actions) {
    if (actions.size() == 1) {
      return 0;
    }
    int walkingDuration = 0;
    for (Action action : actions) {
      if (action.getActionType().equals(ActionType.TRIP)) {
        return walkingDuration;
      }
      walkingDuration += action.getWalkingTime();
    }
    return walkingDuration;
  }

//    @Override
//    public String toString() {
//        return "Path{" +
//            "startStop=" + startStop +
//            ", endStop=" + endStop +
//            ", duration=" + duration +
//            ", date=" + date +
//            ", departureTime=" + departureTime +
//            ", arrivalTime=" + arrivalTime +
//            ", actions=" + actions +
//            "}\n";
//    }

  @Override
  public String toString() {
    String result =
        "Path{" +
            "departureTime=" + departureTime +
            ", arrivalTime=" + arrivalTime +
            ", startStop=" + startStop +
            ", endStop=" + endStop;
    String action = actions.size() == 1 && actions.get(0).getActionType().equals(ActionType.WALKING)
        ? ", actions=" + actions.size() + "W"
        : ", actions=" + actions.size();
    return result + action + "}\n";
  }
}
