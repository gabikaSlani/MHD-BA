package com.fmph.diplomovka.services.mappers;

import api.com.fmph.diplomovka.model.ActionDom;
import api.com.fmph.diplomovka.model.CardDom;
import api.com.fmph.diplomovka.model.TripSegmentDom;
import api.com.fmph.diplomovka.model.TripStopDom;
import com.fmph.diplomovka.model.StopTime;
import com.fmph.diplomovka.services.models.Action;
import com.fmph.diplomovka.services.models.ActionType;
import com.fmph.diplomovka.services.models.Path;
import com.fmph.diplomovka.services.models.TripSegment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

    @Component
    public class PathMapper {

    private final StopMapper stopMapper;

    public PathMapper(StopMapper stopMapper) {
        this.stopMapper = stopMapper;
    }

    public CardDom createCardDomFromPath(Path path) {
        return new CardDom()
                .startStop(stopMapper.createStopNameDom(path.getStartStop().getName()))
                .endStop(stopMapper.createStopNameDom(path.getEndStop().getName()))
                .distance(path.getDistance())
                .duration(path.getDuration())
                .date(path.getDate())
                .actions(crateListOfActionDom(path.getActions()));
    }

    private List<ActionDom> crateListOfActionDom(List<Action> actions) {
        return actions.stream()
                .map(this::createActionDomFromAction).collect(Collectors.toList());
    }

    private ActionDom createActionDomFromAction(Action action) {
        if(action.getActionType().equals(ActionType.WALKING)){
            return new ActionDom()
                    .type(ActionDom.TypeEnum.WALKING)
                    .walkingTime(action.getWalkingTime());
        }
        else{
            return new ActionDom()
                    .type(ActionDom.TypeEnum.TRIP)
                    .trip(crateTripSegmentDomFromTripSegment(action.getTripSegment()));
        }
    }

    private TripSegmentDom crateTripSegmentDomFromTripSegment(TripSegment tripSegment) {
        return new TripSegmentDom()
                .delay(tripSegment.getDelay())
                .finalStop(stopMapper.createStopNameDom(tripSegment.getFinalStop().getName()))
                .tripStops(crateListOfTripStopDom(tripSegment.getStops()));
    }

    private List<TripStopDom> crateListOfTripStopDom(List<StopTime> stopTimes) {
        return stopTimes.stream()
                .map(this::crateTripStopDom).collect(Collectors.toList());
    }

    private TripStopDom crateTripStopDom(StopTime stopTime) {
        return new TripStopDom()
                .name(stopMapper.createStopNameDom(stopTime.getStop().getName()))
                .onRequest(stopTime.getStop().getOnRequest())
                .zone(stopTime.getStop().getZone())
                .departureTime(stopTime.getTime());
    }


}
