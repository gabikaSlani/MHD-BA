package com.fmph.diplomovka.repository;

import com.fmph.diplomovka.model.Holiday;
import com.fmph.diplomovka.model.HolidayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    @Query("select case when (count(h) > 0) then true else false end " +
            "from Holiday as h where h.date=:date AND h.type=:holidayType")
    boolean isHolidayWithType(@Param("date") LocalDate date, @Param("holidayType") HolidayType holidayType);

}
