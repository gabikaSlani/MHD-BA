package com.fmph.diplomovka.services.mappers;

import api.com.fmph.diplomovka.model.CoordsDom;
import com.fmph.diplomovka.model.Coords;
import org.springframework.stereotype.Component;

@Component
public class CoordsMapper {


  public CoordsMapper() {
  }

  public CoordsDom createCoordsDom(Coords coords) {
    return new CoordsDom()
        .lat(coords.getLatitude().toString())
        .lng(coords.getLongitude().toString());
  }

  public Coords getDoubleCoordsFromCoordsDom(CoordsDom coordsDom) {
    return new Coords(parseStringToDouble(coordsDom.getLat()),
        parseStringToDouble(coordsDom.getLng()), null);
  }

  private Double parseStringToDouble(String number) {
    return Double.parseDouble(number);
  }
}
