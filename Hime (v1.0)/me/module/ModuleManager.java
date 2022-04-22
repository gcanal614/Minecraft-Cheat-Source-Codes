package me.module;

import java.util.ArrayList;
import java.util.List;

import me.module.Module.Category;
import me.module.impl.combat.*;
import me.module.impl.combat.ghost.*;
import me.module.impl.exploit.*;
import me.module.impl.movement.*;
import me.module.impl.other.*;
import me.module.impl.player.*;
import me.module.impl.render.*;
import me.module.impl.targets.*;
import me.module.impl.world.*;


public class ModuleManager {
private ArrayList<Module> modules = new ArrayList();
	
	public ModuleManager() {
		 modules.add(new Sprint());
		 modules.add(new HUD());
		 modules.add(new ClickGui());
		 modules.add(new ChestStealer());
		 modules.add(new Dead());
		 modules.add(new Animals());
		 modules.add(new Players());
		 modules.add(new Teams());
		 modules.add(new Invisibles());
		 modules.add(new Villagers());
		 modules.add(new Mobs());
		 modules.add(new ESP());
		 modules.add(new ChestESP());
		 modules.add(new Chams());
		 modules.add(new NameProtect());
		 modules.add(new NoBob());
		 modules.add(new NoHurtCam());
		 modules.add(new Fullbright());
		 modules.add(new CameraNoClip());
		 modules.add(new BlockAnimation());
		 modules.add(new Nametags());
		 modules.add(new Antibot());
		 modules.add(new AutoPot());
		 modules.add(new Criticals());
		 modules.add(new TPAura());
		 modules.add(new Killaura());
		 modules.add(new Regen());
		 modules.add(new Velocity());
		 modules.add(new AimAssist());
		 modules.add(new AutoClicker());
		 modules.add(new Reach());
		 modules.add(new Hitbox());
		 modules.add(new Scaffold());
		 modules.add(new Ambiance());
		 modules.add(new ItemPhysics());
		 modules.add(new InvMove());
		 modules.add(new Step());
		 modules.add(new Disabler());
		 modules.add(new ScoreBoard());
		 modules.add(new AutoArmor());
		 modules.add(new FastPlace());
		 modules.add(new InvManager());
		 modules.add(new Nofall());
		 modules.add(new NoRotate());
		 modules.add(new NoSlow());
		 modules.add(new FastEat());
		 modules.add(new Speed());
		 modules.add(new LongJump());
		 modules.add(new Fly());
		 modules.add(new AutoPlay());
		 modules.add(new AutoGG());
		 modules.add(new Killsults());
		 modules.add(new HealthNum());
		 modules.add(new AntiVoid());
		 modules.add(new HighJump());
		 modules.add(new AntiAim());
		 modules.add(new CustomGlint());
		 modules.add(new Radar());
		 modules.add(new Phase());
		 modules.add(new ESP2d());
		 modules.add(new Swing());
		 modules.add(new ModuleInfo());
		 modules.add(new Timer());
		 modules.add(new Blink());
		 modules.add(new AutoApple());
		 modules.add(new Cape());
		 modules.add(new AutoRegister());
		 modules.add(new AutoHypixel());
		 modules.add(new StaffAlert());
		 modules.add(new Opacity());
		 modules.add(new Keystrokes());
		 modules.add(new Safewalk());
		 modules.add(new ChatFilter());
		 modules.add(new Notifications());
		 modules.add(new NoFov());
		 modules.add(new Crosshair());
		 modules.add(new Inventory());
		 modules.add(new WTap());
		 
		 getModule("HUD").toggle();
		 getModule("Notifications").toggle();
	}
	
	public List<Module> getModules() {
		return modules;
	}
	
	public Module getModule(String name) {
		for (Module m : modules) {
			if (!m.getName().equalsIgnoreCase(name)) {
				continue;
			}
			return m;
		}
		return null;
	}
	
	public <T extends Module> T getModule(Class<T> clazz) {
        return (T) modules.stream().filter(mod -> mod.getClass() == clazz).findFirst().orElse(null);
    }
	
	
	public List<Module> getModulebyCategory(Category category) {
		List<Module> l = new ArrayList();
		for (Module m : modules) {
			if (m.getCategory() == category) {
				l.add(m);
			}
		}
		return l;
	}
}
