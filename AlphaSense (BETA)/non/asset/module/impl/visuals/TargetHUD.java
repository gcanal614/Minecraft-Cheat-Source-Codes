package non.asset.module.impl.visuals;

import java.awt.Color;

import non.asset.module.Module;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.EnumValue;
import non.asset.utils.value.impl.NumberValue;


public class TargetHUD extends Module {
	
	public static EnumValue<modes> Mode = new EnumValue<modes>("Mode", modes.BLUE);

    //public static NumberValue<Float> X = new NumberValue<>("X", 339.8F, -1000.0F, 2000.0F, 0.05F);
    //public static NumberValue<Float> Y = new NumberValue<>("Y", -184.45F, -1000.0F, 2000.0F, 0.05F);
    
    public static BooleanValue animation = new BooleanValue("Smooth", false);
    
    public TargetHUD() {
        super("TargetHUD", Category.VISUALS);
        setHidden(true);
        setDescription("Show the target informations");
    }
    
    public static enum modes {
    	CLIENT, BLUE, OVAL
    }
}
