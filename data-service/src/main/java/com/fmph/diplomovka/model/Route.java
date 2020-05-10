package com.fmph.diplomovka.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "routes")
public class Route implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "mode", nullable = false)
  @Enumerated(EnumType.STRING)
  private Mode mode;

  @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
  private List<Trip> trips = new ArrayList<>();

  public Route(Long id, String name, Mode mode) {
    this.id = id;
    this.name = name;
    this.mode = mode;
  }

  public Route(Long id, String name, Mode mode, List<Trip> trips) {
    this.id = id;
    this.name = name;
    this.mode = mode;
    this.trips = trips;
  }

  public Route() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Mode getMode() {
    return mode;
  }

  public void setMode(Mode mode) {
    this.mode = mode;
  }

  public List<Trip> getTrips() {
    return trips;
  }

  public void setTrips(List<Trip> trips) {
    this.trips = trips;
  }


  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    Route route = (Route) o;
    return id.equals(route.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Route{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
