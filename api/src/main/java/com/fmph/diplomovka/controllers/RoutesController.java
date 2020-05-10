package com.fmph.diplomovka.controllers;

import api.GetAllRoutesApi;
import api.com.fmph.diplomovka.model.RouteDom;
import com.fmph.diplomovka.services.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class RoutesController implements GetAllRoutesApi {

  private final RouteService routeService;

  public RoutesController(RouteService routeService) {
    this.routeService = routeService;
  }


  @Override
  public ResponseEntity<List<RouteDom>> getAllRoutesGet() {
    return ResponseEntity.ok(routeService.getAllRoutes());
  }
}
