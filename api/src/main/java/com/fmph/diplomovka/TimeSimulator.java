package com.fmph.diplomovka;

import com.fmph.diplomovka.raptor.dataStructure.models.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class TimeSimulator {

  @Value("${now-date}")
  private String nowDateString;

  @Value("${now-time}")
  private String nowTimeString;

  private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  private int daysAfterStart = 0;
  private Time actualTime;
  private Date actualDate;

  public TimeSimulator() {
  }


  @Scheduled(fixedRate = 1000, initialDelay = 0)
  public void next() throws ParseException {
    updateTime();
    updateDate();
//    System.out.println("It is: date = " + DATE_FORMAT.format(actualDate) + ", time = " + actualTime.toString());
  }

  public Time getActualTime() {
    return actualTime;
  }

  public Date getActualDate() {
    return actualDate;
  }

  public LocalDate getActualLocalDate() {
    return actualDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  private void updateTime() {
    if (actualTime == null) {
      actualTime = new Time(nowTimeString);
    } else {
      actualTime.plusSeconds(1);
    }
    if (actualTime.isNextDay()) {
      daysAfterStart++;
      actualTime.setNextDay(false);
    }
  }

  private void updateDate() throws ParseException {
    Date actualDate = DATE_FORMAT.parse(nowDateString);
    if (daysAfterStart > 0) {
      Calendar c = Calendar.getInstance();
      c.setTime(actualDate);
      c.add(Calendar.DATE, daysAfterStart);
      actualDate = c.getTime();
    }
    this.actualDate = actualDate;
  }

}
