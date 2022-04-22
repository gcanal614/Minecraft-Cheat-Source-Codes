package non.asset.module.impl.visuals;

import java.awt.*;

import non.asset.module.Module;

public class FullBright extends Module {
    private float oldGamma;

    public FullBright() {
        super("FullBright", Category.VISUALS);
        setRenderLabel("FullBright");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.oldGamma = getMc().gameSettings.gammaSetting;
        getMc().gameSettings.gammaSetting = 2000f;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getMc().gameSettings.gammaSetting = this.oldGamma;
    }
}
