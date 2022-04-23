/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ave
 *  axu
 */
package net.minecraft.realms;

import java.lang.reflect.Constructor;
import net.minecraft.realms.RealmsScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsBridge
extends RealmsScreen {
    private static final Logger LOGGER = LogManager.getLogger();
    private axu previousScreen;

    public void switchToRealms(axu axu2) {
        this.previousScreen = axu2;
        try {
            Class<?> clazz = Class.forName("com.mojang.realmsclient.RealmsMainScreen");
            Constructor<?> \u26032 = clazz.getDeclaredConstructor(RealmsScreen.class);
            \u26032.setAccessible(true);
            Object \u26033 = \u26032.newInstance(this);
            ave.A().a((axu)((RealmsScreen)\u26033).getProxy());
        }
        catch (Exception exception) {
            LOGGER.error("Realms module missing", (Throwable)exception);
        }
    }

    @Override
    public void init() {
        ave.A().a(this.previousScreen);
    }
}

