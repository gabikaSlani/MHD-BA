package com.fmph.diplomovka.raptor.dataStructure.sorters;

import com.fmph.diplomovka.raptor.results.models.RoundTimeAction;
import java.util.Comparator;

public class SortByRound implements Comparator<RoundTimeAction> {

  public int compare(RoundTimeAction a, RoundTimeAction b) {
    return a.getRound() - b.getRound();
  }
}
