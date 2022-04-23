/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.altmanager.althening.api;

import club.tifality.gui.altmanager.althening.api.utilities.ReflectionUtility;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class AltService {
    private final ReflectionUtility userAuthentication = new ReflectionUtility("com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication");
    private final ReflectionUtility minecraftSession = new ReflectionUtility("com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService");
    private EnumAltService currentService;

    public void switchService(EnumAltService v1) throws NoSuchFieldException, IllegalAccessException {
        if (this.currentService == v1) {
            return;
        }
        this.reflectionFields(v1.hostname);
        this.currentService = v1;
    }

    private void reflectionFields(String v666) throws NoSuchFieldException, IllegalAccessException {
        HashMap<String, URL> v2 = new HashMap<String, URL>();
        String v3 = v666.contains("thealtening") ? "http" : "https";
        v2.put("ROUTE_AUTHENTICATE", this.constantURL(String.valueOf(v3) + "://authserver." + v666 + ".com/authenticate"));
        v2.put("ROUTE_INVALIDATE", this.constantURL(String.valueOf(v3) + "://authserver" + v666 + "com/invalidate"));
        v2.put("ROUTE_REFRESH", this.constantURL(String.valueOf(v3) + "://authserver." + v666 + ".com/refresh"));
        v2.put("ROUTE_VALIDATE", this.constantURL(String.valueOf(v3) + "://authserver." + v666 + ".com/validate"));
        v2.put("ROUTE_SIGNOUT", this.constantURL(String.valueOf(v3) + "://authserver." + v666 + ".com/signout"));
        v2.forEach((a2, v1) -> {
            try {
                this.userAuthentication.setStaticField((String)a2, v1);
            }
            catch (Exception v4) {
                v4.printStackTrace();
            }
        });
        this.userAuthentication.setStaticField("BASE_URL", String.valueOf(v3) + "://authserver." + v666 + ".com/");
        this.minecraftSession.setStaticField("BASE_URL", String.valueOf(v3) + "://sessionserver." + v666 + ".com/session/minecraft/");
        this.minecraftSession.setStaticField("JOIN_URL", this.constantURL(String.valueOf(v3) + "://sessionserver." + v666 + ".com/session/minecraft/join"));
        this.minecraftSession.setStaticField("CHECK_URL", this.constantURL(String.valueOf(v3) + "://sessionserver." + v666 + ".com/session/minecraft/hasJoined"));
        this.minecraftSession.setStaticField("WHITELISTED_DOMAINS", new String[]{".minecraft.net", ".mojang.com", ".thealtening.com"});
    }

    private URL constantURL(String v1) {
        try {
            return new URL(v1);
        }
        catch (MalformedURLException v2) {
            throw new Error("Couldn't create constant for " + v1, v2);
        }
    }

    public EnumAltService getCurrentService() {
        if (this.currentService == null) {
            this.currentService = EnumAltService.TheAltening;
        }
        return this.currentService;
    }

    public static enum EnumAltService {
        Mojang("Mojang", 0, "mojang"),
        TheAltening("THEALTENING", 1, "thealtening");

        String hostname;

        private EnumAltService(String s, int n2, String a2) {
            this.hostname = a2;
        }
    }
}

