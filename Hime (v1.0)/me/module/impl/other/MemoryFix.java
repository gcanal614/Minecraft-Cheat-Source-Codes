package me.module.impl.other;

import me.event.impl.EventReceivePacket;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;


public class MemoryFix extends Module{
    public MemoryFix() {
        super("Memory Fix", 0, Category.MISC);
    }

    @Handler
    public void onReceive(EventReceivePacket event) {

    }
}