package non.asset.module.impl.other;

import net.minecraft.network.play.server.S38PacketPlayerListItem;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.game.TickEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.Printer;

import java.awt.*;
import java.util.*;

public class VanishDetector extends Module {
    private Set<UUID> vanished = new HashSet<>();
    private HashMap<UUID, String> uuids = new HashMap<>();

    public VanishDetector() {
        super("VanishDetector", Category.OTHER);
        setDescription("Alerts if have a vanished player in the world");
        setRenderLabel("VanishDetector");
    }

    @Handler
    public void onTick(TickEvent event) {

        if (getMc().thePlayer == null) return;
        
        if (getMc().theWorld == null) return;
        
        if (getMc().getNetHandler() != null) {
            getMc().getNetHandler().getRealPlayerInfoMap().values().forEach(info -> {
                if (info.getGameProfile().getName() != null) uuids.put(info.getGameProfile().getId(), info.getGameProfile().getName());
            });
        }
    }

    @Handler
    public void onPacket(PacketEvent event) {
    	

        if (getMc().thePlayer == null) return;
        
        if (getMc().theWorld == null) return;
        
        if (event.getPacket() instanceof S38PacketPlayerListItem) {
            S38PacketPlayerListItem packet = (S38PacketPlayerListItem) event.getPacket();
            if (packet.func_179768_b() == S38PacketPlayerListItem.Action.UPDATE_LATENCY) {
                packet.func_179767_a().forEach(data -> {
                    if (getMc().getNetHandler().getPlayerInfo(data.getProfile().getId()) == null) {
                        if (!vanished.contains(data.getProfile().getId()))
                            Printer.print(getName(data.getProfile().getId()) + " is now vanished.");
                        vanished.add(data.getProfile().getId());
                    }
                });
            }
        }
    }

    @Handler
    public void onUpdate(UpdateEvent event) {

        if (getMc().thePlayer == null) return;
        
        if (getMc().theWorld == null) return;
        
        if(vanished != null) {
            vanished.forEach(uuid -> {
                if (getMc().getNetHandler().getPlayerInfo(uuid) != null)
                    Printer.print(getName(uuid) + " is no longer vanished.");
                    vanished.remove(uuid);
            });
        }
    }

    public String getName(UUID uuid) {
        if (uuids.containsKey(uuid)) {
            return uuids.get(uuid);
        }
        return "undefined - " + uuid.toString();
    }


}
