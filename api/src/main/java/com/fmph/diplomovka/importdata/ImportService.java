package com.fmph.diplomovka.importdata;

import com.fmph.diplomovka.model.CalendarDate;
import com.fmph.diplomovka.model.Coords;
import com.fmph.diplomovka.model.FootPath;
import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.ServiceDay;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.model.StopArea;
import com.fmph.diplomovka.model.Trip;
import com.fmph.diplomovka.service.CalendarDateDataService;
import com.fmph.diplomovka.service.CoordsDataService;
import com.fmph.diplomovka.service.FootPathDataService;
import com.fmph.diplomovka.service.RouteDataService;
import com.fmph.diplomovka.service.ServiceDayDataService;
import com.fmph.diplomovka.service.StopDataService;
import com.fmph.diplomovka.services.RadialStopSearchService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service

public class ImportService {

  private final ImportHelper importHelper;
  private final StopDataService stopDataService;
  private final CoordsDataService coordsDataService;
  private final RouteDataService routeDataService;
  private final CalendarDateDataService calendarDateDataService;
  private final RadialStopSearchService radialStopSearchService;
  private final FootPathDataService footPathDataService;
  private final FootPathsFileGenerator footPathsFileGenerator;
  private final ServiceDayDataService serviceDayDataService;

  @Value("${data-resources-pattern}")
  private String CLASSPATH;

  public ImportService(ImportHelper importHelper, StopDataService stopDataService,
      CoordsDataService coordsDataService, RouteDataService routeDataService,
      CalendarDateDataService calendarDateDataService,
      RadialStopSearchService radialStopSearchService, FootPathDataService footPathDataService,
      FootPathsFileGenerator footPathsFileGenerator, ServiceDayDataService serviceDayDataService) {
    this.importHelper = importHelper;
    this.stopDataService = stopDataService;
    this.coordsDataService = coordsDataService;
    this.routeDataService = routeDataService;
    this.calendarDateDataService = calendarDateDataService;
    this.radialStopSearchService = radialStopSearchService;
    this.footPathDataService = footPathDataService;
    this.footPathsFileGenerator = footPathsFileGenerator;
    this.serviceDayDataService = serviceDayDataService;
  }

  public ResponseEntity<Void> importGtfsData() {
    long startTime;
    long stopTime;
    System.out.println("Start importing stops");
    startTime = System.nanoTime();
    List<Stop> stops = importStops();
    stopTime = System.nanoTime();
    System.out.println("Stops imported in " + (stopTime - startTime) + " seconds");

    //CALL footPathsFileGenerator.generateFootPaths(FILE_PATH, stops);

    System.out.println("Start importing footPaths");
    startTime = System.nanoTime();
    createFootPaths(stops);
    stopTime = System.nanoTime();
    System.out.println("FootPaths imported in " + (stopTime - startTime) + " seconds");

    System.out.println("Start importing routes");
    startTime = System.nanoTime();
    importRoutes();
    stopTime = System.nanoTime();
    System.out.println("Routes imported in " + (stopTime - startTime) + " seconds");

    System.out.println("Start importing calendarDates");
    startTime = System.nanoTime();
    importCalendarAndDates();
    stopTime = System.nanoTime();
    System.out.println("CalendarDates imported in " + (stopTime - startTime) + " seconds");

    System.out.println("Start importing trips");
    startTime = System.nanoTime();
    List<Trip> trips = importTrips();
    stopTime = System.nanoTime();
    System.out.println("Trips imported in " + (stopTime - startTime) + " seconds");

    System.out.println("Start importing stopTimes");
    startTime = System.nanoTime();
    importStopTimes(trips);
    stopTime = System.nanoTime();
    System.out.println("StopTimes imported in " + (stopTime - startTime) + " seconds");
    System.out.println("Data successfully imported to database");

    return new ResponseEntity<>(HttpStatus.OK);
  }

  private List<Stop> importStops() {
    String data = importHelper.getDataFromResource(CLASSPATH + "stops.txt");
    List<StopArea> stopAreasToSave = new ArrayList<>();
    List<Stop> stops = new ArrayList<>();
    String[] rows = data.split("\r\n");
    long index = 0L;
    boolean first = true;
    for (String row : rows) {
      if (first) {
        first = false;
      } else {
        List<String> values = importHelper.split(row);
        Long stopId = importHelper.parseIdToLong(values.get(0));
        StopArea stopArea = importHelper
            .returnAndAddStopAreaIfNotExists(stopAreasToSave, values.get(1));
        Stop stop = new Stop(stopId, stopArea, importHelper.parseZoneToInt(values.get(4)));
        Coords coords = new Coords(
            index, importHelper.parseCoordToDouble(values.get(2)),
            importHelper.parseCoordToDouble(values.get(3)));
        index++;
        stopDataService.save(stop);
        coords.setStop(stop);
        coordsDataService.save(coords);
        stops.add(stop);
      }
    }
    return stops;
  }

  private void createFootPaths(List<Stop> stops) {
    List<FootPath> footPathsToSave = new ArrayList<>();
    importHelper.importFootPaths(CLASSPATH, footPathsToSave, stops);
    footPathDataService.saveAll(footPathsToSave);
  }

  private void importRoutes() {
    List<Route> routesToSave = new ArrayList<>();
    importHelper.importRoutes(CLASSPATH, routesToSave);
    routeDataService.saveAll(routesToSave);
  }

  private void importCalendarAndDates() {
    List<CalendarDate> calendarDates = new ArrayList<>();
    importCalendar(calendarDates);
    importCalendarDates(calendarDates);
    calendarDateDataService.saveAll(calendarDates);
  }

  private void importCalendar(List<CalendarDate> calendarDates) {
    List<ServiceDay> serviceDays = new ArrayList<>();
    importHelper.importCalendar(CLASSPATH, serviceDays, calendarDates);
    serviceDayDataService.saveAll(serviceDays);
  }

  private void importCalendarDates(List<CalendarDate> calendarDates) {
    importHelper.importCalendarDates(CLASSPATH, calendarDates, getAllServiceDays());
  }

  private List<Trip> importTrips() {
    List<Route> routes = routeDataService.getAll();
    List<Trip> tripsToSave = new ArrayList<>();
    importHelper.importTrips(CLASSPATH, getAllServiceDays(), tripsToSave, routes);
    return tripsToSave;
  }

  private void importStopTimes(List<Trip> trips) {
    List<Stop> stops = stopDataService.getAll();
    importHelper.importStopTimes(CLASSPATH, stops, trips);
    stopDataService.saveAll(stops);
  }

  private List<ServiceDay> getAllServiceDays() {
    return serviceDayDataService.findAll();
  }
}
