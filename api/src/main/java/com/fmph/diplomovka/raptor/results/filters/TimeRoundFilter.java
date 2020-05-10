package com.fmph.diplomovka.raptor.results.filters;

import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import com.fmph.diplomovka.raptor.dataStructure.sorters.SortTimeRoundByTime;
import com.fmph.diplomovka.raptor.results.models.TimeRound;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TimeRoundFilter {

  public List<TimeRound> filterTimeRounds(List<TimeRound> timeRounds) {
    List<TimeRound> filteredTimeRounds = filterLowestRoundForEachTime(timeRounds);
    filteredTimeRounds.sort(new SortTimeRoundByTime());
    filteredTimeRounds = removeLaterTimeInGreaterRound(filteredTimeRounds);
    return removeTooLateInLowerRound(filteredTimeRounds);
  }

  //input timeRounds has for each round one earliest time
  public List<TimeRound> filterLowestRoundForEachTime(List<TimeRound> timeRounds) {
    if (timeRounds.size() < 2) {
      return timeRounds;
    }
    Map<Time, Integer> map = new HashMap<>();
    for (TimeRound timeRound : timeRounds) {
      if (mapNotContainsTimeOrRoundIsLower(map, timeRound)) {
        map.put(timeRound.getTime(), timeRound.getRound());
      }
    }
    return convertTimeRoundMapToTimeRoundList(map);
  }

  //input timeRounds has for each time lowest round and for each round earliest time
  public List<TimeRound> removeLaterTimeInGreaterRound(List<TimeRound> timeRounds) {
    if (timeRounds.size() < 2) {
      return timeRounds;
    }
    List<TimeRound> toRemove = new ArrayList<>();
    for (int i = 0; i < timeRounds.size() - 1; i++) {
      for (int j = i + 1; j < timeRounds.size(); j++) {
        if (timeRounds.get(i).getRound() < timeRounds.get(j).getRound()) {
          toRemove.add(timeRounds.get(j));
        }
      }
    }
    return toRemove.size() > 0 ? removeTimeRoundsFromList(timeRounds, toRemove) : timeRounds;
  }

  public List<TimeRound> removeTooLateInLowerRound(List<TimeRound> timeRounds) {
    int TRANSFER_PENALTY = 5;
    if (timeRounds.size() < 2) {
      return timeRounds;
    }
    List<TimeRound> toRemove = new ArrayList<>();
    TimeRound earliestTimeRound = timeRounds.get(0);
    for (int i = 1; i < timeRounds.size(); i++) {
      TimeRound timeRound = timeRounds.get(i);
      int roundDifference = earliestTimeRound.getRound() - timeRound.getRound();
      int timeDifferenceInSeconds = timeRound.getTime().minus(earliestTimeRound.getTime());
      if (timeDifferenceInSeconds / 60 > roundDifference * TRANSFER_PENALTY) {
        toRemove.add(timeRound);
      }
    }
    return toRemove.size() > 0 ? removeTimeRoundsFromList(timeRounds, toRemove) : timeRounds;
  }

  public List<TimeRound> convertRoundTimeMapToTimeRoundList(Map<Integer, Time> map) {
    List<TimeRound> timeRounds = new ArrayList<>();
    for (Map.Entry<Integer, Time> entry : map.entrySet()) {
      timeRounds.add(new TimeRound(entry.getValue(), entry.getKey()));
    }
    return timeRounds;
  }

  private List<TimeRound> removeTimeRoundsFromList(List<TimeRound> timeRounds,
      List<TimeRound> toRemove) {
    return timeRounds.stream().filter(tr -> !toRemove.contains(tr)).collect(Collectors.toList());
  }

  private List<TimeRound> convertTimeRoundMapToTimeRoundList(Map<Time, Integer> map) {
    List<TimeRound> timeRounds = new ArrayList<>();
    for (Map.Entry<Time, Integer> entry : map.entrySet()) {
      timeRounds.add(new TimeRound(entry.getKey(), entry.getValue()));
    }
    return timeRounds;
  }

  private boolean mapNotContainsTimeOrRoundIsLower(Map<Time, Integer> map, TimeRound timeRound) {
    return !map.containsKey(timeRound.getTime())
        || timeRound.getRound() < map.get(timeRound.getTime());
  }
}
