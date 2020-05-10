package com.fmph.diplomovka.raptor.dataStructure.sorters;

import com.fmph.diplomovka.model.Trip;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import java.util.Comparator;

public class SortTrips implements Comparator<Trip> {

  @Override
  public int compare(Trip trip1, Trip trip2) {
    Time time1 = arrivalTimeOfFirstStopInTrip(trip1);
    Time time2 = arrivalTimeOfFirstStopInTrip(trip2);
    if (time1.equals(time2)) {
      return 0;
    }
    return time1.isBefore(time2) ? -1 : 1;
  }

  private Time arrivalTimeOfFirstStopInTrip(Trip trip) {
    return new Time(trip.getStopTimes().get(0).getTime());
  }
}
