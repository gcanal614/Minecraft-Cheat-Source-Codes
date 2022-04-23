// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.command;

import bozoware.base.command.CommandArgumentException;
import bozoware.base.BozoWare;
import net.minecraft.network.Packet;
import bozoware.base.util.Wrapper;
import net.minecraft.network.play.client.C0CPacketInput;
import bozoware.base.command.Command;

public class BanCommand implements Command
{
    @Override
    public String[] getAliases() {
        return new String[] { "ban", "instaban", "sendc13" };
    }
    
    @Override
    public void execute(final String[] arguments) throws CommandArgumentException {
        for (int i = 0; i < 200; ++i) {
            Wrapper.sendPacketDirect(new C0CPacketInput());
            BozoWare.getInstance().chat("Sent c13");
        }
    }
    
    @Override
    public String getUsage() {
        return null;
    }
}
