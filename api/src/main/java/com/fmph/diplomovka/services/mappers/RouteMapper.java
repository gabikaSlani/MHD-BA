package com.fmph.diplomovka.services.mappers;

import static java.util.stream.Collectors.toList;

import api.com.fmph.diplomovka.model.RouteDetailDom;
import api.com.fmph.diplomovka.model.RouteDom;
import api.com.fmph.diplomovka.model.StopInfoDom;
import api.com.fmph.diplomovka.model.StopNameDom;
import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.raptor.DataStructure;
import com.fmph.diplomovka.raptor.dataStructure.models.Subroute;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Component;

@Component
public class RouteMapper {

  private final RouteInfoMapper routeInfoMapper;
  private final StopNameMapper stopNameMapper;
  private final DataStructure dataStructure;
  private final StopMapper stopMapper;

  public RouteMapper(RouteInfoMapper routeInfoMapper, StopNameMapper stopNameMapper,
      DataStructure dataStructure, StopMapper stopMapper) {
    this.routeInfoMapper = routeInfoMapper;
    this.stopNameMapper = stopNameMapper;
    this.dataStructure = dataStructure;
    this.stopMapper = stopMapper;
  }


  public RouteDom mapRouteToRouteDom(Route route) {
    return new RouteDom()
        .id(route.getId().intValue())
        .routeInfo(routeInfoMapper.createRouteInfoDom(route))
        .startStopName(getFirstRouteStopName(route))
        .endStopName(getLastRouteStopName(route));
  }

  public RouteDetailDom mapRouteToRouteDetailDom(Route route) {
    return new RouteDetailDom()
        .routeInfo(routeInfoMapper.createRouteInfoDom(route))
        .stops(mapRouteStopsToListOfStopInfoDoms(route));
  }

  private List<StopInfoDom> mapRouteStopsToListOfStopInfoDoms(Route route) {
    return getSubrouteWithMostStops(route).getStops().stream()
        .map(stopMapper::mapStopToStopInfoDom)
        .collect(toList());
  }

  private Subroute getSubrouteWithMostStops(Route route) {
    return dataStructure.getDataStructureModel().getRouteSubroutes().get(route)
        .stream().max(Comparator.comparing(Subroute::getLength))
        .orElseThrow(NoSuchElementException::new);
  }

  private StopNameDom getFirstRouteStopName(Route route) {
    List<Stop> stops = getSubrouteWithMostStops(route).getStops();
    if (stops.size() == 0) {
      return null;
    }
    return stopNameMapper.createStopNameDom(stops.get(0).getStopArea().getName());
  }

  private StopNameDom getLastRouteStopName(Route route) {
    List<Stop> stops = getSubrouteWithMostStops(route).getStops();
    if (stops.size() == 0) {
      return null;
    }
    return stopNameMapper.createStopNameDom(stops.get(stops.size() - 1).getStopArea().getName());
  }

}
