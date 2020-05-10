package com.fmph.diplomovka.repository;

import com.fmph.diplomovka.model.CalendarDate;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarDateRepository extends JpaRepository<CalendarDate, Long> {

  Optional<CalendarDate> findCalendarDateByDate(LocalDate date);

}
