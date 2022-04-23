/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.click.component.impl.panel.impl;

import club.tifality.Tifality;
import club.tifality.gui.click.ClickGui;
import club.tifality.gui.click.component.Component;
import club.tifality.gui.click.component.impl.ExpandableComponent;
import club.tifality.gui.click.component.impl.component.module.ModuleComponent;
import club.tifality.gui.click.component.impl.panel.DraggablePanel;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.utils.StringUtils;
import club.tifality.utils.Wrapper;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
            this.children.add(new ModuleComponent((Component)this, module, 1, moduleY, 118, 15));
            moduleY += 15;
            this.getChildren().sort(Comparator.comparingDouble(Component::getX));
        }
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        super.drawComponent(scaledResolution, mouseX, mouseY);
        int x = this.getX();
        int y = this.getY();
        int width = this.getWidth();
        int height = this.getHeight();
        int panelHeaderCol = ClickGui.getInstance().getPalette().getPanelHeaderColor().getRGB();
        int headerHeight = height;
        int heightWithExpand = this.getHeightWithExpand();
        switch (ClickGui.getInstance().getPalette()) {
            case DEFAULT: {
                headerHeight = this.isExpanded() ? heightWithExpand + 1 : height;
            }
        }
        Gui.drawRect(x, y, x + width, y + headerHeight, panelHeaderCol);
        Wrapper.getFontRenderer().drawStringWithShadow(this.getName(), (float)x + (float)width / 2.0f - Wrapper.getFontRenderer().getWidth(this.getName()) / 2.0f - 1.0f, (float)y + 8.5f - 4.0f, ClickGui.getInstance().getPalette().getPanelHeaderTextColor().getRGB());
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

