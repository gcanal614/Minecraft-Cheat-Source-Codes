/*
 * Decompiled with CFR 0.152.
 */
package com.ibm.icu.text;

import com.ibm.icu.text.NFRuleSet;
import com.ibm.icu.text.NFSubstitution;
import com.ibm.icu.text.RuleBasedNumberFormat;

class AbsoluteValueSubstitution
extends NFSubstitution {
    AbsoluteValueSubstitution(int pos, NFRuleSet ruleSet, RuleBasedNumberFormat formatter, String description2) {
        super(pos, ruleSet, formatter, description2);
    }

    public long transformNumber(long number) {
        return Math.abs(number);
    }

    public double transformNumber(double number) {
        return Math.abs(number);
    }

    public double composeRuleValue(double newRuleValue, double oldRuleValue) {
        return -newRuleValue;
    }

    public double calcUpperBound(double oldUpperBound) {
        return Double.MAX_VALUE;
    }

    char tokenChar() {
        return '>';
    }
}

