/*
 * Decompiled with CFR 0.152.
 */
package com.ibm.icu.text;

import com.ibm.icu.text.NFRule;
import com.ibm.icu.text.NFRuleSet;
import com.ibm.icu.text.NFSubstitution;
import com.ibm.icu.text.RuleBasedNumberFormat;
import java.text.ParsePosition;

class ModulusSubstitution
extends NFSubstitution {
    double divisor;
    NFRule ruleToUse;

    ModulusSubstitution(int pos, double divisor, NFRule rulePredecessor, NFRuleSet ruleSet, RuleBasedNumberFormat formatter, String description2) {
        super(pos, ruleSet, formatter, description2);
        this.divisor = divisor;
        if (divisor == 0.0) {
            throw new IllegalStateException("Substitution with bad divisor (" + divisor + ") " + description2.substring(0, pos) + " | " + description2.substring(pos));
        }
        this.ruleToUse = description2.equals(">>>") ? rulePredecessor : null;
    }

    public void setDivisor(int radix, int exponent) {
        this.divisor = Math.pow(radix, exponent);
        if (this.divisor == 0.0) {
            throw new IllegalStateException("Substitution with bad divisor");
        }
    }

    public boolean equals(Object that) {
        if (super.equals(that)) {
            ModulusSubstitution that2 = (ModulusSubstitution)that;
            return this.divisor == that2.divisor;
        }
        return false;
    }

    public int hashCode() {
        assert (false) : "hashCode not designed";
        return 42;
    }

    public void doSubstitution(long number, StringBuffer toInsertInto, int position) {
        if (this.ruleToUse == null) {
            super.doSubstitution(number, toInsertInto, position);
        } else {
            long numberToFormat = this.transformNumber(number);
            this.ruleToUse.doFormat(numberToFormat, toInsertInto, position + this.pos);
        }
    }

    public void doSubstitution(double number, StringBuffer toInsertInto, int position) {
        if (this.ruleToUse == null) {
            super.doSubstitution(number, toInsertInto, position);
        } else {
            double numberToFormat = this.transformNumber(number);
            this.ruleToUse.doFormat(numberToFormat, toInsertInto, position + this.pos);
        }
    }

    public long transformNumber(long number) {
        return (long)Math.floor((double)number % this.divisor);
    }

    public double transformNumber(double number) {
        return Math.floor(number % this.divisor);
    }

    public Number doParse(String text, ParsePosition parsePosition, double baseValue, double upperBound, boolean lenientParse) {
        if (this.ruleToUse == null) {
            return super.doParse(text, parsePosition, baseValue, upperBound, lenientParse);
        }
        Number tempResult = this.ruleToUse.doParse(text, parsePosition, false, upperBound);
        if (parsePosition.getIndex() != 0) {
            double result2 = tempResult.doubleValue();
            if ((result2 = this.composeRuleValue(result2, baseValue)) == (double)((long)result2)) {
                return (long)result2;
            }
            return new Double(result2);
        }
        return tempResult;
    }

    public double composeRuleValue(double newRuleValue, double oldRuleValue) {
        return oldRuleValue - oldRuleValue % this.divisor + newRuleValue;
    }

    public double calcUpperBound(double oldUpperBound) {
        return this.divisor;
    }

    public boolean isModulusSubstitution() {
        return true;
    }

    char tokenChar() {
        return '>';
    }
}

