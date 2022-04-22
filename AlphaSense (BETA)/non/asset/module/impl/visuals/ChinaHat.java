package non.asset.module.impl.visuals;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import non.asset.Clarinet;
import non.asset.event.bus.Handler;
import non.asset.event.impl.render.Render3DEvent;
import non.asset.module.Module;
import non.asset.module.impl.Combat.AntiBot;
import non.asset.utils.OFC.RenderUtil;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.NumberValue;

public class ChinaHat extends Module {
    private static Map<EntityPlayer, float[][]> entities = new HashMap<>();
    
    public boolean confirm; 
    

    private BooleanValue view = new BooleanValue("Render On First Person", false);
    
    private NumberValue<Float> red = new NumberValue<>("Red", 255f, 1f, 255f, 1f);
    private NumberValue<Float> green = new NumberValue<>("Green", 255f, 1f, 255f, 1f);
    private NumberValue<Float> blue = new NumberValue<>("Blue", 255f, 1f, 255f, 1f);

    
    public ChinaHat() {
        super("ChinaHat", Category.VISUALS);
        setRenderLabel("ChinaHat");
        setHidden(false);
    }
    @Handler
    public void onRender2D(Render3DEvent e) {
    	int aaa = new Color(red.getValue() / 255.0f, green.getValue() / 255.0f, blue.getValue() / 255.0f, 0.1f).getRGB();
    	if (this.mc.gameSettings.thirdPersonView != 0 || view.isEnabled()) {
    		for (int i = 0; i < 400; ++i) {
                drawHat((Entity)mc.thePlayer, i * 0.0014, 0, 20, 2.0f, 2.2f - i * 7.85E-4f - 0f, aaa);
            }
        }
    }
    
    public static void drawHat(final Entity entity, final double radius, final float partialTicks, final int points, final float width, final float yAdd, final int color) {
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glDepthMask(false);
            GL11.glLineWidth(width);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2929);
            GL11.glBegin(3);
            double x = RenderUtil.interpolate((entity.posX - entity.lastTickPosX), entity.prevPosX, partialTicks) - RenderManager.getRenderPosX();
            double y = RenderUtil.interpolate((entity.posY - entity.lastTickPosY) + yAdd * partialTicks, entity.prevPosY + yAdd * partialTicks, partialTicks) - RenderManager.getRenderPosY();
            double z = RenderUtil.interpolate((entity.posZ - entity.lastTickPosZ), entity.prevPosZ, partialTicks) - RenderManager.getRenderPosZ();
            GL11.glColor4f(new Color(color).getRed() / 255.0f, new Color(color).getGreen() / 255.0f, new Color(color).getBlue() / 255.0f, new Color(color).getAlpha() / 255.0f);
            for (int i = 0; i <= points; ++i) {
                GL11.glVertex3d(x * partialTicks + radius * Math.cos(i * 3.141592653589793 * 2.0 / points), y * partialTicks + yAdd, z * partialTicks + radius * Math.sin(i * 3.141592653589793 * 2.0 / points));
    		}
            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glEnable(2929);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
            
    }
    

    private Vec3 getVec3(Render3DEvent event, EntityPlayer var0) {
        float pt = event.getPartialTicks();
        double x = var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * pt;
        double y = var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * pt;
        double z = var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * pt;
        return new Vec3(x, y, z);
    }

    public static void addEntity(EntityPlayer e, ModelPlayer model) {
        entities.put(e, new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ}, {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ}, {model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ}, {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}});
    }

    private boolean doesntContain(EntityPlayer var0) {
        return !getMc().theWorld.playerEntities.contains(var0);
    }

    private void startEnd(boolean revert) {
        if (revert) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GL11.glEnable(2848);
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GL11.glHint(3154, 4354);
        } else {
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GL11.glDisable(2848);
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
        GlStateManager.depthMask(!revert);
    }
}