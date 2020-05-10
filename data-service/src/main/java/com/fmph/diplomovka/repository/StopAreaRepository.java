package com.fmph.diplomovka.repository;

import com.fmph.diplomovka.model.StopArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopAreaRepository extends JpaRepository<StopArea, Long> {


}
