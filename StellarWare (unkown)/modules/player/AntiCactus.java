package stellar.skid.modules.player;

import stellar.skid.events.EventTarget;
import stellar.skid.events.events.CollideWithBlockEvent;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.ModuleManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class AntiCactus extends AbstractModule {

    /* constructors */
    public AntiCactus(@NonNull ModuleManager moduleManager) {
        super(moduleManager, "AntiCactus", "Anti Cactus", EnumModuleType.PLAYER, "");
    }

    /* methods */
    @EventTarget
    private void onCollision(CollideWithBlockEvent event) {
        if (event.getBlock() == Blocks.cactus) {
            event.setBoundingBox(new AxisAlignedBB(event.getPos(), event.getPos().add(1, 1, 1)));
        }
    }
}
