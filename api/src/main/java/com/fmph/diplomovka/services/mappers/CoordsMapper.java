package com.fmph.diplomovka.services.mappers;

import api.com.fmph.diplomovka.model.CoordsDom;
import com.fmph.diplomovka.model.Coords;
import com.fmph.diplomovka.service.CoordsDataService;
import org.springframework.stereotype.Component;

@Component
public class CoordsMapper {

    private CoordsDataService coordsDataService;

    public CoordsMapper(CoordsDataService coordsDataService) {
        this.coordsDataService = coordsDataService;
    }

    public CoordsDom createCoordsDom(Coords coords) {
        if(coords == null){
            return new CoordsDom()
                    .lat("48.416152")
                    .lng("17.378281");
        }
        return new CoordsDom()
                .lat(coords.getLatitude())
                .lng(coords.getLongitude());
    }

    public Coords getCoords(CoordsDom coordsDom) {
        return coordsDataService.getCoordsByLatitudeAndLongitude(coordsDom.getLat(), coordsDom.getLng());
    }
}
