// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.command;

import bozoware.base.command.CommandArgumentException;
import java.util.Iterator;
import bozoware.base.BozoWare;
import bozoware.base.module.Module;
import java.util.ArrayList;
import bozoware.base.command.Command;

public class HideCommand implements Command
{
    public static final ArrayList<Module> isHidden;
    
    @Override
    public String[] getAliases() {
        return new String[] { "hide", "show" };
    }
    
    @Override
    public void execute(final String[] arguments) throws CommandArgumentException {
        if (arguments.length == 2) {
            for (final Module module : BozoWare.getInstance().getModuleManager().getModules()) {
                if (module.getModuleName().replaceAll(" ", "").equalsIgnoreCase(arguments[1])) {
                    if (!HideCommand.isHidden.contains(module)) {
                        HideCommand.isHidden.add(module);
                    }
                    else {
                        HideCommand.isHidden.remove(module);
                    }
                }
            }
        }
    }
    
    @Override
    public String getUsage() {
        return ".hide <module>";
    }
    
    static {
        isHidden = new ArrayList<Module>();
    }
}
