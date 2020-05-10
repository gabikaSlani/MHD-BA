package com.fmph.diplomovka.controllers;

import api.GetActualDateTimeApi;
import api.com.fmph.diplomovka.model.DateTimeObjectDom;
import com.fmph.diplomovka.services.ActualDateTimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ActualDateTimeController implements GetActualDateTimeApi {

  private final ActualDateTimeService actualDateTimeService;

  public ActualDateTimeController(
      ActualDateTimeService actualDateTimeService) {
    this.actualDateTimeService = actualDateTimeService;
  }

  @Override
  public ResponseEntity<DateTimeObjectDom> getActualDateTimeGet() {
    return ResponseEntity.ok(actualDateTimeService.getActualDateTimeDom());
  }
}
