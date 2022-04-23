/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

public final class FileUtils {
    public static List<String> getLines(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            return IOUtils.readLines((InputStream)fis, Charsets.UTF_8);
        }
        catch (IOException e) {
            return new ArrayList<String>();
        }
    }
}

