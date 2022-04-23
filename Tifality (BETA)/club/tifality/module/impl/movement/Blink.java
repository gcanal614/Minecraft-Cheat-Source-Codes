/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.movement;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.packet.PacketSendEvent;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.manager.event.impl.render.Render3DEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.utils.render.RenderingUtils;
import club.tifality.utils.timer.TimerUtil;
import java.awt.Color;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.lwjgl.opengl.GL11;

@ModuleInfo(label="Blink", category=ModuleCategory.MOVEMENT)
public final class Blink
extends Module {
    private final LinkedBlockingQueue<Packet> packets = new LinkedBlockingQueue();
    private EntityOtherPlayerMP fakePlayer = null;
    private boolean disableLogger;
    private final LinkedList<double[]> positions = new LinkedList();
    private final Property<Boolean> pulseValue = new Property<Boolean>("Pulse", false);
    private final DoubleProperty pulseDelayValue = new DoubleProperty("Pulse Delay", 1000.0, 500.0, 5000.0, 100.0);
    private final Property<Boolean> renderValue = new Property<Boolean>("Render", true);
    private final TimerUtil pulseTimer = new TimerUtil();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onEnable() {
        if (Blink.mc.thePlayer == null) {
            return;
        }
        if (!this.pulseValue.get().booleanValue()) {
            this.fakePlayer = new EntityOtherPlayerMP(Blink.mc.theWorld, Blink.mc.thePlayer.getGameProfile());
            this.fakePlayer.clonePlayer(Blink.mc.thePlayer, true);
            this.fakePlayer.copyLocationAndAnglesFrom(Blink.mc.thePlayer);
            this.fakePlayer.rotationYawHead = Blink.mc.thePlayer.rotationYawHead;
            Blink.mc.theWorld.addEntityToWorld(-1337, this.fakePlayer);
        }
        LinkedList<double[]> linkedList = this.positions;
        synchronized (linkedList) {
            this.positions.add(new double[]{Blink.mc.thePlayer.posX, Blink.mc.thePlayer.getEntityBoundingBox().minY + (double)(Blink.mc.thePlayer.getEyeHeight() / 2.0f), Blink.mc.thePlayer.posZ});
            this.positions.add(new double[]{Blink.mc.thePlayer.posX, Blink.mc.thePlayer.getEntityBoundingBox().minY, Blink.mc.thePlayer.posZ});
        }
        this.pulseTimer.reset();
    }

    @Override
    public void onDisable() {
        if (Blink.mc.thePlayer == null) {
            return;
        }
        this.blink();
        if (this.fakePlayer != null) {
            Blink.mc.theWorld.removeEntityFromWorld(this.fakePlayer.getEntityId());
            this.fakePlayer = null;
        }
    }

    @Listener
    public void onPacket(PacketSendEvent event) {
        Packet<?> packet = event.getPacket();
        if (Blink.mc.thePlayer == null || this.disableLogger) {
            return;
        }
        if (packet instanceof C03PacketPlayer) {
            event.setCancelled();
        }
        if (packet instanceof C03PacketPlayer.C04PacketPlayerPosition || packet instanceof C03PacketPlayer.C06PacketPlayerPosLook || packet instanceof C08PacketPlayerBlockPlacement || packet instanceof C0APacketAnimation || packet instanceof C0BPacketEntityAction || packet instanceof C02PacketUseEntity) {
            event.setCancelled();
            this.packets.add(packet);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Listener
    public void onUpdate(UpdatePositionEvent event) {
        LinkedList<double[]> linkedList = this.positions;
        synchronized (linkedList) {
            this.positions.add(new double[]{Blink.mc.thePlayer.posX, Blink.mc.thePlayer.getEntityBoundingBox().minY, Blink.mc.thePlayer.posZ});
        }
        if (this.pulseValue.get().booleanValue() && this.pulseTimer.hasTimePassed(((Double)this.pulseDelayValue.get()).intValue())) {
            this.blink();
            this.pulseTimer.reset();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Listener
    public void onRender3D(Render3DEvent event) {
        if (this.renderValue.get().booleanValue()) {
            Color color = new Color(255, 255, 255);
            LinkedList<double[]> linkedList = this.positions;
            synchronized (linkedList) {
                GL11.glPushMatrix();
                GL11.glDisable(3553);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(2848);
                GL11.glEnable(3042);
                GL11.glDisable(2929);
                Blink.mc.entityRenderer.disableLightmap();
                GL11.glBegin(3);
                RenderingUtils.glColor(color);
                double renderPosX = RenderManager.viewerPosX;
                double renderPosY = RenderManager.viewerPosY;
                double renderPosZ = RenderManager.viewerPosZ;
                for (double[] pos : this.positions) {
                    GL11.glVertex3d(pos[0] - renderPosX, pos[1] - renderPosY, pos[2] - renderPosZ);
                }
                GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
                GL11.glEnd();
                GL11.glEnable(2929);
                GL11.glDisable(2848);
                GL11.glDisable(3042);
                GL11.glEnable(3553);
                GL11.glPopMatrix();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void blink() {
        try {
            this.disableLogger = true;
            while (!this.packets.isEmpty()) {
                mc.getNetHandler().getNetworkManager().sendPacket(this.packets.take());
            }
            this.disableLogger = false;
        }
        catch (Exception e) {
            e.printStackTrace();
            this.disableLogger = false;
        }
        LinkedList<double[]> linkedList = this.positions;
        synchronized (linkedList) {
            this.positions.clear();
        }
    }
}

