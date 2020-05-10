package com.fmph.diplomovka.importdata;

import com.fmph.diplomovka.model.CalendarDate;
import com.fmph.diplomovka.model.ServiceDay;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {

  public CalendarService() {
  }

  public void addAllFittingDatesInRangeWithServiceDay(ServiceDay serviceDay, String rangeFrom,
      String rangeTo, List<Boolean> days, List<CalendarDate> calendarDates) {
    LocalDate from = parseStringToLocalDate(rangeFrom);
    LocalDate to = parseStringToLocalDate(rangeTo);
    Set<DayOfWeek> daysOfWeek = fittingDaysOfWeek(days);
    for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {
      if (daysOfWeek.contains(date.getDayOfWeek())) {
        addDateToList(date, serviceDay, calendarDates);
      }
    }
  }

  public void manageServiceDayException(String serviceDayName, String dateString,
      List<CalendarDate> calendarDates, boolean adding, List<ServiceDay> serviceDays) {
    ServiceDay serviceDay = findServiceDayByName(serviceDayName, serviceDays);
    LocalDate date = parseStringToLocalDate(dateString);
    Optional<CalendarDate> calendarDate = getDateFromCalendarDates(date, calendarDates);
    if (calendarDate.isPresent()) {
      if (adding) {
        addServiceDayToSet(calendarDate.get(), serviceDay);
      } else {
        removeServiceDayFromSet(calendarDate.get(), serviceDay);
      }
    }
  }


  public ServiceDay findServiceDayByName(String serviceDayName, List<ServiceDay> serviceDays) {
    Optional<ServiceDay> serviceDay = serviceDays.stream()
        .filter(sd -> sd.getName().equals(serviceDayName)).findAny();
    if (serviceDay.isEmpty()) {
      throw new RuntimeException(
          "ServiceDay with name= " + serviceDayName + " does not exists in field");
    }
    return serviceDay.get();
  }

  private LocalDate parseStringToLocalDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    return LocalDate.parse(date, formatter);
  }

  private Set<DayOfWeek> fittingDaysOfWeek(List<Boolean> days) {
    DayOfWeek[] week = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
    Set<DayOfWeek> fittingDays = new HashSet<>();
    for (int i = 0; i < days.size(); i++) {
      if (days.get(i)) {
        fittingDays.add(week[i]);
      }
    }
    return fittingDays;
  }

  private Optional<CalendarDate> getDateFromCalendarDates(LocalDate date,
      List<CalendarDate> calendarDates) {
    return calendarDates.stream().filter(cd -> cd.getDate().equals(date)).findAny();
  }

  private void addDateToList(LocalDate date, ServiceDay serviceDay,
      List<CalendarDate> calendarDates) {
    Optional<CalendarDate> calendarDate = getDateFromCalendarDates(date, calendarDates);
    if (calendarDate.isPresent()) {
      addServiceDayToSet(calendarDate.get(), serviceDay);
    } else {
      addNewCalendarDateToList(date, serviceDay, calendarDates);
    }
  }

  private void addServiceDayToSet(CalendarDate calendarDate, ServiceDay serviceDay) {
    calendarDate.getServiceDays().add(serviceDay);
  }

  private void removeServiceDayFromSet(CalendarDate calendarDate, ServiceDay serviceDay) {
    calendarDate.getServiceDays().remove(serviceDay);
  }

  private void addNewCalendarDateToList(LocalDate date, ServiceDay serviceDay,
      List<CalendarDate> calendarDates) {
    CalendarDate calendarDate = new CalendarDate((long) calendarDates.size(), date);
    calendarDates.add(calendarDate);
    addServiceDayToSet(calendarDate, serviceDay);
  }

}
