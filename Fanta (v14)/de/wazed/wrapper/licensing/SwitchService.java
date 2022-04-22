/*
 * Decompiled with CFR 0.152.
 */
package de.wazed.wrapper.licensing;

import de.wazed.wrapper.utils.FieldAdapter;
import java.net.URL;

public class SwitchService {
    private final String MINECRAFT_SESSION_SERVICE_CLASS = "com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService";
    private final String MINECRAFT_AUTHENTICATION_SERVICE_CLASS = "com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication";
    private final String[] WHITELISTED_DOMAINS = new String[]{".minecraft.net", ".mojang.com", ".thealtening.com"};
    private final FieldAdapter minecraftSessionServer = new FieldAdapter("com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService");
    private final FieldAdapter userAuthentication = new FieldAdapter("com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication");
    private static SwitchService instance;

    public SwitchService() {
        instance = this;
        try {
            this.minecraftSessionServer.updateFieldIfPresent("WHITELISTED_DOMAINS", this.WHITELISTED_DOMAINS);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchService(String authBase, String sessionBase) {
        try {
            String authServer = authBase;
            FieldAdapter userAuth = this.getUserAuthentication();
            userAuth.updateFieldIfPresent("BASE_URL", authServer);
            userAuth.updateFieldIfPresent("ROUTE_AUTHENTICATE", new URL(authServer + "authenticate"));
            userAuth.updateFieldIfPresent("ROUTE_INVALIDATE", new URL(authServer + "invalidate"));
            userAuth.updateFieldIfPresent("ROUTE_REFRESH", new URL(authServer + "refresh"));
            userAuth.updateFieldIfPresent("ROUTE_VALIDATE", new URL(authServer + "validate"));
            userAuth.updateFieldIfPresent("ROUTE_SIGNOUT", new URL(authServer + "signout"));
            String sessionServer = sessionBase;
            FieldAdapter userSession = this.getMinecraftSessionServer();
            userSession.updateFieldIfPresent("BASE_URL", sessionServer + "session/minecraft/");
            userSession.updateFieldIfPresent("JOIN_URL", new URL(sessionServer + "session/minecraft/join"));
            userSession.updateFieldIfPresent("CHECK_URL", new URL(sessionServer + "session/minecraft/hasJoined"));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public FieldAdapter getMinecraftSessionServer() {
        return this.minecraftSessionServer;
    }

    public FieldAdapter getUserAuthentication() {
        return this.userAuthentication;
    }

    public String getMINECRAFT_AUTHENTICATION_SERVICE_CLASS() {
        return "com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication";
    }

    public String getMINECRAFT_SESSION_SERVICE_CLASS() {
        return "com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService";
    }

    public static SwitchService getInstance() {
        return instance;
    }

    public String[] getWHITELISTED_DOMAINS() {
        return this.WHITELISTED_DOMAINS;
    }
}

