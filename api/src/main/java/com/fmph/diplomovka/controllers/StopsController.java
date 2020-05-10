package com.fmph.diplomovka.controllers;

import api.GetAllStopsApi;
import api.com.fmph.diplomovka.model.StopDom;
import com.fmph.diplomovka.services.StopService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class StopsController implements GetAllStopsApi {

  private final StopService stopService;

  public StopsController(StopService stopService) {
    this.stopService = stopService;
  }

  @Override
  public ResponseEntity<List<StopDom>> getAllStopsGet() {
    return ResponseEntity.ok(stopService.getAllStops());
  }
}
