/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService
 *  com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication
 *  io.netty.util.internal.ThreadLocalRandom
 *  org.json.JSONObject
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package de.fanta.design;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import de.fanta.Client;
import de.fanta.design.AltTypes;
import de.fanta.design.AltsSaver;
import de.fanta.msauth.MicrosoftAuthentication;
import io.netty.util.internal.ThreadLocalRandom;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.Session;
import org.json.JSONObject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class AltManager
extends GuiScreen {
    private static final String ALTENING_AUTH_SERVER = "http://authserver.thealtening.com/";
    private static final String ALTENING_SESSION_SERVER = "http://sessionserver.thealtening.com/";
    public GuiScreen parentScreen;
    private final String status = "";
    public GuiTextField email;
    public GuiTextField password;
    public static String emailName = "";
    public static String passwordName = "";
    public static int i = 0;

    public AltManager(GuiScreen event) {
        this.parentScreen = event;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.add(new GuiButton(10, 10, height / 4 + 120 + 130, 68, 20, "Microsoft"));
        this.buttonList.add(new GuiButton(11, 10, height / 4 + 144 + 130, 68, 20, "Token"));
        this.buttonList.add(new GuiButton(9, 10, height / 4 + 96 + 130, 68, 20, "Add"));
        this.buttonList.add(new GuiButton(3, 10, height / 4 + 96 + 96, 68, 20, "Remove"));
        this.buttonList.add(new GuiButton(1, 10, height / 4 + 96 + 50, 68, 20, "Login"));
        this.buttonList.add(new GuiButton(5, 10, height / 4 + 96 + 73, 68, 20, "Back"));
        this.buttonList.add(new GuiButton(2, 10, height / 4 + 96 + 15, 68, 20, "Clipboard"));
        this.email = new GuiTextField(3, this.fontRendererObj, 10, 50, 200, 20);
        this.password = new GuiTextField(4, this.fontRendererObj, 10, 100, 200, 20);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.email.textboxKeyTyped(typedChar, keyCode);
        this.password.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    public void updateScreen() {
        this.email.updateCursorCounter();
        this.password.updateCursorCounter();
    }

    @Override
    public void mouseClicked(int x, int y, int m) {
        this.email.mouseClicked(x, y, m);
        this.password.mouseClicked(x, y, m);
        try {
            super.mouseClicked(x, y, m);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution s = new ScaledResolution(this.mc);
        this.drawDefaultBackground();
        int i1 = 0;
        Client.getBackgrundAPI3().renderShader();
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Email:", 10.0f, 36.0f, -1);
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Password:", 10.0f, 86.0f, -1);
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("\u00a73Logged in with \u00a7a" + this.mc.session.getUsername(), width / 2, 20.0f, -1);
        this.email.drawTextBox();
        this.password.drawTextBox();
        int boxY = 26;
        int boxX = 50;
        for (AltTypes slot : AltsSaver.AltTypeList) {
            boolean isHovered;
            if (slot == null) continue;
            Gui.drawRect(300.0f, (int)(36.4 + (double)i1), width - 50 - 50, 26 + i1 + 40, new Color(30, 30, 30, 120).getRGB());
            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(slot.EmailName, width / 3, 26 + i1 + 20, -4260079);
            boolean bl = isHovered = mouseX <= width - 50 - 50 && mouseX >= 300 && mouseY >= (int)(36.4 + (double)i1) && mouseY <= 26 + i1 + 40;
            if (isHovered) {
                Gui.drawRect(300.0f, (int)(36.4 + (double)i1), width - 50 - 50, 26 + i1 + 40, 1304477888);
                emailName = slot.getEmail().trim();
                passwordName = slot.getPassword().trim();
                if (this.mc.currentScreen == this && Mouse.isButtonDown((int)0)) {
                    this.login(emailName, passwordName);
                }
            }
            i1 += 40;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean login1(String email, String password) {
        try {
            YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            YggdrasilUserAuthentication auth = new YggdrasilUserAuthentication(service, Agent.MINECRAFT);
            auth.setUsername(email);
            auth.setPassword(password);
            if (!auth.canLogIn()) return false;
            try {
                auth.logIn();
                Minecraft.getMinecraft().session = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
                Client.INSTANCE.ircClient.setIngameName(Minecraft.getMinecraft().session.getUsername());
                return true;
            }
            catch (AuthenticationException authenticationException) {
                return false;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public void login(String Email, String password) {
        YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)authenticationService.createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(Email);
        authentication.setPassword(password);
        try {
            authentication.logIn();
            Minecraft.getMinecraft().session = new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(), authentication.getAuthenticatedToken(), "mojang");
            Client.INSTANCE.ircClient.setIngameName(Minecraft.getMinecraft().session.getUsername());
        }
        catch (Exception e) {
            Minecraft.getMinecraft().session = new Session(Email, "", "", "mojang");
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 11) {
            AltManager.loginToken(AltManager.getClipboardString());
        }
        if (button.id == 10) {
            MicrosoftAuthentication.getInstance().loginWithPopUpWindow();
            Client.INSTANCE.ircClient.setIngameName(Minecraft.getMinecraft().session.getUsername());
        }
        if (button.id == 9) {
            if (!this.email.getText().isEmpty() && !this.password.getText().isEmpty()) {
                emailName = this.email.getText();
                passwordName = this.password.getText();
                AltsSaver.AltTypeList.add(new AltTypes(emailName, passwordName));
            }
            AltsSaver.saveAltsToFile();
        }
        int cfr_ignored_0 = button.id;
        if (button.id == 1) {
            if (!this.email.getText().isEmpty() && !this.password.getText().isEmpty()) {
                this.login(this.email.getText().trim(), this.password.getText().trim());
                this.email.setText("");
                this.password.setText("");
            } else if (this.email.getText().isEmpty() && this.password.getText().isEmpty()) {
                this.password.setText("a");
                StringBuilder randomName = new StringBuilder();
                String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789_";
                int random = ThreadLocalRandom.current().nextInt(8, 16);
                int i = 0;
                while (i < random) {
                    randomName.append(alphabet.charAt(ThreadLocalRandom.current().nextInt(1, alphabet.length())));
                    ++i;
                }
                this.email.setText("" + randomName);
            }
        }
        if (button.id == 2) {
            String Copy = AltManager.getClipboardString();
            String[] WomboCombo = Copy.split(":");
            String Email1 = WomboCombo[0];
            String Passwort = WomboCombo[1];
            this.email.writeText(Email1);
            this.password.writeText(Passwort);
        }
        if (button.id == 26) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        if (button.id == 5) {
            this.mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    public static boolean loginToken(String token) {
        URL url = null;
        try {
            url = new URL("https://api.minecraftservices.com/minecraft/profile/");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)url.openConnection();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setRequestProperty("Content-Type", "application/json");
        try {
            conn.setRequestMethod("GET");
        }
        catch (ProtocolException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder response = new StringBuilder();
        while (true) {
            String output;
            try {
                output = in.readLine();
                if (output == null) {
                    break;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            response.append(output);
        }
        String jsonString = response.toString();
        JSONObject obj = new JSONObject(jsonString);
        String username = obj.getString("name");
        String uuid = obj.getString("id");
        Minecraft.getMinecraft().session = new Session(username, uuid, token, "null");
        Client.INSTANCE.ircClient.setIngameName(Minecraft.getMinecraft().session.getUsername());
        try {
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}

