package com.fmph.diplomovka.raptor.dataStructure.models;

import java.io.Serializable;
import java.util.Objects;

public class Time implements Serializable {

  private static final long serialVersionUID = 1L;

  private int hours;
  private int minutes;
  private int seconds;
  private boolean nextDay;
  private boolean prevDay;

  public Time(String timeString) {
    String[] timeArray = timeString.split(":");
    this.hours = Integer.parseInt(timeArray[0]);
    this.minutes = Integer.parseInt(timeArray[1]);
    this.seconds = timeArray.length > 3 ? Integer.parseInt(timeArray[2]) : 0;
    this.prevDay = false;
    convertIfGreaterThanOneDay();
  }

  private void convertIfGreaterThanOneDay() {
    if (this.hours >= 24) {
      this.hours -= 24;
      this.nextDay = true;
    } else {
      this.nextDay = false;
    }
  }

  public Time(int hours, int minutes, int seconds, boolean nextDay, boolean prevDay) {
    this.hours = hours;
    this.minutes = minutes;
    this.seconds = seconds;
    this.nextDay = nextDay;
    this.prevDay = prevDay;
  }


  public Time(int hours, int minutes, int seconds, boolean nextDay) {
    this.hours = hours;
    this.minutes = minutes;
    this.seconds = seconds;
    this.nextDay = nextDay;
  }

  public Time(int hours, int minutes, int seconds) {
    this.hours = hours;
    this.minutes = minutes;
    this.seconds = seconds;
    this.nextDay = false;
  }

  public Time(Time other) {
    this.hours = other.getHours();
    this.minutes = other.getMinutes();
    this.seconds = other.getSeconds();
    this.nextDay = other.isNextDay();
  }

  public Time() {
  }

  public Time getMaxTime() {
    this.hours = 23;
    this.minutes = 59;
    this.seconds = 59;
    this.nextDay = false;
    return this;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(formatNumberOn2Places(this.hours))
        .append(":")
        .append(formatNumberOn2Places(this.minutes))
        .append(":")
        .append(formatNumberOn2Places(this.seconds));
      if (nextDay) {
          sb.append(" (1)");
      }
    return sb.toString();
  }

  public String toShortString() {
    Time rounded = getRoundTime();
    StringBuffer sb = new StringBuffer();
    sb.append(formatNumberOn2Places(rounded.hours))
        .append(":")
        .append(formatNumberOn2Places(rounded.minutes));
      if (nextDay) {
          sb.append(" (1)");
      }
    return sb.toString();
  }

  public Time getRoundTime() {
    if (this.seconds > 0) {
      Time newTime = new Time(this.hours, this.minutes, 0, this.nextDay);
      newTime.plusMinutes(1);
      return newTime;
    }
    return this;
  }

  public void plusMinutes(int count) {
    plusSeconds(count * 60);
  }

  public Time addMinutes(int count) {
    plusSeconds(count * 60);
    return this;
  }

  public void plusSeconds(int count) {
    int sec = convertToSeconds() + count;
    if (sec < 0) {
      prevDay = true;
      sec = (24 * 60 * 60) + sec;

    } else if (sec > 23 * 60 * 60 + 59 * 60 + 59) {
      nextDay = true;
      sec -= (24 * 60 * 60);
    }
    convertSeconds(sec);
  }

  public boolean isBefore(Time other) {
      if (!this.nextDay && other.nextDay) {
          return true;
      } else if (this.nextDay && !other.nextDay) {
          return false;
      }
      if (this.hours < other.hours) {
          return true;
      } else if (this.hours > other.hours) {
          return false;
      }
      if (this.minutes < other.minutes) {
          return true;
      } else if (this.minutes > other.minutes) {
          return false;
      }
    return this.seconds < other.seconds;
  }

  public boolean isAfter(Time other) {
      if (this.nextDay && !other.nextDay) {
          return true;
      } else if (!this.nextDay && other.nextDay) {
          return false;
      }
      if (this.hours > other.hours) {
          return true;
      } else if (this.hours < other.hours) {
          return false;
      }
      if (this.minutes > other.minutes) {
          return true;
      } else if (this.minutes < other.minutes) {
          return false;
      }
    return this.seconds > other.seconds;
  }

  public boolean isBeforeOrEqual(Time other) {
    return this.isBefore(other) || this.equals(other);
  }

  public int minutesBetween(Time timeTo) {
    return convertToMinutes(timeTo) - convertToMinutes(this);
  }

  public int minus(Time other) {
    return this.convertToSeconds() - other.convertToSeconds();
  }

  private int convertToSeconds() {
    int nextDaySeconds = nextDay ? 24 * 60 * 60 : 0;
    return nextDaySeconds + this.hours * 60 * 60 + this.minutes * 60 + this.seconds;
  }

  private int convertToMinutes(Time time) {
    int nextDayMinutes = time.isNextDay() ? 24 * 60 : 0;
    return nextDayMinutes + time.getHours() * 60 + time.getMinutes();
  }

  private void convertSeconds(int sec) {
    hours = sec / (60 * 60);
    int balance = sec % (60 * 60);
    minutes = balance / 60;
    seconds = balance % 60;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    Time time = (Time) o;
    return hours == time.hours &&
        minutes == time.minutes &&
        seconds == time.seconds &&
        nextDay == time.nextDay &&
        prevDay == time.prevDay;
  }

  public boolean equalsHoursAndMinutes(Object o) {
      if (this == o) {
          return false;
      }
    return compareHoursAndMinutes(o) == 0;
  }

  public int compareHoursAndMinutes(Object o) {
      if (this == o) {
          return -1;
      }
      if (o == null || getClass() != o.getClass()) {
          return 1;
      }
    Time time = (Time) o;
    if (hours == time.hours) {
      return Integer.compare(minutes, time.minutes);
    }
    return Integer.compare(hours, time.hours);
  }


  @Override
  public int hashCode() {
    return Objects.hash(hours, minutes, seconds, nextDay);
  }

  private String formatNumberOn2Places(int number) {
    return String.format("%02d", number);
  }


  public int getHours() {
    return hours;
  }

  public int getMinutes() {
    return minutes;
  }

  public int getSeconds() {
    return seconds;
  }

  public boolean isNextDay() {
    return nextDay;
  }

  public void setNextDay(boolean nextDay) {
    this.nextDay = nextDay;
  }

  public boolean isPrevDay() {
    return prevDay;
  }
}
