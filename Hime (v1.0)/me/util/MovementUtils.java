package me.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.event.impl.EventUpdate;
import me.event.impl.MoveEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class MovementUtils {
	
	protected static Minecraft mc = Minecraft.getMinecraft();
	private static final List<Double> frictionValues = new ArrayList<Double>();
	
	
	public static double getPosForSetPosX(double value) {
		double yaw = Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationYaw);
		double x = -Math.sin(yaw) * value;
		return x;
	}

	public static double getPosForSetPosZ(double value) {
		double yaw = Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationYaw);
		double z = Math.cos(yaw) * value;
		return z;
	}
	
	
	public static boolean isOnGround(double height) {
	    if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty())
	      return true; 
	    return false;
	  }
	
	
	  public static void doStrafe() {
	    	doStrafe(getSpeed());
	    }
	
	  public final static void doStrafe(double speed) {
	        if(!mc.thePlayer.isMoving())  return;

	        final double yaw = getYaw(true);
	        mc.thePlayer.motionX = -Math.sin(yaw) * speed;
	        mc.thePlayer.motionZ = Math.cos(yaw) * speed;
	    }
	  
	  public final static double getSpeed() {
			return Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
		}
	  
	  public final static double getYaw(boolean strafing) {
	        float rotationYaw = strafing ? mc.thePlayer.rotationYawHead : mc.thePlayer.rotationYaw;
	        float forward = 1F;

	        final double moveForward = mc.thePlayer.movementInput.moveForward;
	        final double moveStrafing = mc.thePlayer.movementInput.moveStrafe;
	        final float yaw = mc.thePlayer.rotationYaw;
	        
	        if (moveForward < 0) {
	        	rotationYaw += 180F;
	        }

	        if (moveForward < 0) {
	        	forward = -0.5F;
	        } else if(moveForward > 0) {
	        	forward = 0.5F;
	        }

	        if (moveStrafing > 0) {
	        	rotationYaw -= 90F * forward;
	        } else if(moveStrafing < 0) {
	        	rotationYaw += 90F * forward;
	        }

	        return Math.toRadians(rotationYaw);
	    }
	  
	  
	  public static void setSpeed(double moveSpeed, float yaw, double strafe, double forward) {

	        double fforward = forward;
	        double sstrafe = strafe;
	        float yyaw = yaw;
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
	  
	  	public static void actualSetSpeed(double moveSpeed) {
	  		setSpeed(moveSpeed, mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward);
	  	}
	  
	     public static void actualSetSpeedEvent(EventUpdate event, double speed) {
	        double forward = mc.thePlayer.movementInput.moveForward;
	        double strafe = mc.thePlayer.movementInput.moveStrafe;
	        float yaw = mc.thePlayer.rotationYaw;
	        if (forward == 0.0 && strafe == 0.0) {
	            event.setX(mc.thePlayer.motionX = 0.0);
	            event.setZ(mc.thePlayer.motionZ = 0.0);
	        }
	        else {
	            if (forward != 0.0) {
	                if (strafe > 0.0) {
	                    yaw += ((forward > 0.0) ? -45 : 45);
	                }
	                else if (strafe < 0.0) {
	                    yaw += ((forward > 0.0) ? 45 : -45);
	                }
	                strafe = 0.0;
	                if (forward > 0.0) {
	                    forward = 1.0;
	                }
	                else if (forward < 0.0) {
	                    forward = -1.0;
	                }
	            }
	            event.setX(mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
	            event.setZ(mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
	        }
	      }


	public static void setSpeed(MoveEvent moveEvent, double moveSpeed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += ((forward > 0.0) ? -45 : 45);
            }
            else if (strafe < 0.0) {
                yaw += ((forward > 0.0) ? 45 : -45);
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            }
            else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        }
        else if (strafe < 0.0) {
            strafe = -1.0;
        }
        final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        moveEvent.actualSetSpeedX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.actualSetSpeedZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }
	
	public static double calculateFriction(final double moveSpeed, final double lastDist, final double baseMoveSpeedRef) {
        frictionValues.clear();
        frictionValues.add(lastDist - lastDist / 159.9999);
        frictionValues.add(lastDist - (moveSpeed - lastDist) / 33.3);
        final double materialFriction = mc.thePlayer.isInWater() ? 0.89 : (mc.thePlayer.isInLava() ? 0.535 : 0.98);
        frictionValues.add(lastDist - baseMoveSpeedRef * (1.0 - materialFriction));
        Collections.sort(frictionValues);
        return frictionValues.get(0);
    }

	 public final static void doStrafe(double speed, double yaw) {
        mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        mc.thePlayer.motionZ = Math.cos(yaw) * speed;
    }

	public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2875;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }

	public static double getSpeed(EntityPlayer player) {
		return Math.sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ);
	}

	public static int getSpeedAmplifier() {
     if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
		 return mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
     }
	return 0;
	}



}
