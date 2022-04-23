// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import net.minecraft.client.gui.GuiScreen;
import bozoware.impl.UI.FlappyBirdGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import bozoware.base.event.EventListener;
import bozoware.impl.event.visual.Render2DEvent;
import bozoware.base.event.EventConsumer;
import bozoware.impl.property.EnumProperty;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Games", moduleCategory = ModuleCategory.VISUAL)
public class Games extends Module
{
    private final EnumProperty<gameMode> gameModeEnumProperty;
    @EventListener
    EventConsumer<Render2DEvent> render2dEvent;
    
    public Games() {
        this.gameModeEnumProperty = new EnumProperty<gameMode>("Game Mode", gameMode.flappyBird, this);
        final ScaledResolution scaledResolution;
        this.render2dEvent = (e -> {
            scaledResolution = new ScaledResolution(Games.mc, Games.mc.displayWidth, Games.mc.displayHeight);
            switch (this.gameModeEnumProperty.getPropertyValue()) {
                case flappyBird: {
                    Games.mc.displayGuiScreen(new FlappyBirdGUI());
                    break;
                }
            }
        });
    }
    
    public enum gameMode
    {
        flappyBird, 
        snake;
    }
}
