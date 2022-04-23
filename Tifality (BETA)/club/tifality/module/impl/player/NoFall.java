/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.player;

import club.tifality.Tifality;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.impl.movement.Flight;
import club.tifality.property.Property;
import club.tifality.property.impl.EnumProperty;
import club.tifality.utils.Wrapper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

@ModuleInfo(label="NoFall", category=ModuleCategory.PLAYER)
public final class NoFall
extends Module {
    private final EnumProperty<NoFallMode> noFallModeProperty = new EnumProperty<NoFallMode>("Mode", NoFallMode.EDIT);
    private final Property<Boolean> noSound = new Property<Boolean>("No Sound", false);
    private float fallDist = 0.0f;

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (event.isPre() && Wrapper.getPlayer().fallDistance > 3.0f) {
            NoFallMode mode = (NoFallMode)((Object)this.noFallModeProperty.getValue());
            if ((float)((int)Wrapper.getPlayer().fallDistance) % 3.0f == 0.0f) {
                switch (mode) {
                    case EDIT: {
                        double motionY;
                        double fallingDist;
                        double realDist;
                        if (this.fallDist > NoFall.mc.thePlayer.fallDistance) {
                            this.fallDist = 0.0f;
                        }
                        if (!(NoFall.mc.thePlayer.motionY < 0.0) || !((double)NoFall.mc.thePlayer.fallDistance > 2.124) || this.checkVoid(NoFall.mc.thePlayer) || !this.isBlockUnder() || NoFall.mc.thePlayer.isSpectator() || NoFall.mc.thePlayer.capabilities.allowFlying || !((realDist = (fallingDist = (double)(NoFall.mc.thePlayer.fallDistance - this.fallDist)) + -(((motionY = NoFall.mc.thePlayer.motionY) - 0.08) * (double)0.98f)) >= 3.0)) break;
                        event.setOnGround(true);
                        if (!this.noSound.get().booleanValue()) break;
                        NoFall.mc.thePlayer.fallDistance = 0.0f;
                        break;
                    }
                    case HYPIXEL: {
                        double motionY;
                        double fallingDist;
                        double realDist;
                        if (this.fallDist > NoFall.mc.thePlayer.fallDistance) {
                            this.fallDist = 0.0f;
                        }
                        if (!(NoFall.mc.thePlayer.motionY < 0.0) || !((double)NoFall.mc.thePlayer.fallDistance > 2.124) || this.checkVoid(NoFall.mc.thePlayer) || !this.isBlockUnder() || NoFall.mc.thePlayer.isSpectator() || NoFall.mc.thePlayer.capabilities.allowFlying || !((realDist = (fallingDist = (double)(NoFall.mc.thePlayer.fallDistance - this.fallDist)) + -(((motionY = NoFall.mc.thePlayer.motionY) - 0.08) * (double)0.98f)) >= 3.0)) break;
                        mc.getNetHandler().sendPacket(new C03PacketPlayer(true));
                        this.fallDist = NoFall.mc.thePlayer.fallDistance;
                        if (!this.noSound.get().booleanValue()) break;
                        NoFall.mc.thePlayer.fallDistance = 0.0f;
                    }
                }
            }
        }
    }

    private boolean checkVoid(EntityLivingBase entity) {
        for (int b = -1; b <= 0; b = (int)((byte)(b + 1))) {
            for (int b1 = -1; b1 <= 0; b1 = (int)((byte)(b1 + 1))) {
                if (!this.isVoid(b, b1, entity)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean isVoid(int X, int Z, EntityLivingBase entity) {
        Flight fly = Tifality.INSTANCE.getModuleManager().getModule(Flight.class);
        if (fly.isEnabled()) {
            return false;
        }
        if (NoFall.mc.thePlayer.posY < 0.0) {
            return true;
        }
        for (int off = 0; off < (int)entity.posY + 2; off += 2) {
            AxisAlignedBB bb = entity.getEntityBoundingBox().offset(X, -off, Z);
            if (NoFall.mc.theWorld.getCollidingBoundingBoxes(entity, bb).isEmpty()) {
                continue;
            }
            return false;
        }
        return true;
    }

    private boolean isBlockUnder() {
        int offset = 0;
        while ((double)offset < NoFall.mc.thePlayer.posY + (double)NoFall.mc.thePlayer.getEyeHeight()) {
            AxisAlignedBB boundingBox = NoFall.mc.thePlayer.getEntityBoundingBox().offset(0.0, -offset, 0.0);
            if (NoFall.mc.theWorld.getCollidingBoundingBoxes(NoFall.mc.thePlayer, boundingBox).isEmpty()) {
                return true;
            }
            offset += 2;
        }
        return false;
    }

    private static enum NoFallMode {
        EDIT,
        HYPIXEL;

    }
}

