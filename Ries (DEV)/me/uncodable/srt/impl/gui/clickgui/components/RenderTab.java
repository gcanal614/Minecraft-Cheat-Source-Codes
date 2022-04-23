/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package me.uncodable.srt.impl.gui.clickgui.components;

import java.util.ArrayList;
import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.gui.clickgui.components.RenderModule;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

public class RenderTab {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private final Module.Category category;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private final int secret;
    private boolean dropped = true;
    private boolean renderModulesSetup;
    private final ArrayList<RenderModule> renderModules = new ArrayList();
    private static final int TAB_COLOR = -1578774;
    private static final int DROPPED_COLOR = 0x6060FF;
    private static final int NOT_DROPPED_COLOR = 0xAAAAFF;
    private static final int TEXT_COLOR = 0xFFFFFF;

    public RenderTab(Module.Category category, int startX, int startY, int endX, int endY, int secret) {
        this.category = category;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.secret = secret;
        this.setup();
    }

    public void setup() {
        if (!this.renderModulesSetup) {
            for (int moduleIndex = 0; moduleIndex < Ries.INSTANCE.getModuleManager().getModulesInCategory(this.category).size(); ++moduleIndex) {
                this.renderModules.add(new RenderModule(Ries.INSTANCE.getModuleManager().getModulesInCategory(this.category).get(moduleIndex), this.startX, this.startY + 20 * (moduleIndex + 1), this.endX, this.endY + 20 * (moduleIndex + 1)));
            }
            this.renderModulesSetup = true;
        }
    }

    public void reset() {
        if (this.renderModulesSetup) {
            this.renderModules.clear();
            for (int moduleIndex = 0; moduleIndex < Ries.INSTANCE.getModuleManager().getModulesInCategory(this.category).size(); ++moduleIndex) {
                this.renderModules.add(new RenderModule(Ries.INSTANCE.getModuleManager().getModulesInCategory(this.category).get(moduleIndex), this.startX, this.startY + 20 * (moduleIndex + 1), this.endX, this.endY + 20 * (moduleIndex + 1)));
            }
        }
    }

    public void render(int mouseX, int mouseY, int width, int height) {
        int moveCount = 0;
        String categoryName = this.category.getCategoryName();
        switch (RenderUtils.getFPSLevel()) {
            case "Very Low": {
                moveCount = 20;
                break;
            }
            case "Low": {
                moveCount = 6;
                break;
            }
            case "Medium": {
                moveCount = 3;
                break;
            }
            case "High": {
                if (RenderTab.MC.timer.getElapsedTicks() % 5 != 0) break;
                moveCount = 1;
            }
        }
        if (Mouse.isButtonDown((int)0)) {
            if (mouseX <= 50) {
                this.startX += moveCount;
                this.endX += moveCount;
            } else if (mouseX >= width - 60) {
                this.startX -= moveCount;
                this.endX -= moveCount;
            }
            if (mouseY <= 50) {
                this.startY += moveCount;
                this.endY += moveCount;
            } else if (mouseY >= height - 60) {
                this.startY -= moveCount;
                this.endY -= moveCount;
            }
        }
        Gui.drawRect(this.startX, this.startY, this.endX, this.endY, -1578774);
        if (this.secret > 95 && this.category.getCategoryName().equalsIgnoreCase("visual")) {
            categoryName = "ViSuAl";
        }
        RenderTab.MC.fontRendererObj.drawStringWithShadow(categoryName, this.startX + 5, this.startY + 6, 0xFFFFFF);
        if (this.dropped) {
            RenderTab.MC.fontRendererObj.drawStringWithShadow("-", this.endX - 10, this.startY + 6, 0x6060FF);
        } else {
            RenderTab.MC.fontRendererObj.drawStringWithShadow("+", this.endX - 10, this.startY + 6, 0xAAAAFF);
        }
        if (this.dropped) {
            this.renderModules.forEach(renderModule -> renderModule.render(mouseX, mouseY, width, height));
        }
    }

    public void onClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.hoverOver(mouseX, mouseY) && mouseButton == 1) {
            this.reset();
            boolean bl = this.dropped = !this.dropped;
        }
        if (this.dropped) {
            this.renderModules.forEach(renderModule -> renderModule.onClicked(mouseX, mouseY, mouseButton));
        }
    }

    public boolean hoverOver(int mouseX, int mouseY) {
        return mouseX >= this.startX && mouseX <= this.endX && mouseY >= this.startY && mouseY <= this.endY;
    }

    public void keyTyped(int keyCode) {
        this.renderModules.forEach(renderModule -> renderModule.keyTyped(keyCode));
    }

    public void mouseReleased() {
        this.renderModules.forEach(RenderModule::mouseReleased);
    }

    public Module.Category getCategory() {
        return this.category;
    }

    public int getStartX() {
        return this.startX;
    }

    public int getStartY() {
        return this.startY;
    }

    public int getEndX() {
        return this.endX;
    }

    public int getEndY() {
        return this.endY;
    }

    public int getSecret() {
        return this.secret;
    }

    public boolean isDropped() {
        return this.dropped;
    }

    public boolean isRenderModulesSetup() {
        return this.renderModulesSetup;
    }

    public ArrayList<RenderModule> getRenderModules() {
        return this.renderModules;
    }
}

