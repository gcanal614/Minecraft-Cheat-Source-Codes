/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.io.comparator.AbstractFileComparator;
import org.apache.commons.io.comparator.ReverseComparator;

public class LastModifiedFileComparator
extends AbstractFileComparator
implements Serializable {
    public static final Comparator<File> LASTMODIFIED_COMPARATOR = new LastModifiedFileComparator();
    public static final Comparator<File> LASTMODIFIED_REVERSE = new ReverseComparator(LASTMODIFIED_COMPARATOR);

    @Override
    public int compare(File file1, File file2) {
        long result2 = file1.lastModified() - file2.lastModified();
        if (result2 < 0L) {
            return -1;
        }
        if (result2 > 0L) {
            return 1;
        }
        return 0;
    }
}

