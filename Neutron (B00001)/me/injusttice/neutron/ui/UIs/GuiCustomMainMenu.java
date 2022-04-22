package me.injusttice.neutron.ui.UIs;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.ui.UIs.account.Account;
import me.injusttice.neutron.ui.UIs.account.GuiAccountManager;
import me.injusttice.neutron.ui.UIs.account.GuiAddAccount;
import me.injusttice.neutron.utils.font.Fonts;
import me.injusttice.neutron.utils.font.MCFontRenderer;
import me.injusttice.neutron.utils.alt.GuiAltManager;
import me.injusttice.neutron.ui.Buttons.Button;
import me.injusttice.neutron.utils.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuiCustomMainMenu extends GuiMainMenu {

    private ResourceLocation finalTexture = null;
    private ResourceLocation texture1;
    private ResourceLocation texture2;
    private ResourceLocation texture3;
    private ResourceLocation texture4;
    MCFontRenderer font;

    public GuiCustomMainMenu(){
        font = new MCFontRenderer(Fonts.fontFromTTF(new ResourceLocation("Desync/fonts/SF-Pro.ttf"), 18, 0), true, true);
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();
        String strSSP = I18n.format("SINGLEPLAYER");
        String strSMP = I18n.format("MULTIPLAYER");
        String strAccounts = "ALT MANAGER";
        int initHeight = height / 3 + 48;
        int objHeight = 15;
        int objWidth = 102;
        int xMid = width / 2 - objWidth / 2;
        buttonList.add(new Button(0, xMid, initHeight + 30, objWidth, objHeight, strSSP));
        buttonList.add(new Button(1, xMid, initHeight + 18 - 2 + 30, objWidth, objHeight, strSMP));
        buttonList.add(new Button(4, xMid, initHeight + 35 - 3 + 30, objWidth, objHeight, strAccounts));

        try {
            texture1 = new ResourceLocation("textures/1.png");
            texture2 = new ResourceLocation("textures/2.png");
            texture3 = new ResourceLocation("textures/gui/title/background/panorama_0");
            texture4 = new ResourceLocation("textures/4.png");
            List list = new ArrayList();
            list.add(texture1);
            list.add(texture2);
            list.add(texture3);
            list.add(texture4);
            Random random = new Random();
            finalTexture = (ResourceLocation) list.get(random.nextInt(list.size()));
        } catch (Exception var14) {
            var14.printStackTrace();
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 1:
                mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 2:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;
            case 3:
                mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
                break;
            case 4:
                mc.displayGuiScreen(new GuiAltManager());
                break;
            case 5:
                mc.shutdown();
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.renderSkybox(mouseX, mouseY, partialTicks);
        String s = "§7Neutron Sexy Client";
        String s1 = "§7Welcome, " + NeutronMain.getLoginUser;
        font.drawStringWithShadow(s, 2, height - 10, -1);
        font.drawStringWithShadow(s1, width - font.getStringWidth(s1) - 2, height - 10, -1);

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GlStateManager.pushMatrix();

        for (int i = 0; i < buttonList.size(); ++i) {
            GuiButton g = (GuiButton) buttonList.get(i);
            g.drawButton(mc, mouseX, mouseY);
        }

        GlStateManager.popMatrix();
    }
}