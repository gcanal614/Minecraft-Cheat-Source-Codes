// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.command;

import java.util.Iterator;
import bozoware.base.command.CommandArgumentException;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import bozoware.base.util.Wrapper;
import org.lwjgl.input.Keyboard;
import bozoware.base.module.Module;
import bozoware.base.BozoWare;
import bozoware.base.command.Command;

public class BindCommand implements Command
{
    @Override
    public String[] getAliases() {
        return new String[] { "bind", "b" };
    }
    
    @Override
    public void execute(final String[] arguments) throws CommandArgumentException {
        if (arguments.length == 3) {
            for (final Module module : BozoWare.getInstance().getModuleManager().getModules()) {
                if (module.getModuleName().replaceAll(" ", "").equalsIgnoreCase(arguments[1])) {
                    final String key = String.valueOf(Keyboard.getKeyIndex(arguments[2].toUpperCase()));
                    try {
                        final int parsedKey = Integer.parseInt(key);
                        module.setModuleBind(parsedKey);
                        Wrapper.getPlayer().addChatMessage(new ChatComponentText("Bound §e" + module.getModuleName() + "§f to key §e" + arguments[2].toUpperCase()));
                    }
                    catch (NumberFormatException e) {
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
        return ".bind <module> <key>";
    }
}
