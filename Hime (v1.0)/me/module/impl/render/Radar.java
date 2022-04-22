package me.module.impl.render;

import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ZERO;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex2f;

import me.Hime;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.awt.Color;

import me.event.impl.EventRenderHUD;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.module.impl.combat.Antibot;
import me.settings.Setting;
import me.util.ColorUtil;
import me.util.RainbowUtil;
import me.util.RenderUtil;
import me.util.RotationUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class Radar extends Module {

	public Setting add;
	public String type;
	public Setting hue;
	public Setting sat;
	public Setting bright;
	public Setting rad;
	public Setting size;
	
	public Radar() {
		super("Radar", 0, Category.RENDER);
	      Hime.instance.settingsManager.rSetting(hue = new Setting("Radar Hue", this, 20, 0, 255));
		  Hime.instance.settingsManager.rSetting(sat = new Setting("Radar Brightness", this, 1, -20, 5, 1));
		  Hime.instance.settingsManager.rSetting(bright = new Setting("Radar Saturation", this, 0, -20, 5, "eeee"));
		  Hime.instance.settingsManager.rSetting(add = new Setting("Y add", this, 0, 0, 180, true));
		  Hime.instance.settingsManager.rSetting(rad = new Setting("Radar Radius", this, 60, 10, 200, true));
		  Hime.instance.settingsManager.rSetting(size = new Setting("Radar Size", this, 5, 1, 20, false));
	      this.addModes("Radar type", "Normal", "Astolfo", "Exhibition", "Exhibition2", "Around Corsshar Normal", "Around Corsshar Triangle");
	      this.type = this.getModes("Radar type");
	}
	
	    @Handler
	    public void onRender2D(EventRenderHUD event){
	    	  this.type = this.getModes("Radar type");
	        ScaledResolution sr = new ScaledResolution(mc);
	        switch(type) {
	        case "Normal":
	      //  Gui.drawRect(sr.getScaledWidth() / 7 - 110, sr.getScaledHeight() / 4 + 5, sr.getScaledWidth() / 7 - 42, sr.getScaledHeight() / 4 + 30, new Color(28, 28, 28).getRGB());
	        RenderUtil.drawBorderedRect(5, 30 + this.add.getValInt(), 100, 120 + this.add.getValInt(), 0.5, new Color(40, 40, 40, 255).getRGB(), new Color(60, 60, 60, 255).getRGB(), true);
	        Gui.drawHorizontalLine(5, 99, 75 + this.add.getValInt(), Color.GRAY.getRGB());
	        Gui.drawVerticalLine(52, 30 + this.add.getValInt(), 120 + this.add.getValInt(), Color.GRAY.getRGB());
	        this.drawFilledCircle(52.5f, 75.5f + this.add.getValInt(), 2, Color.WHITE);
	        
	        for(Entity e : mc.theWorld.loadedEntityList){
	            if(e instanceof EntityLivingBase){
	                EntityLivingBase entity = (EntityLivingBase) e;
	                if(canRender(entity)){
	                	   float yaw = RotationUtils.getRotations(entity)[0];
	                       float diffyaw = -mc.thePlayer.rotationYaw + yaw + 90 + 180;
	                    float pitch = RotationUtils.getRotations(entity)[1];
	                    float diffpitch = pitch + 500;
	                    ScaledResolution sr2 = new ScaledResolution(mc);
	                 //   this.drawFilledCircle((int)diffyaw - 5 * 10, (int)diffpitch - 120, 2, Color.WHITE);
	                    double dist = mc.thePlayer.getDistanceToEntity(entity);
	                    GL11.glPushMatrix();
	                    this.prepareScissorBox(5, 30 + this.add.getValInt(), 100, 120 + this.add.getValInt());
	                    GL11.glEnable(3089);
	                    drawTriangle(sr.getScaledWidth() / 6 - 80, sr.getScaledHeight() / 5 - 6 + this.add.getValInt(), (float)(dist), (int)diffyaw - 5, (int)diffyaw + 5);
	                    GL11.glDisable(3089);
	                    GL11.glPopMatrix();
	                }
	            }
	        }
	        break;
	        case "Exhibition":
	        RenderUtil.drawBorderedRect(4, 29 + this.add.getValInt(), 101, 121 + this.add.getValInt(), 1.5, new Color(0, 0, 0, 255).getRGB(), new Color(0, 0, 0, 255).getRGB(), true);
	        RenderUtil.drawBorderedRect(5, 30 + this.add.getValInt(), 100, 120 + this.add.getValInt(), 1.5, new Color(18, 18, 18, 255).getRGB(), new Color(60, 60, 60, 255).getRGB(), true);
	   
	        Gui.drawHorizontalLine(5, 99, 75 + this.add.getValInt(), Color.GRAY.getRGB());
	        Gui.drawVerticalLine(52, 30 + this.add.getValInt(), 120 + this.add.getValInt(), Color.GRAY.getRGB());
	        
	        for(int x = 0; x < 92; x++) {
	        	 Gui.drawRect(8 + x, 31 + this.add.getValInt(), 6 + x, 32 + this.add.getValInt(), RainbowUtil.rainbow(50 * x));	
	        }
	        
	        for(Entity e : mc.theWorld.loadedEntityList){
	            if(e instanceof EntityLivingBase){
	                EntityLivingBase entity = (EntityLivingBase) e;
	                if(canRender(entity)){
	                	   float yaw = RotationUtils.getRotations(entity)[0];
	                       float diffyaw = -mc.thePlayer.rotationYaw + yaw + 90 + 180;
	                    float pitch = RotationUtils.getRotations(entity)[1];
	                    float diffpitch = pitch + 500;
	                    ScaledResolution sr2 = new ScaledResolution(mc);
	                 //   this.drawFilledCircle((int)diffyaw - 5 * 10, (int)diffpitch - 120, 2, Color.WHITE);
	                    double dist = mc.thePlayer.getDistanceToEntity(entity);
	                    GL11.glPushMatrix();
	                    this.prepareScissorBox(5, 32 + this.add.getValInt(), 100, 120 + this.add.getValInt());
	                    GL11.glEnable(3089);
	                    drawTriangle(sr.getScaledWidth() / 6 - 80, sr.getScaledHeight() / 5 - 6 + this.add.getValInt(), (float)(dist), (int)diffyaw - 5, (int)diffyaw + 5);
	                    GL11.glDisable(3089);
	                    GL11.glPopMatrix();
	                }
	            }
	        }
	        break;
	        case "Exhibition2":
		        RenderUtil.drawBorderedRect(4, 29 + this.add.getValInt(), 101, 121 + this.add.getValInt(), 1.5, new Color(0, 0, 0, 255).getRGB(), new Color(0, 0, 0, 255).getRGB(), true);
		        RenderUtil.drawBorderedRect(5, 30 + this.add.getValInt(), 100, 120 + this.add.getValInt(), 1.5, new Color(18, 18, 18, 255).getRGB(), new Color(60, 60, 60, 255).getRGB(), true);
		   
		        Gui.drawHorizontalLine(5, 99, 75 + this.add.getValInt(), Color.GRAY.getRGB());
		        Gui.drawVerticalLine(52, 30 + this.add.getValInt(), 120 + this.add.getValInt(), Color.GRAY.getRGB());
		        
		        final float hue2 = (float) (this.hue.getValDouble() / 255);
	     	    //                           colorSaturation  colorBrightness 
	           Color color22 = Color.getHSBColor(hue2, (float) bright.getValDouble(), (float) sat.getValDouble());
	           
	         
		        
		        for(int x = 0; x < 92; x++) {
		        	  Color color3 = ColorUtil.pulseBrightness(color22,  x * 9, 300);
		        	 Gui.drawRect(8 + x, 31 + this.add.getValInt(), 6 + x, 32 + this.add.getValInt(), color3.getRGB());	
		        }
		        
		        for(Entity e : mc.theWorld.loadedEntityList){
		            if(e instanceof EntityLivingBase){
		                EntityLivingBase entity = (EntityLivingBase) e;
		                if(canRender(entity)){
		                	   float yaw = RotationUtils.getRotations(entity)[0];
		                       float diffyaw = -mc.thePlayer.rotationYaw + yaw + 90 + 180;
		                    float pitch = RotationUtils.getRotations(entity)[1];
		                    float diffpitch = pitch + 500;
		                    ScaledResolution sr2 = new ScaledResolution(mc);
		                 //   this.drawFilledCircle((int)diffyaw - 5 * 10, (int)diffpitch - 120, 2, Color.WHITE);
		                    double dist = mc.thePlayer.getDistanceToEntity(entity);
		                    GL11.glPushMatrix();
		                    this.prepareScissorBox(5, 32 + this.add.getValInt(), 100, 120 + this.add.getValInt());
		                    GL11.glEnable(3089);
		                    drawTriangle(sr.getScaledWidth() / 6 - 80, sr.getScaledHeight() / 5 - 6 + this.add.getValInt(), (float)(dist), (int)diffyaw - 5, (int)diffyaw + 5);
		                    GL11.glDisable(3089);
		                    GL11.glPopMatrix();
		                }
		            }
		        }
		        break;
	        case "Astolfo":
	        final float hue21 = (float) (this.hue.getValDouble() / 255);
	     	//                           colorSaturation  colorBrightness 
	        Color color221 = Color.getHSBColor(hue21, (float) bright.getValDouble(), (float) sat.getValDouble());
	        RenderUtil.drawBorderedRect(5, 30 + this.add.getValInt(), 100, 120 + this.add.getValInt(), 0.5, new Color(0, 0, 0, 120).getRGB(), new Color(0, 0, 0, 120).getRGB(), true);
	        Gui.drawHorizontalLine(5, 99, 75 + this.add.getValInt(), new Color(163, 163, 163).getRGB());
	        Gui.drawVerticalLine(52, 30 + this.add.getValInt(), 120 + this.add.getValInt(), new Color(163, 163, 163).getRGB());
	        Gui.drawRect(5, 30 + this.add.getValInt(), 100, 31 + this.add.getValInt(), color221.getRGB());
	        
	        for(Entity e : mc.theWorld.loadedEntityList){
	            if(e instanceof EntityLivingBase){
	                EntityLivingBase entity = (EntityLivingBase) e;
	                if(canRender(entity)){
	                	   float yaw = RotationUtils.getRotations(entity)[0];
	                       float diffyaw = -mc.thePlayer.rotationYaw + yaw + 90 + 180;
	                    float pitch = RotationUtils.getRotations(entity)[1];
	                    float diffpitch = pitch + 500;
	                    ScaledResolution sr2 = new ScaledResolution(mc);
	                 //   this.drawFilledCircle((int)diffyaw - 5 * 10, (int)diffpitch - 120, 2, Color.WHITE);
	                    double dist = mc.thePlayer.getDistanceToEntity(entity);
	                    GL11.glPushMatrix();
	                    this.prepareScissorBox(5, 30 + this.add.getValInt(), 100, 120 + this.add.getValInt());
	                    GL11.glEnable(3089);
	                    drawTriangle(sr.getScaledWidth() / 6 - 80, sr.getScaledHeight() / 5 - 6 + this.add.getValInt(), (float)(dist), (int)diffyaw - 5, (int)diffyaw + 5);
	                    GL11.glDisable(3089);
	                    GL11.glPopMatrix();
	                }
	            }
	        }	        	
	        break;
	        case "Around Corsshar Normal":
	            for(Entity e : mc.theWorld.loadedEntityList){
	                if(e instanceof EntityLivingBase){
	                    EntityLivingBase entity = (EntityLivingBase) e;
	                    if(canRender(entity)){
	                        float yaw = RotationUtils.getRotations(entity)[0];
	                        float diffyaw = -mc.thePlayer.rotationYaw + yaw + 90 + 180;
	                        drawCircle(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, this.rad.getValInt(), (int)diffyaw - 5, (int)diffyaw + 5);
	                    }
	                }
	            }
	            break;
	        case"Around Corsshar Triangle":
	            float middleX = sr.getScaledWidth() / 2.0F;
	            float middleY = sr.getScaledHeight() / 2.0F;
	            for(Entity e : mc.theWorld.loadedEntityList){
	                if(e instanceof EntityLivingBase){
	                    EntityLivingBase entity = (EntityLivingBase) e;
	                    if(canRender(entity)){
	                    	 GL11.glPushMatrix();
	                          float arrowSizes = (float) this.size.getValDouble();
	                          float alpha = Math.max(1.0F - (mc.thePlayer.getDistanceToEntity(entity) / 30.0F), 0.7F);
	                          final float hue = (float) (this.hue.getValDouble() / 255);
	                          Color c = Color.getHSBColor(hue, (float) bright.getValDouble(), (float) sat.getValDouble());
	                          int color = c.getRGB();
	                          GL11.glTranslatef(middleX + 0.5F, middleY - (arrowSizes / 2.0F), 1.0F);
	                          float yaw = (float) (this.interpolate(
	                                  this.getYawToEntity(entity, true),
	                                  this.getYawToEntity(entity, false),
	                                  event.getPartialTicks()) -
	                                  this.interpolate(mc.thePlayer.prevRotationYaw, mc.thePlayer.rotationYaw, event.getPartialTicks()));
	                          GL11.glRotatef(yaw, 0, 0, 1);
	                          GL11.glTranslatef(0.0F, (float) -rad.getValDouble(), 0.0F);
	                          GL11.glEnable(GL11.GL_BLEND);
	                          GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
	                          glDisable(GL11.GL_TEXTURE_2D);
	                          glBegin(GL11.GL_TRIANGLES);
	                        	  GL11.glColor4ub(
	                                      (byte) (color >> 16 & 255),
	                                      (byte) (color >> 8 & 255),
	                                      (byte) (color & 255),
	                                      (byte) (alpha * 255));
	                          glVertex2f(0, 0);
	                          glVertex2f(-arrowSizes, arrowSizes);
	                          glVertex2f(arrowSizes, arrowSizes);
	                          glEnd();
	                          glEnable(GL11.GL_TEXTURE_2D);
	                          GL11.glDisable(GL11.GL_BLEND);
	                          GL11.glPopMatrix();
	                      }
	                    }
	        }
	            break;
	        }
	    }
	    
	    public void prepareScissorBox(float x2, float y2, float x22, float y22) {
	        ScaledResolution scale = new ScaledResolution(this.mc);
	        int factor = scale.getScaleFactor();
	        GL11.glScissor((int)(x2 * (float)factor), (int)(((float)scale.getScaledHeight() - y22) * (float)factor), (int)((x22 - x2) * (float)factor), (int)((y22 - y2) * (float)factor));
	    }
	    
	    public void drawCircle(float x, float y, float radius, int start, int end) {
	        GlStateManager.enableBlend();
	        GlStateManager.disableTexture2D();
	        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
	        final float hue = (float) (this.hue.getValDouble() / 255);
     	    //                           colorSaturation  colorBrightness 
           Color color2 = Color.getHSBColor(hue, (float) bright.getValDouble(), (float) sat.getValDouble());
           
	        GL11.glColor3f(color2.getRed(), color2.getGreen(), color2.getBlue());

	        glEnable(GL_LINE_SMOOTH);
	        glLineWidth(6F);
	        glBegin(GL_LINE_STRIP);
	        for (float i = end; i >= start; i -= (360 / 90.0f)) {
	            glVertex2f((float) (x + (Math.cos(i * Math.PI / 180) * (radius * 1.001F))), (float) (y + (Math.sin(i * Math.PI / 180) * (radius * 1.001F))));
	        }
	        glEnd();
	        glDisable(GL_LINE_SMOOTH);

	        GlStateManager.enableTexture2D();
	        GlStateManager.disableBlend();
	    }
	    
	    public void drawTriangle(float x, float y, float radius, int start, int end) {
	        GlStateManager.enableBlend();
	        GlStateManager.disableTexture2D();
	        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
	        final float hue = (float) (this.hue.getValDouble() / 255);
     	    //                           colorSaturation  colorBrightness 
           Color color2 = Color.getHSBColor(hue, (float) bright.getValDouble(), (float) sat.getValDouble());
           
	      //  GL11.glColor3f(color2.getRed(), color2.getGreen(), color2.getBlue());
	        for (float i = end; i >= start; i -= (4000 / 90.0f)) {
	        	this.drawFilledCircle((float) (x + (Math.cos(i * Math.PI / 180) * (radius * 1.001F))), (float) (y + (Math.sin(i * Math.PI / 180) * (radius * 1.001F))), 1.4f, color2);
	        }

	        GlStateManager.enableTexture2D();
	        GlStateManager.disableBlend();
	    }
	    
	    public static void drawFilledCircle(float xx, float yy, float radius, Color color) {
		    int sections = 50;
		    double dAngle = 6.283185307179586D / sections;
		    GL11.glPushAttrib(8192);
		    GL11.glEnable(3042);
		    GL11.glDisable(3553);
		    GL11.glBlendFunc(770, 771);
		    GL11.glEnable(2848);
		    GL11.glBegin(6);
		    for (int i = 0; i < sections; i++) {
		      float x = (float)(radius * Math.sin(i * dAngle));
		      float y = (float)(radius * Math.cos(i * dAngle));
		      GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
		      GL11.glVertex2f(xx + x, yy + y);
		    } 
		    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		    GL11.glEnd();
		    GL11.glPopAttrib();
		  }
	    public static double interpolate(double current, double old, double scale) {
	        return old + (current - old) * scale;
	    }
	    public static float getYawToEntity(Entity entity, boolean useOldPos) {
	        final EntityPlayerSP player = mc.thePlayer;
	        double xDist = (useOldPos ? entity.prevPosX : entity.posX) -
	                (useOldPos ? player.prevPosX : player.posX);
	        double zDist = (useOldPos ? entity.prevPosZ : entity.posZ) -
	                (useOldPos ? player.prevPosZ : player.posZ);
	        float rotationYaw = useOldPos ? mc.thePlayer.prevRotationYaw : mc.thePlayer.rotationYaw;
	        float var1 = (float) (Math.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F;
	        return rotationYaw + MathHelper.wrapAngleTo180_float( var1 - rotationYaw);
	    }
	    public boolean canRender(EntityLivingBase player) {
	        if(player == mc.thePlayer)
	            return false;
	        if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
	            if (player instanceof EntityPlayer && !Hime.instance.moduleManager.getModule("Players").isToggled())
	                return false;

	        }
	        if(player instanceof EntityPlayer) {
	            if (Antibot.isBot((EntityPlayer) player))
	                return false;
	        }
	        if (mc.thePlayer.isOnSameTeam(player) && Hime.instance.moduleManager.getModule("Teams").isToggled())
	            return false;
	        if (player.isInvisible() && !Hime.instance.moduleManager.getModule("Invisibles").isToggled())
	            return false;

	        return true;
	    }
}
