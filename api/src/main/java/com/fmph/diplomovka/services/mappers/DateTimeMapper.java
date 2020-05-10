package com.fmph.diplomovka.services.mappers;

import api.com.fmph.diplomovka.model.DateTimeObjectDom;
import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class DateTimeMapper {

  public LocalDate mapDateTimeObjectDomToLocalDate(DateTimeObjectDom dateTimeObjectDom) {
    return LocalDate
        .of(dateTimeObjectDom.getYear(), dateTimeObjectDom.getMonth(), dateTimeObjectDom.getDay());
  }

  public Time mapDateTmeObjectDomToTime(DateTimeObjectDom dateTimeObjectDom) {
    return new Time(dateTimeObjectDom.getHour(), dateTimeObjectDom.getMinute(), 0);
  }
}
