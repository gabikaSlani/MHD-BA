package com.fmph.diplomovka.repository;

import com.fmph.diplomovka.model.Route;
import com.fmph.diplomovka.model.Stop;
import com.fmph.diplomovka.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {

    @Query("select distinct st.stop from Trip as t join t.stopTimes as st where t.route=:route")
    List<Stop> getAllWithRoute(@Param("route") Route route);

    @Query("select st.stop from Trip as t join t.stopTimes as st where t=:trip order by st.time")
    List<Stop> getStopsForTrip(@Param("trip")Trip trip);

    @Query("select count(distinct st.stop) from Trip as t join t.stopTimes as st where t.route=:route")
    Long countOfStopsForRoute(@Param("route") Route route);

}
