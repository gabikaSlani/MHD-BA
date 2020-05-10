package com.fmph.diplomovka.controllers;

import api.GetRouteDetailApi;
import api.com.fmph.diplomovka.model.RouteDetailDom;
import com.fmph.diplomovka.services.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/api")
public class RouteDetailController implements GetRouteDetailApi {

  private final RouteService routeService;

  public RouteDetailController(RouteService routeService) {
    this.routeService = routeService;
  }

  @Override
  public ResponseEntity<RouteDetailDom> getRouteDetailGet(Integer routeId) {
    return ResponseEntity.ok(routeService.getRouteDetailDomForRoute(routeId));
  }
}
