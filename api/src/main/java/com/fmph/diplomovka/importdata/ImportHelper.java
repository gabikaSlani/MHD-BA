package com.fmph.diplomovka.importdata;

import com.fmph.diplomovka.model.CalendarDate;
import com.fmph.diplomovka.model.FootPath;
import com.fmph.diplomovka.model.Mode;
import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.ServiceDay;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.model.StopArea;
import com.fmph.diplomovka.model.StopTime;
import com.fmph.diplomovka.model.Trip;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Service
public class ImportHelper {

  private final ResourceLoader resourceLoader;
  private final CalendarService calendarService;

  public ImportHelper(ResourceLoader resourceLoader, CalendarService calendarService) {
    this.resourceLoader = resourceLoader;
    this.calendarService = calendarService;
  }

  public String getDataFromResource(Resource resource) {
    InputStream inputStream = null;
    try {
      inputStream = resource.getInputStream();
      byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
      return new String(bdata, StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String getDataFromResource(String classPath) {
    Resource resource = loadResource(classPath);
    return getDataFromResource(resource);
  }

  public Resource loadResource(String classpath) {
    return resourceLoader.getResource(classpath);
  }

  public List<Resource> getFilesFromResources(String pattern) {
    try {
      return Arrays.asList(
          ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(pattern));
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }


  public List<String> split(String s) {
    List<String> result = new ArrayList<>();
    String value = "";
    boolean isQuoted = false;
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      if (ch == ',' && !isQuoted) {
        result.add(value);
        value = "";
      } else if (ch == '\"') {
        isQuoted = !isQuoted;
      } else {
        value += ch;
      }
    }
    result.add(value);
    return result;
  }

  public Long parseIdToLong(String id) {
    return Long.parseLong(id.trim());
  }

  public Integer parseZoneToInt(String stringZone) {
    return Integer.parseInt(stringZone);
  }

  public Double parseCoordToDouble(String coord) {
    return Double.parseDouble(coord);
  }

  public Long parseTripId(String id) {
    return parseIdToLong(removeUnderlineSign(id));
  }

  private String removeUnderlineSign(String tripId) {
    return tripId.replaceAll("[_/]", "");
  }

  public StopArea returnAndAddStopAreaIfNotExists(List<StopArea> stopAreas, String name) {
    Optional<StopArea> stopAreaOpt = getStopAreaIfExists(stopAreas, name);
    if (stopAreaOpt.isEmpty()) {
      StopArea stopArea = new StopArea((long) stopAreas.size(), name);
      stopAreas.add(stopArea);
      return stopArea;
    }
    return stopAreaOpt.get();
  }


  public Mode mapNumberToMode(String number) {
    switch (number) {
      case "0":
        return Mode.TRAMWAY;
      case "3":
        return Mode.BUS;
      case "11":
        return Mode.TROLLEYBUS;
      default:
        return Mode.BUS;
    }
  }

  private Optional<StopArea> getStopAreaIfExists(List<StopArea> stopAreas, String name) {
    return stopAreas.stream().filter(sa -> sa.getName().equals(name)).findAny();
  }


  public Optional<Route> findRouteById(Long id, List<Route> routes) {
    return routes.stream().filter(r -> r.getId().equals(id)).findFirst();
  }

  public boolean randomLowFloor() {
    return new Random().nextBoolean();
  }

  public Trip findTripById(Long id, List<Trip> trips) {
    Optional<Trip> trip = trips.stream().filter(t -> t.getId().equals(id)).findFirst();
    if (trip.isEmpty()) {
      throw new RuntimeException("Trip with id= " + id + " does not exists in field");
    }
    return trip.get();
  }

  public Stop findStopById(Long id, List<Stop> stops) {
    Optional<Stop> stop = stops.stream().filter(s -> s.getId().equals(id)).findFirst();
    if (stop.isEmpty()) {
      throw new RuntimeException("Stop with id= " + id + " does not exists in field");
    }
    return stop.get();
  }

  public List<Boolean> createListOfDays(String[] values) {
    List<Boolean> days = new ArrayList<>();
    for (int i = 1; i < 8; i++) {
      days.add(values[i].equals("1"));
    }
    return days;
  }

  public void importCalendarDates(String classPath, List<CalendarDate> calendarDates,
      List<ServiceDay> serviceDays) {
    String data = getDataFromResource(classPath + "calendar_dates.txt");
    String[] rows = data.split("\r\n");
    boolean first = true;
    for (String row : rows) {
      if (first) {
        first = false;
      } else {
        String[] values = row.split(",");
        boolean addingException = values[2].equals("1");
        calendarService
            .manageServiceDayException(values[0], values[1], calendarDates, addingException,
                serviceDays);
      }
    }
  }

  public void importTrips(String classPath, List<ServiceDay> serviceDays, List<Trip> trips,
      List<Route> routes) {
    String data = getDataFromResource(classPath + "trips.txt");
    String[] rows = data.split("\r\n");
    boolean first = true;
    for (String row : rows) {
      if (first) {
        first = false;
      } else {
        List<String> values = split(row);
        Long id = parseTripId(values.get(2));
        Boolean direction = values.get(5).equals("0");
        ServiceDay serviceDay = calendarService.findServiceDayByName(values.get(1), serviceDays);
        Route route = findRouteById(parseIdToLong(values.get(0)), routes).get();
        Trip trip = new Trip(id, serviceDay, randomLowFloor(), direction, route);
        serviceDay.getTrips().add(trip);
        route.getTrips().add(trip);
        trips.add(trip);
      }
    }
  }

  public void importStopTimes(String classPath, List<Stop> stops, List<Trip> trips) {
    String data = getDataFromResource(classPath + "stop_times.txt");
    String[] rows = data.split("\r\n");
    boolean first = true;
    long i = 0L;
    for (String row : rows) {
      if (i % 10000 == 0) {
        System.out.println(i / 7040 + "%");
      }
      if (first) {
        first = false;
      } else {
        String[] values = row.split(",");
        Stop stop = findStopById(parseIdToLong(values[3]), stops);
        Trip trip = findTripById(parseTripId(values[0]), trips);
        stop.setOnRequest(values[6].equals("3"));
        StopTime stopTime = new StopTime(
            stop,
            trip,
            values[1],
            Integer.parseInt(values[4])
        );
        trip.getStopTimes().add(stopTime);
      }
      i++;
    }
  }

  public void importCalendar(String classPath, List<ServiceDay> serviceDays,
      List<CalendarDate> calendarDates) {
    String data = getDataFromResource(classPath + "calendar.txt");
    String[] rows = data.split("\r\n");
    boolean first = true;
    long index = 0L;
    for (String row : rows) {
      if (first) {
        first = false;
      } else {
        String[] values = row.split(",");
        List<Boolean> days = createListOfDays(values);
        ServiceDay serviceDay = new ServiceDay(index, values[0]);
        calendarService
            .addAllFittingDatesInRangeWithServiceDay(serviceDay, values[8], values[9], days,
                calendarDates);
        index++;
        serviceDays.add(serviceDay);
      }
    }
  }

  public void importRoutes(String classPath, List<Route> routes) {
    String data = getDataFromResource(classPath + "routes.txt");
    String[] rows = data.split("\r\n");
    boolean first = true;
    for (String row : rows) {
      if (first) {
        first = false;
      } else {
        String[] values = row.split(",");
        Route route = new Route(parseIdToLong(values[0]), values[2], mapNumberToMode(values[4]));
        routes.add(route);
      }
    }
  }

  public void importFootPaths(String classPath, List<FootPath> footPaths, List<Stop> stops) {
    String data = getDataFromResource(classPath + "foot_paths.txt");
    String[] rows = data.split("\r\n");
    long i = 0L;
    for (String row : rows) {
      String[] values = row.split(",");
      Stop stop1 = findStopById(parseIdToLong(values[0]), stops);
      Stop stop2 = findStopById(parseIdToLong(values[1]), stops);
      Integer minutes = Integer.parseInt(values[2]);
      footPaths.add(new FootPath(i, stop1, stop2, minutes));
      i++;
    }
  }
}
