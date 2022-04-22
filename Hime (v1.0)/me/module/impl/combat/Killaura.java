package me.module.impl.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import me.Hime;
import me.notification.Notification;
import me.notification.NotificationManager;
import me.notification.NotificationType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.command.friend.FriendManager;
import me.event.impl.Event3D;
import me.event.impl.EventRenderHUD;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.ColorUtil;
import me.util.RainbowUtil;
import me.util.RenderUtil;
import me.util.RotationUtils;
import me.util.TimeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class Killaura extends Module {
  public Setting minAps;
  
  public Setting maxAps;
  
  public Setting targetMode;
  
  public Setting priority;
  
  public Setting Range;
  public Setting Range2;
  
  public Setting onhit;
  
  public Setting AutoBlock;
  
  public Setting AutoBlockMode;
  
  public Setting hudmode;
  
  public Setting KeepSprint;
  
  public Setting Rotations;
  
  public Setting RotationsMode;
  
 public Setting autodisable;
  
  public Setting abrange;
  
  public Setting cracksize;
  
  public Setting esp;
  
  public Setting switchdelay;
  
  public Setting existed;
  
  public Setting fov;
  
  public Setting raycast;
  
  public Setting walls;
 
  public Setting AACCheck;
  
  public Setting swing;
  
//  public Setting hvh;
  
  public EntityLivingBase target;
  
  public boolean attacking = false;
  
  public List<EntityLivingBase> loaded = new ArrayList<>();
  
  TimeUtil timer = new TimeUtil();
  
  private float lastHealth = 0.0F;
  
  public int index = 0;
  
  TimeUtil switchTimer = new TimeUtil();
  
  boolean direction;
  
  boolean blocking;
  

  
  float oldHealth;
  
  double delay;
  
  boolean step;
  
  public Killaura() {
    super("Killaura", Keyboard.KEY_R, Category.COMBAT);
    this.direction = false;
    this.oldHealth = 20.0F;
    this.delay = 0.0D;
    this.step = false;
    ArrayList<String> targetModes = new ArrayList<>();
    targetModes.add("Single");
    targetModes.add("Switch");
    targetModes.add("Multi");
    ArrayList<String> priorities = new ArrayList<>();
    priorities.add("Health");
    priorities.add("Angle");
    priorities.add("Distance");
    ArrayList<String> AutoBlockModes = new ArrayList<>();
    AutoBlockModes.add("Vanilla");
    AutoBlockModes.add("Interact");
    AutoBlockModes.add("NCP");
    AutoBlockModes.add("Hypixel");
    AutoBlockModes.add("Legit");
    AutoBlockModes.add("Redesky");
    AutoBlockModes.add("Ghostly");
    AutoBlockModes.add("Fake");
    AutoBlockModes.add("None");
    ArrayList<String> RotationModes = new ArrayList<>();
    RotationModes.add("Normal");
    RotationModes.add("ClientSide");
    RotationModes.add("Smooth");
    RotationModes.add("AAC");
    ArrayList<String> options2 = new ArrayList<String>();
    options2.add("Off");
    options2.add("On");
    ArrayList<String> espModes = new ArrayList<String>();
    espModes.add("None");
    espModes.add("Normal");
    espModes.add("Sigma");
    Hime.instance.settingsManager.rSetting(this.minAps = new Setting("MinAPS", this, 8.0D, 1.0D, 20.0D, false));
    Hime.instance.settingsManager.rSetting(this.maxAps = new Setting("MaxAPS", this, 12.0D, 1.0D, 20.0D, false));
    //min/max range
    Hime.instance.settingsManager.rSetting(this.Range2 = new Setting("Range", this, 4.0D, 0.0D, 10.0D, false));
    Hime.instance.settingsManager.rSetting(this.targetMode = new Setting("Target Mode", this, "Single", targetModes));
    Hime.instance.settingsManager.rSetting(this.priority = new Setting("Target Priority", this, "Angle", priorities));
    Hime.instance.settingsManager.rSetting(this.AutoBlock = new Setting("Auto Block", this, true));
//    Hime.instance.settingsManager.rSetting(this.hvh = new Setting("HVH", this, false));
    Hime.instance.settingsManager.rSetting(this.Range = new Setting("AutoBlock Range", this, 10, 4, 20, false));
    Hime.instance.settingsManager.rSetting(this.AutoBlockMode = new Setting("Auto Block Mode", this, "NCP", AutoBlockModes));
    Hime.instance.settingsManager.rSetting(this.Rotations = new Setting("Rotations", this, true));
    Hime.instance.settingsManager.rSetting(this.RotationsMode = new Setting("Rotations Mode", this, "Normal", RotationModes));
    Hime.instance.settingsManager.rSetting(this.KeepSprint = new Setting("Keep Sprint", this, true));
    Hime.instance.settingsManager.rSetting(this.swing = new Setting("Swing on Hit", this, true));
  //  Hime.instance.settingsManager.rSetting(this.AACCheck = new Setting("ACCCheck", this, false));
    Hime.instance.settingsManager.rSetting(this.esp = new Setting("AuraESP Mode", this, "Normal", espModes));
    Hime.instance.settingsManager.rSetting(this.hudmode = new Setting("TargetHud Mode", this, "Informatic", options2));
    Hime.instance.settingsManager.rSetting(this.autodisable = new Setting("AutoDisable", this, false));
    Hime.instance.settingsManager.rSetting(this.cracksize = new Setting("Crack Size", this, 5, 0, 15, true));
    Hime.instance.settingsManager.rSetting(this.walls = new Setting("Walls", this, true));
    Hime.instance.settingsManager.rSetting(this.switchdelay = new Setting("SwitchDelay", this, 400.0D, 1.0D, 3000.0D, false));
    Hime.instance.settingsManager.rSetting(this.raycast = new Setting("RayCast",  this, false));
    Hime.instance.settingsManager.rSetting(this.fov = new Setting("FOV", this, 360, 0, 360, true));
    Hime.instance.settingsManager.rSetting(this.existed = new Setting("Ticks Existed", this, 30, 0, 500, true));
    Hime.instance.settingsManager.rSetting(this.onhit = new Setting("Rotate On Hit", this, false));
 
  }
  
  private boolean isBlockUnder() {
    for (int i = (int)(this.mc.thePlayer.posY - 1.0D); i > 0; ) {
      BlockPos pos = new BlockPos(this.mc.thePlayer.posX, i, this.mc.thePlayer.posZ);
      if (this.mc.theWorld.getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockAir) {
        i--;
        continue;
      } 
      return true;
    } 
    return false;
  }
  
  @Handler
  public void onUpdate(EventUpdate event) {
	  if (this.autodisable.getValBoolean() && !mc.thePlayer.isEntityAlive()) {
		  NotificationManager.show(new Notification(NotificationType.ERROR, "Killaura Alert", "Disabled Aura Due Your Death!", 2));
		  toggle();
			return;
		}
    if (!isBlockUnder() || this.mc.thePlayer.isCollidedHorizontally)
      this.direction = !this.direction; 
    
    if(this.target == null) {
    	this.attacking = false;
    }else {
    	this.attacking = true;
    }
    
    
    setSuffix(this.targetMode.getValString());
  
    if(event.isPre()) {
      if (this.targetMode.getValString().equalsIgnoreCase("Single")) {
        if (this.priority.getValString().equalsIgnoreCase("Health")) {
          this.target = getHealthPriority();
        } else if (this.priority.getValString().equalsIgnoreCase("Angle")) {
          this.target = getAnglePriority();
        } else {
          this.target = getClosest((this.AutoBlock.getValBoolean() ? this.Range.getValDouble() : this.Range2.getValDouble()));
        } 
        if (this.target != null) {
          if (!this.KeepSprint.getValBoolean()) {
            this.mc.thePlayer.setSprinting(false);
            this.mc.gameSettings.keyBindSprint.pressed = false;
          } 
          if (this.mc.thePlayer.getDistanceToEntity((Entity)this.target) <= this.Range2.getValDouble()) {
            boolean block = this.AutoBlock.getValBoolean();
            if(!this.onhit.getValBoolean()) {
              if(this.Rotations.getValBoolean() ) {
               if(!this.RotationsMode.getValString().equalsIgnoreCase("ClientSide")) {
              float[] rots = getRotations(this.target, event);
              event.setYaw(rots[0]);
              event.setPitch(rots[1]);
              mc.thePlayer.rotationPitchHead = rots[1];
              mc.thePlayer.rotationYawHead = rots[0];
              mc.thePlayer.renderYawOffset = rots[0];
              }else {
            	float[] rots = RotationUtils.getRotations(target);
                mc.thePlayer.rotationYaw = rots[0];
                mc.thePlayer.rotationPitch = rots[1];
                mc.thePlayer.rotationPitchHead = rots[1];
                mc.thePlayer.rotationYawHead = rots[0];
                mc.thePlayer.renderYawOffset = rots[0];  
              }
              }
            }
            if (this.timer.hasTimePassed(randomClickDelay(this.minAps.getValDouble(), this.maxAps.getValDouble()))) {
              if (this.mc.thePlayer.isBlocking() && block && this.AutoBlockMode.getValString().equalsIgnoreCase("NCP")) {
              //   this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)); 
            	 mc.getNetHandler().getNetworkManager().sendPacket((new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1),EnumFacing.DOWN)));   
            // Hime.addClientChatMessage("unblocked");
              }
              this.blocking = false;
              if(block && this.AutoBlockMode.getValString().equalsIgnoreCase("Hypixel")) {
            	  mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0,0), EnumFacing.DOWN));  
              }
              
              if(this.onhit.getValBoolean()) {
                  if(this.Rotations.getValBoolean() ) {
                      if(!this.RotationsMode.getValString().equalsIgnoreCase("ClientSide")) {
                     float[] rots = getRotations(this.target, event);
                     event.setYaw(rots[0]);
                     event.setPitch(rots[1]);
                     mc.thePlayer.rotationPitchHead = rots[1];
                     mc.thePlayer.rotationYawHead = rots[0];
                     mc.thePlayer.renderYawOffset = rots[0];
                     }else {
                   	float[] rots = RotationUtils.getRotations(target);
                       mc.thePlayer.rotationYaw = rots[0];
                       mc.thePlayer.rotationPitch = rots[1];
                       mc.thePlayer.rotationPitchHead = rots[1];
                       mc.thePlayer.rotationYawHead = rots[0];
                       mc.thePlayer.renderYawOffset = rots[0];  
                     }
                     }
                  }
              
              Entity rayCastEntity = this.raycast.getValBoolean() ? this.raycast(this.Range2.getValDouble() + 1.0f, this.target) : null;
             
              if(this.swing.getValBoolean()) {
                  this.mc.thePlayer.swingItem();
                 }else {
                	 mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                 }
              this.attack(target);
              this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity(rayCastEntity == null ? target : rayCastEntity, C02PacketUseEntity.Action.ATTACK));
              this.timer.reset();
            } 
          } 
        } 
      } else if (this.targetMode.getValString().equalsIgnoreCase("Switch")) {
    	  
        this.loaded = getTargets();
        if (this.loaded.size() == 0)
          return; 
        long switchdelay = (long)this.switchdelay.getValDouble();
        if (this.switchTimer.hasTimePassed(switchdelay)) {
          if (this.index < this.loaded.size() - 1) {
            this.index++;
          } else {
            this.index = 0;
          } 
          this.switchTimer.reset();
        } 
        try {
        this.target = this.loaded.get(this.index);
        }catch(Exception e) {
        	e.printStackTrace();
        }
        if (this.target != null) {
          if (!this.KeepSprint.getValBoolean()) {
            this.mc.thePlayer.setSprinting(false);
            this.mc.gameSettings.keyBindSprint.pressed = false;
          } 
          if (this.mc.thePlayer.getDistanceToEntity((Entity)this.target) < this.Range2.getValDouble()) {
            boolean block = this.AutoBlock.getValBoolean();
            if(!this.onhit.getValBoolean()) {
                if(this.Rotations.getValBoolean() ) {
                    if(!this.RotationsMode.getValString().equalsIgnoreCase("ClientSide")) {
                   float[] rots = getRotations(this.target, event);
                   event.setYaw(rots[0]);
                   event.setPitch(rots[1]);
                   mc.thePlayer.rotationPitchHead = rots[1];
                   mc.thePlayer.rotationYawHead = rots[0];
                   mc.thePlayer.renderYawOffset = rots[0];
                   }else {
                 	float[] rots = RotationUtils.getRotations(target);
                     mc.thePlayer.rotationYaw = rots[0];
                     mc.thePlayer.rotationPitch = rots[1];
                     mc.thePlayer.rotationPitchHead = rots[1];
                     mc.thePlayer.rotationYawHead = rots[0];
                     mc.thePlayer.renderYawOffset = rots[0];  
                   }
                   }
           }
          //  if (this.AACCheck.getValBoolean() && (Math.round(event.getYaw()) - Math.round(RotationUtils.getRotations(this.target)[0]) > 3 || Math.round(event.getPitch()) - Math.round(RotationUtils.getRotations(this.target)[1]) > 3))
            //  return; 
            if (this.timer.hasTimePassed(randomClickDelay(this.minAps.getValDouble(), this.maxAps.getValDouble()))) {
            	  if (this.mc.thePlayer.isBlocking() && block && this.AutoBlockMode.getValString().equalsIgnoreCase("NCP")) {
                      //   this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)); 
                    	mc.getNetHandler().getNetworkManager().sendPacket((new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1),EnumFacing.DOWN)));   
                  //      Hime.addClientChatMessage("unblocked");
                      }
                      this.blocking = false;
            
              if(this.onhit.getValBoolean()) {
                  if(this.Rotations.getValBoolean() ) {
                      if(!this.RotationsMode.getValString().equalsIgnoreCase("ClientSide")) {
                     float[] rots = getRotations(this.target, event);
                     event.setYaw(rots[0]);
                     event.setPitch(rots[1]);
                     mc.thePlayer.rotationPitchHead = rots[1];
                     mc.thePlayer.rotationYawHead = rots[0];
                     mc.thePlayer.renderYawOffset = rots[0];
                     }else {
                   	float[] rots = RotationUtils.getRotations(target);
                       mc.thePlayer.rotationYaw = rots[0];
                       mc.thePlayer.rotationPitch = rots[1];
                       mc.thePlayer.rotationPitchHead = rots[1];
                       mc.thePlayer.rotationYawHead = rots[0];
                       mc.thePlayer.renderYawOffset = rots[0];  
                     }
                     }
                  }
              
              Entity rayCastEntity = this.raycast.getValBoolean() ? this.raycast(this.Range2.getValDouble() + 1.0f, this.target) : null;
             if(this.swing.getValBoolean()) {
              this.mc.thePlayer.swingItem();
             }else {
            	 mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
             }
              this.attack(target);
              this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity(rayCastEntity == null ? target : rayCastEntity, C02PacketUseEntity.Action.ATTACK));
              this.timer.reset();
            } 
          } 
        } 
      } else {
        this.loaded = getTargets();
        if (this.loaded.size() == 0)
          return; 
        if (this.switchTimer.hasTimePassed(1L)) {
          if (this.index < this.loaded.size() - 1) {
            this.index++;
          } else {
            this.index = 0;
          } 
          this.switchTimer.reset();
        } 
        this.target = this.loaded.get(this.index);
        if (this.target != null) {
          if (!this.KeepSprint.getValBoolean()) {
            this.mc.thePlayer.setSprinting(false);
            this.mc.gameSettings.keyBindSprint.pressed = false;
          } 
          if (this.mc.thePlayer.getDistanceToEntity((Entity)this.target) < this.Range2.getValDouble()) {
            boolean block = this.AutoBlock.getValBoolean();
            if(!this.onhit.getValBoolean()) {
                if(this.Rotations.getValBoolean() ) {
                    if(!this.RotationsMode.getValString().equalsIgnoreCase("ClientSide")) {
                   float[] rots = getRotations(this.target, event);
                   event.setYaw(rots[0]);
                   event.setPitch(rots[1]);
                   mc.thePlayer.rotationPitchHead = rots[1];
                   mc.thePlayer.rotationYawHead = rots[0];
                   mc.thePlayer.renderYawOffset = rots[0];
                   }else {
                 	float[] rots = RotationUtils.getRotations(target);
                     mc.thePlayer.rotationYaw = rots[0];
                     mc.thePlayer.rotationPitch = rots[1];
                     mc.thePlayer.rotationPitchHead = rots[1];
                     mc.thePlayer.rotationYawHead = rots[0];
                     mc.thePlayer.renderYawOffset = rots[0];  
                   }
                   }
           }
           // if (this.AACCheck.getValBoolean() && (Math.round(event.getYaw()) - Math.round(RotationUtils.getRotations(this.target)[0]) > 3 || Math.round(event.getPitch()) - Math.round(RotationUtils.getRotations(this.target)[1]) > 3))
             // return; 
            if (this.timer.hasTimePassed(randomClickDelay(this.minAps.getValDouble(), this.maxAps.getValDouble()))) {
            	  if (this.mc.thePlayer.isBlocking() && block && this.AutoBlockMode.getValString().equalsIgnoreCase("NCP")) {
                      //   this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN)); 
                    	mc.getNetHandler().getNetworkManager().sendPacket((new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1),EnumFacing.DOWN)));   
                  //      Hime.addClientChatMessage("unblocked");
                      }
                      this.blocking = false;
            
              if(this.onhit.getValBoolean()) {
                  if(this.Rotations.getValBoolean() ) {
                      if(!this.RotationsMode.getValString().equalsIgnoreCase("ClientSide")) {
                     float[] rots = getRotations(this.target, event);
                     event.setYaw(rots[0]);
                     event.setPitch(rots[1]);
                     mc.thePlayer.rotationPitchHead = rots[1];
                     mc.thePlayer.rotationYawHead = rots[0];
                     mc.thePlayer.renderYawOffset = rots[0];
                     }else {
                   	float[] rots = RotationUtils.getRotations(target);
                       mc.thePlayer.rotationYaw = rots[0];
                       mc.thePlayer.rotationPitch = rots[1];
                       mc.thePlayer.rotationPitchHead = rots[1];
                       mc.thePlayer.rotationYawHead = rots[0];
                       mc.thePlayer.renderYawOffset = rots[0];  
                     }
                     }
                  }
              
              Entity rayCastEntity = this.raycast.getValBoolean() ? this.raycast(this.Range2.getValDouble() + 1.0f, this.target) : null;
              if(this.swing.getValBoolean()) {
                  this.mc.thePlayer.swingItem();
                 }else {
                	 mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                 }
              this.attack(target);
              this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity(rayCastEntity == null ? target : rayCastEntity, C02PacketUseEntity.Action.ATTACK));
              this.timer.reset();
            } 
          } 
        } 
      }
    }
      if(event.isPost()) {
      if (this.target != null) {
      boolean block = this.AutoBlock.getValBoolean();
     
      if (block && this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof net.minecraft.item.ItemSword) {
    	  if(this.AutoBlockMode.getValString().equalsIgnoreCase("NCP")) {
    	  mc.thePlayer.setItemInUse(mc.thePlayer.getCurrentEquippedItem(), mc.thePlayer.getCurrentEquippedItem().getMaxItemUseDuration());
        if (!this.blocking) {
          this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getCurrentEquippedItem(), 0.0F, 0.0F, 0.0F));
        //  Hime.addClientChatMessage("blocked");
          this.blocking = true;
        }
      }else if(this.AutoBlockMode.getValString().equalsIgnoreCase("Hypixel")) {
    	  mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem()); 
    	//  mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0,0), EnumFacing.DOWN));  
      }else if(this.AutoBlockMode.getValString().equalsIgnoreCase("Ghostly")) {
    	  mc.gameSettings.keyBindUseItem.pressed = true;
      }else if(this.AutoBlockMode.getValString().equalsIgnoreCase("Interact")) {
    	  this.mc.getNetHandler().addToSendQueue((Packet)new C02PacketUseEntity((Entity)target, target.getPositionVector()));
          this.mc.getNetHandler().addToSendQueue((Packet)new C02PacketUseEntity((Entity)target, C02PacketUseEntity.Action.INTERACT));
          mc.thePlayer.setItemInUse(mc.thePlayer.getCurrentEquippedItem(), 71626);   
      }else if(this.AutoBlockMode.getValString().equalsIgnoreCase("Vanilla")) {
    	  mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
      }else if(this.AutoBlockMode.getValString().equalsIgnoreCase("Legit")) {
    	  if(mc.thePlayer.ticksExisted % 5 == 0) {
              mc.thePlayer.setItemInUse(mc.thePlayer.getCurrentEquippedItem(), 71626);
          }
      }else if(this.AutoBlockMode.getValString().equalsIgnoreCase("Redesky")) {
    	  this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getHeldItem(), this.mc.thePlayer.getHeldItem().getMaxItemUseDuration()); 
      }
    	 
      } 
    } 
      }
    if (this.loaded.isEmpty() && !this.targetMode.getValString().equalsIgnoreCase("Single"))
      this.target = null; 
  }
  
  public static long randomClickDelay(double minCPS, double maxCPS) {
    return (long)(Math.random() * (1000.0D / minCPS - 1000.0D / maxCPS + 1.0D) + 1000.0D / maxCPS);
  }
  
  public boolean canBlock() {
  	if(target != null && this.AutoBlock.getValBoolean() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
  		return true;
  	}
  	return false;
  }
  public float[] getRotations(EntityLivingBase e, EventUpdate event) {
    if (this.RotationsMode.getValString().equalsIgnoreCase("Normal"))
      return RotationUtils.getRotations(e); 
    if (this.RotationsMode.getValString().equalsIgnoreCase("Smooth")) {
      float[] targetYaw = RotationUtils.getRotations(e);
      float yaw = 180.0F;
      float speed = (float)ThreadLocalRandom.current().nextDouble(0D, 5D);
      float yawDifference = event.getLastYaw() - targetYaw[0];
      yaw = event.getLastYaw() - yawDifference / speed;
      float pitch = 0.0F;
      float pitchDifference = event.getLastpitch() - targetYaw[1];
      pitch = event.getLastpitch() - pitchDifference / speed;
      return new float[] { yaw, pitch };
    } 
    if (this.RotationsMode.getValString().equalsIgnoreCase("AAC")) {
      float[] rots = RotationUtils.getRotations(e);
      return new float[] { rots[0] + randomNumber(3, -3), rots[1] + randomNumber(3, -3) };
    } 
    return null;
  }
  
 /* public EntityLivingBase getGroundPriority() {
	  List<EntityLivingBase> entities = new ArrayList<>();
	    for (Entity e : this.mc.theWorld.loadedEntityList) {
	      if (e instanceof EntityLivingBase) {
	        EntityLivingBase player = (EntityLivingBase)e;
	        if (this.mc.thePlayer.getDistanceToEntity((Entity)player) < this.Range.getValDouble() && canAttack(player))
	          entities.add(player); 
	      } 
	    }
	    entities.sort((o1, o2) -> (boolean)((o1.onGround ? o2.onGround() : )));
	    if (entities.isEmpty())
	      return null; 
	    return entities.get(0);
	  
  }
  */
  public EntityLivingBase getHealthPriority() {
    List<EntityLivingBase> entities = new ArrayList<>();
    for (Entity e : this.mc.theWorld.loadedEntityList) {
      if (e instanceof EntityLivingBase) {
        EntityLivingBase player = (EntityLivingBase)e;
        if (this.mc.thePlayer.getDistanceToEntity((Entity)player) < (this.AutoBlock.getValBoolean() ? this.Range.getValDouble() : this.Range2.getValDouble()) && canAttack(player))
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
        if (this.mc.thePlayer.getDistanceToEntity((Entity)player) < (this.AutoBlock.getValBoolean() ? this.Range.getValDouble() : this.Range2.getValDouble()) && canAttack(player))
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
  public void attack(Entity entity)
  {
	  for(int i = 0; i < this.cracksize.getValDouble(); i++) {
          mc.thePlayer.onCriticalHit(entity);
          mc.thePlayer.onEnchantmentCritical(entity);
      }
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
  
  public List<EntityLivingBase> getTargets() {
    List<EntityLivingBase> load = new ArrayList<>();
    for (Entity e : this.mc.theWorld.loadedEntityList) {
      if (e instanceof EntityLivingBase) {
        EntityLivingBase entity = (EntityLivingBase)e;
        if (canAttack(entity) && this.mc.thePlayer.getDistanceToEntity((Entity)entity) < (this.AutoBlock.getValBoolean() ? this.Range.getValDouble() : this.Range2.getValDouble()))
          load.add(entity); 
      } 
    } 
    return load;
  }
  
  public  Entity raycast(double range, Entity entity) {
      Entity var2 = mc.thePlayer;
      Vec3 var9 = entity.getPositionVector().add(new Vec3(0, entity.getEyeHeight(), 0));
      Vec3 var7 = mc.thePlayer.getPositionVector().add(new Vec3(0, mc.thePlayer.getEyeHeight(), 0));
      Vec3 var10 = null;
      float var11 = 1.0F;
      AxisAlignedBB a = mc.thePlayer.getEntityBoundingBox()
              .addCoord(var9.xCoord - var7.xCoord, var9.yCoord - var7.yCoord, var9.zCoord - var7.zCoord)
              .expand(var11, var11, var11);
      List var12 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(var2, a);
      double var13 = range + 0.5;
      Entity b = null;
      for (int var15 = 0; var15 < var12.size(); ++var15) {
          Entity var16 = (Entity) var12.get(var15);

          if (var16.canBeCollidedWith()) {
              float var17 = var16.getCollisionBorderSize();
              AxisAlignedBB var18 = var16.getEntityBoundingBox().expand((double) var17, (double) var17,
                      (double) var17);
              MovingObjectPosition var19 = var18.calculateIntercept(var7, var9);

              if (var18.isVecInside(var7)) {
                  if (0.0D < var13 || var13 == 0.0D) {
                      b = var16;
                      var10 = var19 == null ? var7 : var19.hitVec;
                      var13 = 0.0D;
                  }
              } else if (var19 != null) {
                  double var20 = var7.distanceTo(var19.hitVec);

                  if (var20 < var13 || var13 == 0.0D) {
                      b = var16;
                      var10 = var19.hitVec;
                      var13 = var20;
                  }
              }
          }
      }
      return b;
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
    if (player instanceof EntityPlayer && FriendManager.instance.isFriend(((EntityPlayer) player).getName()))
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
  
  public void onEnable() {
    super.onEnable();
    this.timer.reset();
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
  
  TimeUtil renderTimer = new TimeUtil();
  boolean up = true;
  float height = 0;
  @Handler
  public void on3d(Event3D event) {
	  if(esp.getValString().equalsIgnoreCase("Normal")) {
    if (this.targetMode.getValString().equalsIgnoreCase("Switch")) { 
    if (this.loaded.size() > 0) {
      if (this.loaded.get(this.index) == null)
        return; 
    //  RenderUtil.instance.drawPlatform((Entity)this.loaded.get(this.index), (((EntityLivingBase)this.loaded.get(this.index)).hurtTime == 0) ? new Color(0, 255, 0, 70) : new Color(255, 0, 0, 70));
    }
    } 
    if (this.targetMode.getValString().equalsIgnoreCase("Single")) {    
          if (target == null)
            return; 
          //RenderUtil.instance.drawPlatform(target, ((EntityLivingBase)target).hurtTime == 0 ? new Color(0, 255, 0, 70) : new Color(255, 0, 0, 70));
        
        }
	  } if(esp.getValString().equalsIgnoreCase("Sigma")) {
		    if (this.targetMode.getValString().equalsIgnoreCase("Single")) {    
		        if(this.target != null){
		            if(up){
		                if(renderTimer.hasTimePassed(50)){
		                    height+=0.1f;
		                    if(height > 2){
		                        up = false;
		                    }
		                    renderTimer.reset();
		                }
		            }else{
		                if(renderTimer.hasTimePassed(50)){
		                    height-=0.1f;
		                    if(height <= 0){
		                        up = true;
		                    }
		                    renderTimer.reset();
		                }
		            }

		            if(!up){
		                renderCircle(event, height, 1);
		                renderCircle(event, height + 0.01f, 0.95f);
		                renderCircle(event, height + 0.02f, 0.9f);
		                renderCircle(event, height + 0.03f, 0.85f);
		                renderCircle(event, height + 0.04f, 0.8f);
		                renderCircle(event, height + 0.05f, 0.75f);
		                renderCircle(event, height + 0.06f, 0.7f);
		                renderCircle(event, height + 0.07f, 0.65f);
		                renderCircle(event, height + 0.08f, 0.6f);
		                renderCircle(event, height + 0.09f, 0.55f);
		                renderCircle(event, height + 0.10f, 0.5f);
		                renderCircle(event, height + 0.11f, 0.45f);
		                renderCircle(event, height + 0.12f, 0.4f);
		                renderCircle(event, height + 0.13f, 0.35f);
		                renderCircle(event, height + 0.14f, 0.3f);
		                renderCircle(event, height + 0.15f, 0.25f);
		                renderCircle(event, height + 0.16f, 0.2f);
		                renderCircle(event, height + 0.17f, 0.15f);
		                renderCircle(event, height + 0.18f, 0.1f);
		                renderCircle(event, height + 0.19f, 0.05f);
		                renderCircle(event, height + 0.20f, 0.03f);
		                renderCircle(event, height + 0.21f, 0.02f);
		                renderCircle(event, height + 0.22f, 0.01f);
		            }else{
		                renderCircle(event, height, 0.1f);
		                renderCircle(event, height + 0.01f, 0.15f);
		                renderCircle(event, height + 0.02f, 0.2f);
		                renderCircle(event, height + 0.03f, 0.25f);
		                renderCircle(event, height + 0.04f, 0.3f);
		                renderCircle(event, height + 0.05f, 0.35f);
		                renderCircle(event, height + 0.06f, 0.4f);
		                renderCircle(event, height + 0.07f, 0.45f);
		                renderCircle(event, height + 0.08f, 0.5f);
		                renderCircle(event, height + 0.09f, 0.55f);
		                renderCircle(event, height + 0.10f, 0.6f);
		                renderCircle(event, height + 0.11f, 0.65f);
		                renderCircle(event, height + 0.12f, 0.7f);
		                renderCircle(event, height + 0.13f, 0.75f);
		                renderCircle(event, height + 0.14f, 0.8f);
		                renderCircle(event, height + 0.15f, 0.85f);
		                renderCircle(event, height + 0.16f, 0.9f);
		                renderCircle(event, height + 0.17f, 0.95f);
		                renderCircle(event, height + 0.18f, 1f);
		            }
		        }
		}   
		if (this.targetMode.getValString().equalsIgnoreCase("Switch")) { 
			  if (this.loaded.size() > 0) {
			      if (this.loaded.get(this.index) == null)
			        return; 
			     // drawCircle((Entity)this.target, event.getPartialTicks(), 0.8D, this.delay / 100.0D, 0.2); 
			      if(up){
		                if(renderTimer.hasTimePassed(50)){
		                    height+=0.1f;
		                    if(height > 2){
		                        up = false;
		                    }
		                    renderTimer.reset();
		                }
		            }else{
		                if(renderTimer.hasTimePassed(50)){
		                    height-=0.1f;
		                    if(height <= 0){
		                        up = true;
		                    }
		                    renderTimer.reset();
		                }
		            }

		            if(!up){
		                renderCircle(event, height, 1);
		                renderCircle(event, height + 0.01f, 0.95f);
		                renderCircle(event, height + 0.02f, 0.9f);
		                renderCircle(event, height + 0.03f, 0.85f);
		                renderCircle(event, height + 0.04f, 0.8f);
		                renderCircle(event, height + 0.05f, 0.75f);
		                renderCircle(event, height + 0.06f, 0.7f);
		                renderCircle(event, height + 0.07f, 0.65f);
		                renderCircle(event, height + 0.08f, 0.6f);
		                renderCircle(event, height + 0.09f, 0.55f);
		                renderCircle(event, height + 0.10f, 0.5f);
		                renderCircle(event, height + 0.11f, 0.45f);
		                renderCircle(event, height + 0.12f, 0.4f);
		                renderCircle(event, height + 0.13f, 0.35f);
		                renderCircle(event, height + 0.14f, 0.3f);
		                renderCircle(event, height + 0.15f, 0.25f);
		                renderCircle(event, height + 0.16f, 0.2f);
		                renderCircle(event, height + 0.17f, 0.15f);
		                renderCircle(event, height + 0.18f, 0.1f);
		                renderCircle(event, height + 0.19f, 0.05f);
		                renderCircle(event, height + 0.20f, 0.03f);
		                renderCircle(event, height + 0.21f, 0.02f);
		                renderCircle(event, height + 0.22f, 0.01f);
		            }else{
		                renderCircle(event, height, 0.1f);
		                renderCircle(event, height + 0.01f, 0.15f);
		                renderCircle(event, height + 0.02f, 0.2f);
		                renderCircle(event, height + 0.03f, 0.25f);
		                renderCircle(event, height + 0.04f, 0.3f);
		                renderCircle(event, height + 0.05f, 0.35f);
		                renderCircle(event, height + 0.06f, 0.4f);
		                renderCircle(event, height + 0.07f, 0.45f);
		                renderCircle(event, height + 0.08f, 0.5f);
		                renderCircle(event, height + 0.09f, 0.55f);
		                renderCircle(event, height + 0.10f, 0.6f);
		                renderCircle(event, height + 0.11f, 0.65f);
		                renderCircle(event, height + 0.12f, 0.7f);
		                renderCircle(event, height + 0.13f, 0.75f);
		                renderCircle(event, height + 0.14f, 0.8f);
		                renderCircle(event, height + 0.15f, 0.85f);
		                renderCircle(event, height + 0.16f, 0.9f);
		                renderCircle(event, height + 0.17f, 0.95f);
		                renderCircle(event, height + 0.18f, 1f);
		            }
			    }
		}
	  
	}
  }
  public void drawCircle(Entity entity, float partialTicks, double rad) {
	    GL11.glPushMatrix();
	    GL11.glDisable(3553);
	    GL11.glDisable(2929);
	    GL11.glDepthMask(false);
	    GL11.glLineWidth(1.0F);
	    GL11.glBegin(3);
	    //rgb
	    double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - (this.mc.getRenderManager()).viewerPosX;
	    double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - (this.mc.getRenderManager()).viewerPosY;
	    double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - (this.mc.getRenderManager()).viewerPosZ;
	    float r = 0.003921569F * Color.WHITE.getRed();
	    float g = 0.003921569F * Color.WHITE.getGreen();
	    float b = 0.003921569F * Color.WHITE.getBlue();
	    double pix2 = 6.283185307179586D;
	    for (int i = 0; i <= 90; i++) {
	      GL11.glColor4d(r, g, b, 100);
	      GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / 45.0D), y, z + rad * Math.sin(i * 6.283185307179586D / 45.0D));
	    } 
	    GL11.glEnd();
	    GL11.glDepthMask(true);
	    GL11.glEnable(2929);
	    GL11.glEnable(3553);
	    GL11.glPopMatrix();
	  }
  
  private void drawCircle(Entity entity, float partialTicks, double rad, double height, double alpha) {
    GL11.glPushMatrix();
    GL11.glDisable(3553);
    GL11.glDisable(2929);
    GL11.glDepthMask(false);
    GL11.glLineWidth(2.0F);
    GL11.glBegin(3);
    double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - (this.mc.getRenderManager()).viewerPosX;
    double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - (this.mc.getRenderManager()).viewerPosY;
    double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - (this.mc.getRenderManager()).viewerPosZ;
    final float hue = (float) (ColorUtil.getClickGUIColor());
	  //                           colorSaturation  colorBrightness 
    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
    
    float r = 0.003921569F * color.getRed();
    float g = 0.003921569F * color.getGreen();
    float b = 0.003921569F * color.getBlue();
    double pix2 = 6.283185307179586D;
    for (int i = 0; i <= 90; i++) {
    	//GL11.glColor4d(r, g, b, 0.3);
    	GL11.glColor4d(r, g, b, 0.3);
      GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / 45.0D), y + height, z + rad * Math.sin(i * 6.283185307179586D / 45.0D)); 
    }
      GL11.glEnd();
    GL11.glDepthMask(true);
    GL11.glEnable(2929);
    GL11.glEnable(3553);
    GL11.glPopMatrix();
  }
  
  /*Credit to Julian for giving method to me thanks*/
  public void renderCircle(Event3D event, float height, float alpha) {
      Entity entity = target;
      double rad = 0.6;
      float points = 90.0F;
      GlStateManager.enableDepth();
      GL11.glPushMatrix();
      GL11.glDisable(3553);
//      GL11.glEnable(2848);
      GL11.glEnable(2881);
      GL11.glEnable(2832);
      GL11.glEnable(3042);
      GL11.glEnable(GL11.GL_LINE_SMOOTH);
      GL11.glBlendFunc(770, 771);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
      GL11.glHint(3153, 4354);
      GL11.glDisable(2929);
      GL11.glLineWidth(1.3F);
      GL11.glBegin(3);
      GlStateManager.color(84, 95, 255, alpha);

      double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.getPartialTicks()
              - RenderManager.renderPosX;
      double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.getPartialTicks()
              - RenderManager.renderPosY;
      double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.getPartialTicks()
              - RenderManager.renderPosZ;
      for (int i = 0; i <= 90; i++) {
          GL11.glColor4f(84, 95, 255, alpha);
          GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / points), y + height,
                  z + rad * Math.sin(i * 6.283185307179586D / points));
      }

      GL11.glEnd();
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glDisable(2881);
      GL11.glDisable(GL11.GL_LINE_SMOOTH);
      GL11.glEnable(2832);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
  }
  
  @Handler
  public void onRenderHUD(EventRenderHUD event) {
      FontRenderer fr = mc.fontRendererObj;
      if(target instanceof EntityPlayer) {
          GlStateManager.pushMatrix();
          double hpPercentage = target.getHealth() / target.getMaxHealth();
          float width = (float)(event.getWidth() / 2.0D + 100.0D);
          float height = (float)(event.getHeight() / 2.0D);

          Gui.drawRect((width - 70.0F), (height + 69.0F), (width + 80), (height + 106.0F), (new Color(0, 0, 0, 90)).getRGB());


          float health = target.getHealth();
          double healthPercentage = health / target.getMaxHealth();
          float targetHealthPercentage = 0.0F;
          if (hpPercentage != this.lastHealth) {
              float diff = (float) (hpPercentage - this.lastHealth);
              targetHealthPercentage = this.lastHealth;
              this.lastHealth += diff / 8.0F;
          }
          Color healthcolor = Color.WHITE;
          if (hpPercentage * 100.0F > 75.0F) {
              healthcolor = Color.GREEN;
          } else if (hpPercentage * 100.0F > 50.0F && hpPercentage * 100.0F < 75.0F) {
              healthcolor = Color.YELLOW;
          } else if (hpPercentage * 100.0F < 50.0F && hpPercentage * 100.0F > 25.0F) {
              healthcolor = Color.ORANGE;
          } else if (hpPercentage * 100.0F < 25.0F) {
              healthcolor = Color.RED;
          }
          // RenderUti3.draw2DImage(new ResourceLocation("client/heart.png"), ((int) (width - 57)), (int) (height + 63), 120, 40, new Color(153, 153, 153));
          //RenderUti3.draw2DImage(new ResourceLocation("client/heart.png"), (int) (width - 57), (int) (height + 63), 120, 40, Color.GRAY);
          RenderUtil.drawGradientSideways((width - 70.0F), (height + 101.0F), (width + 80 * targetHealthPercentage), (height + 106.0F), (int)Color.PINK.getRGB(), (int)Color.BLUE.getRGB());
          //Gui.drawRect((width - 36.0F), (height + 87.0F), (width - 36.0F + 132.0F * hpPercentage), (height + 96.0F), new Color(19, 82, 143).getRGB());
          //Gui.drawRect((width - 70.0F), (height + 104.0F), (width - 70.0F + 149.0F * hpPercentage), (height + 106.0F), Color.GREEN.getRGB());




          fr.drawString(((EntityPlayer) target).getName(), (int) (width - 37), (int) (height + 70), Color.GRAY.getRGB());
          String xd = ((EntityPlayer) target).getCurrentEquippedItem() == null ? "Nothing" : ((EntityPlayer) target).getCurrentEquippedItem().getDisplayName().toString();
          // fr.drawString(ChatFormatting.GRAY+ xd, (int) (width + 15), (int) (height + 63), Color.GRAY.getRGB());

          int x = (int) (width - 90);
          int y =  (int) (height + 60);

          GL11.glPushMatrix();
          GlStateManager.translate(x,y,1);
          GL11.glScalef(1.8f,1.8f,1.8f);
          GlStateManager.translate(-x,-y,1);
          //Render2DUtil.drawFace((int)width / 2 + 12, (int)height / 2 - 28, 8, 8, 8, 8, 28, 28, 64, 64, (AbstractClientPlayer)this.target);
          mc.fontRendererObj.drawStringWithShadow(Math.round(target.getHealth() / 2.0f) + " HP", x + 29,y + 12, Color.GRAY.getRGB());
          GL11.glPopMatrix();
          this.drawFace((int) (width - 70), (int) (height + 71.8), 8, 8, 8, 8, 30, 30, 64, 64, (AbstractClientPlayer) this.target);


          GlStateManager.popMatrix();
          //	this.drawEntityOnScreen((int) (width - 50), (int) (height + 95), 20, 25, 25, target);
      }
  }
  
	public void drawFace(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer target) {
		try {
			ResourceLocation skin = target.getLocationSkin();
			Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1, 1, 1, 1);
			Gui.drawScaledCustomSizeModalRect((int)x, (int)y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
			GL11.glDisable(GL11.GL_BLEND);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
  
  public void onDisable() {
	  this.renderTimer.reset();
	  super.onDisable();
  }
}
