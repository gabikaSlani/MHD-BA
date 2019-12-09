package com.fmph.diplomovka.repository;

import com.fmph.diplomovka.model.Coords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordsRepository extends JpaRepository<Coords, Long> {

    Coords findByLatitudeAndLongitude(String latitude, String longitude);
}