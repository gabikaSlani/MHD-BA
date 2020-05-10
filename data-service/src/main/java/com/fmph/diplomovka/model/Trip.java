package com.fmph.diplomovka.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "trips")
public class Trip implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "trip_id", nullable = false)
  private Long tripId;

  @ManyToOne(cascade = CascadeType.ALL)
  private ServiceDay serviceDay;

  @Column(name = "low_floor", nullable = false)
  private Boolean lowFloor;

  @Column(name = "direction", nullable = false)
  private Boolean direction;

  @ManyToOne(cascade = CascadeType.ALL)
  private Route route;

  @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<StopTime> stopTimes = new ArrayList<>();

  public Trip() {
  }

  public Trip(Long tripId, ServiceDay serviceDay, Boolean lowFloor, Boolean direction,
      Route route) {
    this.tripId = tripId;
    this.serviceDay = serviceDay;
    this.lowFloor = lowFloor;
    this.direction = direction;
    this.route = route;
  }

  public Trip(Long tripId, Boolean lowFloor, Boolean direction, List<StopTime> stopTimes,
      ServiceDay serviceDay) {
    this.tripId = tripId;
    this.lowFloor = lowFloor;
    this.direction = direction;
    this.stopTimes = stopTimes;
    this.serviceDay = serviceDay;
  }

  public Long getId() {
    return tripId;
  }

  public void setId(Long id) {
    this.tripId = id;
  }

  public Boolean getLowFloor() {
    return lowFloor;
  }

  public void setLowFloor(Boolean lowFloor) {
    this.lowFloor = lowFloor;
  }

  public List<StopTime> getStopTimes() {
    return stopTimes;
  }

  public void setStopTimes(List<StopTime> stopTimes) {
    this.stopTimes = stopTimes;
  }

  public ServiceDay getServiceDay() {
    return serviceDay;
  }

  public void setServiceDay(ServiceDay serviceDay) {
    this.serviceDay = serviceDay;
  }

  public Route getRoute() {
    return route;
  }

  public void setRoute(Route route) {
    this.route = route;
  }

  public Boolean getDirection() {
    return direction;
  }

  public void setDirection(Boolean direction) {
    this.direction = direction;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    Trip trip = (Trip) o;
      if (this.tripId == null) {
          return false;
      }
    return tripId.equals(trip.tripId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tripId);
  }


}
