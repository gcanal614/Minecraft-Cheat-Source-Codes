package com.ibm.icu.util;

import java.util.Date;

class EasterRule implements DateRule {
  public EasterRule(int daysAfterEaster, boolean isOrthodox) {
    this.calendar = gregorian;
    this.daysAfterEaster = daysAfterEaster;
    if (isOrthodox) {
      orthodox.setGregorianChange(new Date(Long.MAX_VALUE));
      this.calendar = orthodox;
    } 
  }
  
  public Date firstAfter(Date start) {
    return doFirstBetween(start, null);
  }
  
  public Date firstBetween(Date start, Date end) {
    return doFirstBetween(start, end);
  }
  
  public boolean isOn(Date date) {
    synchronized (this.calendar) {
      this.calendar.setTime(date);
      int dayOfYear = this.calendar.get(6);
      this.calendar.setTime(computeInYear(this.calendar.getTime(), this.calendar));
      return (this.calendar.get(6) == dayOfYear);
    } 
  }
  
  public boolean isBetween(Date start, Date end) {
    return (firstBetween(start, end) != null);
  }
  
  private Date doFirstBetween(Date start, Date end) {
    synchronized (this.calendar) {
      Date result = computeInYear(start, this.calendar);
      if (result.before(start)) {
        this.calendar.setTime(start);
        this.calendar.get(1);
        this.calendar.add(1, 1);
        result = computeInYear(this.calendar.getTime(), this.calendar);
      } 
      if (end != null && result.after(end))
        return null; 
      return result;
    } 
  }
  
  private Date computeInYear(Date date, GregorianCalendar cal) {
    if (cal == null)
      cal = this.calendar; 
    synchronized (cal) {
      cal.setTime(date);
      int year = cal.get(1);
      int g = year % 19;
      int i = 0;
      int j = 0;
      if (cal.getTime().after(cal.getGregorianChange())) {
        int c = year / 100;
        int h = (c - c / 4 - (8 * c + 13) / 25 + 19 * g + 15) % 30;
        i = h - h / 28 * (1 - h / 28 * 29 / (h + 1) * (21 - g) / 11);
        j = (year + year / 4 + i + 2 - c + c / 4) % 7;
      } else {
        i = (19 * g + 15) % 30;
        j = (year + year / 4 + i) % 7;
      } 
      int l = i - j;
      int m = 3 + (l + 40) / 44;
      int d = l + 28 - 31 * m / 4;
      cal.clear();
      cal.set(0, 1);
      cal.set(1, year);
      cal.set(2, m - 1);
      cal.set(5, d);
      cal.getTime();
      cal.add(5, this.daysAfterEaster);
      return cal.getTime();
    } 
  }
  
  private static GregorianCalendar gregorian = new GregorianCalendar();
  
  private static GregorianCalendar orthodox = new GregorianCalendar();
  
  private int daysAfterEaster;
  
  private GregorianCalendar calendar;
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\com\ibm\ic\\util\EasterRule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */