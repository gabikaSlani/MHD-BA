package com.fmph.diplomovka.controllers;


import api.FindPathsApi;
import api.com.fmph.diplomovka.model.CardDom;
import api.com.fmph.diplomovka.model.FindPathParametersDom;
import api.com.fmph.diplomovka.model.InlineResponse400Dom;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.services.PathService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PathsController implements FindPathsApi {

  private final PathService pathService;

  public PathsController(PathService pathService) {
    this.pathService = pathService;
  }

  @Override
  public ResponseEntity<List<CardDom>> findPathsPost(@Valid FindPathParametersDom body) {
    return pathService.getResponseEntity(body);
  }
}
