package non.asset.module.impl.visuals;

import java.awt.*;

import non.asset.event.bus.Handler;
import non.asset.event.impl.render.HurtcamEvent;
import non.asset.module.Module;

public class NoHurtCam extends Module {

    public NoHurtCam() {
        super("HurtCam", Category.VISUALS);
    }
    @Handler
    public void Hurtcam(HurtcamEvent event) {
        event.setCanceled(true);
    }
}
