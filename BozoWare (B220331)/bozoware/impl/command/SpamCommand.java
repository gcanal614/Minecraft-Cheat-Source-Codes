// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.command;

import bozoware.base.BozoWare;
import java.util.Arrays;
import bozoware.base.command.Command;

public class SpamCommand implements Command
{
    public static String spamMessage;
    
    @Override
    public String[] getAliases() {
        return new String[] { "spammessage", "spam" };
    }
    
    @Override
    public void execute(final String[] arguments) {
        if (arguments.length >= 2) {
            SpamCommand.spamMessage = arguments[1];
            for (int i = 2; i < Arrays.stream(arguments).count(); ++i) {
                SpamCommand.spamMessage = SpamCommand.spamMessage + " " + arguments[i];
            }
            BozoWare.getInstance().chat("Spam message was set to " + SpamCommand.spamMessage);
        }
    }
    
    @Override
    public String getUsage() {
        return ".watermark <watermark>";
    }
    
    static {
        SpamCommand.spamMessage = "BOZOWARE > YOU NN!";
    }
}
