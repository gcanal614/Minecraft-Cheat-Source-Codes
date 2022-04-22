package com.zerosense.Commands;

import com.zerosense.Commands.Manager.Command;
import com.zerosense.ZeroSense;
import com.zerosense.mods.Module;
import com.zerosense.mods.ModuleManager;

public class Toggle extends Command {
    public Toggle() {
        super("Toggle", "Toggles a module", "toggle <name>", "t");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if(args.length > 0){
            String moduleName = args[0];

            boolean foundModule = false;

            for (Module module : ModuleManager.modules){
                if(module.name.equalsIgnoreCase(moduleName)){
                        module.toggle();

                    ZeroSense.addChatMessage((module.isEnabled() ? "Enabled" : "Disabled") + " " + module.name);

                    foundModule = true;
                    break;
                }
            }

            if(!foundModule){
                ZeroSense.addChatMessage("Could not find module.");
            }
        }
    }
}
