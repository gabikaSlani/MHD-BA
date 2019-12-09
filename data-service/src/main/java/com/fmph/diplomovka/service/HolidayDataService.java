package com.fmph.diplomovka.service;

import com.fmph.diplomovka.model.HolidayType;
import com.fmph.diplomovka.repository.HolidayRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HolidayDataService {

    private final HolidayRepository holidayRepository;

    public HolidayDataService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    public boolean isHolidayWithType(LocalDate date, HolidayType type) {return holidayRepository.isHolidayWithType(date, type);}

}
