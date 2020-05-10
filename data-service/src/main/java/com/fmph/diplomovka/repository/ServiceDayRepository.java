package com.fmph.diplomovka.repository;

import com.fmph.diplomovka.model.ServiceDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceDayRepository extends JpaRepository<ServiceDay, Long> {

  public ServiceDay findByName(String name);

}
