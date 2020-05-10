package com.fmph.diplomovka.raptor.dataStructure.models;

import com.fmph.diplomovka.model.Stop;
import java.io.Serializable;


public class StopTimeObject implements Serializable {

  private static final long serialVersionUID = 1L;

  private Stop stop;
  private Time time;
  private Integer delay;

  public StopTimeObject() {
  }

  public StopTimeObject(Stop stop, Time time, Integer delay) {
    this.stop = stop;
    this.time = time;
    this.delay = delay;
  }

  public Stop getStop() {
    return stop;
  }

  public void setStop(Stop stop) {
    this.stop = stop;
  }

  public Time getTime() {
    return time;
  }

  public void setTime(Time time) {
    this.time = time;
  }

  public Integer getDelay() {
    return delay;
  }

  public void setDelay(Integer delay) {
    this.delay = delay;
  }
}
