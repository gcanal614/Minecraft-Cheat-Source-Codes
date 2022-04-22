package com.zerosense.mods.impl.RENDER;

import com.zerosense.Events.Event;
import com.zerosense.Events.Event3D;
import com.zerosense.Events.impl.EventRender3D;
import com.zerosense.Settings.ModeSetting;
import com.zerosense.Utils.EntityUtil;
import com.zerosense.Utils.RenderUtils;
import com.zerosense.mods.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;



import java.awt.*;
import java.util.Objects;

public class ESP extends Module {

    static {

    }

    public void onEvent(Event paramEvent) {
        if (paramEvent instanceof EventRender3D) {
                for (Object entityLivingBase : this.mc.theWorld.loadedEntityList) {
                 if (entityLivingBase instanceof EntityLivingBase) {
                    EntityLivingBase entityLivingBase1 = (EntityLivingBase) entityLivingBase;
                    AxisAlignedBB axisAlignedBB = new AxisAlignedBB(entityLivingBase1.boundingBox.minX - entityLivingBase1.posX + entityLivingBase1.posX - (this.mc.getRenderManager()).renderPosX, entityLivingBase1.boundingBox.minY - entityLivingBase1.posY + entityLivingBase1.posY - (this.mc.getRenderManager()).renderPosY, entityLivingBase1.boundingBox.minZ - entityLivingBase1.posZ + entityLivingBase1.posZ - (this.mc.getRenderManager()).renderPosZ, entityLivingBase1.boundingBox.maxX - entityLivingBase1.posX + entityLivingBase1.posX - (this.mc.getRenderManager()).renderPosX, entityLivingBase1.boundingBox.maxY - entityLivingBase1.posY + entityLivingBase1.posY - (this.mc.getRenderManager()).renderPosY, entityLivingBase1.boundingBox.maxZ - entityLivingBase1.posZ + entityLivingBase1.posZ - (this.mc.getRenderManager()).renderPosZ);
                    if (entityLivingBase1 != this.mc.thePlayer && entityLivingBase1 instanceof net.minecraft.entity.player.EntityPlayer) {
                        float f1 = (float) (System.currentTimeMillis() % 4500L) / 4500.0F;
                        int i = Color.HSBtoRGB(f1, 1.0F, 1.0F);
                        Color color = new Color(i);
                        float f2 = color.getRed();
                        float f3 = color.getGreen();
                        float f4 = color.getBlue();
                        float f5 = color.getAlpha();
                        RenderUtils.drawBoundingBox(axisAlignedBB, f2 / 255.0F, f3 / 255.0F, f4 / 255.0F, 0.75F);
                    }
                }// hmm idk try it on players  / same
            }
        }
    }

    public ModeSetting mode = new ModeSetting("Mode", "Normal");

    public ESP() {
        super("ESP", 0, Module.Category.RENDER);
        this.addSettings(mode);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
    }
}


