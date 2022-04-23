/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class DoubleMetaphone
implements StringEncoder {
    private static final String VOWELS = "AEIOUY";
    private static final String[] SILENT_START = new String[]{"GN", "KN", "PN", "WR", "PS"};
    private static final String[] L_R_N_M_B_H_F_V_W_SPACE = new String[]{"L", "R", "N", "M", "B", "H", "F", "V", "W", " "};
    private static final String[] ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER = new String[]{"ES", "EP", "EB", "EL", "EY", "IB", "IL", "IN", "IE", "EI", "ER"};
    private static final String[] L_T_K_S_N_M_B_Z = new String[]{"L", "T", "K", "S", "N", "M", "B", "Z"};
    private int maxCodeLen = 4;

    public String doubleMetaphone(String value) {
        return this.doubleMetaphone(value, false);
    }

    public String doubleMetaphone(String value, boolean alternate) {
        if ((value = this.cleanInput(value)) == null) {
            return null;
        }
        boolean slavoGermanic = this.isSlavoGermanic(value);
        int index = this.isSilentStart(value) ? 1 : 0;
        DoubleMetaphoneResult result2 = new DoubleMetaphoneResult(this.getMaxCodeLen());
        block25: while (!result2.isComplete() && index <= value.length() - 1) {
            switch (value.charAt(index)) {
                case 'A': 
                case 'E': 
                case 'I': 
                case 'O': 
                case 'U': 
                case 'Y': {
                    index = this.handleAEIOUY(result2, index);
                    continue block25;
                }
                case 'B': {
                    result2.append('P');
                    index = this.charAt(value, index + 1) == 'B' ? index + 2 : index + 1;
                    continue block25;
                }
                case '\u00c7': {
                    result2.append('S');
                    ++index;
                    continue block25;
                }
                case 'C': {
                    index = this.handleC(value, result2, index);
                    continue block25;
                }
                case 'D': {
                    index = this.handleD(value, result2, index);
                    continue block25;
                }
                case 'F': {
                    result2.append('F');
                    index = this.charAt(value, index + 1) == 'F' ? index + 2 : index + 1;
                    continue block25;
                }
                case 'G': {
                    index = this.handleG(value, result2, index, slavoGermanic);
                    continue block25;
                }
                case 'H': {
                    index = this.handleH(value, result2, index);
                    continue block25;
                }
                case 'J': {
                    index = this.handleJ(value, result2, index, slavoGermanic);
                    continue block25;
                }
                case 'K': {
                    result2.append('K');
                    index = this.charAt(value, index + 1) == 'K' ? index + 2 : index + 1;
                    continue block25;
                }
                case 'L': {
                    index = this.handleL(value, result2, index);
                    continue block25;
                }
                case 'M': {
                    result2.append('M');
                    index = this.conditionM0(value, index) ? index + 2 : index + 1;
                    continue block25;
                }
                case 'N': {
                    result2.append('N');
                    index = this.charAt(value, index + 1) == 'N' ? index + 2 : index + 1;
                    continue block25;
                }
                case '\u00d1': {
                    result2.append('N');
                    ++index;
                    continue block25;
                }
                case 'P': {
                    index = this.handleP(value, result2, index);
                    continue block25;
                }
                case 'Q': {
                    result2.append('K');
                    index = this.charAt(value, index + 1) == 'Q' ? index + 2 : index + 1;
                    continue block25;
                }
                case 'R': {
                    index = this.handleR(value, result2, index, slavoGermanic);
                    continue block25;
                }
                case 'S': {
                    index = this.handleS(value, result2, index, slavoGermanic);
                    continue block25;
                }
                case 'T': {
                    index = this.handleT(value, result2, index);
                    continue block25;
                }
                case 'V': {
                    result2.append('F');
                    index = this.charAt(value, index + 1) == 'V' ? index + 2 : index + 1;
                    continue block25;
                }
                case 'W': {
                    index = this.handleW(value, result2, index);
                    continue block25;
                }
                case 'X': {
                    index = this.handleX(value, result2, index);
                    continue block25;
                }
                case 'Z': {
                    index = this.handleZ(value, result2, index, slavoGermanic);
                    continue block25;
                }
            }
            ++index;
        }
        return alternate ? result2.getAlternate() : result2.getPrimary();
    }

    @Override
    public Object encode(Object obj) throws EncoderException {
        if (!(obj instanceof String)) {
            throw new EncoderException("DoubleMetaphone encode parameter is not of type String");
        }
        return this.doubleMetaphone((String)obj);
    }

    @Override
    public String encode(String value) {
        return this.doubleMetaphone(value);
    }

    public boolean isDoubleMetaphoneEqual(String value1, String value2) {
        return this.isDoubleMetaphoneEqual(value1, value2, false);
    }

    public boolean isDoubleMetaphoneEqual(String value1, String value2, boolean alternate) {
        return this.doubleMetaphone(value1, alternate).equals(this.doubleMetaphone(value2, alternate));
    }

    public int getMaxCodeLen() {
        return this.maxCodeLen;
    }

    public void setMaxCodeLen(int maxCodeLen) {
        this.maxCodeLen = maxCodeLen;
    }

    private int handleAEIOUY(DoubleMetaphoneResult result2, int index) {
        if (index == 0) {
            result2.append('A');
        }
        return index + 1;
    }

    private int handleC(String value, DoubleMetaphoneResult result2, int index) {
        if (this.conditionC0(value, index)) {
            result2.append('K');
            index += 2;
        } else if (index == 0 && DoubleMetaphone.contains(value, index, 6, "CAESAR")) {
            result2.append('S');
            index += 2;
        } else if (DoubleMetaphone.contains(value, index, 2, "CH")) {
            index = this.handleCH(value, result2, index);
        } else if (DoubleMetaphone.contains(value, index, 2, "CZ") && !DoubleMetaphone.contains(value, index - 2, 4, "WICZ")) {
            result2.append('S', 'X');
            index += 2;
        } else if (DoubleMetaphone.contains(value, index + 1, 3, "CIA")) {
            result2.append('X');
            index += 3;
        } else {
            if (DoubleMetaphone.contains(value, index, 2, "CC") && (index != 1 || this.charAt(value, 0) != 'M')) {
                return this.handleCC(value, result2, index);
            }
            if (DoubleMetaphone.contains(value, index, 2, "CK", "CG", "CQ")) {
                result2.append('K');
                index += 2;
            } else if (DoubleMetaphone.contains(value, index, 2, "CI", "CE", "CY")) {
                if (DoubleMetaphone.contains(value, index, 3, "CIO", "CIE", "CIA")) {
                    result2.append('S', 'X');
                } else {
                    result2.append('S');
                }
                index += 2;
            } else {
                result2.append('K');
                index = DoubleMetaphone.contains(value, index + 1, 2, " C", " Q", " G") ? (index += 3) : (DoubleMetaphone.contains(value, index + 1, 1, "C", "K", "Q") && !DoubleMetaphone.contains(value, index + 1, 2, "CE", "CI") ? (index += 2) : ++index);
            }
        }
        return index;
    }

    private int handleCC(String value, DoubleMetaphoneResult result2, int index) {
        if (DoubleMetaphone.contains(value, index + 2, 1, "I", "E", "H") && !DoubleMetaphone.contains(value, index + 2, 2, "HU")) {
            if (index == 1 && this.charAt(value, index - 1) == 'A' || DoubleMetaphone.contains(value, index - 1, 5, "UCCEE", "UCCES")) {
                result2.append("KS");
            } else {
                result2.append('X');
            }
            index += 3;
        } else {
            result2.append('K');
            index += 2;
        }
        return index;
    }

    private int handleCH(String value, DoubleMetaphoneResult result2, int index) {
        if (index > 0 && DoubleMetaphone.contains(value, index, 4, "CHAE")) {
            result2.append('K', 'X');
            return index + 2;
        }
        if (this.conditionCH0(value, index)) {
            result2.append('K');
            return index + 2;
        }
        if (this.conditionCH1(value, index)) {
            result2.append('K');
            return index + 2;
        }
        if (index > 0) {
            if (DoubleMetaphone.contains(value, 0, 2, "MC")) {
                result2.append('K');
            } else {
                result2.append('X', 'K');
            }
        } else {
            result2.append('X');
        }
        return index + 2;
    }

    private int handleD(String value, DoubleMetaphoneResult result2, int index) {
        if (DoubleMetaphone.contains(value, index, 2, "DG")) {
            if (DoubleMetaphone.contains(value, index + 2, 1, "I", "E", "Y")) {
                result2.append('J');
                index += 3;
            } else {
                result2.append("TK");
                index += 2;
            }
        } else if (DoubleMetaphone.contains(value, index, 2, "DT", "DD")) {
            result2.append('T');
            index += 2;
        } else {
            result2.append('T');
            ++index;
        }
        return index;
    }

    private int handleG(String value, DoubleMetaphoneResult result2, int index, boolean slavoGermanic) {
        if (this.charAt(value, index + 1) == 'H') {
            index = this.handleGH(value, result2, index);
        } else if (this.charAt(value, index + 1) == 'N') {
            if (index == 1 && this.isVowel(this.charAt(value, 0)) && !slavoGermanic) {
                result2.append("KN", "N");
            } else if (!DoubleMetaphone.contains(value, index + 2, 2, "EY") && this.charAt(value, index + 1) != 'Y' && !slavoGermanic) {
                result2.append("N", "KN");
            } else {
                result2.append("KN");
            }
            index += 2;
        } else if (DoubleMetaphone.contains(value, index + 1, 2, "LI") && !slavoGermanic) {
            result2.append("KL", "L");
            index += 2;
        } else if (index == 0 && (this.charAt(value, index + 1) == 'Y' || DoubleMetaphone.contains(value, index + 1, 2, ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER))) {
            result2.append('K', 'J');
            index += 2;
        } else if (!(!DoubleMetaphone.contains(value, index + 1, 2, "ER") && this.charAt(value, index + 1) != 'Y' || DoubleMetaphone.contains(value, 0, 6, "DANGER", "RANGER", "MANGER") || DoubleMetaphone.contains(value, index - 1, 1, "E", "I") || DoubleMetaphone.contains(value, index - 1, 3, "RGY", "OGY"))) {
            result2.append('K', 'J');
            index += 2;
        } else if (DoubleMetaphone.contains(value, index + 1, 1, "E", "I", "Y") || DoubleMetaphone.contains(value, index - 1, 4, "AGGI", "OGGI")) {
            if (DoubleMetaphone.contains(value, 0, 4, "VAN ", "VON ") || DoubleMetaphone.contains(value, 0, 3, "SCH") || DoubleMetaphone.contains(value, index + 1, 2, "ET")) {
                result2.append('K');
            } else if (DoubleMetaphone.contains(value, index + 1, 3, "IER")) {
                result2.append('J');
            } else {
                result2.append('J', 'K');
            }
            index += 2;
        } else if (this.charAt(value, index + 1) == 'G') {
            index += 2;
            result2.append('K');
        } else {
            ++index;
            result2.append('K');
        }
        return index;
    }

    private int handleGH(String value, DoubleMetaphoneResult result2, int index) {
        if (index > 0 && !this.isVowel(this.charAt(value, index - 1))) {
            result2.append('K');
            index += 2;
        } else if (index == 0) {
            if (this.charAt(value, index + 2) == 'I') {
                result2.append('J');
            } else {
                result2.append('K');
            }
            index += 2;
        } else if (index > 1 && DoubleMetaphone.contains(value, index - 2, 1, "B", "H", "D") || index > 2 && DoubleMetaphone.contains(value, index - 3, 1, "B", "H", "D") || index > 3 && DoubleMetaphone.contains(value, index - 4, 1, "B", "H")) {
            index += 2;
        } else {
            if (index > 2 && this.charAt(value, index - 1) == 'U' && DoubleMetaphone.contains(value, index - 3, 1, "C", "G", "L", "R", "T")) {
                result2.append('F');
            } else if (index > 0 && this.charAt(value, index - 1) != 'I') {
                result2.append('K');
            }
            index += 2;
        }
        return index;
    }

    private int handleH(String value, DoubleMetaphoneResult result2, int index) {
        if ((index == 0 || this.isVowel(this.charAt(value, index - 1))) && this.isVowel(this.charAt(value, index + 1))) {
            result2.append('H');
            index += 2;
        } else {
            ++index;
        }
        return index;
    }

    private int handleJ(String value, DoubleMetaphoneResult result2, int index, boolean slavoGermanic) {
        if (DoubleMetaphone.contains(value, index, 4, "JOSE") || DoubleMetaphone.contains(value, 0, 4, "SAN ")) {
            if (index == 0 && this.charAt(value, index + 4) == ' ' || value.length() == 4 || DoubleMetaphone.contains(value, 0, 4, "SAN ")) {
                result2.append('H');
            } else {
                result2.append('J', 'H');
            }
            ++index;
        } else {
            if (index == 0 && !DoubleMetaphone.contains(value, index, 4, "JOSE")) {
                result2.append('J', 'A');
            } else if (this.isVowel(this.charAt(value, index - 1)) && !slavoGermanic && (this.charAt(value, index + 1) == 'A' || this.charAt(value, index + 1) == 'O')) {
                result2.append('J', 'H');
            } else if (index == value.length() - 1) {
                result2.append('J', ' ');
            } else if (!DoubleMetaphone.contains(value, index + 1, 1, L_T_K_S_N_M_B_Z) && !DoubleMetaphone.contains(value, index - 1, 1, "S", "K", "L")) {
                result2.append('J');
            }
            index = this.charAt(value, index + 1) == 'J' ? (index += 2) : ++index;
        }
        return index;
    }

    private int handleL(String value, DoubleMetaphoneResult result2, int index) {
        if (this.charAt(value, index + 1) == 'L') {
            if (this.conditionL0(value, index)) {
                result2.appendPrimary('L');
            } else {
                result2.append('L');
            }
            index += 2;
        } else {
            ++index;
            result2.append('L');
        }
        return index;
    }

    private int handleP(String value, DoubleMetaphoneResult result2, int index) {
        if (this.charAt(value, index + 1) == 'H') {
            result2.append('F');
            index += 2;
        } else {
            result2.append('P');
            index = DoubleMetaphone.contains(value, index + 1, 1, "P", "B") ? index + 2 : index + 1;
        }
        return index;
    }

    private int handleR(String value, DoubleMetaphoneResult result2, int index, boolean slavoGermanic) {
        if (index == value.length() - 1 && !slavoGermanic && DoubleMetaphone.contains(value, index - 2, 2, "IE") && !DoubleMetaphone.contains(value, index - 4, 2, "ME", "MA")) {
            result2.appendAlternate('R');
        } else {
            result2.append('R');
        }
        return this.charAt(value, index + 1) == 'R' ? index + 2 : index + 1;
    }

    private int handleS(String value, DoubleMetaphoneResult result2, int index, boolean slavoGermanic) {
        if (DoubleMetaphone.contains(value, index - 1, 3, "ISL", "YSL")) {
            ++index;
        } else if (index == 0 && DoubleMetaphone.contains(value, index, 5, "SUGAR")) {
            result2.append('X', 'S');
            ++index;
        } else if (DoubleMetaphone.contains(value, index, 2, "SH")) {
            if (DoubleMetaphone.contains(value, index + 1, 4, "HEIM", "HOEK", "HOLM", "HOLZ")) {
                result2.append('S');
            } else {
                result2.append('X');
            }
            index += 2;
        } else if (DoubleMetaphone.contains(value, index, 3, "SIO", "SIA") || DoubleMetaphone.contains(value, index, 4, "SIAN")) {
            if (slavoGermanic) {
                result2.append('S');
            } else {
                result2.append('S', 'X');
            }
            index += 3;
        } else if (index == 0 && DoubleMetaphone.contains(value, index + 1, 1, "M", "N", "L", "W") || DoubleMetaphone.contains(value, index + 1, 1, "Z")) {
            result2.append('S', 'X');
            index = DoubleMetaphone.contains(value, index + 1, 1, "Z") ? index + 2 : index + 1;
        } else if (DoubleMetaphone.contains(value, index, 2, "SC")) {
            index = this.handleSC(value, result2, index);
        } else {
            if (index == value.length() - 1 && DoubleMetaphone.contains(value, index - 2, 2, "AI", "OI")) {
                result2.appendAlternate('S');
            } else {
                result2.append('S');
            }
            index = DoubleMetaphone.contains(value, index + 1, 1, "S", "Z") ? index + 2 : index + 1;
        }
        return index;
    }

    private int handleSC(String value, DoubleMetaphoneResult result2, int index) {
        if (this.charAt(value, index + 2) == 'H') {
            if (DoubleMetaphone.contains(value, index + 3, 2, "OO", "ER", "EN", "UY", "ED", "EM")) {
                if (DoubleMetaphone.contains(value, index + 3, 2, "ER", "EN")) {
                    result2.append("X", "SK");
                } else {
                    result2.append("SK");
                }
            } else if (index == 0 && !this.isVowel(this.charAt(value, 3)) && this.charAt(value, 3) != 'W') {
                result2.append('X', 'S');
            } else {
                result2.append('X');
            }
        } else if (DoubleMetaphone.contains(value, index + 2, 1, "I", "E", "Y")) {
            result2.append('S');
        } else {
            result2.append("SK");
        }
        return index + 3;
    }

    private int handleT(String value, DoubleMetaphoneResult result2, int index) {
        if (DoubleMetaphone.contains(value, index, 4, "TION")) {
            result2.append('X');
            index += 3;
        } else if (DoubleMetaphone.contains(value, index, 3, "TIA", "TCH")) {
            result2.append('X');
            index += 3;
        } else if (DoubleMetaphone.contains(value, index, 2, "TH") || DoubleMetaphone.contains(value, index, 3, "TTH")) {
            if (DoubleMetaphone.contains(value, index + 2, 2, "OM", "AM") || DoubleMetaphone.contains(value, 0, 4, "VAN ", "VON ") || DoubleMetaphone.contains(value, 0, 3, "SCH")) {
                result2.append('T');
            } else {
                result2.append('0', 'T');
            }
            index += 2;
        } else {
            result2.append('T');
            index = DoubleMetaphone.contains(value, index + 1, 1, "T", "D") ? index + 2 : index + 1;
        }
        return index;
    }

    private int handleW(String value, DoubleMetaphoneResult result2, int index) {
        if (DoubleMetaphone.contains(value, index, 2, "WR")) {
            result2.append('R');
            index += 2;
        } else if (index == 0 && (this.isVowel(this.charAt(value, index + 1)) || DoubleMetaphone.contains(value, index, 2, "WH"))) {
            if (this.isVowel(this.charAt(value, index + 1))) {
                result2.append('A', 'F');
            } else {
                result2.append('A');
            }
            ++index;
        } else if (index == value.length() - 1 && this.isVowel(this.charAt(value, index - 1)) || DoubleMetaphone.contains(value, index - 1, 5, "EWSKI", "EWSKY", "OWSKI", "OWSKY") || DoubleMetaphone.contains(value, 0, 3, "SCH")) {
            result2.appendAlternate('F');
            ++index;
        } else if (DoubleMetaphone.contains(value, index, 4, "WICZ", "WITZ")) {
            result2.append("TS", "FX");
            index += 4;
        } else {
            ++index;
        }
        return index;
    }

    private int handleX(String value, DoubleMetaphoneResult result2, int index) {
        if (index == 0) {
            result2.append('S');
            ++index;
        } else {
            if (index != value.length() - 1 || !DoubleMetaphone.contains(value, index - 3, 3, "IAU", "EAU") && !DoubleMetaphone.contains(value, index - 2, 2, "AU", "OU")) {
                result2.append("KS");
            }
            index = DoubleMetaphone.contains(value, index + 1, 1, "C", "X") ? index + 2 : index + 1;
        }
        return index;
    }

    private int handleZ(String value, DoubleMetaphoneResult result2, int index, boolean slavoGermanic) {
        if (this.charAt(value, index + 1) == 'H') {
            result2.append('J');
            index += 2;
        } else {
            if (DoubleMetaphone.contains(value, index + 1, 2, "ZO", "ZI", "ZA") || slavoGermanic && index > 0 && this.charAt(value, index - 1) != 'T') {
                result2.append("S", "TS");
            } else {
                result2.append('S');
            }
            index = this.charAt(value, index + 1) == 'Z' ? index + 2 : index + 1;
        }
        return index;
    }

    private boolean conditionC0(String value, int index) {
        if (DoubleMetaphone.contains(value, index, 4, "CHIA")) {
            return true;
        }
        if (index <= 1) {
            return false;
        }
        if (this.isVowel(this.charAt(value, index - 2))) {
            return false;
        }
        if (!DoubleMetaphone.contains(value, index - 1, 3, "ACH")) {
            return false;
        }
        char c = this.charAt(value, index + 2);
        return c != 'I' && c != 'E' || DoubleMetaphone.contains(value, index - 2, 6, "BACHER", "MACHER");
    }

    private boolean conditionCH0(String value, int index) {
        if (index != 0) {
            return false;
        }
        if (!DoubleMetaphone.contains(value, index + 1, 5, "HARAC", "HARIS") && !DoubleMetaphone.contains(value, index + 1, 3, "HOR", "HYM", "HIA", "HEM")) {
            return false;
        }
        return !DoubleMetaphone.contains(value, 0, 5, "CHORE");
    }

    private boolean conditionCH1(String value, int index) {
        return DoubleMetaphone.contains(value, 0, 4, "VAN ", "VON ") || DoubleMetaphone.contains(value, 0, 3, "SCH") || DoubleMetaphone.contains(value, index - 2, 6, "ORCHES", "ARCHIT", "ORCHID") || DoubleMetaphone.contains(value, index + 2, 1, "T", "S") || (DoubleMetaphone.contains(value, index - 1, 1, "A", "O", "U", "E") || index == 0) && (DoubleMetaphone.contains(value, index + 2, 1, L_R_N_M_B_H_F_V_W_SPACE) || index + 1 == value.length() - 1);
    }

    private boolean conditionL0(String value, int index) {
        if (index == value.length() - 3 && DoubleMetaphone.contains(value, index - 1, 4, "ILLO", "ILLA", "ALLE")) {
            return true;
        }
        return (DoubleMetaphone.contains(value, value.length() - 2, 2, "AS", "OS") || DoubleMetaphone.contains(value, value.length() - 1, 1, "A", "O")) && DoubleMetaphone.contains(value, index - 1, 4, "ALLE");
    }

    private boolean conditionM0(String value, int index) {
        if (this.charAt(value, index + 1) == 'M') {
            return true;
        }
        return DoubleMetaphone.contains(value, index - 1, 3, "UMB") && (index + 1 == value.length() - 1 || DoubleMetaphone.contains(value, index + 2, 2, "ER"));
    }

    private boolean isSlavoGermanic(String value) {
        return value.indexOf(87) > -1 || value.indexOf(75) > -1 || value.indexOf("CZ") > -1 || value.indexOf("WITZ") > -1;
    }

    private boolean isVowel(char ch) {
        return VOWELS.indexOf(ch) != -1;
    }

    private boolean isSilentStart(String value) {
        boolean result2 = false;
        for (String element : SILENT_START) {
            if (!value.startsWith(element)) continue;
            result2 = true;
            break;
        }
        return result2;
    }

    private String cleanInput(String input) {
        if (input == null) {
            return null;
        }
        if ((input = input.trim()).length() == 0) {
            return null;
        }
        return input.toUpperCase(Locale.ENGLISH);
    }

    protected char charAt(String value, int index) {
        if (index < 0 || index >= value.length()) {
            return '\u0000';
        }
        return value.charAt(index);
    }

    protected static boolean contains(String value, int start, int length, String ... criteria) {
        boolean result2 = false;
        if (start >= 0 && start + length <= value.length()) {
            String target = value.substring(start, start + length);
            for (String element : criteria) {
                if (!target.equals(element)) continue;
                result2 = true;
                break;
            }
        }
        return result2;
    }

    public class DoubleMetaphoneResult {
        private final StringBuilder primary;
        private final StringBuilder alternate;
        private final int maxLength;

        public DoubleMetaphoneResult(int maxLength) {
            this.primary = new StringBuilder(DoubleMetaphone.this.getMaxCodeLen());
            this.alternate = new StringBuilder(DoubleMetaphone.this.getMaxCodeLen());
            this.maxLength = maxLength;
        }

        public void append(char value) {
            this.appendPrimary(value);
            this.appendAlternate(value);
        }

        public void append(char primary, char alternate) {
            this.appendPrimary(primary);
            this.appendAlternate(alternate);
        }

        public void appendPrimary(char value) {
            if (this.primary.length() < this.maxLength) {
                this.primary.append(value);
            }
        }

        public void appendAlternate(char value) {
            if (this.alternate.length() < this.maxLength) {
                this.alternate.append(value);
            }
        }

        public void append(String value) {
            this.appendPrimary(value);
            this.appendAlternate(value);
        }

        public void append(String primary, String alternate) {
            this.appendPrimary(primary);
            this.appendAlternate(alternate);
        }

        public void appendPrimary(String value) {
            int addChars = this.maxLength - this.primary.length();
            if (value.length() <= addChars) {
                this.primary.append(value);
            } else {
                this.primary.append(value.substring(0, addChars));
            }
        }

        public void appendAlternate(String value) {
            int addChars = this.maxLength - this.alternate.length();
            if (value.length() <= addChars) {
                this.alternate.append(value);
            } else {
                this.alternate.append(value.substring(0, addChars));
            }
        }

        public String getPrimary() {
            return this.primary.toString();
        }

        public String getAlternate() {
            return this.alternate.toString();
        }

        public boolean isComplete() {
            return this.primary.length() >= this.maxLength && this.alternate.length() >= this.maxLength;
        }
    }
}

