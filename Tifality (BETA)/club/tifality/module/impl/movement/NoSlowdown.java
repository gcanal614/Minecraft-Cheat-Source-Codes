/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.movement;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.manager.event.impl.player.UseItemEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.ModuleManager;
import club.tifality.module.impl.combat.KillAura;
import club.tifality.property.impl.EnumProperty;
import club.tifality.utils.Wrapper;
import club.tifality.utils.movement.MovementUtils;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@ModuleInfo(label="NoSlowdown", category=ModuleCategory.MOVEMENT)
public final class NoSlowdown
extends Module {
    public final EnumProperty<NoSlowMode> modeValue = new EnumProperty<NoSlowMode>("Mode", NoSlowMode.NCPNEW);
    private static final C07PacketPlayerDigging PLAYER_DIGGING = new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1.0, -1.0, -1.0), EnumFacing.DOWN);
    private static final C08PacketPlayerBlockPlacement BLOCK_PLACEMENT = new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, null, 0.0f, 0.0f, 0.0f);
    private static final C07PacketPlayerDigging PLAYERDIGGING = new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN);

    public static boolean isNoSlowdownEnabled() {
        return ModuleManager.getInstance(NoSlowdown.class).isEnabled();
    }

    @Listener
    private void onUpdatePositionEvent(UpdatePositionEvent e) {
        if (MovementUtils.isMoving() && !KillAura.isBlocking() && Wrapper.getPlayer().isBlocking()) {
            switch ((NoSlowMode)((Object)this.modeValue.get())) {
                case NCPOLD: {
                    if (e.isPre()) {
                        Wrapper.sendPacketDirect(PLAYER_DIGGING);
                        break;
                    }
                    Wrapper.sendPacketDirect(BLOCK_PLACEMENT);
                    break;
                }
                case NCPNEW: {
                    if (e.isPre()) {
                        Wrapper.sendPacketDirect(PLAYERDIGGING);
                        break;
                    }
                    Wrapper.sendPacketDirect(BLOCK_PLACEMENT);
                }
            }
        }
    }

    @Listener
    public final void onSlowDown(UseItemEvent event) {
        event.setCancelled();
    }

    private static enum NoSlowMode {
        NCPOLD,
        NCPNEW;

    }
}

