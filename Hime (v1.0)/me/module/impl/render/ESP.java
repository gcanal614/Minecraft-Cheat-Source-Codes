package me.module.impl.render;
import java.awt.Color;

import me.Hime;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.event.impl.Event3D;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;


public class ESP extends Module{
public Setting one;
	public double health = 0;
	public Setting mode;
	public Setting healthbar;
	public Setting rects;
	public String mode2;
	public ESP() {
		super("ESP",Keyboard.KEY_NONE, Category.RENDER);
		/*   ArrayList<String> options = new ArrayList<>();
	       options.add("Calico");
	       options.add("Simon");
	       options.add("3D"); 
	       options.add("Outline"); 
	       options.add("Shader"); 
	       Hime.instance.settingsManager.rSetting(mode = new Setting("ESP Mode", this, "3D", options));*/
	       this.addModes("ESP Mode", "Calico", "Simon", "3D", "Outline", "Shader");
	       mode2 = this.getModes("ESP Mode");
	       Hime.instance.settingsManager.rSetting(one = new Setting("croch Simon", this, false));
	       Hime.instance.settingsManager.rSetting(new Setting("Outline ESP Width", this, 2.5, 0, 6, false));
	}

    private int getRainbow(int speed, int offset) {
      float hue = (System.currentTimeMillis() + offset) % speed;
      hue /= speed;
        return Color.getHSBColor(hue, 1f, 1f).getRGB(); 
    } 

	
      @Handler
      public void onRender(Event3D event) {
    	  mode2 = this.getModes("ESP Mode");
			this.setSuffix(mode2); 
			switch(mode2) {
		    	case "3D":
			    	for (Object o : this.mc.theWorld.loadedEntityList) {
			    		if (((o instanceof EntityPlayer)) && (o != this.mc.thePlayer)) {
							EntityPlayer e = (EntityPlayer) o;
						     if(Hime.instance.friendManager.isFriend(e.getName())) {
							   this.entityESPBox(e, 1);
						     }else {
						       this.entityESPBox(e, 0);
						     }
			    		}
			    		if (((o instanceof EntityMob))) {
			    			EntityMob e = (EntityMob) o;
						       this.entityESPBox(e, 0);
			    		}
			    		if (((o instanceof EntityAnimal))) {
			    			EntityAnimal e = (EntityAnimal) o;
						       this.entityESPBox(e, 1);
			    		}
			    	}
			    	break;
		    	case "Calico":
					for (Object o : this.mc.theWorld.loadedEntityList) {
						if (((o instanceof EntityPlayer)) && (o != this.mc.thePlayer)) {
						EntityPlayer e = (EntityPlayer) o;
						double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * this.mc.timer.elapsedPartialTicks
						- this.mc.getRenderManager().renderPosX;
						double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * this.mc.timer.elapsedPartialTicks
						- this.mc.getRenderManager().renderPosY;
						double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * this.mc.timer.elapsedPartialTicks
						- this.mc.getRenderManager().renderPosZ;

						GL11.glPushMatrix();
						GL11.glTranslated(x, y - 0.2D, z);
						GL11.glScalef(0.03F, 0.03F, 0.03F);


						GL11.glRotated(-this.mc.getRenderManager().playerViewY, 0.0D, 1.0D, 0.0D);
						GlStateManager.disableDepth();
						  RenderUtil.instance.draw2DImage(new ResourceLocation("client/cat.png"), -40,  -10, 100, 100, Color.WHITE);
						

						GlStateManager.enableDepth();
						GL11.glPopMatrix();
						    }
						}
					break;
		    	 case "Simon":
					for (Object o : this.mc.theWorld.loadedEntityList) {
						if (((o instanceof EntityPlayer)) && (o != this.mc.thePlayer)) {
						EntityPlayer e = (EntityPlayer) o;
						double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * this.mc.timer.elapsedPartialTicks
						- this.mc.getRenderManager().renderPosX;
						double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * this.mc.timer.elapsedPartialTicks
						- this.mc.getRenderManager().renderPosY;
						double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * this.mc.timer.elapsedPartialTicks
						- this.mc.getRenderManager().renderPosZ;

					
						GL11.glPushMatrix();
						GL11.glTranslated(x, y - 0.2D, z);
						GL11.glScalef(0.03F, 0.03F, 0.03F);


						GL11.glRotated(-this.mc.getRenderManager().playerViewY, 0.0D, 1.0D, 0.0D);
						GlStateManager.disableDepth();
						if(this.one.getValBoolean()) {
						  RenderUtil.instance.draw2DImage(new ResourceLocation("client/simon.png"), -40,  -10, 100, 130, Color.WHITE);
						}else {
							 RenderUtil.instance.draw2DImage(new ResourceLocation("client/simon2.png"), -40,  -10, 100, 130, Color.WHITE);
						}

						GlStateManager.enableDepth();
						GL11.glPopMatrix();
						    }
						}
					break;
			}
        }
      public static void entityESPBox(Entity entity, int mode)
  	{
  		GL11.glBlendFunc(770, 771);
  		GL11.glEnable(GL11.GL_BLEND);
  		GL11.glLineWidth(2.0F);
  		GL11.glDisable(GL11.GL_TEXTURE_2D);
  		GL11.glDisable(GL11.GL_DEPTH_TEST);
  		GL11.glDepthMask(false);
  		if(mode == 0)// Enemy
  			GL11.glColor4d(
  				1 - Minecraft.getMinecraft().thePlayer
  					.getDistanceToEntity(entity) / 40,
  				Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40,
  				0, 0.5F);
  		else if(mode == 1)// Friend
  			GL11.glColor4d(0, 0, 1, 0.5F);
  		else if(mode == 2)// Other
  			GL11.glColor4d(1, 1, 0, 0.5F);
  		else if(mode == 3)// Target
  			GL11.glColor4d(1, 0, 0, 0.5F);
  		else if(mode == 4)// Team
  			GL11.glColor4d(0, 1, 0, 0.5F);
  		Minecraft.getMinecraft().getRenderManager();
  		RenderGlobal.func_181561_a(
  			new AxisAlignedBB(
  				entity.boundingBox.minX
  					- 0.05
  					- entity.posX
  					+ (entity.posX - Minecraft.getMinecraft()
  						.getRenderManager().renderPosX),
  				entity.boundingBox.minY
  					- entity.posY
  					+ (entity.posY - Minecraft.getMinecraft()
  						.getRenderManager().renderPosY),
  				entity.boundingBox.minZ
  					- 0.05
  					- entity.posZ
  					+ (entity.posZ - Minecraft.getMinecraft()
  						.getRenderManager().renderPosZ),
  				entity.boundingBox.maxX
  					+ 0.05
  					- entity.posX
  					+ (entity.posX - Minecraft.getMinecraft()
  						.getRenderManager().renderPosX),
  				entity.boundingBox.maxY
  					+ 0.1
  					- entity.posY
  					+ (entity.posY - Minecraft.getMinecraft()
  						.getRenderManager().renderPosY),
  				entity.boundingBox.maxZ
  					+ 0.05
  					- entity.posZ
  					+ (entity.posZ - Minecraft.getMinecraft()
  						.getRenderManager().renderPosZ)));
  		GL11.glEnable(GL11.GL_TEXTURE_2D);
  		GL11.glEnable(GL11.GL_DEPTH_TEST);
  		GL11.glDepthMask(true);
  		GL11.glDisable(GL11.GL_BLEND);
  	}
}