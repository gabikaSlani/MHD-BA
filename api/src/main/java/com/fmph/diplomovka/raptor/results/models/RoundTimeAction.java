package com.fmph.diplomovka.raptor.results.models;

import com.fmph.diplomovka.raptor.dataStructure.models.Action;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import java.util.Objects;

public class RoundTimeAction {

  private Integer round;
  private Action action;
  private Time arrivalTime;

  public RoundTimeAction(Integer round, Action action, Time arrivalTime) {
    this.round = round;
    this.action = action;
    this.arrivalTime = arrivalTime;
  }

  public Integer getRound() {
    return round;
  }

  public Action getAction() {
    return action;
  }

  public Time getArrivalTime() {
    return arrivalTime;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    RoundTimeAction that = (RoundTimeAction) o;
    return Objects.equals(round, that.round) &&
        Objects.equals(action, that.action) &&
        Objects.equals(arrivalTime, that.arrivalTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(round, action, arrivalTime);
  }
}
