/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package me.uncodable.srt.impl.gui.clickgui.components;

import java.util.ArrayList;
import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.gui.clickgui.components.RenderSetting;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class RenderModule {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private final Module module;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private boolean dropped;
    private boolean binding;
    private boolean renderSettingsSetup;
    private final ArrayList<RenderSetting> renderSettings = new ArrayList();
    private static final int SUB_TAB_COLOR = -1442840576;
    private static final int DROPPED_COLOR = 0x6060FF;
    private static final int NOT_DROPPED_COLOR = 0xAAAAFF;
    private static final int TEXT_COLOR = 0xFFFFFF;
    private static final int TOGGLED_COLOR = 0xBBBBBB;

    public RenderModule(Module module, int startX, int startY, int endX, int endY) {
        this.module = module;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.setup();
    }

    public void setup() {
        if (!this.renderSettingsSetup) {
            for (int settingIndex = 0; settingIndex < Ries.INSTANCE.getSettingManager().getAllSettings(this.module).size(); ++settingIndex) {
                this.renderSettings.add(new RenderSetting(Ries.INSTANCE.getSettingManager().getAllSettings(this.module).get(settingIndex), this.endX, this.startY + 20 * (settingIndex * 2), this.endX + 90, this.endY + 20 * (settingIndex * 2)));
            }
            this.renderSettingsSetup = true;
        }
    }

    public void reset() {
        if (this.renderSettingsSetup) {
            this.renderSettings.clear();
            for (int settingIndex = 0; settingIndex < Ries.INSTANCE.getSettingManager().getAllSettings(this.module).size(); ++settingIndex) {
                this.renderSettings.add(new RenderSetting(Ries.INSTANCE.getSettingManager().getAllSettings(this.module).get(settingIndex), this.endX, this.startY + 20 * (settingIndex * 2), this.endX + 90, this.endY + 20 * (settingIndex * 2)));
            }
        }
    }

    public void render(int mouseX, int mouseY, int width, int height) {
        int moveCount = 0;
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
                if (RenderModule.MC.timer.getElapsedTicks() % 5 != 0) break;
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
        Gui.drawRect(this.startX, this.startY, this.endX, this.endY, -1442840576);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.startX + 5, this.startY + 6, 0.0f);
        GlStateManager.scale(0.75, 0.75, 0.75);
        if (this.binding) {
            RenderModule.MC.fontRendererObj.drawStringWithShadow("Press a key...", 0.0f, 0.0f, this.module.isEnabled() ? 0xBBBBBB : 0xFFFFFF);
        } else {
            RenderModule.MC.fontRendererObj.drawStringWithShadow(this.module.getInfo().name() + (this.module.getPrimaryKey() != 0 ? " (" + Keyboard.getKeyName((int)this.module.getPrimaryKey()) + ")" : ""), 0.0f, 0.0f, this.module.isEnabled() ? 0xBBBBBB : 0xFFFFFF);
        }
        GlStateManager.popMatrix();
        if (Ries.INSTANCE.getSettingManager().getAllSettings(this.module).size() > 0) {
            if (this.dropped) {
                RenderModule.MC.fontRendererObj.drawStringWithShadow("-", this.endX - 10, this.startY + 6, 0x6060FF);
                this.renderSettings.forEach(renderSetting -> renderSetting.render(mouseX, mouseY, width, height));
            } else {
                RenderModule.MC.fontRendererObj.drawStringWithShadow("+", this.endX - 10, this.startY + 6, 0xAAAAFF);
            }
        }
    }

    public void onClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.hoverOver(mouseX, mouseY)) {
            switch (mouseButton) {
                case 0: {
                    this.module.toggle();
                    break;
                }
                case 1: {
                    this.reset();
                    this.dropped = !this.dropped;
                    break;
                }
                case 2: {
                    this.binding = true;
                }
            }
        }
        if (this.dropped) {
            this.renderSettings.forEach(renderSetting -> renderSetting.onClicked(mouseX, mouseY, mouseButton));
        }
    }

    public boolean hoverOver(int mouseX, int mouseY) {
        return mouseX >= this.startX && mouseX <= this.endX && mouseY >= this.startY && mouseY <= this.endY;
    }

    public void keyTyped(int keyCode) {
        if (this.binding) {
            if (keyCode == 1) {
                this.module.setPrimaryKey(0);
            } else {
                this.module.setPrimaryKey(keyCode);
            }
            this.binding = false;
        }
        if (this.dropped) {
            this.renderSettings.forEach(renderSetting -> renderSetting.keyTyped(keyCode));
        }
    }

    public void mouseReleased() {
        this.renderSettings.forEach(RenderSetting::mouseReleased);
    }

    public Module getModule() {
        return this.module;
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

    public boolean isDropped() {
        return this.dropped;
    }

    public boolean isBinding() {
        return this.binding;
    }

    public boolean isRenderSettingsSetup() {
        return this.renderSettingsSetup;
    }

    public ArrayList<RenderSetting> getRenderSettings() {
        return this.renderSettings;
    }
}

