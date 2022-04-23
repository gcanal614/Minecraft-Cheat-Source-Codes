// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.command;

import bozoware.base.command.CommandArgumentException;
import bozoware.base.BozoWare;
import bozoware.base.command.Command;

public class IRCCommand implements Command
{
    @Override
    public String[] getAliases() {
        return new String[] { "irc", "ircchat" };
    }
    
    @Override
    public void execute(final String[] arguments) throws CommandArgumentException {
        if (arguments.length == 2) {
            if (BozoWare.getInstance().getIRCClient().isConnected()) {
                final String message = joinArray(arguments, " ", 1, arguments.length);
                BozoWare.getInstance().getIRCClient().getReceiver().getSender().addMessage(message);
            }
            else {
                BozoWare.getInstance().chat("You are not connected to the IRC!");
            }
        }
    }
    
    @Override
    public String getUsage() {
        return null;
    }
    
    public static final String joinArray(final String[] input, final String seperator, int begin, int end) {
        if (begin < 0) {
            begin = 0;
        }
        if (end > input.length) {
            end = input.length;
        }
        final StringBuilder builder = new StringBuilder();
        for (int i = begin; i < end; ++i) {
            builder.append(input[i]).append(seperator);
        }
        return builder.toString();
    }
}
