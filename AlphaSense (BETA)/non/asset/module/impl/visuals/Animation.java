package non.asset.module.impl.visuals;

import org.apache.commons.lang3.StringUtils;

import non.asset.event.bus.Handler;
import non.asset.event.impl.game.TickEvent;
import non.asset.module.Module;
import non.asset.utils.value.impl.EnumValue;
import non.asset.utils.value.impl.NumberValue;

import java.awt.*;

public class Animation extends Module {
    public EnumValue<mode> Mode = new EnumValue<>("Mode",mode.OLD);
    public static NumberValue<Float> Slow = new NumberValue<>("Slow", 6.0F, 1.0F, 20.0F, 0.1F);
    public static NumberValue<Float> X = new NumberValue<>("X", 0.0F, -2.0F, 2.0F, 0.1F);
    public static NumberValue<Float> Y = new NumberValue<>("Y", 0.0F, -2.0F, 2.0F, 0.1F);
    public static NumberValue<Float> scale = new NumberValue<>("Scale", 1.0F, 0.1F, 2.0F, 0.1F);
    
    public Animation() {
        super("Animation", Category.VISUALS);
        setRenderLabel("Animation");
    }
    @Handler
    public void onTick(TickEvent event) {
        setSuffix(StringUtils.capitalize(Mode.getValue().toString().toLowerCase()));
    }
    public enum mode {
        OLD, NORMAL, SLIDE, EXHI, ASUNA, XD, ALPHA 
    }
}
