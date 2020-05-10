package com.fmph.diplomovka.raptor.results.filters;

import com.fmph.diplomovka.raptor.dataStructure.models.ActionType;
import com.fmph.diplomovka.raptor.results.SortPaths;
import com.fmph.diplomovka.services.models.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class PagePathsFilter {

  public List<Path> filter(List<Path> paths) {
    paths.sort(new SortPaths());
    return IntStream
        .range(0, paths.size())
        .filter(i -> !previousPathAndThisHaveOnlyWalkingAction(paths, i))
        .filter(i -> nextPathDepartsAndArriveLater(paths, i))
        .mapToObj(paths::get)
        .collect(Collectors.toList());
  }

  private boolean previousPathAndThisHaveOnlyWalkingAction(List<Path> paths, int index) {
    if (index == 0) {
      return false;
    }
    Path pathA = paths.get(index - 1);
    Path pathB = paths.get(index);
    return pathHasOnlyOneWalkingAction(pathA) && pathHasOnlyOneWalkingAction(pathB);
  }

  private boolean pathHasOnlyOneWalkingAction(Path path) {
    return path.getActions().size() == 1 &&
        path.getActions().get(0).getActionType().equals(ActionType.WALKING);
  }

  private boolean nextPathDepartsAndArriveLater(List<Path> paths, int index) {
    if (index == paths.size() - 1) {
      return true;
    }
    Path pathA = paths.get(index);
    Path pathB = paths.get(index + 1);
    if (pathsSizesAreEqual(pathA, pathB)
        && (pathADeparturesEarlierOrEqualAndComeLater(pathA, pathB)
        || pathADeparturesEarlierAndComeLaterOrEqual(pathA, pathB))) {
      return false;
    }
    return true;
  }

  private boolean pathsSizesAreEqual(Path pathA, Path pathB) {
    return pathA.getActions().size() == pathB.getActions().size();
  }

  private boolean pathADeparturesEarlierOrEqualAndComeLater(Path pathA, Path pathB) {
    return pathA.getDepartureTime().isBeforeOrEqual(pathB.getDepartureTime())
        && pathB.getArrivalTime().isBefore(pathA.getArrivalTime());
  }

  private boolean pathADeparturesEarlierAndComeLaterOrEqual(Path pathA, Path pathB) {
    return pathA.getDepartureTime().isBefore(pathB.getDepartureTime())
        && pathB.getArrivalTime().isBeforeOrEqual(pathA.getArrivalTime());
  }


}
