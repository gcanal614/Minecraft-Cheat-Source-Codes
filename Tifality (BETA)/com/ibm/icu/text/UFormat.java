/*
 * Decompiled with CFR 0.152.
 */
package com.ibm.icu.text;

import com.ibm.icu.util.ULocale;
import java.text.Format;

public abstract class UFormat
extends Format {
    private static final long serialVersionUID = -4964390515840164416L;
    private ULocale validLocale;
    private ULocale actualLocale;

    public final ULocale getLocale(ULocale.Type type2) {
        return type2 == ULocale.ACTUAL_LOCALE ? this.actualLocale : this.validLocale;
    }

    final void setLocale(ULocale valid, ULocale actual) {
        if (valid == null != (actual == null)) {
            throw new IllegalArgumentException();
        }
        this.validLocale = valid;
        this.actualLocale = actual;
    }
}

