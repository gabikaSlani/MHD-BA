package com.fmph.diplomovka.services.models;

import com.fmph.diplomovka.model.Stop;
import org.springframework.stereotype.Component;

@Component
public class SearchParams {

    private Stop fromStop;

    private Stop toStop;

    private Long time;

    private Integer number;

    private Integer maxNumberOfTransfers;

    private Integer maxTimeOfWalking;

    private Integer minTimeForTransfer;

    private Boolean onlyLowFloor;

    public SearchParams(Builder builder) {
        this.fromStop = builder.fromStop;
        this.toStop = builder.toStop;
        this.time = builder.time;
        this.number = builder.number;
        this.maxNumberOfTransfers = builder.maxNumberOfTransfers;
        this.maxTimeOfWalking = builder.maxTimeOfWalking;
        this.minTimeForTransfer = builder.minTimeForTransfer;
        this.onlyLowFloor = builder.onlyLowFloor;
    }

    public SearchParams() {
    }

    public Stop getFromStop() {
        return fromStop;
    }

    public void setFromStop(Stop fromStop) {
        this.fromStop = fromStop;
    }

    public Stop getToStop() {
        return toStop;
    }

    public void setToStop(Stop toStop) {
        this.toStop = toStop;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

    public void setMaxTimeOfWalking(Integer maxTimeOfWlaking) {
        this.maxTimeOfWalking = maxTimeOfWlaking;
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

    public static Builder searchParamsBuilder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "SearchParams{" +
                "fromStop=" + fromStop.getName() +
                ", toStop=" + toStop.getName() +
                ", time=" + time +
                ", number=" + number +
                ", maxNumberOfTransfers=" + maxNumberOfTransfers +
                ", maxTimeOfWalking=" + maxTimeOfWalking +
                ", minTimeForTransfer=" + minTimeForTransfer +
                ", onlyLowFloor=" + onlyLowFloor +
                '}';
    }

    public static final class Builder {
        private Stop fromStop;
        private Stop toStop;
        private Long time;
        private Integer number;
        private Integer maxNumberOfTransfers;
        private Integer maxTimeOfWalking;
        private Integer minTimeForTransfer;
        private Boolean onlyLowFloor;

        private Builder() {
        }

        public Builder withFromStop(Stop fromStop) {
            this.fromStop = fromStop;
            return this;
        }

        public Builder withToStop(Stop toStop) {
            this.toStop = toStop;
            return this;
        }

        public Builder withTime(Long time) {
            this.time = time;
            return this;
        }

        public Builder withNumber(Integer number) {
            this.number = number;
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

        public  Builder withMinTimeForTransfer(Integer minTimeForTransfer) {
            this.minTimeForTransfer = minTimeForTransfer;
            return this;
        }

        public Builder withOnlyLowFloor(Boolean onlyLowFloor) {
            this.onlyLowFloor = onlyLowFloor;
            return this;
        }

        public SearchParams build() {
            return new SearchParams(this);
        }
    }
}
