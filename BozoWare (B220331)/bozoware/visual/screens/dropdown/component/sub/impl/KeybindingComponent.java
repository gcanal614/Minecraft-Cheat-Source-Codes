// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.dropdown.component.sub.impl;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.Gui;
import bozoware.visual.screens.dropdown.component.sub.ModuleButtonComponent;
import java.util.function.Consumer;
import java.util.function.Supplier;
import bozoware.visual.screens.dropdown.component.Component;

public class KeybindingComponent extends Component
{
    private final Supplier<Integer> keyBindSupplier;
    private final Consumer<Integer> keyBindConsumer;
    public boolean active;
    private final ModuleButtonComponent parent;
    private int offset;
    
    public KeybindingComponent(final Supplier<Integer> keyBindSupplier, final Consumer<Integer> keyBindConsumer, final ModuleButtonComponent parent, final int offset) {
        this.keyBindSupplier = keyBindSupplier;
        this.keyBindConsumer = keyBindConsumer;
        this.parent = parent;
        this.offset = offset;
    }
    
    @Override
    public void onDrawScreen(final int mouseX, final int mouseY) {
        final double x = this.parent.getParentFrame().getX();
        final double y = this.parent.getParentFrame().getY() + this.offset;
        Gui.drawRectWithWidth(x, y, 115.0, 14.0, -15395563);
        this.getFontRenderer().drawStringWithShadow("Key Bind", x + 3.0, y + 4.0, -1);
        final String displayString = "[" + (this.active ? "..." : Keyboard.getKeyName((int)this.keyBindSupplier.get())) + "]";
        this.getFontRenderer().drawStringWithShadow(displayString, x + 113.0 - this.getFontRenderer().getStringWidth(displayString), y + 4.0, -7303024);
        super.onDrawScreen(mouseX, mouseY);
    }
    
    @Override
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHovering(mouseX, mouseY)) {
            this.active = !this.active;
        }
        else {
            this.active = false;
        }
    }
    
    @Override
    public void onKeyTyped(int typedKey) {
        if (this.active) {
            typedKey = Keyboard.getEventKey();
            this.keyBindConsumer.accept((typedKey == 1) ? 0 : typedKey);
            this.active = false;
        }
    }
    
    @Override
    public void onGuiClosed() {
        this.active = false;
    }
    
    private boolean isHovering(final int mouseX, final int mouseY) {
        final double x = this.parent.getParentFrame().getX();
        final double y = this.parent.getParentFrame().getY() + this.offset;
        return mouseX >= x && mouseX <= x + 115.0 && mouseY >= y && mouseY <= y + 14.0;
    }
    
    @Override
    public void setOffset(final int offset) {
        this.offset = offset;
    }
}
