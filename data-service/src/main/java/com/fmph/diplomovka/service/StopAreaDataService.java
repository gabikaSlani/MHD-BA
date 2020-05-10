package com.fmph.diplomovka.service;

import com.fmph.diplomovka.model.StopArea;
import com.fmph.diplomovka.repository.StopAreaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StopAreaDataService {

  private final StopAreaRepository stopAreaRepository;

  public StopAreaDataService(StopAreaRepository stopAreaRepository) {
    this.stopAreaRepository = stopAreaRepository;
  }

  @Transactional
  public void saveAll(List<StopArea> stopAreas) {
    stopAreaRepository.saveAll(stopAreas);
  }

  @Transactional
  public List<StopArea> findAll() {
    return stopAreaRepository.findAll();
  }

  @Transactional
  public Optional<StopArea> findById(Long id) {
    return stopAreaRepository.findById(id);
  }
}
