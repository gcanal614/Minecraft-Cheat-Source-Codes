package non.asset.module.impl.visuals;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import non.asset.Clarinet;
import non.asset.module.Module;
import non.asset.utils.value.impl.EnumValue;


public class Cape extends Module {

    public enum Mode {
    	BLUE, RED, GREEN, PAPER
    }
    
    public Cape() {
        super("Cape", Module.Category.VISUALS);
        setHidden(true);
    }
    
    public ResourceLocation getCape() {
    	
    	/*
    	if(cape.getValue() == Mode.RED) {
            return new ResourceLocation("textures/client/CLIENTRED.jpg");
    	}
    	if(cape.getValue() == Mode.GREEN) {
            return new ResourceLocation("textures/client/CLIENTGREEN.jpg");
    	}
    	if(cape.getValue() == Mode.PAPER) {
            return new ResourceLocation("textures/client/CLIENTPAPER.jpg");
    	}
        return new ResourceLocation("textures/client/CLIENTFADE.jpg");*/
    	return new ResourceLocation("textures/client/GradientCapeWithoutLogo.jpg");
    }
    
    public boolean canRender(AbstractClientPlayer player) {
        if(player == getMc().thePlayer) return true;
        return isEnabled() && Clarinet.INSTANCE.getFriendManager().isFriend(player.getName());
    }
}
