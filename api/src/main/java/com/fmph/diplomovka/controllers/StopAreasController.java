package com.fmph.diplomovka.controllers;

import api.GetAllStopAreasApi;
import api.com.fmph.diplomovka.model.StopAreaDom;
import com.fmph.diplomovka.services.StopAreaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class StopAreasController implements GetAllStopAreasApi {

  private final StopAreaService stopAreaService;

  public StopAreasController(StopAreaService stopAreaService) {
    this.stopAreaService = stopAreaService;
  }

  @Override
  public ResponseEntity<List<StopAreaDom>> getAllStopAreasGet() {
    return ResponseEntity.ok(stopAreaService.getAllStopAreas());
  }
}
