package com.fmph.diplomovka.service;

import com.fmph.diplomovka.model.FootPath;
import com.fmph.diplomovka.repository.FootPathRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FootPathDataService {

  private final FootPathRepository footPathRepository;

  public FootPathDataService(FootPathRepository footPathRepository) {
    this.footPathRepository = footPathRepository;
  }

  @Transactional
  public List<FootPath> getAll() {
    return footPathRepository.findAll();
  }

  @Transactional
  public void saveAll(List<FootPath> footPaths) {
    footPathRepository.saveAll(footPaths);
  }
}
