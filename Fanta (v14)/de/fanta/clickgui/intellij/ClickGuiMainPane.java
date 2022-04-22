/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.clickgui.intellij;

import de.fanta.Client;
import de.fanta.clickgui.intellij.ClickGuiScreen;
import de.fanta.clickgui.intellij.GuiTextChanger;
import de.fanta.clickgui.intellij.openables.ClickGuiOpenable;
import de.fanta.clickgui.intellij.openables.ClickGuiOpenableConfig;
import de.fanta.clickgui.intellij.openables.ClickGuiOpenableModule;
import de.fanta.gui.font.ClientFont;
import de.fanta.gui.font.GlyphPageFontRenderer;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.ColorValue;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.setting.settings.Slider;
import de.fanta.utils.ChatUtil;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ClickGuiMainPane
extends Gui {
    private ClickGuiScreen parentScreen;
    private float width;
    private float height;
    private float x;
    private float y;
    public Module openedModule;
    private Setting selectedSetting;
    private File configDir;
    ClickGuiOpenable fantaOpenable;
    ClickGuiOpenable modulesOpenable;
    ClickGuiOpenable configsOpenable;
    public static final int CLASS_BACKGROUND_COLOR = Color.decode("#2B2B2B").getRGB();
    public static final int TOPBAR_BACKGROUND_COLOR = Color.decode("#3C3F41").getRGB();
    public static final int SELECTED_LIGHT_BACKGROUND_COLOR = Color.decode("#4E5254").getRGB();
    public static final int CLASS_BLUE = Color.decode("#4A88C7").getRGB();
    public static final int OUTLINE_BACKGROUND_COLOR = Color.decode("#323232").getRGB();
    public static final int MIDDLE_LINE_COLOR = Color.decode("#555555").getRGB();
    public static final int LINE_DISPLAY_BACKGROUND_COLOR = Color.decode("#313335").getRGB();
    public static final int MENU_FONT_COLOR = Color.decode("#606366").getRGB();
    public static final int MODIFIER_AND_TYPE_FONT_COLOR = Color.decode("#CC7832").getRGB();
    public static final int VARIABLE_NAME_FONT_COLOR = Color.decode("#9675A8").getRGB();
    public static final int VALUE_FONT_COLOR = Color.decode("#6694B7").getRGB();
    public static final int TYPE_AS_OBJECT_FONT_COLOR = Color.decode("#A9B7C6").getRGB();
    public static final GlyphPageFontRenderer MENU_FONT = ClientFont.font(18, "JetBrainsMono-Light", true);
    public boolean modulesOpened;
    public int index;
    public int horizontalIndex;
    public boolean init;
    public List<GuiTextChanger> changers;

    public ClickGuiMainPane(ClickGuiScreen parentScreen) {
        ArrayList<ClickGuiOpenable> configOpenablesOnline;
        ArrayList<ClickGuiOpenable> configOpenablesOffline;
        ArrayList<ClickGuiOpenable> categoryOpenables;
        block13: {
            File[] toCategory2;
            this.openedModule = null;
            this.selectedSetting = null;
            this.configDir = new File(Minecraft.getMinecraft().mcDataDir + "/" + Client.INSTANCE.name + "/" + "configs" + "/");
            this.modulesOpened = false;
            this.index = 0;
            this.horizontalIndex = 0;
            this.init = true;
            this.parentScreen = parentScreen;
            this.width = (float)ClickGuiScreen.width / 1.5f;
            this.height = (float)ClickGuiScreen.height / 1.5f;
            this.x = (float)(ClickGuiScreen.width / 2) - this.width / 2.0f;
            this.y = (float)(ClickGuiScreen.height / 2) - this.height / 2.0f;
            categoryOpenables = new ArrayList<ClickGuiOpenable>();
            Module.Type[] typeArray = Module.Type.values();
            int n = typeArray.length;
            int n2 = 0;
            while (n2 < n) {
                Module.Type category = typeArray[n2];
                toCategory2 = new ArrayList();
                for (Module module : Client.INSTANCE.moduleManager.modules) {
                    if (module.getType() != category) continue;
                    ClickGuiOpenableModule mo = new ClickGuiOpenableModule(0.0f, 0.0f, 17.0f, ClickGuiOpenable.MENU_FONT.getStringWidth(String.valueOf(module.getName().replace(" ", "")) + ".java") + 2, module, this);
                    toCategory2.add(mo);
                }
                categoryOpenables.add(new ClickGuiOpenable(0.0f, 0.0f, 15.0f, ClickGuiOpenable.MENU_FONT.getStringWidth(category.name()), (List<ClickGuiOpenable>)toCategory2, ClickGuiOpenable.Type.FOLDER, category.name()));
                ++n2;
            }
            configOpenablesOffline = new ArrayList<ClickGuiOpenable>();
            if (this.configDir.isDirectory()) {
                toCategory2 = this.configDir.listFiles();
                int n3 = toCategory2.length;
                n = 0;
                while (n < n3) {
                    File f = toCategory2[n];
                    try {
                        if (f.exists() && f.isFile() && FileUtils.readLines((File)f).size() > 0) {
                            String string = f.getName();
                            if (string.endsWith(".txt")) {
                                // empty if block
                            }
                            string = string.substring(0, string.length() - 4);
                            ClickGuiOpenableConfig mo = new ClickGuiOpenableConfig(0.0f, 0.0f, 17.0f, (float)(ClickGuiOpenable.MENU_FONT.getStringWidth(string) + 2), string, this, false);
                            configOpenablesOffline.add(mo);
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    ++n;
                }
            }
            configOpenablesOnline = new ArrayList<ClickGuiOpenable>();
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(() -> {
                ChatUtil.sendChatMessageWithPrefix("Loading...");
                ArrayList<String> configs = new ArrayList<String>();
                try {
                    URLConnection urlConnection = new URL("https://raw.githubusercontent.com/LCAMODZ/Fanta-configs/main/configs.json").openConnection();
                    urlConnection.setConnectTimeout(10000);
                    urlConnection.connect();
                    Object object = null;
                    Object var5_7 = null;
                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));){
                        String text;
                        while ((text = bufferedReader.readLine()) != null) {
                            if (text.contains("404: Not Found")) {
                                ChatUtil.sendChatMessageWithPrefix("An error occurred while loading the configs from Github.");
                                return;
                            }
                            configs.add(text);
                        }
                    }
                    catch (Throwable throwable) {
                        if (object == null) {
                            object = throwable;
                        } else if (object != throwable) {
                            ((Throwable)object).addSuppressed(throwable);
                        }
                        throw object;
                    }
                }
                catch (IOException urlConnection) {
                    // empty catch block
                }
                for (String config : configs) {
                    ClickGuiOpenableConfig mo = new ClickGuiOpenableConfig(0.0f, 0.0f, 17.0f, (float)(ClickGuiOpenable.MENU_FONT.getStringWidth(config) + 2), config, this, true);
                    configOpenablesOnline.add(mo);
                }
            });
            try {
                try {
                    executor.shutdown();
                    executor.awaitTermination(1L, TimeUnit.SECONDS);
                }
                catch (Exception exception) {
                    executor.shutdownNow();
                    break block13;
                }
            }
            catch (Throwable toCategory2) {
                executor.shutdownNow();
                throw toCategory2;
            }
            executor.shutdownNow();
        }
        this.modulesOpenable = new ClickGuiOpenable(0.0f, 0.0f, 15.0f, ClickGuiOpenable.MENU_FONT.getStringWidth("Modules"), categoryOpenables, ClickGuiOpenable.Type.FOLDER, "Modules");
        List<ClickGuiOpenable> configsOpenables = Arrays.asList(new ClickGuiOpenable(0.0f, 0.0f, 15.0f, ClickGuiOpenable.MENU_FONT.getStringWidth("Offline"), configOpenablesOffline, ClickGuiOpenable.Type.FOLDER, "Offline"), new ClickGuiOpenable(0.0f, 0.0f, 15.0f, ClickGuiOpenable.MENU_FONT.getStringWidth("Online"), configOpenablesOnline, ClickGuiOpenable.Type.FOLDER, "Online"));
        this.configsOpenable = new ClickGuiOpenable(0.0f, 0.0f, 15.0f, ClickGuiOpenable.MENU_FONT.getStringWidth("Configs"), configsOpenables, ClickGuiOpenable.Type.FOLDER, "Configs");
        List<ClickGuiOpenable> openablesMain = Arrays.asList(this.modulesOpenable, this.configsOpenable);
        this.fantaOpenable = new ClickGuiOpenable(this.x, this.y, 15.0f, ClickGuiOpenable.MENU_FONT.getStringWidth("Fanta"), openablesMain, ClickGuiOpenable.Type.FOLDER, "Fanta");
        this.changers = new ArrayList<GuiTextChanger>();
    }

    public void draw(float mouseX, float mouseY) {
        Minecraft mc = Minecraft.getMinecraft();
        float TOP_BAR_HEIGHT = this.height / 22.5f;
        float CLASS_EXPLORER_WIDTH = this.width / 4.5f;
        float LINE_DISPLAY_WIDTH = this.width / 19.0f;
        float MIDDLE_LINE_WIDTH = 1.0f;
        float MODULE_DISPLAY_WIDTH = CLASS_EXPLORER_WIDTH / 4.0f * 3.0f;
        float MODIFIER = 14.0f;
        ClickGuiMainPane.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, CLASS_BACKGROUND_COLOR);
        ClickGuiMainPane.drawRect(this.x, this.y, this.x + this.width, this.y + TOP_BAR_HEIGHT, TOPBAR_BACKGROUND_COLOR);
        this.drawHollowRect(this.x, this.y, this.width, TOP_BAR_HEIGHT, 1.0f, OUTLINE_BACKGROUND_COLOR);
        ClickGuiMainPane.drawRect(this.x, this.y + TOP_BAR_HEIGHT, this.x + CLASS_EXPLORER_WIDTH, this.y + this.height, TOPBAR_BACKGROUND_COLOR);
        ClickGuiMainPane.drawRect(this.x + CLASS_EXPLORER_WIDTH, this.y + TOP_BAR_HEIGHT, this.x + CLASS_EXPLORER_WIDTH + LINE_DISPLAY_WIDTH, this.y + this.height, LINE_DISPLAY_BACKGROUND_COLOR);
        ClickGuiMainPane.drawRect(this.x + CLASS_EXPLORER_WIDTH + LINE_DISPLAY_WIDTH, this.y + TOP_BAR_HEIGHT, this.x + CLASS_EXPLORER_WIDTH + LINE_DISPLAY_WIDTH + 1.0f, this.y + this.height, MIDDLE_LINE_COLOR);
        int lines = 0;
        for (GuiTextChanger changer : this.changers) {
            lines += changer.lines;
        }
        int i = 0;
        while (i < lines) {
            MENU_FONT.drawString("" + (i + 1), this.x + CLASS_EXPLORER_WIDTH + 6.0f, this.y + TOP_BAR_HEIGHT + (float)i * 14.0f + 2.0f, MENU_FONT_COLOR);
            ++i;
        }
        if (this.openedModule != null) {
            ClickGuiMainPane.drawRect(this.x + 1.0f, this.y + 1.0f, this.x + MODULE_DISPLAY_WIDTH, this.y + TOP_BAR_HEIGHT - 1.0f, SELECTED_LIGHT_BACKGROUND_COLOR);
            MENU_FONT.drawString(String.valueOf(this.openedModule.getName().replace(" ", "")) + ".java", this.x + 1.0f + 16.0f, this.y + (float)(MENU_FONT.getFontHeight() / 4) - 1.0f, Color.white.getRGB());
            mc.getTextureManager().bindTexture(new ResourceLocation("textures/class.png"));
            ClickGuiMainPane.drawModalRectWithCustomSizedTexture(this.x + 3.0f, this.y + TOP_BAR_HEIGHT / 4.0f - 2.0f, 0.0f, 0.0f, 12.0f, 12.0f, 12.0f, 12.0f);
            ClickGuiMainPane.drawRect(this.x + 1.0f, this.y + TOP_BAR_HEIGHT - 3.0f, this.x + MODULE_DISPLAY_WIDTH, this.y + TOP_BAR_HEIGHT - 1.0f, CLASS_BLUE);
        }
        if (this.init && this.openedModule != null) {
            this.update();
            this.changers.clear();
            i = 0;
            while (i < this.openedModule.getSettings().size()) {
                Setting setting = this.openedModule.getSettings().get(i);
                GuiTextChanger changer = null;
                if (setting.getSetting() instanceof DropdownBox) {
                    changer = new GuiTextChanger("private", "", "String", setting.getName(), setting.getSetting().getValue().toString(), this.x + CLASS_EXPLORER_WIDTH + LINE_DISPLAY_WIDTH + 6.0f, this.y + TOP_BAR_HEIGHT + (float)i * 14.0f, setting);
                } else if (setting.getSetting() instanceof CheckBox) {
                    changer = new GuiTextChanger("private", "", "boolean", setting.getName(), setting.getSetting().getValue().toString(), this.x + CLASS_EXPLORER_WIDTH + LINE_DISPLAY_WIDTH + 6.0f, this.y + TOP_BAR_HEIGHT + (float)i * 14.0f, setting);
                } else if (setting.getSetting() instanceof Slider) {
                    changer = new GuiTextChanger("private", "", "float", setting.getName(), setting.getSetting().getValue().toString(), this.x + CLASS_EXPLORER_WIDTH + LINE_DISPLAY_WIDTH + 6.0f, this.y + TOP_BAR_HEIGHT + (float)i * 14.0f, setting);
                } else if (setting.getSetting() instanceof ColorValue) {
                    changer = new GuiTextChanger("private", "", "int", setting.getName(), setting.getSetting().getValue().toString(), this.x + CLASS_EXPLORER_WIDTH + LINE_DISPLAY_WIDTH + 6.0f, this.y + TOP_BAR_HEIGHT + (float)i * 14.0f, setting);
                }
                this.changers.add(changer);
                ++i;
            }
            this.init = false;
        }
        GL11.glEnable((int)3089);
        this.sccissor((int)this.x + (int)CLASS_EXPLORER_WIDTH + (int)LINE_DISPLAY_WIDTH, (int)this.y + (int)TOP_BAR_HEIGHT, (int)this.width - ((int)CLASS_EXPLORER_WIDTH + (int)LINE_DISPLAY_WIDTH), (int)this.height, 35);
        i = 0;
        while (i < this.changers.size()) {
            GuiTextChanger changer = this.changers.get(i);
            changer.draw(mouseX, mouseY);
            this.update();
            changer.x = this.x + CLASS_EXPLORER_WIDTH + LINE_DISPLAY_WIDTH + 6.0f - (float)this.horizontalIndex;
            changer.y = this.y + TOP_BAR_HEIGHT + (float)i * 14.0f;
            ++i;
        }
        GL11.glDisable((int)3089);
        if (Keyboard.isKeyDown((int)29)) {
            int mouseD = Mouse.getDWheel();
            if (mouseD < 0) {
                this.horizontalIndex += 8;
            }
            if (mouseD > 0) {
                if (this.horizontalIndex > 0) {
                    --this.horizontalIndex;
                }
                if (this.horizontalIndex > 0) {
                    --this.horizontalIndex;
                }
                if (this.horizontalIndex > 0) {
                    --this.horizontalIndex;
                }
                if (this.horizontalIndex > 0) {
                    --this.horizontalIndex;
                }
                if (this.horizontalIndex > 0) {
                    --this.horizontalIndex;
                }
                if (this.horizontalIndex > 0) {
                    --this.horizontalIndex;
                }
                if (this.horizontalIndex > 0) {
                    --this.horizontalIndex;
                }
                if (this.horizontalIndex > 0) {
                    --this.horizontalIndex;
                }
            }
        }
        GL11.glEnable((int)3089);
        this.sccissor((int)this.x, (int)this.y, (int)CLASS_EXPLORER_WIDTH + 1, (int)this.height - (int)TOP_BAR_HEIGHT, 16);
        this.fantaOpenable.draw(mouseX, mouseY);
        GL11.glDisable((int)3089);
        this.fantaOpenable.setX(this.x + 1.0f);
        this.fantaOpenable.setY(this.y + TOP_BAR_HEIGHT + (float)this.index);
        boolean allowScroll = this.fantaOpenable.getHeight() > this.height - TOP_BAR_HEIGHT;
        int mouseD = Mouse.getDWheel();
        if (allowScroll && mouseD < 0 && (float)this.index > this.height - TOP_BAR_HEIGHT - this.fantaOpenable.getHeight()) {
            this.index -= 8;
        }
        if (mouseD > 0) {
            if (this.index < 0) {
                ++this.index;
            }
            if (this.index < 0) {
                ++this.index;
            }
            if (this.index < 0) {
                ++this.index;
            }
            if (this.index < 0) {
                ++this.index;
            }
            if (this.index < 0) {
                ++this.index;
            }
            if (this.index < 0) {
                ++this.index;
            }
            if (this.index < 0) {
                ++this.index;
            }
            if (this.index < 0) {
                ++this.index;
            }
        }
    }

    public void onClose() {
        this.changers.forEach(changer -> changer.save());
    }

    public String getStringKeyEvents(String input) {
        if (Keyboard.isKeyDown((int)28)) {
            input = input.substring(0, input.length() - 1);
        }
        return input;
    }

    public void update() {
        this.width = (float)ClickGuiScreen.width / 1.25f;
        this.height = (float)ClickGuiScreen.height / 1.25f;
        this.x = (float)(ClickGuiScreen.width / 2) - this.width / 2.0f;
        this.y = (float)(ClickGuiScreen.height / 2) - this.height / 2.0f;
    }

    public void sccissor(int x, int y, int width, int height, int fixValue) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glScissor((int)(x * sr.getScaleFactor()), (int)(sr.getScaledHeight() - y - height + fixValue * sr.getScaleFactor()), (int)(width * sr.getScaleFactor()), (int)(height * sr.getScaleFactor()));
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (GuiTextChanger changer : this.changers) {
            changer.keyTyped(typedChar, keyCode);
        }
    }
}

