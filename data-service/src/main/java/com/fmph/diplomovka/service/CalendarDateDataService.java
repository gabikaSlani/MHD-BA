package com.fmph.diplomovka.service;

import com.fmph.diplomovka.model.CalendarDate;
import com.fmph.diplomovka.repository.CalendarDateRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalendarDateDataService {

  private final CalendarDateRepository calendarDateRepository;

  public CalendarDateDataService(CalendarDateRepository calendarDateRepository) {
    this.calendarDateRepository = calendarDateRepository;
  }

  @Transactional
  public void saveAll(List<CalendarDate> calendarDates) {
    calendarDateRepository.saveAll(calendarDates);
  }

  @Transactional
  public List<CalendarDate> getAll() {
    return calendarDateRepository.findAll();
  }

  @Transactional
  public Optional<CalendarDate> findByDate(LocalDate date) {
    return calendarDateRepository.findCalendarDateByDate(date);
  }

}
