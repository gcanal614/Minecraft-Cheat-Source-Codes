// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.module;

import net.minecraft.client.gui.FontRenderer;
import bozoware.visual.font.MinecraftFontRenderer;
import java.util.Comparator;
import net.minecraft.client.Minecraft;
import bozoware.base.BozoWare;
import java.util.function.Predicate;
import bozoware.impl.command.HideCommand;
import java.util.Collection;
import java.util.Arrays;
import bozoware.impl.module.world.Disabler;
import bozoware.impl.module.world.NoGUIClose;
import bozoware.impl.module.world.AutoHypixel;
import bozoware.impl.module.world.Freecam;
import bozoware.impl.module.world.PingSpoofer;
import bozoware.impl.module.world.HackerDetector;
import bozoware.impl.module.player.Jesus;
import bozoware.impl.module.player.AntiVoid;
import bozoware.impl.module.visual.XRay;
import bozoware.impl.module.visual.SessionInfo;
import bozoware.impl.module.visual.TimeChanger;
import bozoware.impl.module.visual.ImageESP;
import bozoware.impl.module.visual.Camera;
import bozoware.impl.module.visual.ESP;
import bozoware.impl.module.visual.HUD;
import bozoware.impl.module.visual.ClickGUI;
import bozoware.impl.module.visual.Chams;
import bozoware.impl.module.visual.TargetHUD;
import bozoware.impl.module.visual.Animations;
import bozoware.impl.module.visual.ChinaHat;
import bozoware.impl.module.movement.Speed;
import bozoware.impl.module.movement.Flight;
import bozoware.impl.module.movement.HighJump;
import bozoware.impl.module.movement.LongJump;
import bozoware.impl.module.movement.IceSpeed;
import bozoware.impl.module.movement.Sprint;
import bozoware.impl.module.movement.Step;
import bozoware.impl.module.player.BedBreaker;
import bozoware.impl.module.player.FastPlace;
import bozoware.impl.module.player.InvManager;
import bozoware.impl.module.combat.AutoGapple;
import bozoware.impl.module.player.Spammer;
import bozoware.impl.module.combat.AutoPot;
import bozoware.impl.module.player.AutoArmor;
import bozoware.impl.module.player.ChestStealer;
import bozoware.impl.module.player.ResetVL;
import bozoware.impl.module.player.Teleport;
import bozoware.impl.module.player.BlockFly;
import bozoware.impl.module.player.Invmove;
import bozoware.impl.module.player.Blink;
import bozoware.impl.module.player.FastUse;
import bozoware.impl.module.player.NoFall;
import bozoware.impl.module.player.ChatBypass;
import bozoware.impl.module.player.NoSlow;
import bozoware.impl.module.player.KillSays;
import bozoware.impl.module.player.Speedmine;
import bozoware.impl.module.player.Timer;
import bozoware.impl.module.combat.Reach;
import bozoware.impl.module.combat.AntiBot;
import bozoware.impl.module.combat.AutoClicker;
import bozoware.impl.module.combat.TargetStrafe;
import bozoware.impl.module.combat.Criticals;
import bozoware.impl.module.combat.FastBow;
import bozoware.impl.module.combat.Aura;
import bozoware.impl.module.combat.Velocity;
import java.util.function.Function;
import java.util.ArrayList;

public class ModuleManager
{
    private final ArrayList<Module> modules;
    public Function<String, Module> getModuleByName;
    public Function<Class<? extends Module>, Module> getModuleByClass;
    
    public ModuleManager() {
        this.modules = new ArrayList<Module>();
        this.getModuleByName = (label -> this.modules.stream().filter(module -> module.getModuleName().equalsIgnoreCase(label)).findFirst().orElse(null));
        this.getModuleByClass = (moduleClass -> this.modules.stream().filter(module -> module.getClass() == moduleClass).findFirst().orElse(null));
        this.registerModules(new Velocity(), new Aura(), new FastBow(), new Criticals(), new TargetStrafe(), new AutoClicker(), new AntiBot(), new Reach(), new Timer(), new Speedmine(), new KillSays(), new NoSlow(), new ChatBypass(), new NoFall(), new FastUse(), new Blink(), new Invmove(), new BlockFly(), new Teleport(), new ResetVL(), new ChestStealer(), new AutoArmor(), new AutoPot(), new Spammer(), new AutoGapple(), new InvManager(), new FastPlace(), new BedBreaker(), new Step(), new Sprint(), new IceSpeed(), new LongJump(), new HighJump(), new Flight(), new Speed(), new ChinaHat(), new Animations(), new TargetHUD(), new Chams(), new ClickGUI(), new HUD(), new ESP(), new Camera(), new ImageESP(), new TimeChanger(), new SessionInfo(), new XRay(), new AntiVoid(), new Jesus(), new HackerDetector(), new PingSpoofer(), new Freecam(), new AutoHypixel(), new NoGUIClose(), new Disabler());
    }
    
    private void registerModules(final Module... module) {
        this.modules.addAll(Arrays.asList(module));
    }
    
    public void onKeyPressed(final int keyPressed) {
        this.modules.forEach(module -> {
            if (module.getModuleBind() == keyPressed) {
                module.toggleModule();
            }
        });
    }
    
    public ArrayList<Module> getModulesByCategory(final ModuleCategory category) {
        final ArrayList<Module> sortedModules = new ArrayList<Module>(this.modules);
        sortedModules.removeIf(module -> !module.getModuleCategory().equals(category));
        return sortedModules;
    }
    
    public ArrayList<Module> getModulesToDraw(final boolean vanillaFont) {
        final ArrayList<Module> sortedModules = new ArrayList<Module>(this.modules);
        sortedModules.removeIf(module -> !module.isModuleToggled());
        sortedModules.removeIf(HideCommand.isHidden::contains);
        final MinecraftFontRenderer fontRenderer = BozoWare.getInstance().getFontManager().mediumFontRenderer;
        final FontRenderer vanillaFontRenderer = Minecraft.getMinecraft().fontRendererObj;
        if (HUD.getInstance().arrayListPosition.getPropertyValue().equals(HUD.arrayListPos.Top)) {
            final FontRenderer fontRenderer2;
            final MinecraftFontRenderer minecraftFontRenderer;
            sortedModules.sort(Comparator.comparingDouble(module -> vanillaFont ? fontRenderer2.getStringWidth(module.getModuleDisplayName()) : minecraftFontRenderer.getStringWidth(module.getModuleDisplayName())).reversed());
        }
        if (HUD.getInstance().arrayListPosition.getPropertyValue().equals(HUD.arrayListPos.Bottom)) {
            final FontRenderer fontRenderer3;
            final MinecraftFontRenderer minecraftFontRenderer2;
            sortedModules.sort(Comparator.comparingDouble(module -> vanillaFont ? fontRenderer3.getStringWidth(module.getModuleDisplayName()) : minecraftFontRenderer2.getStringWidth(module.getModuleDisplayName())).reversed());
        }
        return sortedModules;
    }
    
    public ArrayList<Module> getModules(final boolean vanillaFont) {
        final ArrayList<Module> sortedModules = new ArrayList<Module>(this.modules);
        final MinecraftFontRenderer fontRenderer = BozoWare.getInstance().getFontManager().mediumFontRenderer;
        final FontRenderer vanillaFontRenderer = Minecraft.getMinecraft().fontRendererObj;
        if (HUD.getInstance().arrayListPosition.getPropertyValue().equals(HUD.arrayListPos.Top)) {
            final FontRenderer fontRenderer2;
            final MinecraftFontRenderer minecraftFontRenderer;
            sortedModules.sort(Comparator.comparingDouble(module -> vanillaFont ? fontRenderer2.getStringWidth(module.getModuleDisplayName()) : minecraftFontRenderer.getStringWidth(module.getModuleDisplayName())).reversed());
        }
        if (HUD.getInstance().arrayListPosition.getPropertyValue().equals(HUD.arrayListPos.Bottom)) {
            final FontRenderer fontRenderer3;
            final MinecraftFontRenderer minecraftFontRenderer2;
            sortedModules.sort(Comparator.comparingDouble(module -> vanillaFont ? fontRenderer3.getStringWidth(module.getModuleDisplayName()) : minecraftFontRenderer2.getStringWidth(module.getModuleDisplayName())).reversed());
        }
        return sortedModules;
    }
    
    public ArrayList<Module> getModules() {
        return this.modules;
    }
}
