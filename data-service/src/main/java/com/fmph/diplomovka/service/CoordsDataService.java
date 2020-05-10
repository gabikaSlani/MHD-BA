package com.fmph.diplomovka.service;

import com.fmph.diplomovka.model.Coords;
import com.fmph.diplomovka.repository.CoordsRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoordsDataService {

  private final CoordsRepository coordsRepository;

  public CoordsDataService(CoordsRepository coordsRepository) {
    this.coordsRepository = coordsRepository;
  }

  @Transactional
  public Optional<Coords> getCoordsByLatitudeAndLongitude(Double latitude, Double longitude) {
    return coordsRepository.findByLatitudeAndLongitude(latitude, longitude);
  }

  @Transactional
  public void save(Coords coords) {
    coordsRepository.save(coords);
  }

  @Transactional
  public List<Coords> getAll() {
    return coordsRepository.findAll();
  }
}
