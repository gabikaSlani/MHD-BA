package com.fmph.diplomovka.services.mappers;

import api.com.fmph.diplomovka.model.StopNameDom;
import org.springframework.stereotype.Component;

@Component
public class StopNameMapper {


  public StopNameDom createStopNameDom(String name) {
    return new StopNameDom().name(name);
  }

}
