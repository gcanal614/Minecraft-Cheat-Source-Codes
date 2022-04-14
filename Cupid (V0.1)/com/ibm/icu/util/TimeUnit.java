package com.ibm.icu.util;

public class TimeUnit extends MeasureUnit {
  private String name;
  
  private static TimeUnit[] values = new TimeUnit[7];
  
  private static int valueCount = 0;
  
  public static TimeUnit SECOND = new TimeUnit("second");
  
  public static TimeUnit MINUTE = new TimeUnit("minute");
  
  public static TimeUnit HOUR = new TimeUnit("hour");
  
  public static TimeUnit DAY = new TimeUnit("day");
  
  public static TimeUnit WEEK = new TimeUnit("week");
  
  public static TimeUnit MONTH = new TimeUnit("month");
  
  public static TimeUnit YEAR = new TimeUnit("year");
  
  private TimeUnit(String name) {
    this.name = name;
    values[valueCount++] = this;
  }
  
  public static TimeUnit[] values() {
    return (TimeUnit[])values.clone();
  }
  
  public String toString() {
    return this.name;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\com\ibm\ic\\util\TimeUnit.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */