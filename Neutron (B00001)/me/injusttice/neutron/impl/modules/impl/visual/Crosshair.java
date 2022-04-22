package me.injusttice.neutron.impl.modules.impl.visual;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.Event2D;
import me.injusttice.neutron.api.events.impl.EventCrosshair;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.utils.astolfo.AstolfoUtils;
import me.injusttice.neutron.utils.movement.MovementUtils;
import me.injusttice.neutron.utils.render.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class Crosshair extends Module {


    public BooleanSet dynamic = new BooleanSet("Dynamic", true);
    public DoubleSet gap = new DoubleSet("Gap", 2.0F, 0.5F, 10.0F, 1.0F);
    public DoubleSet width = new DoubleSet("Width",  0.75F, 0.75F, 10.0F, 1.0F);
    public DoubleSet length = new DoubleSet("Length", 5.0F, 0.5F, 100.0F, 1.0F);
    public DoubleSet dynamicgap = new DoubleSet("DynamicGap", 2.0F, 0.5F, 10.0F, 1.0F);
    public BooleanSet rainbow = new BooleanSet("Rainbow", true);
    public BooleanSet staticRainbow = new BooleanSet("Static Rainbow", true);

    public Crosshair() {
        super("Crosshair", 0, Category.VISUAL);
        addSettings(dynamic, gap, width, length, dynamicgap, rainbow, staticRainbow);
    }

    @EventTarget
    public void onCrosshair(EventCrosshair event) {
        event.setCancelled(true);
    }

    @EventTarget
    public void onRender2D(Event2D event) {
        ScaledResolution sr = new ScaledResolution(mc);

        int count = 0;
        int color = staticRainbow.isEnabled() ? color(2, 100) : (int) (rainbow.isEnabled() ? AstolfoUtils.rainbow(count * -100, 1f, 0.47f) : -1);

        final double middlex = sr.getScaledWidth() / 2;
        final double middley = sr.getScaledHeight() / 2;

        RenderUtil.drawBordered(middlex - (width.getValue()), middley - (gap.getValue() + length.getValue()) - ((MovementUtils.isMoving() && dynamic.isEnabled()) ? dynamicgap.getValue() : 0), middlex + (width.getValue()), middley - (gap.getValue()) - ((MovementUtils.isMoving() && dynamic.isEnabled()) ? dynamicgap.getValue() : 0), 0.5, color, 0xff000000);
        RenderUtil.drawBordered(middlex - (width.getValue()), middley + (gap.getValue()) + ((MovementUtils.isMoving() && dynamic.isEnabled()) ? dynamicgap.getValue() : 0), middlex + (width.getValue()), middley + (gap.getValue() + length.getValue()) + ((MovementUtils.isMoving() && dynamic.isEnabled()) ? dynamicgap.getValue() : 0), 0.5, color, 0xff000000);
        RenderUtil.drawBordered(middlex - (gap.getValue() + length.getValue()) - ((MovementUtils.isMoving() && dynamic.isEnabled()) ? dynamicgap.getValue() : 0), middley - (width.getValue()), middlex - (gap.getValue()) - ((MovementUtils.isMoving() && dynamic.isEnabled()) ? dynamicgap.getValue() : 0), middley + (width.getValue()), 0.5, color, 0xff000000);
        RenderUtil.drawBordered(middlex + (gap.getValue()) + ((MovementUtils.isMoving() && dynamic.isEnabled()) ? dynamicgap.getValue() : 0), middley - (width.getValue()), middlex + (gap.getValue() + length.getValue()) + ((MovementUtils.isMoving() && dynamic.isEnabled()) ? dynamicgap.getValue() : 0), middley + (width.getValue()), 0.5, color, 0xff000000);
    }
    public int color(int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(new Color(255,255,255,255).getRed(),new Color(255,255,255,255).getGreen(),new Color(255,255,255,255).getBlue(), hsb);

        float brightness = Math.abs(((getOffset() + (index / (float) count) * 2) % 2) - 1);

        brightness = 0.4f + (0.4f * brightness);
        hsb[2] = brightness % 1f;
        Color clr = new Color(Color.HSBtoRGB(hsb[0],hsb[1], hsb[2]));
        return new Color(clr.getRed(),clr.getGreen(),clr.getBlue(),new Color(255,255,255,255).getAlpha()).getRGB();
    }

    private float getOffset() {
        return (System.currentTimeMillis() % 2000) / 1000f;
    }
}
