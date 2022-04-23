// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.world;

import java.io.IOException;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import net.minecraft.network.play.client.C01PacketChatMessage;
import bozoware.impl.event.network.PacketSendEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "IRC", moduleCategory = ModuleCategory.WORLD)
public class IRC extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<PacketSendEvent> onPacketSendEvent;
    
    public IRC() {
        C01PacketChatMessage c01;
        this.onPacketSendEvent = (e -> {
            if (e.getPacket() instanceof C01PacketChatMessage) {
                c01 = (C01PacketChatMessage)e.getPacket();
                if (c01.getMessage().startsWith("-")) {
                    e.setCancelled(true);
                }
            }
        });
    }
    
    public void printToIRC() throws IOException {
        final URL url = new URL("https://bozowareauth.000webhostapp.com/");
        final HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
    }
}
