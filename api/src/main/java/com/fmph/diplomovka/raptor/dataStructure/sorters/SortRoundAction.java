package com.fmph.diplomovka.raptor.dataStructure.sorters;

import com.fmph.diplomovka.raptor.results.models.RoundTimeAction;
import java.util.Comparator;

public class SortRoundAction implements Comparator<RoundTimeAction> {

  public int compare(RoundTimeAction a, RoundTimeAction b) {
    return a.getRound().equals(b.getRound())
        ? b.getArrivalTime().minus(a.getArrivalTime())
        : b.getRound() - a.getRound();
  }
}
