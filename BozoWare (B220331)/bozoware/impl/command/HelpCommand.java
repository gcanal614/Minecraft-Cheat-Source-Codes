// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.command;

import bozoware.base.command.CommandArgumentException;
import bozoware.base.BozoWare;
import bozoware.base.command.Command;

public class HelpCommand implements Command
{
    @Override
    public String[] getAliases() {
        return new String[] { "help", "HELP" };
    }
    
    @Override
    public void execute(final String[] arguments) throws CommandArgumentException {
        BozoWare.getInstance().chat("BozoWare");
        BozoWare.getInstance().chat("VClip Command - Adds Then Clips Your Y Postion");
        BozoWare.getInstance().chat("Sus/ph Command - Opens gay porn");
        BozoWare.getInstance().chat("Bozo Command - bozo");
        BozoWare.getInstance().chat("Bind Command - Binds Selected Module To Key");
        BozoWare.getInstance().chat("Config save/load/folder - Allows You To Save, Load And Open Your Configs Folder");
        BozoWare.getInstance().chat("Toggle/T Command - Toggles Module");
        BozoWare.getInstance().chat("Name Command - Copies Your Name To The Clipboard");
    }
    
    @Override
    public String getUsage() {
        return null;
    }
}
