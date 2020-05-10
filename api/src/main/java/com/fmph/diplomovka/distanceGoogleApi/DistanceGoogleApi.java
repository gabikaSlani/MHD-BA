package com.fmph.diplomovka.distanceGoogleApi;

import com.fmph.diplomovka.distanceGoogleApi.model.DistanceMatrixResponse;
import com.fmph.diplomovka.model.Coords;
import com.fmph.diplomovka.model.FootPath;
import com.fmph.diplomovka.model.Stop;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DistanceGoogleApi {

  private Gson gson;

  public DistanceGoogleApi() {
    gson = new Gson();
  }

  public List<FootPath> createAllPossibleFootpaths(List<Stop> stopList) throws IOException {
    String url = createRequestUrl(null, stopList);
    DistanceMatrixResponse apiResponse = callApi(url);
    return mapApiResponseToFootPathList(apiResponse, stopList);
  }

  public Map<Stop, Integer> createDistanceFromCoordsToStops(Coords from, List<Stop> stopList)
      throws Exception {
    String url = createRequestUrl(from, stopList);
    DistanceMatrixResponse apiResponse = apiResponse = callApi(url);
    return mapApiResponseToDurationMap(apiResponse, stopList);
  }

  private String createRequestUrl(Coords from, List<Stop> stopList) {
    StringBuilder sb = new StringBuilder();
    sb.append("https://maps.googleapis.com/maps/api/distancematrix/json");
    sb.append("?key=AIzaSyDemhrMRfGR5AMzRvrvmpUFIPntpZxj08E");
    sb.append("&units=metric");
    sb.append("&mode=walking");
    sb.append("&origins=");
    StringBuilder sbOrigins = new StringBuilder();
    StringBuilder sbDestinations = new StringBuilder();
    boolean first = true;
    for (Stop stop : stopList) {
      if (!first) {
        if (from == null) {
          sbOrigins.append("|");
        }
        sbDestinations.append("|");
      }
      if (from == null) {
        sbOrigins.append(stop.getCoords().getLatitude().toString());
        sbOrigins.append(",");
        sbOrigins.append(stop.getCoords().getLongitude().toString());
      } else if (first) {
        sbOrigins.append(from.getLatitude().toString());
        sbOrigins.append(",");
        sbOrigins.append(from.getLongitude().toString());
      }
      sbDestinations.append(stop.getCoords().getLatitude().toString());
      sbDestinations.append(",");
      sbDestinations.append(stop.getCoords().getLongitude().toString());
      first = false;
    }
    sb.append(sbOrigins);
    sb.append("&destinations=");
    sb.append(sbDestinations);
    return sb.toString();
  }

  private DistanceMatrixResponse callApi(String urlString) throws IOException {
    System.out.println(urlString);
    URL url = new URL(urlString);
    URLConnection conn = url.openConnection();
    InputStream is = conn.getInputStream();
    String responseString = new BufferedReader(new InputStreamReader(is))
        .lines().collect(Collectors.joining("\n"));
    return gson.fromJson(responseString, DistanceMatrixResponse.class);
  }


  private List<FootPath> mapApiResponseToFootPathList(DistanceMatrixResponse apiResponse,
      List<Stop> stopList) {
    List<FootPath> footPaths = new ArrayList<>();
    for (int i = 0; i < stopList.size(); i++) {
      for (int j = 0; j < stopList.size(); j++) {
        if (i == j) {
          continue;
        }
        FootPath footPath = new FootPath();
        footPath.setFromStop(stopList.get(i));
        footPath.setToStop(stopList.get(j));
        footPath.setTime(parseDuration(
            apiResponse.getRows().get(i).getElements().get(j).getDuration().getText()));
        footPaths.add(footPath);
      }
    }
    return footPaths;
  }

  private Map<Stop, Integer> mapApiResponseToDurationMap(DistanceMatrixResponse apiResponse,
      List<Stop> stopList) throws Exception {
    if ("MAX_ELEMENTS_EXCEEDED".equals(apiResponse.getStatus())) {
      throw new Exception("MAX_ELEMENTS_EXCEEDED");
    }
    if (!apiResponse.getStatus().equals("OK")) {
      System.out.println(apiResponse);
    }
    Map<Stop, Integer> durationMap = new HashMap<>();
    for (int i = 0; i < stopList.size(); i++) {
      Stop stop = stopList.get(i);
      Integer duration = parseDuration(
          apiResponse.getRows().get(0).getElements().get(i).getDuration().getText());
      durationMap.put(stop, duration);
    }
    return durationMap;
  }

  private Integer parseDuration(String durationText) {
    return Integer.parseInt(durationText.split(" ")[0]);
  }

}
