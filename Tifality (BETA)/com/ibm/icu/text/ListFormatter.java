/*
 * Decompiled with CFR 0.152.
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class ListFormatter {
    private final String two;
    private final String start;
    private final String middle;
    private final String end;
    static Map<ULocale, ListFormatter> localeToData = new HashMap<ULocale, ListFormatter>();
    static Cache cache = new Cache();

    public ListFormatter(String two, String start, String middle, String end) {
        this.two = two;
        this.start = start;
        this.middle = middle;
        this.end = end;
    }

    public static ListFormatter getInstance(ULocale locale) {
        return cache.get(locale);
    }

    public static ListFormatter getInstance(Locale locale) {
        return ListFormatter.getInstance(ULocale.forLocale(locale));
    }

    public static ListFormatter getInstance() {
        return ListFormatter.getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public String format(Object ... items) {
        return this.format(Arrays.asList(items));
    }

    public String format(Collection<?> items) {
        Iterator<?> it = items.iterator();
        int count = items.size();
        switch (count) {
            case 0: {
                return "";
            }
            case 1: {
                return it.next().toString();
            }
            case 2: {
                return this.format2(this.two, it.next(), it.next());
            }
        }
        String result2 = it.next().toString();
        result2 = this.format2(this.start, result2, it.next());
        count -= 3;
        while (count > 0) {
            result2 = this.format2(this.middle, result2, it.next());
            --count;
        }
        return this.format2(this.end, result2, it.next());
    }

    private String format2(String pattern, Object a, Object b) {
        int i0 = pattern.indexOf("{0}");
        int i1 = pattern.indexOf("{1}");
        if (i0 < 0 || i1 < 0) {
            throw new IllegalArgumentException("Missing {0} or {1} in pattern " + pattern);
        }
        if (i0 < i1) {
            return pattern.substring(0, i0) + a + pattern.substring(i0 + 3, i1) + b + pattern.substring(i1 + 3);
        }
        return pattern.substring(0, i1) + b + pattern.substring(i1 + 3, i0) + a + pattern.substring(i0 + 3);
    }

    static void add(String locale, String ... data2) {
        localeToData.put(new ULocale(locale), new ListFormatter(data2[0], data2[1], data2[2], data2[3]));
    }

    private static class Cache {
        private final ICUCache<ULocale, ListFormatter> cache = new SimpleCache<ULocale, ListFormatter>();

        private Cache() {
        }

        public ListFormatter get(ULocale locale) {
            ListFormatter result2 = this.cache.get(locale);
            if (result2 == null) {
                result2 = Cache.load(locale);
                this.cache.put(locale, result2);
            }
            return result2;
        }

        private static ListFormatter load(ULocale ulocale) {
            ICUResourceBundle r = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt51b", ulocale);
            r = r.getWithFallback("listPattern/standard");
            return new ListFormatter(r.getWithFallback("2").getString(), r.getWithFallback("start").getString(), r.getWithFallback("middle").getString(), r.getWithFallback("end").getString());
        }
    }
}

