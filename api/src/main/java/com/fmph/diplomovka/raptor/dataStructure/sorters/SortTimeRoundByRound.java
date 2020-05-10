package com.fmph.diplomovka.raptor.dataStructure.sorters;

import com.fmph.diplomovka.raptor.results.models.TimeRound;
import java.util.Comparator;

public class SortTimeRoundByRound implements Comparator<TimeRound> {

  @Override
  public int compare(TimeRound a, TimeRound b) {
    return b.getRound() - a.getRound();
  }
}
