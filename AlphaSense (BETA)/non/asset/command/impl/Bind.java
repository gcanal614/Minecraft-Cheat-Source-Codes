package non.asset.command.impl;

import org.lwjgl.input.Keyboard;

import non.asset.Clarinet;
import non.asset.command.Command;
import non.asset.module.Module;
import non.asset.utils.OFC.Printer;

import java.util.Objects;


public class Bind extends Command {

	public Bind() {
		super("Bind",new String[]{"bind","b"});
	}

	@Override
	public void onRun(final String[] args) {
		if (args.length == 2) {
			if (args[1].toLowerCase().equals("resetall")) {
				Clarinet.INSTANCE.getModuleManager().getModuleMap().values().forEach(m -> m.setKeybind(0));
				Clarinet.INSTANCE.getNotificationManager().addNotification("Reset all keybinds.", 2000);
				return;
			}
		}
		
		if (args.length == 3) {
			String moduleName = args[1];
			Module module = Clarinet.INSTANCE.getModuleManager().getModule(moduleName);
			if (module != null) {
				int keyCode = Keyboard.getKeyIndex(args[2].toUpperCase());
				if (keyCode != -1) {
					module.setKeybind(keyCode);
					//Printer.print(module.getLabel() + " is now bound to \"" + Keyboard.getKeyName(keyCode) + "\".");
					Clarinet.INSTANCE.getNotificationManager().addNotification((Objects.nonNull(module.getRenderLabel()) ? module.getRenderLabel():module.getLabel()) + " is now bound to \"" + Keyboard.getKeyName(keyCode) + "\".", 2000);
				} else {
					Printer.print("That is not a valid key code.");
				}
			} else {
				Clarinet.INSTANCE.getNotificationManager().addNotification("That module does not exist.", 2000);
                Printer.print("That module does not exist.");
				//Printer.print("Type \"modules\" for a list of all modules.");
			}
		} else {
			Printer.print("Invalid arguments.");
			Printer.print("Usage: \"bind [module] [key]\"");
		}
	}
}
