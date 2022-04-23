// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.command;

import bozoware.base.command.CommandArgumentException;
import bozoware.base.BozoWare;
import bozoware.base.util.Wrapper;
import bozoware.base.command.Command;

public class VClipCommand implements Command
{
    @Override
    public String[] getAliases() {
        return new String[] { "vclip", "clip" };
    }
    
    @Override
    public void execute(final String[] arguments) throws CommandArgumentException {
        if (arguments.length == 2) {
            try {
                final double parsedValue = Double.parseDouble(arguments[1]);
                Wrapper.getPlayer().setEntityBoundingBox(Wrapper.getPlayer().getEntityBoundingBox().offset(0.0, parsedValue, 0.0));
                BozoWare.getInstance().chat("VClipped " + parsedValue + " blocks!");
                return;
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
                throw new CommandArgumentException(this.getUsage());
            }
        }
        throw new CommandArgumentException(this.getUsage());
    }
    
    @Override
    public String getUsage() {
        return ".vclip <value>";
    }
}
