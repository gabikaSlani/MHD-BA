package com.fmph.diplomovka.services.mappers;

import api.com.fmph.diplomovka.model.StopDom;
import api.com.fmph.diplomovka.model.StopInfoDom;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.model.StopTime;
import org.springframework.stereotype.Component;


@Component
public class StopMapper {

  private final StopAreaMapper stopAreaMapper;
  private final CoordsMapper coordsMapper;
  private final StopNameMapper stopNameMapper;

  public StopMapper(StopAreaMapper stopAreaMapper, CoordsMapper coordsMapper,
      StopNameMapper stopNameMapper) {
    this.stopAreaMapper = stopAreaMapper;
    this.coordsMapper = coordsMapper;
    this.stopNameMapper = stopNameMapper;
  }


  public StopDom mapStopToStopDom(Stop stop) {
    return new StopDom()
        .id(stop.getId().intValue())
        .name(stopNameMapper.createStopNameDom(stop.getStopArea().getName()))
        .coords(coordsMapper.createCoordsDom(stop.getCoords()))
        .stopArea(stopAreaMapper.mapStopAreaToStopAreaDom(stop.getStopArea()));
  }

  public StopDom mapStopTimeToStopDom(StopTime stopTime) {
    Stop stop = stopTime.getStop();
    return new StopDom()
        .id(stop.getId().intValue())
        .name(stopNameMapper.createStopNameDom(stop.getStopArea().getName()))
        .coords(coordsMapper.createCoordsDom(stop.getCoords()));
  }

  public StopInfoDom mapStopToStopInfoDom(Stop stop) {
    return new StopInfoDom()
        .id(stop.getId().intValue())
        .name(stopNameMapper.createStopNameDom(stop.getStopArea().getName()))
        .onRequest(stop.isOnRequest())
        .zone(stop.getZone())
        .coords(coordsMapper.createCoordsDom(stop.getCoords()));
  }

}
