/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.concurrent.GenericFutureListener
 */
package de.fanta.module.impl.miscellaneous;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventPacket;
import de.fanta.events.listeners.EventRender3D;
import de.fanta.events.listeners.EventUpdate;
import de.fanta.module.Module;
import de.fanta.utils.TimeUtil;
import io.netty.util.concurrent.GenericFutureListener;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import net.minecraft.block.BlockAir;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;

public class FullAntiVerus
extends Module {
    public ArrayList<Packet> packets = new ArrayList();
    public LinkedList packetQueue = new LinkedList();
    public int state;
    public int state2;
    public int state3;
    public int stage;
    public int stage2;
    public int stage3;
    public TimeUtil timer = new TimeUtil();
    public TimeUtil timer2 = new TimeUtil();
    private boolean inGround = true;
    private boolean cancelFlag = true;
    private boolean transaction = true;
    private boolean transactionMultiply = true;
    private boolean transactionSend = true;

    public FullAntiVerus() {
        super("FullAntiVerus", 0, Module.Type.Misc, Color.GREEN);
    }

    @Override
    public void onEvent(Event e) {
        boolean send;
        if (e instanceof EventUpdate && (send = this.transactionSend)) {
            while (this.packetQueue.size() > 22) {
                this.sendPacketSilent((Packet)this.packetQueue.poll());
                e.setCancelled(true);
            }
        }
        if (e instanceof EventPacket) {
            if (FullAntiVerus.mc.thePlayer.ticksExisted % 2 != 1) {
                e.setCancelled(false);
            }
            if (FullAntiVerus.mc.thePlayer.ticksExisted % 2 == 1) {
                boolean cfr_ignored_0 = e instanceof EventRender3D;
            }
            if (FullAntiVerus.mc.thePlayer != null && FullAntiVerus.mc.thePlayer.ticksExisted == 0) {
                this.packetQueue.clear();
            }
            EventPacket cfr_ignored_1 = (EventPacket)e;
            Packet packet = EventPacket.getPacket();
            if (packet instanceof S08PacketPlayerPosLook) {
                if (this.cancelFlag) {
                    e.setCancelled(true);
                    C0FPacketConfirmTransaction packetConfirmTransaction = (C0FPacketConfirmTransaction)packet;
                    mc.getNetHandler().addToSendQueue(new C0FPacketConfirmTransaction(Integer.MAX_VALUE, packetConfirmTransaction.getUid(), true));
                    double x = ((S08PacketPlayerPosLook)packet).getX() - FullAntiVerus.mc.thePlayer.posX;
                    double y = ((S08PacketPlayerPosLook)packet).getY() - FullAntiVerus.mc.thePlayer.posY;
                    double z = ((S08PacketPlayerPosLook)packet).getZ() - FullAntiVerus.mc.thePlayer.posZ;
                    double diff = Math.sqrt(x * x + y * y + z * z);
                    e.setCancelled(true);
                    if (diff <= 0.5) {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(((S08PacketPlayerPosLook)packet).getX(), ((S08PacketPlayerPosLook)packet).getY(), ((S08PacketPlayerPosLook)packet).getZ(), ((S08PacketPlayerPosLook)packet).getYaw(), ((S08PacketPlayerPosLook)packet).getPitch(), false));
                        e.setCancelled(true);
                    }
                }
            } else if (packet instanceof C03PacketPlayer) {
                if (this.inGround) {
                    boolean cfr_ignored_2 = packet instanceof C03PacketPlayer.C04PacketPlayerPosition;
                    boolean cfr_ignored_3 = packet instanceof C13PacketPlayerAbilities;
                    boolean cfr_ignored_4 = packet instanceof C03PacketPlayer.C05PacketPlayerLook;
                    boolean cfr_ignored_5 = packet instanceof C03PacketPlayer.C05PacketPlayerLook;
                    if (packet instanceof C13PacketPlayerAbilities) {
                        FullAntiVerus.mc.thePlayer.isEating();
                    }
                    if (FullAntiVerus.mc.thePlayer.ticksExisted % 85 == 0) {
                        this.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(FullAntiVerus.mc.thePlayer.posX, FullAntiVerus.mc.thePlayer.posY, FullAntiVerus.mc.thePlayer.posZ, false));
                        this.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(FullAntiVerus.mc.thePlayer.posX, FullAntiVerus.mc.thePlayer.posY - 90.0, FullAntiVerus.mc.thePlayer.posZ, true));
                        this.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(FullAntiVerus.mc.thePlayer.posX, FullAntiVerus.mc.thePlayer.posY, FullAntiVerus.mc.thePlayer.posZ, false));
                        e.setCancelled(true);
                    }
                }
            } else if (packet instanceof C0FPacketConfirmTransaction) {
                boolean c0f = this.transaction;
                boolean c0fMultiply = this.transactionMultiply;
                if (c0f) {
                    if (c0fMultiply) {
                        int i = 0;
                        while (i < 1) {
                            this.packetQueue.add(packet);
                            ++i;
                        }
                        e.setCancelled(true);
                    } else {
                        this.packetQueue.add(packet);
                    }
                    e.setCancelled(true);
                }
            }
        }
    }

    public void sendPacketSilent(Packet packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet, null, new GenericFutureListener[0]);
    }

    @Override
    public void onDisable() {
        if (this.packets != null && this.packets.size() > 0) {
            this.packets.clear();
        }
        if (this.packetQueue != null && this.packetQueue.size() > 0) {
            this.packetQueue.clear();
        }
        this.timer.reset();
        this.timer2.reset();
        this.state = 0;
        this.state2 = 0;
        this.state3 = 0;
        this.stage = 0;
        this.stage2 = 0;
        this.stage3 = 0;
    }

    @Override
    public void onEnable() {
        FullAntiVerus.mc.thePlayer.ticksExisted = 0;
    }

    public boolean isBlockUnder() {
        int i = (int)FullAntiVerus.mc.thePlayer.posY;
        while (i >= 0) {
            BlockPos position = new BlockPos(FullAntiVerus.mc.thePlayer.posX, (double)i, FullAntiVerus.mc.thePlayer.posZ);
            if (!(FullAntiVerus.mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir)) {
                return true;
            }
            --i;
        }
        return false;
    }
}

