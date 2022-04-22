package non.asset.module.impl.movement;

import org.apache.commons.lang3.StringUtils;

import net.java.games.input.Event;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.player.MotionEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.module.impl.SpeedModifier;
import non.asset.utils.value.impl.EnumValue;
import non.asset.utils.value.impl.NumberValue;

public class LongJump extends Module {
    private double moveSpeed, lastDist;
    private int level;
 
    
    public LongJump() {
        super("LongJump", Category.MOVEMENT);
        setRenderLabel("LongJump");
        
    
    }
}
