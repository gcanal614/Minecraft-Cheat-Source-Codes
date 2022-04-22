/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.module.impl.visual;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventRender3D;
import de.fanta.gui.font.BasicFontRenderer;
import de.fanta.module.Module;
import de.fanta.utils.PlayerUtil;
import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockCake;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class BlockESP
extends Module {
    public BlockESP() {
        super("BlockESP", 0, Module.Type.Visual, Color.green);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender3D) {
            int radius = 30;
            int y = -30;
            while (y <= 30) {
                int x = -30;
                while (x <= 30) {
                    int z = -30;
                    while (z <= 30) {
                        BlockPos blockPos = new BlockPos(BlockESP.mc.thePlayer.posX + (double)x, BlockESP.mc.thePlayer.posY + (double)y, BlockESP.mc.thePlayer.posZ + (double)z);
                        Block block = BlockESP.mc.theWorld.getBlockState(blockPos).getBlock();
                        if (block instanceof BlockBed || block instanceof BlockCake) {
                            BasicFontRenderer font = Minecraft.getMinecraft().fontRendererObj;
                            float distance = (float)PlayerUtil.getDistanceToBlock(new BlockPos(x, y, z));
                            BlockESP.blockESPBox(blockPos);
                        }
                        ++z;
                    }
                    ++x;
                }
                ++y;
            }
        }
    }

    public static void blockESPBox(BlockPos blockPos) {
        long delayCounter = 100L;
        double d = blockPos.getX();
        Minecraft.getMinecraft().getRenderManager();
        double x = d - RenderManager.renderPosX;
        double d2 = blockPos.getY();
        Minecraft.getMinecraft().getRenderManager();
        double y = d2 - RenderManager.renderPosY;
        double d3 = blockPos.getZ();
        Minecraft.getMinecraft().getRenderManager();
        double z = d3 - RenderManager.renderPosZ;
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)1.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4d((double)1.0, (double)0.0, (double)0.0, (double)0.5);
        RenderGlobal.func_181561_a(new AxisAlignedBB(x, y, z, x + 1.0, y, z + 1.0));
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
    }
}

