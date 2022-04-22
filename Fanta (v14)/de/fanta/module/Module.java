/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.lavache.anime.Animate
 */
package de.fanta.module;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.notifications.Notification;
import de.fanta.notifications.NotificationManager;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.utils.Colours;
import de.fanta.utils.MinecraftUtil;
import de.fanta.utils.SoundUtil;
import fr.lavache.anime.Animate;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public abstract class Module
implements MinecraftUtil {
    public String name;
    public Type type;
    public ArrayList<Setting> settings;
    public boolean state;
    private int keyBind;
    public static int aAni = 0;
    public String lastMod;
    public Animate anim = new Animate();
    public Animate anim2 = new Animate();
    public Color color;
    public int oneTimeColor;
    public int oneTimeColor2;

    public Module(String name, int keyBind, Type type, Color moduleColor) {
        this.type = type;
        this.name = name;
        this.keyBind = keyBind;
        this.color = moduleColor;
        this.settings = new ArrayList();
        this.oneTimeColor = Colours.getColor(new Random().nextInt(191) + 64, new Random().nextInt(191) + 64, new Random().nextInt(64) + 191, 255);
        this.oneTimeColor2 = Colours.getColor(new Random().nextInt(1) + 250, new Random().nextInt(1) + 250, new Random().nextInt(64) + 191, 255);
        this.onModuleRegister(1);
    }

    public Setting getSetting(String name) {
        for (Setting s : this.settings) {
            if (!s.getName().equals(name)) continue;
            return s;
        }
        return null;
    }

    public String getMode() {
        String mode = "";
        for (Setting setting : this.settings) {
            if (!setting.getName().equalsIgnoreCase("Modes") && !setting.getName().equalsIgnoreCase("AnimationMode") && !setting.getName().equalsIgnoreCase("Fonts") && !setting.getName().equalsIgnoreCase("StrafeMode")) continue;
            mode = ((DropdownBox)setting.getSetting()).curOption;
        }
        return mode;
    }

    public boolean getState() {
        return this.state;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void setState() {
        if (this.state) {
            this.state = false;
            if (Client.INSTANCE.moduleManager.getModule("Sound").isState()) {
                SoundUtil.play(SoundUtil.toggleOffSound);
            }
            if (Client.INSTANCE.moduleManager.getModule("Notification").isState()) {
                NotificationManager.addNotificationToQueue(new Notification("Module", "\u00a7c" + this.getName() + " Disabled", 1000L, Notification.NotificationType.INFO));
            }
            this.onDisable();
        } else {
            this.state = true;
            if (Client.INSTANCE.moduleManager.getModule("Sound").isState()) {
                SoundUtil.play(SoundUtil.toggleOnSound);
            }
            if (Client.INSTANCE.moduleManager.getModule("Notification").isState()) {
                NotificationManager.addNotificationToQueue(new Notification("Module", "\u00a7a" + this.getName() + " Enabled", 1000L, Notification.NotificationType.INFO));
            }
            this.onEnable();
        }
    }

    public abstract void onEvent(Event var1);

    public void onModuleRegister(int val) {
    }

    public int getKeyBind() {
        return this.keyBind;
    }

    public void setKeyBind(int keyBind) {
        this.keyBind = keyBind;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ArrayList<Setting> getSettings() {
        return this.settings;
    }

    public boolean isState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void toggle() {
        this.state = !this.state;
    }

    public String getLastMod() {
        if (this.state) {
            this.lastMod = this.name;
        }
        return this.lastMod;
    }

    public Color getColor() {
        return this.color;
    }

    public static enum Type {
        Combat(Color.decode("#E74C3C").getRGB(), 'j', "a"),
        Movement(Color.decode("#2ECC71").getRGB(), 'b', "b"),
        Visual(Color.decode("#3700CE").getRGB(), 'g', "g"),
        Player(Color.decode("#8E44AD").getRGB(), 'k', "c"),
        Misc(Color.decode("#F39C12").getRGB(), '?', "f"),
        World(Color.decode("#3498DB").getRGB(), 'h', "f");

        private int color;
        private char icon;
        private final String iconPenis;

        private Type(int color, char icon, String iconPenis) {
            this.color = color;
            this.icon = icon;
            this.iconPenis = iconPenis;
        }

        public int getColor() {
            return this.color;
        }

        public char getIcon() {
            return this.icon;
        }

        public final String getIconPenis() {
            return this.iconPenis;
        }
    }
}

