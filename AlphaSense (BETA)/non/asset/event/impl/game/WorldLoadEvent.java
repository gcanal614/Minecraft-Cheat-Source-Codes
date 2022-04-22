package non.asset.event.impl.game;

import net.minecraft.client.multiplayer.WorldClient;
import non.asset.event.Event;

public class WorldLoadEvent extends Event {
    private WorldClient worldClient;

    public WorldLoadEvent(WorldClient worldClient) {
        this.worldClient = worldClient;
    }

    public WorldClient getWorldClient() {
        return worldClient;
    }
}
