package com.fmph.diplomovka.services;

import api.com.fmph.diplomovka.model.StopDom;
import com.fmph.diplomovka.service.StopDataService;
import org.springframework.stereotype.Service;
import com.fmph.diplomovka.services.mappers.StopMapper;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class StopService {

    private final StopDataService stopDataService;
    private final StopMapper stopMapper;


    public StopService(StopDataService stopDataService, StopMapper stopMapper) {
        this.stopDataService = stopDataService;
        this.stopMapper = stopMapper;
    }

    public List<StopDom> getAllStops(){
        return stopDataService.getAll().stream()
                .map(stopMapper::mapStopToStopDom)
                .collect(toList());
    }
}
