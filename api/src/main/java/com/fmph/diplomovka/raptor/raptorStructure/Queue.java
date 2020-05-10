package com.fmph.diplomovka.raptor.raptorStructure;

import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.raptor.DataStructure;
import com.fmph.diplomovka.raptor.dataStructure.models.Subroute;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Queue {

  private Map<Subroute, Stop> queue = new HashMap<>();

  private final DataStructure dataStructure;

  public Queue(DataStructure dataStructure, Set<Stop> markedStops) {
    this.dataStructure = dataStructure;
    initQueue(markedStops);
  }

  private void initQueue(Set<Stop> markedStops) {
    for (Stop stop : markedStops) {
      for (String subrouteId : dataStructure.getDataStructureModel().getStopSubroutes().get(stop)) {
        Subroute subroute = dataStructure.getDataStructureModel().getSubroutesByIndex()
            .get(subrouteId);
        if (!this.queue.containsKey(subroute) || stopAIsBeforeStopB(subroute, stop,
            this.queue.get(subroute))) {
          this.queue.put(subroute, stop);
        }
      }
    }
  }

  private boolean stopAIsBeforeStopB(Subroute subroute, Stop stopA, Stop stopB) {
    return dataStructure.getDataStructureModel().getStopIndexInSubroute().get(subroute.getId())
        .get(stopA.getId()) <
        dataStructure.getDataStructureModel().getStopIndexInSubroute().get(subroute.getId())
            .get(stopB.getId());
  }

  public Map<Subroute, Stop> getQueue() {
    return queue;
  }

  @Override
  public String toString() {
    StringBuffer result = new StringBuffer("Queue: ");
    for (Map.Entry<Subroute, Stop> entry : queue.entrySet()) {
      result.append("(").append(entry.getKey().toString()).append(", ")
          .append(entry.getValue().toString()).append(")");
      result.append(", ");
    }
    return result.toString();
  }
}
