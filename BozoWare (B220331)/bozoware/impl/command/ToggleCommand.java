// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.command;

import java.util.Iterator;
import bozoware.base.command.CommandArgumentException;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import bozoware.base.util.Wrapper;
import bozoware.base.module.Module;
import bozoware.base.BozoWare;
import bozoware.base.command.Command;

public class ToggleCommand implements Command
{
    @Override
    public String[] getAliases() {
        return new String[] { "toggle", "t" };
    }
    
    @Override
    public void execute(final String[] arguments) throws CommandArgumentException {
        if (arguments.length == 2) {
            for (final Module module : BozoWare.getInstance().getModuleManager().getModules()) {
                if (module.getModuleName().replaceAll(" ", "").equalsIgnoreCase(arguments[1])) {
                    BozoWare.getInstance().getModuleManager().getModuleByName.apply(module.getModuleName()).toggleModule();
                    Wrapper.getPlayer().addChatMessage(new ChatComponentText(module.getModuleName() + " was " + (module.isModuleToggled() ? "§aEnabled" : "§4Disabled")));
                    return;
                }
            }
        }
        throw new CommandArgumentException(this.getUsage());
    }
    
    @Override
    public String getUsage() {
        return ".toggle <module>";
    }
}
