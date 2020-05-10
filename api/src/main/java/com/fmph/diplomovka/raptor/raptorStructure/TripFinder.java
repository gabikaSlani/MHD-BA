package com.fmph.diplomovka.raptor.raptorStructure;


import com.fmph.diplomovka.model.ServiceDay;
import com.fmph.diplomovka.raptor.dataStructure.models.Subroute;
import com.fmph.diplomovka.raptor.dataStructure.models.SubrouteTrip;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TripFinder {

  private Map<SubrouteServiceDay, Integer> lastFoundTripsIndices;

  public TripFinder() {
    lastFoundTripsIndices = new HashMap<>();
  }

  public SubrouteTrip getEarliestTrip(Subroute subroute, int stopIndex, Time searchedTime,
      Set<ServiceDay> possibleServiceDays, boolean onlyLowFloor) {
    SubrouteTrip earliestTrip = null;
    for (ServiceDay serviceDay : possibleServiceDays) {
      SubrouteTrip foundTrip = getEarliestTripOperatingInServiceDay(subroute, stopIndex,
          searchedTime, serviceDay, onlyLowFloor);
      if (earliestTrip == null || foundTrip != null
          && tripIsEarlierThanEarliestTrip(foundTrip, earliestTrip, stopIndex)) {
        earliestTrip = foundTrip;
      }
    }
    return earliestTrip;
  }

  private SubrouteTrip getEarliestTripOperatingInServiceDay(Subroute subroute, int stopIndex,
      Time searchedTime, ServiceDay serviceDay, boolean onlyLowFloor) {
    SubrouteServiceDay subrouteServiceDay = new SubrouteServiceDay(subroute, serviceDay);
    List<SubrouteTrip> subrouteTrips = subroute.getSubrouteTrips().get(serviceDay.getId());
    if (subrouteTrips == null) {
      return null;
    }

    if (!this.lastFoundTripsIndices.containsKey(subrouteServiceDay)) {
      this.lastFoundTripsIndices.put(subrouteServiceDay, subrouteTrips.size() - 1);
    }

    SubrouteTrip lastFound = null;

    if (lastSubrouteTripHasEarlierArrivalTime(subrouteTrips, searchedTime, stopIndex)) {
      return null;
    }
    for (int i = this.lastFoundTripsIndices.get(subrouteServiceDay); i >= 0; i--) {
      SubrouteTrip trip = subrouteTrips.get(i);
      Time tripStopTime = trip.getStopTimeObjects()[stopIndex].getTime();
      if (!onlyLowFloor || trip.isLowFloor()) {
        if (tripStopTime.isBefore(searchedTime)) {
          break;
        } else {
          lastFound = trip;
        }
        lastFoundTripsIndices.put(subrouteServiceDay, i);
      }
    }

    return lastFound;
  }

  private Time getArrivalTimeInStopIndex(SubrouteTrip subrouteTrip, int stopIndex) {
    return subrouteTrip.getStopTimeObjects()[stopIndex].getTime();
  }

  private boolean tripIsEarlierThanEarliestTrip(SubrouteTrip trip, SubrouteTrip earliestTrip,
      int stopIndex) {
    return getArrivalTimeInStopIndex(trip, stopIndex)
        .isBefore(getArrivalTimeInStopIndex(earliestTrip, stopIndex));
  }

  private boolean lastSubrouteTripHasEarlierArrivalTime(List<SubrouteTrip> subrouteTrips,
      Time arrivalTime, int stopIndex) {
    Time lastTripArrivalTimeAtStop = subrouteTrips.get(subrouteTrips.size() - 1)
        .getStopTimeObjects()[stopIndex].getTime();
    return lastTripArrivalTimeAtStop.isBefore(arrivalTime);

  }
}
