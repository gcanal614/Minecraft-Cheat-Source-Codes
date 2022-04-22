/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module;

import de.fanta.events.listeners.EventRegisterModule;
import de.fanta.module.Module;
import de.fanta.module.impl.combat.ChestAura;
import de.fanta.module.impl.combat.Criticals;
import de.fanta.module.impl.combat.Killaura;
import de.fanta.module.impl.combat.TestAura;
import de.fanta.module.impl.combat.TpAura;
import de.fanta.module.impl.combat.Velocity;
import de.fanta.module.impl.miscellaneous.Disabler;
import de.fanta.module.impl.miscellaneous.FullAntiVerus;
import de.fanta.module.impl.miscellaneous.MemoryCleaner;
import de.fanta.module.impl.miscellaneous.MiddleClickFriends;
import de.fanta.module.impl.miscellaneous.NoRotateSet;
import de.fanta.module.impl.miscellaneous.Sound;
import de.fanta.module.impl.movement.AntiVoid;
import de.fanta.module.impl.movement.Flight;
import de.fanta.module.impl.movement.InvMove;
import de.fanta.module.impl.movement.Jesus;
import de.fanta.module.impl.movement.Longjump;
import de.fanta.module.impl.movement.NoSlowDown;
import de.fanta.module.impl.movement.NoWeb;
import de.fanta.module.impl.movement.Speed;
import de.fanta.module.impl.movement.Sprint;
import de.fanta.module.impl.movement.Step;
import de.fanta.module.impl.movement.TargetStrafe;
import de.fanta.module.impl.movement.Teleport;
import de.fanta.module.impl.player.AutoArmor;
import de.fanta.module.impl.player.AutoTool;
import de.fanta.module.impl.player.ChestStealer;
import de.fanta.module.impl.player.FastUse;
import de.fanta.module.impl.player.InvCleaner;
import de.fanta.module.impl.player.InvManager;
import de.fanta.module.impl.player.Nofall;
import de.fanta.module.impl.player.Regen;
import de.fanta.module.impl.player.Spammer;
import de.fanta.module.impl.player.Spider;
import de.fanta.module.impl.visual.AntiChat;
import de.fanta.module.impl.visual.ArrayList;
import de.fanta.module.impl.visual.BindHUD;
import de.fanta.module.impl.visual.BlockESP;
import de.fanta.module.impl.visual.BlockHit;
import de.fanta.module.impl.visual.ChestESP;
import de.fanta.module.impl.visual.ChinaHat;
import de.fanta.module.impl.visual.Crosshair;
import de.fanta.module.impl.visual.CustomHotbar;
import de.fanta.module.impl.visual.ESP;
import de.fanta.module.impl.visual.FakeBlock;
import de.fanta.module.impl.visual.Glint;
import de.fanta.module.impl.visual.GuiBlur;
import de.fanta.module.impl.visual.HitSlow;
import de.fanta.module.impl.visual.InventoryRenderer;
import de.fanta.module.impl.visual.MotionGraph;
import de.fanta.module.impl.visual.NameTags;
import de.fanta.module.impl.visual.NoBob;
import de.fanta.module.impl.visual.NoFov;
import de.fanta.module.impl.visual.Notification;
import de.fanta.module.impl.visual.Radar;
import de.fanta.module.impl.visual.Tabgui;
import de.fanta.module.impl.visual.Themes;
import de.fanta.module.impl.visual.Tracers2D;
import de.fanta.module.impl.visual.TrailESP;
import de.fanta.module.impl.world.FastBreak;
import de.fanta.module.impl.world.Fucker;
import de.fanta.module.impl.world.Phase;
import de.fanta.module.impl.world.Scaffold;
import de.fanta.module.impl.world.TP;
import de.fanta.module.impl.world.Vclip;
import de.hero.example.GUI;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModuleManager {
    public CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList();

    public ModuleManager() {
        this.addModule(new Killaura());
        this.addModule(new Velocity());
        this.addModule(new ChestAura());
        this.addModule(new Criticals());
        this.addModule(new TpAura());
        this.addModule(new TestAura());
        this.addModule(new MemoryCleaner());
        this.addModule(new NoRotateSet());
        this.addModule(new MiddleClickFriends());
        this.addModule(new FullAntiVerus());
        this.addModule(new Sound());
        this.addModule(new Sprint());
        this.addModule(new InvMove());
        this.addModule(new Jesus());
        this.addModule(new NoWeb());
        this.addModule(new Speed());
        this.addModule(new Flight());
        this.addModule(new Step());
        this.addModule(new NoSlowDown());
        this.addModule(new AntiVoid());
        this.addModule(new Longjump());
        this.addModule(new TargetStrafe());
        this.addModule(new Teleport());
        this.addModule(new InvCleaner());
        this.addModule(new AutoArmor());
        this.addModule(new Spammer());
        this.addModule(new Nofall());
        this.addModule(new Spider());
        this.addModule(new FastUse());
        this.addModule(new Regen());
        this.addModule(new InvManager());
        this.addModule(new AutoTool());
        this.addModule(new ChestStealer());
        this.addModule(new Scaffold());
        this.addModule(new Disabler());
        this.addModule(new Fucker());
        this.addModule(new TP());
        this.addModule(new FastBreak());
        this.addModule(new Vclip());
        this.addModule(new Phase());
        this.addModule(new ArrayList());
        this.addModule(new Themes());
        this.addModule(new Crosshair());
        this.addModule(new ESP());
        this.addModule(new NameTags());
        this.addModule(new NoFov());
        this.addModule(new ChestESP());
        this.addModule(new MotionGraph());
        this.addModule(new BlockHit());
        this.addModule(new FakeBlock());
        this.addModule(new NoBob());
        this.addModule(new Notification());
        this.addModule(new BlockESP());
        this.addModule(new AntiChat());
        this.addModule(new GUI());
        this.addModule(new Tabgui());
        this.addModule(new Tracers2D());
        this.addModule(new CustomHotbar());
        this.addModule(new GuiBlur());
        this.addModule(new ChinaHat());
        this.addModule(new Radar());
        this.addModule(new TrailESP());
        this.addModule(new HitSlow());
        this.addModule(new InventoryRenderer());
        this.addModule(new BindHUD());
        this.addModule(new Glint());
    }

    public void addModule(Module module) {
        this.modules.add(module);
        try {
            module.onEvent(new EventRegisterModule());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public Module getModule(String name) {
        for (Module mod : this.modules) {
            if (!mod.name.equalsIgnoreCase(name)) continue;
            return mod;
        }
        return null;
    }

    public final <T extends Module> T getModule(Class<T> tClass) {
        return (T)((Module)this.modules.stream().filter(module -> module.getClass().equals(tClass)).findFirst().orElse(null));
    }

    public final boolean isToggled(Class tClass) {
        return ((Module)this.getModule(tClass)).state;
    }
}

