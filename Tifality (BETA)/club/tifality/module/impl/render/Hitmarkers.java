/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.render;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.packet.PacketReceiveEvent;
import club.tifality.manager.event.impl.packet.PacketSendEvent;
import club.tifality.manager.event.impl.render.Render2DEvent;
import club.tifality.manager.event.impl.world.PlaySoundEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.utils.render.LockedResolution;
import club.tifality.utils.render.OGLUtils;
import club.tifality.utils.render.RenderingUtils;
import club.tifality.utils.timer.TimerUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import org.lwjgl.opengl.GL11;

@ModuleInfo(label="Hitmarker", category=ModuleCategory.RENDER)
public final class Hitmarkers
extends Module {
    private final Property<Integer> hitColorProperty = new Property<Integer>("Hit Color", -1);
    private final Property<Integer> killColorProperty = new Property<Integer>("Kill Color", -65536);
    private final DoubleProperty xOffsetProperty = new DoubleProperty("Offset", 2.0, 0.5, 10.0, 0.5);
    private final DoubleProperty lengthProperty = new DoubleProperty("Length", 4.0, 0.5, 10.0, 0.5);
    private final DoubleProperty hitMarkerThicknessProperty = new DoubleProperty("Thickness", 1.0, 0.5, 3.0, 0.5);
    private final Property<Boolean> soundsProperty = new Property<Boolean>("Hit Sound", true);
    private final TimerUtil attackTimeOut = new TimerUtil();
    private final TimerUtil killTimeOut = new TimerUtil();
    private int color;
    private double progress;
    private int lastAttackedEntity;
    private int toBeKilledEntity;

    @Listener
    private void onPacketReceive(PacketReceiveEvent event) {
        S19PacketEntityStatus packetEntityStatus;
        int entityId;
        Packet<?> packet = event.getPacket();
        if (packet instanceof S19PacketEntityStatus && ((entityId = (packetEntityStatus = (S19PacketEntityStatus)packet).getEntityId()) == this.lastAttackedEntity || !this.killTimeOut.hasElapsed(50L) && entityId == this.toBeKilledEntity)) {
            switch (packetEntityStatus.getOpCode()) {
                case 2: {
                    this.color = this.hitColorProperty.getValue();
                    this.progress = 1.0;
                    this.killTimeOut.reset();
                    this.toBeKilledEntity = this.lastAttackedEntity;
                    break;
                }
                case 3: {
                    this.color = this.killColorProperty.getValue();
                    this.progress = 1.0;
                    this.toBeKilledEntity = -1;
                }
            }
            this.lastAttackedEntity = -1;
        }
    }

    @Listener
    private void onPacketSend(PacketSendEvent event) {
        Packet<?> packet = event.getPacket();
        if (packet instanceof C02PacketUseEntity) {
            C02PacketUseEntity packetUseEntity = (C02PacketUseEntity)packet;
            if (packetUseEntity.getAction() == C02PacketUseEntity.Action.ATTACK) {
                this.lastAttackedEntity = packetUseEntity.getEntityId();
                this.attackTimeOut.reset();
            }
        } else if (packet instanceof C03PacketPlayer && this.lastAttackedEntity != -1 && this.attackTimeOut.hasElapsed(500L)) {
            this.lastAttackedEntity = -1;
        }
    }

    @Listener
    private void onRender2D(Render2DEvent event) {
        if (this.progress > 0.0) {
            this.progress = RenderingUtils.linearAnimation(this.progress, 0.0, 0.02);
            LockedResolution resolution = event.getResolution();
            double xMiddle = (double)resolution.getWidth() / 2.0;
            double yMiddle = (double)resolution.getHeight() / 2.0;
            GL11.glPushMatrix();
            OGLUtils.enableBlending();
            GL11.glDisable(3553);
            GL11.glHint(3155, 4354);
            GL11.glEnable(2881);
            GL11.glTranslated(xMiddle, yMiddle, 0.0);
            GL11.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
            OGLUtils.color(RenderingUtils.fadeTo(Hitmarkers.removeAlphaComponent(this.color), this.color, (float)this.progress));
            for (int i = 0; i < 4; ++i) {
                Hitmarkers.drawHitMarker((Double)this.xOffsetProperty.getValue(), (Double)this.lengthProperty.getValue(), (Double)this.hitMarkerThicknessProperty.getValue());
                if (i == 3) continue;
                GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glDisable(2881);
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
        }
    }

    @Listener
    private void onPlaySound(PlaySoundEvent event) {
        event.setCancelled(this.soundsProperty.getValue() == false);
    }

    private static int removeAlphaComponent(int color) {
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
    }

    private static void drawHitMarker(double xOffset, double length, double width) {
        double halfWidth = width * 0.5;
        GL11.glBegin(7);
        GL11.glVertex2d(-(xOffset + length), -halfWidth);
        GL11.glVertex2d(-(xOffset + length), halfWidth);
        GL11.glVertex2d(-xOffset, halfWidth);
        GL11.glVertex2d(-xOffset, -halfWidth);
        GL11.glEnd();
    }
}

