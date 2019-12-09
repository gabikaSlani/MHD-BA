package com.fmph.diplomovka.repository;

import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByRoute(Route route);
}
