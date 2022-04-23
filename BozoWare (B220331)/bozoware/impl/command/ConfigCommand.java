// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.command;

import java.io.IOException;
import java.io.File;
import java.awt.Desktop;
import bozoware.base.config.Config;
import bozoware.base.command.CommandArgumentException;
import bozoware.base.BozoWare;
import bozoware.base.command.Command;

public class ConfigCommand implements Command
{
    @Override
    public String[] getAliases() {
        return new String[] { "config", "profile", "preset", "c" };
    }
    
    @Override
    public void execute(final String[] arguments) throws CommandArgumentException {
        if (arguments.length == 3 || arguments[1].equalsIgnoreCase("folder")) {
            final String action = arguments[1];
            final String lowerCase = action.toLowerCase();
            switch (lowerCase) {
                case "load": {
                    if (BozoWare.getInstance().getConfigManager().loadConfig(arguments[2])) {
                        BozoWare.getInstance().chat("Successfully loaded config §e" + arguments[2]);
                        return;
                    }
                    throw new CommandArgumentException(this.getUsage());
                }
                case "save": {
                    if (BozoWare.getInstance().getConfigManager().saveConfig(new Config(arguments[2]))) {
                        BozoWare.getInstance().chat("Successfully saved config §e" + arguments[2]);
                        return;
                    }
                    throw new CommandArgumentException(this.getUsage());
                }
                case "folder": {
                    try {
                        Desktop.getDesktop().open(new File(BozoWare.getInstance().getConfigManager().getConfigDirectory()));
                    }
                    catch (IOException e) {
                        BozoWare.getInstance().chat("Couldn't open config folder");
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
        throw new CommandArgumentException(this.getUsage());
    }
    
    @Override
    public String getUsage() {
        return "config <load/save/folder> <config-name>";
    }
}
