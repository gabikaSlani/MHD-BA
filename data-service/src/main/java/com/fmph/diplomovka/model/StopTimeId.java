package com.fmph.diplomovka.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StopTimeId implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "stop_id")
  private Long stopId;

  @Column(name = "trip_id")
  private Long tripId;

  public StopTimeId() {
  }

  public StopTimeId(Long stopId, Long tripId) {
    this.stopId = stopId;
    this.tripId = tripId;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    StopTimeId that = (StopTimeId) o;
    return stopId.equals(that.stopId) &&
        tripId.equals(that.tripId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stopId, tripId);
  }

  public Long getStopId() {
    return stopId;
  }

  public Long getTripId() {
    return tripId;
  }
}
