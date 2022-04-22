package non.asset.module.impl.other;

import com.google.common.eventbus.Subscribe;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.game.TickEvent;
import non.asset.module.Module;
import non.asset.utils.value.impl.NumberValue;

import java.awt.*;

public class TimeChanger extends Module {
    
	public static NumberValue<Long> time = new NumberValue<>("Time", 18400L, 0L, 24000L, 100L);
   
    public TimeChanger() {
        super("TimeChanger", Category.OTHER);
        setRenderLabel("TimeChanger");
        setDescription("Change client-side time");
    }
    
}
