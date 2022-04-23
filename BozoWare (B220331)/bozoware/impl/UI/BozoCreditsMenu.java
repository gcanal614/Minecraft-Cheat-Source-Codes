// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.UI;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.client.gui.GuiButton;
import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Stream;
import java.util.Iterator;
import bozoware.visual.font.MinecraftFontRenderer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import bozoware.base.BozoWare;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class BozoCreditsMenu extends GuiScreen
{
    private static final List<String> credits;
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final MinecraftFontRenderer SLFR = BozoWare.getInstance().getFontManager().largeFontRenderer2;
        final MinecraftFontRenderer LFR = BozoWare.getInstance().getFontManager().largeFontRenderer;
        final MinecraftFontRenderer SFR = BozoWare.getInstance().getFontManager().smallFontRenderer;
        final ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        Gui.drawRect(0.0, 0.0, sr.getScaledWidth(), sr.getScaledHeight(), -15263977);
        Gui.drawRect(50.0, 50.0, sr.getScaledWidth() - 50, sr.getScaledHeight() - 50, 1880561431);
        final MinecraftFontRenderer minecraftFontRenderer = SFR;
        final StringBuilder append = new StringBuilder().append("Version ").append(EnumChatFormatting.RED);
        BozoWare.getInstance().getClass();
        minecraftFontRenderer.drawCenteredStringWithShadow(append.append("220331").toString(), sr.getScaledWidth() / 2, sr.getScaledHeight() - 10, 1090519039);
        int yPos = sr.getScaledHeight() / 2 - 120;
        final int xPos = sr.getScaledWidth() / 2 - 120;
        for (final String line : BozoCreditsMenu.credits) {
            final char type = line.charAt(1);
            final int col = (type == '+') ? 65369 : ((type == '-') ? 16711712 : 16776960);
            final int alpha = (mouseX > xPos && mouseX < xPos + SFR.getStringWidth(line) && mouseY > yPos && mouseY < yPos + SFR.getHeight() + 2) ? 255 : 128;
            SFR.drawStringWithShadow(line, xPos, yPos, col | alpha << 24);
            yPos += SFR.getHeight() + 15;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    private static Stream<String> fetchChangeLog() {
        try {
            final URL url = new URL("https://pastebin.com/raw/hUg4NJvu");
            final InputStream is = url.openStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return reader.lines();
        }
        catch (IOException e) {
            return Stream.empty();
        }
    }
    
    @Override
    public void initGui() {
        final int j = this.height / 2 - 55;
        final int x = this.width / 2 - 100;
        super.initGui();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 1) {
            this.mc.displayGuiScreen(new BozoMainMenu());
        }
        super.actionPerformed(button);
    }
    
    static {
        credits = fetchChangeLog().collect((Collector<? super String, ?, List<String>>)Collectors.toList());
    }
}
