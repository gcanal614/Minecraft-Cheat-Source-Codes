// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.command;

import bozoware.base.command.CommandArgumentException;
import bozoware.base.command.Command;

public class TPCommand implements Command
{
    public static String player;
    
    @Override
    public String[] getAliases() {
        return new String[] { "tp" };
    }
    
    @Override
    public void execute(final String[] arguments) throws CommandArgumentException {
        if (arguments.length == 2) {
            TPCommand.player = arguments[1];
        }
    }
    
    @Override
    public String getUsage() {
        return ".tp <player>";
    }
}
