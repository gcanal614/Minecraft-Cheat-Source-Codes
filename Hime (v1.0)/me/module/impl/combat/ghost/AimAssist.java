package me.module.impl.combat.ghost;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.Hime;
import me.command.friend.FriendManager;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.module.impl.combat.Antibot;
import me.settings.Setting;
import me.util.RotationUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;


public class AimAssist extends Module {

	private long lastClick;
	private long hold;
	
	public Setting speed;
	
	
	public Setting range;
	
	public Setting mode;
	
	public Setting sword;
	
	public Setting walls;
	
	public Setting click;

    public Setting existed;
	
	public Setting fov;
	
	public Setting vertical;
		
	public Setting horizontal;
	
	private double holdLength;
	private double min;
	private double max;
	
	EntityLivingBase target;
	protected Random rand = new Random();
	
	public AimAssist() {
		super("AimAssist",0, Category.COMBAT);
		
		//Hime.instance.settingsManager.rSetting(this.min2 = new Setting("MinCPS", this, 8, 1, 20, false));
		Hime.instance.settingsManager.rSetting(this.speed = new Setting("Aim Speed", this, 1.00d, 0d, 1.00d, false));;
		Hime.instance.settingsManager.rSetting(this.range = new Setting("Aim Range", this, 4.20d, 0d, 8.10d, false));
		Hime.instance.settingsManager.rSetting(this.horizontal = new Setting("Horizontal", this, 9.88d, 0d, 10.00d, false));
		Hime.instance.settingsManager.rSetting(this.vertical = new Setting("Vertical", this, 9.88d, 0d, 10.00d, false));
		Hime.instance.settingsManager.rSetting(this.sword = new Setting("Holding Sword", this, false));
		Hime.instance.settingsManager.rSetting(this.click = new Setting("Aim On Click", this, false));
		Hime.instance.settingsManager.rSetting(this.walls = new Setting("Aim Behind Walls", this, false));
		Hime.instance.settingsManager.rSetting(this.fov = new Setting("Aim FOV", this, 360, 0, 360, true));
	    Hime.instance.settingsManager.rSetting(this.existed = new Setting("Existed", this, 30, 0, 500, true));
		
		ArrayList<String> options = new ArrayList<String>();
        options.add("Distance");
        options.add("Health");
        options.add("Angle");
        Hime.instance.settingsManager.rSetting(mode = new Setting("Aim Priority Mode", this, "Distance", options));
	}
	

    @Handler
    public void onUpdate(EventUpdate event) {
    	 this.setSuffix(mode.getValString());
    	if ((click.getValBoolean() && !this.mc.gameSettings.keyBindAttack.isKeyDown())) {
			return;
		}
    //	if(mc.thePlayer.getHeldItem().getItem() != null) {
    	///  if (!(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) && this.sword.getValBoolean()) {
			//  return;
		 // }
    	//}
    	  if (this.mode.getValString().equalsIgnoreCase("Health")) {
              this.target = getHealthPriority();
            } else if (this.mode.getValString().equalsIgnoreCase("Angle")) {
              this.target = getAnglePriority();
            } else {
              this.target = getClosest(this.range.getValDouble());
            } 
    	
    	
    	if(this.target != null) {
    		double horizontalSpeed =  horizontal.getValDouble() * 3.0 + (horizontal.getValDouble() > 0.0 ? this.rand.nextDouble() : 0.0);
			double verticalSpeed = vertical.getValDouble() * 3.0 + (vertical.getValDouble() > 0.0 ? this.rand.nextDouble() : 0.0);
    		horizontalSpeed *= speed.getValDouble();
			verticalSpeed *= speed.getValDouble();
			
    		this.faceTarget(target, 0.0f, (float) verticalSpeed);
			this.faceTarget(target, (float) horizontalSpeed, 0.0f);
    	}
	
	}
	
    protected float getRotation(float currentRotation, float targetRotation, float maxIncrement) {
		float deltaAngle = MathHelper.wrapAngleTo180_float(targetRotation - currentRotation);
		if (deltaAngle > maxIncrement) {
			deltaAngle = maxIncrement;
		}
		if (deltaAngle < -maxIncrement) {
			deltaAngle = -maxIncrement;
		}
		return currentRotation + deltaAngle / 2.0f;
	}
    
    private void faceTarget(EntityLivingBase target, float yawspeed, float pitchspeed) {
		EntityPlayerSP player = this.mc.thePlayer;
		float yaw = getRotations(target)[0];
		float pitch = getRotations(target)[1];
		player.rotationYaw = this.getRotation(player.rotationYaw, yaw, yawspeed);
		player.rotationPitch = this.getRotation(player.rotationPitch, pitch, pitchspeed);
	}
    
    public float[] getRotations(EntityLivingBase e) {
          return RotationUtils.getRotations(e); 
      }
    
	@Override
	public void onEnable() {
		super.onEnable();

	}
	  public boolean canAttack(EntityLivingBase player) {
		    if (player == this.mc.thePlayer)
		      return false; 
		    if (player instanceof EntityPlayer || player instanceof net.minecraft.entity.passive.EntityAnimal || player instanceof net.minecraft.entity.monster.EntityMob || player instanceof net.minecraft.entity.passive.EntityVillager) {
		      if (player instanceof EntityPlayer && !(Hime.instance.moduleManager.getModule("Players")).isToggled())
		        return false; 
		      if (player instanceof net.minecraft.entity.passive.EntityAnimal && !(Hime.instance.moduleManager.getModule("Animals")).isToggled())
		        return false; 
		      if (player instanceof net.minecraft.entity.monster.EntityMob && !(Hime.instance.moduleManager.getModule("Monsters")).isToggled())
		        return false; 
		      if (player instanceof net.minecraft.entity.passive.EntityVillager && !(Hime.instance.moduleManager.getModule("Villagers")).isToggled())
		        return false; 
		      if (!player.isEntityAlive() && !(Hime.instance.moduleManager.getModule("Dead")).isToggled())
		        return false; 
		    } 
		    if (player instanceof EntityPlayer && Antibot.isBot((EntityPlayer)player))
		      return false; 
		    if (player instanceof EntityPlayer && FriendManager.instance.isFriend(player.getName()))
		        return false; 
		    if(!isInFOV(player, this.fov.getValDouble()))
		        return false;
		    if(!mc.thePlayer.canEntityBeSeen(player) && !this.walls.getValBoolean())
		    	return false;
		    if (player instanceof EntityPlayer) {
		      if (isTeam((EntityPlayer)this.mc.thePlayer, (EntityPlayer)player) && (Hime.instance.moduleManager.getModule("Teams")).isToggled())
		        return false; 
		    } 
		    if (player.isInvisible() && !(Hime.instance.moduleManager.getModule("Invisibles")).isToggled())
		      return false; 
		    return player.ticksExisted > this.existed.getValDouble();
		  }
	  
	  private boolean isInFOV(EntityLivingBase entity, double angle) {
	      angle *= .5D;
	      double angleDiff = getAngleDifference(mc.thePlayer.rotationYaw, getRotations(entity.posX, entity.posY, entity.posZ)[0]);
	      return (angleDiff > 0 && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0);
	  }

	  private float getAngleDifference(float dir, float yaw) {
	      float f = Math.abs(yaw - dir) % 360F;
	      float dist = f > 180F ? 360F - f : f;
	      return dist;
	  }

	  private float[] getRotations(double x, double y, double z) {
	      double diffX = x + .5D - mc.thePlayer.posX;
	      double diffY = (y + .5D) / 2D - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
	      double diffZ = z + .5D - mc.thePlayer.posZ;

	      double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
	      float yaw = (float)(Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
	      float pitch = (float)-(Math.atan2(diffY, dist) * 180D / Math.PI);

	      return new float[] { yaw, pitch };
	  }
	  public static boolean isTeam(EntityPlayer e, EntityPlayer e2) {
	    if (e2.getTeam() != null && e.getTeam() != null) {
	      Character target = Character.valueOf(e2.getDisplayName().getFormattedText().charAt(1));
	      Character player = Character.valueOf(e.getDisplayName().getFormattedText().charAt(1));
	      if (target.equals(player))
	        return true; 
	    } else {
	      return true;
	    } 
	    return false;
	  }

	  private EntityLivingBase getClosest(double range) {
		    double dist = range;
		    EntityLivingBase target = null;
		    for (Object object : this.mc.theWorld.loadedEntityList) {
		      Entity entity = (Entity)object;
		      if (entity instanceof EntityLivingBase) {
		        EntityLivingBase player = (EntityLivingBase)entity;
		        if (canAttack(player)) {
		          double currentDist = this.mc.thePlayer.getDistanceToEntity((Entity)player);
		          if (currentDist <= dist) {
		            dist = currentDist;
		            target = player;
		          } 
		        } 
		      } 
		    } 
		    return target;
		  }
	  public EntityLivingBase getHealthPriority() {
		    List<EntityLivingBase> entities = new ArrayList<>();
		    for (Entity e : this.mc.theWorld.loadedEntityList) {
		      if (e instanceof EntityLivingBase) {
		        EntityLivingBase player = (EntityLivingBase)e;
		        if (this.mc.thePlayer.getDistanceToEntity((Entity)player) < (this.range.getValDouble()) && canAttack(player))
		          entities.add(player); 
		      } 
		    } 
		    entities.sort((o1, o2) -> (int)(o1.getHealth() - o2.getHealth()));
		    if (entities.isEmpty())
		      return null; 
		    return entities.get(0);
		  }
		  
		  public static int randomNumber(int max, int min) {
		    return Math.round(min + (float)Math.random() * (max - min));
		  }
		  
		  public EntityLivingBase getAnglePriority() {
		    List<EntityLivingBase> entities = new ArrayList<>();
		    for (Entity e : this.mc.theWorld.loadedEntityList) {
		      if (e instanceof EntityLivingBase) {
		        EntityLivingBase player = (EntityLivingBase)e;
		        if (this.mc.thePlayer.getDistanceToEntity((Entity)player) < (this.range.getValDouble()) && canAttack(player))
		          entities.add(player); 
		      } 
		    } 
		    entities.sort((o1, o2) -> {
		          float[] rot1 = RotationUtils.getRotations(o1);
		          float[] rot2 = RotationUtils.getRotations(o2);
		          return (int)(this.mc.thePlayer.rotationYaw - rot1[0] - this.mc.thePlayer.rotationYaw - rot2[0]);
		        });
		    if (entities.isEmpty())
		      return null; 
		    return entities.get(0);
		  }
	  
}
