package stellar.skid.events.events;

import stellar.skid.events.events.callables.CancellableEvent;
import net.minecraft.network.Packet;

public class PacketEvent2 extends CancellableEvent {
    private final PacketDirection direction;
    private Packet packet;
    private static String c;

    public PacketEvent2(Packet Packet1, PacketDirection PacketDirection2, PacketDirection direction) {
        this.direction = direction;
        MotionUpdateEvent2.c();




    }

    public void a(Packet var1) {
        this.packet = var1;
    }

    public PacketDirection getDirection() {
        return this.direction;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public static void b(String var0) {
        c = var0;
    }

    public static String c() {
        return c;
    }

    static {
        if(c() == null) {
            b("RD2zX");
        }

    }
}

