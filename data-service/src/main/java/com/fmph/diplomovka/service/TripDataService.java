package com.fmph.diplomovka.service;

import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.Trip;
import com.fmph.diplomovka.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripDataService {

    private final TripRepository tripRepository;

    public TripDataService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<Trip> getTripsForRoute(Route route) {
        return tripRepository.findByRoute(route);
    }
}
