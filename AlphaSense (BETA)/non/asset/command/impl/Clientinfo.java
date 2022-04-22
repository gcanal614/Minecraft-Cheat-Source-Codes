package non.asset.command.impl;

import non.asset.Clarinet;
import non.asset.command.Command;
import non.asset.utils.OFC.MoveUtil;
import non.asset.utils.OFC.Printer;

public class Clientinfo extends Command {

	public Clientinfo() {
		super("Clientinfo", new String[]{"client", "clientinfo"});
	}

	@Override
	public void onRun(final String[] s) {
		Printer.print(Clarinet.name + " " + Clarinet.version);
		Printer.print("by Prilicat & VinhMC");
		Printer.print("Beta " + MoveUtil.sigmaHatar != null ? "yes" : "no");
		
	}
	
	public String plssssMODULESS() {

		StringBuilder mods = new StringBuilder("Modules (" + Clarinet.INSTANCE.getModuleManager().getModuleMap().values().size() + "): ");
		Clarinet.INSTANCE.getModuleManager().getModuleMap().values()
                .forEach(mod -> mods.append(mod.isEnabled() ? "\247a" : "\247c").append(mod.getLabel()).append("\247r, "));
        return mods.toString().substring(0, mods.length() - 2);
        
	}
}
