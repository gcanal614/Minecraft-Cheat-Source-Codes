/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.render;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.render.Render2DEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.utils.render.Colors;
import club.tifality.utils.render.RenderingUtils;
import java.awt.Color;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

@ModuleInfo(label="Radar", category=ModuleCategory.RENDER)
public class Radar
extends Module {
    private final DoubleProperty scale = new DoubleProperty("Scale", 2.0, 1.0, 5.0, 0.5);
    private final DoubleProperty x = new DoubleProperty("X", 650.0, 1.0, 1920.0, 5.0);
    private final DoubleProperty y = new DoubleProperty("Y", 2.0, 0.0, 1080.0, 5.0);
    private final DoubleProperty size = new DoubleProperty("Size", 125.0, 50.0, 500.0, 5.0);
    private boolean dragging;
    float hue;

    @Listener
    public void onRender2D(Render2DEvent e) {
        ScaledResolution sr = new ScaledResolution(mc);
        int size1 = ((Double)this.size.getValue()).intValue();
        float xOffset = ((Double)this.x.getValue()).floatValue();
        float yOffset = ((Double)this.y.getValue()).floatValue();
        float playerOffsetX = (float)Radar.mc.thePlayer.posX;
        float playerOffSetZ = (float)Radar.mc.thePlayer.posZ;
        int var141 = sr.getScaledWidth();
        int var151 = sr.getScaledHeight();
        int mouseX = Mouse.getX() * var141 / Radar.mc.displayWidth;
        int mouseY = var151 - Mouse.getY() * var151 / Radar.mc.displayHeight - 1;
        if ((float)mouseX >= xOffset && (float)mouseX <= xOffset + (float)size1 && (float)mouseY >= yOffset - 3.0f && (float)mouseY <= yOffset + 10.0f && Mouse.getEventButton() == 0) {
            if (this.dragging && Radar.mc.currentScreen instanceof GuiChat) {
                Object newValue = this.castNumber(Double.toString((float)mouseX - (float)size1 / 2.0f), 5);
                this.x.setValue((Double)newValue);
                Object newValueY = this.castNumber(Double.toString(mouseY - 2), 5);
                this.y.setValue((Double)newValueY);
            } else {
                this.dragging = false;
            }
        }
        if (this.hue > 255.0f) {
            this.hue = 0.0f;
        }
        float h = this.hue;
        float h2 = this.hue + 85.0f;
        float h3 = this.hue + 170.0f;
        if (h > 255.0f) {
            h = 0.0f;
        }
        if (h2 > 255.0f) {
            h2 -= 255.0f;
        }
        if (h3 > 255.0f) {
            h3 -= 255.0f;
        }
        Color color33 = Color.getHSBColor(h / 255.0f, 0.9f, 1.0f);
        Color color34 = Color.getHSBColor(h2 / 255.0f, 0.9f, 1.0f);
        Color color35 = Color.getHSBColor(h3 / 255.0f, 0.9f, 1.0f);
        int color36 = color33.getRGB();
        int color37 = color34.getRGB();
        int color38 = color35.getRGB();
        this.hue = (float)((double)this.hue + 0.1);
        RenderingUtils.rectangleBordered(xOffset, yOffset, (double)xOffset + (Double)this.size.get(), (double)yOffset + (Double)this.size.get(), 0.5, Colors.getColor(90), Colors.getColor(0));
        RenderingUtils.rectangleBordered(xOffset + 1.0f, yOffset + 1.0f, (double)xOffset + (Double)this.size.get() - 1.0, (double)yOffset + (Double)this.size.get() - 1.0, 1.0, Colors.getColor(90), Colors.getColor(61));
        RenderingUtils.rectangleBordered((double)xOffset + 2.5, (double)yOffset + 2.5, (double)xOffset + (Double)this.size.get() - 2.5, (double)yOffset + (Double)this.size.get() - 2.5, 0.5, Colors.getColor(61), Colors.getColor(0));
        RenderingUtils.rectangleBordered(xOffset + 3.0f, yOffset + 3.0f, (double)xOffset + (Double)this.size.get() - 3.0, (double)yOffset + (Double)this.size.get() - 3.0, 0.5, Colors.getColor(27), Colors.getColor(61));
        RenderingUtils.drawGradientSideways(xOffset + 3.0f, yOffset + 3.0f, (double)xOffset + (Double)this.size.get() / 2.0, (double)yOffset + 3.6, color36, color37);
        RenderingUtils.drawGradientSideways((double)xOffset + (Double)this.size.get() / 2.0, yOffset + 3.0f, (double)xOffset + (Double)this.size.get() - 3.0, (double)yOffset + 3.6, color37, color38);
        RenderingUtils.rectangle((double)xOffset + ((Double)this.size.get() / 2.0 - 0.5), (double)yOffset + 3.5, (double)xOffset + ((Double)this.size.get() / 2.0 + 0.5), (double)yOffset + (Double)this.size.get() - 3.5, Colors.getColor(255, 80));
        RenderingUtils.rectangle((double)xOffset + 3.5, (double)yOffset + ((Double)this.size.get() / 2.0 - 0.5), (double)xOffset + (Double)this.size.get() - 3.5, (double)yOffset + ((Double)this.size.get() / 2.0 + 0.5), Colors.getColor(255, 80));
        for (Entity o : Radar.mc.theWorld.getLoadedEntityList()) {
            EntityPlayer ent;
            if (!(o instanceof EntityPlayer) || !(ent = (EntityPlayer)o).isEntityAlive() || ent == Radar.mc.thePlayer || ent.isInvisible() || ent.isInvisibleToPlayer(Radar.mc.thePlayer)) continue;
            float pTicks = Radar.mc.timer.renderPartialTicks;
            float posX = (float)((ent.posX + (ent.posX - ent.lastTickPosX) * (double)pTicks - (double)playerOffsetX) * (Double)this.scale.getValue());
            float posZ = (float)((ent.posZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - (double)playerOffSetZ) * (Double)this.scale.getValue());
            String formattedText = ent.getDisplayName().getFormattedText();
            int color = Radar.mc.thePlayer.canEntityBeSeen(ent) ? new Color(255, 255, 255).getRGB() : new Color(120, 120, 120).getRGB();
            for (int i = 0; i < formattedText.length(); ++i) {
                int index;
                if (formattedText.charAt(i) != '\u00a7' || i + 1 >= formattedText.length() || (index = "0123456789abcdefklmnorg".indexOf(Character.toLowerCase(formattedText.charAt(i + 1)))) >= 16) continue;
                try {
                    Color color21 = new Color(Radar.mc.fontRendererObj.colorCode[index]);
                    color = this.getColor(color21.getRed(), color21.getGreen(), color21.getBlue(), 255);
                    continue;
                }
                catch (ArrayIndexOutOfBoundsException color21) {
                    // empty catch block
                }
            }
            if (ent.hurtTime > 0) {
                color = new Color(255, 0, 0).getRGB();
            }
            float cos = (float)Math.cos((double)Radar.mc.thePlayer.rotationYaw * (Math.PI / 180));
            float sin = (float)Math.sin((double)Radar.mc.thePlayer.rotationYaw * (Math.PI / 180));
            float rotY = -posZ * cos - posX * sin;
            float rotX = -posX * cos + posZ * sin;
            if (rotY > (float)(size1 / 2 - 5)) {
                rotY = (float)(size1 / 2) - 5.0f;
            } else if (rotY < (float)(-size1 / 2 - 5)) {
                rotY = -size1 / 2 - 5;
            }
            if (rotX > (float)(size1 / 2) - 5.0f) {
                rotX = size1 / 2 - 5;
            } else if (rotX < (float)(-size1 / 2 - 5)) {
                rotX = (float)(-(size1 / 2)) - 5.0f;
            }
            RenderingUtils.rectangleBordered((double)(xOffset + 4.0f + (float)(size1 / 2) + rotX) - 1.5, (double)(yOffset + 4.0f + (float)(size1 / 2) + rotY) - 1.5, (double)(xOffset + 4.0f + (float)(size1 / 2) + rotX) + 1.5, (double)(yOffset + 4.0f + (float)(size1 / 2) + rotY) + 1.5, 0.5, color, Colors.getColor(46));
        }
    }

    public int getColor(int p_clamp_int_0_, int p_clamp_int_0_2, int p_clamp_int_0_3, int p_clamp_int_0_4) {
        return MathHelper.clamp_int(p_clamp_int_0_4, 0, 255) << 24 | MathHelper.clamp_int(p_clamp_int_0_, 0, 255) << 16 | MathHelper.clamp_int(p_clamp_int_0_2, 0, 255) << 8 | MathHelper.clamp_int(p_clamp_int_0_3, 0, 255);
    }

    public Object castNumber(String newValueText, Object currentValue) {
        if (newValueText.contains(".")) {
            if (newValueText.toLowerCase().contains("f")) {
                return Float.valueOf(Float.parseFloat(newValueText));
            }
            return Double.parseDouble(newValueText);
        }
        if (this.isNumeric(newValueText)) {
            return Integer.parseInt(newValueText);
        }
        return newValueText;
    }

    public boolean isNumeric(String text) {
        try {
            Integer.parseInt(text);
            return true;
        }
        catch (NumberFormatException numberFormatException) {
            return false;
        }
    }
}

