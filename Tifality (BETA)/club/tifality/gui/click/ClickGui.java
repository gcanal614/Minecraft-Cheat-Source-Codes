/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.click;

import club.tifality.gui.click.Palette;
import club.tifality.gui.click.component.Component;
import club.tifality.gui.click.component.impl.ExpandableComponent;
import club.tifality.gui.click.component.impl.panel.impl.CategoryPanel;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.KeyPressEvent;
import club.tifality.module.ModuleCategory;
import club.tifality.module.impl.render.ClickGUI;
import club.tifality.utils.Wrapper;
import club.tifality.utils.render.RenderingUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public final class ClickGui
extends GuiScreen {
    public static boolean escapeKeyInUse;
    private static ClickGui instance;
    private final List<Component> components = new ArrayList<Component>();
    private final Palette palette;
    private Component selectedPanel;

    public ClickGui() {
        instance = this;
        this.palette = Palette.DEFAULT;
        int panelX = 2;
        for (ModuleCategory category : ModuleCategory.values()) {
            CategoryPanel panel = new CategoryPanel(category, panelX, 2);
            this.components.add(panel);
            panelX += panel.getWidth() + 2;
            this.selectedPanel = panel;
        }
    }

    public static ClickGui getInstance() {
        return instance;
    }

    public Palette getPalette() {
        return this.palette;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (Component component : this.components) {
            component.drawComponent(RenderingUtils.getScaledResolution(), mouseX, mouseY);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.selectedPanel.onKeyPress(keyCode);
        if (!escapeKeyInUse) {
            super.keyTyped(typedChar, keyCode);
        }
        escapeKeyInUse = false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (int i = this.components.size() - 1; i >= 0; --i) {
            ExpandableComponent expandableComponent;
            Component component = this.components.get(i);
            int x = component.getX();
            int y = component.getY();
            int cHeight = component.getHeight();
            if (component instanceof ExpandableComponent && (expandableComponent = (ExpandableComponent)component).isExpanded()) {
                cHeight = expandableComponent.getHeightWithExpand();
            }
            if (mouseX <= x || mouseY <= y || mouseX >= x + component.getWidth() || mouseY >= y + cHeight) continue;
            this.selectedPanel = component;
            component.onMouseClick(mouseX, mouseY, mouseButton);
            break;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.selectedPanel.onMouseRelease(state);
    }

    @Listener
    private void onKeyPressEvent(KeyPressEvent e) {
        if (e.getKey() == 54 && ClickGUI.clickGuiMode.getValue() == ClickGUI.ClickGuiMode.DROPDOWN) {
            Wrapper.getMinecraft().displayGuiScreen(this);
        }
    }
}

