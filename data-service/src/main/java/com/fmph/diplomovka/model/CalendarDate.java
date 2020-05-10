package com.fmph.diplomovka.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "calendar_dates")
public class CalendarDate implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "calendar_date_id")
  private Long id;

  @Column(name = "date")
  private LocalDate date;

  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(
      name = "date_service_day",
      joinColumns = {@JoinColumn(name = "calendar_date_id")},
      inverseJoinColumns = {@JoinColumn(name = "service_day_id")}
  )
  private Set<ServiceDay> serviceDays = new HashSet<>();

  public CalendarDate(LocalDate date, Set<ServiceDay> serviceDays) {
    this.date = date;
    this.serviceDays = serviceDays;
  }

  public CalendarDate() {
  }

  public CalendarDate(Long id, LocalDate date) {
    this.date = date;
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Set<ServiceDay> getServiceDays() {
    return serviceDays;
  }

  public void setServiceDays(Set<ServiceDay> serviceDays) {
    this.serviceDays = serviceDays;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    CalendarDate that = (CalendarDate) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
