package com.fmph.diplomovka.raptor.dataStructure.models;

import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.ServiceDay;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class SubrouteTrip implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private StopTimeObject[] stopTimeObjects;
  private ServiceDay serviceDay;
  private boolean lowFloor;
  private Route route;
  private int delay;

  public SubrouteTrip() {
  }

  public SubrouteTrip(Long id, StopTimeObject[] stopTimeObjects, ServiceDay serviceDay,
      boolean lowFloor, Route route) {
    this.id = id;
    this.stopTimeObjects = stopTimeObjects;
    this.serviceDay = serviceDay;
    this.lowFloor = lowFloor;
    this.route = route;
    this.delay = 0;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public StopTimeObject[] getStopTimeObjects() {
    return stopTimeObjects;
  }

  public void setStopTimeObjects(StopTimeObject[] stopTimeObjects) {
    this.stopTimeObjects = stopTimeObjects;
  }

  public ServiceDay getServiceDay() {
    return serviceDay;
  }

  public void setServiceDay(ServiceDay serviceDay) {
    this.serviceDay = serviceDay;
  }

  public boolean isLowFloor() {
    return lowFloor;
  }

  public void setLowFloor(boolean lowFloor) {
    this.lowFloor = lowFloor;
  }

  public Route getRoute() {
    return route;
  }

  public int getDelay() {
    return delay;
  }

  public void setDelay(int delay) {
    this.delay = delay;
  }

  public boolean runsOn(Set<ServiceDay> serviceDaySet) {
    return serviceDaySet.contains(this.serviceDay);
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    SubrouteTrip subrouteTrip = (SubrouteTrip) o;
    return Objects.equals(id, subrouteTrip.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "SubrouteTrip{" +
        "id=" + id +
        ", serviceDay=" + serviceDay +
        ", lowFloor=" + lowFloor +
        ", route=" + route +
        ", delay=" + delay +
        '}';
  }
}
