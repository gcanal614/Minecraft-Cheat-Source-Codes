/*
 * Decompiled with CFR 0.152.
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.NFRule;
import com.ibm.icu.text.RuleBasedNumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;

final class NFRuleSet {
    private String name;
    private NFRule[] rules;
    private NFRule negativeNumberRule = null;
    private NFRule[] fractionRules = new NFRule[3];
    private boolean isFractionRuleSet = false;
    private boolean isParseable = true;
    private int recursionCount = 0;
    private static final int RECURSION_LIMIT = 50;

    public NFRuleSet(String[] descriptions, int index) throws IllegalArgumentException {
        String description2 = descriptions[index];
        if (description2.length() == 0) {
            throw new IllegalArgumentException("Empty rule set description");
        }
        if (description2.charAt(0) == '%') {
            int pos = description2.indexOf(58);
            if (pos == -1) {
                throw new IllegalArgumentException("Rule set name doesn't end in colon");
            }
            this.name = description2.substring(0, pos);
            while (pos < description2.length() && PatternProps.isWhiteSpace(description2.charAt(++pos))) {
            }
            descriptions[index] = description2 = description2.substring(pos);
        } else {
            this.name = "%default";
        }
        if (description2.length() == 0) {
            throw new IllegalArgumentException("Empty rule set description");
        }
        if (this.name.endsWith("@noparse")) {
            this.name = this.name.substring(0, this.name.length() - 8);
            this.isParseable = false;
        }
    }

    public void parseRules(String description2, RuleBasedNumberFormat owner) {
        ArrayList<String> ruleDescriptions = new ArrayList<String>();
        int oldP = 0;
        int p = description2.indexOf(59);
        while (oldP != -1) {
            if (p != -1) {
                ruleDescriptions.add(description2.substring(oldP, p));
                oldP = p + 1;
            } else {
                if (oldP < description2.length()) {
                    ruleDescriptions.add(description2.substring(oldP));
                }
                oldP = p;
            }
            p = description2.indexOf(59, p + 1);
        }
        ArrayList<NFRule> tempRules = new ArrayList<NFRule>();
        NFRule predecessor = null;
        for (int i = 0; i < ruleDescriptions.size(); ++i) {
            Object temp = NFRule.makeRules((String)ruleDescriptions.get(i), this, predecessor, owner);
            if (temp instanceof NFRule) {
                tempRules.add((NFRule)temp);
                predecessor = (NFRule)temp;
                continue;
            }
            if (!(temp instanceof NFRule[])) continue;
            NFRule[] rulesToAdd = (NFRule[])temp;
            for (int j = 0; j < rulesToAdd.length; ++j) {
                tempRules.add(rulesToAdd[j]);
                predecessor = rulesToAdd[j];
            }
        }
        ruleDescriptions = null;
        long defaultBaseValue = 0L;
        int i = 0;
        block10: while (i < tempRules.size()) {
            NFRule rule = (NFRule)tempRules.get(i);
            switch ((int)rule.getBaseValue()) {
                case 0: {
                    rule.setBaseValue(defaultBaseValue);
                    if (!this.isFractionRuleSet) {
                        ++defaultBaseValue;
                    }
                    ++i;
                    continue block10;
                }
                case -1: {
                    this.negativeNumberRule = rule;
                    tempRules.remove(i);
                    continue block10;
                }
                case -2: {
                    this.fractionRules[0] = rule;
                    tempRules.remove(i);
                    continue block10;
                }
                case -3: {
                    this.fractionRules[1] = rule;
                    tempRules.remove(i);
                    continue block10;
                }
                case -4: {
                    this.fractionRules[2] = rule;
                    tempRules.remove(i);
                    continue block10;
                }
            }
            if (rule.getBaseValue() < defaultBaseValue) {
                throw new IllegalArgumentException("Rules are not in order, base: " + rule.getBaseValue() + " < " + defaultBaseValue);
            }
            defaultBaseValue = rule.getBaseValue();
            if (!this.isFractionRuleSet) {
                ++defaultBaseValue;
            }
            ++i;
        }
        this.rules = new NFRule[tempRules.size()];
        tempRules.toArray(this.rules);
    }

    public void makeIntoFractionRuleSet() {
        this.isFractionRuleSet = true;
    }

    public boolean equals(Object that) {
        if (!(that instanceof NFRuleSet)) {
            return false;
        }
        NFRuleSet that2 = (NFRuleSet)that;
        if (!(this.name.equals(that2.name) && Utility.objectEquals(this.negativeNumberRule, that2.negativeNumberRule) && Utility.objectEquals(this.fractionRules[0], that2.fractionRules[0]) && Utility.objectEquals(this.fractionRules[1], that2.fractionRules[1]) && Utility.objectEquals(this.fractionRules[2], that2.fractionRules[2]) && this.rules.length == that2.rules.length && this.isFractionRuleSet == that2.isFractionRuleSet)) {
            return false;
        }
        for (int i = 0; i < this.rules.length; ++i) {
            if (this.rules[i].equals(that2.rules[i])) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        assert (false) : "hashCode not designed";
        return 42;
    }

    public String toString() {
        StringBuilder result2 = new StringBuilder();
        result2.append(this.name + ":\n");
        for (int i = 0; i < this.rules.length; ++i) {
            result2.append("    " + this.rules[i].toString() + "\n");
        }
        if (this.negativeNumberRule != null) {
            result2.append("    " + this.negativeNumberRule.toString() + "\n");
        }
        if (this.fractionRules[0] != null) {
            result2.append("    " + this.fractionRules[0].toString() + "\n");
        }
        if (this.fractionRules[1] != null) {
            result2.append("    " + this.fractionRules[1].toString() + "\n");
        }
        if (this.fractionRules[2] != null) {
            result2.append("    " + this.fractionRules[2].toString() + "\n");
        }
        return result2.toString();
    }

    public boolean isFractionSet() {
        return this.isFractionRuleSet;
    }

    public String getName() {
        return this.name;
    }

    public boolean isPublic() {
        return !this.name.startsWith("%%");
    }

    public boolean isParseable() {
        return this.isParseable;
    }

    public void format(long number, StringBuffer toInsertInto, int pos) {
        NFRule applicableRule = this.findNormalRule(number);
        if (++this.recursionCount >= 50) {
            this.recursionCount = 0;
            throw new IllegalStateException("Recursion limit exceeded when applying ruleSet " + this.name);
        }
        applicableRule.doFormat(number, toInsertInto, pos);
        --this.recursionCount;
    }

    public void format(double number, StringBuffer toInsertInto, int pos) {
        NFRule applicableRule = this.findRule(number);
        if (++this.recursionCount >= 50) {
            this.recursionCount = 0;
            throw new IllegalStateException("Recursion limit exceeded when applying ruleSet " + this.name);
        }
        applicableRule.doFormat(number, toInsertInto, pos);
        --this.recursionCount;
    }

    private NFRule findRule(double number) {
        if (this.isFractionRuleSet) {
            return this.findFractionRuleSetRule(number);
        }
        if (number < 0.0) {
            if (this.negativeNumberRule != null) {
                return this.negativeNumberRule;
            }
            number = -number;
        }
        if (number != Math.floor(number)) {
            if (number < 1.0 && this.fractionRules[1] != null) {
                return this.fractionRules[1];
            }
            if (this.fractionRules[0] != null) {
                return this.fractionRules[0];
            }
        }
        if (this.fractionRules[2] != null) {
            return this.fractionRules[2];
        }
        return this.findNormalRule(Math.round(number));
    }

    private NFRule findNormalRule(long number) {
        if (this.isFractionRuleSet) {
            return this.findFractionRuleSetRule(number);
        }
        if (number < 0L) {
            if (this.negativeNumberRule != null) {
                return this.negativeNumberRule;
            }
            number = -number;
        }
        int lo = 0;
        int hi = this.rules.length;
        if (hi > 0) {
            while (lo < hi) {
                int mid = lo + hi >>> 1;
                if (this.rules[mid].getBaseValue() == number) {
                    return this.rules[mid];
                }
                if (this.rules[mid].getBaseValue() > number) {
                    hi = mid;
                    continue;
                }
                lo = mid + 1;
            }
            if (hi == 0) {
                throw new IllegalStateException("The rule set " + this.name + " cannot format the value " + number);
            }
            NFRule result2 = this.rules[hi - 1];
            if (result2.shouldRollBack(number)) {
                if (hi == 1) {
                    throw new IllegalStateException("The rule set " + this.name + " cannot roll back from the rule '" + result2 + "'");
                }
                result2 = this.rules[hi - 2];
            }
            return result2;
        }
        return this.fractionRules[2];
    }

    private NFRule findFractionRuleSetRule(double number) {
        long leastCommonMultiple = this.rules[0].getBaseValue();
        for (int i = 1; i < this.rules.length; ++i) {
            leastCommonMultiple = NFRuleSet.lcm(leastCommonMultiple, this.rules[i].getBaseValue());
        }
        long numerator = Math.round(number * (double)leastCommonMultiple);
        long difference = Long.MAX_VALUE;
        int winner = 0;
        for (int i = 0; i < this.rules.length; ++i) {
            long tempDifference = numerator * this.rules[i].getBaseValue() % leastCommonMultiple;
            if (leastCommonMultiple - tempDifference < tempDifference) {
                tempDifference = leastCommonMultiple - tempDifference;
            }
            if (tempDifference >= difference) continue;
            difference = tempDifference;
            winner = i;
            if (difference == 0L) break;
        }
        if (winner + 1 < this.rules.length && this.rules[winner + 1].getBaseValue() == this.rules[winner].getBaseValue() && (Math.round(number * (double)this.rules[winner].getBaseValue()) < 1L || Math.round(number * (double)this.rules[winner].getBaseValue()) >= 2L)) {
            ++winner;
        }
        return this.rules[winner];
    }

    private static long lcm(long x, long y) {
        long x1 = x;
        long y1 = y;
        int p2 = 0;
        while ((x1 & 1L) == 0L && (y1 & 1L) == 0L) {
            ++p2;
            x1 >>= 1;
            y1 >>= 1;
        }
        long t = (x1 & 1L) == 1L ? -y1 : x1;
        while (t != 0L) {
            while ((t & 1L) == 0L) {
                t >>= 1;
            }
            if (t > 0L) {
                x1 = t;
            } else {
                y1 = -t;
            }
            t = x1 - y1;
        }
        long gcd = x1 << p2;
        return x / gcd * y;
    }

    public Number parse(String text, ParsePosition parsePosition, double upperBound) {
        int i;
        ParsePosition highWaterMark = new ParsePosition(0);
        Number result2 = 0L;
        Number tempResult = null;
        if (text.length() == 0) {
            return result2;
        }
        if (this.negativeNumberRule != null) {
            tempResult = this.negativeNumberRule.doParse(text, parsePosition, false, upperBound);
            if (parsePosition.getIndex() > highWaterMark.getIndex()) {
                result2 = tempResult;
                highWaterMark.setIndex(parsePosition.getIndex());
            }
            parsePosition.setIndex(0);
        }
        for (i = 0; i < 3; ++i) {
            if (this.fractionRules[i] == null) continue;
            tempResult = this.fractionRules[i].doParse(text, parsePosition, false, upperBound);
            if (parsePosition.getIndex() > highWaterMark.getIndex()) {
                result2 = tempResult;
                highWaterMark.setIndex(parsePosition.getIndex());
            }
            parsePosition.setIndex(0);
        }
        for (i = this.rules.length - 1; i >= 0 && highWaterMark.getIndex() < text.length(); --i) {
            if (!this.isFractionRuleSet && (double)this.rules[i].getBaseValue() >= upperBound) continue;
            tempResult = this.rules[i].doParse(text, parsePosition, this.isFractionRuleSet, upperBound);
            if (parsePosition.getIndex() > highWaterMark.getIndex()) {
                result2 = tempResult;
                highWaterMark.setIndex(parsePosition.getIndex());
            }
            parsePosition.setIndex(0);
        }
        parsePosition.setIndex(highWaterMark.getIndex());
        return result2;
    }
}

