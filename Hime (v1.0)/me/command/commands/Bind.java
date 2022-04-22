package me.command.commands;

import java.io.File;

import me.Hime;
import org.lwjgl.input.Keyboard;

import me.command.Command;
import me.module.Module;
import me.script.ScriptManager;



public class Bind extends Command{

	@Override
	public String getName() {
		return "bind";
	}

	@Override
	public String getDescription() {
		return "Allows user to change keybind of module.";
	}

	@Override
	public String getSyntax() {
		return ".bind set [MOD] [Key] | .bind del [MOD] | .bind clear";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if(args[0].equalsIgnoreCase("set")){
			args[2] = args[2].toUpperCase();
			int key = Keyboard.getKeyIndex(args[2]);
			
			for(Module m: Hime.instance.moduleManager.getModules()){
				if(args[1].equalsIgnoreCase(m.getName())){
					m.setKey(Keyboard.getKeyIndex(Keyboard.getKeyName(key)));
					Hime.addClientChatMessage("§9" + m.getName() + " §fhas been §7bound §fto §a" + args[2]);
				}
			}
			
			int countReal = 0;
			for(File file : ScriptManager.instance.dir.listFiles()) {
				String realName = file.getName().replace(".txt", "");
			//	 Hime.addClientChatMessage(ScriptManager.instance.scriptKeycodes.get(countReal).toString() + ScriptManager.instance.enabledScripts.get(countReal).toString());
				 
				if(args[1].equalsIgnoreCase(realName)) {
				 ScriptManager.instance.scriptKeycodes.set(countReal, Keyboard.getKeyIndex(Keyboard.getKeyName(key)));
				 Hime.addClientChatMessage("§9" + realName + " §f script has been §7bound §fto §a" + args[2]);
			
				}
				  countReal++;
			}
			
		}else if(args[0].equalsIgnoreCase("del")){
			for(Module m: Hime.instance.moduleManager.getModules()){
				if(m.getName().equalsIgnoreCase(args[1])){
					m.setKey(0);
					Hime.addClientChatMessage("§9" + m.getName() + " §fhas been §7unbound");
				}
			}
			int countReal = 0;
			for(File file : ScriptManager.instance.dir.listFiles()) {
				String realName = file.getName().replace(".txt", "");
				if(args[1].equalsIgnoreCase(realName)) {
				 ScriptManager.instance.scriptKeycodes.set(countReal, 0);
				 Hime.addClientChatMessage("§9" + realName + " §f script has been §7unbound");
				}
				  countReal++;
			}
		}else if(args[0].equalsIgnoreCase("clear")){
			for(Module m: Hime.instance.moduleManager.getModules()){
				m.setKey(0);
			}
			int countReal = 0;
			for(File file : ScriptManager.instance.dir.listFiles()) {
				String realName = file.getName().replace(".txt", "");
				 ScriptManager.instance.scriptKeycodes.set(countReal, 0);
				// Hime.addClientChatMessage("§9" + realName + " §f script has been §7unbound");
				  countReal++;
			}
			Hime.addClientChatMessage("All keys cleared");
		}
	}

}
