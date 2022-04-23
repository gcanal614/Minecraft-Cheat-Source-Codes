/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.click.component.impl.component.module;

import club.tifality.gui.click.ClickGui;
import club.tifality.gui.click.component.Component;
import club.tifality.gui.click.component.impl.ExpandableComponent;
import club.tifality.gui.click.component.impl.component.property.PropertyComponent;
import club.tifality.gui.click.component.impl.component.property.impl.BooleanPropertyComponent;
import club.tifality.gui.click.component.impl.component.property.impl.ColorPropertyComponent;
import club.tifality.gui.click.component.impl.component.property.impl.EnumBoxProperty;
import club.tifality.gui.click.component.impl.component.property.impl.SliderPropertyComponent;
import club.tifality.module.Module;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.utils.Wrapper;
import club.tifality.utils.render.RenderingUtils;
import java.util.List;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public final class ModuleComponent
extends ExpandableComponent {
    private final Module module;
    private boolean binding;

    public ModuleComponent(Component parent, Module module, int x, int y, int width, int height) {
        super(parent, module.getLabel(), x, y, width, height);
        this.module = module;
        List properties2 = module.getElements();
        int propertyX = 1;
        int propertyY = height;
        for (Property property : properties2) {
            Component component = null;
            if (property.getType() == Boolean.class) {
                component = new BooleanPropertyComponent((Component)this, property, propertyX, propertyY, width - 2, 15);
            } else if (property.getType() == Integer.class) {
                component = new ColorPropertyComponent((Component)this, property, propertyX, propertyY, width - 2, 15);
            } else if (property instanceof DoubleProperty) {
                component = new SliderPropertyComponent((Component)this, (DoubleProperty)property, propertyX, propertyY, width - 2, 15);
            } else if (property instanceof EnumProperty) {
                component = new EnumBoxProperty((Component)this, (EnumProperty)property, propertyX, propertyY, width - 2, 22);
            }
            if (component == null) continue;
            this.children.add(component);
            propertyY += component.getHeight();
        }
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        int x = this.getX();
        int y = this.getY();
        int width = this.getWidth();
        int height = this.getHeight();
        if (this.isExpanded()) {
            int childY = 15;
            for (Component child : this.children) {
                ExpandableComponent expandableComponent;
                PropertyComponent propertyComponent;
                int cHeight = child.getHeight();
                if (child instanceof PropertyComponent && !(propertyComponent = (PropertyComponent)((Object)child)).getProperty().isAvailable()) continue;
                if (child instanceof ExpandableComponent && (expandableComponent = (ExpandableComponent)child).isExpanded()) {
                    cHeight = expandableComponent.getHeightWithExpand();
                }
                child.setY(childY);
                child.drawComponent(scaledResolution, mouseX, mouseY);
                childY += cHeight;
            }
        }
        int moduleColor = 0;
        switch (ClickGui.getInstance().getPalette()) {
            case DEFAULT: {
                moduleColor = ClickGui.getInstance().getPalette().getPanelHeaderTextColor().getRGB();
                break;
            }
            case PINK: {
                moduleColor = this.module.isEnabled() ? ClickGui.getInstance().getPalette().getEnabledModuleColor().getRGB() : ClickGui.getInstance().getPalette().getDisabledModuleColor().getRGB();
            }
        }
        Gui.drawRect(x, y, x + width, y + height, this.getBackgroundColor(this.isHovered(mouseX, mouseY)));
        Gui.drawRect(x, y, x + width, y + height, this.getBackgroundEnabledColor(this.module.isEnabled()));
        Wrapper.getFontRenderer().drawStringWithShadow(this.binding ? "Press A Key..." : this.getName(), x + 2, (float)y + (float)height / 2.0f - 4.0f, moduleColor);
        if (this.canExpand()) {
            RenderingUtils.drawAndRotateArrow(x + width - 10, (float)y + (float)height / 2.0f - 2.0f, 6.0f, this.isExpanded());
        }
    }

    @Override
    public boolean canExpand() {
        return !this.children.isEmpty();
    }

    @Override
    public void onPress(int mouseX, int mouseY, int button) {
        switch (button) {
            case 0: {
                this.module.toggle();
                break;
            }
            case 2: {
                this.binding = !this.binding;
            }
        }
    }

    @Override
    public void onKeyPress(int keyCode) {
        if (this.binding) {
            ClickGui.escapeKeyInUse = true;
            this.module.setKey(keyCode == 1 ? 0 : keyCode);
            this.binding = false;
        }
    }

    @Override
    public int getHeightWithExpand() {
        int height = this.getHeight();
        if (this.isExpanded()) {
            for (Component child : this.children) {
                ExpandableComponent expandableComponent;
                PropertyComponent propertyComponent;
                int cHeight = child.getHeight();
                if (child instanceof PropertyComponent && !(propertyComponent = (PropertyComponent)((Object)child)).getProperty().isAvailable()) continue;
                if (child instanceof ExpandableComponent && (expandableComponent = (ExpandableComponent)child).isExpanded()) {
                    cHeight = expandableComponent.getHeightWithExpand();
                }
                height += cHeight;
            }
        }
        return height;
    }
}

