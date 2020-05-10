package com.fmph.diplomovka.services;

import static java.util.stream.Collectors.toList;

import api.com.fmph.diplomovka.model.RouteDetailDom;
import api.com.fmph.diplomovka.model.RouteDom;
import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.service.RouteDataService;
import com.fmph.diplomovka.services.mappers.RouteMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

  private final RouteDataService routeDataService;
  private final RouteMapper routeMapper;

  public RouteService(RouteDataService routeDataService, RouteMapper routeMapper) {
    this.routeDataService = routeDataService;
    this.routeMapper = routeMapper;
  }

  public List<RouteDom> getAllRoutes() {
    List<Route> routes = routeDataService.getAll();
    return routes.stream()
        .map(routeMapper::mapRouteToRouteDom)
        .collect(toList());
  }

  public RouteDetailDom getRouteDetailDomForRoute(int routeId) {
    Optional<Route> route = routeDataService.findById((long) routeId);
    if (route.isEmpty()) {
      throw new RuntimeException("Route with id= " + routeId + " does not exist.");
    }
    return routeMapper.mapRouteToRouteDetailDom(route.get());
  }
}
