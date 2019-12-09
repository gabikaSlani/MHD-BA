package com.fmph.diplomovka.services.models;

public class Action {

    private ActionType actionType;
    private Integer walkingTime;
    private TripSegment tripSegment;

    public Action(ActionType actionType, Integer walkingTime, TripSegment tripSegment) {
        this.actionType = actionType;
        this.walkingTime = walkingTime;
        this.tripSegment = tripSegment;
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
}
