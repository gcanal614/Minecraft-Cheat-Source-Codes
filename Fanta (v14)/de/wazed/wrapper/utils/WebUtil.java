/*
 * Decompiled with CFR 0.152.
 */
package de.wazed.wrapper.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebUtil {
    private static WebUtil instance;

    public WebUtil() {
        instance = this;
    }

    public List<String> performRequest(String url) throws IOException {
        String line;
        ArrayList<String> tempList = new ArrayList<String>();
        URL requestUrl = new URL(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(requestUrl.openStream()));
        while ((line = br.readLine()) != null) {
            tempList.add(line);
        }
        return tempList;
    }

    public static WebUtil getInstance() {
        return instance;
    }
}

