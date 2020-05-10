package com.fmph.diplomovka.raptor.results.models;

import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import java.util.Objects;

public class StopArrivalTime {

  private Stop stop;
  private Time arrivalTime;

  public StopArrivalTime(Stop stop, Time arrivalTime) {
    this.stop = stop;
    this.arrivalTime = arrivalTime;
  }

  public Stop getStop() {
    return stop;
  }

  public void setStop(Stop stop) {
    this.stop = stop;
  }

  public Time getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(Time arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    StopArrivalTime that = (StopArrivalTime) o;
    return Objects.equals(stop, that.stop) &&
        Objects.equals(arrivalTime, that.arrivalTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stop, arrivalTime);
  }
}


