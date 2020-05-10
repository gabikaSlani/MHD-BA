package com.fmph.diplomovka.services.mappers;

import api.com.fmph.diplomovka.model.ActionDom;
import api.com.fmph.diplomovka.model.CardDom;
import api.com.fmph.diplomovka.model.StopNameDom;
import api.com.fmph.diplomovka.model.TripSegmentDom;
import api.com.fmph.diplomovka.model.TripStopDom;
import com.fmph.diplomovka.raptor.dataStructure.models.Action;
import com.fmph.diplomovka.raptor.dataStructure.models.ActionType;
import com.fmph.diplomovka.raptor.dataStructure.models.StopTimeObject;
import com.fmph.diplomovka.raptor.dataStructure.models.TripSegment;
import com.fmph.diplomovka.services.models.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PathMapper {

  private final StopNameMapper stopNameMapper;
  private final RouteInfoMapper routeInfoMapper;

  public PathMapper(StopNameMapper stopNameMapper, RouteInfoMapper routeInfoMapper) {
    this.stopNameMapper = stopNameMapper;
    this.routeInfoMapper = routeInfoMapper;
  }

  public List<CardDom> mapPathsToCardDoms(List<Path> paths) {
    return paths.stream().map(this::createCardDomFromPath).collect(Collectors.toList());
  }

  private CardDom createCardDomFromPath(Path path) {
    return new CardDom()
        .startStop(stopNameMapper.createStopNameDom(path.getStartStop().getStopArea().getName()))
        .endStop(stopNameMapper.createStopNameDom(path.getEndStop().getStopArea().getName()))
        .duration(path.getDuration())
        .date(convertDateToString(path.getDate()))
        .arrivalTime(path.getArrivalTime().toShortString())
        .departureTime(path.getDepartureTime().toShortString())
        .actions(crateListOfActionDom(path.getActions()));
  }

  private List<ActionDom> crateListOfActionDom(List<Action> actions) {
    return actions.stream()
        .map(this::createActionDomFromAction).collect(Collectors.toList());
  }

  private ActionDom createActionDomFromAction(Action action) {
    StopNameDom startStop = action.getStartStop() == null ?
        null
        : stopNameMapper.createStopNameDom(action.getStartStop().getStopArea().getName());
    StopNameDom endStop = stopNameMapper
        .createStopNameDom(action.getEndStop().getStopArea().getName());
    if (action.getActionType().equals(ActionType.WALKING)) {
      return new ActionDom()
          .startStop(startStop)
          .endStop(endStop)
          .type(ActionDom.TypeEnum.WALKING)
          .walkingTime(action.getWalkingTime());
    } else {
      return new ActionDom()
          .startStop(startStop)
          .endStop(endStop)
          .type(ActionDom.TypeEnum.TRIP)
          .trip(crateTripSegmentDomFromTripSegment(action.getTripSegment()));
    }
  }

  private TripSegmentDom crateTripSegmentDomFromTripSegment(TripSegment tripSegment) {
    return new TripSegmentDom()
        .delay(tripSegment.getSubrouteTrip().getDelay())
        .lowFloor(tripSegment.getSubrouteTrip().isLowFloor())
        .routeInfo(routeInfoMapper.createRouteInfoDom(tripSegment.getSubrouteTrip().getRoute()))
        .finalStop(stopNameMapper.createStopNameDom(tripSegment.getFinalStopAreaName()))
        .tripStops(crateListOfTripStopDom(tripSegment.getStops()))
        .boardingTime(tripSegment.getBoardingTime().toShortString())
        .getOffTime(tripSegment.getGetOfFTime().toShortString())
        .leftTheOriginStop(tripSegment.hasLeftTheOriginStop());
  }

  private List<TripStopDom> crateListOfTripStopDom(List<StopTimeObject> stopTimes) {
    return stopTimes.stream()
        .map(this::crateTripStopDom).collect(Collectors.toList());
  }

  private TripStopDom crateTripStopDom(StopTimeObject stopTime) {
    return new TripStopDom()
        .name(stopNameMapper.createStopNameDom(stopTime.getStop().getStopArea().getName()))
        .onRequest(stopTime.getStop().isOnRequest())
        .zone(stopTime.getStop().getZone())
        .departureTime(stopTime.getTime().toShortString());
  }

  private String convertDateToString(LocalDate date) {
    return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
  }

}
