// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.dropdown.component;

import bozoware.base.BozoWare;
import bozoware.visual.font.MinecraftFontRenderer;
import bozoware.impl.module.visual.HUD;

public class Component
{
    private ModuleCategoryFrame parentFrame;
    public final int componentAccentColor;
    public final int componentAccentColorHovered;
    
    public Component() {
        this.componentAccentColor = HUD.getInstance().bozoColor;
        HUD.getInstance();
        this.componentAccentColorHovered = HUD.bozoColor2;
    }
    
    public final MinecraftFontRenderer getFontRenderer() {
        return BozoWare.getInstance().getFontManager().mediumFontRenderer;
    }
    
    public ModuleCategoryFrame getParentFrame() {
        return this.parentFrame;
    }
    
    public void setParentFrame(final ModuleCategoryFrame frame) {
        this.parentFrame = frame;
    }
    
    public void onDrawScreen(final int mouseX, final int mouseY) {
    }
    
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void onKeyTyped(final int typedKey) {
    }
    
    public int getColor1() {
        return this.componentAccentColor;
    }
    
    public int getColor2() {
        return this.componentAccentColorHovered;
    }
    
    public double getHeight() {
        return 14.0;
    }
    
    public void onAnimationEvent() {
    }
    
    public boolean isHidden() {
        return false;
    }
    
    public void setOffset(final int offset) {
    }
    
    public void onGuiClosed() {
    }
}
