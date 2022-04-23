// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import bozoware.base.util.player.MovementUtil;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.Entity;
import java.util.Iterator;
import bozoware.base.util.misc.MathUtil;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.Packet;
import bozoware.impl.module.movement.Flight;
import bozoware.base.BozoWare;
import net.minecraft.util.BlockPos;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.property.ValueProperty;
import net.minecraft.network.play.client.C03PacketPlayer;
import java.util.ArrayList;
import bozoware.base.util.misc.TimerUtil;
import bozoware.impl.event.network.PacketSendEvent;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Anti Void", moduleCategory = ModuleCategory.WORLD)
public class AntiVoid extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    @EventListener
    EventConsumer<PacketSendEvent> onPacketSendEvent;
    public static int flags;
    TimerUtil timer;
    public static ArrayList<C03PacketPlayer> packets;
    public double[] lastGroundPos;
    private final ValueProperty<Integer> distance;
    private final EnumProperty<damode> antivoid;
    BlockPos lastPos;
    public boolean damaged;
    
    public AntiVoid() {
        this.timer = new TimerUtil();
        this.lastGroundPos = new double[3];
        this.distance = new ValueProperty<Integer>("Distance", 8, 1, 16, this);
        this.antivoid = new EnumProperty<damode>("Mode", damode.Hypixel, this);
        this.distance.setHidden(true);
        C03PacketPlayer packet;
        final Iterator<C03PacketPlayer> iterator;
        C03PacketPlayer p;
        this.onPacketSendEvent = (e -> {
            switch (this.antivoid.getPropertyValue()) {
                case Hypixel: {
                    if (!AntiVoid.packets.isEmpty() && AntiVoid.mc.thePlayer.ticksExisted < 100) {
                        AntiVoid.packets.clear();
                    }
                    if (AntiVoid.mc.thePlayer.ticksExisted < 150) {
                        return;
                    }
                    else if (e.getPacket() instanceof C03PacketPlayer && !BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Flight.class).isModuleToggled()) {
                        packet = (C03PacketPlayer)e.getPacket();
                        if (isInVoid()) {
                            if (this.timer.hasReached(400L) && AntiVoid.mc.thePlayer.motionY < 0.0) {
                                e.setCancelled(true);
                                AntiVoid.packets.add(packet);
                                if (this.timer.hasReached(700L) && !isBlockUnder()) {
                                    AntiVoid.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.lastGroundPos[0], this.lastGroundPos[1] - 1.0, this.lastGroundPos[2], true));
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            this.timer.reset();
                            this.lastGroundPos[0] = AntiVoid.mc.thePlayer.posX;
                            this.lastGroundPos[1] = AntiVoid.mc.thePlayer.posY;
                            this.lastGroundPos[2] = AntiVoid.mc.thePlayer.posZ;
                            if (!AntiVoid.packets.isEmpty()) {
                                AntiVoid.packets.iterator();
                                while (iterator.hasNext()) {
                                    p = iterator.next();
                                    AntiVoid.mc.getNetHandler().getNetworkManager().sendPacket(p);
                                }
                                AntiVoid.packets.clear();
                            }
                            this.timer.reset();
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
        this.onPacketReceiveEvent = (e -> {
            if (e.getPacket() instanceof S08PacketPlayerPosLook && !isBlockUnder()) {
                ++AntiVoid.flags;
            }
            if (e.getPacket() instanceof S08PacketPlayerPosLook) {
                AntiVoid.packets.clear();
            }
            return;
        });
        this.onUpdatePositionEvent = (e -> {
            if (AntiVoid.mc.thePlayer.onGround) {
                this.lastPos = AntiVoid.mc.thePlayer.getPosition();
            }
            switch (this.antivoid.getPropertyValue()) {
                case Motion: {
                    if (AntiVoid.mc.thePlayer.fallDistance > this.distance.getPropertyValue() && !isBlockUnder() && !this.timer.hasReached(1000L)) {
                        AntiVoid.mc.thePlayer.motionX = 0.2;
                        AntiVoid.mc.thePlayer.motionZ = 0.2;
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case Stop: {
                    if (AntiVoid.mc.thePlayer.fallDistance > this.distance.getPropertyValue() && !isBlockUnder()) {
                        if (!this.timer.hasReached(1000L)) {
                            AntiVoid.mc.thePlayer.motionY = 0.0;
                            AntiVoid.mc.thePlayer.motionX = 0.0;
                            AntiVoid.mc.thePlayer.motionZ = 0.0;
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        this.timer.reset();
                        break;
                    }
                    break;
                }
                case Jump: {
                    if (AntiVoid.flags < 2 && AntiVoid.mc.thePlayer.fallDistance > this.distance.getPropertyValue() && !isBlockUnder()) {
                        AntiVoid.mc.thePlayer.jump();
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case Position: {
                    if (AntiVoid.flags < 8 && !Jesus.isInLiquid() && !Jesus.isOnLiquid() && AntiVoid.mc.thePlayer.fallDistance > this.distance.getPropertyValue() && !isBlockUnder() && this.lastPos != null) {
                        e.setX(e.getX() + MathUtil.getRandomInRange(-0.30000001192092896, 0.30000001192092896));
                        e.setZ(e.getZ() + MathUtil.getRandomInRange(-0.30000001192092896, 0.30000001192092896));
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            if (AntiVoid.mc.thePlayer.onGround) {
                AntiVoid.flags = 0;
            }
            return;
        });
        this.antivoid.onValueChange = (() -> {
            this.setModuleSuffix(this.antivoid.getPropertyValue().name());
            if (this.antivoid.getPropertyValue().equals(damode.Hypixel)) {
                this.distance.setHidden(true);
            }
            else {
                this.distance.setHidden(false);
            }
        });
    }
    
    public static boolean isBlockUnder() {
        for (int offset = 0; offset < AntiVoid.mc.thePlayer.posY + AntiVoid.mc.thePlayer.getEyeHeight(); offset += 2) {
            final AxisAlignedBB boundingBox = AntiVoid.mc.thePlayer.getEntityBoundingBox().offset(0.0, -offset, 0.0);
            if (!AntiVoid.mc.theWorld.getCollidingBoundingBoxes(AntiVoid.mc.thePlayer, boundingBox).isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isInVoid() {
        for (int i = 0; i <= 128; ++i) {
            if (MovementUtil.isOnGround(i)) {
                return false;
            }
        }
        return true;
    }
    
    static {
        AntiVoid.packets = new ArrayList<C03PacketPlayer>();
    }
    
    private enum damode
    {
        Hypixel, 
        Jump, 
        Stop, 
        Motion, 
        Position;
    }
}
