package com.fmph.diplomovka.services.models;

import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class SearchParams {

  private Map<Stop, Integer> startStops;
  private Set<Stop> endStops;
  private LocalDate dateFrom;
  private Time timeFrom;
  private Integer maxNumberOfTransfers;
  private Integer maxTimeOfWalking;
  private Integer minTimeForTransfer;
  private Boolean onlyLowFloor;
  private Boolean actualLocation;

  public SearchParams(Builder builder) {
    this.startStops = builder.startStops;
    this.endStops = builder.endStops;
    this.dateFrom = builder.dateFrom;
    this.timeFrom = builder.timeFrom;
    this.maxNumberOfTransfers = builder.maxNumberOfTransfers;
    this.maxTimeOfWalking = builder.maxTimeOfWalking;
    this.minTimeForTransfer = builder.minTimeForTransfer;
    this.onlyLowFloor = builder.onlyLowFloor;
    this.actualLocation = builder.actualLocation;
  }

  public SearchParams() {
  }

  public static Builder searchParamsBuilder() {
    return new Builder();
  }

  public Map<Stop, Integer> getStartStops() {
    return startStops;
  }

  public void setStartStops(Map<Stop, Integer> startStops) {
    this.startStops = startStops;
  }

  public Set<Stop> getEndStops() {
    return endStops;
  }

  public void setEndStops(Set<Stop> endStops) {
    this.endStops = endStops;
  }

  public Integer getMaxNumberOfTransfers() {
    return maxNumberOfTransfers;
  }

  public void setMaxNumberOfTransfers(Integer maxNumberOfTransfers) {
    this.maxNumberOfTransfers = maxNumberOfTransfers;
  }

  public Integer getMaxTimeOfWalking() {
    return maxTimeOfWalking;
  }

  public void setMaxTimeOfWalking(Integer maxTimeOfWalking) {
    this.maxTimeOfWalking = maxTimeOfWalking;
  }

  public Integer getMinTimeForTransfer() {
    return minTimeForTransfer;
  }

  public void setMinTimeForTransfer(Integer minTimeForTransfer) {
    this.minTimeForTransfer = minTimeForTransfer;
  }

  public Boolean getOnlyLowFloor() {
    return onlyLowFloor;
  }

  public void setOnlyLowFloor(Boolean onlyLowFloor) {
    this.onlyLowFloor = onlyLowFloor;
  }

  public Boolean getActualLocation() {
    return actualLocation;
  }

  public void setActualLocation(Boolean actualLocation) {
    this.actualLocation = actualLocation;
  }

  public LocalDate getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(LocalDate dateFrom) {
    this.dateFrom = dateFrom;
  }

  public Time getTimeFrom() {
    return timeFrom;
  }

  public void setTimeFrom(Time timeFrom) {
    this.timeFrom = timeFrom;
  }

  public static final class Builder {

    private Map<Stop, Integer> startStops;
    private Set<Stop> endStops;
    private LocalDate dateFrom;
    private Time timeFrom;
    private Integer maxNumberOfTransfers;
    private Integer maxTimeOfWalking;
    private Integer minTimeForTransfer;
    private Boolean onlyLowFloor;
    private Boolean actualLocation;

    private Builder() {
    }

    public Builder withStartStops(Map<Stop, Integer> startStops) {
      this.startStops = startStops;
      return this;
    }

    public Builder withEndStops(Set<Stop> endStops) {
      this.endStops = endStops;
      return this;
    }

    public Builder withDateFrom(LocalDate dateFrom) {
      this.dateFrom = dateFrom;
      return this;
    }

    public Builder withTimeFrom(Time timeFrom) {
      this.timeFrom = timeFrom;
      return this;
    }

    public Builder withMaxNumberOfTransfers(Integer maxNumberOfTransfers) {
      this.maxNumberOfTransfers = maxNumberOfTransfers;
      return this;
    }

    public Builder withMaxTimeOfWalking(Integer maxTimeOfWalking) {
      this.maxTimeOfWalking = maxTimeOfWalking;
      return this;
    }

    public Builder withMinTimeForTransfer(Integer minTimeForTransfer) {
      this.minTimeForTransfer = minTimeForTransfer;
      return this;
    }

    public Builder withOnlyLowFloor(Boolean onlyLowFloor) {
      this.onlyLowFloor = onlyLowFloor;
      return this;
    }

    public Builder withActualLocation(Boolean actualLocation) {
      this.actualLocation = actualLocation;
      return this;
    }

    public SearchParams build() {
      return new SearchParams(this);
    }
  }
}
