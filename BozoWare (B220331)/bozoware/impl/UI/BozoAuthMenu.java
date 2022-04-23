// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.UI;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import bozoware.base.security.utils.SecurityUtils;
import bozoware.base.security.Auth;
import net.minecraft.client.gui.GuiButton;
import bozoware.visual.font.MinecraftFontRenderer;
import bozoware.base.util.visual.RenderUtil;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import java.io.IOException;
import net.minecraft.client.gui.ScaledResolution;
import bozoware.base.BozoWare;
import bozoware.base.GLSLShader.GLSLSandboxShader;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiScreen;

public class BozoAuthMenu extends GuiScreen
{
    public static GuiTextField UIDText;
    public static String Status;
    public static int StatusColor;
    private GLSLSandboxShader backgroundShader;
    private long initTime;
    
    public BozoAuthMenu() {
        this.initTime = System.currentTimeMillis();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final MinecraftFontRenderer Icons = BozoWare.getInstance().getFontManager().MenuIcons;
        final MinecraftFontRenderer SLFR = BozoWare.getInstance().getFontManager().largeFontRenderer2;
        final MinecraftFontRenderer MFR = BozoWare.getInstance().getFontManager().mediumFontRenderer;
        final ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        try {
            this.backgroundShader = new GLSLSandboxShader("/bozoware/base/GLSLShader/noise2.fsh");
        }
        catch (IOException e) {
            throw new IllegalStateException("Failed to load shader", e);
        }
        GlStateManager.disableCull();
        this.backgroundShader.useShader(this.mc.displayWidth - 700, this.mc.displayHeight, (float)mouseX, (float)mouseY, (System.currentTimeMillis() - this.initTime) / 1000.0f);
        GL11.glBegin(7);
        GL11.glVertex2f(-1.0f, -1.0f);
        GL11.glVertex2f(-1.0f, 1.0f);
        GL11.glVertex2f(1.0f, 1.0f);
        GL11.glVertex2f(1.0f, -1.0f);
        GL11.glEnd();
        GL20.glUseProgram(0);
        RenderUtil.drawSmoothRoundedRect((float)(sr.getScaledWidth() / 2 - 110), (float)(sr.getScaledHeight() / 2 - 100), (float)(sr.getScaledWidth() / 2 + 110), (float)(sr.getScaledHeight() / 2 + 75), 15.0f, -15263977);
        RenderUtil.drawRoundedOutline((float)(sr.getScaledWidth() / 2 - 110), (float)(sr.getScaledHeight() / 2 - 100), (float)(sr.getScaledWidth() / 2 + 110), (float)(sr.getScaledHeight() / 2 + 75), -16777216, 3.0f, 2.0f);
        RenderUtil.drawRoundedOutline((float)(sr.getScaledWidth() / 2 - 110), (float)(sr.getScaledHeight() / 2 - 100), (float)(sr.getScaledWidth() / 2 + 110), (float)(sr.getScaledHeight() / 2 + 75), -12829636, 2.0f, 2.0f);
        RenderUtil.drawRoundedOutline((float)(sr.getScaledWidth() / 2 - 110), (float)(sr.getScaledHeight() / 2 - 100), (float)(sr.getScaledWidth() / 2 + 110), (float)(sr.getScaledHeight() / 2 + 75), -14145496, 0.8f, 2.0f);
        RenderUtil.glHorizontalGradientQuad(sr.getScaledWidth() / 2 - 108, sr.getScaledHeight() / 2 - 98.5, 108.0, 2.0, -13127206, -3644747);
        RenderUtil.glHorizontalGradientQuad(sr.getScaledWidth() / 2 - 2, sr.getScaledHeight() / 2 - 98.5, 110.0, 2.0, -3644747, -3349962);
        SLFR.drawSmoothString("BozoWare", sr.getScaledWidth() / 2 - 45, sr.getScaledHeight() / 2 - 75.0f, -1, true);
        MFR.drawCenteredStringWithShadow(BozoAuthMenu.Status, sr.getScaledWidth() / 2, 10.0, BozoAuthMenu.StatusColor);
        BozoAuthMenu.UIDText.drawTextBox();
        if (BozoAuthMenu.UIDText.getText().length() == 0 && !BozoAuthMenu.UIDText.isFocused()) {
            MFR.drawString("§7Enter UID", this.width / 2 - 100 + 53, this.height / 2 - 55 + 56, -1);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void keyTyped(final char character, final int key) {
        BozoAuthMenu.UIDText.textboxKeyTyped(character, key);
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        BozoAuthMenu.UIDText.mouseClicked(mouseX, mouseY, button);
        try {
            super.mouseClicked(mouseX, mouseY, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void initGui() {
        final int j = this.height / 2 - 55;
        final int x = this.width / 2 - 100;
        BozoAuthMenu.UIDText = new GuiTextField(69420, this.mc.fontRendererObj, x + 51, j + 50, 98, 20);
        this.buttonList.add(new GuiButton(69420, x + 51, j + 85, 98, 20, "Login"));
        this.buttonList.add(new GuiButton(69, x + 51, j + 85 + 19, 98, 20, "Copy HWID"));
        super.initGui();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        final ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        if (button.id == 69420) {
            Auth.LoadClient(1L, 1L);
        }
        if (button.id == 69) {
            final StringSelection stringSelection = new StringSelection(SecurityUtils.getHWID());
            final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            BozoAuthMenu.StatusColor = -9408480;
            BozoAuthMenu.Status = "HWID Copied! DM shidder to get whitelisted.";
        }
        super.actionPerformed(button);
    }
    
    static {
        BozoAuthMenu.Status = "Idle...";
        BozoAuthMenu.StatusColor = -1;
    }
}
