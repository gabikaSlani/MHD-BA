package com.fmph.diplomovka.controllers;

import api.ImportApi;
import com.fmph.diplomovka.importdata.ImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ImportController implements ImportApi {

  private final ImportService importService;

  public ImportController(ImportService importService) {
    this.importService = importService;
  }

  @Override
  public ResponseEntity<Void> importGet() {
    return importService.importGtfsData();
  }
}
