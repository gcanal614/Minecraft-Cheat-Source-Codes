// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.UI;

import java.io.IOException;
import bozoware.base.security.utils.SecurityUtils;
import net.minecraft.client.gui.GuiButton;
import bozoware.visual.font.MinecraftFontRenderer;
import net.minecraft.util.EnumChatFormatting;
import bozoware.base.util.visual.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import bozoware.base.BozoWare;
import net.minecraft.client.gui.GuiScreen;

public class BozoInfoMenu extends GuiScreen
{
    public static String UserName;
    public static String UserUID;
    public static String UserCurrentIP;
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final MinecraftFontRenderer SLFR = BozoWare.getInstance().getFontManager().largeFontRenderer2;
        final MinecraftFontRenderer LFR = BozoWare.getInstance().getFontManager().largeFontRenderer;
        final MinecraftFontRenderer SFR = BozoWare.getInstance().getFontManager().smallFontRenderer;
        final ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        Gui.drawRect(0.0, 0.0, sr.getScaledWidth(), sr.getScaledHeight(), -15263977);
        RenderUtil.drawSmoothRoundedRect((float)(sr.getScaledWidth() / 2 - 110), (float)(sr.getScaledHeight() / 2 - 100), (float)(sr.getScaledWidth() / 2 + 110), (float)(sr.getScaledHeight() / 2 + 75), 15.0f, -14737633);
        RenderUtil.drawRoundedOutline((float)(sr.getScaledWidth() / 2 - 110), (float)(sr.getScaledHeight() / 2 - 100), (float)(sr.getScaledWidth() / 2 + 110), (float)(sr.getScaledHeight() / 2 + 75), -16777216, 3.0f, 2.0f);
        RenderUtil.drawRoundedOutline((float)(sr.getScaledWidth() / 2 - 110), (float)(sr.getScaledHeight() / 2 - 100), (float)(sr.getScaledWidth() / 2 + 110), (float)(sr.getScaledHeight() / 2 + 75), -12829636, 2.0f, 2.0f);
        RenderUtil.drawRoundedOutline((float)(sr.getScaledWidth() / 2 - 110), (float)(sr.getScaledHeight() / 2 - 100), (float)(sr.getScaledWidth() / 2 + 110), (float)(sr.getScaledHeight() / 2 + 75), -14145496, 0.8f, 2.0f);
        RenderUtil.glHorizontalGradientQuad(sr.getScaledWidth() / 2 - 108, sr.getScaledHeight() / 2 - 98.5, 108.0, 2.0, -13127206, -3644747);
        RenderUtil.glHorizontalGradientQuad(sr.getScaledWidth() / 2 - 2, sr.getScaledHeight() / 2 - 98.5, 110.5, 2.0, -3644747, -3349962);
        SLFR.drawCenteredStringWithShadow("Name: " + BozoInfoMenu.UserName, sr.getScaledWidth() / 2, sr.getScaledHeight() / 2 - 75.0f, -1);
        LFR.drawCenteredStringWithShadow("UID: " + BozoInfoMenu.UserUID, sr.getScaledWidth() / 2, sr.getScaledHeight() / 2 - 50.0f, -1);
        LFR.drawCenteredStringWithShadow("Current Ip: " + BozoInfoMenu.UserCurrentIP, sr.getScaledWidth() / 2, sr.getScaledHeight() / 2 - 35.0f, -1);
        final MinecraftFontRenderer minecraftFontRenderer = SFR;
        final StringBuilder append = new StringBuilder().append("Version ").append(EnumChatFormatting.RED);
        BozoWare.getInstance().getClass();
        minecraftFontRenderer.drawCenteredStringWithShadow(append.append("220331").toString(), sr.getScaledWidth() / 2, sr.getScaledHeight() - 10, 1090519039);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void initGui() {
        final int j = this.height / 2 - 55;
        final int x = this.width / 2 - 100;
        this.buttonList.add(new GuiButton(1, x + 51, j + 85, 98, 20, "Back"));
        this.buttonList.add(new GuiButton(2, x + 51, j + 60, 98, 20, "Get Current IP"));
        super.initGui();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 1) {
            this.mc.displayGuiScreen(new BozoMainMenu());
        }
        if (button.id == 2) {
            BozoInfoMenu.UserCurrentIP = SecurityUtils.grabCurrentIP();
        }
        super.actionPerformed(button);
    }
    
    static {
        BozoInfoMenu.UserName = BozoWare.BozoUserName;
        BozoInfoMenu.UserUID = BozoAuthMenu.UIDText.getText();
    }
}
