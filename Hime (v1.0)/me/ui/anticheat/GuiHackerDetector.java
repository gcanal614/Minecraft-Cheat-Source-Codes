package me.ui.anticheat;

import me.Hime;
import me.ui.anticheat.sub.CheaterManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class GuiHackerDetector extends GuiScreen{

	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawRect(180, 20, 600, 290, 0xFF222222);
	    //this.drawRect(22, 22, 100, 278, 0xFF111111);  
		int count = 0;
		for(EntityPlayer e : CheaterManager.instance.player) {
			Hime.instance.cfrs.drawString(e.getName(), 190, count * 11, -1);
			count++;
		}
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	  public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent)
	  {
	      GlStateManager.enableColorMaterial();
	      GlStateManager.pushMatrix();
	      GlStateManager.translate((float)posX, (float)posY, 50.0F);
	      GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
	      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
	      float f = ent.renderYawOffset;
	      float f1 = ent.rotationYaw;
	      float f2 = ent.rotationPitch;
	      float f3 = ent.prevRotationYawHead;
	      float f4 = ent.rotationYawHead;
	      GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
	      RenderHelper.enableStandardItemLighting();
	      GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
	      GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
	      ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
	      ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
	      ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
	      ent.rotationYawHead = ent.rotationYaw;
	      ent.prevRotationYawHead = ent.rotationYaw;
	      GlStateManager.translate(0.0F, 0.0F, 0.0F);
	      RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
	      rendermanager.setPlayerViewY(180.0F);
	      rendermanager.setRenderShadow(false);
	      rendermanager.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
	      rendermanager.setRenderShadow(true);
	      ent.renderYawOffset = f;
	      ent.rotationYaw = f1;
	      ent.rotationPitch = f2;
	      ent.prevRotationYawHead = f3;
	      ent.rotationYawHead = f4;
	      GlStateManager.popMatrix();
	      RenderHelper.disableStandardItemLighting();
	      GlStateManager.disableRescaleNormal();
	      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	      GlStateManager.disableTexture2D();
	      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	  }
	  
	
}
