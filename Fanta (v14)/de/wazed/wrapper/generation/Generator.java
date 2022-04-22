/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.wazed.wrapper.generation;

import de.wazed.wrapper.generation.Account;
import de.wazed.wrapper.utils.WebUtil;
import de.wazed.wrapper.utils.exceptions.NotFoundException;
import de.wazed.wrapper.utils.exceptions.UnauthorizedException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

public class Generator {
    private static Generator instance;

    public Generator() {
        instance = this;
    }

    public Account generate(String token) throws UnauthorizedException, NotFoundException {
        try {
            List<String> lines = WebUtil.getInstance().performRequest("http://api.thealtening.com/v1/generate?token=" + token + "&info=true");
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line).append("\n");
            }
            JSONObject object = new JSONObject(sb.toString());
            HashMap map = new HashMap();
            HashMap<String, String> infoMap = new HashMap<String, String>();
            for (String key : object.getJSONObject("info").keySet()) {
                infoMap.put(key, object.getJSONObject("info").getString(key));
            }
            Account tempAccount = new Account(object.getString("token"), object.getString("password"), object.getString("username"), object.getBoolean("limit"), infoMap);
            return tempAccount;
        }
        catch (IOException e) {
            if (e.getMessage().contains("403")) {
                throw new UnauthorizedException();
            }
            if (e.getMessage().contains("404")) {
                throw new NotFoundException();
            }
            return null;
        }
    }

    public static Generator getInstance() {
        return instance;
    }
}

