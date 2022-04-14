package com.boomer.client.module.modules.player;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.Client;
import com.boomer.client.event.events.game.TickEvent;
import com.boomer.client.module.Module;
import com.boomer.client.utils.TimerUtil;
import com.boomer.client.utils.value.impl.BooleanValue;
import com.boomer.client.utils.value.impl.NumberValue;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * @author Xen for BoomerWare
 * @since 7/31/2019
 **/
public class Capes extends Module {
    public BooleanValue animated = new BooleanValue("Animated",false);
    public NumberValue<Integer> delay = new NumberValue<>("Animated Delay",3,1,10,1);

    private int frame = 0;
    private TimerUtil timerUtil = new TimerUtil();

    public Capes() {
        super("Capes", Category.PLAYER, 0);
        setHidden(true);
        addValues(animated,delay);
    }

    @Handler
    public void onTick(TickEvent event) {
        if(timerUtil.reach(10000)) {
            frame++;
        }
        if(frame > 5) {
            frame = 0;
        }
    }

    public ResourceLocation getCape() {
        if(animated.isEnabled()) {
            return new ResourceLocation("textures/client/cape/bhopped_" + frame + ".png");
        }
        return new ResourceLocation("textures/client/cape/bhopped_0.png");
    }

    public boolean canRender(AbstractClientPlayer player) {
        if(player == mc.thePlayer) return true;
        return isEnabled() && Client.INSTANCE.getFriendManager().isFriend(player.getName());
    }

}
