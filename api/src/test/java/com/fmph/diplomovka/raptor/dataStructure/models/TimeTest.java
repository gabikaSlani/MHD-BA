package com.fmph.diplomovka.raptor.dataStructure.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeTest {

    @Test
    public void shouldReturnMaxTime() {
        Time time = new Time().getMaxTime();
        Time maxTime = new Time(23,59,59);
        assertEquals(maxTime, time);
    }

    @Test
    public void shouldRoundTimeToMinutes() {
        Time time = new Time(12,50,10);
        Time expectedTime = new Time(12, 51, 0);
        assertEquals(expectedTime, time.getRoundTime());
    }

    @Test
    public void shouldConvertToString() {
        Time time = new Time(12,50,10);
        assertEquals("12:50:10", time.toString());
    }

    @Test
    public void shouldFormatCorrectlyToString() {
        Time time = new Time(0,2,0);
        assertEquals("00:02:00", time.toString());
    }

    @Test
    public void shouldCorrectlyAddHour() {
        Time time = new Time(12,50,0);
        time.plusMinutes(23);
        Time expectedTime = new Time(13,13,0);
        assertEquals(expectedTime, time);
    }

    @Test
    public void shouldReturnNewDay() {
        Time time = new Time(23,59,12);
        time.plusSeconds(53);
        Time expectedTime = new Time(0,0,5, true);
        assertEquals(expectedTime, time);
    }

    @Test
    public void shouldCompareCorrectlyTwoTimes(){
        Time time = new Time(12,12,11);
        Time timeAfter = new Time(12,12,12);
        assertTrue(time.isBefore(timeAfter));
    }

    @Test
    public void shouldCompareCorrectlyTwoTimes1(){
        Time time = new Time(12,12,12);
        Time timeAfter = new Time(12,12,12);
        assertFalse(time.isBefore(timeAfter));
    }

    @Test
    public void shouldCompareCorrectlyTwoTimes2(){
        Time time = new Time(13,12,12);
        Time timeAfter = new Time(12,12,12);
        assertFalse(time.isBefore(timeAfter));
    }

    @Test
    public void shouldCompareCorrectlyTwoTimes3(){
        Time time = new Time(12,10,12);
        Time timeAfter = new Time(12,12,12);
        assertTrue(time.isBefore(timeAfter));
    }

    @Test
    public void shouldCompareCorrectlyTwoTimes4(){
        Time time = new Time(12,13,12);
        Time timeAfter = new Time(12,12,12);
        assertFalse(time.isBefore(timeAfter));
    }

    @Test
    public void shouldCompareCorrectlyTwoTimes5(){
        Time time = new Time(12,12,12);
        Time timeAfter = new Time(12,12,12);
        assertFalse(time.isBefore(timeAfter));
    }

    @Test
    public void shouldCalculateMinutesBetweenTwoTimes(){
        Time timeFrom = new Time(8, 45, 0);
        Time timeTo = new Time(9,5,0);
        assertEquals(20, timeFrom.minutesBetween(timeTo));
    }

    @Test
    public void shouldCorrectlySubtractTwoTimes(){
        Time time1 = new Time(8, 0, 0);
        Time time2 = new Time(10, 0, 0);
        assertEquals(-7200, time1.minus(time2));
    }

    @Test
    public void shouldCompareTwoTimesInDifferentDays(){
        Time time = new Time(11,12,12, false);
        Time timeAfter = new Time(12,12,12, true);
        assertTrue(time.isBefore(timeAfter));
    }

    @Test
    public void shouldCompareTwoTimesInDifferentDays1(){
        Time time = new Time(11,12,12, true);
        Time timeAfter = new Time(12,12,12, false);
        assertFalse(time.isBefore(timeAfter));
    }

    @Test
    public void shouldCompareTwoTimesInDifferentDays2(){
        Time time = new Time(11,12,12, true);
        Time timeAfter = new Time(12,12,12, true);
        assertTrue(time.isBefore(timeAfter));
    }

    @Test
    public void shouldCompareTwoTimesInDifferentDays3(){
        Time time = new Time(11,12,12, true);
        Time timeAfter = new Time(11,12,12, true);
        assertFalse(time.isBefore(timeAfter));
    }

    @Test
    public void shouldCorrectlyAddMinutesAtTheEndOfDay(){
        Time time = new Time(23,59,00, false);
        Time timeAfter = new Time(0,0,0, true);
        time.plusMinutes(1);
        assertEquals(timeAfter, time);
    }

    @Test
    public void shouldCorrectlyAddNegativeMinutes(){
        Time time = new Time(23,59,0, false);
        Time timeBefore = new Time(23,54,0, false);
        time.plusMinutes(-5);
        assertEquals(timeBefore, time);
    }

    @Test
    public void shouldCorrectlyAddNegativeMinutesAtTheBegiiningOfTheDay(){
        Time time = new Time(0,2,5, false, false);
        Time timeBefore = new Time(23,57,5, false, true);
        time.plusMinutes(-5);
        assertEquals(timeBefore, time);
    }
}
