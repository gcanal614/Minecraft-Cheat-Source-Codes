/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.visual;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventRender2D;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.ColorValue;
import de.fanta.setting.settings.Slider;
import de.fanta.utils.ColorUtils;
import de.fanta.utils.Colors;
import java.awt.Color;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class InventoryRenderer
extends Module {
    public static double alpha;
    public static double alpha2;

    public InventoryRenderer() {
        super("InventoryHUD", 0, Module.Type.Visual, ColorUtils.getRandomColor());
        this.settings.add(new Setting("Blur", new CheckBox(false)));
        this.settings.add(new Setting("Lines", new CheckBox(false)));
        this.settings.add(new Setting("Alpha", new Slider(1.0, 255.0, 0.1, 50.0)));
        this.settings.add(new Setting("Alpha2", new Slider(1.0, 255.0, 0.1, 50.0)));
        this.settings.add(new Setting("Color", new ColorValue(Color.RED.getRGB())));
    }

    @Override
    public void onEvent(Event event) {
        alpha = ((Slider)this.getSetting((String)"Alpha").getSetting()).curValue;
        alpha2 = ((Slider)this.getSetting((String)"Alpha2").getSetting()).curValue;
        if (event instanceof EventRender2D) {
            EventRender2D e = (EventRender2D)event;
            int[] rgb = Colors.getRGB(this.getColor2());
            try {
                float x = 7.5f;
                float y = 20.0f;
                if (Client.INSTANCE.moduleManager.getModule("Radar").isState()) {
                    y = 100.0f;
                }
                if (Client.INSTANCE.moduleManager.getModule("Tabgui").isState()) {
                    y = 140.0f;
                }
                if (!Client.INSTANCE.moduleManager.getModule("Tabgui").isState()) {
                    x = 15.0f;
                }
                Partition<ItemStack> itms = Partition.ofSize(Arrays.asList(InventoryRenderer.mc.thePlayer.inventory.mainInventory), 9);
                if (((CheckBox)this.getSetting((String)"Blur").getSetting()).state) {
                    if (!((CheckBox)this.getSetting((String)"Lines").getSetting()).state) {
                        Client.blurHelper.blur2(x - 2.0f, y + 17.0f - 15.0f, x + 153.0f + 2.0f, y + 17.0f, 10.0f);
                        new Gui();
                        Gui.drawRect(x - 2.0f, y + 17.0f - 15.0f, x + 153.0f + 2.0f, y + 15.0f, new Color(20, 20, 20, (int)alpha2).getRGB());
                    } else {
                        Client.blurHelper.blur2(x - 2.0f, y + 17.0f - 15.0f, x + 153.0f + 2.0f, y + 15.0f, 10.0f);
                        new Gui();
                        Gui.drawRect(x - 2.0f, y + 17.0f - 15.0f, x + 153.0f + 2.0f, y + 15.0f, new Color(20, 20, 20, (int)alpha2).getRGB());
                    }
                    if (((CheckBox)this.getSetting((String)"Lines").getSetting()).state) {
                        new Gui();
                        Gui.drawRect(x - 2.0f, y + 17.0f - 2.5f, x + 153.0f + 2.0f, y + 15.0f, Colors.getColor(rgb[0], rgb[1], rgb[2], (int)alpha));
                    }
                    Client.blurHelper.blur2(x - 2.0f, y + 17.0f - 2.0f, x + 153.0f + 2.0f, y + (float)(itms.size() * 17) + 2.0f, 10.0f);
                    new Gui();
                    Gui.drawRect(x - 2.0f, y + 17.0f - 2.0f, x + 153.0f + 2.0f, y + (float)(itms.size() * 17) + 2.0f, new Color(20, 20, 20, (int)alpha2).getRGB());
                } else {
                    new Gui();
                    Gui.drawRect(x - 2.0f, y + 17.0f - 15.0f, x + 153.0f + 2.0f, y + 15.0f, new Color(20, 20, 20).getRGB());
                    new Gui();
                    Gui.drawRect(x - 2.0f, y + 17.0f - 2.5f, x + 153.0f + 2.0f, y + 15.0f, Colors.getColor(rgb[0], rgb[1], rgb[2], (int)alpha));
                    new Gui();
                    Gui.drawRect(x - 2.0f, y + 17.0f - 2.0f, x + 153.0f + 2.0f, y + (float)(itms.size() * 17) + 2.0f, new Color(20, 20, 20).getRGB());
                }
                Client.INSTANCE.unicodeBasicFontRenderer.drawCenteredString("Inventory", (int)x + 76, (int)y + 1, Color.white.getRGB());
                int i = 1;
                while (i < itms.size()) {
                    List row = (List)itms.get(i);
                    if (((CheckBox)this.getSetting((String)"Lines").getSetting()).state && i > 1) {
                        new Gui();
                        Gui.drawRect(x - 2.0f, y + (float)(17 * i) - 0.5f, x + 153.0f + 2.0f, y + (float)(17 * i), Colors.getColor(rgb[0], rgb[1], rgb[2], (int)alpha));
                    }
                    int j = 0;
                    while (j < row.size()) {
                        if (row.get(j) != null) {
                            ItemStack itm = (ItemStack)row.get(j);
                            RenderHelper.enableStandardItemLighting();
                            mc.getRenderItem().renderItemAndEffectIntoGUI(itm, (int)x + j * 17, (int)y + i * 17);
                        }
                        ++j;
                    }
                    ++i;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public int getColor2() {
        try {
            return ((ColorValue)this.getSetting((String)"Color").getSetting()).color;
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }

    static final class Partition<T>
    extends AbstractList<List<T>> {
        private final List<T> list = new ArrayList<T>();
        private final int chunkSize;

        public Partition(List<T> list, int chunkSize) {
            this.list.addAll(list);
            this.chunkSize = chunkSize;
        }

        public static <T> Partition<T> ofSize(List<T> list, int chunkSize) {
            return new Partition<T>(list, chunkSize);
        }

        @Override
        public List<T> get(int index) {
            int start = index * this.chunkSize;
            int end = Math.min(start + this.chunkSize, this.list.size());
            if (start > end) {
                throw new IndexOutOfBoundsException("Index " + index + " is out of the list range <0," + (this.size() - 1) + ">");
            }
            ArrayList<T> ret = new ArrayList<T>();
            ret.addAll(this.list.subList(start, end));
            return ret;
        }

        @Override
        public int size() {
            return (int)Math.ceil((double)this.list.size() / (double)this.chunkSize);
        }
    }
}

