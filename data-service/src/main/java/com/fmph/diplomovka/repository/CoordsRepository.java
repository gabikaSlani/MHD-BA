package com.fmph.diplomovka.repository;

import com.fmph.diplomovka.model.Coords;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordsRepository extends JpaRepository<Coords, Long> {

  Optional<Coords> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
