package me.module.impl.render;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import me.Hime;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import me.event.impl.EventRenderHUD;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;



public class ESP2d extends Module{
public Setting one;
	public double health = 0;
	public Setting mode;
	public Setting healthbar;
	public Setting rects;
	public Setting hue;
	public Setting sat;
	public Setting armor;
	public Setting bright;
	public final List<Entity> collectedEntities = new ArrayList<>();
	public ESP2d() {
		super("2DESP",Keyboard.KEY_NONE, Category.RENDER);
		
		    ArrayList<String> options = new ArrayList<>();
	        options.add("Full");
	        options.add("Corners");
	        Hime.instance.settingsManager.rSetting(mode = new Setting("ESP2D Mode", this, "Corners", options));
	        Hime.instance.settingsManager.rSetting(healthbar = new Setting("Healthbar", this, true));
	        Hime.instance.settingsManager.rSetting(rects = new Setting("ESP Rects", this, true));
	        Hime.instance.settingsManager.rSetting(armor = new Setting("Armor Show", this, false));
	  	    Hime.instance.settingsManager.rSetting(hue = new Setting("Outline Hue", this, 20, 0, 255));
		    Hime.instance.settingsManager.rSetting(sat = new Setting("Outline Brightness", this, 1, -20, 5, 1));
		    Hime.instance.settingsManager.rSetting(bright = new Setting("Outline Saturation", this, 1, -20, 5, "eeee"));
	}



	   private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
	    private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
	    private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
	    private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
	    private final static Frustum frustrum = new Frustum();
	    
	    public static double interpolate(double current, double old, double scale) {
	        return old + (current - old) * scale;
	    }
	    
	    public static boolean isInViewFrustrum(Entity entity) {
	        return isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
	    }

	    private static boolean isInViewFrustrum(AxisAlignedBB bb) {
	        Entity current = Minecraft.getMinecraft().getRenderViewEntity();
	        frustrum.setPosition(current.posX, current.posY, current.posZ);
	        return frustrum.isBoundingBoxInFrustum(bb);
	    }


	/*Credit to someone for the esp base i forgot who lol*/
    @Handler
    public void onRenderUtil(EventRenderHUD event) {
    	ScaledResolution scaledResolution = new ScaledResolution(mc);
        GL11.glPushMatrix();
        collectEntities();
        final float partialTicks = mc.timer.renderPartialTicks;
 
        final int scaleFactor = scaledResolution.getScaleFactor();
        final double scaling = scaleFactor / Math.pow(scaleFactor, 3.0D);
        GL11.glScaled(scaling, scaling, scaling);
        final int black = Color.BLACK.getRGB();
        final int background = new Color(0, 0, 0, 150).getRGB();
        float scale = 0.65F;
        final RenderManager renderMng = mc.getRenderManager();
        final EntityRenderer entityRenderer = mc.entityRenderer;
        final boolean outline = this.rects.getValBoolean();
        final boolean health = this.healthbar.getValBoolean();
   //     final boolean armor = this.armorBar.isEnabled();



        final List<Entity> collectedEntities = this.collectedEntities;
        for (final Entity entity : collectedEntities) {
            if (entity != mc.thePlayer && this.isInViewFrustrum(entity) && !entity.isInvisible()) {
                final double x = this.interpolate(entity.posX, entity.lastTickPosX, partialTicks);
                final double y = this.interpolate(entity.posY, entity.lastTickPosY, partialTicks);
                final double z = this.interpolate(entity.posZ, entity.lastTickPosZ, partialTicks);
                final double width = entity.width / 1.5;
                final double height = entity.height + (entity.isSneaking() ? -0.3 : 0.2);

                final AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
                final List<Vector3d> vectors = Arrays.asList(new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ),
                        new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ),
                        new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ),
                        new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ));
                entityRenderer.setupCameraTransform(partialTicks, 0);
                Vector4d position = null;

                for (Vector3d vector : vectors) {
                    vector = project2D(scaleFactor, vector.x - renderMng.viewerPosX, vector.y - renderMng.viewerPosY, vector.z - renderMng.viewerPosZ);

                    if (vector != null && vector.z >= 0.0 && vector.z < 1.0) {
                        if (position == null) {
                            position = new Vector4d(vector.x, vector.y, vector.z, 0.0);
                        }

                        position.x = Math.min(vector.x, position.x);
                        position.y = Math.min(vector.y, position.y);
                        position.z = Math.max(vector.x, position.z);
                        position.w = Math.max(vector.y, position.w);
                    }
                }

                if (position != null) {
                    entityRenderer.setupOverlayRendering(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
                    final double posX = position.x;
                    final double posY = position.y;
                    final double endPosX = position.z;
                    final float endPosY = (float) position.w;


                    int count = 0;
                    
                    final float hue = (float) (this.hue.getValDouble() / 255);
              	    //                           colorSaturation  colorBrightness 
                    Color color2 = Color.getHSBColor(hue, (float) bright.getValDouble(), (float) sat.getValDouble());
                    
                    int color = color2.getRGB();

                    if (outline) {
                        switch (mode.getValString()) {
                            case "Full":
                            	//
                                Gui.drawRect(posX - .5, posY, posX + 0.3D - .5,  
                                		endPosY, color);
                                
                                //
                                Gui.drawRect(posX, endPosY - 0.3D, endPosX,
                                		endPosY, color);
                                
                                //
                                Gui.drawRect(posX - .5, posY, endPosX, 
                                		posY + 0.3D, color);
                                
                                //
                                Gui.drawRect(endPosX - 0.3D, posY, endPosX,
                                		endPosY, color);
                                
                                break;
                            case "Corners":
                                Gui.drawRect(posX, posY, posX - .5, posY + (endPosY - posY) / 4, 
                                		color);
                                
                                //
                                Gui.drawRect(posX, endPosY, posX - .5, endPosY - (endPosY - posY) / 4,
                                		color);
                                
                                //
                                Gui.drawRect(posX - .5, posY, posX + (endPosX - posX) / 3, posY + .2,
                                		color);
                                
                                //
                                Gui.drawRect(endPosX - (endPosX - posX) / 3, posY, endPosX, posY + .2, 
                                		color);
                                
                                //
                                Gui.drawRect(endPosX - .2, posY, endPosX, posY + (endPosY - posY) / 4, 
                                		color);
                                
                                //
                                Gui.drawRect(endPosX - .5, endPosY, endPosX, endPosY - (endPosY - posY) / 4, 
                                		color);
                                
                                //
                                Gui.drawRect(posX, endPosY - .2, posX + (endPosX - posX) / 3, endPosY,
                                		color);
                                
                                //
                                Gui.drawRect(endPosX - (endPosX - posX) / 3, endPosY - .2, endPosX - .5, endPosY, 
                                		color);

                                break;
                        }
                    }

                    count++;
                    
                    
             

                    final boolean living = entity instanceof EntityLivingBase;

                    if (living) {
                        final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                      
                        if(this.armor.getValBoolean()) {
                       ScaledResolution sr = new ScaledResolution(mc);
                		GL11.glPushMatrix();
                		double dist = mc.thePlayer.getDistanceToEntity(entityLivingBase);
                		GL11.glScaled(0.5,0.5,0.5);
                		//System.out.println(1 - dist / 40);
                		//Standard lighting might cause screen to be darker
                		RenderHelper.enableGUIStandardItemLighting();
                		mc.getRenderItem().renderItemAndEffectIntoGUI(entityLivingBase.getCurrentArmor(3), (int)endPosX * 2 + 10, (int)posY * 2 + 10);
                		mc.getRenderItem().renderItemAndEffectIntoGUI(entityLivingBase.getCurrentArmor(2), (int)endPosX * 2 + 10, (int)posY * 2 + 30);
                		mc.getRenderItem().renderItemAndEffectIntoGUI(entityLivingBase.getCurrentArmor(1), (int)endPosX * 2 + 10, (int)posY * 2 + 50);
                		mc.getRenderItem().renderItemAndEffectIntoGUI(entityLivingBase.getCurrentArmor(0), (int)endPosX * 2 + 10, (int)posY * 2 + 70);
                		GL11.glPopMatrix();}
                        
                        if (health) {
                            float hp = entityLivingBase.getHealth();
                            final float maxHealth = entityLivingBase.getMaxHealth();
                            if (hp > maxHealth) {
                                hp = maxHealth;
                            }

                            final double hpPercentage = hp / maxHealth;

                            final double hpHeight = (endPosY - posY) * hpPercentage;

                            Gui.drawRect(posX - 2.28, posY - .5, posX - 1.5, endPosY + 0.5, background);

                            if (hp > 0) {
                                int colorrectCode = entityLivingBase.getHealth() > 15 ? 0xff4DF75B : entityLivingBase.getHealth() > 10 ? 0xffF1F74D : entityLivingBase.getHealth() > 7 ? 0xffF7854D : 0xffF7524D;
                                Gui.drawRect(posX - 2.28, endPosY, posX - 2, endPosY - hpHeight, colorrectCode);
                            }
                        }
                    }
                }
            }
        }


        GlStateManager.enableBlend();

        entityRenderer.setupOverlayRendering(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
        GL11.glPopMatrix();
    }




private void collectEntities() {
    collectedEntities.clear();

    final List<Entity> playerEntities = mc.theWorld.loadedEntityList;
    for (final Entity entity : playerEntities) {
    	if(entity != mc.thePlayer && !entity.isInvisible())
            collectedEntities.add(entity);
    }
}

private Vector3d project2D(int scaleFactor, double x, double y, double z) {
    GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
    GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
    GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);

    if (GLU.gluProject((float) x, (float) y, (float) z, modelview, projection, viewport, vector)) {
        return new Vector3d(vector.get(0) / scaleFactor, (Display.getHeight() - vector.get(1)) / scaleFactor, vector.get(2));
    }

    return null;
}

}