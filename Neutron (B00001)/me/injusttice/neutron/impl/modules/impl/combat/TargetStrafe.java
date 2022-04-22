package me.injusttice.neutron.impl.modules.impl.combat;

import java.util.Iterator;
import java.awt.Color;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.api.events.impl.EventRender3D;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.impl.modules.ModuleManager;
import me.injusttice.neutron.impl.modules.impl.movement.Flight;
import me.injusttice.neutron.impl.modules.impl.movement.Speed;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public final class TargetStrafe extends Module {

   public static float currentYaw;
   public DoubleSet radius = new DoubleSet("Radius", 2.0, 0.1, 10.0, 1.0D);
   public BooleanSet controllable = new BooleanSet("Controllable", true);
   public BooleanSet holdspace = new BooleanSet("Hold Space", true);
   public BooleanSet render = new BooleanSet("Render", true);
   public byte direction;

   public TargetStrafe() {
      super("TargetStrafe", 0, Category.COMBAT);
      addSettings(radius, controllable, holdspace, render);
   }

   @EventTarget
   public void onMotionUpdate(EventMotion event) {
      this.setDisplayName("Target Strafe");
      if (event.isPre()) {
         if(!controllable.isEnabled()) return;
         if (mc.thePlayer.isCollidedHorizontally) {
            direction = (byte)(-direction);
            return;
         }

         if (mc.gameSettings.keyBindLeft.isKeyDown()) {
            direction = 1;
            return;
         }

         if (mc.gameSettings.keyBindRight.isKeyDown()) {
            direction = -1;
         }
      }
   }

   public static void drawLinesAroundPlayer(Entity entity, float partialTicks, double rad, double height) {
      GL11.glPushMatrix();
      GL11.glDisable(GL11.GL_TEXTURE_2D);
      GL11.glDisable(GL11.GL_DEPTH_TEST);
      GL11.glDepthMask(false);
      GL11.glLineWidth(2.0f);
      GL11.glBegin(GL11.GL_LINE_STRIP);

      final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosX;
      final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosY;
      final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosZ;

      final float r = ((float) 1 / 255) * Color.WHITE.getRed();
      final float g = ((float) 1 / 255) * Color.WHITE.getGreen();
      final float b = ((float) 1 / 255) * Color.WHITE.getBlue();

      final double pix2 = Math.PI * 2.0D;

      for (int i = 0; i <= 90; ++i) {
         GL11.glVertex3d(x + rad * Math.cos(i * pix2 / 45), y + height, z + rad * Math.sin(i * pix2 / 45));
      }

      GL11.glEnd();
      GL11.glDepthMask(true);
      GL11.glEnable(GL11.GL_DEPTH_TEST);
      GL11.glEnable(GL11.GL_TEXTURE_2D);
      GL11.glPopMatrix();
   }

   @EventTarget
   public void onRender3D(EventRender3D event) {
      KillAura killAura = ModuleManager.getModule(KillAura.class);
      Speed speed = ModuleManager.getModule(Speed.class);
      Flight flight = ModuleManager.getModule(Flight.class);
      Iterator var5 = mc.theWorld.getLoadedEntityList().iterator();

      while(var5.hasNext()) {
         Entity entity = (Entity)var5.next();
         boolean colorchange = speed.isEnabled() || flight.isEnabled();
         int color;
         if (killAura.target == entity && colorchange && !holdspace.isEnabled()) {
            color = Color.white.getRGB();
         } else if (killAura.target == entity && colorchange && holdspace.isEnabled() && mc.gameSettings.keyBindJump.isKeyDown()) {
            color = Color.white.getRGB();
         } else {
            color = Color.white.getRGB();
         }

         if (killAura.isEnabled() && render.isEnabled() && entity instanceof EntityPlayer && entity != mc.thePlayer && killAura.target == entity) {
            //drawLinesAroundPlayer(entity, mc.getRenderManager(), radius.getValue(), event.getPartialTicks(), 12, 3.0F, Color.black.getRGB());
            //drawLinesAroundPlayer(entity, mc.getRenderManager(), radius.getValue(), event.getPartialTicks(), 12, 2.0F, color);
            drawLinesAroundPlayer(entity, event.getPartialTicks(), radius.getValue(), 12 / 100);
         }
      }
   }
}
