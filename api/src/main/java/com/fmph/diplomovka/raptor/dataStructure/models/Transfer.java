package com.fmph.diplomovka.raptor.dataStructure.models;

import com.fmph.diplomovka.model.Stop;
import java.io.Serializable;

public class Transfer implements Serializable {

  private static final long serialVersionUID = 1L;

  private Stop originStop;
  private Stop destinationStop;
  private Integer duration;

  public Transfer(Stop originStop, Stop destinationStop, Integer duration) {
    this.originStop = originStop;
    this.destinationStop = destinationStop;
    this.duration = duration;
  }

  public Transfer() {
  }

  public Stop getOriginStop() {
    return originStop;
  }

  public void setOriginStop(Stop originStop) {
    this.originStop = originStop;
  }

  public Stop getDestinationStop() {
    return destinationStop;
  }

  public void setDestinationStop(Stop destinationStop) {
    this.destinationStop = destinationStop;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  @Override
  public String toString() {
    return "Transfer{" +
        "originStop=" + originStop +
        ", destinationStop=" + destinationStop +
        ", duration=" + duration +
        '}';
  }
}
