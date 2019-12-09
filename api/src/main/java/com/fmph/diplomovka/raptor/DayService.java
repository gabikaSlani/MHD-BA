package com.fmph.diplomovka.raptor;

import com.fmph.diplomovka.model.DayType;
import com.fmph.diplomovka.model.HolidayType;
import com.fmph.diplomovka.service.HolidayDataService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@Service
public class DayService {

    private final HolidayDataService holidayDataService;

    public DayService(HolidayDataService holidayDataService) {
        this.holidayDataService = holidayDataService;
    }

    public Set<DayType> getDayTypesBelongToDate(LocalDate date) {
        Set<DayType> dayTypes = new HashSet<>();
        dayTypes.add(DayType.DAILY);
        if(isFreeDay(date)){
            dayTypes.add(DayType.FREE_DAY);
        }
        else{
            if(isSchoolHoliday(date)){
                dayTypes.add(DayType.SCHOOL_HOLIDAY);
                dayTypes.add(DayType.WORKING_DAY);
            }
            else{
                dayTypes.add(DayType.SCHOOL_DAY);
                dayTypes.add(DayType.WORKING_DAY);
            }
        }
        return dayTypes;
    }

    private boolean isFreeDay(LocalDate date) {
        return isWeekend(date) || isNationalHoliday(date);
    }

    private boolean isNationalHoliday(LocalDate date) {
        return holidayDataService.isHolidayWithType(date, HolidayType.NATIONAL);
    }

    private boolean isSchoolHoliday(LocalDate date) {
        return holidayDataService.isHolidayWithType(date, HolidayType.SCHOOL);
    }

    private boolean isWeekend(LocalDate date) {
        Set<DayOfWeek> weekend = EnumSet.of( DayOfWeek.SATURDAY , DayOfWeek.SUNDAY );
        return weekend.contains(date.getDayOfWeek());
    }


}
