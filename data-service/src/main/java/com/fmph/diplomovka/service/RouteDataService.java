package com.fmph.diplomovka.service;

import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.repository.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RouteDataService {

    private final RouteRepository routeRepository;

    public RouteDataService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Transactional
    public List<Route> getAll() {
        return routeRepository.findAll();
    }

    public List<Route> getAllWithStop(Stop stop) {
        return routeRepository.getAllRoutesWithStop(stop);
    }

    @Transactional
    public Long getCountOfRoutes() {
        return routeRepository.count();
    }
}
