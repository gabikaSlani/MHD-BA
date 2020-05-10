package com.fmph.diplomovka.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stops")
public class Stop implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "stop_id", nullable = false)
  private Long stopId;

  @Column(name = "zone", nullable = false)
  private Integer zone;

  @Column(name = "on_request")
  private boolean onRequest;

  @OneToOne(mappedBy = "stop", cascade = CascadeType.ALL)
  private Coords coords;

  @ManyToOne(cascade = CascadeType.ALL)
  private StopArea stopArea;

  public Stop() {
  }

  public Stop(Long stopId, Integer zone, Coords coords, StopArea stopArea, boolean onRequest) {
    this.stopId = stopId;
    this.zone = zone;
    this.coords = coords;
    this.stopArea = stopArea;
    this.onRequest = onRequest;
  }

  public Stop(Long stopId, StopArea stopArea, Integer zone) {
    this.stopId = stopId;
    this.stopArea = stopArea;
    this.zone = zone;
  }


  public Long getId() {
    return stopId;
  }

  public void setId(Long id) {
    this.stopId = id;
  }

  public StopArea getStopArea() {
    return stopArea;
  }

  public void setStopArea(StopArea stopArea) {
    this.stopArea = stopArea;
  }

  public Integer getZone() {
    return zone;
  }

  public void setZone(Integer zone) {
    this.zone = zone;
  }

  public Coords getCoords() {
    return coords;
  }

  public void setCoords(Coords coords) {
    this.coords = coords;
  }

  public boolean isOnRequest() {
    return onRequest;
  }

  public void setOnRequest(boolean onRequest) {
    this.onRequest = onRequest;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
      if (this.stopId == null) {
          return false;
      }
    Stop stop = (Stop) o;
    return stopId.equals(stop.stopId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stopId);
  }

  @Override
  public String toString() {
    return stopId.toString();
  }
}
