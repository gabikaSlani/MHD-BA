package com.fmph.diplomovka.services.mappers;

import api.com.fmph.diplomovka.model.RouteInfoDom;
import api.com.fmph.diplomovka.model.StopDom;
import api.com.fmph.diplomovka.model.StopNameDom;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.service.RouteDataService;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class StopMapper {

    private final RouteDataService routeDataService;
    private final RouteInfoMapper routeInfoMapper;
    private final CoordsMapper coordsMapper;

    public StopMapper(RouteDataService routeDataService, RouteInfoMapper routeInfoMapper, CoordsMapper coordsMapper) {
        this.routeDataService = routeDataService;
        this.routeInfoMapper = routeInfoMapper;
        this.coordsMapper = coordsMapper;
    }


    public StopDom mapStopToStopDom(Stop stop) {
        return new StopDom()
                .id(stop.getId().intValue())
                .name(createStopNameDom(stop.getName()))
                .coords(coordsMapper.createCoordsDom(stop.getCoords()))
                .onRequest(stop.getOnRequest())
                .zone(stop.getZone())
                .routes(createListOfRoutesInfoDom(stop));
    }

    public StopNameDom createStopNameDom(String name) {
        return new StopNameDom().name(name);
    }


    private List<RouteInfoDom> createListOfRoutesInfoDom(Stop stop) {
        return routeDataService.getAllWithStop(stop).stream()
                .map(routeInfoMapper::createRouteInfoDom)
                .collect(toList());
    }
}
