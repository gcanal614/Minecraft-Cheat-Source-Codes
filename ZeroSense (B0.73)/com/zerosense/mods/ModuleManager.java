package com.zerosense.mods;

import com.zerosense.Commands.Manager.CommandManager;
import com.zerosense.Events.Event;
import com.zerosense.Events.impl.EventChat;
import com.zerosense.mods.impl.COMBAT.Killaura;
import com.zerosense.mods.impl.EXPLOIT.Disabler;
import com.zerosense.mods.impl.EXPLOIT.NoRotateSet;
import com.zerosense.mods.impl.MOVEMENT.*;
import com.zerosense.mods.impl.PLAYER.NoFall;
import com.zerosense.mods.impl.PLAYER.Scaffold;
import com.zerosense.mods.impl.RENDER.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModuleManager {

    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();


    public static CommandManager commandManager = new CommandManager();

    public static Module getModuleByName(String name) {
        for (Module module : modules) {
            if (module.getDisplayName().equalsIgnoreCase(name))
                return module;
        }
        return null;
    }




    public static void onEvent(Event e) {
        if (e instanceof EventChat)
            commandManager.handleChat((EventChat)e);
        for (Module m : modules) {
            if (!m.toggled)
                continue;
            m.onEvent(e);
        }
    }

    public static Scaffold scaff;

    public static void startupModules(){


        modules.add(flight = new Flight());
        modules.add(hud = new HUD());
        modules.add(new NoRotateSet());
        modules.add(new SessionInf());
        modules.add(speed = new Speed());
       //modules.add(new TabGUI());
        modules.add(new ClickGui());
        //modules.add(new ClickGui()); idk why have 2 clickgui
        modules.add(new Killaura());
        modules.add(new Disabler());
        modules.add(new Sprint());
        modules.add(scaff = new Scaffold());
        modules.add(new Step());
        //modules.add(new Longjump());
        modules.add(new NoFall());
        //modules.add(new Scaffold());
        //modules.add(new FastPlace());
        modules.add(new ESP());
        modules.add(noslow = new  NoSlow());
        modules.add(new SafeWalk());
       // modules.add(new AntiBot());
        modules.add(new Rotations());

    }

    public static List<Module> getModulesByCategory(Module.Category c) {
        List<Module> modules = new ArrayList<>();
        for (Module m : ModuleManager.modules) {
            if (m.category == c)
                modules.add(m);
        }
        return modules;
    }

    public static void keyPress(int key){
        for(Module m :modules){
            if(m.getKey() == key){
                m.toggle();
            }
        }
    }

    public static Flight flight;
    public static HUD hud;
    public static Speed speed;
    public static NoSlow noslow;
}
