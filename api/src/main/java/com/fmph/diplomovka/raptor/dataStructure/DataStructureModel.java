package com.fmph.diplomovka.raptor.dataStructure;


import com.fmph.diplomovka.model.CalendarDate;
import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.raptor.dataStructure.models.Subroute;
import com.fmph.diplomovka.raptor.dataStructure.models.Transfer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStructureModel implements Serializable {

  private static final long serialVersionUID = 1L;

  private Map<Route, List<Subroute>> routeSubroutes = new HashMap<>();
  private Map<Long, List<Transfer>> stopTransfers = new HashMap<>();
  private Map<Stop, List<String>> stopSubroutes = new HashMap<>();
  private Map<String, Map<Long, Integer>> stopIndexInSubroute = new HashMap<>();
  private List<CalendarDate> calendarDates = new ArrayList<>();
  private Map<String, Subroute> subroutesByIndex = new HashMap<>();

  public DataStructureModel() {
  }

  public DataStructureModel(Map<Route, List<Subroute>> routeSubroutes,
      Map<Long, List<Transfer>> stopTransfers, Map<Stop, List<String>> stopSubroutes,
      Map<String, Map<Long, Integer>> stopIndexInSubroute, List<CalendarDate> calendarDates,
      Map<String, Subroute> subroutesByIndex) {
    this.routeSubroutes = routeSubroutes;
    this.stopTransfers = stopTransfers;
    this.stopSubroutes = stopSubroutes;
    this.stopIndexInSubroute = stopIndexInSubroute;
    this.calendarDates = calendarDates;
    this.subroutesByIndex = subroutesByIndex;
  }

  public Map<Route, List<Subroute>> getRouteSubroutes() {
    return routeSubroutes;
  }

  public void setRouteSubroutes(Map<Route, List<Subroute>> routeSubroutes) {
    this.routeSubroutes = routeSubroutes;
  }

  public Map<Long, List<Transfer>> getStopTransfers() {
    return stopTransfers;
  }

  public void setStopTransfers(Map<Long, List<Transfer>> stopTransfers) {
    this.stopTransfers = stopTransfers;
  }

  public Map<Stop, List<String>> getStopSubroutes() {
    return stopSubroutes;
  }

  public void setStopSubroutes(Map<Stop, List<String>> stopSubroutes) {
    this.stopSubroutes = stopSubroutes;
  }

  public Map<String, Map<Long, Integer>> getStopIndexInSubroute() {
    return stopIndexInSubroute;
  }

  public void setStopIndexInSubroute(Map<String, Map<Long, Integer>> stopIndexInSubroute) {
    this.stopIndexInSubroute = stopIndexInSubroute;
  }

  public List<CalendarDate> getCalendarDates() {
    return calendarDates;
  }

  public void setCalendarDates(List<CalendarDate> calendarDates) {
    this.calendarDates = calendarDates;
  }

  public Map<String, Subroute> getSubroutesByIndex() {
    return subroutesByIndex;
  }

  public void setSubroutesByIndex(
      Map<String, Subroute> subroutesByIndex) {
    this.subroutesByIndex = subroutesByIndex;
  }
}
