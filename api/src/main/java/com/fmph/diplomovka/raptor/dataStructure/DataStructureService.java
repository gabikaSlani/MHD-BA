package com.fmph.diplomovka.raptor.dataStructure;

import com.fmph.diplomovka.importdata.LoadService;
import com.fmph.diplomovka.model.CalendarDate;
import com.fmph.diplomovka.model.FootPath;
import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.model.Trip;
import com.fmph.diplomovka.raptor.dataStructure.models.Subroute;
import com.fmph.diplomovka.raptor.dataStructure.models.Transfer;
import com.fmph.diplomovka.service.CalendarDateDataService;
import com.fmph.diplomovka.service.FootPathDataService;
import com.fmph.diplomovka.service.StopAreaDataService;
import com.fmph.diplomovka.service.TripDataService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;


@Service
public class DataStructureService {

  private final FootPathDataService footPathDataService;
  private final TripDataService tripDataService;
  private final CalendarDateDataService calendarDateDataService;
  private final LoadService loadService;
  private final StopAreaDataService stopAreaDataService;
  private final DataStructureFiller dataStructureFiller;

  public DataStructureService(FootPathDataService footPathDataService,
      TripDataService tripDataService, CalendarDateDataService calendarDateDataService,
      LoadService loadService, StopAreaDataService stopAreaDataService,
      DataStructureFiller dataStructureFiller) {
    this.footPathDataService = footPathDataService;
    this.tripDataService = tripDataService;
    this.calendarDateDataService = calendarDateDataService;
    this.loadService = loadService;
    this.stopAreaDataService = stopAreaDataService;
    this.dataStructureFiller = dataStructureFiller;
  }

  public DataStructureModel createDataStructure() {
    Map<Route, List<Subroute>> routeSubroutes = new HashMap<>();
    Map<Long, List<Transfer>> stopTransfers = new HashMap<>();
    Map<Stop, List<String>> stopSubroutes = new HashMap<>();
    Map<String, Map<Long, Integer>> stopIndexInSubroute = new HashMap<>();
    Map<String, Subroute> subroutesByIndex = new HashMap<>();
    importDataFromGTFS();
    List<Trip> allTrips = loadService.getAllTrips();
    List<FootPath> allFootPaths = loadService.getAllFootPaths();
    List<Stop> allStops = loadService.getAllStops();
    List<CalendarDate> allCalendarDates = loadService.getCalendarDates();
    dataStructureFiller.reduceCalendarDates(allCalendarDates);
    dataStructureFiller.fillDataStructure(stopTransfers, routeSubroutes, stopSubroutes,
        stopIndexInSubroute, allTrips, allFootPaths, allStops, subroutesByIndex);
    return new DataStructureModel(routeSubroutes, stopTransfers, stopSubroutes,
        stopIndexInSubroute, allCalendarDates, subroutesByIndex);
  }

  private void importDataFromGTFS() {
    System.out.println("Loading GTFS data started.");
    long startTime = System.nanoTime();
    loadService.importFiles();
    long stopTime = System.nanoTime();
    System.out.println("Loading data finished in " + (stopTime - startTime) + " seconds");
  }
}
