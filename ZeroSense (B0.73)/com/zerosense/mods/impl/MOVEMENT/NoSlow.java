package com.zerosense.mods.impl.MOVEMENT;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.zerosense.Events.Event;
import com.zerosense.Settings.ModeSetting;
import com.zerosense.mods.Module;
import com.zerosense.mods.ModuleManager;
import com.zerosense.mods.impl.COMBAT.Killaura;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow extends Module {
    public ModeSetting mode = new ModeSetting("Mode", "Vanilla", "NCP");
    public NoSlow() {
        super("NoSlow", 0, Category.MOVEMENT);
        this.addSettings(mode);
    }

    @Override
    public void onEvent(Event e){
        this.setDisplayname("NoSlow" + ChatFormatting.GRAY + " - " + this.mode.getMode());
        if(mode.is("Vanilla")) { }
        if(mode.is("NCP")){
            if(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && mc.thePlayer.isUsingItem()){
                if(e.isPre()){
                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }
                if(e.isPost()){
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1,-1,-1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
                }
            }
        }
    }
}