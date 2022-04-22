/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.events.listeners;

import de.fanta.events.Event;
import net.minecraft.util.BlockPos;

public class EventBlockRightClick
extends Event<EventBlockRightClick> {
    private BlockPos bp;

    public EventBlockRightClick(BlockPos bp) {
        this.bp = bp;
    }

    public BlockPos getBlockpos() {
        return this.bp;
    }
}

