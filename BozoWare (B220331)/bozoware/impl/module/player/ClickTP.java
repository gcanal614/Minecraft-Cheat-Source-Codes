// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.MouseClickEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Click TP", moduleCategory = ModuleCategory.PLAYER)
public class ClickTP extends Module
{
    private boolean dispatchTeleport;
    @EventListener
    EventConsumer<MouseClickEvent> onMouseClickEvent;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    private final ValueProperty<Double> blocks;
    
    public ClickTP() {
        this.blocks = new ValueProperty<Double>("Reach", 25.0, 5.0, 500.0, this);
        MovingObjectPosition ray;
        double x_new;
        double y_new;
        double z_new;
        double distance;
        double d;
        this.onUpdatePositionEvent = (e -> {
            if (e.isPre()) {
                ray = this.rayTrace(this.blocks.getPropertyValue());
                if (ray != null) {
                    if (this.dispatchTeleport) {
                        e.setOnGround(true);
                        x_new = ray.getBlockPos().getX() + 0.5;
                        y_new = ray.getBlockPos().getY() + 1;
                        z_new = ray.getBlockPos().getZ() + 0.5;
                        for (distance = ClickTP.mc.thePlayer.getDistance(x_new, y_new, z_new), d = 0.0; d < distance; d += 2.0) {
                            this.setPos(ClickTP.mc.thePlayer.posX + (x_new - ClickTP.mc.thePlayer.getHorizontalFacing().getFrontOffsetX() - ClickTP.mc.thePlayer.posX) * d / distance, ClickTP.mc.thePlayer.posY + (y_new - ClickTP.mc.thePlayer.posY) * d / distance, ClickTP.mc.thePlayer.posZ + (z_new - ClickTP.mc.thePlayer.getHorizontalFacing().getFrontOffsetZ() - ClickTP.mc.thePlayer.posZ) * d / distance);
                        }
                        this.setPos(x_new, y_new, z_new);
                        ClickTP.mc.renderGlobal.loadRenderers();
                        this.dispatchTeleport = false;
                    }
                }
            }
            return;
        });
        this.onMouseClickEvent = (e -> {
            if (e.getButton() == 1) {
                this.dispatchTeleport = true;
            }
        });
    }
    
    public MovingObjectPosition rayTrace(final double blockReachDistance) {
        final Vec3 vec3 = ClickTP.mc.thePlayer.getPositionEyes(1.0f);
        final Vec3 vec4 = ClickTP.mc.thePlayer.getLookVec();
        final Vec3 vec5 = vec3.addVector(vec4.xCoord * blockReachDistance, vec4.yCoord * blockReachDistance, vec4.zCoord * blockReachDistance);
        return ClickTP.mc.theWorld.rayTraceBlocks(vec3, vec5, !ClickTP.mc.thePlayer.isInWater(), false, false);
    }
    
    public void setPos(final double x, final double y, final double z) {
        ClickTP.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
        ClickTP.mc.thePlayer.setPosition(x, y, z);
    }
}
