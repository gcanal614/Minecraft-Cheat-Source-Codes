/*
 * Decompiled with CFR 0.152.
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.text.NFRuleSet;
import com.ibm.icu.text.NFSubstitution;
import com.ibm.icu.text.RbnfLenientScanner;
import com.ibm.icu.text.RuleBasedNumberFormat;
import java.text.ParsePosition;

final class NFRule {
    public static final int NEGATIVE_NUMBER_RULE = -1;
    public static final int IMPROPER_FRACTION_RULE = -2;
    public static final int PROPER_FRACTION_RULE = -3;
    public static final int MASTER_RULE = -4;
    private long baseValue;
    private int radix = 10;
    private short exponent = 0;
    private String ruleText = null;
    private NFSubstitution sub1 = null;
    private NFSubstitution sub2 = null;
    private RuleBasedNumberFormat formatter = null;

    public static Object makeRules(String description2, NFRuleSet owner, NFRule predecessor, RuleBasedNumberFormat ownersOwner) {
        NFRule rule1 = new NFRule(ownersOwner);
        description2 = rule1.parseRuleDescriptor(description2);
        int brack1 = description2.indexOf("[");
        int brack2 = description2.indexOf("]");
        if (brack1 == -1 || brack2 == -1 || brack1 > brack2 || rule1.getBaseValue() == -3L || rule1.getBaseValue() == -1L) {
            rule1.ruleText = description2;
            rule1.extractSubstitutions(owner, predecessor, ownersOwner);
            return rule1;
        }
        NFRule rule2 = null;
        StringBuilder sbuf = new StringBuilder();
        if (rule1.baseValue > 0L && (double)rule1.baseValue % Math.pow(rule1.radix, rule1.exponent) == 0.0 || rule1.baseValue == -2L || rule1.baseValue == -4L) {
            rule2 = new NFRule(ownersOwner);
            if (rule1.baseValue >= 0L) {
                rule2.baseValue = rule1.baseValue++;
                if (!owner.isFractionSet()) {
                    // empty if block
                }
            } else if (rule1.baseValue == -2L) {
                rule2.baseValue = -3L;
            } else if (rule1.baseValue == -4L) {
                rule2.baseValue = rule1.baseValue;
                rule1.baseValue = -2L;
            }
            rule2.radix = rule1.radix;
            rule2.exponent = rule1.exponent;
            sbuf.append(description2.substring(0, brack1));
            if (brack2 + 1 < description2.length()) {
                sbuf.append(description2.substring(brack2 + 1));
            }
            rule2.ruleText = sbuf.toString();
            rule2.extractSubstitutions(owner, predecessor, ownersOwner);
        }
        sbuf.setLength(0);
        sbuf.append(description2.substring(0, brack1));
        sbuf.append(description2.substring(brack1 + 1, brack2));
        if (brack2 + 1 < description2.length()) {
            sbuf.append(description2.substring(brack2 + 1));
        }
        rule1.ruleText = sbuf.toString();
        rule1.extractSubstitutions(owner, predecessor, ownersOwner);
        if (rule2 == null) {
            return rule1;
        }
        return new NFRule[]{rule2, rule1};
    }

    public NFRule(RuleBasedNumberFormat formatter) {
        this.formatter = formatter;
    }

    private String parseRuleDescriptor(String description2) {
        int p = description2.indexOf(":");
        if (p == -1) {
            this.setBaseValue(0L);
        } else {
            String descriptor2 = description2.substring(0, p);
            ++p;
            while (p < description2.length() && PatternProps.isWhiteSpace(description2.charAt(p))) {
                ++p;
            }
            description2 = description2.substring(p);
            if (descriptor2.equals("-x")) {
                this.setBaseValue(-1L);
            } else if (descriptor2.equals("x.x")) {
                this.setBaseValue(-2L);
            } else if (descriptor2.equals("0.x")) {
                this.setBaseValue(-3L);
            } else if (descriptor2.equals("x.0")) {
                this.setBaseValue(-4L);
            } else if (descriptor2.charAt(0) >= '0' && descriptor2.charAt(0) <= '9') {
                StringBuilder tempValue = new StringBuilder();
                char c = ' ';
                for (p = 0; p < descriptor2.length(); ++p) {
                    c = descriptor2.charAt(p);
                    if (c >= '0' && c <= '9') {
                        tempValue.append(c);
                        continue;
                    }
                    if (c == 47 || c == '>') break;
                    if (PatternProps.isWhiteSpace(c) || c == ',' || c == '.') continue;
                    throw new IllegalArgumentException("Illegal character in rule descriptor");
                }
                this.setBaseValue(Long.parseLong(tempValue.toString()));
                if (c == '/') {
                    tempValue.setLength(0);
                    ++p;
                    while (p < descriptor2.length()) {
                        c = descriptor2.charAt(p);
                        if (c >= '0' && c <= '9') {
                            tempValue.append(c);
                        } else {
                            if (c == '>') break;
                            if (!PatternProps.isWhiteSpace(c) && c != ',' && c != '.') {
                                throw new IllegalArgumentException("Illegal character is rule descriptor");
                            }
                        }
                        ++p;
                    }
                    this.radix = Integer.parseInt(tempValue.toString());
                    if (this.radix == 0) {
                        throw new IllegalArgumentException("Rule can't have radix of 0");
                    }
                    this.exponent = this.expectedExponent();
                }
                if (c == '>') {
                    while (p < descriptor2.length()) {
                        c = descriptor2.charAt(p);
                        if (c != '>' || this.exponent <= 0) {
                            throw new IllegalArgumentException("Illegal character in rule descriptor");
                        }
                        this.exponent = (short)(this.exponent - 1);
                        ++p;
                    }
                }
            }
        }
        if (description2.length() > 0 && description2.charAt(0) == '\'') {
            description2 = description2.substring(1);
        }
        return description2;
    }

    private void extractSubstitutions(NFRuleSet owner, NFRule predecessor, RuleBasedNumberFormat ownersOwner) {
        this.sub1 = this.extractSubstitution(owner, predecessor, ownersOwner);
        this.sub2 = this.extractSubstitution(owner, predecessor, ownersOwner);
    }

    private NFSubstitution extractSubstitution(NFRuleSet owner, NFRule predecessor, RuleBasedNumberFormat ownersOwner) {
        int subEnd;
        NFSubstitution result2 = null;
        int subStart = this.indexOfAny(new String[]{"<<", "<%", "<#", "<0", ">>", ">%", ">#", ">0", "=%", "=#", "=0"});
        if (subStart == -1) {
            return NFSubstitution.makeSubstitution(this.ruleText.length(), this, predecessor, owner, ownersOwner, "");
        }
        if (this.ruleText.substring(subStart).startsWith(">>>")) {
            subEnd = subStart + 2;
        } else {
            char c = this.ruleText.charAt(subStart);
            subEnd = this.ruleText.indexOf(c, subStart + 1);
            if (c == '<' && subEnd != -1 && subEnd < this.ruleText.length() - 1 && this.ruleText.charAt(subEnd + 1) == c) {
                ++subEnd;
            }
        }
        if (subEnd == -1) {
            return NFSubstitution.makeSubstitution(this.ruleText.length(), this, predecessor, owner, ownersOwner, "");
        }
        result2 = NFSubstitution.makeSubstitution(subStart, this, predecessor, owner, ownersOwner, this.ruleText.substring(subStart, subEnd + 1));
        this.ruleText = this.ruleText.substring(0, subStart) + this.ruleText.substring(subEnd + 1);
        return result2;
    }

    public final void setBaseValue(long newBaseValue) {
        this.baseValue = newBaseValue;
        if (this.baseValue >= 1L) {
            this.radix = 10;
            this.exponent = this.expectedExponent();
            if (this.sub1 != null) {
                this.sub1.setDivisor(this.radix, this.exponent);
            }
            if (this.sub2 != null) {
                this.sub2.setDivisor(this.radix, this.exponent);
            }
        } else {
            this.radix = 10;
            this.exponent = 0;
        }
    }

    private short expectedExponent() {
        if (this.radix == 0 || this.baseValue < 1L) {
            return 0;
        }
        short tempResult = (short)(Math.log(this.baseValue) / Math.log(this.radix));
        if (Math.pow(this.radix, tempResult + 1) <= (double)this.baseValue) {
            return (short)(tempResult + 1);
        }
        return tempResult;
    }

    private int indexOfAny(String[] strings) {
        int result2 = -1;
        for (int i = 0; i < strings.length; ++i) {
            int pos = this.ruleText.indexOf(strings[i]);
            if (pos == -1 || result2 != -1 && pos >= result2) continue;
            result2 = pos;
        }
        return result2;
    }

    public boolean equals(Object that) {
        if (that instanceof NFRule) {
            NFRule that2 = (NFRule)that;
            return this.baseValue == that2.baseValue && this.radix == that2.radix && this.exponent == that2.exponent && this.ruleText.equals(that2.ruleText) && this.sub1.equals(that2.sub1) && this.sub2.equals(that2.sub2);
        }
        return false;
    }

    public int hashCode() {
        assert (false) : "hashCode not designed";
        return 42;
    }

    public String toString() {
        StringBuilder result2 = new StringBuilder();
        if (this.baseValue == -1L) {
            result2.append("-x: ");
        } else if (this.baseValue == -2L) {
            result2.append("x.x: ");
        } else if (this.baseValue == -3L) {
            result2.append("0.x: ");
        } else if (this.baseValue == -4L) {
            result2.append("x.0: ");
        } else {
            result2.append(String.valueOf(this.baseValue));
            if (this.radix != 10) {
                result2.append('/');
                result2.append(String.valueOf(this.radix));
            }
            int numCarets = this.expectedExponent() - this.exponent;
            for (int i = 0; i < numCarets; ++i) {
                result2.append('>');
            }
            result2.append(": ");
        }
        if (this.ruleText.startsWith(" ") && (this.sub1 == null || this.sub1.getPos() != 0)) {
            result2.append("'");
        }
        StringBuilder ruleTextCopy = new StringBuilder(this.ruleText);
        ruleTextCopy.insert(this.sub2.getPos(), this.sub2.toString());
        ruleTextCopy.insert(this.sub1.getPos(), this.sub1.toString());
        result2.append(ruleTextCopy.toString());
        result2.append(';');
        return result2.toString();
    }

    public final long getBaseValue() {
        return this.baseValue;
    }

    public double getDivisor() {
        return Math.pow(this.radix, this.exponent);
    }

    public void doFormat(long number, StringBuffer toInsertInto, int pos) {
        toInsertInto.insert(pos, this.ruleText);
        this.sub2.doSubstitution(number, toInsertInto, pos);
        this.sub1.doSubstitution(number, toInsertInto, pos);
    }

    public void doFormat(double number, StringBuffer toInsertInto, int pos) {
        toInsertInto.insert(pos, this.ruleText);
        this.sub2.doSubstitution(number, toInsertInto, pos);
        this.sub1.doSubstitution(number, toInsertInto, pos);
    }

    public boolean shouldRollBack(double number) {
        if (this.sub1.isModulusSubstitution() || this.sub2.isModulusSubstitution()) {
            return number % Math.pow(this.radix, this.exponent) == 0.0 && (double)this.baseValue % Math.pow(this.radix, this.exponent) != 0.0;
        }
        return false;
    }

    public Number doParse(String text, ParsePosition parsePosition, boolean isFractionRule, double upperBound) {
        ParsePosition pp = new ParsePosition(0);
        String workText = this.stripPrefix(text, this.ruleText.substring(0, this.sub1.getPos()), pp);
        int prefixLength = text.length() - workText.length();
        if (pp.getIndex() == 0 && this.sub1.getPos() != 0) {
            return 0L;
        }
        int highWaterMark = 0;
        double result2 = 0.0;
        int start = 0;
        double tempBaseValue = Math.max(0L, this.baseValue);
        do {
            pp.setIndex(0);
            double partialResult = this.matchToDelimiter(workText, start, tempBaseValue, this.ruleText.substring(this.sub1.getPos(), this.sub2.getPos()), pp, this.sub1, upperBound).doubleValue();
            if (pp.getIndex() == 0 && !this.sub1.isNullSubstitution()) continue;
            start = pp.getIndex();
            String workText2 = workText.substring(pp.getIndex());
            ParsePosition pp2 = new ParsePosition(0);
            partialResult = this.matchToDelimiter(workText2, 0, partialResult, this.ruleText.substring(this.sub2.getPos()), pp2, this.sub2, upperBound).doubleValue();
            if (pp2.getIndex() == 0 && !this.sub2.isNullSubstitution() || prefixLength + pp.getIndex() + pp2.getIndex() <= highWaterMark) continue;
            highWaterMark = prefixLength + pp.getIndex() + pp2.getIndex();
            result2 = partialResult;
        } while (this.sub1.getPos() != this.sub2.getPos() && pp.getIndex() > 0 && pp.getIndex() < workText.length() && pp.getIndex() != start);
        parsePosition.setIndex(highWaterMark);
        if (isFractionRule && highWaterMark > 0 && this.sub1.isNullSubstitution()) {
            result2 = 1.0 / result2;
        }
        if (result2 == (double)((long)result2)) {
            return (long)result2;
        }
        return new Double(result2);
    }

    private String stripPrefix(String text, String prefix, ParsePosition pp) {
        if (prefix.length() == 0) {
            return text;
        }
        int pfl = this.prefixLength(text, prefix);
        if (pfl != 0) {
            pp.setIndex(pp.getIndex() + pfl);
            return text.substring(pfl);
        }
        return text;
    }

    private Number matchToDelimiter(String text, int startPos, double baseVal, String delimiter, ParsePosition pp, NFSubstitution sub, double upperBound) {
        if (!this.allIgnorable(delimiter)) {
            ParsePosition tempPP = new ParsePosition(0);
            int[] temp = this.findText(text, delimiter, startPos);
            int dPos = temp[0];
            int dLen = temp[1];
            while (dPos >= 0) {
                String subText = text.substring(0, dPos);
                if (subText.length() > 0) {
                    Number tempResult = sub.doParse(subText, tempPP, baseVal, upperBound, this.formatter.lenientParseEnabled());
                    if (tempPP.getIndex() == dPos) {
                        pp.setIndex(dPos + dLen);
                        return tempResult;
                    }
                }
                tempPP.setIndex(0);
                temp = this.findText(text, delimiter, dPos + dLen);
                dPos = temp[0];
                dLen = temp[1];
            }
            pp.setIndex(0);
            return 0L;
        }
        ParsePosition tempPP = new ParsePosition(0);
        Number result2 = 0L;
        Number tempResult = sub.doParse(text, tempPP, baseVal, upperBound, this.formatter.lenientParseEnabled());
        if (tempPP.getIndex() != 0 || sub.isNullSubstitution()) {
            pp.setIndex(tempPP.getIndex());
            if (tempResult != null) {
                result2 = tempResult;
            }
        }
        return result2;
    }

    private int prefixLength(String str, String prefix) {
        if (prefix.length() == 0) {
            return 0;
        }
        RbnfLenientScanner scanner = this.formatter.getLenientScanner();
        if (scanner != null) {
            return scanner.prefixLength(str, prefix);
        }
        if (str.startsWith(prefix)) {
            return prefix.length();
        }
        return 0;
    }

    private int[] findText(String str, String key, int startingAt) {
        RbnfLenientScanner scanner = this.formatter.getLenientScanner();
        if (scanner == null) {
            return new int[]{str.indexOf(key, startingAt), key.length()};
        }
        return scanner.findText(str, key, startingAt);
    }

    private boolean allIgnorable(String str) {
        if (str.length() == 0) {
            return true;
        }
        RbnfLenientScanner scanner = this.formatter.getLenientScanner();
        if (scanner != null) {
            return scanner.allIgnorable(str);
        }
        return false;
    }
}

