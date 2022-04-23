// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.gui;

import java.util.Iterator;
import com.mojang.realmsclient.gui.ChatFormatting;
import bozoware.base.api.GuiAltLogin;
import net.minecraft.util.Session;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.GuiConnecting;
import bozoware.impl.UI.BozoMainMenu;
import bozoware.impl.module.visual.SessionInfo;
import java.io.IOException;
import net.minecraft.client.resources.I18n;
import java.util.List;
import net.minecraft.util.IChatComponent;

public class GuiDisconnected extends GuiScreen
{
    private String reason;
    private IChatComponent message;
    private List<String> multilineMessage;
    private final GuiScreen parentScreen;
    private int field_175353_i;
    static int initTime;
    static String name;
    
    public GuiDisconnected(final GuiScreen screen, final String reasonLocalizationKey, final IChatComponent chatComp) {
        this.parentScreen = screen;
        this.reason = I18n.format(reasonLocalizationKey, new Object[0]);
        this.message = chatComp;
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
    }
    
    @Override
    public void initGui() {
        GuiDisconnected.initTime = (int)System.currentTimeMillis() - SessionInfo.bruh;
        this.buttonList.clear();
        this.multilineMessage = (List<String>)this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
        this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu", new Object[0])));
        if (this.mc.getCurrentServerData() != null) {
            this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 24, "Reconnect"));
            this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 48, "Reconnect With Cracked Alt"));
            this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 48 + 24, "Alt Manager "));
        }
        else {
            this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 24, "Alt Manager "));
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 0) {
            SessionInfo.bruh = (int)System.currentTimeMillis();
            this.mc.displayGuiScreen(this.parentScreen);
        }
        if (button.id == 1 && this.mc.getCurrentServerData() != null) {
            this.mc.displayGuiScreen(new GuiConnecting(new BozoMainMenu(), this.mc, this.mc.getCurrentServerData()));
        }
        if (button.id == 2) {
            SessionInfo.bruh = (int)System.currentTimeMillis();
            String bozo = "Bozo";
            final String[] numba = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
            for (int i = 0; i < 12; ++i) {
                bozo += numba[(int)Math.floor(Math.random() * numba.length)];
            }
            Minecraft.getMinecraft().session = new Session(bozo, "", "", "mojang");
            if (this.mc.getCurrentServerData() != null) {
                this.mc.displayGuiScreen(new GuiConnecting(new BozoMainMenu(), this.mc, this.mc.getCurrentServerData()));
            }
        }
        if (button.id == 3) {
            SessionInfo.bruh = (int)System.currentTimeMillis();
            this.mc.displayGuiScreen(new GuiAltLogin());
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
        int i = this.height / 2 - this.field_175353_i / 2;
        if (this.multilineMessage != null) {
            for (final String s : this.multilineMessage) {
                this.drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
                i += this.fontRendererObj.FONT_HEIGHT;
            }
            this.drawCenteredString(this.fontRendererObj, "Playtime: " + ChatFormatting.RED + GuiDisconnected.initTime / 3600000 % 24 + "h " + GuiDisconnected.initTime / 60000 % 60 + "m " + GuiDisconnected.initTime / 1000 % 60 + "s", this.width / 2, i - 200, 16777215);
            super.drawScreen(mouseX, mouseY, partialTicks);
        }
    }
}
