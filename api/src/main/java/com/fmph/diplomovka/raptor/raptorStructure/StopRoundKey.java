package com.fmph.diplomovka.raptor.raptorStructure;

import java.util.Objects;

public class StopRoundKey {
    private Long stopId;
    private Integer round;


    public StopRoundKey(Long stopId, Integer round) {
        this.stopId = stopId;
        this.round = round;
    }

    public Long getStopId() {
        return stopId;
    }

    public void setStopId(Long stopId) {
        this.stopId = stopId;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StopRoundKey that = (StopRoundKey) o;
        return stopId.equals(that.stopId) &&
                round.equals(that.round);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stopId, round);
    }
}