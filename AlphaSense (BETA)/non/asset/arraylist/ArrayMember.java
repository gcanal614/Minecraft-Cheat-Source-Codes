package non.asset.arraylist;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import non.asset.Clarinet;
import non.asset.module.Module;
import non.asset.module.impl.visuals.HUD;
import non.asset.utils.AnimationUtils;
import non.asset.utils.RenderUtil;
import non.asset.utils.OFC.TimerUtil;

public class ArrayMember {
    private static Minecraft mc = Minecraft.getMinecraft();
    private float x, oldX, y, oldY, width;
    private boolean done, start;
    private Module module;
    private TimerUtil timer = new TimerUtil();

    public ArrayMember(Module module) {
        final HUD hud = (HUD) Clarinet.INSTANCE.getModuleManager().getModule("hud");
        this.x = new ScaledResolution(mc).getScaledWidth() - 2;
        this.width = (hud.font.isEnabled() ?  hud.fontValue.getValue().getStringWidth(getModuleRenderString(module)):mc.fontRendererObj.getStringWidth(getModuleRenderString(module)));
        this.module = module;
        done = false;
    }

    public void draw(ArrayList<ArrayMember> arrayMembers, float startY, float prevY) {
        if (!start) {
            y = startY;
            start = true;
        }
        final HUD hud = (HUD) Clarinet.INSTANCE.getModuleManager().getModule("hud");
        final float xSpeed = width / (Minecraft.getDebugFPS() / 4);
        final float ySpeed = (new ScaledResolution(mc).getScaledHeight() - prevY) / (Minecraft.getDebugFPS() * 2);
        if (width != (hud.font.isEnabled() ?  hud.fontValue.getValue().getStringWidth(getModuleRenderString(module)):mc.fontRendererObj.getStringWidth(getModuleRenderString(module))))
        width = (hud.font.isEnabled() ?  hud.fontValue.getValue().getStringWidth(getModuleRenderString(module)):mc.fontRendererObj.getStringWidth(getModuleRenderString(module)));
        if (done) {
            oldX = x;
            oldY = y;
            x += xSpeed;
            y += ySpeed;
        }
        
        if (!done() && !done) {
            
        	oldX = x;
            
            if (x <= new ScaledResolution(mc).getScaledWidth() - 2 - width + xSpeed) {
                x = (float) AnimationUtils.animate(x, new ScaledResolution(mc).getScaledWidth() - 2 - width, 0.9);
            }else x -= xSpeed;
        }
        if (x < new ScaledResolution(mc).getScaledWidth() - 2 - width) {
            oldX = x;

            x = (float) AnimationUtils.animate(x, new ScaledResolution(mc).getScaledWidth() - 2 - width, 0.9);
            
        }

        y = (float) AnimationUtils.animate(y, prevY, 0.7);
        
        if (!module.isEnabled() || module.isHidden()) done = true;
        float finishedX = oldX + (x - oldX) - 7;
        float finishedY = oldY + (y - oldY) + 7;
        
        int color = hud.getGradientOffset(new Color(hud.Start.getColor().getRed(), hud.Start.getColor().getGreen(), hud.Start.getColor().getBlue()), new Color(hud.Finish.getColor().getRed(), hud.Finish.getColor().getGreen(), hud.Finish.getColor().getBlue()), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D) 
                + (finishedY / ((hud.font.isEnabled() ? hud.fontValue.getValue().getHeight() + 6 : mc.fontRendererObj.FONT_HEIGHT + 4) / 2))
                ).getRGB();
        
        switch (hud.BORDERMODE.getValue()) {
            case NONE:
                if (hud.background.isEnabled()) {
                    RenderUtil.drawRect(finishedX - 1, finishedY - 3, width + 4, hud.fontValue.getValue().getHeight() + 4, new Color(0, 0, 0, hud.backgroundAlpha.getValue()).getRGB());
                }
                break;
            case RIGHT:
                RenderUtil.drawRect(finishedX + (hud.font.isEnabled() ? hud.fontValue.getValue().getStringWidth(getModuleRenderString(module)) : mc.fontRendererObj.getStringWidth(getModuleRenderString(module))) + 2 - hud.borderWidth.getValue(), finishedY - 3, hud.borderWidth.getValue(), hud.fontValue.getValue().getHeight() + 4, color);
                finishedX -= hud.font.isEnabled() ? 4 : 2;
                if (hud.background.isEnabled()) {
                    RenderUtil.drawRect(finishedX - hud.borderWidth.getValue(), finishedY - 3, (hud.font.isEnabled() ? hud.fontValue.getValue().getStringWidth(getModuleRenderString(module)) : mc.fontRendererObj.getStringWidth(getModuleRenderString(module))) + (hud.font.isEnabled() ? 6 : 4), hud.fontValue.getValue().getHeight() + 4, new Color(0, 0, 0, hud.backgroundAlpha.getValue()).getRGB());
                }
                break;
            case LEFT:
                if (hud.background.isEnabled()) {
                    RenderUtil.drawRect(finishedX - 2, finishedY - 3, (hud.font.isEnabled() ? hud.fontValue.getValue().getStringWidth(getModuleRenderString(module)) : mc.fontRendererObj.getStringWidth(getModuleRenderString(module))) + 4, hud.fontValue.getValue().getHeight() + 4, new Color(0, 0, 0, hud.backgroundAlpha.getValue()).getRGB());
                }
                RenderUtil.drawRect(finishedX - 2 - hud.borderWidth.getValue(), finishedY - 3, hud.borderWidth.getValue(), hud.fontValue.getValue().getHeight() + 4, color);
                break;
            case WRAPPERLEFT:
                if (hud.background.isEnabled()) {
                    RenderUtil.drawRect(finishedX - 2, finishedY - 3, width + 4, hud.fontValue.getValue().getHeight() + 4, new Color(0, 0, 0, hud.backgroundAlpha.getValue()).getRGB());
                }
                RenderUtil.drawRect(finishedX - 2 - hud.borderWidth.getValue(), finishedY - 3, hud.borderWidth.getValue(), hud.fontValue.getValue().getHeight() + 4, color);
                if (arrayMembers.indexOf(this) == arrayMembers.size() - 1)
                    RenderUtil.drawRect(finishedX - 2 - hud.borderWidth.getValue(), finishedY + hud.fontValue.getValue().getHeight() + 1, (hud.font.isEnabled() ? hud.fontValue.getValue().getStringWidth(getModuleRenderString(module)) : mc.fontRendererObj.getStringWidth(getModuleRenderString(module))) + 4 + hud.borderWidth.getValue(), hud.borderWidth.getValue() + 0.25f, color);
                else {
                    final Module nextMod = arrayMembers.get(arrayMembers.indexOf(this) + 1).getModule();
                    final float dist = (hud.font.isEnabled() ? hud.fontValue.getValue().getStringWidth(getModuleRenderString(module)) - hud.fontValue.getValue().getStringWidth(getModuleRenderString(nextMod)) : mc.fontRendererObj.getStringWidth(getModuleRenderString(module)) - mc.fontRendererObj.getStringWidth(getModuleRenderString(nextMod)));
                    RenderUtil.drawRect(finishedX - 2 - hud.borderWidth.getValue(), finishedY + hud.fontValue.getValue().getHeight() + 1, hud.borderWidth.getValue() + dist, hud.borderWidth.getValue() + 0.25f, color);
                }
                break;
        }
        if (hud.font.isEnabled())
            hud.fontValue.getValue().drawStringWithShadow(getModuleRenderString(module), finishedX - (hud.BORDERMODE.getValue() == HUD.bordermode.RIGHT ? hud.borderWidth.getValue() - 2 : 0), finishedY - 1, color);
        else
            mc.fontRendererObj.drawStringWithShadow(getModuleRenderString(module), finishedX - (hud.BORDERMODE.getValue() == HUD.bordermode.RIGHT ? hud.borderWidth.getValue() - 2 : 0), finishedY - 1, color);
        if (delete()) {
            Clarinet.INSTANCE.getArraylistManager().getArrayMembers().remove(this);
        }
    }
    
    private Color getGradientOffset(Color color1, Color color2, double offset) {
        if (offset > 1) {
            double left = offset % 1;
            int off = (int) offset;
            offset = off % 2 == 0 ? left : 1 - left;

        }
        double inverse_percent = 1 - offset;
        int redPart = (int) (color1.getRed() * inverse_percent + color2.getRed() * offset);
        int greenPart = (int) (color1.getGreen() * inverse_percent + color2.getGreen() * offset);
        int bluePart = (int) (color1.getBlue() * inverse_percent + color2.getBlue() * offset);
        return new Color(redPart, greenPart, bluePart);
    }

    private String getModuleRenderString(Module module) {
        final HUD hud = (HUD) Clarinet.INSTANCE.getModuleManager().getModule("hud");
        if (hud.font.isEnabled()) {
        	
        	return Objects.nonNull(module.getRenderLabel()) ? (module.getRenderLabel() + (Objects.nonNull(module.getSuffix()) ? " " + module.getSuffix() : "")) : (module.getLabel() + (Objects.nonNull(module.getSuffix()) ?  " " + module.getSuffix() : ""));
        	
        }else {
        	return Objects.nonNull(module.getRenderLabel()) ? (module.getRenderLabel() + (Objects.nonNull(module.getSuffix()) ? EnumChatFormatting.GRAY +  " " + module.getSuffix() : "")) : (module.getLabel() + (Objects.nonNull(module.getSuffix()) ? EnumChatFormatting.GRAY +  " " + module.getSuffix() : ""));
        }
    }

    public boolean done() {
        return x <= new ScaledResolution(mc).getScaledWidth() - 7 - width;
    }

    public boolean delete() {
        return x >= new ScaledResolution(mc).getScaledWidth() - 7 && done;
    }

    public Module getModule() {
        return module;
    }
}
