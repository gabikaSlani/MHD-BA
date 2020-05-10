package com.fmph.diplomovka.service;

import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.repository.StopRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StopDataService {

  private final StopRepository stopRepository;

  public StopDataService(StopRepository stopRepository) {
    this.stopRepository = stopRepository;
  }

  @Transactional
  public List<Stop> getAll() {
    return stopRepository.findAll();
  }

  @Transactional
  public Optional<Stop> getById(Long id) {
    return stopRepository.findById(id);
  }

  @Transactional
  public void save(Stop stop) {
    stopRepository.save(stop);
  }

  @Transactional
  public void saveAll(List<Stop> stops) {
    stopRepository.saveAll(stops);
  }


}
