package non.asset.module.impl.ghost;

import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import non.asset.event.bus.Handler;
import non.asset.event.impl.input.ClickMouseEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.CombatUtil;
import non.asset.utils.value.impl.NumberValue;

import java.awt.*;

public class Reach extends Module {
    private NumberValue<Float> range = new NumberValue<>("Range", 3.1F, 3F, 5.0F, 0.01F);

    public Reach() {
        super("Reach", Category.GHOST);
        setDescription("Change your reach");
    }

    @Handler
    public void onUpdate(ClickMouseEvent event) {
        final Object[] objects = CombatUtil.getEntityCustom(getMc().thePlayer.rotationPitch, getMc().thePlayer.rotationYaw, range.getValue(), 0, 0.0F);
        if (objects == null) {
            return;
        }
        getMc().objectMouseOver = new MovingObjectPosition((Entity) objects[0], (Vec3) objects[1]);
        getMc().pointedEntity = (Entity)objects[0];
    }
}