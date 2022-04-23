// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.security.utils;

import java.net.MalformedURLException;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.net.URL;

public class SecurityUtils
{
    public static URL kilLSwitchURL;
    public static URL kilLSwitchURL1;
    public static URL kilLSwitchURL2;
    
    public static String getHWID() {
        try {
            final String toEncrypt = System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toEncrypt.getBytes());
            final StringBuffer hexString = new StringBuffer();
            final byte[] BD = md.digest();
            for (int i = 0; i < BD.length; ++i) {
                final byte aBD = BD[i];
                final String hex = Integer.toHexString(0xFF & aBD);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (Exception var9) {
            var9.printStackTrace();
            return "Error";
        }
    }
    
    public static String MD5Hash(final String toBeHashed) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toBeHashed.getBytes());
            final StringBuffer hexString = new StringBuffer();
            final byte[] BD = md.digest();
            for (int i = 0; i < BD.length; ++i) {
                final byte aBD = BD[i];
                final String hex = Integer.toHexString(0xFF & aBD);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (Exception var9) {
            var9.printStackTrace();
            return "Error";
        }
    }
    
    public static String grabCurrentIP() {
        try {
            final URL URL = new URL("http://checkip.amazonaws.com/");
            final InputStreamReader ISR = new InputStreamReader(URL.openStream());
            final BufferedReader br = new BufferedReader(ISR);
            return br.readLine();
        }
        catch (IOException var2) {
            return null;
        }
    }
    
    static {
        try {
            SecurityUtils.kilLSwitchURL = new URL("https://pastebin.com/raw/BkHUzxmj");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            SecurityUtils.kilLSwitchURL1 = new URL("https://pastebin.com/raw/BkHUzxmj");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            SecurityUtils.kilLSwitchURL2 = new URL("https://pastebin.com/raw/BkHUzxmj");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
