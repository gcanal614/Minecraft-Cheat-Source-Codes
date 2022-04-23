// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.world;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import bozoware.base.BozoWare;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import bozoware.impl.module.player.BlockFly;
import bozoware.impl.module.movement.Flight;
import bozoware.base.util.Wrapper;
import java.util.concurrent.ConcurrentLinkedQueue;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.impl.event.world.onWorldLoadEvent;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.network.PacketSendEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.util.misc.TimerUtil;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.EnumProperty;
import net.minecraft.network.Packet;
import java.util.Queue;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Ping Spoof", moduleCategory = ModuleCategory.WORLD)
public class PingSpoofer extends Module
{
    private final Queue<Packet<?>> packetQueue;
    private final EnumProperty<pingSpoofMode> mode;
    public final ValueProperty<Integer> delay;
    public static final TimerUtil spikeStopwatch;
    public static int stage;
    public final ValueProperty<Double> spikeInterval;
    public final ValueProperty<Double> spikeAmount;
    @EventListener
    EventConsumer<PacketSendEvent> onPacketSendEvent;
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    @EventListener
    EventConsumer<onWorldLoadEvent> onLoadWorldEvent;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    
    public PingSpoofer() {
        this.packetQueue = new ConcurrentLinkedQueue<Packet<?>>();
        this.mode = new EnumProperty<pingSpoofMode>("Mode", pingSpoofMode.Watchdog, this);
        this.delay = new ValueProperty<Integer>("Delay", 500, 100, 5000, this);
        this.spikeInterval = new ValueProperty<Double>("Spike Interval", 10000.0, 5000.0, 20000.0, this);
        this.spikeAmount = new ValueProperty<Double>("Spike Delay", 200.0, 150.0, 500.0, this);
        this.delay.setHidden(true);
        this.mode.onValueChange = (() -> {
            if (this.mode.getPropertyValue().equals(pingSpoofMode.Normal)) {
                this.delay.setHidden(false);
                this.spikeInterval.setHidden(true);
                this.spikeAmount.setHidden(true);
            }
            else {
                this.delay.setHidden(true);
                this.spikeInterval.setHidden(false);
                this.spikeAmount.setHidden(false);
            }
            this.setModuleSuffix(this.mode.getPropertyValue() + "");
            return;
        });
        this.onUpdatePositionEvent = (e -> {
            if (!e.isPre()) {
                return;
            }
            else {
                if (PingSpoofer.stage == 0 && PingSpoofer.spikeStopwatch.hasReached(1000L)) {
                    while (!this.packetQueue.isEmpty()) {
                        Wrapper.sendPacketDirect(this.packetQueue.remove());
                    }
                    PingSpoofer.spikeStopwatch.reset();
                }
                if (PingSpoofer.stage > 0 && this.shouldReset()) {
                    PingSpoofer.stage = 0;
                }
                if (PingSpoofer.stage == 1 && PingSpoofer.spikeStopwatch.hasReached(this.spikeInterval.getPropertyValue().intValue()) && !Flight.getInstance().isModuleToggled() && !BlockFly.getInstance().isModuleToggled()) {
                    PingSpoofer.spikeStopwatch.reset();
                    PingSpoofer.stage = 2;
                }
                return;
            }
        });
        this.onLoadWorldEvent = (e -> {
            this.packetQueue.clear();
            PingSpoofer.spikeStopwatch.reset();
            return;
        });
        this.onModuleEnabled = (() -> this.setModuleSuffix(this.mode.getPropertyValue() + ""));
        this.delay.onValueChange = (() -> this.setModuleSuffix(this.mode.getPropertyValue() + ""));
        S08PacketPlayerPosLook packetIn;
        EntityPlayer entityplayer;
        double d0;
        double d2;
        double d3;
        S32PacketConfirmTransaction packetConfirmTransaction;
        this.onPacketReceiveEvent = (e -> {
            switch (this.mode.getPropertyValue()) {
                case Watchdog: {
                    if (e.getPacket() instanceof S08PacketPlayerPosLook && !Flight.getInstance().isModuleToggled() && PingSpoofer.mc.thePlayer.ticksExisted > 5) {
                        packetIn = (S08PacketPlayerPosLook)e.getPacket();
                        entityplayer = PingSpoofer.mc.thePlayer;
                        d0 = packetIn.getX();
                        d2 = packetIn.getY();
                        d3 = packetIn.getZ();
                        if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X)) {
                            d0 += entityplayer.posX;
                        }
                        else {
                            entityplayer.motionX = 0.0;
                        }
                        if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
                            d2 += entityplayer.posY;
                        }
                        else {
                            entityplayer.motionY = 0.0;
                        }
                        if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
                            d3 += entityplayer.posZ;
                        }
                        else {
                            entityplayer.motionZ = 0.0;
                        }
                        entityplayer.setPosition(d0, d2, d3);
                        PingSpoofer.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(entityplayer.posX, entityplayer.getEntityBoundingBox().minY, entityplayer.posZ, false));
                        if (!PingSpoofer.mc.getNetHandler().doneLoadingTerrain) {
                            entityplayer.prevPosX = entityplayer.posX;
                            entityplayer.prevPosY = entityplayer.posY;
                            entityplayer.prevPosZ = entityplayer.posZ;
                            PingSpoofer.mc.getNetHandler().doneLoadingTerrain = true;
                            PingSpoofer.mc.displayGuiScreen(null);
                        }
                        e.setCancelled(true);
                        if (PingSpoofer.stage == 1) {
                            PingSpoofer.spikeStopwatch.reset();
                            PingSpoofer.stage = 2;
                            BozoWare.getInstance().chat("Spiking... Lagback...");
                        }
                        break;
                    }
                    else if (e.getPacket() instanceof S32PacketConfirmTransaction && PingSpoofer.stage == 0) {
                        packetConfirmTransaction = (S32PacketConfirmTransaction)e.getPacket();
                        if (packetConfirmTransaction.getWindowId() == 0 && packetConfirmTransaction.getActionNumber() < 0) {
                            PingSpoofer.stage = 1;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        C01PacketPing packet;
        this.onPacketSendEvent = (e -> {
            Label_0396_1: {
                switch (this.mode.getPropertyValue()) {
                    case Normal: {
                        if (e.getPacket() instanceof C0FPacketConfirmTransaction) {
                            e.setCancelled(true);
                            this.packetQueue.add(e.getPacket());
                        }
                        if (e.getPacket() instanceof C01PacketPing) {
                            packet = (C01PacketPing)e.getPacket();
                            packet.setClientTime(this.delay.getPropertyValue());
                            e.setPacket(packet);
                        }
                        if (e.getPacket() instanceof C00PacketKeepAlive) {
                            e.setCancelled(true);
                            this.packetQueue.add(e.getPacket());
                        }
                        if (PingSpoofer.spikeStopwatch.hasReached(this.delay.getPropertyValue()) && !this.packetQueue.isEmpty()) {
                            Wrapper.sendPacketDirect(this.packetQueue.poll());
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Watchdog: {
                        switch (PingSpoofer.stage) {
                            case 0: {
                                this.packetQueue.add(e.getPacket());
                                e.setCancelled(true);
                                break Label_0396_1;
                            }
                            case 1: {
                                while (!this.packetQueue.isEmpty()) {
                                    Wrapper.sendPacketDirect(this.packetQueue.remove());
                                }
                                break Label_0396_1;
                            }
                            case 2: {
                                if (e.getPacket() instanceof C03PacketPlayer || e.getPacket() instanceof C00PacketKeepAlive || e.getPacket() instanceof C0FPacketConfirmTransaction) {
                                    this.packetQueue.add(e.getPacket());
                                    e.setCancelled(true);
                                }
                                if (PingSpoofer.spikeStopwatch.hasReached(this.spikeAmount.getPropertyValue().intValue())) {
                                    while (!this.packetQueue.isEmpty()) {
                                        Wrapper.sendPacketDirect(this.packetQueue.remove());
                                    }
                                    PingSpoofer.spikeStopwatch.reset();
                                    BozoWare.getInstance().chat("Flushed");
                                    PingSpoofer.stage = 1;
                                    break Label_0396_1;
                                }
                                else {
                                    break Label_0396_1;
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        });
    }
    
    private boolean shouldReset() {
        return PingSpoofer.mc.thePlayer.ticksExisted < 5;
    }
    
    static {
        spikeStopwatch = new TimerUtil();
        PingSpoofer.stage = 0;
    }
    
    private enum pingSpoofMode
    {
        Normal, 
        Watchdog;
    }
}
