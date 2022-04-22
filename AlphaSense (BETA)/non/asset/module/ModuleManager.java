package non.asset.module;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import non.asset.Clarinet;
import non.asset.event.bus.Handler;
import non.asset.event.impl.input.KeyInputEvent;
import non.asset.module.impl.Combat.*;
import non.asset.module.impl.exploit.*;
import non.asset.module.impl.ghost.*;
import non.asset.module.impl.movement.*;
import non.asset.module.impl.other.*;
import non.asset.module.impl.player.*;
import non.asset.module.impl.visuals.*;
import non.asset.utils.value.Value;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class ModuleManager {
    private Map<String, Module> map = new HashMap<>();
    private File dir;

    public void initializeModules() {
        registerMod(HUD.class);
        registerMod(NoHurtCam.class);
        registerMod(FastLadder.class);
        registerMod(Speedmine.class);
        registerMod(Fastbow.class);
        registerMod(FastUse.class);
        registerMod(Reach.class);
        registerMod(AntiBot.class);
        registerMod(Blink.class);
        registerMod(LongJump.class);
        registerMod(Phase.class);
        registerMod(ChinaHat.class);
        registerMod(Sults.class);
        registerMod(Autoclicker.class);
        registerMod(SkeletonESP.class);
        registerMod(Animation.class);
        registerMod(Chams.class);
        registerMod(Disabler.class);
        registerMod(SyncItem.class);
        registerMod(Crasher.class);
        registerMod(AutoArmor.class);
        registerMod(Cape.class);
        registerMod(Criticals.class);
        registerMod(FullBright.class);
        registerMod(ChestESP.class);
        registerMod(TimeChanger.class);
        registerMod(AntiVoid.class);
        registerMod(VanishDetector.class);
        registerMod(Scaffold.class);
        registerMod(AutoSword.class);
        registerMod(AutoPot.class);
        registerMod(Sneak.class);
        registerMod(Step.class);
        registerMod(Jesus.class);
        registerMod(Sprint.class);
        registerMod(NoRotate.class);
        registerMod(InvCleaner.class);
        registerMod(NoSlow.class);
        registerMod(TargetStrafe.class);
        registerMod(MurderMystery.class);
        registerMod(InvManager.class);
        registerMod(ItemPhysics.class);
        registerMod(Freecam.class);
        registerMod(AutoTool.class);
        registerMod(InvWalk.class);
        registerMod(AutoApple.class);
        registerMod(Speed.class);
        registerMod(AutoPot.class);
        registerMod(Fly.class);
        registerMod(AutoMatchJoin.class);
        registerMod(TargetHUD.class);
        registerMod(Aura.class);
        registerMod(ChestStealer.class);
        registerMod(NoFall.class);
        registerMod(Nametags.class);
        registerMod(Velocity.class);
        registerMod(AimAssist.class);
        registerMod(Tracers.class);
        registerMod(AutoGame.class);
        registerMod(WTap.class);
        registerMod(TapBlock.class);
        registerMod(ESP.class);
        registerMod(ClickGui.class);
        registerMod(BedAura.class);
        Clarinet.INSTANCE.getEventBus().bind(this);
    }

    @Handler
    public void onKeyInput(KeyInputEvent event) {
        for (Module module : map.values()) {
            if (module.getKeybind() == event.getKey()) module.setEnabled(!module.isEnabled());
        }
    }

    public void setDir(File dir) {
        this.dir = dir;
    }

    public Map<String, Module> getMap() {
        return map;
    }

    public File getDir() {
        return dir;
    }

    public Map<String, Module> getModuleMap() {
        return map;
    }

    public boolean isModule(final String modulename) {
        for (Module mod : getModuleMap().values()) {
            if (mod.getLabel().equalsIgnoreCase(modulename)) {
                return true;
            }
        }
        return false;
    }

    public Module getModuleClass(final Class<?> clazz) {
        for (Module mod : getModuleMap().values()) {
            if (mod.getClass().equals(clazz)) {
                return mod;
            }
        }
        return null;
    }

    public ArrayList<Module> getModulesInCategory(Module.Category category) {
        final ArrayList<Module> mods = new ArrayList<>();
        for (Module module : map.values()) {
            if (module.getCategory() == category) {
                mods.add(module);
            }
        }
        return mods;
    }

    public float getLongestModInCategory(Module.Category category) {
        final HUD hud = (HUD) Clarinet.INSTANCE.getModuleManager().getModule("hud");
        float width = hud.fontValue.getValue().getStringWidth(getModulesInCategory(category).get(0).getLabel());
        for (Module module : getModulesInCategory(category)) {
            if (hud.fontValue.getValue().getStringWidth(module.getLabel()) > width) {
                width = hud.fontValue.getValue().getStringWidth(module.getLabel());
            }
        }
        return width;
    }

    public Module getModule(String name) {
        return getModuleMap().get(name.toLowerCase());
    }


    private void registerMod(Class<? extends Module> moduleClass) {
        try {
            final Module createdModule = moduleClass.newInstance();
            for (final Field field : createdModule.getClass().getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    final Object obj = field.get(createdModule);

                    if (obj instanceof Value)
                        createdModule.getValues().add((Value) obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            map.put(createdModule.getLabel().toLowerCase(), createdModule);
        } catch (Exception ignored) {
        }
    }

    public void saveModules() {
        File[] files = dir.listFiles();
        if (!dir.exists()) {
            dir.mkdir();
        } else if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        getModuleMap().values().forEach(module -> {
            File file = new File(dir, module.getLabel() + ".json");
            JsonObject node = new JsonObject();
            module.save(node, true);
            if (node.entrySet().isEmpty()) {
                return;
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                return;
            }
            try (Writer writer = new FileWriter(file)) {
                writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(node));
            } catch (IOException e) {
                file.delete();
            }
        });
        files = dir.listFiles();
        if (files == null || files.length == 0) {
            dir.delete();
        }
    }

    public void loadModules() {
        getModuleMap().values().forEach(module -> {
            final File file = new File(dir, module.getLabel() + ".json");
            if (!file.exists()) {
                return;
            }
            try (Reader reader = new FileReader(file)) {
                JsonElement node = new JsonParser().parse(reader);
                if (!node.isJsonObject()) {
                    return;
                }
                module.load(node.getAsJsonObject());
            } catch (IOException e) {
            }
        });
    }
}
