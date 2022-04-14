package com.ibm.icu.text;

import com.ibm.icu.impl.CalendarData;
import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.DateInterval;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DateIntervalFormat extends UFormat {
  private static final long serialVersionUID = 1L;
  
  static final class BestMatchInfo {
    final String bestMatchSkeleton;
    
    final int bestMatchDistanceInfo;
    
    BestMatchInfo(String bestSkeleton, int difference) {
      this.bestMatchSkeleton = bestSkeleton;
      this.bestMatchDistanceInfo = difference;
    }
  }
  
  private static final class SkeletonAndItsBestMatch {
    final String skeleton;
    
    final String bestMatchSkeleton;
    
    SkeletonAndItsBestMatch(String skeleton, String bestMatch) {
      this.skeleton = skeleton;
      this.bestMatchSkeleton = bestMatch;
    }
  }
  
  private static ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>> LOCAL_PATTERN_CACHE = (ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>>)new SimpleCache();
  
  private DateIntervalInfo fInfo;
  
  private SimpleDateFormat fDateFormat;
  
  private Calendar fFromCalendar;
  
  private Calendar fToCalendar;
  
  private String fSkeleton = null;
  
  private boolean isDateIntervalInfoDefault;
  
  private transient Map<String, DateIntervalInfo.PatternInfo> fIntervalPatterns = null;
  
  private DateIntervalFormat() {}
  
  public DateIntervalFormat(String skeleton, DateIntervalInfo dtItvInfo, SimpleDateFormat simpleDateFormat) {
    this.fDateFormat = simpleDateFormat;
    dtItvInfo.freeze();
    this.fSkeleton = skeleton;
    this.fInfo = dtItvInfo;
    this.isDateIntervalInfoDefault = false;
    this.fFromCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
    this.fToCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
    initializePattern(null);
  }
  
  private DateIntervalFormat(String skeleton, ULocale locale, SimpleDateFormat simpleDateFormat) {
    this.fDateFormat = simpleDateFormat;
    this.fSkeleton = skeleton;
    this.fInfo = (new DateIntervalInfo(locale)).freeze();
    this.isDateIntervalInfoDefault = true;
    this.fFromCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
    this.fToCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
    initializePattern(LOCAL_PATTERN_CACHE);
  }
  
  public static final DateIntervalFormat getInstance(String skeleton) {
    return getInstance(skeleton, ULocale.getDefault(ULocale.Category.FORMAT));
  }
  
  public static final DateIntervalFormat getInstance(String skeleton, Locale locale) {
    return getInstance(skeleton, ULocale.forLocale(locale));
  }
  
  public static final DateIntervalFormat getInstance(String skeleton, ULocale locale) {
    DateTimePatternGenerator generator = DateTimePatternGenerator.getInstance(locale);
    return new DateIntervalFormat(skeleton, locale, new SimpleDateFormat(generator.getBestPattern(skeleton), locale));
  }
  
  public static final DateIntervalFormat getInstance(String skeleton, DateIntervalInfo dtitvinf) {
    return getInstance(skeleton, ULocale.getDefault(ULocale.Category.FORMAT), dtitvinf);
  }
  
  public static final DateIntervalFormat getInstance(String skeleton, Locale locale, DateIntervalInfo dtitvinf) {
    return getInstance(skeleton, ULocale.forLocale(locale), dtitvinf);
  }
  
  public static final DateIntervalFormat getInstance(String skeleton, ULocale locale, DateIntervalInfo dtitvinf) {
    dtitvinf = (DateIntervalInfo)dtitvinf.clone();
    DateTimePatternGenerator generator = DateTimePatternGenerator.getInstance(locale);
    return new DateIntervalFormat(skeleton, dtitvinf, new SimpleDateFormat(generator.getBestPattern(skeleton), locale));
  }
  
  public Object clone() {
    DateIntervalFormat other = (DateIntervalFormat)super.clone();
    other.fDateFormat = (SimpleDateFormat)this.fDateFormat.clone();
    other.fInfo = (DateIntervalInfo)this.fInfo.clone();
    other.fFromCalendar = (Calendar)this.fFromCalendar.clone();
    other.fToCalendar = (Calendar)this.fToCalendar.clone();
    return other;
  }
  
  public final StringBuffer format(Object obj, StringBuffer appendTo, FieldPosition fieldPosition) {
    if (obj instanceof DateInterval)
      return format((DateInterval)obj, appendTo, fieldPosition); 
    throw new IllegalArgumentException("Cannot format given Object (" + obj.getClass().getName() + ") as a DateInterval");
  }
  
  public final StringBuffer format(DateInterval dtInterval, StringBuffer appendTo, FieldPosition fieldPosition) {
    this.fFromCalendar.setTimeInMillis(dtInterval.getFromDate());
    this.fToCalendar.setTimeInMillis(dtInterval.getToDate());
    return format(this.fFromCalendar, this.fToCalendar, appendTo, fieldPosition);
  }
  
  public final StringBuffer format(Calendar fromCalendar, Calendar toCalendar, StringBuffer appendTo, FieldPosition pos) {
    Calendar firstCal, secondCal;
    if (!fromCalendar.isEquivalentTo(toCalendar))
      throw new IllegalArgumentException("can not format on two different calendars"); 
    int field = -1;
    if (fromCalendar.get(0) != toCalendar.get(0)) {
      field = 0;
    } else if (fromCalendar.get(1) != toCalendar.get(1)) {
      field = 1;
    } else if (fromCalendar.get(2) != toCalendar.get(2)) {
      field = 2;
    } else if (fromCalendar.get(5) != toCalendar.get(5)) {
      field = 5;
    } else if (fromCalendar.get(9) != toCalendar.get(9)) {
      field = 9;
    } else if (fromCalendar.get(10) != toCalendar.get(10)) {
      field = 10;
    } else if (fromCalendar.get(12) != toCalendar.get(12)) {
      field = 12;
    } else {
      return this.fDateFormat.format(fromCalendar, appendTo, pos);
    } 
    DateIntervalInfo.PatternInfo intervalPattern = this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field]);
    if (intervalPattern == null) {
      if (this.fDateFormat.isFieldUnitIgnored(field))
        return this.fDateFormat.format(fromCalendar, appendTo, pos); 
      return fallbackFormat(fromCalendar, toCalendar, appendTo, pos);
    } 
    if (intervalPattern.getFirstPart() == null)
      return fallbackFormat(fromCalendar, toCalendar, appendTo, pos, intervalPattern.getSecondPart()); 
    if (intervalPattern.firstDateInPtnIsLaterDate()) {
      firstCal = toCalendar;
      secondCal = fromCalendar;
    } else {
      firstCal = fromCalendar;
      secondCal = toCalendar;
    } 
    String originalPattern = this.fDateFormat.toPattern();
    this.fDateFormat.applyPattern(intervalPattern.getFirstPart());
    this.fDateFormat.format(firstCal, appendTo, pos);
    if (intervalPattern.getSecondPart() != null) {
      this.fDateFormat.applyPattern(intervalPattern.getSecondPart());
      this.fDateFormat.format(secondCal, appendTo, pos);
    } 
    this.fDateFormat.applyPattern(originalPattern);
    return appendTo;
  }
  
  private final StringBuffer fallbackFormat(Calendar fromCalendar, Calendar toCalendar, StringBuffer appendTo, FieldPosition pos) {
    StringBuffer earlierDate = new StringBuffer(64);
    earlierDate = this.fDateFormat.format(fromCalendar, earlierDate, pos);
    StringBuffer laterDate = new StringBuffer(64);
    laterDate = this.fDateFormat.format(toCalendar, laterDate, pos);
    String fallbackPattern = this.fInfo.getFallbackIntervalPattern();
    String fallback = MessageFormat.format(fallbackPattern, new Object[] { earlierDate.toString(), laterDate.toString() });
    appendTo.append(fallback);
    return appendTo;
  }
  
  private final StringBuffer fallbackFormat(Calendar fromCalendar, Calendar toCalendar, StringBuffer appendTo, FieldPosition pos, String fullPattern) {
    String originalPattern = this.fDateFormat.toPattern();
    this.fDateFormat.applyPattern(fullPattern);
    fallbackFormat(fromCalendar, toCalendar, appendTo, pos);
    this.fDateFormat.applyPattern(originalPattern);
    return appendTo;
  }
  
  public Object parseObject(String source, ParsePosition parse_pos) {
    throw new UnsupportedOperationException("parsing is not supported");
  }
  
  public DateIntervalInfo getDateIntervalInfo() {
    return (DateIntervalInfo)this.fInfo.clone();
  }
  
  public void setDateIntervalInfo(DateIntervalInfo newItvPattern) {
    this.fInfo = (DateIntervalInfo)newItvPattern.clone();
    this.isDateIntervalInfoDefault = false;
    this.fInfo.freeze();
    if (this.fDateFormat != null)
      initializePattern(null); 
  }
  
  public DateFormat getDateFormat() {
    return (DateFormat)this.fDateFormat.clone();
  }
  
  private void initializePattern(ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>> cache) {
    String fullPattern = this.fDateFormat.toPattern();
    ULocale locale = this.fDateFormat.getLocale();
    String key = null;
    Map<String, DateIntervalInfo.PatternInfo> patterns = null;
    if (cache != null) {
      if (this.fSkeleton != null) {
        key = locale.toString() + "+" + fullPattern + "+" + this.fSkeleton;
      } else {
        key = locale.toString() + "+" + fullPattern;
      } 
      patterns = (Map<String, DateIntervalInfo.PatternInfo>)cache.get(key);
    } 
    if (patterns == null) {
      Map<String, DateIntervalInfo.PatternInfo> intervalPatterns = initializeIntervalPattern(fullPattern, locale);
      patterns = Collections.unmodifiableMap(intervalPatterns);
      if (cache != null)
        cache.put(key, patterns); 
    } 
    this.fIntervalPatterns = patterns;
  }
  
  private Map<String, DateIntervalInfo.PatternInfo> initializeIntervalPattern(String fullPattern, ULocale locale) {
    DateTimePatternGenerator dtpng = DateTimePatternGenerator.getInstance(locale);
    if (this.fSkeleton == null)
      this.fSkeleton = dtpng.getSkeleton(fullPattern); 
    String skeleton = this.fSkeleton;
    HashMap<String, DateIntervalInfo.PatternInfo> intervalPatterns = new HashMap<String, DateIntervalInfo.PatternInfo>();
    StringBuilder date = new StringBuilder(skeleton.length());
    StringBuilder normalizedDate = new StringBuilder(skeleton.length());
    StringBuilder time = new StringBuilder(skeleton.length());
    StringBuilder normalizedTime = new StringBuilder(skeleton.length());
    getDateTimeSkeleton(skeleton, date, normalizedDate, time, normalizedTime);
    String dateSkeleton = date.toString();
    String timeSkeleton = time.toString();
    String normalizedDateSkeleton = normalizedDate.toString();
    String normalizedTimeSkeleton = normalizedTime.toString();
    boolean found = genSeparateDateTimePtn(normalizedDateSkeleton, normalizedTimeSkeleton, intervalPatterns);
    if (!found) {
      if (time.length() != 0)
        if (date.length() == 0) {
          timeSkeleton = "yMd" + timeSkeleton;
          String pattern = dtpng.getBestPattern(timeSkeleton);
          DateIntervalInfo.PatternInfo ptn = new DateIntervalInfo.PatternInfo(null, pattern, this.fInfo.getDefaultOrder());
          intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], ptn);
          intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], ptn);
          intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], ptn);
        }  
      return intervalPatterns;
    } 
    if (time.length() != 0)
      if (date.length() == 0) {
        timeSkeleton = "yMd" + timeSkeleton;
        String pattern = dtpng.getBestPattern(timeSkeleton);
        DateIntervalInfo.PatternInfo ptn = new DateIntervalInfo.PatternInfo(null, pattern, this.fInfo.getDefaultOrder());
        intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], ptn);
        intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], ptn);
        intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], ptn);
      } else {
        if (!fieldExistsInSkeleton(5, dateSkeleton)) {
          skeleton = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5] + skeleton;
          genFallbackPattern(5, skeleton, intervalPatterns, dtpng);
        } 
        if (!fieldExistsInSkeleton(2, dateSkeleton)) {
          skeleton = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2] + skeleton;
          genFallbackPattern(2, skeleton, intervalPatterns, dtpng);
        } 
        if (!fieldExistsInSkeleton(1, dateSkeleton)) {
          skeleton = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1] + skeleton;
          genFallbackPattern(1, skeleton, intervalPatterns, dtpng);
        } 
        CalendarData calData = new CalendarData(locale, null);
        String[] patterns = calData.getDateTimePatterns();
        String datePattern = dtpng.getBestPattern(dateSkeleton);
        concatSingleDate2TimeInterval(patterns[8], datePattern, 9, intervalPatterns);
        concatSingleDate2TimeInterval(patterns[8], datePattern, 10, intervalPatterns);
        concatSingleDate2TimeInterval(patterns[8], datePattern, 12, intervalPatterns);
      }  
    return intervalPatterns;
  }
  
  private void genFallbackPattern(int field, String skeleton, Map<String, DateIntervalInfo.PatternInfo> intervalPatterns, DateTimePatternGenerator dtpng) {
    String pattern = dtpng.getBestPattern(skeleton);
    DateIntervalInfo.PatternInfo ptn = new DateIntervalInfo.PatternInfo(null, pattern, this.fInfo.getDefaultOrder());
    intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], ptn);
  }
  
  private static void getDateTimeSkeleton(String skeleton, StringBuilder dateSkeleton, StringBuilder normalizedDateSkeleton, StringBuilder timeSkeleton, StringBuilder normalizedTimeSkeleton) {
    int ECount = 0;
    int dCount = 0;
    int MCount = 0;
    int yCount = 0;
    int hCount = 0;
    int HCount = 0;
    int mCount = 0;
    int vCount = 0;
    int zCount = 0;
    int i;
    for (i = 0; i < skeleton.length(); i++) {
      char ch = skeleton.charAt(i);
      switch (ch) {
        case 'E':
          dateSkeleton.append(ch);
          ECount++;
          break;
        case 'd':
          dateSkeleton.append(ch);
          dCount++;
          break;
        case 'M':
          dateSkeleton.append(ch);
          MCount++;
          break;
        case 'y':
          dateSkeleton.append(ch);
          yCount++;
          break;
        case 'D':
        case 'F':
        case 'G':
        case 'L':
        case 'Q':
        case 'W':
        case 'Y':
        case 'c':
        case 'e':
        case 'g':
        case 'l':
        case 'q':
        case 'u':
        case 'w':
          normalizedDateSkeleton.append(ch);
          dateSkeleton.append(ch);
          break;
        case 'a':
          timeSkeleton.append(ch);
          break;
        case 'h':
          timeSkeleton.append(ch);
          hCount++;
          break;
        case 'H':
          timeSkeleton.append(ch);
          HCount++;
          break;
        case 'm':
          timeSkeleton.append(ch);
          mCount++;
          break;
        case 'z':
          zCount++;
          timeSkeleton.append(ch);
          break;
        case 'v':
          vCount++;
          timeSkeleton.append(ch);
          break;
        case 'A':
        case 'K':
        case 'S':
        case 'V':
        case 'Z':
        case 'j':
        case 'k':
        case 's':
          timeSkeleton.append(ch);
          normalizedTimeSkeleton.append(ch);
          break;
      } 
    } 
    if (yCount != 0)
      normalizedDateSkeleton.append('y'); 
    if (MCount != 0)
      if (MCount < 3) {
        normalizedDateSkeleton.append('M');
      } else {
        for (i = 0; i < MCount && i < 5; i++)
          normalizedDateSkeleton.append('M'); 
      }  
    if (ECount != 0)
      if (ECount <= 3) {
        normalizedDateSkeleton.append('E');
      } else {
        for (i = 0; i < ECount && i < 5; i++)
          normalizedDateSkeleton.append('E'); 
      }  
    if (dCount != 0)
      normalizedDateSkeleton.append('d'); 
    if (HCount != 0) {
      normalizedTimeSkeleton.append('H');
    } else if (hCount != 0) {
      normalizedTimeSkeleton.append('h');
    } 
    if (mCount != 0)
      normalizedTimeSkeleton.append('m'); 
    if (zCount != 0)
      normalizedTimeSkeleton.append('z'); 
    if (vCount != 0)
      normalizedTimeSkeleton.append('v'); 
  }
  
  private boolean genSeparateDateTimePtn(String dateSkeleton, String timeSkeleton, Map<String, DateIntervalInfo.PatternInfo> intervalPatterns) {
    String skeleton;
    if (timeSkeleton.length() != 0) {
      skeleton = timeSkeleton;
    } else {
      skeleton = dateSkeleton;
    } 
    BestMatchInfo retValue = this.fInfo.getBestSkeleton(skeleton);
    String bestSkeleton = retValue.bestMatchSkeleton;
    int differenceInfo = retValue.bestMatchDistanceInfo;
    if (differenceInfo == -1)
      return false; 
    if (timeSkeleton.length() == 0) {
      genIntervalPattern(5, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
      SkeletonAndItsBestMatch skeletons = genIntervalPattern(2, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
      if (skeletons != null) {
        bestSkeleton = skeletons.skeleton;
        skeleton = skeletons.bestMatchSkeleton;
      } 
      genIntervalPattern(1, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
    } else {
      genIntervalPattern(12, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
      genIntervalPattern(10, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
      genIntervalPattern(9, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
    } 
    return true;
  }
  
  private SkeletonAndItsBestMatch genIntervalPattern(int field, String skeleton, String bestSkeleton, int differenceInfo, Map<String, DateIntervalInfo.PatternInfo> intervalPatterns) {
    SkeletonAndItsBestMatch retValue = null;
    DateIntervalInfo.PatternInfo pattern = this.fInfo.getIntervalPattern(bestSkeleton, field);
    if (pattern == null) {
      if (SimpleDateFormat.isFieldUnitIgnored(bestSkeleton, field)) {
        DateIntervalInfo.PatternInfo ptnInfo = new DateIntervalInfo.PatternInfo(this.fDateFormat.toPattern(), null, this.fInfo.getDefaultOrder());
        intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], ptnInfo);
        return null;
      } 
      if (field == 9) {
        pattern = this.fInfo.getIntervalPattern(bestSkeleton, 10);
        if (pattern != null)
          intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], pattern); 
        return null;
      } 
      String fieldLetter = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field];
      bestSkeleton = fieldLetter + bestSkeleton;
      skeleton = fieldLetter + skeleton;
      pattern = this.fInfo.getIntervalPattern(bestSkeleton, field);
      if (pattern == null && differenceInfo == 0) {
        BestMatchInfo tmpRetValue = this.fInfo.getBestSkeleton(skeleton);
        String tmpBestSkeleton = tmpRetValue.bestMatchSkeleton;
        differenceInfo = tmpRetValue.bestMatchDistanceInfo;
        if (tmpBestSkeleton.length() != 0 && differenceInfo != -1) {
          pattern = this.fInfo.getIntervalPattern(tmpBestSkeleton, field);
          bestSkeleton = tmpBestSkeleton;
        } 
      } 
      if (pattern != null)
        retValue = new SkeletonAndItsBestMatch(skeleton, bestSkeleton); 
    } 
    if (pattern != null) {
      if (differenceInfo != 0) {
        String part1 = adjustFieldWidth(skeleton, bestSkeleton, pattern.getFirstPart(), differenceInfo);
        String part2 = adjustFieldWidth(skeleton, bestSkeleton, pattern.getSecondPart(), differenceInfo);
        pattern = new DateIntervalInfo.PatternInfo(part1, part2, pattern.firstDateInPtnIsLaterDate());
      } 
      intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], pattern);
    } 
    return retValue;
  }
  
  private static String adjustFieldWidth(String inputSkeleton, String bestMatchSkeleton, String bestMatchIntervalPattern, int differenceInfo) {
    if (bestMatchIntervalPattern == null)
      return null; 
    int[] inputSkeletonFieldWidth = new int[58];
    int[] bestMatchSkeletonFieldWidth = new int[58];
    DateIntervalInfo.parseSkeleton(inputSkeleton, inputSkeletonFieldWidth);
    DateIntervalInfo.parseSkeleton(bestMatchSkeleton, bestMatchSkeletonFieldWidth);
    if (differenceInfo == 2)
      bestMatchIntervalPattern = bestMatchIntervalPattern.replace('v', 'z'); 
    StringBuilder adjustedPtn = new StringBuilder(bestMatchIntervalPattern);
    boolean inQuote = false;
    char prevCh = Character.MIN_VALUE;
    int count = 0;
    int PATTERN_CHAR_BASE = 65;
    int adjustedPtnLength = adjustedPtn.length();
    for (int i = 0; i < adjustedPtnLength; i++) {
      char ch = adjustedPtn.charAt(i);
      if (ch != prevCh && count > 0) {
        char skeletonChar = prevCh;
        if (skeletonChar == 'L')
          skeletonChar = 'M'; 
        int fieldCount = bestMatchSkeletonFieldWidth[skeletonChar - PATTERN_CHAR_BASE];
        int inputFieldCount = inputSkeletonFieldWidth[skeletonChar - PATTERN_CHAR_BASE];
        if (fieldCount == count && inputFieldCount > fieldCount) {
          count = inputFieldCount - fieldCount;
          for (int j = 0; j < count; j++)
            adjustedPtn.insert(i, prevCh); 
          i += count;
          adjustedPtnLength += count;
        } 
        count = 0;
      } 
      if (ch == '\'') {
        if (i + 1 < adjustedPtn.length() && adjustedPtn.charAt(i + 1) == '\'') {
          i++;
        } else {
          inQuote = !inQuote;
        } 
      } else if (!inQuote && ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))) {
        prevCh = ch;
        count++;
      } 
    } 
    if (count > 0) {
      char skeletonChar = prevCh;
      if (skeletonChar == 'L')
        skeletonChar = 'M'; 
      int fieldCount = bestMatchSkeletonFieldWidth[skeletonChar - PATTERN_CHAR_BASE];
      int inputFieldCount = inputSkeletonFieldWidth[skeletonChar - PATTERN_CHAR_BASE];
      if (fieldCount == count && inputFieldCount > fieldCount) {
        count = inputFieldCount - fieldCount;
        for (int j = 0; j < count; j++)
          adjustedPtn.append(prevCh); 
      } 
    } 
    return adjustedPtn.toString();
  }
  
  private void concatSingleDate2TimeInterval(String dtfmt, String datePattern, int field, Map<String, DateIntervalInfo.PatternInfo> intervalPatterns) {
    DateIntervalInfo.PatternInfo timeItvPtnInfo = intervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field]);
    if (timeItvPtnInfo != null) {
      String timeIntervalPattern = timeItvPtnInfo.getFirstPart() + timeItvPtnInfo.getSecondPart();
      String pattern = MessageFormat.format(dtfmt, new Object[] { timeIntervalPattern, datePattern });
      timeItvPtnInfo = DateIntervalInfo.genPatternInfo(pattern, timeItvPtnInfo.firstDateInPtnIsLaterDate());
      intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], timeItvPtnInfo);
    } 
  }
  
  private static boolean fieldExistsInSkeleton(int field, String skeleton) {
    String fieldChar = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field];
    return !(skeleton.indexOf(fieldChar) == -1);
  }
  
  private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
    stream.defaultReadObject();
    initializePattern(this.isDateIntervalInfoDefault ? LOCAL_PATTERN_CACHE : null);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\com\ibm\icu\text\DateIntervalFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */