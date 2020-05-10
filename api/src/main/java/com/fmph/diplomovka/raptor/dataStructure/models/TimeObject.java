package com.fmph.diplomovka.raptor.dataStructure.models;

import com.fmph.diplomovka.model.ServiceDay;

public class TimeObject {

  private Long time;
  private Integer delay;
  private ServiceDay serviceDay;

  public TimeObject(Long time, Integer delay, ServiceDay serviceDay) {
    this.time = time;
    this.delay = delay;
    this.serviceDay = serviceDay;
  }

  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

  public Integer getDelay() {
    return delay;
  }

  public void setDelay(Integer delay) {
    this.delay = delay;
  }

  public ServiceDay getServiceDay() {
    return serviceDay;
  }

  public void setServiceDay(ServiceDay serviceDay) {
    this.serviceDay = serviceDay;
  }
}
