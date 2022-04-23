/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.strawberry.component.impl.panel.impl;

import club.tifality.Tifality;
import club.tifality.gui.strawberry.component.Component;
import club.tifality.gui.strawberry.component.impl.ExpandableComponent;
import club.tifality.gui.strawberry.component.impl.component.module.ModuleComponent;
import club.tifality.gui.strawberry.component.impl.panel.DraggablePanel;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.impl.render.hud.Hud;
import club.tifality.utils.StringUtils;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public final class CategoryPanel
extends DraggablePanel {
    public static final int HEADER_WIDTH = 120;
    public static final int X_ITEM_OFFSET = 1;
    public static final int ITEM_HEIGHT = 15;
    public static final int HEADER_HEIGHT = 17;
    private final List<Module> modules;

    public CategoryPanel(ModuleCategory category, int x, int y) {
        super(null, StringUtils.upperSnakeCaseToPascal(category.name()), x, y, 120, 17);
        int moduleY = 17;
        this.modules = Collections.unmodifiableList(Tifality.getInstance().getModuleManager().getModulesForCategory(category));
        for (Module module : this.modules) {
            this.children.add(new ModuleComponent((Component)this, module, 2, moduleY, 98, 15));
            moduleY += 15;
        }
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        super.drawComponent(scaledResolution, mouseX, mouseY);
        int x = this.getX();
        int y = this.getY();
        int width = this.getWidth();
        int height = this.getHeight();
        int heightWithExpand = this.getHeightWithExpand();
        int headerHeight = this.isExpanded() ? heightWithExpand : height;
        Gui.drawRect(x, y, x + width - 18, y + height, (int)Hud.hudColor.get());
        Gui.drawRect(x, y, x + 2, y + headerHeight, (int)Hud.hudColor.get());
        Gui.drawRect(x + width - 20, y, x + width - 18, y + headerHeight, (int)Hud.hudColor.get());
        Gui.drawRect(x, y + headerHeight, x + width - 18, y + headerHeight + 2, (int)Hud.hudColor.get());
        if (this.isExpanded()) {
            int moduleY = height;
            for (Component child : this.children) {
                ExpandableComponent expandableComponent;
                child.setY(moduleY);
                child.drawComponent(scaledResolution, mouseX, mouseY);
                int cHeight = child.getHeight();
                if (child instanceof ExpandableComponent && (expandableComponent = (ExpandableComponent)child).isExpanded()) {
                    cHeight = expandableComponent.getHeightWithExpand();
                }
                moduleY += cHeight;
            }
        }
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.getName(), x + 2, (float)y + 8.5f - 4.0f, new Color(255, 255, 255).getRGB());
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.isExpanded() ? "<" : ">", x + width - 27, (float)y + (float)height / 2.0f - 4.0f, new Color(255, 255, 255).getRGB());
    }

    @Override
    public boolean canExpand() {
        return !this.modules.isEmpty();
    }

    @Override
    public int getHeightWithExpand() {
        int height = this.getHeight();
        if (this.isExpanded()) {
            for (Component child : this.children) {
                ExpandableComponent expandableComponent;
                int cHeight = child.getHeight();
                if (child instanceof ExpandableComponent && (expandableComponent = (ExpandableComponent)child).isExpanded()) {
                    cHeight = expandableComponent.getHeightWithExpand();
                }
                height += cHeight;
            }
        }
        return height;
    }
}

