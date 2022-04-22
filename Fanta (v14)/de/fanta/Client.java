/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  de.fanta.clickgui.mega.ClickGUI
 *  org.lwjgl.opengl.Display
 *  viamcp.ViaMCP
 */
package de.fanta;

import de.fanta.clickgui.defaultgui.ClickGui;
import de.fanta.clickgui.mega.ClickGUI;
import de.fanta.command.CommandManager;
import de.fanta.design.AltsSaver;
import de.fanta.design.fonts.FontManager;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventRender2D;
import de.fanta.module.Module;
import de.fanta.module.ModuleManager;
import de.fanta.module.impl.combat.TestAura;
import de.fanta.utils.BlurHelper;
import de.fanta.utils.BlurHelper2;
import de.fanta.utils.FileUtil;
import de.fanta.utils.IrcChatListener;
import de.fanta.utils.ShaderBackgrundAPI;
import de.fanta.utils.ShaderBackgrundAPI2;
import de.fanta.utils.ShaderBackgrundAPI3;
import de.fanta.utils.UnicodeFontRenderer;
import de.fanta.utils.UnicodeFontRenderer10;
import de.fanta.utils.UnicodeFontRenderer11;
import de.fanta.utils.UnicodeFontRenderer12;
import de.fanta.utils.UnicodeFontRenderer13;
import de.fanta.utils.UnicodeFontRenderer14;
import de.fanta.utils.UnicodeFontRenderer15;
import de.fanta.utils.UnicodeFontRenderer16;
import de.fanta.utils.UnicodeFontRenderer17;
import de.fanta.utils.UnicodeFontRenderer18;
import de.fanta.utils.UnicodeFontRenderer19;
import de.fanta.utils.UnicodeFontRenderer2;
import de.fanta.utils.UnicodeFontRenderer20;
import de.fanta.utils.UnicodeFontRenderer3;
import de.fanta.utils.UnicodeFontRenderer4;
import de.fanta.utils.UnicodeFontRenderer5;
import de.fanta.utils.UnicodeFontRenderer6;
import de.fanta.utils.UnicodeFontRenderer7;
import de.fanta.utils.UnicodeFontRenderer8;
import de.fanta.utils.UnicodeFontRenderer9;
import de.jo.scripting.base.ScriptEngine;
import de.liquiddev.ircclient.api.IrcClient;
import de.liquiddev.ircclient.client.ClientType;
import de.liquiddev.ircclient.client.IrcClientFactory;
import java.nio.ByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.Display;
import viamcp.ViaMCP;

public class Client {
    public static Client INSTANCE;
    public ModuleManager moduleManager;
    public static BlurHelper blurHelper;
    public static BlurHelper2 blurHelper2;
    public UnicodeFontRenderer unicodeBasicFontRenderer;
    public UnicodeFontRenderer arial;
    public UnicodeFontRenderer2 unicodeBasicFontRenderer2;
    public UnicodeFontRenderer2 arial2;
    public UnicodeFontRenderer3 unicodeBasicFontRenderer3;
    public UnicodeFontRenderer3 arial3;
    public UnicodeFontRenderer4 unicodeBasicFontRenderer4;
    public UnicodeFontRenderer4 Roboto;
    public UnicodeFontRenderer4 heroTabGui;
    public UnicodeFontRenderer5 unicodeBasicFontRenderer5;
    public UnicodeFontRenderer5 arial5;
    public UnicodeFontRenderer5 arrial;
    public UnicodeFontRenderer5 arriall;
    public UnicodeFontRenderer6 unicodeBasicFontRenderer6;
    public UnicodeFontRenderer6 pepsi;
    public UnicodeFontRenderer7 unicodeBasicFontRenderer7;
    public UnicodeFontRenderer7 arialbold;
    public UnicodeFontRenderer8 fluxTabGuiFont;
    public UnicodeFontRenderer8 fluxTabGuiFont2;
    public UnicodeFontRenderer9 fluxicon;
    public UnicodeFontRenderer10 Sigma;
    public UnicodeFontRenderer11 vanta;
    public UnicodeFontRenderer12 ViolenceTabGUi;
    public UnicodeFontRenderer13 Skid;
    public UnicodeFontRenderer14 Violence;
    public UnicodeFontRenderer14 Violence2;
    public UnicodeFontRenderer15 Holo;
    public UnicodeFontRenderer16 Jello;
    public UnicodeFontRenderer16 Jello2;
    public UnicodeFontRenderer16 Jello3;
    public UnicodeFontRenderer17 verdana;
    public UnicodeFontRenderer17 verdana2;
    public UnicodeFontRenderer18 unify;
    public UnicodeFontRenderer19 unify2;
    public UnicodeFontRenderer20 ambien;
    public static de.hero.clickgui.ClickGUI clickgui;
    public static ClickGui clickGui;
    public static ClickGUI megaClickGui;
    public final String name = "Fanta";
    public final String version = "14";
    public final String prefix = "[Fanta]";
    public static final String[] authors;
    public CommandManager commandManager;
    public FontManager fontManager;
    public IrcClient ircClient;
    public FileUtil fileUtil;
    public static ShaderBackgrundAPI3 backgrundAPI3;
    public static ShaderBackgrundAPI backgrundAPI;
    public static ShaderBackgrundAPI2 backgrundAPI2;
    public de.fanta.fontrenderer.FontManager ttFontManager;
    public static boolean allowed;
    private double current;
    public ScriptEngine scriptEngine;

    static {
        authors = new String[]{"Command_JO", "LCA_MODZ", "Exeos"};
        allowed = true;
    }

    public void onStart() {
        INSTANCE = this;
        this.fileUtil = new FileUtil();
        FileUtil.loadKeys();
        try {
            new ViaMCP();
            ViaMCP.getInstance().start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Display.setTitle((String)(String.valueOf(this.getName()) + " v" + this.getVersion()));
        this.unicodeBasicFontRenderer = UnicodeFontRenderer.getFontOnPC("Arial", 22, 0, 0.0f, 1.0f);
        this.unicodeBasicFontRenderer2 = UnicodeFontRenderer2.getFontOnPC("Arial", 50, 0, 0.0f, 1.0f);
        this.unicodeBasicFontRenderer3 = UnicodeFontRenderer3.getFontFromAssets("Winter Insight", 22, 0, 0.0f, 1.0f);
        this.ViolenceTabGUi = UnicodeFontRenderer12.getFontFromAssets("ViolenceTab", 22, 0, 0.0f, 1.0f);
        this.unicodeBasicFontRenderer4 = UnicodeFontRenderer4.getFontFromAssets("Roboto-Thin", 22, 0, 0.0f, 1.0f);
        this.heroTabGui = UnicodeFontRenderer4.getFontFromAssets("Roboto-Thin", 45, 0, 0.0f, 1.0f);
        this.unicodeBasicFontRenderer6 = UnicodeFontRenderer6.getFontFromAssets("pepsi-light", 20, 0, 0.0f, 1.0f);
        this.unicodeBasicFontRenderer7 = UnicodeFontRenderer7.getFontFromAssets("arial-bold", 20, 0, 0.0f, 1.0f);
        this.vanta = UnicodeFontRenderer11.getFontFromAssets("VerdanaBold", 15, 0, 0.0f, 1.0f);
        this.unicodeBasicFontRenderer5 = UnicodeFontRenderer5.getFontOnPC("Arial", 18, 0, 0.0f, 1.0f);
        this.arrial = UnicodeFontRenderer5.getFontOnPC("Arial", 20, 0, 0.0f, 1.0f);
        this.arriall = UnicodeFontRenderer5.getFontOnPC("Arial", 15, 0, 0.0f, 1.0f);
        this.arial = UnicodeFontRenderer.getFontOnPC("Arial", 24, 0, 4.0f, 4.0f);
        this.arial2 = UnicodeFontRenderer2.getFontOnPC("Arial", 33, 0, 0.0f, 1.0f);
        this.fluxTabGuiFont = UnicodeFontRenderer8.getFontFromAssets("Baloo", 20, 0, 0.0f, 1.0f);
        this.fluxTabGuiFont2 = UnicodeFontRenderer8.getFontFromAssets("Baloo", 50, 0, 0.0f, 1.0f);
        this.Sigma = UnicodeFontRenderer10.getFontFromAssets("SF-UI-Display-Medium", 10, 0, 0.0f, 1.0f);
        this.fluxicon = UnicodeFontRenderer9.getFontFromAssets("Icon", 20, 0, 0.0f, 1.0f);
        this.Skid = UnicodeFontRenderer13.getFontFromAssets("AldotheApache", 20, 0, 0.0f, 1.0f);
        this.Violence = UnicodeFontRenderer14.getFontFromAssets("V", 25, 0, 0.0f, 1.0f);
        this.Violence2 = UnicodeFontRenderer14.getFontFromAssets("V", 35, 0, 0.0f, 1.0f);
        this.Holo = UnicodeFontRenderer15.getFontFromAssets("Holo", 20, 0, 0.0f, 1.0f);
        this.Jello = UnicodeFontRenderer16.getFontFromAssets("Jello", 18, 0, 0.0f, 1.0f);
        this.Jello2 = UnicodeFontRenderer16.getFontFromAssets("Jello", 41, 0, 0.0f, 1.0f);
        this.Jello3 = UnicodeFontRenderer16.getFontFromAssets("Jello", 16, 0, 0.0f, 1.0f);
        this.verdana = UnicodeFontRenderer17.getFontFromAssets("c", 20, 0, 0.0f, 1.0f);
        this.verdana2 = UnicodeFontRenderer17.getFontFromAssets("verdana", 16, 0, 0.0f, 1.0f);
        this.unify = UnicodeFontRenderer18.getFontFromAssets("Unify", 20, 0, 0.0f, 1.0f);
        this.unify2 = UnicodeFontRenderer19.getFontFromAssets("unify2", 20, 0, 0.0f, 1.0f);
        this.ambien = UnicodeFontRenderer20.getFontFromAssets("ambienold", 25, 0, 0.0f, 1.0f);
        this.ttFontManager = new de.fanta.fontrenderer.FontManager();
        this.moduleManager = new ModuleManager();
        this.fontManager = new FontManager();
        AltsSaver.altsReader();
        blurHelper = new BlurHelper();
        blurHelper.init();
        blurHelper2 = new BlurHelper2();
        blurHelper2.init();
        clickGui = new ClickGui(false, 50.0f, 50.0f);
        clickgui = new de.hero.clickgui.ClickGUI();
        megaClickGui = new ClickGUI();
        this.commandManager = new CommandManager();
        backgrundAPI3 = new ShaderBackgrundAPI3();
        backgrundAPI = new ShaderBackgrundAPI();
        backgrundAPI2 = new ShaderBackgrundAPI2();
        String ign = Minecraft.getMinecraft().session.getUsername();
        this.ircClient = IrcClientFactory.getDefault().createIrcClient(ClientType.FANTA, "6XK7pNhw3wUxJwCL", ign, "14");
        this.ircClient.getApiManager().registerApi(new IrcChatListener());
        this.ircClient.getApiManager().registerCustomDataListener((sender, tag, data) -> {
            ByteBuffer buffer = ByteBuffer.wrap(data);
            int id = buffer.getInt();
            if (tag.toLowerCase().startsWith("report_")) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/report " + tag.split("report_")[1] + " hacking confirm");
            }
        });
        this.scriptEngine = new ScriptEngine();
    }

    public void onStart2() {
        INSTANCE = this;
        this.fileUtil = new FileUtil();
        FileUtil.loadKeys();
        Display.setTitle((String)(String.valueOf(this.getName()) + " v" + this.getVersion()));
        this.unicodeBasicFontRenderer = UnicodeFontRenderer.getFontOnPC("Arial", 22, 0, 0.0f, 1.0f);
        this.arial = UnicodeFontRenderer.getFontOnPC("Arial", 24, 0, 4.0f, 4.0f);
        this.moduleManager = new ModuleManager();
        this.fontManager = new FontManager();
        clickGui = new ClickGui(false, 50.0f, 50.0f);
        this.commandManager = new CommandManager();
    }

    public void onEvent(Event e) {
        try {
            if (Minecraft.getMinecraft().thePlayer == null) {
                return;
            }
            for (Module mod : this.moduleManager.modules) {
                if (!(Client.INSTANCE.moduleManager.getModule("Flight").isState() || Client.INSTANCE.moduleManager.getModule("Speed").isState() || Client.INSTANCE.moduleManager.getModule("Step").isState() || Client.INSTANCE.moduleManager.getModule("Longjump").isState())) {
                    Minecraft.getMinecraft().thePlayer.speedInAir = 0.02f;
                }
                if (!mod.state) continue;
                if (e == null || mod == null) {
                    return;
                }
                mod.onEvent(e);
            }
        }
        catch (Exception mod) {
            // empty catch block
        }
        if (e instanceof EventRender2D) {
            EntityLivingBase entityLivingBase;
            this.moduleManager.getModule(TestAura.class);
            if (TestAura.target == null) {
                entityLivingBase = Minecraft.getMinecraft().thePlayer;
            } else {
                EntityPlayerSP target;
                this.moduleManager.getModule(TestAura.class);
                entityLivingBase = target = TestAura.target;
            }
            if (!(this.moduleManager.getModule(TestAura.class) != null && this.moduleManager.getModule(TestAura.class).isState() || Minecraft.getMinecraft().currentScreen instanceof GuiChat)) {
                this.current = 0.7;
                this.moduleManager.getModule(TestAura.class).dragging = false;
                return;
            }
        }
    }

    public void fixClickGui() {
        Minecraft.getMinecraft().displayGuiScreen(null);
        megaClickGui = new ClickGUI();
        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)megaClickGui);
    }

    public void shutdown() {
        System.out.print("Exeos/LCA_MODZ/CommanderJo is sexy ^^");
    }

    public final String getName() {
        return "Fanta";
    }

    public final String getVersion() {
        return "14";
    }

    public final String[] getAuthors() {
        return authors;
    }

    public static ShaderBackgrundAPI3 getBackgrundAPI3() {
        return backgrundAPI3;
    }

    public static ShaderBackgrundAPI getBackgrundAPI() {
        return backgrundAPI;
    }

    public static ShaderBackgrundAPI2 getBackgrundAPI2() {
        return backgrundAPI2;
    }
}

