package non.asset.command.impl;

import org.apache.commons.lang3.text.WordUtils;

import non.asset.Clarinet;
import non.asset.command.Command;
import non.asset.utils.OFC.Printer;

public class Help extends Command {

	public Help() {
		super("Help", new String[]{"h", "help"});
	}

	@Override
	public void onRun(final String[] s) {
		Clarinet.INSTANCE.getCommandManager().getCommandMap().values().forEach(command -> {
			Printer.print(WordUtils.capitalizeFully(command.getLabel()));
		});
	}
}
