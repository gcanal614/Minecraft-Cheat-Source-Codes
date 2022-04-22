package stellar.skid.gui.screen.dropdown;

import stellar.skid.StellarWare;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.visual.HUD;
import stellar.skid.utils.RenderUtils;
import stellar.skid.utils.fonts.impl.Fonts;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static stellar.skid.utils.fonts.impl.Fonts.SF.SF_20.SF_20;

public class Tab {

    private final EnumModuleType enumModuleType;
    private float posX;
    private float posY;
    public boolean dragging, opened;
    public List<Module> modules = new CopyOnWriteArrayList<>();

    public Tab(EnumModuleType enumModuleType, float posX, float posY) {
        this.enumModuleType = enumModuleType;
        this.posX = posX;
        this.posY = posY;
        for (AbstractModule abstractModule : StellarWare.getInstance().getModuleManager().getModuleListByCategory(enumModuleType)) {
            modules.add(new Module(abstractModule, this));
        }
    }

    public void drawScreen(int mouseX, int mouseY) {
        HUD hud = StellarWare.getInstance().getModuleManager().getModule(HUD.class);

        String l = "";
        if (enumModuleType.name().equalsIgnoreCase("Combat")) {
            l = "D";
        } else if (enumModuleType.name().equalsIgnoreCase("Movement")) {
            l = "A";
        } else if (enumModuleType.name().equalsIgnoreCase("Player")) {
            l = "B";
        } else if (enumModuleType.name().equalsIgnoreCase("Visuals")) {
            l = "C";
        } else if (enumModuleType.name().equalsIgnoreCase("Exploits")) {
            l = "G";
        } else if (enumModuleType.name().equalsIgnoreCase("Misc")) {
            l = "F";
        }
        RenderUtils.drawRoundedRect(posX - 1, posY, /*posX + */102, /*posY + */ 15, 5, new Color(29, 29, 29, 255).getRGB());
        Fonts.ICONFONT.ICONFONT_24.ICONFONT_24.drawString(l, posX + 88, posY + 5, 0xffffffff);
        SF_20.drawString(enumModuleType.name().charAt(0) + enumModuleType.name().substring(1).toLowerCase(), posX + 4, posY + 4, 0xffffffff, true);
        if (opened) {
            RenderUtils.drawRoundedRect(posX - 1, posY, /*posX + */102, /*posY + */ 18, 5, new Color(29, 29, 29, 255).getRGB());
            SF_20.drawString(enumModuleType.name().charAt(0) + enumModuleType.name().substring(1).toLowerCase(), posX + 4, posY + 4, 0xffffffff, true);
            Fonts.ICONFONT.ICONFONT_24.ICONFONT_24.drawString(l, posX + 88, posY + 5, 0xffffffff);
            RenderUtils.drawRoundedRect(posX - 1, posY + 15, /*posX + */ 102, /*posY + */getTabHeight() + 3, 5, new Color(29, 29, 29, 255).getRGB());
            modules.forEach(module -> module.drawScreen(mouseX, mouseY));
        } else {
            modules.forEach(module -> module.yPerModule = 0);
        }
    }

    public int getTabHeight() {
        int height = 0;
        for (Module module : modules) {
            height += module.yPerModule;
        }
        return height;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= posX && mouseY >= posY && mouseX <= posX + 101 && mouseY <= posY + 15;
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (opened) {
            modules.forEach(module -> module.mouseReleased(mouseX, mouseY, state));
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        modules.forEach(module -> module.keyTyped(typedChar, keyCode));
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (isHovered(mouseX, mouseY) && mouseButton == 1) {
            opened = !opened;
            if (opened) {
                for (Module module : modules) {
                    module.fraction = 0;
                }
            }
        }

        if (opened) {
            modules.forEach(module -> {
                try {
                    module.mouseClicked(mouseX, mouseY, mouseButton);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getPosY() {
        return posY;
    }

    public float getPosX() {
        return posX;
    }

    public List<Module> getModules() {
        return modules;
    }
}
