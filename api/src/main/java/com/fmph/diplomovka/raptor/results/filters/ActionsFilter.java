package com.fmph.diplomovka.raptor.results.filters;

import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import com.fmph.diplomovka.raptor.results.models.RoundTimeAction;
import com.fmph.diplomovka.raptor.results.models.TimeRound;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ActionsFilter {

  private final TimeRoundFilter timeRoundFilter;

  public ActionsFilter(TimeRoundFilter timeRoundFilter) {
    this.timeRoundFilter = timeRoundFilter;
  }

  public void filterStopRoundActions(Map<Stop, List<RoundTimeAction>> roundActions) {
    for (Map.Entry<Stop, List<RoundTimeAction>> sra : roundActions.entrySet()) {
      Stop stop = sra.getKey();
      if (sra.getValue().size() > 1) {
        List<RoundTimeAction> filteredRoundTimeActions = filterRoundActions(sra.getValue());
        roundActions.put(stop, filteredRoundTimeActions);
      }
    }
  }

  private List<RoundTimeAction> filterRoundActions(List<RoundTimeAction> roundTimeActions) {
    List<TimeRound> filteredTimeRounds = filterEarliestTimeForEachRound(roundTimeActions);
    filteredTimeRounds = timeRoundFilter.filterTimeRounds(filteredTimeRounds);
    return createFilteredRoundActions(roundTimeActions, filteredTimeRounds);
  }

  private List<TimeRound> filterEarliestTimeForEachRound(List<RoundTimeAction> roundTimeActions) {
    Map<Integer, Time> map = new HashMap<>();
    for (RoundTimeAction roundTimeAction : roundTimeActions) {
      if (mapNotContainRoundOrTimeIsEarlier(map, roundTimeAction)) {
        map.put(roundTimeAction.getRound(), roundTimeAction.getArrivalTime());
      }
    }
    return timeRoundFilter.convertRoundTimeMapToTimeRoundList(map);
  }

  private List<RoundTimeAction> createFilteredRoundActions(List<RoundTimeAction> roundTimeActions,
      List<TimeRound> filteredTimeRounds) {
    return roundTimeActions.stream()
        .filter(
            ra -> filteredTimeRounds.contains(new TimeRound(ra.getArrivalTime(), ra.getRound())))
        .collect(Collectors.toList());
  }

  private boolean mapNotContainRoundOrTimeIsEarlier(Map<Integer, Time> map,
      RoundTimeAction roundTimeAction) {
    return !map.containsKey(roundTimeAction.getRound()) || roundTimeAction.getArrivalTime()
        .isBefore(map.get(roundTimeAction.getRound()));
  }

}
