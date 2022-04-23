/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module;

import club.tifality.Tifality;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.impl.combat.AntiBot;
import club.tifality.module.impl.combat.AntiVelocity;
import club.tifality.module.impl.combat.AutoClicker;
import club.tifality.module.impl.combat.AutoPot;
import club.tifality.module.impl.combat.Criticals;
import club.tifality.module.impl.combat.Heal;
import club.tifality.module.impl.combat.KillAura;
import club.tifality.module.impl.combat.Reach;
import club.tifality.module.impl.movement.AntiFall;
import club.tifality.module.impl.movement.Blink;
import club.tifality.module.impl.movement.DisablerHypixelInifniteAuraWatchDogFlyMovementOMG;
import club.tifality.module.impl.movement.Flight;
import club.tifality.module.impl.movement.NoSlowdown;
import club.tifality.module.impl.movement.Phase;
import club.tifality.module.impl.movement.Scaffold;
import club.tifality.module.impl.movement.Speed;
import club.tifality.module.impl.movement.Sprint;
import club.tifality.module.impl.movement.Step;
import club.tifality.module.impl.movement.TargetStrafe;
import club.tifality.module.impl.other.Animations;
import club.tifality.module.impl.other.AntiAim;
import club.tifality.module.impl.other.AutoHypixel;
import club.tifality.module.impl.other.ChatBypass;
import club.tifality.module.impl.other.GameSpeed;
import club.tifality.module.impl.other.HackerDetect;
import club.tifality.module.impl.other.LagBack;
import club.tifality.module.impl.other.MCF;
import club.tifality.module.impl.other.MemoryFix;
import club.tifality.module.impl.other.PingSpoof;
import club.tifality.module.impl.other.SilentView;
import club.tifality.module.impl.player.AntiObby;
import club.tifality.module.impl.player.AutoArmor;
import club.tifality.module.impl.player.AutoTool;
import club.tifality.module.impl.player.ChestStealer;
import club.tifality.module.impl.player.FastUse;
import club.tifality.module.impl.player.InventoryCleaner;
import club.tifality.module.impl.player.InventoryMove;
import club.tifality.module.impl.player.NoFall;
import club.tifality.module.impl.player.NoRotate;
import club.tifality.module.impl.player.SpeedMine;
import club.tifality.module.impl.player.StreamerMode;
import club.tifality.module.impl.render.ArmorHUD;
import club.tifality.module.impl.render.Brightness;
import club.tifality.module.impl.render.Cape;
import club.tifality.module.impl.render.Chams;
import club.tifality.module.impl.render.ChestESP;
import club.tifality.module.impl.render.ChinaHat;
import club.tifality.module.impl.render.ClickGUI;
import club.tifality.module.impl.render.Crosshair;
import club.tifality.module.impl.render.Debug;
import club.tifality.module.impl.render.ESP;
import club.tifality.module.impl.render.Health;
import club.tifality.module.impl.render.Hitmarkers;
import club.tifality.module.impl.render.Indicators;
import club.tifality.module.impl.render.NoHurtCamera;
import club.tifality.module.impl.render.Radar;
import club.tifality.module.impl.render.Skeletal;
import club.tifality.module.impl.render.TargetHUD;
import club.tifality.module.impl.render.TimeChanger;
import club.tifality.module.impl.render.hud.Hud;
import com.google.common.collect.ImmutableClassToInstanceMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ModuleManager {
    private final ImmutableClassToInstanceMap<Module> instanceMap = this.putInInstanceMap(new Sprint(), new Speed(), new NoSlowdown(), new Flight(), new Step(), new AntiFall(), new Scaffold(), new Phase(), new DisablerHypixelInifniteAuraWatchDogFlyMovementOMG(), new KillAura(), new AntiVelocity(), new Criticals(), new AutoPot(), new TargetStrafe(), new AntiBot(), new Heal(), new Hud(), new Cape(), new ESP(), new SilentView(), new Indicators(), new Hitmarkers(), new Crosshair(), new TargetHUD(), new Brightness(), new ArmorHUD(), new Debug(), new Radar(), new ClickGUI(), new ChestESP(), new Chams(), new Health(), new Animations(), new NoHurtCamera(), new ChinaHat(), new Skeletal(), new AntiAim(), new GameSpeed(), new ChatBypass(), new InventoryMove(), new PingSpoof(), new MemoryFix(), new TimeChanger(), new ChestStealer(), new AutoHypixel(), new HackerDetect(), new LagBack(), new MCF(), new StreamerMode(), new AntiObby(), new AutoArmor(), new SpeedMine(), new FastUse(), new NoRotate(), new AutoTool(), new InventoryCleaner(), new NoFall(), new Blink(), new Reach(), new AutoClicker());

    public ModuleManager() {
        this.getModules().forEach(Module::reflectProperties);
        this.getModules().forEach(Module::resetPropertyValues);
        Tifality.getInstance().getEventBus().subscribe(this);
    }

    public void postInit() {
        this.getModules().forEach(Module::resetPropertyValues);
    }

    private <T extends Module> ImmutableClassToInstanceMap<Module> putInInstanceMap(Module ... modules) {
        ImmutableClassToInstanceMap.Builder moduleBuilder = ImmutableClassToInstanceMap.builder();
        Arrays.stream(modules).forEach(module -> moduleBuilder.put(module.getClass(), module));
        return moduleBuilder.build();
    }

    public Collection<Module> getModules() {
        return this.instanceMap.values();
    }

    public <T extends Module> T getModule(Class<T> moduleClass) {
        return (T)((Module)this.instanceMap.getInstance(moduleClass));
    }

    public Optional<Module> getModule(String label) {
        return this.getModules().stream().filter(module -> module.getLabel().replace(" ", "").equalsIgnoreCase(label)).findFirst();
    }

    public static <T extends Module> T getInstance(Class<T> clazz) {
        return Tifality.getInstance().getModuleManager().getModule(clazz);
    }

    public List<Module> getModulesForCategory(ModuleCategory category) {
        return this.getModules().stream().filter(module -> module.getCategory() == category).collect(Collectors.toList());
    }
}

