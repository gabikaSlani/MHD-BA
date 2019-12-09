package com.fmph.diplomovka.services;

import api.com.fmph.diplomovka.model.RouteDom;
import com.fmph.diplomovka.service.RouteDataService;
import com.fmph.diplomovka.services.mappers.RouteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class RouteService {

    private final RouteDataService routeDataService;
    private final RouteMapper routeMapper;

    public RouteService(RouteDataService routeDataService, RouteMapper routeMapper) {
        this.routeDataService = routeDataService;
        this.routeMapper = routeMapper;
    }

    public List<RouteDom> getAllRoutes() {
        return routeDataService.getAll().stream()
                .map(routeMapper::mapRouteToRouteDom)
                .collect(toList());
    }
}