/*
 * Decompiled with CFR 0.152.
 */
package com.ibm.icu.text;

import com.ibm.icu.text.AbsoluteValueSubstitution;
import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.FractionalPartSubstitution;
import com.ibm.icu.text.IntegralPartSubstitution;
import com.ibm.icu.text.ModulusSubstitution;
import com.ibm.icu.text.MultiplierSubstitution;
import com.ibm.icu.text.NFRule;
import com.ibm.icu.text.NFRuleSet;
import com.ibm.icu.text.NullSubstitution;
import com.ibm.icu.text.NumeratorSubstitution;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.text.SameValueSubstitution;
import java.text.ParsePosition;

abstract class NFSubstitution {
    int pos;
    NFRuleSet ruleSet = null;
    DecimalFormat numberFormat = null;
    RuleBasedNumberFormat rbnf = null;

    public static NFSubstitution makeSubstitution(int pos, NFRule rule, NFRule rulePredecessor, NFRuleSet ruleSet, RuleBasedNumberFormat formatter, String description2) {
        if (description2.length() == 0) {
            return new NullSubstitution(pos, ruleSet, formatter, description2);
        }
        switch (description2.charAt(0)) {
            case '<': {
                if (rule.getBaseValue() == -1L) {
                    throw new IllegalArgumentException("<< not allowed in negative-number rule");
                }
                if (rule.getBaseValue() == -2L || rule.getBaseValue() == -3L || rule.getBaseValue() == -4L) {
                    return new IntegralPartSubstitution(pos, ruleSet, formatter, description2);
                }
                if (ruleSet.isFractionSet()) {
                    return new NumeratorSubstitution(pos, rule.getBaseValue(), formatter.getDefaultRuleSet(), formatter, description2);
                }
                return new MultiplierSubstitution(pos, rule.getDivisor(), ruleSet, formatter, description2);
            }
            case '>': {
                if (rule.getBaseValue() == -1L) {
                    return new AbsoluteValueSubstitution(pos, ruleSet, formatter, description2);
                }
                if (rule.getBaseValue() == -2L || rule.getBaseValue() == -3L || rule.getBaseValue() == -4L) {
                    return new FractionalPartSubstitution(pos, ruleSet, formatter, description2);
                }
                if (ruleSet.isFractionSet()) {
                    throw new IllegalArgumentException(">> not allowed in fraction rule set");
                }
                return new ModulusSubstitution(pos, rule.getDivisor(), rulePredecessor, ruleSet, formatter, description2);
            }
            case '=': {
                return new SameValueSubstitution(pos, ruleSet, formatter, description2);
            }
        }
        throw new IllegalArgumentException("Illegal substitution character");
    }

    NFSubstitution(int pos, NFRuleSet ruleSet, RuleBasedNumberFormat formatter, String description2) {
        this.pos = pos;
        this.rbnf = formatter;
        if (description2.length() >= 2 && description2.charAt(0) == description2.charAt(description2.length() - 1)) {
            description2 = description2.substring(1, description2.length() - 1);
        } else if (description2.length() != 0) {
            throw new IllegalArgumentException("Illegal substitution syntax");
        }
        if (description2.length() == 0) {
            this.ruleSet = ruleSet;
        } else if (description2.charAt(0) == '%') {
            this.ruleSet = formatter.findRuleSet(description2);
        } else if (description2.charAt(0) == '#' || description2.charAt(0) == '0') {
            this.numberFormat = new DecimalFormat(description2);
            this.numberFormat.setDecimalFormatSymbols(formatter.getDecimalFormatSymbols());
        } else if (description2.charAt(0) == '>') {
            this.ruleSet = ruleSet;
            this.numberFormat = null;
        } else {
            throw new IllegalArgumentException("Illegal substitution syntax");
        }
    }

    public void setDivisor(int radix, int exponent) {
    }

    public boolean equals(Object that) {
        if (that == null) {
            return false;
        }
        if (this == that) {
            return true;
        }
        if (this.getClass() == that.getClass()) {
            NFSubstitution that2 = (NFSubstitution)that;
            return this.pos == that2.pos && (this.ruleSet != null || that2.ruleSet == null) && (this.numberFormat == null ? that2.numberFormat == null : this.numberFormat.equals(that2.numberFormat));
        }
        return false;
    }

    public int hashCode() {
        assert (false) : "hashCode not designed";
        return 42;
    }

    public String toString() {
        if (this.ruleSet != null) {
            return this.tokenChar() + this.ruleSet.getName() + this.tokenChar();
        }
        return this.tokenChar() + this.numberFormat.toPattern() + this.tokenChar();
    }

    public void doSubstitution(long number, StringBuffer toInsertInto, int position) {
        if (this.ruleSet != null) {
            long numberToFormat = this.transformNumber(number);
            this.ruleSet.format(numberToFormat, toInsertInto, position + this.pos);
        } else {
            double numberToFormat = this.transformNumber((double)number);
            if (this.numberFormat.getMaximumFractionDigits() == 0) {
                numberToFormat = Math.floor(numberToFormat);
            }
            toInsertInto.insert(position + this.pos, this.numberFormat.format(numberToFormat));
        }
    }

    public void doSubstitution(double number, StringBuffer toInsertInto, int position) {
        double numberToFormat = this.transformNumber(number);
        if (numberToFormat == Math.floor(numberToFormat) && this.ruleSet != null) {
            this.ruleSet.format((long)numberToFormat, toInsertInto, position + this.pos);
        } else if (this.ruleSet != null) {
            this.ruleSet.format(numberToFormat, toInsertInto, position + this.pos);
        } else {
            toInsertInto.insert(position + this.pos, this.numberFormat.format(numberToFormat));
        }
    }

    public abstract long transformNumber(long var1);

    public abstract double transformNumber(double var1);

    public Number doParse(String text, ParsePosition parsePosition, double baseValue, double upperBound, boolean lenientParse) {
        Number tempResult;
        upperBound = this.calcUpperBound(upperBound);
        if (this.ruleSet != null) {
            tempResult = this.ruleSet.parse(text, parsePosition, upperBound);
            if (lenientParse && !this.ruleSet.isFractionSet() && parsePosition.getIndex() == 0) {
                tempResult = this.rbnf.getDecimalFormat().parse(text, parsePosition);
            }
        } else {
            tempResult = this.numberFormat.parse(text, parsePosition);
        }
        if (parsePosition.getIndex() != 0) {
            double result2 = tempResult.doubleValue();
            if ((result2 = this.composeRuleValue(result2, baseValue)) == (double)((long)result2)) {
                return (long)result2;
            }
            return new Double(result2);
        }
        return tempResult;
    }

    public abstract double composeRuleValue(double var1, double var3);

    public abstract double calcUpperBound(double var1);

    public final int getPos() {
        return this.pos;
    }

    abstract char tokenChar();

    public boolean isNullSubstitution() {
        return false;
    }

    public boolean isModulusSubstitution() {
        return false;
    }
}

