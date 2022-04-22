package me.injusttice.neutron.impl.modules.impl.visual;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventKey;
import me.injusttice.neutron.api.events.impl.EventRender2D;
import me.injusttice.neutron.utils.font.Fonts;
import me.injusttice.neutron.utils.font.MCFontRenderer;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import java.awt.Color;

public class TabGUI extends Module {

    public boolean expandedCategory = false;
    public int cTab = 0;
    public int mTab = 0;
    MCFontRenderer font;

    public TabGUI() {
        super("TabGUI", 0, Category.VISUAL);
        font = new MCFontRenderer(Fonts.fontFromTTF(new ResourceLocation("Desync/fonts/SF-Pro.ttf"), 18, 0), true, true);
    }

    @EventTarget
    public void onDraw(EventRender2D e) {
        double x = 2.0D;
        double y = 14.0D;
        Category selectedCategory = Category.values()[this.cTab];
        double inc = 11.0D;
        double categoryWitdh = 60.0D;
        double moduleWidth = 70.0D;
        for (Category c : Category.values()) {
            boolean isCategoryHovered = (selectedCategory == c);
            int color = (new Color(-2130706432, true)).getRGB();
            if (isCategoryHovered)
                color = new Color(255,150,200).getRGB();
            Gui.drawRect(x, y, x + categoryWitdh, y + inc, color);
            font.drawStringWithShadow(c.name, x + 3.0D + (isCategoryHovered ? 2 : -1), y + 2.0D, -1);
            y += inc;
        }
        double modCount = 14.0D;
        if (this.expandedCategory)
            for (Module m : NeutronMain.instance.moduleManager.getModulesByCategory(selectedCategory)) {
                boolean isModuleHovered = (m == NeutronMain.instance.moduleManager.getModulesByCategory(selectedCategory).get(this.mTab));
                int modColor = (new Color(-2130706432, true)).getRGB();
                if (isModuleHovered)
                    modColor = new Color(255,150,200).getRGB();
                int textColor = (new Color(13290186)).getRGB();
                if (m.isToggled())
                    textColor = -1;
                Gui.drawRect(x + categoryWitdh + 1.0D, modCount, x + categoryWitdh + moduleWidth + 1.0D, modCount + inc, modColor);
                font.drawStringWithShadow(m.getName(), x + 4.0D + categoryWitdh + (isModuleHovered ? 2 : -1), modCount + 2.0D, textColor);
                modCount += inc;
            }
    }

    @EventTarget
    public void onKey(EventKey e) {
        int key = e.getKey();
        Category selectedCategory = Category.values()[this.cTab];
        if (key == 200)
            if (this.expandedCategory) {
                if (this.mTab <= 0) {
                    this.mTab = NeutronMain.instance.moduleManager.getModulesByCategory(selectedCategory).size() - 1;
                } else {
                    this.mTab--;
                }
            } else if (this.cTab <= 0) {
                this.cTab = (Category.values()).length - 1;
            } else {
                this.cTab--;
            }
        if (key == 208)
            if (this.expandedCategory) {
                if (this.mTab >= NeutronMain.instance.moduleManager.getModulesByCategory(selectedCategory).size() - 1) {
                    this.mTab = 0;
                } else {
                    this.mTab++;
                }
            } else if (this.cTab >= (Category.values()).length - 1) {
                this.cTab = 0;
            } else {
                this.cTab++;
            }
        if (key == 205) {
            if (this.expandedCategory) {
                Module mod = NeutronMain.instance.moduleManager.getModulesByCategory(selectedCategory).get(this.mTab);
                mod.setToggled(!mod.isToggled());
            } else {
                this.mTab = 0;
            }
            this.expandedCategory = true;
        }
        if (key == 203) {
            this.mTab = 0;
            this.expandedCategory = false;
        }
    }
}
