package com.fmph.diplomovka.services.mappers;

import api.com.fmph.diplomovka.model.RouteInfoDom;
import com.fmph.diplomovka.model.Mode;
import com.fmph.diplomovka.model.Route;
import org.springframework.stereotype.Component;

@Component
public class RouteInfoMapper {


    public RouteInfoMapper() {
    }

    public RouteInfoDom createRouteInfoDom(Route route) {
        return new RouteInfoDom()
                .mode(mapModeToModeEnum(route.getMode()))
                .name(route.getName());
    }

    private RouteInfoDom.ModeEnum mapModeToModeEnum(Mode mode) {
        switch (mode) {
            case BUS:
            case TRAMWAY:
            case TROLLEYBUS:
                return RouteInfoDom.ModeEnum.valueOf(mode.name());
            default:
                throw new IllegalArgumentException("Unknown mode: " + mode);
        }
    }
}
