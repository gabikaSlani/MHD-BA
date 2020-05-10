package com.fmph.diplomovka.services.mappers;

import static java.util.stream.Collectors.toList;

import api.com.fmph.diplomovka.model.RouteInfoDom;
import api.com.fmph.diplomovka.model.StopAreaDom;
import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.StopArea;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class StopAreaMapper {

  private final StopNameMapper stopNameMapper;
  private final RouteInfoMapper routeInfoMapper;

  public StopAreaMapper(StopNameMapper stopNameMapper, RouteInfoMapper routeInfoMapper) {
    this.stopNameMapper = stopNameMapper;
    this.routeInfoMapper = routeInfoMapper;
  }

  public StopAreaDom mapStopAreaToStopAreaDom(StopArea stopArea, Set<Route> routes) {
    return new StopAreaDom()
        .id(stopArea.getId().intValue())
        .name(stopNameMapper.createStopNameDom(stopArea.getName()))
        .routes(mapRoutesToListOfRouteInfoDom(routes));
  }

  public StopAreaDom mapStopAreaToStopAreaDom(StopArea stopArea) {
    return new StopAreaDom()
        .id(stopArea.getId().intValue())
        .name(stopNameMapper.createStopNameDom(stopArea.getName()));
  }

  private List<RouteInfoDom> mapRoutesToListOfRouteInfoDom(Set<Route> routes) {
    return routes.stream().map(routeInfoMapper::createRouteInfoDom).collect(toList());
  }


}
