package com.fmph.diplomovka.service;

import com.fmph.diplomovka.model.ServiceDay;
import com.fmph.diplomovka.repository.ServiceDayRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceDayDataService {

  private final ServiceDayRepository serviceDayRepository;

  public ServiceDayDataService(ServiceDayRepository serviceDayRepository) {
    this.serviceDayRepository = serviceDayRepository;
  }

  @Transactional
  public void save(ServiceDay serviceDay) {
    serviceDayRepository.save(serviceDay);
  }

  @Transactional
  public void saveAll(List<ServiceDay> serviceDays) {
    serviceDayRepository.saveAll(serviceDays);
  }

  @Transactional
  public ServiceDay findByName(String name) {
    return serviceDayRepository.findByName(name);
  }

  @Transactional
  public List<ServiceDay> findAll() {
    return serviceDayRepository.findAll();
  }
}
