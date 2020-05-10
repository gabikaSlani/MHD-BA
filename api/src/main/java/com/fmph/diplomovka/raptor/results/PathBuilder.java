package com.fmph.diplomovka.raptor.results;

import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.raptor.dataStructure.models.Action;
import com.fmph.diplomovka.raptor.dataStructure.models.ActionType;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import com.fmph.diplomovka.raptor.results.filters.ActionsFilter;
import com.fmph.diplomovka.raptor.results.models.RoundTimeAction;
import com.fmph.diplomovka.services.models.Path;
import com.fmph.diplomovka.services.models.SearchParams;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PathBuilder {

  private final ActionsFilter actionsFilter;

  public PathBuilder(ActionsFilter actionsFilter) {
    this.actionsFilter = actionsFilter;
  }

  public List<Path> buildPaths(SearchParams searchParams, RaptorResults raptorResults) {
    actionsFilter.filterStopRoundActions(raptorResults.getRoundActions());
    return createPaths(raptorResults, searchParams);
  }

  private List<Path> createPaths(RaptorResults raptorResults, SearchParams searchParams) {
    List<Path> resultPaths = new ArrayList<>();
    for (Stop endStop : searchParams.getEndStops()) {
      if (raptorResults.getRoundActions().containsKey(endStop)) {
        for (RoundTimeAction roundTimeAction : raptorResults.getRoundActions().get(endStop)) {
          List<Action> finalActions = new ArrayList<>();
          finalActions.add(roundTimeAction.getAction());
          traverseActionsRecursive(roundTimeAction.getAction().getStartStop(), roundTimeAction
                  .getArrivalTime(), roundTimeAction.getRound(), raptorResults, finalActions,
              resultPaths,
              searchParams);
        }
      }
    }
    return resultPaths;
  }

  private void traverseActionsRecursive(Stop stop, Time arrivalTime, int round,
      RaptorResults raptorResults, List<Action> actions, List<Path> paths, SearchParams searchParams
  ) {
      if (round < 0) {
          return;
      }
    if (isStartStop(stop, searchParams)) {
      if (wasSearchedFromActualLocation(searchParams)) {
        addInitialWalking(searchParams, stop, actions);
      }
      Collections.reverse(actions);
      paths.add(new Path(actions, arrivalTime, searchParams));
    } else {
      List<RoundTimeAction> roundTimeActionForStop = raptorResults.getRoundActions().get(stop);
      for (RoundTimeAction roundTimeAction : roundTimeActionForStop) {
        List<Action> actionsPlus = new ArrayList<>(actions);
        actionsPlus.add(roundTimeAction.getAction());
        traverseActionsRecursive(roundTimeAction.getAction().getStartStop(), arrivalTime, round - 1,
            raptorResults, actionsPlus, paths, searchParams);
      }
    }
  }

  private void addInitialWalking(SearchParams searchParams, Stop stop, List<Action> actions) {
    actions.add(
        new Action(ActionType.WALKING, searchParams.getStartStops().get(stop), null, null, stop));
  }

  private boolean wasSearchedFromActualLocation(SearchParams searchParams) {
    return searchParams.getActualLocation();
  }

  private boolean isStartStop(Stop stop, SearchParams searchParams) {
    return searchParams.getStartStops().keySet().contains(stop);
  }

}
