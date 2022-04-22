package stellar.skid.commands.impl;

import stellar.skid.StellarWare;
import stellar.skid.commands.NovoCommand;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.configurations.holder.ModuleHolder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.stream.Collectors;

public final class ToggleCommand extends NovoCommand {

    /* constructors */
    public ToggleCommand(@NonNull StellarWare stellarWare) {
        super(stellarWare, "t", "Toggles module", "toggle");
    }

    /* methods */
    @Override
    public void process(String[] args) {
        if (args.length < 1) {
            notifyError("Use .toggle (module)");
            return;
        }

        for (String arg : args) {
            if (arg != null) {
                final AbstractModule module = StellarWare.getInstance().getModuleManager().getByNameIgnoreCase(arg);

                if (module == null) {
                    notifyError("Module " + arg + " was not found!");
                    continue;
                }

                notify((module.toggle() ? "Enabled" : "Disabled") + " " + module.getName());
            }
        }
    }

    @Override
    public @NonNull List<String> completeTabOptions(@NonNull String[] args) {
        final String arg = args[args.length - 1].toLowerCase();
        return this.stellarWare.getModuleManager().getModuleManager().values().stream().map(ModuleHolder::getModule)
                .map(AbstractModule::getName).map(String::toLowerCase).filter(s -> s.startsWith(arg))
                .collect(Collectors.toCollection(ObjectArrayList::new));
    }
}
