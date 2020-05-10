package com.fmph.diplomovka.raptor.results.filters;

import static com.fmph.diplomovka.raptor.dataStructure.models.ActionType.TRIP;
import static com.fmph.diplomovka.raptor.dataStructure.models.ActionType.WALKING;

import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.raptor.dataStructure.models.Action;
import com.fmph.diplomovka.raptor.dataStructure.models.ActionType;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import com.fmph.diplomovka.raptor.results.models.TimeRound;
import com.fmph.diplomovka.services.models.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PathFilter {

  private final TimeRoundFilter timeRoundFilter;

  public PathFilter(TimeRoundFilter timeRoundFilter) {
    this.timeRoundFilter = timeRoundFilter;
  }

  public List<Path> filter(List<Path> paths) {
    List<Path> filteredPathsByActions = filterPathsByActions(paths);
    List<Path> filteredPathsByTimes = filterPathsByTimeAndNumberOfActions(filteredPathsByActions);
    return filterSimilarPaths(filteredPathsByTimes);
  }

  private List<Path> filterSimilarPaths(List<Path> paths) {
    if (paths.size() < 2) {
      return paths;
    }
    List<Path> toRemove = new ArrayList<>();
    for (int i = 0; i < paths.size() - 1; i++) {
      for (int j = i + 1; j < paths.size(); j++) {
        Path pathA = paths.get(i);
        Path pathB = paths.get(j);
        if (pathsAreSimilar(pathA, pathB)) {
          toRemove.add(pathA);
          break;
        }
      }
    }
    return removePathsFromPaths(paths, toRemove);
  }

  private List<Path> removePathsFromPaths(List<Path> paths, List<Path> toRemove) {
    for (Path path : toRemove) {
      paths.remove(path);
    }
    return paths;
  }

  private boolean pathsAreSimilar(Path pathA, Path pathB) {
    if (!pathA.getArrivalTime().equals(pathB.getArrivalTime())) {
      return false;
    }
    if (!pathA.getDepartureTime().equals(pathB.getDepartureTime())) {
      return false;
    }
    if (pathA.getActions().size() != pathB.getActions().size()) {
      return false;
    }
    return pathsActionsAreSimilar(pathA, pathB);
  }

  private boolean pathsActionsAreSimilar(Path pathA, Path pathB) {
    for (int i = 0; i < pathA.getActions().size(); i++) {
      if (actionsHaveDifferentTypes(pathA, pathB, i)) {
        return false;
      }
      if (nthActionOfPathHasType(i, pathA, TRIP) &&
          tripActionsHaveDifferentRoutes(pathA, pathB, i)) {
        return false;
      }
    }
    return true;
  }

  private boolean tripActionsHaveDifferentRoutes(Path pathA, Path pathB, int index) {
    return !getRouteOfNthAction(index, pathA).equals(getRouteOfNthAction(index, pathB));
  }

  private boolean actionsHaveDifferentTypes(Path pathA, Path pathB, int index) {
    return !pathA.getActions().get(index).getActionType()
        .equals(pathB.getActions().get(index).getActionType());
  }

  private Route getRouteOfNthAction(int n, Path path) {
    return path.getActions().get(n).getTripSegment().getSubrouteTrip().getRoute();
  }

  private boolean nthActionOfPathHasType(int n, Path path, ActionType actionType) {
    return path.getActions().get(n).getActionType().equals(actionType);
  }

  private List<Path> filterPathsByActions(List<Path> paths) {
    if (paths.size() < 2) {
      return paths;
    }
    List<Path> filteredPaths = new ArrayList<>();
    for (Path path : paths) {
      if (!pathContainsTwoNeighbourWalkingActions(path) &&
          !firstTwoStopsAreInSameStopArea(path) &&
          !lastTwoStopsAreInSameStopArea(path)) {
        filteredPaths.add(path);
      }
    }
    return filteredPaths;
  }

  private boolean pathContainsOnlyWalkingAction(Path path) {
    return path.getActions().size() == 1 && path.getActions().get(0).getActionType()
        .equals(WALKING);
  }

  private boolean lastTwoStopsAreInSameStopArea(Path path) {
    List<Action> actions = path.getActions();
    if (actions.size() > 1) {
      return actionStopsHaveSameStopAreas(actions, actions.size() - 1);
    }
    return false;
  }

  private boolean firstTwoStopsAreInSameStopArea(Path path) {
    if (path.getActions().size() > 1) {
      int firstIndex = 0;
      if (path.getActions().get(0).getStartStop() == null) {
        firstIndex = 1;
      }
      return actionStopsHaveSameStopAreas(path.getActions(), firstIndex);
    }
    return false;
  }

  private boolean pathContainsTwoNeighbourWalkingActions(Path path) {
    for (int i = 0; i < path.getActions().size() - 1; i++) {
      Action prevAction = path.getActions().get(i);
      Action action = path.getActions().get(i + 1);
      if (previousAndThisActionAreBothWalking(prevAction, action)) {
        return true;
      }
    }
    return false;
  }

  private boolean actionStopsHaveSameStopAreas(List<Action> actions, int index) {
    return actions.get(index).getStartStop().getStopArea()
        .equals(actions.get(index).getEndStop().getStopArea());
  }

  private boolean previousAndThisActionAreBothWalking(Action previousAction, Action action) {
    return previousAction.getActionType().equals(WALKING)
        && action.getActionType().equals(WALKING);
  }

  private List<Path> filterPathsByTimeAndNumberOfActions(List<Path> paths) {
    List<TimeRound> filteredTimeRounds = filterEarliestTimeForEachRound(paths);
    filteredTimeRounds = timeRoundFilter.filterTimeRounds(filteredTimeRounds);
    return createFilteredPaths(paths, filteredTimeRounds);
  }

  private List<TimeRound> filterEarliestTimeForEachRound(List<Path> paths) {
    Map<Integer, Time> bestTimeForRound = new HashMap<>();
    for (Path path : paths) {
      if (mapNotContainRoundOrArrivalTimeIsEarlier(bestTimeForRound, path)) {
        bestTimeForRound.put(path.getActions().size(), path.getArrivalTime());
      }
    }
    return timeRoundFilter.convertRoundTimeMapToTimeRoundList(bestTimeForRound);
  }

  private boolean mapNotContainRoundOrArrivalTimeIsEarlier(Map<Integer, Time> bestTimeForRounds,
      Path path) {
    int actionsSize = path.getActions().size();
    return !bestTimeForRounds.containsKey(actionsSize)
        || path.getArrivalTime().isBefore(bestTimeForRounds.get(actionsSize));
  }

  private List<Path> createFilteredPaths(List<Path> paths, List<TimeRound> filteredTimeRounds) {
    return paths.stream()
        .filter(p -> filteredTimeRounds
            .contains(new TimeRound(p.getArrivalTime(), p.getActions().size())))
        .collect(Collectors.toList());
  }

}
