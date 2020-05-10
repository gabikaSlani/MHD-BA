package com.fmph.diplomovka.raptor.raptorStructure;

import com.fmph.diplomovka.model.ServiceDay;
import com.fmph.diplomovka.raptor.dataStructure.models.StopTimeObject;
import com.fmph.diplomovka.raptor.dataStructure.models.Subroute;
import com.fmph.diplomovka.raptor.dataStructure.models.SubrouteTrip;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import java.util.List;
import java.util.Set;

public class TodayTripFinder {

  public TodayTripFinder() {
  }

  public SubrouteTrip findEarliestTrip(Subroute subroute, int stopIndex, Time searchedTime,
      Set<ServiceDay> possibleServiceDays, boolean onlyLowFloor) {
    SubrouteTrip earliestTrip = null;
    for (ServiceDay serviceDay : possibleServiceDays) {
      SubrouteTrip foundTrip = getEarliestTripOperatingOnServiceDay(subroute, stopIndex,
          searchedTime, serviceDay, onlyLowFloor);
      if (earliestTrip == null || foundTrip != null
          && foundTripIsEarlierThanEarliestTrip(foundTrip, earliestTrip, stopIndex)) {
        earliestTrip = foundTrip;
      }
    }
    return earliestTrip;
  }


  private SubrouteTrip getEarliestTripOperatingOnServiceDay(Subroute subroute, int stopIndex,
      Time searchedTime, ServiceDay serviceDay, boolean onlyLowFloor) {
    List<SubrouteTrip> subrouteTrips = subroute.getSubrouteTrips().get(serviceDay.getId());
    if (subrouteTrips == null) {
      return null;
    }
    SubrouteTrip lastFound = null;
    Time lastFoundTripTimeWithDelay = new Time().getMaxTime();
    for (SubrouteTrip subrouteTrip : subrouteTrips) {
      Time tripStopTimeWithDelay = getTripTimeWithDelayOnStopWithIndex(subrouteTrip, stopIndex);
      if (!onlyLowFloor || subrouteTrip.isLowFloor()) {
        if (searchedTime.isBeforeOrEqual(tripStopTimeWithDelay) &&
            tripStopTimeWithDelay.isBefore(lastFoundTripTimeWithDelay)) {
          lastFound = subrouteTrip;
          lastFoundTripTimeWithDelay = getTripTimeWithDelayOnStopWithIndex(lastFound, stopIndex);
        }
      }
    }
    return lastFound;
  }

  private boolean foundTripIsEarlierThanEarliestTrip(SubrouteTrip foundTrip,
      SubrouteTrip earliestTrip, int stopIndex) {
    return getTripTimeWithDelayOnStopWithIndex(foundTrip, stopIndex)
        .isBefore(getTripTimeWithDelayOnStopWithIndex(earliestTrip, stopIndex));
  }

  private StopTimeObject getStopTimeObject(SubrouteTrip trip, int stopIndex) {
    return trip.getStopTimeObjects()[stopIndex];
  }

  private Time getTripTimeWithDelayOnStopWithIndex(SubrouteTrip trip, int stopIndex) {
    StopTimeObject stopTimeObject = getStopTimeObject(trip, stopIndex);
    return new Time(stopTimeObject.getTime()).addMinutes(stopTimeObject.getDelay());
  }
}
