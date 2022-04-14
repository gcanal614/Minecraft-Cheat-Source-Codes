package com.boomer.client.module.modules.movement;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.Client;
import com.boomer.client.event.events.player.StepEvent;
import com.boomer.client.event.events.player.UpdateEvent;
import com.boomer.client.module.Module;
import com.boomer.client.utils.TimerUtil;
import com.boomer.client.utils.value.impl.BooleanValue;
import com.boomer.client.utils.value.impl.NumberValue;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Step extends Module {
    private BooleanValue vanilla = new BooleanValue("Vanilla",true);
    private NumberValue<Float> height = new NumberValue<>("Height",1.0f,1.0f,2.5f,0.25f);
    private NumberValue<Integer> delay = new NumberValue<>("Delay",300,0,2000,1);

    private TimerUtil time = new TimerUtil();
    public Step() {
        super("Step", Category.MOVEMENT, new Color(102, 255, 51, 255).getRGB());
        setDescription("gain an extra leg");
        addValues(vanilla,height,delay);
    }
    @Override
    public void onDisable() {
        if (mc.thePlayer != null)
            mc.thePlayer.stepHeight = 0.6f;
    }
    @Handler
    public void onStep(StepEvent event) {
        if (mc.thePlayer == null || vanilla.isEnabled() || Client.INSTANCE.getModuleManager().getModule("speed").isEnabled() || Client.INSTANCE.getModuleManager().getModule("flight").isEnabled() || Client.INSTANCE.getModuleManager().getModule("speed").isEnabled() || Client.INSTANCE.getModuleManager().getModule("longjump").isEnabled()) return;
        if (!mc.thePlayer.isInLiquid()) if (event.isPre()) {
            if (mc.thePlayer.isCollidedVertically && !mc.gameSettings.keyBindJump.isKeyDown() && time.reach(delay.getValue())) {
                event.setHeight(height.getValue());
            } else {
                event.setHeight(0.6f);
            }
        }
        double rheight = mc.thePlayer.getEntityBoundingBox().minY - mc.thePlayer.posY;
        boolean canStep = rheight >= 0.625;
        if (canStep) {
            ncpStep(rheight);
            time.reset();
        }
    }
    @Handler
    public void onUpdate(UpdateEvent event) {
        setSuffix(vanilla.isEnabled()? "Vanilla":"NCP");
        if (vanilla.isEnabled())
            mc.thePlayer.stepHeight = height.getValue();
    }

    private void ncpStep(double height) {
        List<Float> offset = Arrays.asList(0.42f, 0.33F, 0.24F, 0.083F, -0.078F);
        double posX = mc.thePlayer.posX;
        double posZ = mc.thePlayer.posZ;
        double y = mc.thePlayer.posY;
        if (height < 1.1) {
            double first = 0.42;
            double second = 0.75;
            if (height != 1) {
                first *= height;
                second *= height;
                if (first > 0.425) {
                    first = 0.425;
                }
                if (second > 0.78) {
                    second = 0.78;
                }
                if (second < 0.49) {
                    second = 0.49;
                }
            }
            if (first == 0.42) first = 0.42;
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + first, posZ, false));
            if (y + second < y + height)
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + second, posZ, false));
            return;
        } else if (height < 1.6) {
            for (int i = 0; i < offset.size(); i++) {
                double off = offset.get(i);
                y += off;
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y, posZ, false));
            }
        } else if (height < 2.1) {
            double[] heights = {0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869};
            for (double off : heights) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + off, posZ, false));
            }
        } else {
            double[] heights = {0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907};
            for (double off : heights) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + off, posZ, false));
            }
        }
    }
}