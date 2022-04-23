/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.movement;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.MoveEntityEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.utils.timer.TimerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

@ModuleInfo(label="AntiFall", category=ModuleCategory.MOVEMENT)
public class AntiFall
extends Module {
    private final TimerUtil timer = new TimerUtil();
    private boolean saveMe;

    @Listener
    public void onMove(MoveEntityEvent e) {
        double dist;
        if (this.saveMe && this.timer.hasTimePassed(150L) || AntiFall.mc.thePlayer.isCollidedVertically) {
            this.saveMe = false;
            this.timer.reset();
        }
        if ((double)AntiFall.mc.thePlayer.fallDistance > (dist = 5.0) && !this.isBlockUnder()) {
            if (!this.saveMe) {
                this.saveMe = true;
                this.timer.reset();
            }
            AntiFall.mc.thePlayer.fallDistance = 0.0f;
            AntiFall.mc.thePlayer.sendQueue.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(AntiFall.mc.thePlayer.posX, AntiFall.mc.thePlayer.posY + 12.0, AntiFall.mc.thePlayer.posZ, false));
        }
    }

    private boolean isBlockUnder() {
        if (AntiFall.mc.thePlayer.posY < 0.0) {
            return false;
        }
        for (int off = 0; off < (int)AntiFall.mc.thePlayer.posY + 2; off += 2) {
            AxisAlignedBB bb = AntiFall.mc.thePlayer.getEntityBoundingBox().offset(0.0, -off, 0.0);
            if (AntiFall.mc.theWorld.getCollidingBoundingBoxes(AntiFall.mc.thePlayer, bb).isEmpty()) continue;
            return true;
        }
        return false;
    }
}

