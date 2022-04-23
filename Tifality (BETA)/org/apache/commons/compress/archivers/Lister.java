/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.compress.archivers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;

public final class Lister {
    private static final ArchiveStreamFactory factory = new ArchiveStreamFactory();

    public static void main(String[] args2) throws Exception {
        ArchiveEntry ae;
        if (args2.length == 0) {
            Lister.usage();
            return;
        }
        System.out.println("Analysing " + args2[0]);
        File f = new File(args2[0]);
        if (!f.isFile()) {
            System.err.println(f + " doesn't exist or is a directory");
        }
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(f));
        ArchiveInputStream ais = args2.length > 1 ? factory.createArchiveInputStream(args2[1], fis) : factory.createArchiveInputStream(fis);
        System.out.println("Created " + ais.toString());
        while ((ae = ais.getNextEntry()) != null) {
            System.out.println(ae.getName());
        }
        ais.close();
        ((InputStream)fis).close();
    }

    private static void usage() {
        System.out.println("Parameters: archive-name [archive-type]");
    }
}

