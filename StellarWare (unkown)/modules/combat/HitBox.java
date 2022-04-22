package stellar.skid.modules.combat;

import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.gui.screen.setting.SettingType;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.configurations.annotation.Property;
import stellar.skid.modules.configurations.property.object.FloatProperty;
import org.checkerframework.checker.nullness.qual.NonNull;

import static stellar.skid.modules.configurations.property.object.PropertyFactory.createFloat;

public final class HitBox extends AbstractModule {

    /* properties @off */
    @Property("size")
    private final FloatProperty hitBoxSize = createFloat(0.3F).minimum(0.1F).maximum(1.0F);

    /* constructors @on */
    public HitBox(@NonNull ModuleManager moduleManager) {
        super(moduleManager, EnumModuleType.COMBAT, "HitBox", "Hit Box");
        Manager.put(new Setting("ENTITY_BOX", "Box Size", SettingType.SLIDER, this, this.hitBoxSize, 0.1F));
    }

    public FloatProperty getHitBoxSize() {
        return hitBoxSize;
    }
}

