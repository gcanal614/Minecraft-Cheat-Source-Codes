// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.security;

import net.minecraft.client.gui.GuiScreen;
import bozoware.impl.UI.BozoMainMenu;
import net.minecraft.client.Minecraft;
import java.util.Iterator;
import java.io.IOException;
import bozoware.base.BozoWare;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.net.URL;
import bozoware.impl.UI.BozoAuthMenu;
import bozoware.base.security.utils.SecurityUtils;
import java.awt.Color;

public class Auth
{
    public static String devTerm;
    public static boolean isDev;
    public static Color embedColor;
    
    public static boolean Authenticate() {
        final String UserHWID = SecurityUtils.getHWID();
        final String UID = BozoAuthMenu.UIDText.getText();
        try {
            final URL URL = new URL(SecurityUtils.kilLSwitchURL1.toString());
            if (!Objects.equals(SecurityUtils.kilLSwitchURL1.toString(), SecurityUtils.kilLSwitchURL.toString())) {
                return false;
            }
            if (!Objects.equals(SecurityUtils.kilLSwitchURL1.toString(), "https://pastebin.com/raw/BkHUzxmj")) {
                return false;
            }
            if (!Objects.equals(SecurityUtils.kilLSwitchURL.toString(), "https://pastebin.com/raw/BkHUzxmj")) {
                return false;
            }
            if (!Objects.equals(SecurityUtils.kilLSwitchURL1.toString(), SecurityUtils.kilLSwitchURL2.toString())) {
                return false;
            }
            if (!Objects.equals(SecurityUtils.kilLSwitchURL.toString(), SecurityUtils.kilLSwitchURL2.toString())) {
                return false;
            }
            if (!Objects.equals("https://pastebin.com/raw/BkHUzxmj", SecurityUtils.kilLSwitchURL2.toString())) {
                return false;
            }
            final InputStreamReader ISR = new InputStreamReader(URL.openStream());
            final BufferedReader br = new BufferedReader(ISR);
            final List<String> data = br.lines().collect((Collector<? super String, ?, List<String>>)Collectors.toList());
            if (UID.length() == 4) {
                for (final String line : data) {
                    if (line.contains(UserHWID) && line.contains(UID)) {
                        if (line.contains(":")) {
                            BozoWare.BozoUserName = line.split(":")[0];
                        }
                        return true;
                    }
                }
            }
        }
        catch (IOException var2) {
            return false;
        }
        return false;
    }
    
    public static void LoadClient(final long FalseMillis, final long TrueMillis) {
        if (!Authenticate()) {
            System.out.println("Not Authenticated... DEBUG: " + Authenticate());
            BozoAuthMenu.Status = "Not Authenticated. Please Enter Your UID.";
            BozoAuthMenu.StatusColor = -65536;
            try {
                Thread.sleep(FalseMillis);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Authenticated, Welcome to BozoWare! DEBUG: " + Authenticate());
            Minecraft.getMinecraft().displayGuiScreen(new BozoMainMenu());
        }
    }
}
