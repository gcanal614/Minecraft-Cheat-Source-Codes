// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.UI;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.io.InputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Stream;
import bozoware.visual.screens.alt.GuiAltManager;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiButton;
import bozoware.visual.font.MinecraftFontRenderer;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import java.io.IOException;
import bozoware.base.util.visual.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import bozoware.base.BozoWare;
import bozoware.base.GLSLShader.GLSLSandboxShader;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class BozoMainMenu extends GuiScreen
{
    public static String UserName;
    public static int hA;
    public static int hA1;
    public static int hA2;
    private static final List<String> changeLogEntries;
    private GLSLSandboxShader backgroundShader;
    private long initTime;
    
    public BozoMainMenu() {
        this.initTime = System.currentTimeMillis();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final MinecraftFontRenderer Icons = BozoWare.getInstance().getFontManager().MenuIcons;
        final MinecraftFontRenderer Icons2 = BozoWare.getInstance().getFontManager().MenuIcons2;
        final MinecraftFontRenderer SLFR = BozoWare.getInstance().getFontManager().largeFontRenderer2;
        final MinecraftFontRenderer MFR = BozoWare.getInstance().getFontManager().mediumFontRenderer;
        final MinecraftFontRenderer SFR = BozoWare.getInstance().getFontManager().smallFontRenderer;
        final ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        Gui.drawRect(0.0, 0.0, sr.getScaledWidth(), sr.getScaledHeight(), -14737633);
        RenderUtil.drawSmoothRoundedRect(sr.getScaledWidth() / 2.0f - 110.0f, sr.getScaledHeight() / 2.0f - 100.0f, sr.getScaledWidth() / 2.0f + 110.0f, sr.getScaledHeight() / 2.0f + 75.0f, 15.0f, -15263977);
        RenderUtil.drawRoundedOutline(sr.getScaledWidth() / 2.0f - 110.0f, sr.getScaledHeight() / 2.0f - 100.0f, sr.getScaledWidth() / 2.0f + 110.0f, sr.getScaledHeight() / 2.0f + 75.0f, -16777216, 3.0f, 2.0f);
        RenderUtil.drawRoundedOutline(sr.getScaledWidth() / 2.0f - 110.0f, sr.getScaledHeight() / 2.0f - 100.0f, sr.getScaledWidth() / 2.0f + 110.0f, sr.getScaledHeight() / 2.0f + 75.0f, -12829636, 2.0f, 2.0f);
        RenderUtil.drawRoundedOutline(sr.getScaledWidth() / 2.0f - 110.0f, sr.getScaledHeight() / 2.0f - 100.0f, sr.getScaledWidth() / 2.0f + 110.0f, sr.getScaledHeight() / 2.0f + 75.0f, -14145496, 0.8f, 2.0f);
        RenderUtil.glHorizontalGradientQuad(sr.getScaledWidth() / 2.0f - 108.0f, sr.getScaledHeight() / 2.0f - 98.5, 108.0, 2.0, -13127206, -3644747);
        RenderUtil.glHorizontalGradientQuad(sr.getScaledWidth() / 2.0f - 2.0f, sr.getScaledHeight() / 2.0f - 98.5, 110.5, 2.0, -3644747, -3349962);
        Icons.drawStringWithShadow("e", 8.0, sr.getScaledHeight() - 525, BozoMainMenu.hA1);
        Icons.drawStringWithShadow("d", sr.getScaledWidth() - 25, sr.getScaledHeight() - 525, BozoMainMenu.hA2);
        Icons2.drawStringWithShadow("b", sr.getScaledWidth() / 2.0f - 475.0f, sr.getScaledHeight() - 525, BozoMainMenu.hA);
        SLFR.drawCenteredStringWithShadow("BozoWare", sr.getScaledWidth() / 2.0f, sr.getScaledHeight() / 2.0f - 75.0f, -1);
        try {
            this.backgroundShader = new GLSLSandboxShader("/bozoware/base/GLSLShader/noise1.fsh");
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
        MFR.drawCenteredStringWithShadow("Authenticated, Welcome To BozoWare " + EnumChatFormatting.RED + BozoMainMenu.UserName, sr.getScaledWidth() / 2.0f, sr.getScaledHeight() - 12, 1090519039);
        final MinecraftFontRenderer minecraftFontRenderer = SFR;
        final StringBuilder append = new StringBuilder().append("Version ").append(EnumChatFormatting.RED);
        BozoWare.getInstance().getClass();
        minecraftFontRenderer.drawCenteredStringWithShadow(append.append("220331").toString(), sr.getScaledWidth() / 2.0f, sr.getScaledHeight() - 22, 1090519039);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void initGui() {
        final int j = this.height / 2 - 55;
        final int x = this.width / 2 - 100;
        final ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        this.buttonList.add(new GuiButton(1, x, j + 10, "Singleplayer"));
        this.buttonList.add(new GuiButton(2, x, j + 35, "Multiplayer"));
        this.buttonList.add(new GuiButton(3, x, j + 60, "Alt Manager"));
        this.buttonList.add(new GuiButton(4, x + 51, j + 85, 98, 20, "Quit"));
        this.buttonList.add(new GuiButton(5, sr.getScaledWidth() - 100, sr.getScaledHeight() - 22, 98, 20, "Credits"));
        super.initGui();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (button.id == 3) {
            this.mc.displayGuiScreen(new GuiAltManager());
        }
        if (button.id == 4) {
            this.mc.shutdown();
        }
        if (button.id == 5) {
            this.mc.displayGuiScreen(new BozoCreditsMenu());
        }
        super.actionPerformed(button);
    }
    
    private static Stream<String> fetchChangeLog() {
        try {
            final URL url = new URL("https://kobleyauthentication.000webhostapp.com/changelog");
            final InputStream is = url.openStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return reader.lines();
        }
        catch (IOException e) {
            return Stream.empty();
        }
    }
    
    public boolean isShidder() {
        return BozoMainMenu.UserName.contains("Shidder") && BozoAuthMenu.UIDText.getText().contains("0000") && BozoAuthMenu.UIDText.getText().length() == 4;
    }
    
    static {
        BozoMainMenu.UserName = BozoWare.BozoUserName;
        changeLogEntries = fetchChangeLog().collect((Collector<? super String, ?, List<String>>)Collectors.toList());
    }
}
