package com.fmph.diplomovka.raptor.dataStructure;

import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.ServiceDay;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.model.StopTime;
import com.fmph.diplomovka.model.Trip;
import com.fmph.diplomovka.raptor.dataStructure.models.Subroute;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import com.fmph.diplomovka.raptor.dataStructure.sorters.SortTrips;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CsvWriter {

  public void createShedulter(DataStructureModel dataStructureModel, String pathName) {
    CSVWriter writer = null;
    try {
      System.out.println("Start creating csv file");
      writer = new CSVWriter(new FileWriter(pathName));
      List<String[]> stringArray = createStringArray(dataStructureModel);
      for (String[] array : stringArray) {
        writer.writeNext(array);
      }
      writer.close();
      System.out.println("File created");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private List<String[]> createStringArray(DataStructureModel dataStructureModel) {
    List<String[]> result = new ArrayList<>();
    for (Route route : dataStructureModel.getRouteSubroutes().keySet()) {
      //opakujeme 2 krat pre obidva smery
      for (int i = 0; i < 2; i++) {
        Boolean direction = (i == 0);
        List<String> firstLine = new ArrayList<>();
        firstLine.add(route.getId().toString());
        List<Stop> stops = getLongestStopSequenceForRouteWithDirection(route, direction,
            dataStructureModel);
        if (stops != null) {
          //mapa kde kluc je zastavka a hodnota bude pole, ktore obsahuje pre kazdy service day pole casov
          LinkedHashMap<Stop, List<String[]>> mapIndexedByStops = initializeMap(stops);
          Map<ServiceDay, Map<Boolean, List<Trip>>> tripsIndexedByServiceDay = tripsAndServiceDaysForRoute(
              route);
          int serviceDayIndex = 0;
          Map<Integer, ServiceDay> serviceDayMap = new HashMap<>();
          for (ServiceDay serviceDay : tripsIndexedByServiceDay.keySet()) {
            serviceDayMap.put(serviceDayIndex, serviceDay);
            firstLine.add(serviceDay.getName());
            int countOfTrips = tripsIndexedByServiceDay.get(serviceDay).get(direction).size();
            for (int j = 0; j < countOfTrips - 1; j++) {
              firstLine.add("");
            }
            addToEveryListNewArrayFilledWithDash(mapIndexedByStops, countOfTrips);
            int tripIndex = 0;
            for (Trip trip : tripsIndexedByServiceDay.get(serviceDay).get(direction)) {
              for (StopTime stopTime : trip.getStopTimes()) {
                Stop stop = stopTime.getStop();
                if (!mapIndexedByStops.containsKey(stop)) {
                  addNewStopToMap(mapIndexedByStops, stop, serviceDayIndex, serviceDayMap,
                      direction, tripsIndexedByServiceDay);
                }
                String reducedTime = new Time(stopTime.getTime()).toShortString();
                mapIndexedByStops.get(stop).get(serviceDayIndex)[tripIndex] = reducedTime;
              }
              tripIndex++;
            }

            serviceDayIndex++;
          }
          //create List<String[]> and add to result with empty row
          result.add(firstLine.toArray(String[]::new));
          for (Stop stop : mapIndexedByStops.keySet()) {
            List<String> rowArrays = new ArrayList<>();
            rowArrays.add(stop.getId().toString());
            for (String[] array : mapIndexedByStops.get(stop)) {
              Collections.addAll(rowArrays, array);
            }
            String[] finalRow = rowArrays.toArray(new String[0]);
            result.add(finalRow);
          }
          result.add(new String[]{""});
        }
      }
      result.add(new String[]{""});
    }
    return result;
  }

  private Map<ServiceDay, Map<Boolean, List<Trip>>> tripsAndServiceDaysForRoute(Route route) {
    Map<ServiceDay, Map<Boolean, List<Trip>>> result = new HashMap<>();
    for (Trip trip : route.getTrips()) {
      ServiceDay serviceDay = trip.getServiceDay();
      Boolean direction = trip.getDirection();
      if (result.containsKey(serviceDay)) {
        if (result.get(serviceDay).containsKey(direction)) {
          result.get(serviceDay).get(direction).add(trip);
        } else {
          List<Trip> trips = new ArrayList<>();
          trips.add(trip);
          result.get(serviceDay).put(direction, trips);
        }
      } else {
        Map<Boolean, List<Trip>> map = new HashMap<>();
        List<Trip> list = new ArrayList<>();
        list.add(trip);
        map.put(direction, list);
        result.put(serviceDay, map);
      }
    }
    sortTrips(result);
    return result;
  }

  private void sortTrips(Map<ServiceDay, Map<Boolean, List<Trip>>> map) {
    for (Map.Entry<ServiceDay, Map<Boolean, List<Trip>>> entry1 : map.entrySet()) {
      for (Map.Entry<Boolean, List<Trip>> entry2 : entry1.getValue().entrySet()) {
        List<Trip> trips = entry2.getValue();
        trips.sort(new SortTrips());
      }
    }
  }

  private List<Stop> getLongestStopSequenceForRouteWithDirection(Route route, boolean direction,
      DataStructureModel dataStructureModel) {
    List<Subroute> subroutesWithDirection = dataStructureModel.getRouteSubroutes().get(route)
        .stream().filter(s -> s.isDirection() == direction).collect(Collectors.toList());
    if (subroutesWithDirection.size() < 1) {
      return null;
    }
    Subroute longestSubroute = subroutesWithDirection.stream()
        .max(Comparator.comparing(Subroute::getLength)).get();
    return longestSubroute.getStops();
  }


  private LinkedHashMap<Stop, List<String[]>> initializeMap(List<Stop> stops) {
    LinkedHashMap<Stop, List<String[]>> mapIndexedByStops = new LinkedHashMap<>();
    for (Stop stop : stops) {
      List<String[]> row = new ArrayList<>();
      mapIndexedByStops.put(stop, row);
    }
    return mapIndexedByStops;
  }

  private void addToEveryListNewArrayFilledWithDash(Map<Stop, List<String[]>> mapIndexedByStops,
      int countOfTrips) {
    for (List<String[]> arrays : mapIndexedByStops.values()) {
      String[] row = new String[countOfTrips];
      Arrays.fill(row, "-");
      arrays.add(row);
    }
  }

  private void addNewStopToMap(Map<Stop, List<String[]>> mapIndexedByStops, Stop stop,
      int serviceDayIndex, Map<Integer, ServiceDay> serviceDayMap, boolean direction,
      Map<ServiceDay, Map<Boolean, List<Trip>>> tripsIndexedByServiceDay) {
    List<String[]> list = new ArrayList<>();
    for (int i = 0; i <= serviceDayIndex; i++) {
      ServiceDay serviceDay = serviceDayMap.get(i);
      int countOfTrips = tripsIndexedByServiceDay.get(serviceDay).get(direction).size();
      String[] row = new String[countOfTrips];
      Arrays.fill(row, "-");
      list.add(row);
    }
    mapIndexedByStops.put(stop, list);
  }

  private String cutString(String time) {
    if (time.length() == 7) {
      return time.substring(0, 4);
    } else {
      return time.substring(0, 5);
    }
  }
}
