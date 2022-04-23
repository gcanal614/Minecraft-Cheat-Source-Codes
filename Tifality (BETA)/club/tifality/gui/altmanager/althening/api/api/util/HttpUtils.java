/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.altmanager.althening.api.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class HttpUtils {
    protected String connect(String v1) throws IOException {
        String v5;
        InputStream v2 = new URL(v1).openStream();
        BufferedReader v3 = new BufferedReader(new InputStreamReader(v2));
        StringBuilder v4 = new StringBuilder();
        while ((v5 = v3.readLine()) != null) {
            v4.append(v5).append("\n");
        }
        return v4.toString();
    }
}

