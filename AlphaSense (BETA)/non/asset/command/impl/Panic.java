package non.asset.command.impl;

import org.apache.commons.lang3.text.WordUtils;

import non.asset.Clarinet;
import non.asset.command.Command;
import non.asset.module.Module;
import non.asset.utils.OFC.Printer;

public class Panic extends Command {

	public Panic() {
		super("Panic", new String[]{"panic", "panic"});
	}

	@Override
	public void onRun(final String[] s) {
		Printer.print("Turned off all modules.");
		for (Module m : Clarinet.INSTANCE.getModuleManager().getModuleMap().values()) {
			if(m.isEnabled()) {
				m.toggle();
			}
		}
	}
}
