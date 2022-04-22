/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.player;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventPreMotion;
import de.fanta.events.listeners.EventTick;
import de.fanta.module.Module;
import de.fanta.module.impl.combat.TestAura;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class Nofall
extends Module {
    TimeUtil time = new TimeUtil();

    public Nofall() {
        super("Nofall", 0, Module.Type.Player, Color.cyan);
        this.settings.add(new Setting("Modes", new DropdownBox("Vanilla", new String[]{"Vanilla", "Hypixel", "Intave", "AAC", "Verus"})));
    }

    @Override
    public void onEvent(Event event) {
        BlockPos pos = new BlockPos(Nofall.mc.thePlayer.posX, Nofall.mc.thePlayer.posY - 3.0, Nofall.mc.thePlayer.posZ);
        Block block = Nofall.mc.theWorld.getBlockState(pos).getBlock();
        double x = Nofall.mc.thePlayer.posX;
        double y = Nofall.mc.thePlayer.posY;
        double z = Nofall.mc.thePlayer.posZ;
        switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
            case "Vanilla": {
                if (!(event instanceof EventPreMotion) || !(Nofall.mc.thePlayer.fallDistance > 0.0f)) break;
                Nofall.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                break;
            }
            case "Hypixel": {
                if (Client.INSTANCE.moduleManager.getModule("Flight").isState() || !((double)Nofall.mc.thePlayer.fallDistance > 3.0)) break;
                Nofall.mc.thePlayer.onGround = true;
                break;
            }
            case "Verus": {
                if (TestAura.target != null) break;
                if (Client.INSTANCE.moduleManager.getModule("Speed").isState() || Client.INSTANCE.moduleManager.getModule("Flight").isState() && TestAura.target != null) {
                    return;
                }
                if (!(Nofall.mc.thePlayer.fallDistance > 2.0f)) break;
                Nofall.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                Nofall.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(this.getX(), this.getY() - 1.5, this.getZ()), 1, new ItemStack(Blocks.stone.getItem(Nofall.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
                Nofall.mc.thePlayer.motionY = 0.0;
                Nofall.mc.thePlayer.onGround = true;
                if (event instanceof EventTick) {
                    Nofall.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                }
                Nofall.mc.thePlayer.fallDistance = 0.0f;
                break;
            }
            case "AAC": {
                if (!Nofall.mc.thePlayer.onGround) {
                    Nofall.mc.thePlayer.motionY -= 0.04;
                }
                if (!(Nofall.mc.thePlayer.fallDistance > 1.0f) || !this.time.hasReached(10L) || block.getMaterial() != Material.air) break;
                Nofall.mc.thePlayer.onGround = true;
                this.time.reset();
                break;
            }
            case "Intave": {
                if (!Nofall.mc.thePlayer.onGround) {
                    Nofall.mc.thePlayer.motionY -= 0.04;
                }
                if (!(Nofall.mc.thePlayer.fallDistance > 1.0f)) break;
                if (this.time.hasReached(10L) && block.getMaterial() == Material.air) {
                    Nofall.mc.thePlayer.motionY = 0.0;
                    Nofall.mc.thePlayer.setPosition(x, y - 0.4, z);
                    this.time.reset();
                }
                if (block.getMaterial() != Material.air) {
                    Nofall.mc.thePlayer.motionY -= 5.0;
                    mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Double.NaN, y, Double.NaN, true));
                }
                if (!Nofall.mc.thePlayer.onGround || !this.time.hasReached(100L)) break;
                Nofall.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Double.NaN, y, Double.NaN, false));
                Nofall.mc.thePlayer.setPosition(x, y, z);
                this.time.reset();
            }
        }
    }

    public boolean isBlockUnder() {
        int i = (int)Nofall.mc.thePlayer.posY;
        while (i >= 0) {
            BlockPos position = new BlockPos(Nofall.mc.thePlayer.posX, (double)i, Nofall.mc.thePlayer.posZ);
            if (!(Nofall.mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir)) {
                return true;
            }
            --i;
        }
        return false;
    }

    public double getX() {
        return this.getPlayer().posX;
    }

    public double getY() {
        return this.getPlayer().posY;
    }

    public double getZ() {
        return this.getPlayer().posZ;
    }

    public static void sendPacketUnlogged(Packet<? extends INetHandler> packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }
}

