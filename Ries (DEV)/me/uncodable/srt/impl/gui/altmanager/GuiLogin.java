/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
 *  org.lwjgl.input.Keyboard
 */
package me.uncodable.srt.impl.gui.altmanager;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.net.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

public class GuiLogin
extends GuiScreen {
    private GuiTextField username;
    private GuiTextField password;
    private static final Minecraft MC = Minecraft.getMinecraft();

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.mc.fontRendererObj, String.format("Alt Login (Current User: %s)", GuiLogin.MC.session.getUsername()), this.width / 2, 20, 0xFFFFFF);
        this.username.drawTextBox();
        this.password.drawTextBox();
        if (this.username.getText().isEmpty() && !this.username.isFocused()) {
            GuiLogin.MC.fontRendererObj.drawStringWithShadow("Username", (float)this.width / 2.0f - 96.0f, 66.0f, 0xAAAAAA);
        }
        if (this.password.getText().isEmpty() && !this.password.isFocused()) {
            GuiLogin.MC.fontRendererObj.drawStringWithShadow("Password", (float)this.width / 2.0f - 96.0f, 106.0f, 0xAAAAAA);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        int var3 = this.height / 4 + 24;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, var3 + 72 + 12 + 24, "Clipboard"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, var3 + 72 + 12 + 48, "Back"));
        this.username = new GuiTextField(var3, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.password = new GuiTextField(var3 + 24, this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents((boolean)true);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
                new Thread(() -> {
                    GuiLogin.MC.session = this.createSession(this.username.getText(), this.password.getText());
                }).start();
                break;
            }
            case 1: {
                try {
                    String data = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    if (!data.contains(":")) break;
                    String[] credentials = data.split(":");
                    if (credentials[0] != null) {
                        this.username.setText(credentials[0]);
                    }
                    if (credentials[1] == null) break;
                    this.password.setText(credentials[1]);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                MC.displayGuiScreen(new GuiMainMenu());
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        try {
            super.keyTyped(typedChar, keyCode);
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (typedChar == '\t') {
            if (!this.username.isFocused() && !this.password.isFocused()) {
                this.username.setFocused(true);
            } else {
                this.username.setFocused(this.password.isFocused());
                this.password.setFocused(!this.username.isFocused());
            }
        } else if (typedChar == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(typedChar, keyCode);
        this.password.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        try {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.username.mouseClicked(mouseX, mouseY, mouseButton);
        this.password.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    private Session createSession(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        }
        catch (Exception e) {
            return new Session(username, "", auth.getAuthenticatedToken(), "mojang");
        }
    }
}

