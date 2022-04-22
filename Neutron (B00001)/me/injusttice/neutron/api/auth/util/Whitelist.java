package me.injusttice.neutron.api.auth.util;

import me.injusttice.neutron.api.auth.LoginScreen;

import java.net.URL;
import java.util.Scanner;

public class Whitelist {

    public static String getHWID() throws Exception {
        final String s = Encryption.encrypt(HWID.HWID);
        return s;
    }

    public static boolean WhiteList()
    {
        try
        {
            String url = Encryption.decrypt("QBep6X8iyF3hp6RbgOOOZjCHbxq2ltyosI8RrvEvjo4uxPiOYnG4ygQY+8C3QnpY");
            String s = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();
            return s.contains(LoginScreen.username.getText() + ":" + getHWID());
        }
        catch (Exception e) { return false; }
    }
}
