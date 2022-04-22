package me.injusttice.neutron.impl.modules.impl.player;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.movement.MovementUtils;
import net.minecraft.util.BlockPos;

public class Jesus extends Module {

    public ModeSet mode = new ModeSet("Mode", "Verus", "Verus", "Jump", "Vanilla");

    public Jesus() {
        super("Jesus", 0, Category.PLAYER);
        addSettings(mode);
    }

    @EventTarget
    public void onPre(EventMotion e) {
        setDisplayName("Jesus §7" + mode.getMode());
        BlockPos bpos = new BlockPos(localPlayer.posX, localPlayer.posY - (localPlayer.onGround ? 0.1D : 0.41D), localPlayer.posZ);
        if (this.mc.theWorld.getBlockState(bpos).getBlock() instanceof net.minecraft.block.BlockLiquid && !this.mc.gameSettings.keyBindJump.isKeyDown())
            switch (this.mode.getMode()) {
                case "Verus":
                    MovementUtils.actualSetSpeed(MovementUtils.getSpeed());
                    e.setOnGround(true);
                    localPlayer.motionY = 0.0D;
                    break;
                case "Jump":
                    localPlayer.motionY = 0.21D;
                    break;
                case "Vanilla":
                    localPlayer.motionY = 0.0D;
                    break;
            }
    }
}
