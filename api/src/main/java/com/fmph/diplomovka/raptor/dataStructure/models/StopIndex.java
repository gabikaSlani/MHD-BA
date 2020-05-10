package com.fmph.diplomovka.raptor.dataStructure.models;

import com.fmph.diplomovka.model.Stop;
import java.util.Objects;

public class StopIndex {

  private Stop stop;
  private Integer index;

  public StopIndex() {
  }

  public StopIndex(Stop stop, Integer index) {
    this.stop = stop;
    this.index = index;
  }

  public Stop getStop() {
    return stop;
  }

  public void setStop(Stop stop) {
    this.stop = stop;
  }

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    StopIndex stopIndex = (StopIndex) o;
    return Objects.equals(stop, stopIndex.stop) &&
        Objects.equals(index, stopIndex.index);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stop, index);
  }
}
