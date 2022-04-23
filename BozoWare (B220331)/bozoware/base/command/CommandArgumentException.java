// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.command;

public class CommandArgumentException extends Exception
{
    public CommandArgumentException(final String message) {
        super("§eInvalid Arguments: §f" + message);
    }
}
