package wtf.astronicy.IMPL.module.impl.visuals;

import java.awt.Color;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import wtf.astronicy.Astronicy;
import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.render.RenderGuiEvent;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.impl.combat.AuraMod;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import wtf.astronicy.IMPL.module.options.impl.EnumOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@ModName("TargetHUD")
@Category(ModuleCategory.VISUALS)
public final class TargetHUDMod extends Module {
	FontRenderer fr;
	
	public final EnumOption THUDMode;
	
	public boolean mouse = false;

    public float x = 0;
    public float y = 0;
    public boolean start = false;
    
   public TargetHUDMod() {
	   fr = mc.fontRenderer;
	   this.THUDMode = new EnumOption("Mode", TargetHUDMod.THUDModes.NOVO);
   }

   @Listener(RenderGuiEvent.class)
   public final void onRenderGui(RenderGuiEvent event) {
      AuraMod aura = (AuraMod) Astronicy.MANAGER_REGISTRY.moduleManager.getModuleOrNull(AuraMod.class);
      if(mc.currentScreen instanceof GuiChat) {
          Mouse.setGrabbed(false);
          int z = (Mouse.getEventX())/2;
          int g = (int) -(Mouse.getEventY() - mc.displayHeight)/2;
          final boolean hovered = mouseWithinBounds(z, g, this.x, this.y, 100, 50);
          if(hovered) {
              if(Mouse.isButtonDown(0)) {
                  mouse = true;
              }else if(Mouse.isButtonDown(1) && mouse){
                  mouse = false;
              }
          }
          if(hovered && mouse) {
              x = z - 142/2f;
              y = g - 16;
          }
          drawTargetHud(mc.thePlayer, this.x, this.y, 100, 50, new Color(0, 0, 0, 80).getRGB(), 0xffff0000);
      }else {
          //if (aura.getTarget() != null){
          //    drawTargetHud(aura.getTarget(), this.x, this.y, 100, 50, new Color(0, 0, 0, 80).getRGB(), 0xffff0000);
         // }
          mouse = false;
      }
   }
   
   public void drawTargetHud(EntityLivingBase entity, float x, float y, float witdh, float height, int color, int color2) {
	   switch((TargetHUDMod.THUDModes)THUDMode.getValue()) {
	   case NOVO:
       	double asdwas = witdh + fr.getStringWidth(entity.getName());
        drawRect(x, y, asdwas, height, color);
        fr.drawString(entity.getName(), x + 40, y + 2, -1, false);
        drawFace(x + 2, y + 2, 8.0F, 8.0F, 8, 8, 36, 36, 64.0F, 64.0F, (AbstractClientPlayer) entity);
		   break;
	   }
   }

   public static boolean mouseWithinBounds(int mouseX, int mouseY, double x, double y, double width, double height) {
       return (mouseX >= x && mouseX <= (x + width)) && (mouseY >= y && mouseY <= (y + height));
   }

   public static void drawRect(double x, double y, double width, double height, int color) {
       float f = (color >> 24 & 0xFF) / 255.0F;
       float f1 = (color >> 16 & 0xFF) / 255.0F;
       float f2 = (color >> 8 & 0xFF) / 255.0F;
       float f3 = (color & 0xFF) / 255.0F;
       GL11.glColor4f(f1, f2, f3, f);
       Gui.drawRect(x, y, x + width, y + height, color);
   }

   public void drawFace(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer target) {
       try {
           ResourceLocation skin = target.getLocationSkin();
           Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
           GL11.glEnable(3042);
           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
           Gui.drawScaledCustomSizeModalRect((int)x, (int)y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
           GL11.glDisable(3042);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
   
   private static enum THUDModes {
	  NOVO;
   }
   
}
