/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package de.fanta.utils;

import de.fanta.Client;
import de.fanta.gui.font.BasicFontRenderer;
import java.awt.Color;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

public class LoginScreen
extends GuiScreen {
    public GuiTextField name;
    public GuiTextField password;
    public String status = "";
    public String statuse = "";
    private int wdithfade = 0;
    private int rfade = 0;
    private int fade = 0;

    @Override
    public void initGui() {
        this.wdithfade = -180;
        this.rfade = 0;
        this.fade = 50;
        Keyboard.enableRepeatEvents((boolean)true);
        int var3 = height / 4 + 48;
        this.buttonList.add(new GuiButton(1, width / 2 - 103, var3 + 72 - 5, 98, 20, "Login"));
        this.buttonList.add(new GuiButton(2, width / 2 + 3, var3 + 72 - 5, 98, 20, "Quit"));
        this.name = new GuiTextField(3, this.fontRendererObj, width / 2 - 102, 215, 202, 20);
        this.password = new GuiTextField(3, this.fontRendererObj, width / 2 - 100, 116, 200, 20);
        this.name.setMaxStringLength(50);
        this.password.setMaxStringLength(50);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    public void updateScreen() {
        this.name.updateCursorCounter();
        this.password.updateCursorCounter();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.name.mouseClicked(mouseX, mouseY, mouseButton);
        this.password.mouseClicked(mouseX, mouseY, mouseButton);
        try {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        block10: {
            if (button.id == 1) {
                if (!this.name.getText().isEmpty()) {
                    URL url;
                    try {
                        url = new URL("https://skidsense.000webhostapp.com/auth/lcaauth.php?hwid=" + LoginScreen.getHwid());
                    }
                    catch (UnsupportedEncodingException | MalformedURLException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        return;
                    }
                    Scanner scanner = new Scanner(url.openStream());
                    String scanners = "";
                    while (scanner.hasNext()) {
                        scanners = String.valueOf(scanners) + scanner.next();
                    }
                    try {
                        String[] Split = scanners.split(":");
                        String UID = Split[0];
                        String HWID = Split[1];
                        Boolean.parseBoolean(HWID);
                        if (this.name.getText().equals(UID) && Boolean.parseBoolean(HWID)) {
                            this.mc.displayGuiScreen(new GuiMainMenu());
                            break block10;
                        }
                        this.statuse = "";
                        this.status = "Ung\u00fcltige UID/HWID";
                    }
                    catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
                } else {
                    this.statuse = "";
                    this.status = "Bitte geben sie etwas ein!";
                }
            }
        }
        if (button.id == 2) {
            this.mc.shutdown();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.name.textboxKeyTyped(typedChar, keyCode);
        this.password.textboxKeyTyped(typedChar, keyCode);
        if (typedChar == '\t') {
            if (this.name.isFocused()) {
                this.name.setFocused(false);
                this.password.setFocused(true);
            } else {
                this.name.setFocused(true);
                this.password.setFocused(false);
            }
        }
        if (typedChar == '\r') {
            try {
                this.actionPerformed((GuiButton)this.buttonList.get(0));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        BasicFontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        ScaledResolution si = new ScaledResolution(this.mc);
        Client.getBackgrundAPI3().renderShader();
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        Gui.drawRect((float)scaledresolution.getScaledWidth() / 2.0f + 110.0f, (float)scaledresolution.getScaledHeight() / 2.0f - 90.0f, scaledresolution.getScaledWidth() / 2 - 110, (float)scaledresolution.getScaledHeight() / 2.0f + 25.0f, new Color(0, 0, 0, 120).getRGB());
        this.name.drawTextBox();
        this.wdithfade = this.wdithfade < width ? (this.wdithfade += 2) : -180;
        if (this.fade < 290) {
            ++this.fade;
        }
        if (this.rfade < 110) {
            ++this.rfade;
        }
        Client.INSTANCE.unicodeBasicFontRenderer2.drawCenteredString("UID-Login", width / 3 + 152, 180.0f, Color.WHITE.getRGB());
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(this.status, width / 3 + 112, 266.0f, Color.red.getRGB());
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(this.statuse, width / 4, 266.0f, Color.red.getRGB());
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(this.statuse, width / 3 + 112, 266.0f, Color.red.getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
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
        int b = 0;
        while (b < j) {
            byte b2 = arrayOfByte1[b];
            hwid = String.valueOf(String.valueOf(String.valueOf(hwid))) + Integer.toHexString(b2 + 255 | 0x100).substring(0, 3);
            if (i != md5.length - 1) {
                hwid = String.valueOf(String.valueOf(String.valueOf(hwid))) + "-";
            }
            ++i;
            b = (byte)(b + 1);
        }
        return hwid;
    }
}

