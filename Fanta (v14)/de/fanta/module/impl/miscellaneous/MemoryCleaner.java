/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.miscellaneous;

import de.fanta.events.Event;
import de.fanta.module.Module;
import de.fanta.utils.TimeUtil;
import java.awt.Color;

public class MemoryCleaner
extends Module {
    TimeUtil time = new TimeUtil();

    public MemoryCleaner() {
        super("RamClean", 0, Module.Type.Misc, Color.WHITE);
    }

    @Override
    public void onEvent(Event event) {
        if (this.time.hasReached(300000L)) {
            this.time.reset();
        }
    }
}

