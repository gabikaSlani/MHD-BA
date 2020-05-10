package com.fmph.diplomovka.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "service_days")
public class ServiceDay implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "service_day_id")
  private Long id;

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "serviceDay", cascade = CascadeType.ALL)
  private List<Trip> trips = new ArrayList<>();

  @ManyToMany(mappedBy = "serviceDays")
  private Set<CalendarDate> calendarDates = new HashSet<>();

  public ServiceDay(String name) {
    this.name = name;
  }

  public ServiceDay() {
  }

  public ServiceDay(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public ServiceDay(Long id, String name, List<Trip> trips, Set<CalendarDate> calendarDates) {
    this.id = id;
    this.name = name;
    this.trips = trips;
    this.calendarDates = calendarDates;
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

  public List<Trip> getTrips() {
    return trips;
  }

  public void setTrips(List<Trip> trips) {
    this.trips = trips;
  }

  public Set<CalendarDate> getCalendarDates() {
    return calendarDates;
  }

  public void setCalendarDates(Set<CalendarDate> calendarDates) {
    this.calendarDates = calendarDates;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    ServiceDay serviceDay = (ServiceDay) o;
    return id.equals(serviceDay.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "ServiceDay{" +
        "name='" + name + '\'' +
        '}';
  }
}
