package stellar.skid.commands.impl;

import stellar.skid.StellarWare;
import stellar.skid.commands.NovoCommand;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.utils.messages.MessageFactory;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;

public final class RenameCommand extends NovoCommand {

    /* constructors */
    public RenameCommand(@NonNull StellarWare stellarWare) {
        super(stellarWare, "rename", "Renames the given module", Arrays.asList("modulerename", "modulerename"));
    }

    /* methods */
    // todo
    @Override
    public void process(String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reset")) {
            for (EnumModuleType type : EnumModuleType.values()) {
                for (AbstractModule module : stellarWare.getModuleManager().getModuleManager().getByCategory(type)) {
                    module.setDisplayNameProperty(module.getVanillaDisplayName());
                    notify("all module names reseted");
                }
            }

        } else if (args.length == 2 && stellarWare.getModuleManager().getByNameIgnoreCase(args[0]) != null) {
            AbstractModule module = stellarWare.getModuleManager().getByNameIgnoreCase(args[0]);

            if (args[1].equalsIgnoreCase("reset")) {
                module.setDisplayNameProperty(module.getVanillaDisplayName());
                notify(args[0] + " name reseted");
            } else {
                String newName = args[1].replace("_", " ").replace("&", "\u00A7");
                module.setDisplayNameProperty(newName);
                notify(args[0] + " renamed to " + newName);
            }

        } else {
            sendHelp( // @off
                    "Rename help:", ".rename",
                    MessageFactory.usage("(module name) (new name)", "rename module"),
                    MessageFactory.usage("(module name) reset", "resets module name"),
                    MessageFactory.usage("reset", "resets names for all modules"));
        }
    }
}
