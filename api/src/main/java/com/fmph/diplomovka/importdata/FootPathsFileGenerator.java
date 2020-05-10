package com.fmph.diplomovka.importdata;

import com.fmph.diplomovka.distanceGoogleApi.DistanceGoogleApi;
import com.fmph.diplomovka.model.FootPath;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.services.RadialStopSearchService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FootPathsFileGenerator {

  private final RadialStopSearchService radialStopSearchService;
  private DistanceGoogleApi distanceMatrixApi;
  private final Integer MATRIX_SIZE = 10;

  public FootPathsFileGenerator(RadialStopSearchService radialStopSearchService) {
    distanceMatrixApi = new DistanceGoogleApi();
    this.radialStopSearchService = radialStopSearchService;
  }

  public void generateFootPaths(String filePath, List<Stop> stops) throws IOException {
    List<FootPath> footPathList = createFootpaths(stops);
    saveFootPathsToResourceFile(filePath, footPathList);
  }

  private List<FootPath> createFootpaths(List<Stop> stops) {
    List<FootPath> footPathList = new ArrayList<>();
    for (Stop stop : stops) {
      try {
        List<Stop> nearbyStops = radialStopSearchService
            .findStopsNearStopInDistance(stop.getCoords(), 0.8, stops);
        Map<Stop, Integer> distancesToNearbyStops = distanceMatrixApi
            .createDistanceFromCoordsToStops(stop.getCoords(), nearbyStops);
        distancesToNearbyStops
            .forEach((key, value) -> footPathList.add(new FootPath(stop, key, value)));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return footPathList;
  }

  private void saveFootPathsToResourceFile(String filePath, List<FootPath> footPaths)
      throws IOException {
    PrintWriter writer = new PrintWriter(filePath, StandardCharsets.UTF_8);
    StringBuilder line = new StringBuilder();
    for (FootPath footPath : footPaths) {
      line.setLength(0);
      line.append(footPath.getFromStop().getId());
      line.append(",");
      line.append(footPath.getToStop().getId());
      line.append(",");
      line.append(footPath.getTime());
      writer.println(line.toString());
    }
    writer.close();
  }

}
