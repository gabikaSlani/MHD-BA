package com.fmph.diplomovka.services;

import api.com.fmph.diplomovka.model.DateTimeObjectDom;
import com.fmph.diplomovka.TimeSimulator;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class ActualDateTimeService {

  private final TimeSimulator timeSimulator;

  public ActualDateTimeService(TimeSimulator timeSimulator) {
    this.timeSimulator = timeSimulator;
  }

  public DateTimeObjectDom getActualDateTimeDom() {
    LocalDate date = timeSimulator.getActualLocalDate();
    Time time = timeSimulator.getActualTime();
    return new DateTimeObjectDom()
        .year(date.getYear())
        .month(date.getMonthValue())
        .day(date.getDayOfMonth())
        .hour(time.getHours())
        .minute(time.getMinutes())
        .second(time.getSeconds());
  }
}
