package com.fmph.diplomovka.importdata.model;

import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import java.time.LocalDate;

public class DelayRow {

  private LocalDate date;
  private Time time;
  private String routeName;
  private Long stopId;
  private Integer delay;

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Time getTime() {
    return time;
  }

  public void setTime(Time time) {
    this.time = time;
  }

  public String getRouteName() {
    return routeName;
  }

  public void setRouteName(String routeName) {
    this.routeName = routeName;
  }

  public Long getStopId() {
    return stopId;
  }

  public void setStopId(Long stopId) {
    this.stopId = stopId;
  }

  public Integer getDelay() {
    return delay;
  }

  public void setDelay(Integer delay) {
    this.delay = delay;
  }

  @Override
  public String toString() {
    return "DelayRow{" +
        "date=" + date +
        ", time=" + time.toString() +
        ", routeName=" + routeName +
        ", stopId=" + stopId +
        ", delay=" + delay +
        "}";
  }
}
