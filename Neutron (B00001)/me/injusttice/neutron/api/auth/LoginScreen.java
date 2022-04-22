package me.injusttice.neutron.api.auth;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.api.auth.util.Encryption;
import me.injusttice.neutron.api.auth.util.HWID;
import me.injusttice.neutron.api.auth.util.Whitelist;
import me.injusttice.neutron.utils.font.Fonts;
import me.injusttice.neutron.utils.font.MCFontRenderer;
import me.injusttice.neutron.ui.UIs.GuiCustomMainMenu;
import me.injusttice.neutron.utils.render.TranslationUtils;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.GuiButton;
import java.io.IOException;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiScreen;

public class LoginScreen extends GuiScreen {

    private GuiScreen previousScreen;
    public static GuiTextField username;
    private long initTime;
    MCFontRenderer fontRenderer;
    public static LoginScreen instance;
    public TranslationUtils translate;
    public static String progression;

    public LoginScreen() {
        this.initTime = System.currentTimeMillis();
        this.fontRenderer = new MCFontRenderer(Fonts.fontFromTTF(new ResourceLocation("Desync/fonts/niggas.ttf"), 18, 0), true, true);
        this.previousScreen = this.previousScreen;
    }

    public static LoginScreen getInstance() {
        return LoginScreen.instance;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            if(Whitelist.WhiteList()){
                NeutronMain.authorized = true;
                NeutronMain.getLoginUser = username.getText();
            } else {
                LoginScreen.progression = "Wrong Username!";
            }
        }
        if (button.id == 69) {
            try {
                GuiScreen.setClipboardString(Encryption.encrypt(HWID.HWID));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void drawScreen(int x2, int y2, float z2) {
        GlStateManager.enableAlpha();
        GlStateManager.disableCull();
        int i = 274;
        int j = LoginScreen.width / 2 - i / 2;
        int k = 30;
        GL11.glBegin(7);
        GL11.glVertex2f(-1.0f, -1.0f);
        GL11.glVertex2f(-1.0f, 1.0f);
        GL11.glVertex2f(1.0f, 1.0f);
        GL11.glVertex2f(1.0f, -1.0f);
        GL11.glEnd();
        GL20.glUseProgram(0);
        this.drawDefaultBackground();
        this.translate.interpolate((float)LoginScreen.width, (float)LoginScreen.height, 2.0);
        double xmod = LoginScreen.height - this.translate.getY();
        GL11.glPushMatrix();
        GlStateManager.translate(0.0, 0.5 * xmod, 0.0);
        Gui.drawRect(LoginScreen.width / 2 - 100, LoginScreen.height / 2 + 55, LoginScreen.width / 2 + 100, LoginScreen.height / 2 - 55, -1895825408);
        fontRenderer.drawCenteredString(LoginScreen.progression, (float)(LoginScreen.width / 2), (float)(LoginScreen.height / 2 - 45), -1);
        this.username.drawTextBox();
        super.drawScreen(x2, y2, z2);
        GL11.glPopMatrix();
        if (NeutronMain.authorized) {
            try {
                this.mc.displayGuiScreen(new GuiCustomMainMenu());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initGui() {
        int var3 = LoginScreen.height / 4 + 24;
        this.translate = new TranslationUtils(0.0f, 0.0f);
        this.buttonList.add(new GuiButton(0, LoginScreen.width / 2 - 73, LoginScreen.height / 2 + 25, 150, 20, "Login"));
        this.username = new GuiTextField(var3, this.mc.fontRendererObj, LoginScreen.width / 2 - 73, LoginScreen.height / 2 - 25, 150, 20);
        this.buttonList.add(new GuiButton(69, 5, 5, 90, 20, "Copy Key"));
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\t') {
            if (!this.username.isFocused()) {
                this.username.setFocused(true);
            }
        }
        if (character == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(int x2, int y2, int button) {
        try {
            super.mouseClicked(x2, y2, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(x2, y2, button);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
    }

    static {
        LoginScreen.instance = new LoginScreen();
        LoginScreen.progression = "Idle...";
    }
}
