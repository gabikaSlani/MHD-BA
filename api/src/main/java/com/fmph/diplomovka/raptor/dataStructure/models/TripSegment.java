package com.fmph.diplomovka.raptor.dataStructure.models;

import java.util.ArrayList;
import java.util.List;

public class TripSegment {

  private Integer boardingStopIndex;
  private Integer getOffStopIndex;
  private SubrouteTrip subrouteTrip;
  private Time boardingTime;
  private Time getOfFTime;
  private boolean leftTheOriginStop;

  public TripSegment(Integer boardingStopIndex, Integer getOffStopIndex,
      SubrouteTrip subrouteTrip, boolean isToday, Time actualTime) {
    this.boardingStopIndex = boardingStopIndex;
    this.getOffStopIndex = getOffStopIndex;
    this.subrouteTrip = subrouteTrip;
    setOtherParametersAccordingToActualTime(isToday, actualTime);
  }

  private void setOtherParametersAccordingToActualTime(boolean isToday, Time actualTime) {
    if (tripHasLeftFromOrigin(isToday, actualTime)) {
      leftTheOriginStop = true;
      boardingTime = getDepartureTimeWithDelayAtStopIndex(boardingStopIndex);
      getOfFTime = getDepartureTimeWithDelayAtStopIndex(getOffStopIndex);
    } else {
      leftTheOriginStop = false;
      boardingTime = getDepartureTimeOfStopAtIndex(boardingStopIndex);
      getOfFTime = getDepartureTimeOfStopAtIndex(getOffStopIndex);
    }
  }

  public List<StopTimeObject> getStops() {
    List<StopTimeObject> stops = new ArrayList<>();
    for (int i = boardingStopIndex; i <= getOffStopIndex; i++) {
      stops.add(subrouteTrip.getStopTimeObjects()[i]);
    }
    return stops;
  }

  private boolean tripHasLeftFromOrigin(boolean isToday, Time actualTime) {
    return isToday && getOriginStopDepartureTime().isBeforeOrEqual(actualTime);
  }

  public SubrouteTrip getSubrouteTrip() {
    return subrouteTrip;
  }

  public String getFinalStopAreaName() {
    int lastStopIndex = subrouteTrip.getStopTimeObjects().length - 1;
    return subrouteTrip.getStopTimeObjects()[lastStopIndex].getStop().getStopArea().getName();
  }

  private Time getDepartureTimeOfStopAtIndex(int stopIndex) {
    return subrouteTrip.getStopTimeObjects()[stopIndex].getTime();
  }

  private Time getDepartureTimeWithDelayAtStopIndex(int stopIndex) {
    return new Time(getDepartureTimeOfStopAtIndex(stopIndex))
        .addMinutes(getDelayOfStopAtIndex(stopIndex));
  }

  private int getDelayOfStopAtIndex(int stopIndex) {
    return subrouteTrip.getStopTimeObjects()[stopIndex].getDelay();
  }

  public Time getOriginStopDepartureTime() {
    return subrouteTrip.getStopTimeObjects()[0].getTime();
  }

  public Time getBoardingTime() {
    return boardingTime;
  }

  public Time getGetOfFTime() {
    return getOfFTime;
  }

  public boolean hasLeftTheOriginStop() {
    return leftTheOriginStop;
  }

  @Override
  public String toString() {
    return "TripSegment{" +
        "boardingStopIndex=" + boardingStopIndex +
        ", getOffStopIndex=" + getOffStopIndex +
        ", boardingTime=" + boardingTime +
        ", getOfFTime=" + getOfFTime +
        ", leftTheOriginStop=" + leftTheOriginStop +
        '}';
  }


}
