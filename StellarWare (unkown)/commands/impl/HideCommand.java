package stellar.skid.commands.impl;

import stellar.skid.StellarWare;
import stellar.skid.commands.NovoCommand;
import stellar.skid.modules.AbstractModule;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;

public final class HideCommand extends NovoCommand {

    public HideCommand(@NonNull StellarWare stellarWare) {
        super(stellarWare, "Hide", "Hides modules from arraylist", Arrays.asList("hide", "h"));
    }

    @Override
    public void process(String[] args) {
        if (args.length == 1) {
            final String arg = args[0];
            final AbstractModule module = this.stellarWare.getModuleManager().getByNameIgnoreCase(arg);

            if (module == null) {
                notifyError("Module " + arg + " was not found!");
                return;
            }

            module.setHidden(!module.isHidden());
            notify((module.isHidden() ? "Hidden" : "Shown") + " " + module.getName());
        } else {
            notifyError("Use .hide <module> to hide a module!");
        }
    }

}
