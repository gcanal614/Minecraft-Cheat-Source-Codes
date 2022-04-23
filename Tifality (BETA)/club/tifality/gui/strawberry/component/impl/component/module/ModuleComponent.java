/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.strawberry.component.impl.component.module;

import club.tifality.gui.strawberry.ClickGui;
import club.tifality.gui.strawberry.component.Component;
import club.tifality.gui.strawberry.component.impl.ExpandableComponent;
import club.tifality.gui.strawberry.component.impl.component.property.PropertyComponent;
import club.tifality.gui.strawberry.component.impl.component.property.impl.BooleanPropertyComponent;
import club.tifality.gui.strawberry.component.impl.component.property.impl.ColorPropertyComponent;
import club.tifality.gui.strawberry.component.impl.component.property.impl.EnumBoxProperty;
import club.tifality.gui.strawberry.component.impl.component.property.impl.MultiBoxProperty;
import club.tifality.gui.strawberry.component.impl.component.property.impl.SliderPropertyComponent;
import club.tifality.module.Module;
import club.tifality.module.impl.render.hud.Hud;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.property.impl.MultiSelectEnumProperty;
import java.awt.Color;
import java.util.List;
import net.minecraft.client.Minecraft;
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
                component = new EnumBoxProperty((Component)this, (EnumProperty)property, propertyX, propertyY, width - 2, 15);
            } else if (property instanceof MultiSelectEnumProperty) {
                component = new MultiBoxProperty((Component)this, (MultiSelectEnumProperty)property, propertyX, propertyY, width - 2, 15);
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
        Gui.drawRect(x, y, x + width, y + height, this.getBackgroundColor(this.isHovered(mouseX, mouseY)));
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.binding ? "Press A Key..." : this.getName(), x + 2, (float)y + (float)height / 2.0f - 4.0f, this.module.isEnabled() ? Hud.hudColor.get().intValue() : new Color(255, 255, 255).getRGB());
        if (this.canExpand()) {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.isExpanded() ? "<" : ">", x + width - 7, (float)y + (float)height / 2.0f - 4.0f, new Color(255, 255, 255).getRGB());
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

