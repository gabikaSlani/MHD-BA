package com.fmph.diplomovka.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "stop_times")
public class StopTime implements Comparable<StopTime>, Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private StopTimeId id;

  @ManyToOne
  @MapsId("stopId")
  private Stop stop;

  @ManyToOne
  @MapsId("tripId")
  private Trip trip;

  @Column(name = "time", nullable = false)
  private String time;

  @Column(name = "sequence_order", nullable = false)
  private Integer sequenceOrder;

  public StopTime() {
  }

  public StopTime(Stop stop, Trip trip, String time, Integer sequenceOrder) {
    this.stop = stop;
    this.trip = trip;
    this.time = time;
    this.sequenceOrder = sequenceOrder;
    this.id = new StopTimeId(stop.getId(), trip.getId());
  }

  public StopTimeId getId() {
    return id;
  }

  public Stop getStop() {
    return stop;
  }

  public void setStop(Stop stop) {
    this.stop = stop;
  }

  public Trip getTrip() {
    return trip;
  }

  public void setTrip(Trip trip) {
    this.trip = trip;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public Integer getSequenceOrder() {
    return sequenceOrder;
  }

  public void setSequenceOrder(Integer sequenceOrder) {
    this.sequenceOrder = sequenceOrder;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    StopTime stopTime = (StopTime) o;
    return stop.equals(stopTime.stop) &&
        trip.equals(stopTime.trip);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stop, trip);
  }

  @Override
  public int compareTo(StopTime o) {
    return sequenceOrder.compareTo(o.sequenceOrder);
  }
}
