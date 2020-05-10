package com.fmph.diplomovka.raptor.results.models;

import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import java.util.Objects;

public class TimeRound {

  private Time time;
  private Integer round;

  public TimeRound(Time time, Integer round) {
    this.time = time;
    this.round = round;
  }

  public Time getTime() {
    return time;
  }

  public Integer getRound() {
    return round;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimeRound timeRound = (TimeRound) o;
    return Objects.equals(time, timeRound.time) &&
        Objects.equals(round, timeRound.round);
  }

  @Override
  public int hashCode() {
    return Objects.hash(time, round);
  }
}
