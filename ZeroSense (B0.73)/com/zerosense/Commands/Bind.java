package com.zerosense.Commands;


import com.zerosense.Commands.Manager.Command;
import com.zerosense.mods.Module;
import com.zerosense.mods.ModuleManager;

public class Bind extends Command {

    public Bind(){
        super("Bind","Binds a module", "bind <name> <key> | clear", "b");
    }

    @Override
    public void onCommand(String[] args, String commad){
        if (args.length == 2){
            String moduleName = args[0];
            String keyName = args[1];

            boolean foundModule = false;

            for (Module module : ModuleManager.modules){
                
            }
        }
    }
}
