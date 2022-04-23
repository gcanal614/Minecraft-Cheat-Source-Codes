// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.command;

public interface Command
{
    String[] getAliases();
    
    void execute(final String[] p0) throws CommandArgumentException;
    
    String getUsage();
}
