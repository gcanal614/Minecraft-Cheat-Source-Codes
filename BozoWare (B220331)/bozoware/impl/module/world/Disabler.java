// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.world;

import java.util.Iterator;
import java.util.Map;
import bozoware.base.util.misc.MathUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C18PacketSpectate;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import bozoware.impl.module.movement.LongJump;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.apache.commons.lang3.RandomUtils;
import bozoware.impl.module.movement.Flight;
import bozoware.impl.module.combat.Aura;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import bozoware.base.util.player.MovementUtil;
import bozoware.impl.module.movement.Speed;
import bozoware.impl.module.player.BlockFly;
import bozoware.base.BozoWare;
import bozoware.base.util.Wrapper;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.event.world.onWorldLoadEvent;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.impl.event.network.PacketSendEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.util.misc.TimerUtil;
import net.minecraft.network.Packet;
import java.util.LinkedList;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Disabler", moduleCategory = ModuleCategory.WORLD)
public class Disabler extends Module
{
    LinkedList<Packet> packetQueue;
    final TimerUtil timer;
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    @EventListener
    EventConsumer<PacketSendEvent> onPacketSendEvent;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<onWorldLoadEvent> onWorldLoadEvent;
    private final EnumProperty<disablerModes> mode;
    private final BooleanProperty pingSpoofBool;
    private final ValueProperty<Long> pingSpoof;
    private final BooleanProperty strafeFixBool;
    private int bypassValue;
    private double posX;
    private double posY;
    private double posZ;
    private long lastTransaction;
    int CanceledPackets;
    int count;
    boolean didTPVerus;
    private final ConcurrentHashMap<Packet<?>, Long> packets;
    private Deque<Packet<?>> transactionQueue;
    private int lagbacks;
    private boolean spike;
    private TimerUtil timer1;
    private TimerUtil timer2;
    private boolean cancel;
    private TimerUtil spikeTimer;
    private TimerUtil hypixelTimer;
    private TimerUtil packetTimer;
    
    public Disabler() {
        this.packetQueue = new LinkedList<Packet>();
        this.timer = new TimerUtil();
        this.mode = new EnumProperty<disablerModes>("Mode", disablerModes.Watchdog, this);
        this.pingSpoofBool = new BooleanProperty("Ping Spoof", true, this);
        this.pingSpoof = new ValueProperty<Long>("Ping Spoof Delay", 350L, 50L, 30000L, this);
        this.strafeFixBool = new BooleanProperty("Strafe Fix", true, this);
        this.bypassValue = 0;
        this.lastTransaction = 0L;
        this.packets = new ConcurrentHashMap<Packet<?>, Long>();
        this.transactionQueue = new ArrayDeque<Packet<?>>();
        this.lagbacks = 0;
        this.spike = false;
        this.timer1 = new TimerUtil();
        this.timer2 = new TimerUtil();
        this.spikeTimer = new TimerUtil();
        this.hypixelTimer = new TimerUtil();
        this.packetTimer = new TimerUtil();
        this.setModuleSuffix(this.mode.getPropertyValue().toString());
        this.pingSpoof.setHidden(false);
        this.pingSpoofBool.setHidden(false);
        this.onModuleDisabled = (() -> {});
        this.onModuleEnabled = (() -> {
            this.timer.reset();
            this.setModuleSuffix(this.mode.getPropertyValue().toString());
            this.CanceledPackets = 0;
            this.count = 0;
            return;
        });
        this.onWorldLoadEvent = (e -> {
            this.CanceledPackets = 0;
            this.transactionQueue.clear();
            this.packets.clear();
            this.packetQueue.clear();
            this.spikeTimer.reset();
            this.hypixelTimer.reset();
            this.packetTimer.reset();
            this.lastTransaction = 0L;
            this.lagbacks = 0;
            this.spike = false;
            return;
        });
        S08PacketPlayerPosLook s08;
        S08PacketPlayerPosLook S08;
        S08PacketPlayerPosLook s9;
        this.onPacketReceiveEvent = (e -> {
            if (e.getPacket() != null && Disabler.mc.thePlayer != null) {
                Label_0113_3: {
                    switch (this.mode.getPropertyValue()) {
                        case Watchdog: {
                            if (e.getPacket() instanceof S08PacketPlayerPosLook && Disabler.mc.thePlayer.ticksExisted < 150) {
                                s08 = (S08PacketPlayerPosLook)e.getPacket();
                                s08.setY(s08.getY() - 9999.0);
                                break Label_0113_3;
                            }
                            else {
                                break Label_0113_3;
                            }
                            break;
                        }
                        case WatchdogTimer: {
                            if (e.getPacket() instanceof S07PacketRespawn) {
                                this.lagbacks = 0;
                                this.packetQueue.clear();
                                this.spike = false;
                                this.spikeTimer.reset();
                            }
                            if (e.getPacket() instanceof S08PacketPlayerPosLook && Disabler.mc.thePlayer.ticksExisted >= 4 && Disabler.mc.thePlayer.isCollidedVertically) {
                                S08 = (S08PacketPlayerPosLook)e.getPacket();
                                if (this.hypixelTimer.hasReached(2000L) && Disabler.mc.thePlayer.getDistance(S08.getX(), S08.getY(), S08.getZ()) < 10.0) {
                                    ++this.lagbacks;
                                    this.lastTransaction = 6L;
                                    e.setCancelled(true);
                                }
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                        case BlocksMC: {
                            if (e.getPacket() instanceof S08PacketPlayerPosLook && this.didTPVerus) {
                                s9 = (S08PacketPlayerPosLook)e.getPacket();
                                this.didTPVerus = false;
                                e.setCancelled(true);
                                Wrapper.sendPacketDirect(new C03PacketPlayer.C06PacketPlayerPosLook(s9.getX(), s9.getY(), s9.getZ(), s9.getYaw(), s9.getPitch(), true));
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                    }
                }
            }
            return;
        });
        C03PacketPlayer C03;
        C0FPacketConfirmTransaction C0F;
        C03PacketPlayer.C06PacketPlayerPosLook c06;
        Packet<?> p;
        C03PacketPlayer c7;
        this.onPacketSendEvent = (e -> {
            if (this.strafeFixBool.getPropertyValue() && e.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook && !BozoWare.getInstance().getModuleManager().getModuleByClass.apply(BlockFly.class).isModuleToggled() && BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Speed.class).isModuleToggled()) {
                e.setPacket(new C03PacketPlayer.C06PacketPlayerPosLook(((C03PacketPlayer.C06PacketPlayerPosLook)e.getPacket()).getPositionX(), ((C03PacketPlayer.C06PacketPlayerPosLook)e.getPacket()).getPositionY(), ((C03PacketPlayer.C06PacketPlayerPosLook)e.getPacket()).getPositionZ(), MovementUtil.getDirectionStrafeFix(Disabler.mc.thePlayer.moveForward, Disabler.mc.thePlayer.moveStrafing, Disabler.mc.thePlayer.rotationYaw), ((C03PacketPlayer.C06PacketPlayerPosLook)e.getPacket()).getPitch(), ((C03PacketPlayer.C06PacketPlayerPosLook)e.getPacket()).isOnGround()));
            }
            if (e.getPacket() != null && Disabler.mc.thePlayer != null) {
                switch (this.mode.getPropertyValue()) {
                    case WatchdogTimer: {
                        if (e.getPacket() instanceof C03PacketPlayer && this.lastTransaction > 0L) {
                            e.setCancelled(true);
                            --this.lastTransaction;
                            return;
                        }
                        else {
                            if (e.getPacket() instanceof C03PacketPlayer) {
                                C03 = (C03PacketPlayer)e.getPacket();
                                if (!C03.isMoving() && !C03.getRotating() && Disabler.mc.thePlayer.motionY == 0.0) {
                                    e.setCancelled(true);
                                }
                                else {
                                    this.packetQueue.push(e.getPacket());
                                }
                                e.setCancelled(true);
                            }
                            if (e.getPacket() instanceof C00PacketKeepAlive) {
                                this.packetQueue.push(e.getPacket());
                                e.setCancelled(true);
                            }
                            if (e.getPacket() instanceof C0FPacketConfirmTransaction) {
                                C0F = (C0FPacketConfirmTransaction)e.getPacket();
                                this.packetQueue.push(e.getPacket());
                                ++this.lastTransaction;
                                Wrapper.sendPacketDirect(new C0CPacketInput());
                                e.setCancelled(true);
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        break;
                    }
                    case Watchdog: {
                        if (e.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook && Disabler.mc.thePlayer.ticksExisted < 100) {
                            c06 = (C03PacketPlayer.C06PacketPlayerPosLook)e.getPacket();
                            c06.setY(0.0);
                        }
                        if (this.pingSpoofBool.getPropertyValue() && !BozoWare.getInstance().getModuleManager().getModuleByClass.apply(BlockFly.class).isModuleToggled() && Aura.target == null && !BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Flight.class).isModuleToggled()) {
                            if (Disabler.mc.isSingleplayer()) {
                                return;
                            }
                            else {
                                p = e.getPacket();
                                if (p instanceof C0FPacketConfirmTransaction || p instanceof C00PacketKeepAlive) {
                                    this.packets.put(p, System.currentTimeMillis() + this.pingSpoof.getPropertyValue());
                                    e.setCancelled(true);
                                }
                                break;
                            }
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Viper: {
                        if (this.timer.hasReached(5250L) || Disabler.mc.thePlayer.ticksExisted < 20) {
                            if (e.getPacket() instanceof C0FPacketConfirmTransaction) {
                                e.setCancelled(true);
                                Wrapper.sendPacketDelayed(e.getPacket(), this.pingSpoof.getPropertyValue());
                            }
                            if (e.getPacket() instanceof C00PacketKeepAlive) {
                                e.setCancelled(true);
                                Wrapper.sendPacketDelayed(e.getPacket(), this.pingSpoof.getPropertyValue());
                            }
                            if (this.timer.hasReached(6250L + this.pingSpoof.getPropertyValue())) {
                                this.timer.reset();
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
                    case BlocksMC: {
                        if (e.getPacket() instanceof C03PacketPlayer) {
                            Disabler.mc.thePlayer.sendQueue.addToSendQueue(new C0CPacketInput());
                            c7 = (C03PacketPlayer)e.getPacket();
                            if (Disabler.mc.thePlayer.ticksExisted % 20 == 0) {
                                c7.y = RandomUtils.nextDouble(0.0, 1000.0);
                                c7.setOnGround(false);
                                this.didTPVerus = true;
                            }
                        }
                        if (e.getPacket() instanceof C0FPacketConfirmTransaction) {
                            e.setCancelled(true);
                            this.packetQueue.add(e.getPacket());
                        }
                        if (e.getPacket() instanceof C00PacketKeepAlive) {
                            ((C00PacketKeepAlive)e.getPacket()).setKey(-2);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case NoPayload: {
                        if (e.getPacket() instanceof C17PacketCustomPayload) {
                            e.setCancelled(true);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
            }
            return;
        });
        Iterator<Packet<?>> iterator;
        Packet<?> packet;
        Iterator<Packet<?>> iterator2;
        Packet<?> packet2;
        final C08PacketPlayerBlockPlacement packet3;
        Iterator<Map.Entry<Packet<?>, Long>> iterator3;
        Map.Entry<Packet<?>, Long> entry;
        this.onUpdatePositionEvent = (e -> {
            this.setModuleSuffix(this.mode.getPropertyValue().name());
            if (this.strafeFixBool.getPropertyValue() && ((!BozoWare.getInstance().getModuleManager().getModuleByClass.apply(BlockFly.class).isModuleToggled() && BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Speed.class).isModuleToggled()) || BozoWare.getInstance().getModuleManager().getModuleByClass.apply(LongJump.class).isModuleToggled())) {
                e.setYaw(MovementUtil.getDirectionStrafeFix(Disabler.mc.thePlayer.moveForward, Disabler.mc.thePlayer.moveStrafing, Disabler.mc.thePlayer.rotationYaw));
                Disabler.mc.thePlayer.rotationYawHead = MovementUtil.getDirectionStrafeFix(Disabler.mc.thePlayer.moveForward, Disabler.mc.thePlayer.moveStrafing, Disabler.mc.thePlayer.rotationYaw);
            }
            if (Disabler.mc.thePlayer != null) {
                switch (this.mode.getPropertyValue()) {
                    case WatchdogTimer: {
                        if (!e.isPre()) {
                            if (Minecraft.getMinecraft().theWorld == null) {
                                this.packetQueue.clear();
                            }
                            if (this.packetTimer.hasReached((Disabler.mc.thePlayer.ticksExisted < 70) ? 1250L : ((long)this.pingSpoof.getPropertyValue()))) {
                                iterator = this.packetQueue.descendingIterator();
                                while (iterator.hasNext()) {
                                    packet = iterator.next();
                                    Wrapper.sendPacketDirect(packet);
                                }
                                System.out.println("Cleared Queue");
                                this.packetQueue.clear();
                                this.spike = false;
                            }
                            if (Disabler.mc.thePlayer.ticksExisted == 40) {
                                BozoWare.getInstance().chat("Disabled Watchdog!");
                                e.setX(e.getX() + 1.0);
                                e.setZ(e.getZ() + 1.0);
                            }
                            if (this.spikeTimer.hasReached(10000L)) {
                                this.spike = true;
                                this.packetTimer.reset();
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            iterator2 = this.packetQueue.descendingIterator();
                            while (iterator2.hasNext()) {
                                packet2 = iterator2.next();
                                Wrapper.sendPacketDirect(packet2);
                            }
                            this.packetQueue.clear();
                            this.packetTimer.reset();
                            break;
                        }
                        break;
                    }
                    case Viper:
                    case BlocksMC: {
                        if (!this.packetQueue.isEmpty()) {
                            if (Disabler.mc.thePlayer.ticksExisted % 25 == 0) {
                                Wrapper.sendPacketDirect(this.packetQueue.remove(0));
                                if (this.packetQueue.size() > 4) {
                                    Wrapper.sendPacketDirect(this.packetQueue.poll());
                                }
                            }
                            if (Disabler.mc.thePlayer.ticksExisted % 250 == 0) {
                                this.packetQueue.clear();
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
                    case Spectator: {
                        Wrapper.sendPacketDirect(new C18PacketSpectate(UUID.randomUUID()));
                        break;
                    }
                    case NullPlace: {
                        new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 1, null, 0.0f, 0.0f, 0.0f);
                        Wrapper.sendPacketDirect(packet3);
                        break;
                    }
                    case TransactionSpam: {
                        if (Disabler.mc.thePlayer.ticksExisted % 5 == 0) {
                            Wrapper.sendPacketDirect(new C0FPacketConfirmTransaction(0, (short)MathUtil.getRandomInRange(-32767.0, 32767.0), true));
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Watchdog: {
                        if (Disabler.mc.isSingleplayer()) {
                            return;
                        }
                        else {
                            iterator3 = this.packets.entrySet().iterator();
                            while (iterator3.hasNext()) {
                                entry = iterator3.next();
                                if (entry.getValue() < System.currentTimeMillis()) {
                                    Wrapper.sendPacketDirect(entry.getKey());
                                    iterator3.remove();
                                }
                            }
                            break;
                        }
                        break;
                    }
                }
            }
            this.mode.onValueChange = (() -> {
                if (this.mode.getPropertyValue().equals(disablerModes.Watchdog)) {
                    this.pingSpoof.setHidden(false);
                    this.pingSpoofBool.setHidden(false);
                    this.setModuleSuffix(this.mode.getPropertyValue().name);
                }
                else {
                    this.pingSpoof.setHidden(true);
                    this.pingSpoofBool.setHidden(true);
                    this.setModuleSuffix(this.mode.getPropertyValue().name);
                }
            });
        });
    }
    
    public double getPosX() {
        return this.posX;
    }
    
    public double getPosY() {
        return this.posY;
    }
    
    public double getPosZ() {
        return this.posZ;
    }
    
    private boolean checkAction(final short action) {
        return action > 0 && action < 100;
    }
    
    private enum disablerModes
    {
        Watchdog("Watchdog"), 
        WatchdogTimer("Watchdog Timer"), 
        NoPayload("No Payload"), 
        Spectator("Spectator"), 
        TransactionSpam("Transaction Spam"), 
        BlocksMC("BlocksMC"), 
        Viper("Viper"), 
        NullPlace("Null Place");
        
        private final String name;
        
        private disablerModes(final String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }
    
    private class TimestampedPacket
    {
        public final Packet packet;
        private long timestamp;
        
        public TimestampedPacket(final Packet packet, final long timestamp) {
            this.packet = packet;
            this.timestamp = timestamp;
        }
    }
}
