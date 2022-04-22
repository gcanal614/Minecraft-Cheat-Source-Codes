package me.injusttice.neutron.impl.modules.impl.ghost;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.api.events.impl.EventRender2D;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Mouse;

import java.util.concurrent.ThreadLocalRandom;

public class AutoClicker extends Module {

    public DoubleSet aps = new DoubleSet("Speed", 10.0, 1.0, 20.0, 0.5);
    public BooleanSet jitterSet = new BooleanSet("Jitter", false);
    private double holdLength;
    private double speed;
    private long lastClick;
    private long hold;

    public AutoClicker() {
        super("AutoClicker", 0, Category.GHOST);
        addSettings(aps, jitterSet);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        update();
    }

    @EventTarget
    public void onRender(EventRender2D e) {
        if (Mouse.isButtonDown(0))
            if ((System.currentTimeMillis() - lastClick) > speed * 1000.0D) {
                lastClick = System.currentTimeMillis();
                if (hold < lastClick)
                    hold = lastClick;
                int key = mc.gameSettings.keyBindAttack.getKeyCode();
                KeyBinding.setKeyBindState(key, true);
                KeyBinding.onTick(key);
                update();
            } else if ((System.currentTimeMillis() - hold) > holdLength * 1000.0D) {
                int key = mc.gameSettings.keyBindAttack.getKeyCode();
                KeyBinding.setKeyBindState(key, false);
                update();
            }
    }

    @EventTarget
    public void onMotion(EventMotion e) {
        setDisplayName("AutoClicker §7" + Math.round(aps.getValue()));
        if (Mouse.isButtonDown(0) && mc.currentScreen == null && jitterSet.isEnabled()) {
            mc.thePlayer.rotationYaw = (float)(mc.thePlayer.rotationYaw + ThreadLocalRandom.current().nextDouble(-0.5D, 0.5D));
            mc.thePlayer.rotationPitch = (float)(mc.thePlayer.rotationPitch + ThreadLocalRandom.current().nextDouble(-0.5D, 0.5D));
        }
    }

    private void update() {
        double max = aps.getValue();
        double min = aps.getValue() - 6.5D;
        speed = 1.0D / ThreadLocalRandom.current().nextDouble(min - 0.15D, max);
        holdLength = speed / ThreadLocalRandom.current().nextDouble(min, max);
    }
}
