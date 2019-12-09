package com.fmph.diplomovka.service;

import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.model.Trip;
import com.fmph.diplomovka.repository.StopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StopDataService {

    private final StopRepository stopRepository;

    public StopDataService(StopRepository stopRepository) {
        this.stopRepository = stopRepository;
    }

    @Transactional
    public List<Stop> getAll(){
        return stopRepository.findAll();
    }

    @Transactional
    public List<Stop> getAllWithRoute(Route route) {return stopRepository.getAllWithRoute(route); }

    @Transactional
    public Optional<Stop> getById(Integer id) {return stopRepository.findById(id.longValue());}

    @Transactional
    public Long getCountOfStopsForRoute(Route route) {return stopRepository.countOfStopsForRoute(route); }

    @Transactional
    public List<Stop> getStopsForTrip(Trip trip) {return stopRepository.getStopsForTrip(trip);}

}
