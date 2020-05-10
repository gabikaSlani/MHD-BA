package com.fmph.diplomovka.importdata;

import com.fmph.diplomovka.TimeSimulator;
import com.fmph.diplomovka.importdata.model.DelayRow;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.raptor.DataStructure;
import com.fmph.diplomovka.raptor.dataStructure.models.Subroute;
import com.fmph.diplomovka.raptor.dataStructure.models.SubrouteTrip;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import com.fmph.diplomovka.services.ServiceDayService;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RealTimeDataImport {

  private static final Integer MIN_DELAY = -100;

  private boolean firstRun = true;

  private final DataStructure dataStructure;
  private final ImportHelper importHelper;
  private final ServiceDayService serviceDayService;
  private final TimeSimulator timeSimulator;

  private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  private final SimpleDateFormat YEAR_MONTH_FORMAT = new SimpleDateFormat("yyyy/MM/");

  @Value("${realtime-data-resources-pattern}")
  private String realTimeDataResourcesPattern;

  @Value("${now-date}")
  private String nowDateString;

  @Value("${now-time}")
  private String nowTimeString;

  public RealTimeDataImport(DataStructure dataStructure, ImportHelper importHelper,
      ServiceDayService serviceDayService, TimeSimulator timeSimulator) {
    this.dataStructure = dataStructure;
    this.importHelper = importHelper;
    this.serviceDayService = serviceDayService;
    this.timeSimulator = timeSimulator;
  }

  @Scheduled(fixedRate = 60000, initialDelay = 1)
  public void loadRealTimeData() {
    Time nowTime = timeSimulator.getActualTime();
    Date nowDate = timeSimulator.getActualDate();
    String resourceFileNameForDay = DATE_FORMAT.format(nowDate) + "-statistics.csv";
    String resourceFilePathForDay =
        realTimeDataResourcesPattern + YEAR_MONTH_FORMAT.format(nowDate) + resourceFileNameForDay;
    Resource file = importHelper.loadResource(resourceFilePathForDay);
    List<DelayRow> delayRows = readDelayRows(file);
    delayRows = filterRows(delayRows, nowTime);
    addDelaysToDataStructure(delayRows);
    firstRun = false;
  }

  private List<DelayRow> filterRows(List<DelayRow> delayRows, Time nowTime) {
    Time nowTimeMinusMinute = new Time(nowTime).addMinutes(-1);
    return delayRows
        .stream()
        .filter(r -> r.getDelay() != null
            && r.getDelay() > MIN_DELAY
//            && r.getDelay() <= 0
            && r.getStopId() != null
            && r.getTime() != null
            && ((firstRun && r.getTime().compareHoursAndMinutes(nowTimeMinusMinute) < 0)
            || r.getTime().equalsHoursAndMinutes(nowTimeMinusMinute)))
        .collect(Collectors.toList());
  }

  private List<DelayRow> readDelayRows(Resource file) {
    List<DelayRow> delayRows = new ArrayList<>();
    String data = importHelper.getDataFromResource(file);
    String[] rows = data.split("\r\n");
    boolean first = true;
    for (String row : rows) {
      if (first) {
        first = false;
        continue;
      }
      String[] splittedRow = row.split(";");
      String dateTime = splittedRow[1];
      String routeName = splittedRow[3];
      String stopId = splittedRow[5];
      String delay = splittedRow[7];
      DelayRow delayRow = createDelayRow(dateTime, routeName, stopId, delay);
      if (delayRow != null) {
        delayRows.add(delayRow);
      }
    }
    return delayRows;
  }

  private DelayRow createDelayRow(String dateTime, String routeName, String stopId, String delay) {
    DelayRow delayRow = new DelayRow();
    LocalDate date = LocalDate.parse(dateTime.substring(0, 10), DATE_FORMATTER);
    delayRow.setDate(date);
    if (dateTime.length() > 11) {
      String timeString = dateTime.substring(11);
      delayRow.setTime(new Time(timeString));
    }
    delayRow.setRouteName(parseRouteName(routeName));
    delayRow.setStopId(parseStopId(stopId));
    delayRow.setDelay((delay != null && !delay.equals("n/a")) ? Integer.valueOf(delay) : null);
    return delayRow;
  }

  private String parseRouteName(String routeName) {
    if (routeName.length() == 3 && routeName.startsWith("4")) {
      return "N" + routeName.substring(1);
    }
    return routeName;
  }

  private Long parseStopId(String stopId) {
    if (stopId == null || stopId.equals("n/a")) {
      return null;
    }
    for (int i = stopId.length() - 1; i >= 0; i--) {
      if (stopId.charAt(i) == '0') {
        return Long.valueOf(stopId.substring(0, i) + "000" + stopId.substring(i));
      }
    }
    return Long.valueOf(stopId);
  }

  private void addDelaysToDataStructure(List<DelayRow> delayRows) {
    for (DelayRow delayRow : delayRows) {
      findTripInDataStructure(delayRow);
    }
  }

  private void findTripInDataStructure(DelayRow delayRow) {
    SubrouteTrip foundTrip = null;
    int stopId = 0;
    for (Entry<Stop, List<String>> stopSubroutes : dataStructure.getDataStructureModel()
        .getStopSubroutes().entrySet()) {
      if (stopSubroutes.getKey().getId().equals(delayRow.getStopId())) {
        foundTrip = findTripInSubroutes(stopSubroutes.getValue(), delayRow);
        if (foundTrip != null) {
          break;
        }
      }
      stopId++;
    }
  }

  private SubrouteTrip findTripInSubroutes(List<String> subroutesIds, DelayRow delayRow) {
    for (String subrouteId : subroutesIds) {
      Subroute subroute = dataStructure.getDataStructureModel().getSubroutesByIndex()
          .get(subrouteId);
      if (subroute.getRoute().getName().equals(delayRow.getRouteName())) {
        SubrouteTrip foundTrip = findTripInSubrouteTrips(subroute, delayRow);
        if (foundTrip != null) {
          return foundTrip;
        }
      }
    }
    return null;
  }

  private SubrouteTrip findTripInSubrouteTrips(Subroute subroute, DelayRow delayRow) {
    for (Entry<Long, List<SubrouteTrip>> subrouteTrips : subroute
        .getSubrouteTrips().entrySet()) {
      if (serviceDayService.getPossibleServiceDayIds(delayRow.getDate())
          .contains(subrouteTrips.getKey())) {
        int stopIndex = dataStructure.getDataStructureModel()
            .getStopIndexInSubroute()
            .get(subroute.getId()).get(delayRow.getStopId());
        SubrouteTrip foundTrip = findTripAccordingToTimeAndDelay(subrouteTrips.getValue(),
            stopIndex,
            delayRow);
        if (foundTrip != null) {
          return foundTrip;
        }
      }
    }
    return null;
  }

  private SubrouteTrip findTripAccordingToTimeAndDelay(List<SubrouteTrip> subrouteTrips,
      int stopIndex, DelayRow delayRow) {
    for (SubrouteTrip subrouteTrip : subrouteTrips) {
      Time staticTime = subrouteTrip.getStopTimeObjects()[stopIndex]
          .getTime();
      if (subrouteTripAtStopInTimeWithDelay(delayRow.getTime(), staticTime, delayRow.getDelay())) {
        updateDelayInDataStructureForTripFromStop(subrouteTrip, stopIndex, delayRow.getDelay());
        return subrouteTrip;
      }
    }
    return null;
  }

  private void updateDelayInDataStructureForTripFromStop(SubrouteTrip subrouteTrip, int stopIndex,
      int delay) {
    int editedDelay = negativeSetToPositiveAndPossitiveToZero(delay);
    subrouteTrip.setDelay(editedDelay);
    int tripSize = subrouteTrip.getStopTimeObjects().length;
    for (int i = stopIndex; i < tripSize; i++) {
      subrouteTrip.getStopTimeObjects()[i].setDelay(editedDelay);
    }
  }

  private int negativeSetToPositiveAndPossitiveToZero(int delay) {
    return delay < 0 ? delay * (-1) : 0;
  }

  private boolean subrouteTripAtStopInTimeWithDelay(Time searchedTime, Time staticTime, int delay) {
    Time timeMinusDelay = new Time(searchedTime).addMinutes(delay);
    Time timeMinusDelayMinus1 = new Time(searchedTime).addMinutes(delay - 1);
    Time timeMinusDelayPlus1 = new Time(searchedTime).addMinutes(delay + 1);
    return staticTime.equals(timeMinusDelay)
        || staticTime.equals(timeMinusDelayMinus1)
        || staticTime.equals(timeMinusDelayPlus1);
  }
}
