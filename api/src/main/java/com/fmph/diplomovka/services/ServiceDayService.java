package com.fmph.diplomovka.services;

import com.fmph.diplomovka.model.CalendarDate;
import com.fmph.diplomovka.model.ServiceDay;
import com.fmph.diplomovka.raptor.DataStructure;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ServiceDayService {

  private final DataStructure dataStructure;

  public ServiceDayService(DataStructure dataStructure) {
    this.dataStructure = dataStructure;
  }

  public Set<ServiceDay> getPossibleServiceDays(LocalDate dateFrom) {
    return findCalendarDateByDate(dateFrom).getServiceDays();
  }

  public Set<Long> getPossibleServiceDayIds(LocalDate dateFrom) {
    return findCalendarDateByDate(dateFrom).getServiceDays()
        .stream().map(sd -> sd.getId()).collect(Collectors.toSet());
  }

  private CalendarDate findCalendarDateByDate(LocalDate dateFrom) {
    Optional<CalendarDate> calendarDate = dataStructure.getDataStructureModel().getCalendarDates()
        .stream()
        .filter(cd -> cd.getDate().equals(dateFrom)).findFirst();
    if (calendarDate.isEmpty()) {
      throw new RuntimeException("Nie je povolený range dátumu");
    }
    return calendarDate.get();
  }
}
