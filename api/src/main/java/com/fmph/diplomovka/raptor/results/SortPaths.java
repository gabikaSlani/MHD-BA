package com.fmph.diplomovka.raptor.results;

import com.fmph.diplomovka.services.models.Path;
import java.util.Comparator;

public class SortPaths implements Comparator<Path> {

  @Override
  //sort by departure time ascendant and by arrival time ascendant
  public int compare(Path path1, Path path2) {
    return path1.getDepartureTime().equals(path2.getDepartureTime())
        ? path1.getArrivalTime().minus(path2.getArrivalTime())
        : path1.getDepartureTime().minus(path2.getDepartureTime());
  }
}
