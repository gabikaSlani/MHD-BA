package com.fmph.diplomovka.service;

import com.fmph.diplomovka.model.Coords;
import com.fmph.diplomovka.repository.CoordsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoordsDataService {

    private final CoordsRepository coordsRepository;

    public CoordsDataService(CoordsRepository coordsRepository) {
        this.coordsRepository = coordsRepository;
    }

    @Transactional
    public Coords getCoordsByLatitudeAndLongitude(String latitude, String longitude) {
        return coordsRepository.findByLatitudeAndLongitude(latitude, longitude);
    }
}
