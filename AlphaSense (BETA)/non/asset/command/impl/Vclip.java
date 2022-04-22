package non.asset.command.impl;

import net.minecraft.client.Minecraft;
import non.asset.Clarinet;
import non.asset.command.Command;
import non.asset.utils.OFC.Printer;

public class Vclip extends Command {

	public Vclip() {
		super("Vclip", new String[]{"v", "vclip"});
	}

	@Override
	public void onRun(final String[] args) {
		if (args.length == 1) {
			Printer.print(".vclip <value>");
			return;
		}
		final double distance = Double.parseDouble(args[1]);
		Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offsetAndUpdate(0, distance, 0);
		Clarinet.INSTANCE.getNotificationManager().addNotification("Vcliped " + args[1] + "!", 2000);
		Printer.print("Vcliped " + args[1] + "!");
	}
}
