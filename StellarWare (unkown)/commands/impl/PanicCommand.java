package stellar.skid.commands.impl;

import stellar.skid.StellarWare;
import stellar.skid.commands.NovoCommand;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.configurations.holder.ModuleHolder;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PanicCommand extends NovoCommand {

    /* constructors */
    public PanicCommand(@NonNull StellarWare stellarWare) {
        super(stellarWare, "p", "Disable modules", "panic");
    }

    /* methods */
    @Override
    public void process(String[] args) {
        if (args.length > 1) {
            notifyError("Use .panic or .panic (module type)");
            return;
        }


    }
}
