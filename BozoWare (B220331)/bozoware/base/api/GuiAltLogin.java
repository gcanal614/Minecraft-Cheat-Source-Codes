// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.api;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.net.Proxy;
import bozoware.visual.screens.alt.GuiAltManager;
import com.mojang.authlib.exceptions.AuthenticationException;
import net.minecraft.util.Session;
import java.util.Objects;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiScreen;

public class GuiAltLogin extends GuiScreen
{
    private GuiTextField emailField;
    private GuiTextField passwordField;
    private String status;
    
    public GuiAltLogin() {
        this.status = "Idle...";
    }
    
    public void keyTyped(final char character, final int key) {
        this.emailField.textboxKeyTyped(character, key);
        this.passwordField.textboxKeyTyped(character, key);
    }
    
    @Override
    public void initGui() {
        this.emailField = new GuiTextField(this.height / 4 + 24, Minecraft.getMinecraft().fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.passwordField = new GuiTextField(this.height / 4 + 44, this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
        final ScaledResolution sr = new ScaledResolution(this.mc, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        final int var3 = this.height / 4 + 24;
        this.buttonList.add(new GuiButton(1, sr.getScaledWidth() / 2 - 100, var3 + 72 - 12, "Import email:pass"));
        this.buttonList.add(new GuiButton(2, sr.getScaledWidth() / 2 - 100, var3 + 72 + 12, "Login"));
        this.buttonList.add(new GuiButton(3, sr.getScaledWidth() / 2 - 100, var3 + 72 + 12 + 24, "Generate Cracked"));
        this.buttonList.add(new GuiButton(4, sr.getScaledWidth() / 2 - 100, var3 + 72 + 12 + 24 + 24, "Back to menu"));
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.emailField.drawTextBox();
        this.passwordField.drawTextBox();
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.status, (float)(this.width / 2.0 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.status) / 2), 25.0f, -1);
        if (this.emailField.getText().length() == 0) {
            this.mc.fontRendererObj.drawString("§7Username / Email", (float)(int)(this.width / 2.0f - 95.0f), 66.0f, -1);
        }
        if (this.passwordField.getText().length() == 0) {
            this.mc.fontRendererObj.drawString("§7Password", (float)(int)(this.width / 2.0f - 95.0f), 106.0f, -1);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        this.emailField.mouseClicked(mouseX, mouseY, button);
        this.passwordField.mouseClicked(mouseX, mouseY, button);
        try {
            super.mouseClicked(mouseX, mouseY, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 1: {
                final String clipBoardText = GuiScreen.getClipboardString();
                if (clipBoardText.contains("@") && clipBoardText.contains(":")) {
                    this.emailField.setText(clipBoardText.split(":")[0]);
                    this.passwordField.setText(clipBoardText.split(":")[1].replaceAll("\n", ""));
                    break;
                }
                break;
            }
            case 2: {
                try {
                    if (!Objects.equals(this.passwordField.getText(), "")) {
                        this.loginToAccount(this.emailField.getText(), this.passwordField.getText());
                    }
                    else {
                        Minecraft.getMinecraft().session = new Session(this.emailField.getText(), "", "", "mojang");
                    }
                    this.status = "Logged in as: §e" + this.mc.getSession().getUsername();
                }
                catch (AuthenticationException e) {
                    this.status = "§4Invalid details!";
                    e.printStackTrace();
                }
                break;
            }
            case 3: {
                String bozo = "Bozo";
                final String[] numba = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
                for (int i = 0; i < 16 - bozo.length(); bozo += numba[(int)Math.floor(Math.random() * numba.length)], ++i) {}
                Minecraft.getMinecraft().session = new Session(bozo, "", "", "mojang");
                this.status = "Logged in as: §e" + this.mc.getSession().getUsername();
                break;
            }
            case 4: {
                this.mc.displayGuiScreen(new GuiAltManager());
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + button.id);
            }
        }
    }
    
    private void loginToAccount(final String email, final String pass) throws AuthenticationException {
        final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        final YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(email);
        auth.setPassword(pass);
        auth.logIn();
        Minecraft.getMinecraft().session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
    }
}
