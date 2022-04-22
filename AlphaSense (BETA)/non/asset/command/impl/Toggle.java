package non.asset.command.impl;


import java.util.Objects;

import non.asset.Clarinet;
import non.asset.command.Command;
import non.asset.module.Module;
import non.asset.utils.OFC.Printer;

public class Toggle extends Command {

	public Toggle() {
		super("Toggle",new String[]{"t","toggle"});
	}

	@Override
	public void onRun(final String[] s) {
		if (s.length <= 1) {
			Printer.print("Not enough args.");
			return;
		}
			for (Module m : Clarinet.INSTANCE.getModuleManager().getModuleMap().values()) {
				if (m.getLabel().toLowerCase().equals(s[1])) {
					m.toggle();
					if(m.isEnabled()) {
						Clarinet.INSTANCE.getNotificationManager().addNotification("Toggled " + (Objects.nonNull(m.getRenderLabel()) ? m.getRenderLabel():m.getLabel()), 2000);
                    	Printer.print("Toggled " + m.getLabel());
					}else {
						Clarinet.INSTANCE.getNotificationManager().addNotification("Disabled " + (Objects.nonNull(m.getRenderLabel()) ? m.getRenderLabel():m.getLabel()), 2000);
                    	Printer.print("Disabled " + m.getLabel());
					}
					break;
				}
			}
	}
}
