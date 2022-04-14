package com.ibm.icu.util;

import com.ibm.icu.impl.Grego;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;

public class SimpleTimeZone extends BasicTimeZone {
  private static final long serialVersionUID = -7034676239311322769L;
  
  public static final int WALL_TIME = 0;
  
  public static final int STANDARD_TIME = 1;
  
  public static final int UTC_TIME = 2;
  
  public SimpleTimeZone(int rawOffset, String ID) {
    super(ID);
    this.dst = 3600000;
    this.xinfo = null;
    this.isFrozen = false;
    construct(rawOffset, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3600000);
  }
  
  public SimpleTimeZone(int rawOffset, String ID, int startMonth, int startDay, int startDayOfWeek, int startTime, int endMonth, int endDay, int endDayOfWeek, int endTime) {
    super(ID);
    this.dst = 3600000;
    this.xinfo = null;
    this.isFrozen = false;
    construct(rawOffset, startMonth, startDay, startDayOfWeek, startTime, 0, endMonth, endDay, endDayOfWeek, endTime, 0, 3600000);
  }
  
  public SimpleTimeZone(int rawOffset, String ID, int startMonth, int startDay, int startDayOfWeek, int startTime, int startTimeMode, int endMonth, int endDay, int endDayOfWeek, int endTime, int endTimeMode, int dstSavings) {
    super(ID);
    this.dst = 3600000;
    this.xinfo = null;
    this.isFrozen = false;
    construct(rawOffset, startMonth, startDay, startDayOfWeek, startTime, startTimeMode, endMonth, endDay, endDayOfWeek, endTime, endTimeMode, dstSavings);
  }
  
  public SimpleTimeZone(int rawOffset, String ID, int startMonth, int startDay, int startDayOfWeek, int startTime, int endMonth, int endDay, int endDayOfWeek, int endTime, int dstSavings) {
    super(ID);
    this.dst = 3600000;
    this.xinfo = null;
    this.isFrozen = false;
    construct(rawOffset, startMonth, startDay, startDayOfWeek, startTime, 0, endMonth, endDay, endDayOfWeek, endTime, 0, dstSavings);
  }
  
  public void setID(String ID) {
    if (isFrozen())
      throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance."); 
    super.setID(ID);
    this.transitionRulesInitialized = false;
  }
  
  public void setRawOffset(int offsetMillis) {
    if (isFrozen())
      throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance."); 
    this.raw = offsetMillis;
    this.transitionRulesInitialized = false;
  }
  
  public int getRawOffset() {
    return this.raw;
  }
  
  public void setStartYear(int year) {
    if (isFrozen())
      throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance."); 
    (getSTZInfo()).sy = year;
    this.startYear = year;
    this.transitionRulesInitialized = false;
  }
  
  public void setStartRule(int month, int dayOfWeekInMonth, int dayOfWeek, int time) {
    if (isFrozen())
      throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance."); 
    getSTZInfo().setStart(month, dayOfWeekInMonth, dayOfWeek, time, -1, false);
    setStartRule(month, dayOfWeekInMonth, dayOfWeek, time, 0);
  }
  
  private void setStartRule(int month, int dayOfWeekInMonth, int dayOfWeek, int time, int mode) {
    assert !isFrozen();
    this.startMonth = month;
    this.startDay = dayOfWeekInMonth;
    this.startDayOfWeek = dayOfWeek;
    this.startTime = time;
    this.startTimeMode = mode;
    decodeStartRule();
    this.transitionRulesInitialized = false;
  }
  
  public void setStartRule(int month, int dayOfMonth, int time) {
    if (isFrozen())
      throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance."); 
    getSTZInfo().setStart(month, -1, -1, time, dayOfMonth, false);
    setStartRule(month, dayOfMonth, 0, time, 0);
  }
  
  public void setStartRule(int month, int dayOfMonth, int dayOfWeek, int time, boolean after) {
    if (isFrozen())
      throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance."); 
    getSTZInfo().setStart(month, -1, dayOfWeek, time, dayOfMonth, after);
    setStartRule(month, after ? dayOfMonth : -dayOfMonth, -dayOfWeek, time, 0);
  }
  
  public void setEndRule(int month, int dayOfWeekInMonth, int dayOfWeek, int time) {
    if (isFrozen())
      throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance."); 
    getSTZInfo().setEnd(month, dayOfWeekInMonth, dayOfWeek, time, -1, false);
    setEndRule(month, dayOfWeekInMonth, dayOfWeek, time, 0);
  }
  
  public void setEndRule(int month, int dayOfMonth, int time) {
    if (isFrozen())
      throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance."); 
    getSTZInfo().setEnd(month, -1, -1, time, dayOfMonth, false);
    setEndRule(month, dayOfMonth, 0, time);
  }
  
  public void setEndRule(int month, int dayOfMonth, int dayOfWeek, int time, boolean after) {
    if (isFrozen())
      throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance."); 
    getSTZInfo().setEnd(month, -1, dayOfWeek, time, dayOfMonth, after);
    setEndRule(month, dayOfMonth, dayOfWeek, time, 0, after);
  }
  
  private void setEndRule(int month, int dayOfMonth, int dayOfWeek, int time, int mode, boolean after) {
    assert !isFrozen();
    setEndRule(month, after ? dayOfMonth : -dayOfMonth, -dayOfWeek, time, mode);
  }
  
  private void setEndRule(int month, int dayOfWeekInMonth, int dayOfWeek, int time, int mode) {
    assert !isFrozen();
    this.endMonth = month;
    this.endDay = dayOfWeekInMonth;
    this.endDayOfWeek = dayOfWeek;
    this.endTime = time;
    this.endTimeMode = mode;
    decodeEndRule();
    this.transitionRulesInitialized = false;
  }
  
  public void setDSTSavings(int millisSavedDuringDST) {
    if (isFrozen())
      throw new UnsupportedOperationException("Attempt to modify a frozen SimpleTimeZone instance."); 
    if (millisSavedDuringDST <= 0)
      throw new IllegalArgumentException(); 
    this.dst = millisSavedDuringDST;
    this.transitionRulesInitialized = false;
  }
  
  public int getDSTSavings() {
    return this.dst;
  }
  
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    if (this.xinfo != null)
      this.xinfo.applyTo(this); 
  }
  
  public String toString() {
    return "SimpleTimeZone: " + getID();
  }
  
  private STZInfo getSTZInfo() {
    if (this.xinfo == null)
      this.xinfo = new STZInfo(); 
    return this.xinfo;
  }
  
  private static final byte[] staticMonthLength = new byte[] { 
      31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 
      30, 31 };
  
  private static final int DOM_MODE = 1;
  
  private static final int DOW_IN_MONTH_MODE = 2;
  
  private static final int DOW_GE_DOM_MODE = 3;
  
  private static final int DOW_LE_DOM_MODE = 4;
  
  private int raw;
  
  private int dst;
  
  private STZInfo xinfo;
  
  private int startMonth;
  
  private int startDay;
  
  private int startDayOfWeek;
  
  private int startTime;
  
  private int startTimeMode;
  
  private int endTimeMode;
  
  private int endMonth;
  
  private int endDay;
  
  private int endDayOfWeek;
  
  private int endTime;
  
  private int startYear;
  
  private boolean useDaylight;
  
  private int startMode;
  
  private int endMode;
  
  private transient boolean transitionRulesInitialized;
  
  private transient InitialTimeZoneRule initialRule;
  
  private transient TimeZoneTransition firstTransition;
  
  private transient AnnualTimeZoneRule stdRule;
  
  private transient AnnualTimeZoneRule dstRule;
  
  private transient boolean isFrozen;
  
  public int getOffset(int era, int year, int month, int day, int dayOfWeek, int millis) {
    if (month < 0 || month > 11)
      throw new IllegalArgumentException(); 
    return getOffset(era, year, month, day, dayOfWeek, millis, Grego.monthLength(year, month));
  }
  
  public int getOffset(int era, int year, int month, int day, int dayOfWeek, int millis, int monthLength) {
    if (month < 0 || month > 11)
      throw new IllegalArgumentException(); 
    return getOffset(era, year, month, day, dayOfWeek, millis, Grego.monthLength(year, month), Grego.previousMonthLength(year, month));
  }
  
  private int getOffset(int era, int year, int month, int day, int dayOfWeek, int millis, int monthLength, int prevMonthLength) {
    if ((era != 1 && era != 0) || month < 0 || month > 11 || day < 1 || day > monthLength || dayOfWeek < 1 || dayOfWeek > 7 || millis < 0 || millis >= 86400000 || monthLength < 28 || monthLength > 31 || prevMonthLength < 28 || prevMonthLength > 31)
      throw new IllegalArgumentException(); 
    int result = this.raw;
    if (!this.useDaylight || year < this.startYear || era != 1)
      return result; 
    boolean southern = (this.startMonth > this.endMonth);
    int startCompare = compareToRule(month, monthLength, prevMonthLength, day, dayOfWeek, millis, (this.startTimeMode == 2) ? -this.raw : 0, this.startMode, this.startMonth, this.startDayOfWeek, this.startDay, this.startTime);
    int endCompare = 0;
    if (southern != ((startCompare >= 0)))
      endCompare = compareToRule(month, monthLength, prevMonthLength, day, dayOfWeek, millis, (this.endTimeMode == 0) ? this.dst : ((this.endTimeMode == 2) ? -this.raw : 0), this.endMode, this.endMonth, this.endDayOfWeek, this.endDay, this.endTime); 
    if ((!southern && startCompare >= 0 && endCompare < 0) || (southern && (startCompare >= 0 || endCompare < 0)))
      result += this.dst; 
    return result;
  }
  
  public void getOffsetFromLocal(long date, int nonExistingTimeOpt, int duplicatedTimeOpt, int[] offsets) {
    offsets[0] = getRawOffset();
    int[] fields = new int[6];
    Grego.timeToFields(date, fields);
    offsets[1] = getOffset(1, fields[0], fields[1], fields[2], fields[3], fields[5]) - offsets[0];
    boolean recalc = false;
    if (offsets[1] > 0) {
      if ((nonExistingTimeOpt & 0x3) == 1 || ((nonExistingTimeOpt & 0x3) != 3 && (nonExistingTimeOpt & 0xC) != 12)) {
        date -= getDSTSavings();
        recalc = true;
      } 
    } else if ((duplicatedTimeOpt & 0x3) == 3 || ((duplicatedTimeOpt & 0x3) != 1 && (duplicatedTimeOpt & 0xC) == 4)) {
      date -= getDSTSavings();
      recalc = true;
    } 
    if (recalc) {
      Grego.timeToFields(date, fields);
      offsets[1] = getOffset(1, fields[0], fields[1], fields[2], fields[3], fields[5]) - offsets[0];
    } 
  }
  
  private int compareToRule(int month, int monthLen, int prevMonthLen, int dayOfMonth, int dayOfWeek, int millis, int millisDelta, int ruleMode, int ruleMonth, int ruleDayOfWeek, int ruleDay, int ruleMillis) {
    millis += millisDelta;
    while (millis >= 86400000) {
      millis -= 86400000;
      dayOfMonth++;
      dayOfWeek = 1 + dayOfWeek % 7;
      if (dayOfMonth > monthLen) {
        dayOfMonth = 1;
        month++;
      } 
    } 
    while (millis < 0) {
      dayOfMonth--;
      dayOfWeek = 1 + (dayOfWeek + 5) % 7;
      if (dayOfMonth < 1) {
        dayOfMonth = prevMonthLen;
        month--;
      } 
      millis += 86400000;
    } 
    if (month < ruleMonth)
      return -1; 
    if (month > ruleMonth)
      return 1; 
    int ruleDayOfMonth = 0;
    if (ruleDay > monthLen)
      ruleDay = monthLen; 
    switch (ruleMode) {
      case 1:
        ruleDayOfMonth = ruleDay;
        break;
      case 2:
        if (ruleDay > 0) {
          ruleDayOfMonth = 1 + (ruleDay - 1) * 7 + (7 + ruleDayOfWeek - dayOfWeek - dayOfMonth + 1) % 7;
          break;
        } 
        ruleDayOfMonth = monthLen + (ruleDay + 1) * 7 - (7 + dayOfWeek + monthLen - dayOfMonth - ruleDayOfWeek) % 7;
        break;
      case 3:
        ruleDayOfMonth = ruleDay + (49 + ruleDayOfWeek - ruleDay - dayOfWeek + dayOfMonth) % 7;
        break;
      case 4:
        ruleDayOfMonth = ruleDay - (49 - ruleDayOfWeek + ruleDay + dayOfWeek - dayOfMonth) % 7;
        break;
    } 
    if (dayOfMonth < ruleDayOfMonth)
      return -1; 
    if (dayOfMonth > ruleDayOfMonth)
      return 1; 
    if (millis < ruleMillis)
      return -1; 
    if (millis > ruleMillis)
      return 1; 
    return 0;
  }
  
  public boolean useDaylightTime() {
    return this.useDaylight;
  }
  
  public boolean observesDaylightTime() {
    return this.useDaylight;
  }
  
  public boolean inDaylightTime(Date date) {
    GregorianCalendar gc = new GregorianCalendar(this);
    gc.setTime(date);
    return gc.inDaylightTime();
  }
  
  private void construct(int _raw, int _startMonth, int _startDay, int _startDayOfWeek, int _startTime, int _startTimeMode, int _endMonth, int _endDay, int _endDayOfWeek, int _endTime, int _endTimeMode, int _dst) {
    this.raw = _raw;
    this.startMonth = _startMonth;
    this.startDay = _startDay;
    this.startDayOfWeek = _startDayOfWeek;
    this.startTime = _startTime;
    this.startTimeMode = _startTimeMode;
    this.endMonth = _endMonth;
    this.endDay = _endDay;
    this.endDayOfWeek = _endDayOfWeek;
    this.endTime = _endTime;
    this.endTimeMode = _endTimeMode;
    this.dst = _dst;
    this.startYear = 0;
    this.startMode = 1;
    this.endMode = 1;
    decodeRules();
    if (_dst <= 0)
      throw new IllegalArgumentException(); 
  }
  
  private void decodeRules() {
    decodeStartRule();
    decodeEndRule();
  }
  
  private void decodeStartRule() {
    this.useDaylight = (this.startDay != 0 && this.endDay != 0);
    if (this.useDaylight && this.dst == 0)
      this.dst = 86400000; 
    if (this.startDay != 0) {
      if (this.startMonth < 0 || this.startMonth > 11)
        throw new IllegalArgumentException(); 
      if (this.startTime < 0 || this.startTime > 86400000 || this.startTimeMode < 0 || this.startTimeMode > 2)
        throw new IllegalArgumentException(); 
      if (this.startDayOfWeek == 0) {
        this.startMode = 1;
      } else {
        if (this.startDayOfWeek > 0) {
          this.startMode = 2;
        } else {
          this.startDayOfWeek = -this.startDayOfWeek;
          if (this.startDay > 0) {
            this.startMode = 3;
          } else {
            this.startDay = -this.startDay;
            this.startMode = 4;
          } 
        } 
        if (this.startDayOfWeek > 7)
          throw new IllegalArgumentException(); 
      } 
      if (this.startMode == 2) {
        if (this.startDay < -5 || this.startDay > 5)
          throw new IllegalArgumentException(); 
      } else if (this.startDay < 1 || this.startDay > staticMonthLength[this.startMonth]) {
        throw new IllegalArgumentException();
      } 
    } 
  }
  
  private void decodeEndRule() {
    this.useDaylight = (this.startDay != 0 && this.endDay != 0);
    if (this.useDaylight && this.dst == 0)
      this.dst = 86400000; 
    if (this.endDay != 0) {
      if (this.endMonth < 0 || this.endMonth > 11)
        throw new IllegalArgumentException(); 
      if (this.endTime < 0 || this.endTime > 86400000 || this.endTimeMode < 0 || this.endTimeMode > 2)
        throw new IllegalArgumentException(); 
      if (this.endDayOfWeek == 0) {
        this.endMode = 1;
      } else {
        if (this.endDayOfWeek > 0) {
          this.endMode = 2;
        } else {
          this.endDayOfWeek = -this.endDayOfWeek;
          if (this.endDay > 0) {
            this.endMode = 3;
          } else {
            this.endDay = -this.endDay;
            this.endMode = 4;
          } 
        } 
        if (this.endDayOfWeek > 7)
          throw new IllegalArgumentException(); 
      } 
      if (this.endMode == 2) {
        if (this.endDay < -5 || this.endDay > 5)
          throw new IllegalArgumentException(); 
      } else if (this.endDay < 1 || this.endDay > staticMonthLength[this.endMonth]) {
        throw new IllegalArgumentException();
      } 
    } 
  }
  
  public boolean equals(Object obj) {
    if (this == obj)
      return true; 
    if (obj == null || getClass() != obj.getClass())
      return false; 
    SimpleTimeZone that = (SimpleTimeZone)obj;
    return (this.raw == that.raw && this.useDaylight == that.useDaylight && idEquals(getID(), that.getID()) && (!this.useDaylight || (this.dst == that.dst && this.startMode == that.startMode && this.startMonth == that.startMonth && this.startDay == that.startDay && this.startDayOfWeek == that.startDayOfWeek && this.startTime == that.startTime && this.startTimeMode == that.startTimeMode && this.endMode == that.endMode && this.endMonth == that.endMonth && this.endDay == that.endDay && this.endDayOfWeek == that.endDayOfWeek && this.endTime == that.endTime && this.endTimeMode == that.endTimeMode && this.startYear == that.startYear)));
  }
  
  private boolean idEquals(String id1, String id2) {
    if (id1 == null && id2 == null)
      return true; 
    if (id1 != null && id2 != null)
      return id1.equals(id2); 
    return false;
  }
  
  public int hashCode() {
    int ret = super.hashCode() + this.raw ^ (this.raw >>> 8) + (this.useDaylight ? 0 : 1);
    if (!this.useDaylight)
      ret += this.dst ^ (this.dst >>> 10) + this.startMode ^ (this.startMode >>> 11) + this.startMonth ^ (this.startMonth >>> 12) + this.startDay ^ (this.startDay >>> 13) + this.startDayOfWeek ^ (this.startDayOfWeek >>> 14) + this.startTime ^ (this.startTime >>> 15) + this.startTimeMode ^ (this.startTimeMode >>> 16) + this.endMode ^ (this.endMode >>> 17) + this.endMonth ^ (this.endMonth >>> 18) + this.endDay ^ (this.endDay >>> 19) + this.endDayOfWeek ^ (this.endDayOfWeek >>> 20) + this.endTime ^ (this.endTime >>> 21) + this.endTimeMode ^ (this.endTimeMode >>> 22) + this.startYear ^ this.startYear >>> 23; 
    return ret;
  }
  
  public Object clone() {
    if (isFrozen())
      return this; 
    return cloneAsThawed();
  }
  
  public boolean hasSameRules(TimeZone othr) {
    if (this == othr)
      return true; 
    if (!(othr instanceof SimpleTimeZone))
      return false; 
    SimpleTimeZone other = (SimpleTimeZone)othr;
    return (other != null && this.raw == other.raw && this.useDaylight == other.useDaylight && (!this.useDaylight || (this.dst == other.dst && this.startMode == other.startMode && this.startMonth == other.startMonth && this.startDay == other.startDay && this.startDayOfWeek == other.startDayOfWeek && this.startTime == other.startTime && this.startTimeMode == other.startTimeMode && this.endMode == other.endMode && this.endMonth == other.endMonth && this.endDay == other.endDay && this.endDayOfWeek == other.endDayOfWeek && this.endTime == other.endTime && this.endTimeMode == other.endTimeMode && this.startYear == other.startYear)));
  }
  
  public TimeZoneTransition getNextTransition(long base, boolean inclusive) {
    if (!this.useDaylight)
      return null; 
    initTransitionRules();
    long firstTransitionTime = this.firstTransition.getTime();
    if (base < firstTransitionTime || (inclusive && base == firstTransitionTime))
      return this.firstTransition; 
    Date stdDate = this.stdRule.getNextStart(base, this.dstRule.getRawOffset(), this.dstRule.getDSTSavings(), inclusive);
    Date dstDate = this.dstRule.getNextStart(base, this.stdRule.getRawOffset(), this.stdRule.getDSTSavings(), inclusive);
    if (stdDate != null && (dstDate == null || stdDate.before(dstDate)))
      return new TimeZoneTransition(stdDate.getTime(), this.dstRule, this.stdRule); 
    if (dstDate != null && (stdDate == null || dstDate.before(stdDate)))
      return new TimeZoneTransition(dstDate.getTime(), this.stdRule, this.dstRule); 
    return null;
  }
  
  public TimeZoneTransition getPreviousTransition(long base, boolean inclusive) {
    if (!this.useDaylight)
      return null; 
    initTransitionRules();
    long firstTransitionTime = this.firstTransition.getTime();
    if (base < firstTransitionTime || (!inclusive && base == firstTransitionTime))
      return null; 
    Date stdDate = this.stdRule.getPreviousStart(base, this.dstRule.getRawOffset(), this.dstRule.getDSTSavings(), inclusive);
    Date dstDate = this.dstRule.getPreviousStart(base, this.stdRule.getRawOffset(), this.stdRule.getDSTSavings(), inclusive);
    if (stdDate != null && (dstDate == null || stdDate.after(dstDate)))
      return new TimeZoneTransition(stdDate.getTime(), this.dstRule, this.stdRule); 
    if (dstDate != null && (stdDate == null || dstDate.after(stdDate)))
      return new TimeZoneTransition(dstDate.getTime(), this.stdRule, this.dstRule); 
    return null;
  }
  
  public TimeZoneRule[] getTimeZoneRules() {
    initTransitionRules();
    int size = this.useDaylight ? 3 : 1;
    TimeZoneRule[] rules = new TimeZoneRule[size];
    rules[0] = this.initialRule;
    if (this.useDaylight) {
      rules[1] = this.stdRule;
      rules[2] = this.dstRule;
    } 
    return rules;
  }
  
  private synchronized void initTransitionRules() {
    if (this.transitionRulesInitialized)
      return; 
    if (this.useDaylight) {
      DateTimeRule dtRule = null;
      int timeRuleType = (this.startTimeMode == 1) ? 1 : ((this.startTimeMode == 2) ? 2 : 0);
      switch (this.startMode) {
        case 1:
          dtRule = new DateTimeRule(this.startMonth, this.startDay, this.startTime, timeRuleType);
          break;
        case 2:
          dtRule = new DateTimeRule(this.startMonth, this.startDay, this.startDayOfWeek, this.startTime, timeRuleType);
          break;
        case 3:
          dtRule = new DateTimeRule(this.startMonth, this.startDay, this.startDayOfWeek, true, this.startTime, timeRuleType);
          break;
        case 4:
          dtRule = new DateTimeRule(this.startMonth, this.startDay, this.startDayOfWeek, false, this.startTime, timeRuleType);
          break;
      } 
      this.dstRule = new AnnualTimeZoneRule(getID() + "(DST)", getRawOffset(), getDSTSavings(), dtRule, this.startYear, 2147483647);
      long firstDstStart = this.dstRule.getFirstStart(getRawOffset(), 0).getTime();
      timeRuleType = (this.endTimeMode == 1) ? 1 : ((this.endTimeMode == 2) ? 2 : 0);
      switch (this.endMode) {
        case 1:
          dtRule = new DateTimeRule(this.endMonth, this.endDay, this.endTime, timeRuleType);
          break;
        case 2:
          dtRule = new DateTimeRule(this.endMonth, this.endDay, this.endDayOfWeek, this.endTime, timeRuleType);
          break;
        case 3:
          dtRule = new DateTimeRule(this.endMonth, this.endDay, this.endDayOfWeek, true, this.endTime, timeRuleType);
          break;
        case 4:
          dtRule = new DateTimeRule(this.endMonth, this.endDay, this.endDayOfWeek, false, this.endTime, timeRuleType);
          break;
      } 
      this.stdRule = new AnnualTimeZoneRule(getID() + "(STD)", getRawOffset(), 0, dtRule, this.startYear, 2147483647);
      long firstStdStart = this.stdRule.getFirstStart(getRawOffset(), this.dstRule.getDSTSavings()).getTime();
      if (firstStdStart < firstDstStart) {
        this.initialRule = new InitialTimeZoneRule(getID() + "(DST)", getRawOffset(), this.dstRule.getDSTSavings());
        this.firstTransition = new TimeZoneTransition(firstStdStart, this.initialRule, this.stdRule);
      } else {
        this.initialRule = new InitialTimeZoneRule(getID() + "(STD)", getRawOffset(), 0);
        this.firstTransition = new TimeZoneTransition(firstDstStart, this.initialRule, this.dstRule);
      } 
    } else {
      this.initialRule = new InitialTimeZoneRule(getID(), getRawOffset(), 0);
    } 
    this.transitionRulesInitialized = true;
  }
  
  public boolean isFrozen() {
    return this.isFrozen;
  }
  
  public TimeZone freeze() {
    this.isFrozen = true;
    return this;
  }
  
  public TimeZone cloneAsThawed() {
    SimpleTimeZone tz = (SimpleTimeZone)super.cloneAsThawed();
    tz.isFrozen = false;
    return tz;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\com\ibm\ic\\util\SimpleTimeZone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */