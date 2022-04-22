/*
package cc.stellarWare.modules.misc;

import cc.stellarWare.events.EventTarget;
import cc.stellarWare.events.events.PacketEvent;
import cc.stellarWare.events.events.MotionUpdateEvent;
import cc.stellarWare.gui.screen.setting.Manager;
import cc.stellarWare.gui.screen.setting.Setting;
import cc.stellarWare.gui.screen.setting.SettingType;
import cc.stellarWare.modules.AbstractModule;
import cc.stellarWare.modules.EnumModuleType;
import cc.stellarWare.modules.ModuleManager;
import cc.stellarWare.modules.configurations.annotation.Property;
import cc.stellarWare.modules.configurations.property.object.BooleanProperty;
import cc.stellarWare.modules.configurations.property.object.FloatProperty;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.checkerframework.checker.nullness.qual.NonNull;

import static cc.stellarWare.modules.configurations.property.object.PropertyFactory.booleanTrue;
import static cc.stellarWare.modules.configurations.property.object.PropertyFactory.createFloat;

public final class GameSpeed extends AbstractModule {

    */
/* properties @off *//*

    @Property("timer-speed")
    private final FloatProperty timerSpeed = createFloat(1.0F).minimum(0.3F).maximum(5.0F);
    @Property("lagback")
    private final BooleanProperty lagback = booleanTrue();

    */
/* constructors @on *//*

    public GameSpeed(@NonNull ModuleManager moduleManager) {
        super(moduleManager, EnumModuleType.MISC, "GameSpeed", "Game Speed");
        Manager.put(new Setting("TIMERSPEED", "Timer Speed", SettingType.SLIDER, this, this.timerSpeed, 0.05));
        Manager.put(new Setting("GS_LAGBACK", "Lagback check", SettingType.CHECKBOX, this, lagback));
    }

    */
/* methods *//*

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1;
    }

    */
/* events *//*

    @EventTarget
    public void onPreUpdate(MotionUpdateEvent event) {
        if(event.getState().equals(MotionUpdateEvent.State.PRE)) {
            mc.timer.timerSpeed = timerSpeed.get();
        }
    }

    @EventTarget
    public void onReceive(PacketEvent event) {
        if (event.getState().equals(PacketEvent.State.INCOMING)) {
            if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                if (lagback.get()) {
                    checkModule(getClass());
                }
            }
        }
    }

}
*/
