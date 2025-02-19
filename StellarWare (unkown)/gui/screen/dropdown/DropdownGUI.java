package stellar.skid.gui.screen.dropdown;

import stellar.skid.StellarWare;
import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.visual.ClickGUI;
import stellar.skid.utils.ScaleUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DropdownGUI extends GuiScreen {

    private final List<Tab> tabs = new CopyOnWriteArrayList<>();

    private boolean dragging;
    private int dragX;
    private int dragY;
    private int alpha;

    public DropdownGUI() {

    }

    @Override
    public void initGui() {
        float x = 75;
        alphaBG = 0;

        if (tabs.isEmpty()) {
            for (EnumModuleType value : EnumModuleType.values()) {
                tabs.add(new Tab(value, x, 10));
                x += 110;
            }
        }

        if (!(mc.previousScreen instanceof GuiChest) && mc.previousScreen != this) {
            for (Tab tab : tabs) {
                for (stellar.skid.gui.screen.dropdown.Module module : tab.modules) {
                    module.fraction = 0;

                    for (stellar.skid.gui.screen.dropdown.Setting setting : module.settings) {
                        setting.setPercent(0);
                    }
                }
            }
        }

        if (StellarWare.getInstance().getModuleManager().getModule(ClickGUI.class).getBlur().get()) {
            mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }

        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        if (mc.entityRenderer.theShaderGroup != null) {
            mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            mc.entityRenderer.theShaderGroup = null;
        }
        super.onGuiClosed();
    }

    int alphaBG = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GL11.glPushMatrix();
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int x = ScaleUtils.getScaledMouseCoordinates(mc, mouseX, mouseY)[0];
        int y = ScaleUtils.getScaledMouseCoordinates(mc, mouseX, mouseY)[1];
        ScaleUtils.scale(mc);
        Gui.drawRect(0, 0, scaledResolution.getScaledWidthStatic(mc), scaledResolution.getScaledHeightStatic(mc), new Color(0, 0, 0, 120).getRGB());
        for (Tab tab : tabs) {
            tab.drawScreen(x, y);
            if (tab.dragging) {
                tab.setPosX(dragX + x);
                tab.setPosY(dragY + y);
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
        GL11.glPopMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int x = ScaleUtils.getScaledMouseCoordinates(mc, mouseX, mouseY)[0];
        int y = ScaleUtils.getScaledMouseCoordinates(mc, mouseX, mouseY)[1];
        for (Tab tab : tabs) {
            if (tab.isHovered(x, y) && mouseButton == 0) {
                if (!anyDragging()) {
                    tab.dragging = true;
                    dragX = (int) (tab.getPosX() - x);
                    dragY = (int) (tab.getPosY() - y);
                }
            }
            try {
                tab.mouseClicked(x, y, mouseButton);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private boolean anyDragging() {
        for (Tab tab : tabs) {
            if (tab.dragging) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE && !areAnyHovered()) {
            mc.displayGuiScreen(null);
            if (mc.currentScreen == null) {
                mc.setIngameFocus();
            }
        } else {
            tabs.forEach(tab -> tab.keyTyped(typedChar, keyCode));
        }

    }

    private boolean areAnyHovered() {
        for (Setting setting : Manager.getSettingList()) {
            if (setting.isTextHovered()) {
                System.out.println(setting.getDisplayName());
                return true;
            }
        }
        return false;
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            tabs.forEach(tab -> tab.dragging = false);
        }

        tabs.forEach(tab -> tab.mouseReleased(mouseX, mouseY, state));
        super.mouseReleased(mouseX, mouseY, state);
    }

    public List<Tab> getTabs() {
        return tabs;
    }
}
