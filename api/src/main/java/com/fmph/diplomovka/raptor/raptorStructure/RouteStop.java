package com.fmph.diplomovka.raptor.raptorStructure;

import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.Stop;

public class RouteStop {

    private Route route;
    private Stop stop;

    public RouteStop(Route route, Stop stop) {
        this.route = route;
        this.stop = stop;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }
}
