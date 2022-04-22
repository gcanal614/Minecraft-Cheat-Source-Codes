package me.injusttice.neutron.impl.modules.impl.visual;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventTick;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import net.minecraft.entity.item.EntityItem;

public class NoRender extends Module {

    public NoRender(){
        super("NoRender", 0, Category.VISUAL);
    }

    @EventTarget
    public void onUpdate(EventTick e) {
        this.setDisplayName("No Render");
        for (Object o : mc.theWorld.loadedEntityList) {
            if ((o instanceof EntityItem)) {
                EntityItem i = (EntityItem) o;
                mc.theWorld.removeEntity(i);
            }
        }
    }
}
