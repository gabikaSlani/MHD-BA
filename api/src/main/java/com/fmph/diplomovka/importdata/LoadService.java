package com.fmph.diplomovka.importdata;

import com.fmph.diplomovka.model.CalendarDate;
import com.fmph.diplomovka.model.Coords;
import com.fmph.diplomovka.model.FootPath;
import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.ServiceDay;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.model.StopArea;
import com.fmph.diplomovka.model.Trip;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoadService {

  private final ImportHelper importHelper;

  private List<Stop> stops = new ArrayList<>();
  private List<StopArea> stopAreas = new ArrayList<>();
  private List<FootPath> footPaths = new ArrayList<>();
  private List<Route> routes = new ArrayList<>();
  private List<CalendarDate> calendarDates = new ArrayList<>();
  private List<ServiceDay> serviceDays = new ArrayList<>();
  private List<Trip> trips = new ArrayList<>();

  @Value("${data-resources-pattern}")
  private String CLASSPATH;

  public LoadService(ImportHelper importHelper) {
    this.importHelper = importHelper;
  }

  public List<Trip> getAllTrips() {
    return trips;
  }

  public List<FootPath> getAllFootPaths() {
    return footPaths;
  }

  public List<CalendarDate> getCalendarDates() {
    return calendarDates;
  }

  public List<Stop> getAllStops() {
    return stops;
  }

  public void importFiles() {
    importStops();
    importFootPaths();
    importRoutes();
    importCalendarAndDates();
    importTrips();
    importStopTimes();
  }

  private void importStops() {
    String data = importHelper.getDataFromResource(CLASSPATH + "stops.txt");
    String[] rows = data.split("\r\n");
    boolean first = true;
    long i = 0L;
    for (String row : rows) {
      if (first) {
        first = false;
      } else {
        List<String> values = importHelper.split(row);
        StopArea stopArea = importHelper.returnAndAddStopAreaIfNotExists(stopAreas, values.get(1));
        Stop stop = new Stop(importHelper.parseIdToLong(values.get(0)), stopArea,
            importHelper.parseZoneToInt(values.get(4)));
        Coords coords = new Coords(i, importHelper.parseCoordToDouble(values.get(2)),
            importHelper.parseCoordToDouble(values.get(3)), stop);
        i++;
        stop.setCoords(coords);
        stopArea.getStops().add(stop);
        stops.add(stop);
      }
    }
  }

  private void importFootPaths() {
    importHelper.importFootPaths(CLASSPATH, footPaths, stops);
  }

  private void importRoutes() {
    importHelper.importRoutes(CLASSPATH, routes);
  }

  private void importCalendarAndDates() {
    importCalendar();
    importCalendarDates();
  }

  private void importCalendar() {
    importHelper.importCalendar(CLASSPATH, serviceDays, calendarDates);
  }

  private void importCalendarDates() {
    importHelper.importCalendarDates(CLASSPATH, calendarDates, serviceDays);
  }

  private void importTrips() {
    importHelper.importTrips(CLASSPATH, serviceDays, trips, routes);
  }

  private void importStopTimes() {
    importHelper.importStopTimes(CLASSPATH, stops, trips);
  }
}
