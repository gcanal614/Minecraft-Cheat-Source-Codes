/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.demo;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class DemoWorldServer
extends WorldServer {
    private static final long demoWorldSeed = "North Carolina".hashCode();
    public static final WorldSettings demoWorldSettings = new WorldSettings(demoWorldSeed, WorldSettings.GameType.SURVIVAL, true, false, WorldType.DEFAULT).enableBonusChest();

    public DemoWorldServer(MinecraftServer server, ISaveHandler saveHandlerIn, WorldInfo worldInfoIn, int dimensionId) {
        super(server, saveHandlerIn, worldInfoIn, dimensionId);
        this.worldInfo.populateFromWorldSettings(demoWorldSettings);
    }
}

