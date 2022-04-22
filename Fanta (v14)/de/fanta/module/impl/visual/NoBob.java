/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.visual;

import de.fanta.events.Event;
import de.fanta.module.Module;
import java.awt.Color;

public class NoBob
extends Module {
    public NoBob() {
        super("NoBob", 0, Module.Type.Visual, Color.green);
    }

    @Override
    public void onEvent(Event event) {
        NoBob.mc.gameSettings.viewBobbing = true;
        NoBob.mc.thePlayer.distanceWalkedModified = 0.0f;
    }
}

