// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.dropdown.component.sub;

import java.util.Collection;
import bozoware.impl.module.visual.HUD;
import net.minecraft.client.gui.Gui;
import java.util.Iterator;
import bozoware.visual.screens.dropdown.component.sub.impl.KeybindingComponent;
import bozoware.visual.screens.dropdown.component.sub.impl.StringPropertyComponent;
import bozoware.impl.property.StringProperty;
import bozoware.visual.screens.dropdown.component.sub.impl.MultiSelectEnumPropertyComponent;
import bozoware.impl.property.MultiSelectEnumProperty;
import bozoware.visual.screens.dropdown.component.sub.impl.ColorPropertyComponent;
import bozoware.impl.property.ColorProperty;
import bozoware.visual.screens.dropdown.component.sub.impl.EnumPropertyComponent;
import bozoware.impl.property.EnumProperty;
import bozoware.visual.screens.dropdown.component.sub.impl.ValuePropertyComponent;
import bozoware.impl.property.ValueProperty;
import bozoware.visual.screens.dropdown.component.sub.impl.BooleanPropertyComponent;
import bozoware.impl.property.BooleanProperty;
import bozoware.base.property.Property;
import bozoware.base.BozoWare;
import java.util.ArrayList;
import bozoware.base.module.Module;
import bozoware.visual.screens.dropdown.component.Component;

public class ModuleButtonComponent extends Component
{
    private final Module module;
    public int offset;
    public boolean expanded;
    public ArrayList<Component> subComponents;
    
    public ModuleButtonComponent(final Module module, final int offset) {
        this.subComponents = new ArrayList<Component>();
        this.module = module;
        this.offset = offset;
        int settingOffset = offset + 14;
        for (final Property<?> property : BozoWare.getInstance().getPropertyManager().getPropertiesByModule(module)) {
            if (property instanceof BooleanProperty) {
                this.subComponents.add(new BooleanPropertyComponent((BooleanProperty)property, this, settingOffset));
                settingOffset += 14;
            }
            if (property instanceof ValueProperty) {
                this.subComponents.add(new ValuePropertyComponent((ValueProperty)property, this, settingOffset));
                settingOffset += 14;
            }
            if (property instanceof EnumProperty) {
                this.subComponents.add(new EnumPropertyComponent((EnumProperty)property, this, settingOffset));
                settingOffset += 14;
            }
            if (property instanceof ColorProperty) {
                this.subComponents.add(new ColorPropertyComponent((ColorProperty)property, this, settingOffset));
                settingOffset += 14;
            }
            if (property instanceof MultiSelectEnumProperty) {
                this.subComponents.add(new MultiSelectEnumPropertyComponent((MultiSelectEnumProperty)property, this, settingOffset));
                settingOffset += 14;
            }
            if (property instanceof StringProperty) {
                this.subComponents.add(new StringPropertyComponent((StringProperty)property, this, settingOffset));
                settingOffset += 14;
            }
        }
        this.subComponents.add(new KeybindingComponent(module::getModuleBind, module::setModuleBind, this, settingOffset));
    }
    
    @Override
    public void onDrawScreen(final int mouseX, final int mouseY) {
        final double y = this.getParentFrame().getY() + this.offset;
        Gui.drawRectWithWidth(this.getParentFrame().getX(), y, this.getParentFrame().frameWidth, this.getParentFrame().frameHeight, this.isHoveringButton(mouseX, mouseY) ? -13619152 : -14671840);
        if (this.module.isModuleToggled()) {
            Gui.drawRectWithWidth(this.getParentFrame().getX(), y, this.getParentFrame().frameWidth, this.getParentFrame().frameHeight, HUD.getInstance().bozoColor);
        }
        BozoWare.getInstance().getFontManager().mediumFontRenderer.drawStringWithShadow(this.module.getModuleName(), this.getParentFrame().getX() + 3, y + 4.0, -1);
        if (this.expanded) {
            this.subComponents.forEach(subComponent -> {
                if (!subComponent.isHidden()) {
                    subComponent.onDrawScreen(mouseX, mouseY);
                }
            });
        }
    }
    
    @Override
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHoveringButton(mouseX, mouseY)) {
            if (mouseButton == 0) {
                this.module.setModuleToggled(!this.module.isModuleToggled());
            }
            if (mouseButton == 1) {
                this.expanded = !this.expanded;
                this.subComponents.forEach(Component::onAnimationEvent);
            }
        }
        if (this.expanded) {
            this.subComponents.forEach(subComponent -> {
                if (!subComponent.isHidden()) {
                    subComponent.onMouseClicked(mouseX, mouseY, mouseButton);
                }
            });
        }
    }
    
    @Override
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.expanded) {
            this.subComponents.forEach(subComponent -> {
                if (!subComponent.isHidden()) {
                    subComponent.onMouseReleased(mouseX, mouseY, mouseButton);
                }
            });
        }
    }
    
    @Override
    public void onKeyTyped(final int typedKey) {
        if (this.expanded) {
            this.subComponents.forEach(subComponent -> {
                if (!subComponent.isHidden()) {
                    subComponent.onKeyTyped(typedKey);
                }
                return;
            });
        }
        super.onKeyTyped(typedKey);
    }
    
    private boolean isHoveringButton(final int mouseX, final int mouseY) {
        return mouseX >= this.getParentFrame().getX() && mouseX <= this.getParentFrame().getX() + this.getParentFrame().frameWidth && mouseY >= this.getParentFrame().getY() + this.offset && mouseY <= this.getParentFrame().getY() + this.offset + 13;
    }
    
    @Override
    public void onAnimationEvent() {
        if (this.expanded) {
            this.subComponents.forEach(Component::onAnimationEvent);
        }
    }
    
    @Override
    public double getHeight() {
        final ArrayList<Component> visibleComponents = new ArrayList<Component>(this.subComponents);
        visibleComponents.removeIf(Component::isHidden);
        if (this.expanded) {
            double height = 14.0;
            for (final Component component : visibleComponents) {
                height += component.getHeight();
            }
            return height;
        }
        return 14.0;
    }
    
    @Override
    public void setOffset(final int offset) {
        this.offset = offset;
        int subY = this.offset + 14;
        for (final Component comp : this.subComponents) {
            if (!comp.isHidden()) {
                comp.setOffset(subY);
                subY += (int)comp.getHeight();
            }
        }
    }
}
