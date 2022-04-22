package me.command.commands;

import me.Hime;
import me.command.Command;
import me.module.Module;


public class Rename extends Command{

    @Override
    public String getName() {
        return "Rename";
    }

    @Override
    public String getDescription() {
        return "Allows user to customize the name of a module.";
    }

    @Override
    public String getSyntax() {
        return ".rename [MODULE] [CUSTOMNAME] | .rename [MODULE] reset | .rename clear";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
    	 String oldName;
        if(!(args[1].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("clear"))){
            Hime.instance.moduleManager.getModule(args[0]).setName(args[1]);
        }else if(args[1].equalsIgnoreCase("reset")){
            Hime.instance.moduleManager.getModule(args[0]).setName(Hime.instance.moduleManager.getModule(args[0]).getName());
            // i know this is aids but it didnt work
        }else if(args[0].equalsIgnoreCase("clear")){
            for(Module m : Hime.instance.moduleManager.getModules()){
                m.setName(m.getName());
                // i know this is aids but it didnt work
            }
        }
    }

}