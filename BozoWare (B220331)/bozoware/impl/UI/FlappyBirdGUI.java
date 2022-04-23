// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.UI;

import net.minecraft.client.gui.Gui;
import java.util.ArrayDeque;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiScreen;

public class FlappyBirdGUI extends GuiScreen
{
    ScaledResolution scaledResolution;
    private final ArrayDeque<double[]> positions;
    public String direction;
    
    public FlappyBirdGUI() {
        this.scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        this.positions = new ArrayDeque<double[]>();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        Gui.drawRect(0.0, 0.0, this.scaledResolution.getScaledWidth(), this.scaledResolution.getScaledHeight(), -16777216);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
