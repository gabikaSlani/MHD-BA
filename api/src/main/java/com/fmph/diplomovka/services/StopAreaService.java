package com.fmph.diplomovka.services;

import static java.util.stream.Collectors.toList;

import api.com.fmph.diplomovka.model.StopAreaDom;
import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.model.StopArea;
import com.fmph.diplomovka.raptor.DataStructure;
import com.fmph.diplomovka.raptor.dataStructure.models.Subroute;
import com.fmph.diplomovka.service.StopAreaDataService;
import com.fmph.diplomovka.services.mappers.StopAreaMapper;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class StopAreaService {

  private final StopAreaMapper stopAreaMapper;
  private final StopAreaDataService stopAreaDataService;
  private final DataStructure dataStructure;

  public StopAreaService(StopAreaMapper stopAreaMapper, StopAreaDataService stopAreaDataService,
      DataStructure dataStructure) {
    this.stopAreaMapper = stopAreaMapper;
    this.stopAreaDataService = stopAreaDataService;
    this.dataStructure = dataStructure;
  }

  public List<StopAreaDom> getAllStopAreas() {
    return stopAreaDataService.findAll().stream()
        .map(sa -> stopAreaMapper
            .mapStopAreaToStopAreaDom(sa, createSetOfRoutesThatOperateOnStopArea(sa)))
        .collect(toList());

  }

  private Set<Route> createSetOfRoutesThatOperateOnStopArea(StopArea stopArea) {
    Set<Route> routes = new HashSet<>();
    for (Stop stop : stopArea.getStops()) {
      List<String> subroutesIds = dataStructure.getDataStructureModel().getStopSubroutes()
          .get(stop);
      for (Entry<String, Subroute> entry : dataStructure.getDataStructureModel()
          .getSubroutesByIndex().entrySet()) {
        if (subroutesIds.contains(entry.getKey())) {
          routes.add(entry.getValue().getRoute());
        }
      }
    }
    return routes;
  }
}
