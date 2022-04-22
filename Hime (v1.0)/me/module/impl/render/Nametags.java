package me.module.impl.render;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import me.Hime;
import me.event.impl.EventRenderHUD;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.DrawUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

public class Nametags extends Module{
	EntityPlayer p;
	//public double health = 0;
	private DrawUtils draw;
	public Setting background;
	public Setting health;
	public Setting healthtext;
	public Setting customfont;
	public Setting hue;
	public Setting sat;
	public Setting bright;
	
	public Nametags() {
		super("Nametags", 0, Category.RENDER);
	}
	
	    @Override
	    public void setup() {
	        Hime.instance.settingsManager.rSetting(background = new Setting("Background", this, true));
	        Hime.instance.settingsManager.rSetting(health = new Setting("Health", this, true));
	        Hime.instance.settingsManager.rSetting(healthtext = new Setting("Health Text", this, true));
	        Hime.instance.settingsManager.rSetting(customfont = new Setting("Custom Font Text", this, true));
	        Hime.instance.settingsManager.rSetting(hue = new Setting("Nametag Hue", this, 20, 0, 255));
		    Hime.instance.settingsManager.rSetting(sat = new Setting("Nametag Brightness", this, 1, -20, 5, 1));
		    Hime.instance.settingsManager.rSetting(bright = new Setting("Nametag Saturation", this, 0, -20, 5, "eeee"));
	    }
	    

		   private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
		    private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
		    private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
		    private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
		    private final static Frustum frustrum = new Frustum();
			public final List<Entity> collectedEntities = new ArrayList<>();
		    
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
	                       
	                       final boolean living = entity instanceof EntityLivingBase;

	                       if (living) {
	                           final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
	                           if(entity instanceof EntityPlayer) {
	                       	    float health = ((EntityPlayer) entity).getHealth();
	            		        float healthPercentage = health / ((EntityPlayer) entity).getMaxHealth();
	            		       
	            		        Color healthcolor = Color.WHITE;
	            		        if (healthPercentage * 100.0F > 75.0F) {
	            		          healthcolor = Color.GREEN;
	            		        } else if (healthPercentage * 100.0F > 50.0F && healthPercentage * 100.0F < 75.0F) {
	            		          healthcolor = Color.YELLOW;
	            		        } else if (healthPercentage * 100.0F < 50.0F && healthPercentage * 100.0F > 25.0F) {
	            		          healthcolor = Color.ORANGE;
	            		        } else if (healthPercentage * 100.0F < 25.0F) {
	            		          healthcolor = Color.RED;
	            		        } 
	                        	   if(this.background.getValBoolean()) {
	                        		   if(this.healthtext.getValBoolean()) {
	                        			   Gui.drawRect(posX, posY - 5, posX + Hime.instance.rfrs.getWidth(entity.getName() + "  (" + Math.round(((EntityPlayer) entity).getHealth()) + ")"), posY - 17, 0x80000000);
	                        		   }else {
	                        		   Gui.drawRect(posX, posY - 5, posX + Hime.instance.rfrs.getWidth(entity.getName()), posY - 17, 0x80000000);
	                        		   }
	                        		}
	                        	   if(this.health.getValBoolean()) {
	                        		   if(this.healthtext.getValBoolean()) {
	                        		   Gui.drawRect(posX, posY - 5, posX + Hime.instance.rfrs.getWidth(entity.getName() + "  (" + Math.round(((EntityPlayer) entity).getHealth()) + ")") * healthPercentage, posY - 6, healthcolor.getRGB());
	                        		   }else {
	                        			   Gui.drawRect(posX, posY - 5, posX + Hime.instance.rfrs.getWidth(entity.getName()) * healthPercentage, posY - 6, healthcolor.getRGB());
	                        		   }
	                        		   }
	                        	}
	                       }
	                       
	                   }
	               }
	           }
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
	                       
	                       final boolean living = entity instanceof EntityLivingBase;

	                       if (living) {
	                           final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
	                           if(entity instanceof EntityPlayer) {
	                        	 if(this.customfont.getValBoolean()) {
	                        		if(this.healthtext.getValBoolean()) {
	                        			  Hime.instance.rfrs.drawString(entity.getName() + "  (" + Math.round(((EntityPlayer) entity).getHealth()) + ")", (float)posX, (float)posY - 15, color);
	                        		 }else {
	                        	  Hime.instance.rfrs.drawString(entity.getName(), (float)posX, (float)posY - 15, color);
	                        		 }
	                        	   }else {
	                        		if(this.healthtext.getValBoolean()) {
	                        			Minecraft.getMinecraft().fontRendererObj.drawString(entity.getName() + "  (" + Math.round(((EntityPlayer) entity).getHealth()) + ")", (int)posX, (int)posY - 15, color);	 
		                        	 }else {
	                        		Minecraft.getMinecraft().fontRendererObj.drawString(entity.getName(), (int)posX, (int)posY - 15, color);
		                         	 }
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

	@Override
	public void onEnable() {
		super.onEnable();
	}

	@Override
	public void onDisable() {
		super.onDisable();
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


	