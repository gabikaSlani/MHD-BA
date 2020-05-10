package com.fmph.diplomovka.service;

import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.repository.RouteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
  public Long getCountOfRoutes() {
    return routeRepository.count();
  }

  @Transactional
  public void saveAll(List<Route> routes) {
    routeRepository.saveAll(routes);
  }

  @Transactional
  public Optional<Route> findById(Long id) {
    return routeRepository.findById(id);
  }
}
