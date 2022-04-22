/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.visual;

import de.fanta.events.Event;
import de.fanta.module.Module;
import java.awt.Color;

public class FakeBlock
extends Module {
    public FakeBlock() {
        super("FakeBlock", 0, Module.Type.Visual, Color.RED);
    }

    @Override
    public void onEvent(Event event) {
    }
}

