package com.fmph.diplomovka.services.mappers;

import api.com.fmph.diplomovka.model.FindPathParametersDom;
import api.com.fmph.diplomovka.model.StartPointDom;
import com.fmph.diplomovka.model.Coords;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.service.StopDataService;
import com.fmph.diplomovka.services.models.SearchParams;
import org.springframework.stereotype.Component;

import static com.fmph.diplomovka.services.models.SearchParams.searchParamsBuilder;

@Component
public class PathParamMapper {

    private final CoordsMapper coordsMapper;
    private final StopDataService stopDataService;

    public PathParamMapper(CoordsMapper coordsMapper, StopDataService stopDataService) {
        this.coordsMapper = coordsMapper;
        this.stopDataService = stopDataService;
    }

    public SearchParams createSearchParams(FindPathParametersDom findPathParametersDom) {
        Stop startStop = getStartStop(findPathParametersDom.getFrom());
        Stop toStop = stopDataService.getById(findPathParametersDom.getTo())
         .orElseThrow(() -> new IllegalStateException("Current stop not exists."));
        return searchParamsBuilder()
                .withFromStop(startStop)
                .withToStop(toStop)
                .withTime(findPathParametersDom.getTime().longValue())
                .withMaxTimeOfWalking(findPathParametersDom.getMaxTimeOfWalking())
                .withMinTimeForTransfer(findPathParametersDom.getMinTimeForTransfer())
                .withMaxNumberOfTransfers(findPathParametersDom.getMaxNumberOfTransfers())
                .withNumber(findPathParametersDom.getNumber())
                .withOnlyLowFloor(findPathParametersDom.getOnlyLowFloor())
                .build();
    }

    private Stop getStartStop(StartPointDom startPointDom) {
        if(startPointDom.getStopId() != null) {
            return stopDataService.getById(startPointDom.getStopId())
                    .orElseThrow(() -> new IllegalStateException("Current stop not exists."));
        }
        else{
            Coords actualLocation = coordsMapper.getCoords(startPointDom.getCoords());
            //find stop in area and crate list of searchParams
            //until having coords it sets only 1. stop in database
            return stopDataService.getById(1)
                    .orElseThrow(() -> new IllegalStateException("Current stop not exists."));
        }

    }

}
