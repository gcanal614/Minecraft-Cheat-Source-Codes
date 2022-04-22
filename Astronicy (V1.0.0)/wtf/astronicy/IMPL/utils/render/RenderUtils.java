package wtf.astronicy.IMPL.utils.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class RenderUtils {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private static final Frustum frustrum = new Frustum();

   public static double interpolate(double current, double old, double scale) {
      return old + (current - old) * scale;
   }

   public static boolean isInViewFrustrum(Entity entity) {
      return isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
   }

   private static boolean isInViewFrustrum(AxisAlignedBB bb) {
      Entity current = mc.getRenderViewEntity();
      frustrum.setPosition(current.posX, current.posY, current.posZ);
      return frustrum.isBoundingBoxInFrustum(bb);
   }

   public static void drawImg(ResourceLocation loc, double posX, double posY, double width, double height) {
      mc.getTextureManager().bindTexture(loc);
      float f = 1.0F / (float)width;
      float f1 = 1.0F / (float)height;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos(posX, posY + height, 0.0D).tex((double)(0.0F * f), (double)((0.0F + (float)height) * f1)).endVertex();
      worldrenderer.pos(posX + width, posY + height, 0.0D).tex((double)((0.0F + (float)width) * f), (double)((0.0F + (float)height) * f1)).endVertex();
      worldrenderer.pos(posX + width, posY, 0.0D).tex((double)((0.0F + (float)width) * f), (double)(0.0F * f1)).endVertex();
      worldrenderer.pos(posX, posY, 0.0D).tex((double)(0.0F * f), (double)(0.0F * f1)).endVertex();
      tessellator.draw();
   }

   public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
      float f = (float)(startColor >> 24 & 255) / 255.0F;
      float f1 = (float)(startColor >> 16 & 255) / 255.0F;
      float f2 = (float)(startColor >> 8 & 255) / 255.0F;
      float f3 = (float)(startColor & 255) / 255.0F;
      float f4 = (float)(endColor >> 24 & 255) / 255.0F;
      float f5 = (float)(endColor >> 16 & 255) / 255.0F;
      float f6 = (float)(endColor >> 8 & 255) / 255.0F;
      float f7 = (float)(endColor & 255) / 255.0F;
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
      worldrenderer.pos(right, top, 0.0D).color(f1, f2, f3, f).endVertex();
      worldrenderer.pos(left, top, 0.0D).color(f1, f2, f3, f).endVertex();
      worldrenderer.pos(left, bottom, 0.0D).color(f5, f6, f7, f4).endVertex();
      worldrenderer.pos(right, bottom, 0.0D).color(f5, f6, f7, f4).endVertex();
      tessellator.draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
   }

   public static void drawHsvScale(double left, double top, double right, double bottom) {
      float width = (float)(right - left);

      for(float i = 0.0F; i < width; ++i) {
         double posX = left + (double)i;
         int color = Color.getHSBColor(i / width, 1.0F, 1.0F).getRGB();
         Gui.drawRect(posX, top, posX + 1.0D, bottom, color);
      }

   }

   public static void prepareScissorBox(float x, float y, float x2, float y2) {
      ScaledResolution scale = new ScaledResolution(mc);
      int factor = scale.getScaleFactor();
      GL11.glScissor((int)(x * (float)factor), (int)(((float)scale.getScaledHeight() - y2) * (float)factor), (int)((x2 - x) * (float)factor), (int)((y2 - y) * (float)factor));
   }

   public static void drawBorderedRect(double left, double top, double right, double bottom, double borderWidth, int insideColor, int borderColor) {
      Gui.drawRect(left - borderWidth, top - borderWidth, right + borderWidth, bottom + borderWidth, borderColor);
      Gui.drawRect(left, top, right, bottom, insideColor);
   }

   public static void drawBorder(double left, double top, double width, double height, double lineWidth, int color) {
      Gui.drawRect(left, top, left + width, top + lineWidth, color);
      Gui.drawRect(left, top, left + lineWidth, top + height, color);
      Gui.drawRect(left, top + height - lineWidth, left + width, top + height, color);
      Gui.drawRect(left + width - lineWidth, top, left + width, top + height, color);
   }
   
   public static void drawArc(float n, float n2, double n3, final int n4, final int n5, final double n6, final int n7) {
       n3 *= 2.0;
       n *= 2.0f;
       n2 *= 2.0f;
       final float n8 = (n4 >> 24 & 0xFF) / 255.0f;
       final float n9 = (n4 >> 16 & 0xFF) / 255.0f;
       final float n10 = (n4 >> 8 & 0xFF) / 255.0f;
       final float n11 = (n4 & 0xFF) / 255.0f;
       GL11.glDisable(2929);
       GL11.glEnable(3042);
       GL11.glDisable(3553);
       GL11.glBlendFunc(770, 771);
       GL11.glDepthMask(true);
       GL11.glEnable(2848);
       GL11.glHint(3154, 4354);
       GL11.glHint(3155, 4354);
       GL11.glScalef(0.5f, 0.5f, 0.5f);
       GL11.glLineWidth((float)n7);
       GL11.glEnable(2848);
       GL11.glColor4f(n9, n10, n11, n8);
       GL11.glBegin(3);
       int n12 = n5;
       while (n12 <= n6) {
           GL11.glVertex2d(n + Math.sin(n12 * 3.141592653589793 / 180.0) * n3, n2 + Math.cos(n12 * 3.141592653589793 / 180.0) * n3);
           ++n12;
       }
       GL11.glEnd();
       GL11.glDisable(2848);
       GL11.glScalef(2.0f, 2.0f, 2.0f);
       GL11.glEnable(3553);
       GL11.glDisable(3042);
       GL11.glEnable(2929);
       GL11.glDisable(2848);
       GL11.glHint(3154, 4352);
       GL11.glHint(3155, 4352);
   }

   public static void startSmooth() {
      GL11.glEnable(2848);
      GL11.glEnable(2881);
      GL11.glEnable(2832);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
      GL11.glHint(3153, 4354);
   }

   public static void endSmooth() {
      GL11.glDisable(2848);
      GL11.glDisable(2881);
      GL11.glEnable(2832);
   }
}
