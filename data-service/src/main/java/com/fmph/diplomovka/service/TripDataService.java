package com.fmph.diplomovka.service;

import com.fmph.diplomovka.model.Trip;
import com.fmph.diplomovka.repository.TripRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TripDataService {

  private final TripRepository tripRepository;

  public TripDataService(TripRepository tripRepository) {
    this.tripRepository = tripRepository;
  }

  @Transactional
  public void saveAll(List<Trip> trips) {
    tripRepository.saveAll(trips);
  }

  @Transactional
  public void save(Trip trip) {
    tripRepository.save(trip);
  }

  @Transactional
  public List<Trip> getAll() {
    return tripRepository.findAll();
  }
}
