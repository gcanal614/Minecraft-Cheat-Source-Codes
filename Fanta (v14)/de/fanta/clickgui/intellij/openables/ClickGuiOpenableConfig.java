/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package de.fanta.clickgui.intellij.openables;

import de.fanta.Client;
import de.fanta.clickgui.intellij.ClickGuiMainPane;
import de.fanta.clickgui.intellij.openables.ClickGuiOpenable;
import de.fanta.utils.FileUtil;
import java.awt.Color;
import java.io.File;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class ClickGuiOpenableConfig
extends ClickGuiOpenable {
    private String config;
    private ClickGuiMainPane pane;
    private File dir;
    private boolean online;

    public ClickGuiOpenableConfig(float x, float y, float baseHeight, float width, String config, ClickGuiMainPane pane, boolean online) {
        super(x, y, baseHeight, width, null, ClickGuiOpenable.Type.CLASS, config);
        this.dir = new File(Minecraft.getMinecraft().mcDataDir + "/" + Client.INSTANCE.name + "/" + "configs" + "/");
        this.config = config;
        this.pane = pane;
        this.online = online;
    }

    @Override
    public void draw(float mouseX, float mouseY) {
        String useString;
        Minecraft mc = Minecraft.getMinecraft();
        boolean hovered = false;
        String string = useString = !this.config.endsWith(".txt") ? this.config : this.config.substring(0, this.config.length() - 4);
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
                            if (!this.online) {
                                if (Arrays.asList(this.dir.list()).contains(useString.endsWith(".txt") ? useString : String.valueOf(useString) + ".txt")) {
                                    System.out.println("test");
                                    FileUtil.loadValues(useString, true, false);
                                    this.lastMS = System.currentTimeMillis();
                                }
                            } else {
                                try {
                                    FileUtil.loadValues(useString, true, true);
                                }
                                catch (Exception exception) {
                                    // empty catch block
                                }
                            }
                        }
                        if (!Mouse.isButtonDown((int)1)) break;
                        if (!this.online) {
                            if (!Arrays.asList(this.dir.list()).contains(useString.endsWith(".txt") ? useString : String.valueOf(useString) + ".txt")) break;
                            FileUtil.loadValues(useString, true, false);
                            this.lastMS = System.currentTimeMillis();
                            break;
                        }
                        try {
                            FileUtil.loadValues(useString, true, true);
                        }
                        catch (Exception exception) {
                        }
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
                ClickGuiOpenableConfig.drawModalRectWithCustomSizedTexture(this.x + 3.0f, this.y + 2.0f, 0.0f, 0.0f, 12.0f, 12.0f, 12.0f, 12.0f);
                GlStateManager.popMatrix();
                if (!this.online) {
                    MENU_FONT.drawString(this.name, this.x + 16.0f, this.y + 2.0f, Arrays.asList(this.dir.list()).contains(useString.endsWith(".txt") ? useString : String.valueOf(useString) + ".txt") ? Color.white.getRGB() : Color.red.getRGB());
                } else {
                    MENU_FONT.drawString(this.name, this.x + 16.0f, this.y + 2.0f, ClickGuiMainPane.MODIFIER_AND_TYPE_FONT_COLOR);
                }
                this.width = 16 + MENU_FONT.getStringWidth(this.name) + 2;
                break;
            }
        }
    }
}

