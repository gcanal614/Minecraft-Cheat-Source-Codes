// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.ot.component;

import bozoware.base.BozoWare;
import bozoware.visual.font.MinecraftFontRenderer;
import java.util.ArrayList;

public abstract class OTComponent
{
    private final ArrayList<OTComponent> childrenComponents;
    private final OTComponent parentComponent;
    private double xPosition;
    private double yPosition;
    private final double width;
    private final double height;
    
    public OTComponent(final OTComponent parentComponent, final double xPosition, final double yPosition, final double width, final double height) {
        this.childrenComponents = new ArrayList<OTComponent>();
        this.parentComponent = parentComponent;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
    }
    
    public abstract void onDrawScreen(final int p0, final int p1);
    
    public abstract void onMouseClicked(final int p0, final int p1, final int p2);
    
    public abstract void onMouseReleased(final int p0, final int p1, final int p2);
    
    public abstract void onKeyTyped(final int p0);
    
    public void handleMouseInput() {
    }
    
    public double getXPosition() {
        return this.xPosition;
    }
    
    public double getYPosition() {
        return this.yPosition;
    }
    
    public void setXPosition(final double xPosition) {
        this.xPosition = xPosition;
    }
    
    public void setYPosition(final double yPosition) {
        this.yPosition = yPosition;
    }
    
    public double getWidth() {
        return this.width;
    }
    
    public double getHeight() {
        return this.height;
    }
    
    public OTComponent getParentComponent() {
        return this.parentComponent;
    }
    
    public ArrayList<OTComponent> getChildrenComponents() {
        return this.childrenComponents;
    }
    
    public void addChild(final OTComponent component) {
        this.childrenComponents.add(component);
    }
    
    public MinecraftFontRenderer getDefaultFontRenderer() {
        return BozoWare.getInstance().getFontManager().onetapDefaultRenderer;
    }
    
    public MinecraftFontRenderer getIconsFontRenderer() {
        return BozoWare.getInstance().getFontManager().onetapIconsRenderer;
    }
}
