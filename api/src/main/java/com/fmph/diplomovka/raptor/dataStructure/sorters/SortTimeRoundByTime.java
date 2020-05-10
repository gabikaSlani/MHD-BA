package com.fmph.diplomovka.raptor.dataStructure.sorters;

import com.fmph.diplomovka.raptor.results.models.TimeRound;
import java.util.Comparator;

public class SortTimeRoundByTime implements Comparator<TimeRound> {

  @Override
  public int compare(TimeRound a, TimeRound b) {
    return a.getTime().minus(b.getTime());
  }
}
