package stellar.skid.modules.combat;

import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.configurations.annotation.Property;
import stellar.skid.modules.configurations.property.object.FloatProperty;
import org.checkerframework.checker.nullness.qual.NonNull;

import static stellar.skid.gui.screen.setting.SettingType.SLIDER;
import static stellar.skid.modules.configurations.property.object.PropertyFactory.createFloat;

public final class Reach extends AbstractModule {

    /* properties @off */
    @Property("range")
    private final FloatProperty range = createFloat(5.0F).minimum(3.0F).maximum(5.0F);

    private boolean hittingBlock;

    /* constructors @on */
    public Reach(@NonNull ModuleManager moduleManager) {
        super(moduleManager, "Reach", EnumModuleType.COMBAT, "Expands reach");
        Manager.put(new Setting("Reach_Distance", "Range", SLIDER, this, this.range, 0.1D));
    }

    public float getRange() {
        return range.get();
    }
}
