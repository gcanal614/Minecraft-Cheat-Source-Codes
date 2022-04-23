// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.command;

import bozoware.base.command.CommandArgumentException;
import java.net.URISyntaxException;
import java.io.IOException;
import bozoware.base.BozoWare;
import java.net.URI;
import java.awt.Desktop;
import bozoware.base.command.Command;

public class BozoCommand implements Command
{
    @Override
    public String[] getAliases() {
        return new String[] { "bozo", "BOZO" };
    }
    
    @Override
    public void execute(final String[] arguments) throws CommandArgumentException {
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=GU4Hw-xbBNo&ab"));
            System.out.println("BOZO DOWN!!");
            BozoWare.getInstance().chat("BOZO DOWN");
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
