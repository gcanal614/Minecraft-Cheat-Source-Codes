/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.visual;

import de.fanta.events.Event;
import de.fanta.module.Module;
import java.awt.Color;

public class NoFov
extends Module {
    public NoFov() {
        super("Nofov", 0, Module.Type.Visual, Color.yellow);
    }

    @Override
    public void onEvent(Event event) {
    }
}

