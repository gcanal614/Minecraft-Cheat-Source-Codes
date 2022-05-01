package ensemble.mixin.cc.mixin.client;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Gui.class)
public abstract class MixinGui {

    @Shadow
    protected float zLevel;

    @Shadow
    public abstract void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height);


}