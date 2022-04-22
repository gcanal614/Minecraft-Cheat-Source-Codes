/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package de.fanta.clickgui.astolfo;

import de.fanta.clickgui.astolfo.ClickGuiPane;
import de.fanta.module.Module;
import de.fanta.utils.FileManager;
import de.fanta.utils.JsonUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

public class ClickGuiScreen
extends GuiScreen {
    private List<ClickGuiPane> panes = new ArrayList<ClickGuiPane>();
    public ClickGuiPane selectedPane = null;

    @Override
    public void initGui() {
        this.panes.clear();
        int i = 20;
        Module.Type[] typeArray = Module.Type.values();
        int n = typeArray.length;
        int n2 = 0;
        while (n2 < n) {
            Module.Type Type2 = typeArray[n2];
            File f = new File(FileManager.getDirectory("astolfoclickgui"), String.valueOf(Type2.name().toLowerCase()) + ".json");
            ClickGuiPane pane = JsonUtil.getObject(ClickGuiPane.class, f);
            ClickGuiPane pane2 = new ClickGuiPane(Type2, i, 20, this);
            if (pane != null) {
                pane2.x = pane.x;
                pane2.y = pane.y;
                pane2.extended = pane.extended;
                pane2.type = pane.type;
            }
            this.panes.add(pane2);
            i += 120;
            ++n2;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ClickGuiScreen.drawRect(0.0f, 0.0f, width, height, Integer.MIN_VALUE);
        for (ClickGuiPane pane : this.panes) {
            pane.draw(mouseX, mouseY);
        }
        if (this.selectedPane != null && Mouse.isButtonDown((int)0)) {
            this.selectedPane.mouseDragged(mouseX, mouseY);
        }
        int mouseD = Mouse.getDWheel();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (ClickGuiPane pane : this.panes) {
            pane.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        for (ClickGuiPane pane : this.panes) {
            if (this.selectedPane != null && this.selectedPane != pane || !pane.isHovered(mouseX, mouseY)) continue;
            this.selectedPane = pane;
        }
        if (this.selectedPane != null && clickedMouseButton == 0) {
            this.selectedPane.mouseDragged(mouseX, mouseY);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.selectedPane = null;
    }

    @Override
    public void onGuiClosed() {
        for (ClickGuiPane pane : this.panes) {
            pane.onGuiClosed();
        }
        this.savePanes();
    }

    public void savePanes() {
        FileManager.createDirectory("astolfoclickgui");
        for (ClickGuiPane pane : this.panes) {
            JsonUtil.writeObjectToFile(pane, new File(FileManager.getDirectory("astolfoclickgui"), String.valueOf(pane.type.name().toLowerCase()) + ".json"));
        }
    }
}

