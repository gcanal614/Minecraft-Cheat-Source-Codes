/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package de.fanta.clickgui.intellij.openables;

import de.fanta.clickgui.intellij.ClickGuiMainPane;
import de.fanta.clickgui.intellij.openables.ClickGuiOpenable;
import de.fanta.module.Module;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class ClickGuiOpenableModule
extends ClickGuiOpenable {
    private Module module;
    private ClickGuiMainPane pane;

    public ClickGuiOpenableModule(float x, float y, float baseHeight, float width, Module module, ClickGuiMainPane pane) {
        super(x, y, baseHeight, width, null, ClickGuiOpenable.Type.CLASS, String.valueOf(module.name.replace(" ", "")) + ".java");
        this.module = module;
        this.pane = pane;
    }

    @Override
    public void draw(float mouseX, float mouseY) {
        Minecraft mc = Minecraft.getMinecraft();
        boolean hovered = false;
        if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.baseHeight) {
            hovered = true;
        }
        if (hovered && System.currentTimeMillis() - this.lastMS >= 100L) {
            switch (this.type) {
                case FOLDER: {
                    break;
                }
                case CLASS: {
                    try {
                        if (Mouse.isButtonDown((int)0)) {
                            this.module.toggle();
                        }
                        if (Mouse.isButtonDown((int)1)) {
                            this.pane.openedModule = this.module;
                        }
                        this.pane.init = true;
                        this.lastMS = System.currentTimeMillis();
                    }
                    catch (Exception exception) {}
                    break;
                }
            }
        }
        switch (this.type) {
            case FOLDER: {
                break;
            }
            case CLASS: {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f);
                mc.getTextureManager().bindTexture(new ResourceLocation("textures/class.png"));
                ClickGuiOpenableModule.drawModalRectWithCustomSizedTexture(this.x + 3.0f, this.y + 2.0f, 0.0f, 0.0f, 12.0f, 12.0f, 12.0f, 12.0f);
                GlStateManager.popMatrix();
                MENU_FONT.drawString(this.name, this.x + 16.0f, this.y + 2.0f, this.module.isState() ? Color.white.getRGB() : ClickGuiMainPane.MENU_FONT_COLOR);
                this.width = 16 + MENU_FONT.getStringWidth(this.name) + 2;
                break;
            }
        }
    }
}

