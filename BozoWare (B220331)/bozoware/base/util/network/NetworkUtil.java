// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.network;

import net.minecraft.client.network.NetworkPlayerInfo;
import bozoware.base.util.Wrapper;
import net.minecraft.client.Minecraft;

public class NetworkUtil
{
    static Minecraft mc;
    
    public static int getPing() {
        final NetworkPlayerInfo playerInfo = Wrapper.getMinecraft().getNetHandler().getPlayerInfo(Wrapper.getPlayer().getUniqueID());
        return (playerInfo == null) ? 0 : playerInfo.getResponseTime();
    }
    
    public static boolean isHypixel() {
        return !NetworkUtil.mc.isSingleplayer() && NetworkUtil.mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel.net");
    }
    
    static {
        NetworkUtil.mc = Minecraft.getMinecraft();
    }
}
