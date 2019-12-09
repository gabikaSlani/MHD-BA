package com.fmph.diplomovka.services.mappers;

import api.com.fmph.diplomovka.model.RouteDom;
import api.com.fmph.diplomovka.model.StopDom;
import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.service.StopDataService;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class RouteMapper {

    private RouteInfoMapper routeInfoMapper;
    private StopMapper stopMapper;
    private StopDataService stopDataService;

    public RouteMapper(RouteInfoMapper routeInfoMapper, StopMapper stopMapper, StopDataService stopDataService) {
        this.routeInfoMapper = routeInfoMapper;
        this.stopMapper = stopMapper;
        this.stopDataService = stopDataService;
    }

    public RouteDom mapRouteToRouteDom(Route route) {
        return new RouteDom()
                .id(route.getId().intValue())
                .routeInfo(routeInfoMapper.createRouteInfoDom(route))
                .stops(createListOfStopDomsForRoute(route));
    }

    private List<StopDom> createListOfStopDomsForRoute(Route route) {
        return stopDataService.getAllWithRoute(route).stream()
                .map(s -> stopMapper.mapStopToStopDom(s))
                .collect(toList());
    }
}
