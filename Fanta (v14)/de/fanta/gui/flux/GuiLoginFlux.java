/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package de.fanta.gui.flux;

import de.fanta.gui.flux.GuiButtonFlux;
import de.fanta.gui.flux.GuiTextFieldFlux;
import de.fanta.gui.font.ClientFont;
import de.fanta.gui.font.GlyphPageFontRenderer;
import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class GuiLoginFlux
extends GuiScreen {
    private GuiScreen parent;
    GlyphPageFontRenderer icons;
    GlyphPageFontRenderer logoFont;
    GlyphPageFontRenderer icons2;
    GlyphPageFontRenderer clicks;
    GuiTextFieldFlux usernameField;
    GuiTextFieldFlux passwordField;

    public GuiLoginFlux(GuiScreen parent) {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GuiLoginFlux.drawRect(0.0f, 0.0f, width, height, Color.white.getRGB());
        this.mc.getTextureManager().bindTexture(new ResourceLocation("Fanta/gui/loginbackground.png"));
        GuiLoginFlux.drawModalRectWithCustomSizedTexture(0.0f, 0.0f, 150.0f, 0.0f, width - 150, height, width, height);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.icons.drawString("q", width - 125, 27.0f, Color.decode("#0FA292").getRGB(), false);
        this.logoFont.drawString("Fanta", width - 107, 24.0f, Color.decode("#0FA292").getRGB(), false);
        this.icons2.drawString("q", width - 60, height - 60, new Color(220, 220, 220).getRGB());
        this.clicks = ClientFont.font(13, ClientFont.FontType.ARIAL, true);
        String clickOne = "No Account?";
        boolean hoveredOne = mouseX >= width - 85 - this.clicks.getStringWidth(clickOne) && mouseX <= width - 85 && mouseY >= height - 44 && mouseY <= height - 44 + this.clicks.getFontHeight();
        this.clicks.drawString(clickOne, width - 85 - this.clicks.getStringWidth(clickOne), height - 44, hoveredOne ? Color.decode("#0C8275").getRGB() : Color.decode("#0FA292").getRGB(), false);
        String clickTwo = "Password Reset";
        boolean hoveredTwo = mouseX >= width - 85 - this.clicks.getStringWidth(clickOne) && mouseX <= width - 85 && mouseY >= height - 29 && mouseY <= height - 29 + this.clicks.getFontHeight();
        this.clicks.drawString(clickTwo, width - 85 - this.clicks.getStringWidth(clickOne), height - 29, hoveredTwo ? Color.decode("#7D7D7D").getRGB() : Color.decode("#9D9D9D").getRGB(), false);
        if (hoveredOne && Mouse.isButtonDown((int)0)) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (hoveredTwo && Mouse.isButtonDown((int)0)) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.usernameField.drawTextBox();
        this.passwordField.drawTextBox();
        for (GuiButton button : this.buttonList) {
            if (button.id != 1) continue;
            button.enabled = !this.usernameField.getText().isEmpty();
            boolean bl = button.enabled;
        }
    }

    @Override
    public void initGui() {
        this.icons = ClientFont.font(30, ClientFont.FontType.FLUX_ICONS, true);
        this.icons2 = ClientFont.font(150, ClientFont.FontType.FLUX_ICONS, true);
        this.logoFont = ClientFont.font(34, "tahomabold", true);
        this.buttonList.add(new GuiButtonFlux(0, width - 125, height - 115, 100, 15, "GET HWID"));
        this.buttonList.add(new GuiButtonFlux(1, width - 125, height - 95, 100, 25, "LOGIN"));
        this.usernameField = new GuiTextFieldFlux(0, this.fontRendererObj, width - 125, height / 2 - 50, 100, 20, "UID");
        this.passwordField = new GuiTextFieldFlux(0, this.fontRendererObj, width - 125, height / 2 - 20, 100, 20, "COMING SOON");
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            try {
                GuiLoginFlux.setClipboardString(GuiLoginFlux.getHwid());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.usernameField.mouseClicked(mouseX, mouseY, mouseButton);
        this.passwordField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        this.usernameField.updateCursorCounter();
        this.passwordField.updateCursorCounter();
        super.updateScreen();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.usernameField.textboxKeyTyped(typedChar, keyCode);
        this.passwordField.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    public static String getHwid() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String hwid = "";
        String main = (String.valueOf(String.valueOf(String.valueOf(System.getenv("PROCESSOR_IDENTIFIER")))) + System.getenv("COMPUTERNAME") + System.getProperty("user.name")).trim();
        byte[] bytes = main.getBytes(StandardCharsets.UTF_8);
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] md5 = messageDigest.digest(bytes);
        int i = 0;
        byte[] arrayOfByte1 = md5;
        int j = md5.length;
        for (int b = 0; b < j; b = (int)((byte)(b + 1))) {
            byte b2 = arrayOfByte1[b];
            hwid = String.valueOf(String.valueOf(String.valueOf(hwid))) + Integer.toHexString(b2 + 255 | 0x100).substring(0, 3);
            if (i != md5.length - 1) {
                hwid = String.valueOf(String.valueOf(String.valueOf(hwid))) + "-";
            }
            ++i;
        }
        return hwid;
    }
}

