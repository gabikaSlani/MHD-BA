package com.fmph.diplomovka.raptor.dataStructure.sorters;

import com.fmph.diplomovka.raptor.dataStructure.models.SubrouteTrip;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import java.util.Comparator;

public class SortSubrouteTrips implements Comparator<SubrouteTrip> {

  @Override
  public int compare(SubrouteTrip subrouteTrip1, SubrouteTrip subrouteTrip2) {
    Time time1 = arrivalTimeOfFirstStopInTrip(subrouteTrip1);
    Time time2 = arrivalTimeOfFirstStopInTrip(subrouteTrip2);
    if (time1.equals(time2)) {
      return 0;
    }
    return time1.isBefore(time2) ? -1 : 1;
  }

  private Time arrivalTimeOfFirstStopInTrip(SubrouteTrip subrouteTrip) {
    return subrouteTrip.getStopTimeObjects()[0].getTime();
  }
}
