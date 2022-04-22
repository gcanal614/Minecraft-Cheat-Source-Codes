package me.module.impl.render;


import java.awt.Color;

import me.Hime;
import org.lwjgl.opengl.GL11;

import me.event.impl.Event3D;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.OutlineUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class ChestESP extends Module{

	public Setting mode;
	public String mode2;
	public ChestESP() {
		super("ChestESP", 0, Category.RENDER);

		/*   ArrayList<String> options = new ArrayList<>();
	        options.add("CSGO");
	        options.add("Box"); 
	        options.add("Outline"); 
	        options.add("Outline2"); 
	        Hime.instance.settingsManager.rSetting(mode = new Setting("Chest Mode", this, "Box", options));*/
	        addModes("Chest Mode", "CSGO", "Box", "Outline", "Outline2");
	        mode2 = this.getModes("Chest Mode");
	}
	
    @Handler
    public void onRender(Event3D event) {
    	    mode2 = this.getModes("Chest Mode");
			this.setSuffix(mode2); 
			switch(mode2) {
			case "Box":
			for (Object o : this.mc.theWorld.loadedTileEntityList) {
				if (o instanceof TileEntityChest) {
					TileEntityChest e = (TileEntityChest) o;
					this.drawChestEsp(e.getPos());
				}
			}
			break;
			case "CSGO":
			for (Object o : this.mc.theWorld.loadedTileEntityList) {
				if (o instanceof TileEntityChest) {
					TileEntityChest e = (TileEntityChest) o;
					this.drawCSGOChestEsp(e);
				}
			}
			break;
			case "Outline2":
			for (TileEntity entity : mc.theWorld.loadedTileEntityList) {
				if (entity instanceof TileEntityChest || entity instanceof TileEntityEnderChest) {
					OutlineUtil.renderOne((float) 2.5);
					TileEntityRendererDispatcher.instance.renderTileEntity(entity, event.getPartialTicks(), -1);
					OutlineUtil.renderTwo();
					TileEntityRendererDispatcher.instance.renderTileEntity(entity, event.getPartialTicks(), -1);
					OutlineUtil.renderThree();
					TileEntityRendererDispatcher.instance.renderTileEntity(entity, event.getPartialTicks(), -1);
					OutlineUtil.renderFour(Color.white);
					TileEntityRendererDispatcher.instance.renderTileEntity(entity, event.getPartialTicks(), -1);
					OutlineUtil.renderFive();
					OutlineUtil.setColor(Color.white);
				}
			}
			break;
		}
    }
    
    public static void drawChestEsp(BlockPos blockPos) {
		double x = blockPos.getX() - mc.getRenderManager().renderPosX;
		double y = blockPos.getY() - mc.getRenderManager().renderPosY;
		double z = blockPos.getZ() - mc.getRenderManager().renderPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glColor4d(0, 1, 0, 0.15F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		final float hue = (float) (Hime.instance.settingsManager.getSettingByName("Hue").getValDouble() / 255);
		   //                           colorSaturation  colorBrightness 
	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
		GL11.glColor4d(color.getRed(), color.getGreen(), color.getBlue(), 0.5F);
		RenderGlobal.func_181561_a(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}
    
    public static void drawCSGOChestEsp(TileEntityChest e) {
    	//double x = e.getX() - mc.getRenderManager().renderPosX;
		//double y = e.getY() - mc.getRenderManager().renderPosY;
		//double z = e.getZ() - mc.getRenderManager().renderPosZ;
	
				GL11.glPushMatrix();
				//GL11.glTranslated(x, y - 0.2D, z);
				  GlStateManager.translate(e.getPos().getX() - RenderManager.renderPosX + 0.5D, e.getPos().getY() - RenderManager.renderPosY + 0.35D, e.getPos().getZ() - RenderManager.renderPosZ + 0.5D);
				GL11.glScalef(0.03F, 0.03F, 0.03F);


				GL11.glRotated(-mc.getRenderManager().playerViewY, 0, 1, 0);
				GL11.glRotated(mc.getRenderManager().playerViewX, 1, 0, 0);
				GlStateManager.disableDepth();
				
				//Bottom
				Gui.drawRect(-23.0D, -24.0D, 23.0D, -21.0D, -16777216);
				Gui.drawRect(-22.0D, -23.0D, 22.0D, -22.0D, -1);
				//Top
			    Gui.drawRect(-23.0D, 24.0D, 23.0D, 27.0D, -16777216);
			    Gui.drawRect(-22.0D, 26.0D, 22.0D, 25.0D, -1);
			    //Left
			    Gui.drawRect(20.0D, 25.0D, 23.0D, -22.0D, -16777216);
			    Gui.drawRect(21.0D, 25.0D, 22.0D, -22.0D, -1);
			    //Right
		        Gui.drawRect(-20.0D, 25.0D, -23.0D, -22.0D, -16777216);
		        Gui.drawRect(-21.0D, 25.0D, -22.0D, -22.0D, -1);
				
				GlStateManager.enableDepth();
				GL11.glPopMatrix();
    }
	

}