/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Lists
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package me.uncodable.srt.impl.gui.clickgui2;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import me.uncodable.srt.impl.gui.clickgui2.components.RenderTab;
import me.uncodable.srt.impl.utils.EasingUtils;
import me.uncodable.srt.impl.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ClickGUIScreen
extends GuiScreen {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private final ArrayList<RenderTab> renderTabs;
    private final Timer timer = new Timer();
    private static final long ANIMATION_MILLISECONDS = 500L;

    public ClickGUIScreen(ArrayList<RenderTab> renderTabs) {
        this.renderTabs = renderTabs;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float progress = (float)this.timer.difference() / 500.0f;
        this.drawDefaultBackground();
        GL11.glPushMatrix();
        if (progress <= 1.0f) {
            float animationProgress = EasingUtils.easeOutBack(progress);
            GL11.glTranslatef((float)((float)this.width * (1.0f - animationProgress) / 2.0f), (float)((float)this.height * (1.0f - animationProgress) / 2.0f), (float)0.0f);
            GL11.glScalef((float)animationProgress, (float)animationProgress, (float)animationProgress);
        }
        this.renderTabs.forEach(renderTab -> renderTab.render(mouseX, mouseY, this.width, this.height));
        this.renderTabs.forEach(renderTab -> renderTab.getRenderModules().forEach(renderModule -> {
            if (renderModule.hoverOver(mouseX, mouseY) && renderTab.isDropped()) {
                String message = String.format("\u00a7dDescription:\n\n\u00a75%s\n\n\u00a7dPrimary Bind:\u00a75 %s\n\n\u00a7dSecondary Bind:\u00a75 %s", renderModule.getModule().getInfo().desc().replaceAll("", "\u00a75"), Keyboard.getKeyName((int)renderModule.getModule().getPrimaryKey()), "N/A");
                this.drawHoveringText(Lists.newArrayList((Iterable)Splitter.on((char)'\n').split((CharSequence)message)), mouseX, mouseY);
            }
        }));
        GL11.glDisable((int)2896);
        GL11.glPopMatrix();
        this.mc.fontRendererObj.drawStringWithShadow("Prototype Click GUI", 0.0f, this.height - 12, 0xFFFFFF);
        ClickGUIScreen.MC.gameSettings.keyBindJump.setKeyDown(Keyboard.isKeyDown((int)ClickGUIScreen.MC.gameSettings.keyBindJump.getKeyCode()));
        ClickGUIScreen.MC.gameSettings.keyBindRight.setKeyDown(Keyboard.isKeyDown((int)ClickGUIScreen.MC.gameSettings.keyBindRight.getKeyCode()));
        ClickGUIScreen.MC.gameSettings.keyBindLeft.setKeyDown(Keyboard.isKeyDown((int)ClickGUIScreen.MC.gameSettings.keyBindLeft.getKeyCode()));
        ClickGUIScreen.MC.gameSettings.keyBindForward.setKeyDown(Keyboard.isKeyDown((int)ClickGUIScreen.MC.gameSettings.keyBindForward.getKeyCode()));
        ClickGUIScreen.MC.gameSettings.keyBindBack.setKeyDown(Keyboard.isKeyDown((int)ClickGUIScreen.MC.gameSettings.keyBindBack.getKeyCode()));
        ClickGUIScreen.MC.gameSettings.keyBindSprint.setKeyDown(Keyboard.isKeyDown((int)ClickGUIScreen.MC.gameSettings.keyBindSprint.getKeyCode()));
        ClickGUIScreen.MC.gameSettings.keyBindSneak.setKeyDown(Keyboard.isKeyDown((int)ClickGUIScreen.MC.gameSettings.keyBindSneak.getKeyCode()));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.renderTabs.forEach(renderTab -> renderTab.onClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.renderTabs.forEach(RenderTab::mouseReleased);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.renderTabs.forEach(renderTab -> renderTab.keyTyped(keyCode));
        if (keyCode == 1) {
            super.keyTyped(typedChar, keyCode);
        }
    }
}

