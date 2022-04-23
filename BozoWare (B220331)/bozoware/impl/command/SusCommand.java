// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.command;

import bozoware.base.command.CommandArgumentException;
import java.net.URISyntaxException;
import java.io.IOException;
import java.net.URI;
import java.awt.Desktop;
import bozoware.base.command.Command;

public class SusCommand implements Command
{
    @Override
    public String[] getAliases() {
        return new String[] { "sus", "ph" };
    }
    
    @Override
    public void execute(final String[] arguments) throws CommandArgumentException {
        try {
            Desktop.getDesktop().browse(new URI("https://pornhub.com/gay"));
        }
        catch (IOException | URISyntaxException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    @Override
    public String getUsage() {
        return null;
    }
}
