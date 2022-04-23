// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.command;

import bozoware.base.BozoWare;
import java.util.Arrays;
import bozoware.base.command.Command;

public class WatermarkCommand implements Command
{
    public static String watermark;
    
    @Override
    public String[] getAliases() {
        return new String[] { "watermark", "clientname" };
    }
    
    @Override
    public void execute(final String[] arguments) {
        if (arguments.length >= 2) {
            WatermarkCommand.watermark = arguments[1];
            for (int i = 2; i < Arrays.stream(arguments).count(); ++i) {
                WatermarkCommand.watermark = WatermarkCommand.watermark + " " + arguments[i];
            }
            final String watermark = WatermarkCommand.watermark;
            final String regex = "<build>";
            BozoWare.getInstance().getClass();
            WatermarkCommand.watermark = watermark.replaceAll(regex, "220331");
            WatermarkCommand.watermark = WatermarkCommand.watermark.replaceAll("<user>", BozoWare.BozoUserName);
        }
    }
    
    @Override
    public String getUsage() {
        return ".watermark <watermark>";
    }
    
    static {
        WatermarkCommand.watermark = "BozoWare";
    }
}
