// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.combat;

import net.minecraft.client.settings.KeyBinding;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Mouse;
import net.minecraft.client.Minecraft;
import bozoware.base.util.misc.TimerUtil;
import bozoware.impl.property.ValueProperty;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "AutoClicker", moduleCategory = ModuleCategory.COMBAT)
public class AutoClicker extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    private final ValueProperty<Integer> cpsValue;
    TimerUtil timer;
    
    public AutoClicker() {
        this.cpsValue = new ValueProperty<Integer>("CPS", 12, 1, 25, this);
        this.timer = new TimerUtil();
        this.onUpdatePositionEvent = (e -> {
            if (Minecraft.getMinecraft().currentScreen == null && Mouse.isButtonDown(0)) {
                if (this.timer.hasReached(1000 / RandomUtils.nextInt(this.cpsValue.getPropertyValue() - 2, (int)this.cpsValue.getPropertyValue()))) {
                    KeyBinding.setKeyBindState(-100, true);
                    KeyBinding.onTick(-100);
                    this.timer.reset();
                }
                else {
                    KeyBinding.setKeyBindState(-100, false);
                }
            }
        });
    }
}
