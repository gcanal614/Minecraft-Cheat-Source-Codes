/*
 * Decompiled with CFR 0.152.
 */
package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.FixedUnitBuilder;
import com.ibm.icu.impl.duration.MultiUnitBuilder;
import com.ibm.icu.impl.duration.OneOrTwoUnitBuilder;
import com.ibm.icu.impl.duration.Period;
import com.ibm.icu.impl.duration.PeriodBuilder;
import com.ibm.icu.impl.duration.PeriodBuilderFactory;
import com.ibm.icu.impl.duration.SingleUnitBuilder;
import com.ibm.icu.impl.duration.TimeUnit;
import com.ibm.icu.impl.duration.impl.PeriodFormatterData;
import com.ibm.icu.impl.duration.impl.PeriodFormatterDataService;
import java.util.TimeZone;

class BasicPeriodBuilderFactory
implements PeriodBuilderFactory {
    private PeriodFormatterDataService ds;
    private Settings settings;
    private static final short allBits = 255;

    BasicPeriodBuilderFactory(PeriodFormatterDataService ds) {
        this.ds = ds;
        this.settings = new Settings();
    }

    static long approximateDurationOf(TimeUnit unit) {
        return TimeUnit.approxDurations[unit.ordinal];
    }

    public PeriodBuilderFactory setAvailableUnitRange(TimeUnit minUnit, TimeUnit maxUnit) {
        int uset = 0;
        for (int i = maxUnit.ordinal; i <= minUnit.ordinal; ++i) {
            uset |= 1 << i;
        }
        if (uset == 0) {
            throw new IllegalArgumentException("range " + minUnit + " to " + maxUnit + " is empty");
        }
        this.settings = this.settings.setUnits(uset);
        return this;
    }

    public PeriodBuilderFactory setUnitIsAvailable(TimeUnit unit, boolean available) {
        int uset = this.settings.uset;
        uset = available ? (uset |= 1 << unit.ordinal) : (uset &= ~(1 << unit.ordinal));
        this.settings = this.settings.setUnits(uset);
        return this;
    }

    public PeriodBuilderFactory setMaxLimit(float maxLimit) {
        this.settings = this.settings.setMaxLimit(maxLimit);
        return this;
    }

    public PeriodBuilderFactory setMinLimit(float minLimit) {
        this.settings = this.settings.setMinLimit(minLimit);
        return this;
    }

    public PeriodBuilderFactory setAllowZero(boolean allow) {
        this.settings = this.settings.setAllowZero(allow);
        return this;
    }

    public PeriodBuilderFactory setWeeksAloneOnly(boolean aloneOnly) {
        this.settings = this.settings.setWeeksAloneOnly(aloneOnly);
        return this;
    }

    public PeriodBuilderFactory setAllowMilliseconds(boolean allow) {
        this.settings = this.settings.setAllowMilliseconds(allow);
        return this;
    }

    public PeriodBuilderFactory setLocale(String localeName) {
        this.settings = this.settings.setLocale(localeName);
        return this;
    }

    public PeriodBuilderFactory setTimeZone(TimeZone timeZone) {
        return this;
    }

    private Settings getSettings() {
        if (this.settings.effectiveSet() == 0) {
            return null;
        }
        return this.settings.setInUse();
    }

    public PeriodBuilder getFixedUnitBuilder(TimeUnit unit) {
        return FixedUnitBuilder.get(unit, this.getSettings());
    }

    public PeriodBuilder getSingleUnitBuilder() {
        return SingleUnitBuilder.get(this.getSettings());
    }

    public PeriodBuilder getOneOrTwoUnitBuilder() {
        return OneOrTwoUnitBuilder.get(this.getSettings());
    }

    public PeriodBuilder getMultiUnitBuilder(int periodCount) {
        return MultiUnitBuilder.get(periodCount, this.getSettings());
    }

    class Settings {
        boolean inUse;
        short uset = (short)255;
        TimeUnit maxUnit = TimeUnit.YEAR;
        TimeUnit minUnit = TimeUnit.MILLISECOND;
        int maxLimit;
        int minLimit;
        boolean allowZero = true;
        boolean weeksAloneOnly;
        boolean allowMillis = true;

        Settings() {
        }

        Settings setUnits(int uset) {
            if (this.uset == uset) {
                return this;
            }
            Settings result2 = this.inUse ? this.copy() : this;
            result2.uset = (short)uset;
            if ((uset & 0xFF) == 255) {
                result2.uset = (short)255;
                result2.maxUnit = TimeUnit.YEAR;
                result2.minUnit = TimeUnit.MILLISECOND;
            } else {
                int lastUnit = -1;
                for (int i = 0; i < TimeUnit.units.length; ++i) {
                    if (0 == (uset & 1 << i)) continue;
                    if (lastUnit == -1) {
                        result2.maxUnit = TimeUnit.units[i];
                    }
                    lastUnit = i;
                }
                if (lastUnit == -1) {
                    result2.maxUnit = null;
                    result2.minUnit = null;
                } else {
                    result2.minUnit = TimeUnit.units[lastUnit];
                }
            }
            return result2;
        }

        short effectiveSet() {
            if (this.allowMillis) {
                return this.uset;
            }
            return (short)(this.uset & ~(1 << TimeUnit.MILLISECOND.ordinal));
        }

        TimeUnit effectiveMinUnit() {
            if (this.allowMillis || this.minUnit != TimeUnit.MILLISECOND) {
                return this.minUnit;
            }
            int i = TimeUnit.units.length - 1;
            while (--i >= 0) {
                if (0 == (this.uset & 1 << i)) continue;
                return TimeUnit.units[i];
            }
            return TimeUnit.SECOND;
        }

        Settings setMaxLimit(float maxLimit) {
            int val;
            int n = val = maxLimit <= 0.0f ? 0 : (int)(maxLimit * 1000.0f);
            if (maxLimit == (float)val) {
                return this;
            }
            Settings result2 = this.inUse ? this.copy() : this;
            result2.maxLimit = val;
            return result2;
        }

        Settings setMinLimit(float minLimit) {
            int val;
            int n = val = minLimit <= 0.0f ? 0 : (int)(minLimit * 1000.0f);
            if (minLimit == (float)val) {
                return this;
            }
            Settings result2 = this.inUse ? this.copy() : this;
            result2.minLimit = val;
            return result2;
        }

        Settings setAllowZero(boolean allow) {
            if (this.allowZero == allow) {
                return this;
            }
            Settings result2 = this.inUse ? this.copy() : this;
            result2.allowZero = allow;
            return result2;
        }

        Settings setWeeksAloneOnly(boolean weeksAlone) {
            if (this.weeksAloneOnly == weeksAlone) {
                return this;
            }
            Settings result2 = this.inUse ? this.copy() : this;
            result2.weeksAloneOnly = weeksAlone;
            return result2;
        }

        Settings setAllowMilliseconds(boolean allowMillis) {
            if (this.allowMillis == allowMillis) {
                return this;
            }
            Settings result2 = this.inUse ? this.copy() : this;
            result2.allowMillis = allowMillis;
            return result2;
        }

        Settings setLocale(String localeName) {
            PeriodFormatterData data2 = BasicPeriodBuilderFactory.this.ds.get(localeName);
            return this.setAllowZero(data2.allowZero()).setWeeksAloneOnly(data2.weeksAloneOnly()).setAllowMilliseconds(data2.useMilliseconds() != 1);
        }

        Settings setInUse() {
            this.inUse = true;
            return this;
        }

        Period createLimited(long duration, boolean inPast) {
            long maxUnitDuration;
            if (this.maxLimit > 0 && duration * 1000L > (long)this.maxLimit * (maxUnitDuration = BasicPeriodBuilderFactory.approximateDurationOf(this.maxUnit))) {
                return Period.moreThan((float)this.maxLimit / 1000.0f, this.maxUnit).inPast(inPast);
            }
            if (this.minLimit > 0) {
                long eml;
                TimeUnit emu = this.effectiveMinUnit();
                long emud = BasicPeriodBuilderFactory.approximateDurationOf(emu);
                long l = eml = emu == this.minUnit ? (long)this.minLimit : Math.max(1000L, BasicPeriodBuilderFactory.approximateDurationOf(this.minUnit) * (long)this.minLimit / emud);
                if (duration * 1000L < eml * emud) {
                    return Period.lessThan((float)eml / 1000.0f, emu).inPast(inPast);
                }
            }
            return null;
        }

        public Settings copy() {
            Settings result2 = new Settings();
            result2.inUse = this.inUse;
            result2.uset = this.uset;
            result2.maxUnit = this.maxUnit;
            result2.minUnit = this.minUnit;
            result2.maxLimit = this.maxLimit;
            result2.minLimit = this.minLimit;
            result2.allowZero = this.allowZero;
            result2.weeksAloneOnly = this.weeksAloneOnly;
            result2.allowMillis = this.allowMillis;
            return result2;
        }
    }
}

