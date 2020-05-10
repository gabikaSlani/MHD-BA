package com.fmph.diplomovka.raptor.dataStructure.models;

import com.fmph.diplomovka.model.Stop;

public class Action {

  private ActionType actionType;
  private Integer walkingTime;
  private TripSegment tripSegment;
  private Stop startStop;
  private Stop endStop;

  public Action(ActionType actionType, Integer walkingTime, TripSegment tripSegment, Stop startStop,
      Stop endStop) {
    this.actionType = actionType;
    this.walkingTime = walkingTime;
    this.tripSegment = tripSegment;
    this.startStop = startStop;
    this.endStop = endStop;
  }

  public ActionType getActionType() {
    return actionType;
  }

  public void setActionType(ActionType actionType) {
    this.actionType = actionType;
  }

  public Integer getWalkingTime() {
    return walkingTime;
  }

  public void setWalkingTime(Integer walkingTime) {
    this.walkingTime = walkingTime;
  }

  public TripSegment getTripSegment() {
    return tripSegment;
  }

  public void setTripSegment(TripSegment tripSegment) {
    this.tripSegment = tripSegment;
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

  @Override
  public String toString() {
    String info = "Action{" +
        "startStop=" + startStop +
        ", endStop=" + endStop +
        ", actionType=" + actionType;
    String action =
        actionType.equals(ActionType.WALKING)
            ? ", walkingTime=" + walkingTime
            : ", tripSegment=" + tripSegment;
    return info + action + '}';
  }
}
