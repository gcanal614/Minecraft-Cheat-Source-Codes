/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.concurrent.GenericFutureListener
 */
package de.fanta.module.impl.miscellaneous;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventNoClip;
import de.fanta.events.listeners.EventReceivedPacket;
import de.fanta.module.Module;
import de.fanta.module.impl.combat.Killaura;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.utils.TimeUtil;
import io.netty.util.concurrent.GenericFutureListener;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;

public class Disabler
extends Module {
    public ArrayList<Packet> packets = new ArrayList();
    public LinkedList packetQueue = new LinkedList();
    public int state;
    public int state2;
    public int state3;
    public int stage;
    public int stage2;
    public int stage3;
    public TimeUtil helper = new TimeUtil();
    public TimeUtil helper2 = new TimeUtil();

    public Disabler() {
        super("Disabler", 0, Module.Type.Misc, Color.WHITE);
        this.settings.add(new Setting("NoClip", new CheckBox(false)));
        this.settings.add(new Setting("NoFall Test", new CheckBox(false)));
        this.settings.add(new Setting("InGround", new CheckBox(false)));
        this.settings.add(new Setting("AdjusttoGroundState", new CheckBox(false)));
        this.settings.add(new Setting("CancelFlag", new CheckBox(false)));
        this.settings.add(new Setting("Transaction", new CheckBox(false)));
        this.settings.add(new Setting("(Transaction)Multiply", new CheckBox(false)));
        this.settings.add(new Setting("TransactionSend", new CheckBox(false)));
        this.settings.add(new Setting("KeepAlive", new CheckBox(false)));
        this.settings.add(new Setting("(KeepAlive)Multiply", new CheckBox(false)));
        this.settings.add(new Setting("Modes", new DropdownBox("VerusSemi", new String[]{"VerusSemi", "BlocksMC"})));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Disabler.mc.thePlayer.ticksExisted = 0;
    }

    @Override
    public void onDisable() {
        if (this.packets != null && this.packets.size() > 0) {
            this.packets.clear();
        }
        if (this.packetQueue != null && this.packetQueue.size() > 0) {
            this.packetQueue.clear();
        }
        this.helper.setLastMS();
        this.helper2.setLastMS();
        this.state = 0;
        this.state2 = 0;
        this.state3 = 0;
        this.stage = 0;
        this.stage2 = 0;
        this.stage3 = 0;
        super.onDisable();
    }

    @Override
    public void onEvent(Event event) {
        block11: {
            if (!(event instanceof EventReceivedPacket)) break block11;
            switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
                case "VerusSemi": {
                    if (((CheckBox)this.getSetting((String)"NoClip").getSetting()).state && event instanceof EventNoClip) {
                        ((EventNoClip)event).noClip = true;
                    }
                    if (Killaura.hasTarget()) break;
                    Packet p = EventReceivedPacket.INSTANCE.getPacket();
                    boolean cfr_ignored_0 = p instanceof C03PacketPlayer.C04PacketPlayerPosition;
                    if (p instanceof S32PacketConfirmTransaction) {
                        S32PacketConfirmTransaction packet = (S32PacketConfirmTransaction)p;
                        if (p instanceof C0FPacketConfirmTransaction) {
                            C0FPacketConfirmTransaction packetConfirmTransaction = (C0FPacketConfirmTransaction)p;
                            mc.getNetHandler().addToSendQueue(new C0FPacketConfirmTransaction(Integer.MAX_VALUE, packetConfirmTransaction.getUid(), true));
                            event.setCancelled(true);
                        }
                        if (packet.getActionNumber() >= 0) {
                            EventReceivedPacket.INSTANCE.setCancelled(true);
                        }
                    }
                    int cfr_ignored_1 = Disabler.mc.thePlayer.ticksExisted % 30;
                    int cfr_ignored_2 = Disabler.mc.thePlayer.ticksExisted % 25;
                    boolean cfr_ignored_3 = p instanceof C0FPacketConfirmTransaction;
                    if (!(p instanceof C00PacketKeepAlive)) break;
                    EventReceivedPacket.INSTANCE.setCancelled(true);
                }
            }
        }
    }

    static void sendAbilities() {
        PlayerCapabilities capabilities = new PlayerCapabilities();
        capabilities.isFlying = true;
        capabilities.allowFlying = true;
        capabilities.isCreativeMode = true;
        Disabler.mc.thePlayer.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(capabilities));
    }

    public void sendPacketSilent(Packet packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet, null, new GenericFutureListener[0]);
    }
}

