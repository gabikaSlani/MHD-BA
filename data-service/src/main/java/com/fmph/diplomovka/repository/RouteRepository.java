package com.fmph.diplomovka.repository;

import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query("select distinct r from Route as r join r.trips as t join t.stopTimes as st where st.stop=:stop")
    List<Route> getAllRoutesWithStop(@Param("stop") Stop stop);

}


