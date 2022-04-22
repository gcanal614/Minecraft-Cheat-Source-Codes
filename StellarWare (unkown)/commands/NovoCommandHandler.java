package stellar.skid.commands;

import stellar.skid.StellarWare;
import net.minecraft.command.CommandHandler;
import org.jetbrains.annotations.NotNull;
import stellar.skid.commands.impl.*;

/**
 * @author xDelsy
 */

//@Protect
public class NovoCommandHandler extends CommandHandler {

	public NovoCommandHandler(@NotNull StellarWare stellarWare) {
		super(".");
		registerCommands(stellarWare);
	}

	public void registerCommands(StellarWare stellarWare) {
		registerCommand(new TargetCommand(stellarWare));
		registerCommand(new BindCommand(stellarWare));
		registerCommand(new ConfigCommand(stellarWare));
		registerCommand(new FriendCommand(stellarWare));
		registerCommand(new NameCommand(stellarWare));
		registerCommand(new ToggleCommand(stellarWare));
		registerCommand(new VClipCommand(stellarWare));
		registerCommand(new WaypointCommand(stellarWare));
		registerCommand(new PanicCommand(stellarWare));
		registerCommand(new HideCommand(stellarWare));
		registerCommand(new StatusCommand(stellarWare));
		registerCommand(new KillsultsCommand(stellarWare));
		registerCommand(new TeleportCommand(stellarWare));
		registerCommand(new RenameCommand(stellarWare));

		registerCommand(new TestCommand(stellarWare));
	}
}
