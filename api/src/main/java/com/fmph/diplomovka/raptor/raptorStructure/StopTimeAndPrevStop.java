package com.fmph.diplomovka.raptor.raptorStructure;

public class StopTimeAndPrevStop{
    private Long time;
    private Long prevStopId;

    public StopTimeAndPrevStop(Long time, Long prevStopId) {
        this.time = time;
        this.prevStopId = prevStopId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getPrevStopId() {
        return prevStopId;
    }

    public void setPrevStopId(Long prevStopId) {
        this.prevStopId = prevStopId;
    }
}