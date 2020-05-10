package com.fmph.diplomovka.services.mappers;

import static com.fmph.diplomovka.services.models.SearchParams.searchParamsBuilder;

import api.com.fmph.diplomovka.model.FindPathParametersDom;
import api.com.fmph.diplomovka.model.StartPointDom;
import com.fmph.diplomovka.distanceGoogleApi.DistanceGoogleApi;
import com.fmph.diplomovka.model.Coords;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.model.StopArea;
import com.fmph.diplomovka.service.StopAreaDataService;
import com.fmph.diplomovka.services.RadialStopSearchService;
import com.fmph.diplomovka.services.models.SearchParams;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PathParamMapper {

  private final CoordsMapper coordsMapper;
  private final StopAreaDataService stopAreaDataService;
  private final RadialStopSearchService radialStopSearchService;
  private final DateTimeMapper dateTimeMapper;

  public PathParamMapper(CoordsMapper coordsMapper, StopAreaDataService stopAreaDataService,
      RadialStopSearchService radialStopSearchService, DateTimeMapper dateTimeMapper) {
    this.coordsMapper = coordsMapper;
    this.stopAreaDataService = stopAreaDataService;
    this.radialStopSearchService = radialStopSearchService;
    this.dateTimeMapper = dateTimeMapper;
  }

  public SearchParams createSearchParams(FindPathParametersDom findPathParametersDom)
      throws Exception {
    Map<Stop, Integer> startStops = getPossibleStartStops(findPathParametersDom.getFrom());
    Set<Stop> endStops = getPossibleStopsForStopArea(findPathParametersDom.getTo());
    return fillSearchParams(startStops, endStops, findPathParametersDom);
  }

  private SearchParams fillSearchParams(Map<Stop, Integer> startStops, Set<Stop> endStops,
      FindPathParametersDom findPathParametersDom) {
    return searchParamsBuilder()
        .withStartStops(startStops)
        .withEndStops(endStops)
        .withTimeFrom(dateTimeMapper.mapDateTmeObjectDomToTime(findPathParametersDom.getTimeFrom()))
        .withDateFrom(
            dateTimeMapper.mapDateTimeObjectDomToLocalDate(findPathParametersDom.getTimeFrom()))
        .withMaxTimeOfWalking(findPathParametersDom.getMaxTimeOfWalking())
        .withMinTimeForTransfer(findPathParametersDom.getMinTimeForTransfer())
        .withMaxNumberOfTransfers(findPathParametersDom.getMaxNumberOfTransfers())
        .withOnlyLowFloor(findPathParametersDom.getOnlyLowFloor())
        .withActualLocation(findPathParametersDom.getFrom().getStopAreaId() == null)
        .build();
  }

  private Map<Stop, Integer> getPossibleStartStops(StartPointDom startPointDom) throws Exception {
    Integer stopAreaId = startPointDom.getStopAreaId();
    if (stopAreaId != null) {
      return getPossibleStopsWithZeroDistanceForStopArea(stopAreaId);
    } else {
      return findNearbyStopsToActualLocation(coordsMapper
          .getDoubleCoordsFromCoordsDom(startPointDom.getCoords()));
    }
  }

  private Map<Stop, Integer> findNearbyStopsToActualLocation(Coords location) throws Exception {
    List<Stop> nearByStops = radialStopSearchService.findStopsNearLocation(location);
    DistanceGoogleApi distanceGoogleApi = new DistanceGoogleApi();
    return distanceGoogleApi.createDistanceFromCoordsToStops(location, nearByStops);
  }

  private StopArea getStopArea(int id) {
    Optional<StopArea> stopArea = getStopAreaByIdFromDatabase(id);
    if (stopArea.isEmpty()) {
      throw new RuntimeException("StopArea with id= " + id + " does not exist.");
    }
    return stopArea.get();
  }

  private Optional<StopArea> getStopAreaByIdFromDatabase(int id) {
    return stopAreaDataService.findById((long) id);
  }

  private Map<Stop, Integer> getPossibleStopsWithZeroDistanceForStopArea(int stopAreaId) {
    return getStopArea(stopAreaId).getStops()
        .stream().collect(Collectors.toMap(stop -> stop, stop -> 0));
  }

  private Set<Stop> getPossibleStopsForStopArea(int stopAreaId) {
    return getStopArea(stopAreaId).getStops();
  }
}
