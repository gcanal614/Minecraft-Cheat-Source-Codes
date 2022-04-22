package me.injusttice.neutron.impl.modules.impl.ghost;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventClickMouse;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.utils.combat.CombatUtil;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Reach extends Module {

    public DoubleSet reachDistance = new DoubleSet("Reach Distance", 5.0, 4.5, 6.0, 0.05);

    public Reach() {
        super("Reach", 0, Category.GHOST);
        addSettings(reachDistance);
    }

    @EventTarget
    public void onUpdate() {
        this.setDisplayName("Reach §7" + reachDistance.getValue());
    }

    @EventTarget
    public void onClick(EventClickMouse event) {
        Object[] objects = CombatUtil.getEntityCustom(mc.thePlayer.rotationPitch, mc.thePlayer.rotationYaw, reachDistance.getValue(), 0, 0.0F);
        if (objects == null) {
            return;
        }
        mc.objectMouseOver = new MovingObjectPosition((Entity) objects[0], (Vec3) objects[1]);
        mc.pointedEntity = (Entity)objects[0];
    }
}
