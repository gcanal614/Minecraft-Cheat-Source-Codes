package non.asset.module.impl.other;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.game.TickEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.ChatUtil;
import non.asset.utils.OFC.TimerUtil;
import non.asset.utils.value.impl.BooleanValue;

public class NameProtect extends Module {
    private TimerUtil timer = new TimerUtil();
	
    public static String NAMEPROTECT = "Stackfl0w";
	
    private String SCRAMBLE = "SCRAMBLE";
    private String SPOOFSKINS = "SPOOFSKINS";
    

    public static boolean scrambleNames;
    public static boolean spoofSkins;
    

    public static List<String> strings = new ArrayList<>();
    

    public static BooleanValue scramble = new BooleanValue("Scramble", false);
    public static BooleanValue spoofskin = new BooleanValue("Spoof Skins", false);

    public NameProtect() {
        super("NameProtect", Category.OTHER);
        setRenderLabel("NameProtect");
    }
    
    @Override
    public void onEnable() {
        
    }
    
    @Handler
    public void onUpdate(UpdateEvent event) {
        if (getMc().theWorld == null) return;
    
        scrambleNames = scramble.isEnabled();
        spoofSkins = spoofskin.isEnabled();
        
        final NetHandlerPlayClient var4 = mc.thePlayer.sendQueue;
        final List players = (List) GuiPlayerTabOverlay.field_175252_a.sortedCopy(var4.getPlayerInfoMap());
        for (final Object o : players) {
            final NetworkPlayerInfo info = (NetworkPlayerInfo) o;
            if (info == null) {
                break;
            }
            if (!strings.contains(info.getGameProfile().getName())) {
                strings.add(info.getGameProfile().getName());
            }
        }
        
        for (Object o : mc.theWorld.getLoadedEntityList()) {
            if (o instanceof EntityPlayer) {
                if (!strings.contains(((EntityPlayer) o).getName())) {
                    strings.add(((EntityPlayer) o).getName());
                }
            }
        }
        
    }
        

    @Handler
    public void onTick(TickEvent event) {
        if (getMc().thePlayer != null) {
            
        }
    }

    @Handler
    public void onPacket(PacketEvent event) {
        
    	if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            if (packet.getChatComponent().getUnformattedText().contains(mc.thePlayer.getName())) {
                String temp = packet.getChatComponent().getFormattedText();
                ChatUtil.printChat(temp.replaceAll(mc.thePlayer.getName(), "\2479You\247r"));
                event.setCanceled(true);
            } else {
                String[] list = new String[]{"join", "left", "leave", "leaving", "lobby", "server", "fell", "died", "slain", "burn", "void", "disconnect", "kill", "by", "was", "quit", "blood", "game"};
                for (String str : list) {
                    if (packet.getChatComponent().getUnformattedText().toLowerCase().contains(str)) {
                        event.setCanceled(true);
                        break;
                    }
                }
            }
        }
    	
    }
}
