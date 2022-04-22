/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.player;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventTick;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.setting.settings.Slider;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import java.util.Random;

public class Spammer
extends Module {
    private String[] emote = new String[]{"( \u0361\u00b0 \u035c\u0296 \u0361\u00b0)", "\u0ca0_\u0ca0", "(\u256f\u00b0\u25a1\u00b0\uff09\u256f\ufe35 \u253b\u2501\u253b", "\u253b\u2501\u253b \ufe35 \u30fd(\u00b0\u25a1\u00b0\u30fd)", "\u253b\u2501\u253b \ufe35 \uff3c( \u00b0\u25a1\u00b0 )\uff0f \ufe35 \u253b\u2501\u253b", "\u252c\u2500\u252c\u30ce( \u00ba _ \u00ba\u30ce)", "(\uff89\u0ca5\u76ca\u0ca5\uff09\uff89 \u253b\u2501\u253b", "\u0295\u30ce\u2022\u1d25\u2022\u0294\u30ce \ufe35 \u253b\u2501\u253b", "\u253b\u2501\u253b \ufe35\u30fd(`\u0414\u00b4)\uff89\ufe35 \u253b\u2501\u253b", "\u250c\u2229\u2510(\u25e3_\u25e2)\u250c\u2229\u2510", "\u10da(\u0ca0\u76ca\u0ca0)\u10da", "(\u0e07\u2019\u0300-\u2018\u0301)\u0e07", "(\u0ca0_\u0ca0)", "\u256d\u2229\u256e\uff08\ufe36\ufe3f\ufe36\uff09\u256d\u2229\u256e", "( \u3002\u30fb_\u30fb\u3002)\u4eba(\u3002\u30fb_\u30fb\u3002 )", "\u2514(^o^ )\uff38( ^o^)\u2518", "(\u273f\u25e0\u203f\u25e0)", "(\uff61\u25d5\u203f\u25d5\uff61)", "\u30fd\u0f3c\u0e88\u0644\u035c\u0e88\u0f3d\uff89", "(\u3065\uff61\u25d5\u203f\u203f\u25d5\uff61)\u3065", "~(\u02d8\u25be\u02d8~)", "\u30d8( ^o^)\u30ce\uff3c(^_^ )", "(. \u275b \u1d17 \u275b.)", "\uff61^\u203f^\uff61", "( \u0361\u1d54 \u035c\u0296 \u0361\u1d54 )", "\u2609_\u2609", "(\u309c-\u309c)", "(\u30fb_\u30fb\u30fe", "o_O", "(\u00ac_\u00ac)", "( \u0361\u00b0 \u0296\u032f \u0361\u00b0)", "\u256e (. \u275b \u1d17 \u275b.) \u256d", "(\u2022_\u2022) ( \u2022_\u2022)>\u2310\u25a0-\u25a0 (\u2310\u25a0_\u25a0)", "(\u2580\u033f\u0139\u032f\u2580\u033f \u033f)", "( \u00b0 \u035c\u0296\u0361\u00b0)\u256d\u2229\u256e", "( \u0361\u2686 \u035c\u0296 \u0361\u2686)\u256d\u2229\u256e", "(\u0361\u2022 \u035c\u0296 \u0361\u2022)", "\u255a\u2550( \u0361\u00b0 \u035c\u0296 \u0361\u00b0) \u2550\u255d", "( \u0361\u00b0\u0437 \u0361\u00b0)", "\u0361\u00b0 \u035c\u0296 \u0361 \u2013", "(\u0361\u25d4 \u035c\u0296 \u0361\u25d4)"};
    private String[] intave = new String[]{"Nice aura checks, do you sell?", "Richiboy ist stur", "Nichts", "Are you serious?", "Intave entfernt f\u00fcr fast 50 Euro alle Legits auf eurem Server!", "NCP > AAC > Alle anderen > Intave"};
    private String[] hypixel = new String[]{"Watch out for the dog!", "This game is going to end right now", "Watchdog overlooks me", "I might be banned soon, but not from Watchdog.", "I will be banned soon, but not from Watchdog."};
    private String[] cubecraft = new String[]{"SeNtInEl Is AlWaYs WaTcHiNg", "Dont stop me now", "Sentinel overlooks me"};
    private String[] Fanta = new String[]{"Get Good Get Fanta", "Fanta > FDP/RISE", "FDP Better than ALL!?!?", "Fucked By Fanta Client", "Fanta Client By LCA_MODZ", "deletefdp.today"};
    TimeUtil time = new TimeUtil();

    public Spammer() {
        super("Spammer", 0, Module.Type.Misc, Color.magenta);
        this.settings.add(new Setting("SpammerMode", new DropdownBox("Emote", new String[]{"Fanta", "Cubecraft", "Intave", "Hypixel", "Emote"})));
        this.settings.add(new Setting("Delay", new Slider(1.0, 10000.0, 1.0, 100.0)));
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventTick && e.isPre()) {
            Random rnd = new Random();
            if (this.time.hasReached((long)((Slider)this.getSetting((String)"Delay").getSetting()).curValue)) {
                if (((DropdownBox)this.getSetting((String)"SpammerMode").getSetting()).curOption == "Emote") {
                    Spammer.mc.thePlayer.sendChatMessage(this.emote[rnd.nextInt(this.emote.length)]);
                } else if (((DropdownBox)this.getSetting((String)"SpammerMode").getSetting()).curOption == "Intave") {
                    Spammer.mc.thePlayer.sendChatMessage(this.intave[rnd.nextInt(this.intave.length)]);
                } else if (((DropdownBox)this.getSetting((String)"SpammerMode").getSetting()).curOption == "Cubecraft") {
                    Spammer.mc.thePlayer.sendChatMessage(this.cubecraft[rnd.nextInt(this.cubecraft.length)]);
                } else if (((DropdownBox)this.getSetting((String)"SpammerMode").getSetting()).curOption == "Hypixel") {
                    Spammer.mc.thePlayer.sendChatMessage(this.hypixel[rnd.nextInt(this.hypixel.length)]);
                } else if (((DropdownBox)this.getSetting((String)"SpammerMode").getSetting()).curOption == "Fanta") {
                    Spammer.mc.thePlayer.sendChatMessage(this.Fanta[rnd.nextInt(this.Fanta.length)]);
                }
                this.time.reset();
            }
        }
    }
}

