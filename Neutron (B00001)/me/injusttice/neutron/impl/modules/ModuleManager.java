package me.injusttice.neutron.impl.modules;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.api.events.impl.EventNigger;
import me.injusttice.neutron.impl.modules.impl.combat.*;
import me.injusttice.neutron.impl.modules.impl.exploit.*;
import me.injusttice.neutron.impl.modules.impl.ghost.AutoClicker;
import me.injusttice.neutron.impl.modules.impl.ghost.Reach;
import me.injusttice.neutron.api.settings.Setting;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.impl.modules.impl.movement.*;
import me.injusttice.neutron.impl.modules.impl.other.*;
import me.injusttice.neutron.impl.modules.impl.player.*;
import me.injusttice.neutron.impl.modules.impl.visual.*;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private static ArrayList<Module> modules = new ArrayList<Module>();

    public ModuleManager(){
        // COMBAT
        modules.add(new TargetStrafe());
        modules.add(new KillAura());
        modules.add(new Velocity());
        modules.add(new Criticals());
        modules.add(new Criticals2());
        modules.add(new AutoArmor());
        modules.add(new AutoHeal());
        modules.add(new AutoSoup());
        modules.add(new AutoPot());
        modules.add(new FastBow());
        modules.add(new AntiBot());
        modules.add(new TPAura());
        modules.add(new Regen());

        // MOVEMENT
        modules.add(new CustomSpeed());
        modules.add(new HypixelFly());
        modules.add(new CustomFly());
        modules.add(new Scaffold());
        modules.add(new HighJump());
        modules.add(new LongJump());
        modules.add(new Flight());
        modules.add(new NoSlow());
        modules.add(new Speed());
        modules.add(new Step());
        modules.add(new Sprint());

        // PLAYER
        modules.add(new InventoryManager());
        modules.add(new AutoRespawn());
        modules.add(new NoRotate());
        modules.add(new AntiVoid());
        modules.add(new FastMine());
        modules.add(new InvMove());
        modules.add(new AutoTool());
        modules.add(new Freecam());
        modules.add(new Stealer());
        modules.add(new Breaker());
        modules.add(new NoFall());
        modules.add(new Jesus());
        modules.add(new Annoy());
        modules.add(new TimerNigger());

        // EXPLOIT
        modules.add(new AntiTabComplete());
        modules.add(new StrafeDisabler());
        modules.add(new LunarSpoofer());
        modules.add(new PingSpoof());
        modules.add(new Disabler());
        modules.add(new FastEat());
        modules.add(new Plugins());
        modules.add(new Phase());
        modules.add(new Blink());

        // OTHER
        modules.add(new ScoreboardMover());
        modules.add(new NameProtect());
        modules.add(new AntiVanish());
        modules.add(new ChatFilter());
        modules.add(new FastPlace());
        modules.add(new Killsults());
        modules.add(new Streamer());
        modules.add(new AutoHypixel());

        // GHOST
        modules.add(new AutoClicker());
        modules.add(new Reach());

        // VISUAL
        modules.add(new BlockOverlay());
        modules.add(new Animations());
        modules.add(new FullBright());
        modules.add(new ChinaHat());
        modules.add(new NoHurtCam());
        modules.add(new Crosshair());
        modules.add(new NameTags());
        modules.add(new ClickGUI());
        modules.add(new WorldTime());
        modules.add(new NoRender());
        modules.add(new ChestESP());
        modules.add(new TabGUI());
        modules.add(new Camera());
        modules.add(new Chams());
        modules.add(new Radar());
        modules.add(new HUD());
    }

    public Module[] getModulesInCategory(final Category moduleCategory) {
        return modules.stream().filter(module -> module.getCategory() == moduleCategory).toArray(Module[] :: new);
    }

    public void onUpdate() {
        for(Module m : modules) {
            m.onUpdate();
        }
    }

    public void onEvent(EventNigger e) {
        for (Module m : NeutronMain.instance.moduleManager.getModules()) {
            if (!m.toggled)
                continue;

            m.onEvent(e);
        }
    }

    public ModeSet getModeSetByName(String moduleName, String settingName) {
        for (Module m : getModules()) {
            if (m.name.equalsIgnoreCase(moduleName))
                for (Setting s : m.settings) {
                    if (s.name.equalsIgnoreCase(settingName))
                        return (ModeSet)s;
                }
        }
        return null;
    }

    public Module getMod(String name) {
        return this.modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static <T extends Module> T getModule(Class<T> clazz) {
        return (T) modules.stream().filter(mod -> mod.getClass() == clazz).findFirst().orElse(null);
    }

    public List<Module> getModulesByCategory(Category c) {
        List<Module> modules = new ArrayList<>();
        for (Module m : NeutronMain.instance.moduleManager.modules) {
            if (m.category == c)
                modules.add(m);
        }
        return modules;
    }

    public ArrayList<Module> getModules() {
        return this.modules;
    }

    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public final Module getModuleOrNull(Class clazz) {
        List modules = this.modules;
        int i = 0;

        for(int modulesSize = modules.size(); i < modulesSize; ++i) {
            Module module = (Module)modules.get(i);
            if (module.getClass() == clazz) {
                return module;
            }
        }

        return null;
    }
}
