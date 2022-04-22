package wtf.astronicy.IMPL.utils;

import wtf.astronicy.API.events.player.MoveEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public final class MovementUtils {
   private static final Minecraft mc = Minecraft.getMinecraft();

   public static void setSpeed(MoveEvent moveEvent, double moveSpeed) {
      setSpeed(moveEvent, moveSpeed, mc.thePlayer.rotationYaw, (double)mc.thePlayer.movementInput.moveStrafe, (double)mc.thePlayer.movementInput.moveForward);
   }
   
   public static void setSpeed(double moveSpeed) {
       setSpeed(moveSpeed, mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward);
   }
   
   public static void setSpeed(double moveSpeed, float yaw, double strafe, double forward) {
       if (forward != 0.0D) {
           if (strafe > 0.0D) {
               yaw += ((forward > 0.0D) ? -45 : 45);
           } else if (strafe < 0.0D) {
               yaw += ((forward > 0.0D) ? 45 : -45);
           }
           strafe = 0.0D;
           if (forward > 0.0D) {
               forward = 1.0D;
           } else if (forward < 0.0D) {
               forward = -1.0D;
           }
       }
       if (strafe > 0.0D) {
           strafe = 1.0D;
       } else if (strafe < 0.0D) {
           strafe = -1.0D;
       }
       double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
       double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
       mc.thePlayer.motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
       mc.thePlayer.motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;
   }
   
   public static boolean isBlockUnder(final Minecraft mc) {
       return mc.theWorld.checkBlockCollision(mc.thePlayer.getEntityBoundingBox().addCoord(0.0, -1.0, 0.0));
   }
   
   public static float getSensitivityMultiplier() {
	      float f = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
	      return f * f * f * 8.0F * 0.15F;
	   }

   public static boolean isOverVoid(final Minecraft mc) {
       final AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox();
       final double height = bb.maxY - bb.minY;

       double offset = height;

       AxisAlignedBB bbPos;

       while (!mc.theWorld.checkBlockCollision((bbPos = bb.addCoord(0, -offset, 0)))) {
           if (bbPos.minY <= 0.0) return true;

           offset += height;
       }

       return false;
   }
   
   public static double getJumpHeight() {
	      return getJumpHeight(0.41999998688697815D);
	   }
   
   public static double getJumpHeight(double baseJumpHeight) {
	      return baseJumpHeight + (double)((float)getJumpBoostModifier() * 0.1F);
	   }
   
   public static int getJumpBoostModifier() {
	      return getPotionModifier(mc.thePlayer, Potion.jump);
	   }
   
   public static int getPotionModifier(EntityLivingBase entity, Potion potion) {
	      PotionEffect effect = entity.getActivePotionEffect(potion);
	      return effect != null ? effect.getAmplifier() + 1 : 0;
	   }
   
   public static boolean isOnGround(double height) {
	      return !Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
	   }

   public static void setSpeed(MoveEvent moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
      double forward = pseudoForward;
      double strafe = pseudoStrafe;
      float yaw = pseudoYaw;
      if (pseudoForward != 0.0D) {
         if (pseudoStrafe > 0.0D) {
            yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? -45 : 45);
         } else if (pseudoStrafe < 0.0D) {
            yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? 45 : -45);
         }

         strafe = 0.0D;
         if (pseudoForward > 0.0D) {
            forward = 1.0D;
         } else if (pseudoForward < 0.0D) {
            forward = -1.0D;
         }
      }

      if (strafe > 0.0D) {
         strafe = 1.0D;
      } else if (strafe < 0.0D) {
         strafe = -1.0D;
      }

      double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
      double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
      moveEvent.x = forward * moveSpeed * mx + strafe * moveSpeed * mz;
      moveEvent.z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
   }

   public static double getBaseMoveSpeed() {
      double baseSpeed = 0.2875D;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         baseSpeed *= 1.0D + 0.2D * (double)(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
      }

      return baseSpeed;
   }

   public static double getJumpBoostModifier(double baseJumpHeight) {
      if (mc.thePlayer.isPotionActive(Potion.jump)) {
         int amplifier = mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
         baseJumpHeight += (double)((float)(amplifier + 1) * 0.1F);
      }

      return baseJumpHeight;
   }

   public static float getMovementDirection() {
       final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
       final float forward = player.moveForward;
       final float strafe = player.moveStrafing;
       float direction = 0.0f;
       if (forward < 0.0f) {
           direction += 180.0f;
           if (strafe > 0.0f) {
               direction += 45.0f;
           }
           else if (strafe < 0.0f) {
               direction -= 45.0f;
           }
       }
       else if (forward > 0.0f) {
           if (strafe > 0.0f) {
               direction -= 45.0f;
           }
           else if (strafe < 0.0f) {
               direction += 45.0f;
           }
       }
       else if (strafe > 0.0f) {
           direction -= 90.0f;
       }
       else if (strafe < 0.0f) {
           direction += 90.0f;
       }
       direction += player.rotationYaw;
       return MathHelper.wrapAngleTo180_float(direction);
   }
}
