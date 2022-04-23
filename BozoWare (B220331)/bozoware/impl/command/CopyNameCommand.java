// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.command;

import bozoware.base.command.CommandArgumentException;
import bozoware.base.BozoWare;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import bozoware.base.util.Wrapper;
import bozoware.base.command.Command;

public class CopyNameCommand implements Command
{
    @Override
    public String[] getAliases() {
        return new String[] { "name", "copys name" };
    }
    
    @Override
    public void execute(final String[] arguments) throws CommandArgumentException {
        if (arguments.length == 1) {
            try {
                final StringSelection stringselection = new StringSelection(Wrapper.getPlayer().getName());
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
                BozoWare.getInstance().chat("Copied Name!");
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
        return ".name";
    }
}
