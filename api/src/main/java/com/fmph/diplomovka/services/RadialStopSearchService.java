package com.fmph.diplomovka.services;

import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.fmph.diplomovka.model.Coords;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.service.StopDataService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RadialStopSearchService {

  private final StopDataService stopDataService;

  public RadialStopSearchService(StopDataService stopDataService) {
    this.stopDataService = stopDataService;
  }

  public List<Stop> findStopsNearLocation(Coords location) {
    List<Stop> allStops = stopDataService.getAll();
    List<Stop> nearStops = new ArrayList<>();
    List<Double> distances = List.of(0.2, 0.4, 0.6, 0.8, 1.0, 1.5);
    for (Double dist : distances) {
      nearStops = findStopsNearStopInDistance(location, dist, allStops);
      if (nearStops.size() > 0) {
        return nearStops;
      }
    }
    return nearStops;
  }

  public List<Stop> findStopsNearStopInDistance(Coords coordsFrom, Double distance,
      List<Stop> allStops) {
    List<Stop> stopsNearStopWithDistance = new ArrayList<>();
    for (Stop s : allStops) {
      Double distToStop = calculateDistanceToStopFromLocation(s.getCoords(),
          coordsFrom.getLatitude(), coordsFrom.getLongitude());
      if (!coordsAreSame(s.getCoords(), coordsFrom) && distToStop < distance) {
        stopsNearStopWithDistance.add(s);
      }
    }
    return stopsNearStopWithDistance;
  }


  private boolean coordsAreSame(Coords coords1, Coords coords2) {
    return coords1.getLatitude().equals(coords2.getLatitude()) && coords1.getLongitude()
        .equals(coords2.getLongitude());
  }

  private Double calculateDistanceToStopFromLocation(Coords coords, Double latitude,
      Double longitude) {
    return (((acos(sin((latitude * PI / 180)) * sin((coords.getLatitude() * PI / 180))
        + cos((latitude * PI / 180))
        * cos((coords.getLatitude() * PI / 180)) * cos(
        ((longitude - coords.getLongitude()) * PI / 180)))) * 180 / PI)
        * 60 * 1.1515 * 1.609344);
  }
}
