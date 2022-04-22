/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.visual;

import de.fanta.events.Event;
import de.fanta.module.Module;
import java.awt.Color;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;

public class ChestESP
extends Module {
    public ChestESP() {
        super("Chestesp", 0, Module.Type.Visual, new Color(108, 2, 139));
    }

    @Override
    public void onEvent(Event event) {
        for (Object o : ChestESP.mc.theWorld.loadedTileEntityList) {
            boolean cfr_ignored_0 = o instanceof TileEntityChest;
        }
    }

    public static void drawChestESP() {
        List loadedTileEntityList = ChestESP.mc.getMinecraft().theWorld.loadedTileEntityList;
        int i = 0;
        int loadedTileEntityListSize = loadedTileEntityList.size();
        while (i < loadedTileEntityListSize) {
            TileEntity tileEntity = (TileEntity)loadedTileEntityList.get(i);
            if (tileEntity instanceof TileEntityChest) {
                GlStateManager.disableTexture2D();
                TileEntityRendererDispatcher.instance.renderTileEntity(tileEntity, ChestESP.mc.getMinecraft().timer.renderPartialTicks, 1);
                GlStateManager.enableTexture2D();
            }
            ++i;
        }
    }
}

