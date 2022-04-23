/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.altmanager;

import club.tifality.gui.altmanager.Alt;
import java.util.ArrayList;

public class AltManager {
    public static Alt lastAlt;
    public static ArrayList registry;

    public ArrayList getRegistry() {
        return registry;
    }

    public void setLastAlt(Alt alt) {
        lastAlt = alt;
    }

    static {
        registry = new ArrayList();
    }
}

