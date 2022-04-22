package me.module.impl.movement;
import java.awt.Color;
import java.util.ArrayList;

import me.Hime;
import me.notification.Notification;
import me.notification.NotificationManager;
import me.notification.NotificationType;
import org.lwjgl.input.Keyboard;

import me.event.impl.EventReceivePacket;
import me.event.impl.EventRenderHUD;
import me.event.impl.EventSendPacket;
import me.event.impl.EventUpdate;
import me.event.impl.MoveEvent;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.MovementUtils;
import me.util.PlayerUtils;
import me.util.TimeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Fly extends Module {
	private int trys;;
	TimeUtil time =  new TimeUtil();

//MoveUtil move;
private double mineplexSpeed;

public int dam = 1;
private boolean back, done;
private final TimeUtil time2 = new TimeUtil();
public static Fly instance = new Fly();
private final TimeUtil time3 = new TimeUtil();
private final TimeUtil time4 = new TimeUtil();
private final TimeUtil time5 = new TimeUtil();
private final TimeUtil time10 = new TimeUtil();
private int delay = 0;
public ArrayList<Packet> packetList = new ArrayList<Packet>();
private boolean damaged;
double lastDist = 0, moveSpeed = 0, lastDist2 = 0;
private double tvalue;
private double movespeed;
//public Setting disabler;
public Setting mode;
public Setting hud;
int prevSlot2;
public Setting duration;
public Setting duration2;
public Setting hypixelMode;
public Setting speed;
public Setting timer;
public Setting damage;
public Setting bob;
public Setting speedvanilla;
public Setting speedmotion;
public Setting bar;
public Setting disablerMode2;
//public String disablerMode; 
public boolean hypixelDamaged = false;

int y;

private int state;
public  int timerDelay;

protected FontRenderer fontRendererObj;
static int toggle = 0;
    public Fly() {
        super("Flight", Keyboard.KEY_F, Category.MOVEMENT);
        
    }

    private long start;
    

    ArrayList<Packet> packets = new ArrayList<>();
    int stage;
    int ticks;
    int height = 30;
    boolean collided;
    int stage2;
    public double motionXZ;
   
	public Setting lagback;
	public Setting blink;
	public Setting milli;
	public Setting ground;
	public boolean verusDamaged = false;
	
	/*public Setting bowjump;
	public Setting samey;
	public Setting bowspeed;
	public Setting bowyvalue;*/
	private int stage3;
	private int stage4;
    @Override
    public void setup() {
    	Hime.instance.settingsManager.rSetting(timer = new Setting("Hypixel Timer", this, 1.0, 0.0, 5.0, false));
    	Hime.instance.settingsManager.rSetting(speed = new Setting("Hypixel Speed", this, 1.0, 0.0, 4.0, false));
    	Hime.instance.settingsManager.rSetting(duration = new Setting("Timer Duration", this, 25, 0, 200, true));
    	Hime.instance.settingsManager.rSetting(milli = new Setting("Timer Milliseconds", this, 700, 0, 2000, true));
       	Hime.instance.settingsManager.rSetting(duration2 = new Setting("Blink Pulse Duration", this, 250, 1, 1500, true));
        Hime.instance.settingsManager.rSetting(speedvanilla = new Setting("Vanilla speed", this, 2, 1, 20, true));
     	Hime.instance.settingsManager.rSetting(speedmotion = new Setting("Motion speed", this, 0.7D, 0.0D, 5.0D, false));
        Hime.instance.settingsManager.rSetting(damage = new Setting("Hypixel Damage", this, true));
        Hime.instance.settingsManager.rSetting(bob = new Setting("View Bobbing", this, true));
        Hime.instance.settingsManager.rSetting(hud = new Setting("HUD", this, false));
        Hime.instance.settingsManager.rSetting(bar = new Setting("Progress Bar", this, false));
        //TODO: bow
     /*   Hime.instance.settingsManager.rSetting(bowjump = new Setting("Bow Fly Jump", this, true));
        Hime.instance.settingsManager.rSetting(samey = new Setting("Bow Fly Same Y", this, true));
        //Hime.instance.settingsManager.rSetting(bowspeed = new Setting("Bow Speed", this, 0.099, 0.0, 0.6, false));
        Hime.instance.settingsManager.rSetting(bowyvalue = new Setting("Bow Y Increase", this, 0.02D, 0.0D, 0.6D, false));*/
        ArrayList<String> options = new ArrayList<String>();
        ArrayList<String> options2 = new ArrayList<String>();
        ArrayList<String> options3 = new ArrayList<String>();
        //fly modes
        options.add("Vanilla");
        options.add("Motion");   
        options.add("Hypixel");
        options.add("Funcraft");
        options.add("Viper");
        options.add("Viper2");
        options.add("MCCenteral");
        options.add("Rewinside");
        options.add("OldAAC");
        options.add("Verus1");
        options.add("Verus");
        options.add("VerusFast");
        options.add("Ghostly");
        options.add("On Damage");
        options.add("Minemen");
        options.add("Redesky");
        options.add("RedeskySlow");
        options.add("Collide");
        options.add("Hive");
        //hypixel modes
        options2.add("BowNew");
        
        options2.add("Blink");
        options2.add("Old");
        options2.add("Blinkless");
        options2.add("BlinkPulse");
        options2.add("BlinklessSlow");
        options2.add("BlinkSlow");
        options2.add("Drop");
        options2.add("Full Disabler");
        //disabler modes
        options3.add("Hover");
        options3.add("Ender Pearl");
        Hime.instance.settingsManager.rSetting(mode = new Setting("Fly Mode", this, "Motion", options));
        Hime.instance.settingsManager.rSetting(hypixelMode = new Setting("Hypixel Mode", this, "Blinkless", options2));
        Hime.instance.settingsManager.rSetting(disablerMode2 = new Setting("Full Disabler Mode", this, "Hover", options3));
        Hime.instance.settingsManager.rSetting(ground = new Setting("On Ground equals true", this, false));
        Hime.instance.settingsManager.rSetting(lagback = new Setting("Lagback Fly Check", this, false));
        Hime.instance.settingsManager.rSetting(blink = new Setting("Blink", this, true));
       
    //    this.addModes("Full Disabler Mode", "Hover", "Ender Pearl");
    //    this.disablerMode = this.getModes("Full Disabler Mode");
        
    }
    
    public void show() {
        start = System.currentTimeMillis();
    }

  

    private long getTime() {
        return System.currentTimeMillis() - start;
    }
    @Handler
    public void onRender(EventRenderHUD event) {
    	ScaledResolution sr = new ScaledResolution(mc);
    	 long time7 = getTime();
    	 
    	 if(bar.getValBoolean()) {
    			if(time10.hasTimePassed(2000)) {
    				this.show();
    				 time10.reset();
    			}
    			
    			   Gui.drawRect(GuiScreen.width - 460 + (time7 / 15), GuiScreen.height - 5 - height - height * 5.7, GuiScreen.width /2.5 , GuiScreen.height / 2,  new Color(128,0,128).getRGB()); 
    	 }
    	 
    		 if(hud.getValBoolean()) {
    			  double currSpeed = lastDist * 15.3571428571;
    	           String bps = String.format("%.2f", currSpeed);
    	           Hime.instance.cfrs.drawString("BPS: " + bps, sr.getScaledWidth() /2 - 15, sr.getScaledHeight() /2 + 8, -1);
    		 }
    		}
    	
    public void onEnable() {
    	 this.show();
    	 time10.reset();
    	 time2.reset();
    	 this.collided = false;
    	 prevSlot2 = mc.thePlayer.inventory.currentItem;
    
     if(this.mode.getValString().equalsIgnoreCase("VerusFast")) {
    	 if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0, 3.45, 0).expand(0, 0, 0)).isEmpty()) {
    		 this.verusDamaged = true;
             mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.45, mc.thePlayer.posZ, false));
             mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
             mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
             this.verusDamaged = false;
         }
    	 
    	 
    	 //mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.42, mc.thePlayer.posZ);
     }
     
     if(this.mode.getValString().equalsIgnoreCase("Verus")) {
    	 if(mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0, 3.0001, 0).expand(0, 0, 0)).isEmpty()) {
             mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.0001, mc.thePlayer.posZ, false));
             mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
             mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
         }
    	 
    	 
    	 mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.42, mc.thePlayer.posZ);
     }
    	//movespeed = Hime.settingsManager.getSettingByName("Hypixel Speed").getValDouble() + Hime.settingsManager.getSettingByName("Hypixel Timer").getValDouble();
    super.onEnable();
    if(mode.getValString().equalsIgnoreCase("test")) {
    	this.damagePlayerCubecraft();
    }
    if(mode.getValString().equalsIgnoreCase("Redesky")) {
    	//this.damagePlayerCubecraft();
    }
    if (mode.getValString().equalsIgnoreCase("Hypixel")) {
        if(hypixelMode.getValString().equalsIgnoreCase("Full Disabler") && this.disablerMode2.getValString().equalsIgnoreCase("Hover")){
            NotificationManager.show(new Notification(NotificationType.WARNING, "Alt Alert", "Why Are U Using A Old Thing?", 2));
    	//mc.thePlayer.sendQueue.getNetworkManager().sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.280, mc.thePlayer.posZ, true));
        mc.thePlayer.jump();
        //MovementUtils.actualSetSpeed(0);
        }
    }
    	  mineplexSpeed = 0;
    	  stage2 = 0;
    	  stage3 = 0;
    	  stage4 = 0;
    	  ticks = 0;
    	  this.movespeed = 0;
    	  moveSpeed = 0;
    	  y = 0;


        stage = 0;
        done = false;
        back = false;
    	 lastDist = 0;
    	 lastDist2 = 0;
    	//String FlyMode = Hime.instance.settingsManager.getSettingByName("Fly Mode").getValString();;
      time5.reset();
    	  //if(FlyMode.equalsIgnoreCase("Hypixel")) {
    		//mc.thePlayer.jump();
      		//}
      	
    
	 }
    public static int airSlot() {
        for (int j = 0; j < 8; ++j) {
            if (Minecraft.getMinecraft().thePlayer.inventory.mainInventory[j] == null) {
                return j;
            }
        }
        Hime.instance.addClientChatMessage("Clear a hotbar slot.");
        return -10;
    }
    
    @Handler
    public void onMotion(MoveEvent event) {
  	if(mode.getValString().equalsIgnoreCase("Hypixel")) {
      if(this.hypixelMode.getValString().equalsIgnoreCase("Blinkless") || hypixelMode.getValString().equalsIgnoreCase("Blink") || hypixelMode.getValString().equalsIgnoreCase("BlinkPulse")) {
        if (mc.thePlayer.isMoving()) {
            switch (stage) {
                case 0:
                    if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
                    	if(this.damage.getValBoolean()) {
                    	 PlayerUtils.damage();
                    	}
                       // moveSpeed = 0.426 * speed.getValDouble();
                        moveSpeed = 0.4 * speed.getValDouble();
                       // System.out.println(0.426 * speed.getValDouble());
                        //moveSpeed = speed.getValDouble();
                    }
                    break;
                case 1:
                    if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
                        event.actualSetSpeedY(mc.thePlayer.motionY = 0.39999994D);
                    }
                   // moveSpeed *= 2.14946758;
                	this.moveSpeed *= 2.149D;
                    break;
                case 2:
                	moveSpeed = 1.3 * speed.getValDouble();
                  //  moveSpeed = 1.296775 * speed.getValDouble();
                    break;
                default:
                  //  moveSpeed = lastDist - lastDist / 159.9999D;
                	 moveSpeed = lastDist - lastDist / 159.0D;
                    break;
            }

            this.motionXZ = moveSpeed;
            MovementUtils.setSpeed(event, Math.max(moveSpeed, MovementUtils.getBaseMoveSpeed()));
            stage++;
        }
      }
      if(this.hypixelMode.getValString().equalsIgnoreCase("Old")){
    	  if (mc.thePlayer.isMoving()) {
  			switch (this.stage) {
  			case 0:
  				if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
  					if(this.damage.getValBoolean()) {
  						PlayerUtils.damage3();
  					 }
  					}
  					moveSpeed = 0.5 * speed.getValDouble();
  					break;
  					
  				
  			case 1:
  				if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
  					final double jumpBoostModifier = getJumpBoostModifier(0.399999994);
  					mc.thePlayer.motionY = jumpBoostModifier;
  					event.actualSetSpeedY(getJumpBoostModifier(0.399999994));
  				}
  				this.moveSpeed = 0.635;
  				 mc.timer.timerSpeed = (float) this.timer.getValDouble();
  				 new Thread(() -> {
                       try {
                           //Wait X milliseconds
                           Thread.sleep((long)milli.getValDouble());
                       }
                       catch (InterruptedException localInterruptedException) {}
                       //Set timer to normal
                       mc.timer.timerSpeed = 1.0F;
                   }).start();
                   
  				break;
  			case 2:
  				moveSpeed = 1.8 * speed.getValDouble();
  				break;
  			default:
  				moveSpeed = this.lastDist - this.lastDist / (MovementUtils.getSpeedAmplifier() > 0 ? Double.parseDouble("131") : Double.parseDouble("131"));
  				break;
  			}
  			MovementUtils.setSpeed(event, Math.max(moveSpeed, MovementUtils.getBaseMoveSpeed()));
  			//System.out.println(mc.thePlayer.posX - mc.thePlayer.lastTickPosX);
  			//mc.timer.timerSpeed = 1.0F;	
  			++stage;
  		}
      }
  	}
	if(mode.getValString().equalsIgnoreCase("Hive")) {
		  if (mc.thePlayer.posY < -70) {
              mc.thePlayer.motionY = speed.getValDouble();
          }
          if (mc.gameSettings.keyBindSprint.isKeyDown()) {
              mc.timer.timerSpeed = 0.3f;
          } else {
              mc.timer.timerSpeed = 1f;
          }
	 }
    }
    
    @Handler
    public void onPre(EventUpdate event) {
      	if(mode.getValString().equalsIgnoreCase("Hypixel")) {
        	  if(this.hypixelMode.getValString().equalsIgnoreCase("Blinkless") || hypixelMode.getValString().equalsIgnoreCase("Blink")|| hypixelMode.getValString().equalsIgnoreCase("BlinkPulse")) {
    	   if (stage < this.duration.getValDouble()) {
               mc.timer.timerSpeed = (float) timer.getValDouble();
    	   }else {
               mc.timer.timerSpeed = 1;
    	   }
        	  }
        	  if(this.hypixelMode.getValString().equalsIgnoreCase("Drop")) {  
        		  switch (stage) {
        		  case 0:
        			  if(mc.thePlayer.fallDistance > 5.3) {
        				  PlayerUtils.damage();
        				  this.movespeed = this.speed.getValDouble();
        				  stage++;
        			  }
        			  break;
        		  case 1:
        			  mc.thePlayer.motionY = 0;
        			  
        			  MovementUtils.actualSetSpeed(this.movespeed);
        	                mc.thePlayer.motionY = 0;
        	                if (mc.thePlayer.ticksExisted % 2 == 0) {
        	                    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0006185623653, mc.thePlayer.posZ);
        	                } else {
        	                    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.0006185623653, mc.thePlayer.posZ);
        	                }
        	      	    
        			  break;
        		  }
        		  
        	  }
      	}
    	   
    if(event.isPre()) {
    	if(mode.getValString().equalsIgnoreCase("Hypixel")) {
      	  if(this.hypixelMode.getValString().equalsIgnoreCase("Blinkless") || hypixelMode.getValString().equalsIgnoreCase("Blink")|| hypixelMode.getValString().equalsIgnoreCase("BlinkPulse")) {
      		if (stage > 1) {
                //mc.thePlayer.motionY = 0;
      		}
      		
      	    if (stage > 1) {
                mc.thePlayer.motionY = 0;
                if (mc.thePlayer.ticksExisted % 2 == 0) {
                    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0006185623653, mc.thePlayer.posZ);
                } else {
                    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.0006185623653, mc.thePlayer.posZ);
                }
            }
      	  }
      	  if(hypixelMode.getValString().equalsIgnoreCase("BlinkSlow")|| hypixelMode.getValString().equalsIgnoreCase("BlinklessSlow")) {
      		  if (mc.thePlayer.isMoving()) {
                  mc.thePlayer.motionY = 0;
                  if (mc.thePlayer.ticksExisted % 2 == 0) {
                      mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0006185623653, mc.thePlayer.posZ);
                  } else {
                      mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.0006185623653, mc.thePlayer.posZ);
                  }
              }  
      	  }
      	  if(hypixelMode.getValString().equalsIgnoreCase("Old")) {
      		if (stage > 2) {
                mc.thePlayer.motionY = 0;
                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.prevPosY + (mc.thePlayer.ticksExisted % 2 == 0 ? -0.0009F : 0.0009F), mc.thePlayer.posZ);
      		}
      		}
        }
        }
    }
 
    @Handler
    public void onSendPacket(EventSendPacket event){
        if(mode.getValString().equalsIgnoreCase("Hypixel")){
            if(hypixelMode.getValString().equalsIgnoreCase("Blink") || hypixelMode.getValString().equalsIgnoreCase("BlinkSlow")){
             if(event.getPacket() instanceof C03PacketPlayer) {
                  event.cancel();
                  this.packets.add(event.getPacket());
                }
            }
            if(hypixelMode.getValString().equalsIgnoreCase("Full Disabler") && this.blink.getValBoolean()){
                if(event.getPacket() instanceof C03PacketPlayer) {
                     event.cancel();
                     this.packets.add(event.getPacket());
                   }
               }
            if(hypixelMode.getValString().equalsIgnoreCase("Old")) {
            	if(this.blink.getValBoolean()) {
            		  if(event.getPacket() instanceof C03PacketPlayer) {
                          event.cancel();
                          this.packets.add(event.getPacket());
                      }
            	}
            }if(hypixelMode.getValString().equalsIgnoreCase("BowNew")) {
          		  if(event.getPacket() instanceof C03PacketPlayer && stage > 3) {
                        event.cancel();
                        this.packets.add(event.getPacket());
                    }
            }
            if(hypixelMode.getValString().equalsIgnoreCase("BlinkPulse")) {
               if(event.getPacket() instanceof C03PacketPlayer && this.time2.hasTimePassed((long) this.duration2.getValDouble())) {
                    event.cancel();
                    this.packets.add(event.getPacket());
                    time.reset();
               }
            }
    	}
        if(mode.getValString().equalsIgnoreCase("Verus")){
            if (event.getPacket() instanceof C03PacketPlayer) {
            	C03PacketPlayer c03 = (C03PacketPlayer) event.getPacket();
                c03.onGround = true;
            }
           }if(mode.getValString().equalsIgnoreCase("VerusFast")){
        	   if (event.getPacket() instanceof C03PacketPlayer && !this.verusDamaged) {
               	C03PacketPlayer c03 = (C03PacketPlayer) event.getPacket();
                c03.onGround = true;
               } 
           }
    }
    
    @Handler
    public void onUpdate(EventUpdate event) {
    	  this.setSuffix(mode.getValString());
    	  
    //	  this.disablerMode = this.getModes("Full Disabler Mode");
    	  
    	  double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
          double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
          lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
          
          double xDist2 = mc.thePlayer.posX - mc.thePlayer.lastTickPosX;
          double zDist2 = mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ;
          lastDist2 = Math.sqrt(xDist2 * xDist2 + zDist2 * zDist2);
    	  
          if(bob.getValBoolean()) {
      		mc.thePlayer.cameraYaw = 0.105F;
     	  }
          
          if(this.ground.getValBoolean()) {
        	  mc.thePlayer.onGround = true;
          }
          
          if(mode.getValString().equalsIgnoreCase("Verus1")){
        	//  mc.thePlayer.motionY = 0;
        	  mc.thePlayer.onGround = true;
        	 // mc.thePlayer.setEntityBoundingBox(new AxisAlignedBB(-2, -1, -2, 2, 1, 2).offset(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ));
         // mc.thePlayer.getCollisionBoundingBox().offset(-2, -1, -2);
        	//  mc.thePlayer.getCollisionBoundingBox().offset(0.0D, 0.0D, 0.0D);
        	//  mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getCollisionBoundingBox().offset(0.0D, 0, 0.0D));
          }if(mode.getValString().equalsIgnoreCase("Verus")){
        	//mc.thePlayer.onGround = true;
        	  //mc.thePlayer.motionY = 0;
        	
        	  mc.thePlayer.motionX = 0;
              mc.thePlayer.motionY = 0;
              mc.thePlayer.motionZ = 0;
              mc.thePlayer.speedInAir = 0.25f;
              //MovementUtils.actualSetSpeed(0.2);
          }if(mode.getValString().equalsIgnoreCase("VerusFast")){
        	  mc.thePlayer.onGround = true;
              mc.thePlayer.motionY = 0;
              if (mc.thePlayer.hurtTime > 0) {
            	 // Hime.addClientChatMessage("Did a thing");
                  mc.thePlayer.motionX = 0;
                  mc.thePlayer.motionZ = 0;
                  MovementUtils.actualSetSpeed(mc.thePlayer.hurtTime / 10 * 9);
              }
          }
      

    		  if(mode.getValString().equalsIgnoreCase("Ghostly")){
        		 mc.getNetHandler().addToSendQueueSilent(new C0CPacketInput());
        		 mc.getNetHandler().addToSendQueueSilent(new C0FPacketConfirmTransaction());
        		 mc.thePlayer.motionY = 0;
        		 if(mc.thePlayer.isMoving()) {
        		 mc.thePlayer.setSpeed((float) 1.0000024);
        		 }
        		 if(mc.gameSettings.keyBindSneak.isKeyDown()) {
        			 mc.thePlayer.motionY -= 1;
        		 }
        		 if(mc.gameSettings.keyBindJump.isKeyDown()) {
        			 mc.thePlayer.motionY += 1;
        		 }
        		 
              }
    		  
    		  if(mode.getValString().equalsIgnoreCase("Funcraft")) {
    		 	  if (mc.thePlayer.isMoving()) {
    		  			switch (this.stage) {
    		  			case 0:
    		  					moveSpeed = 0.5 * speed.getValDouble();
    		  					break;
    		  					
    		  				
    		  			case 1:
    		  				this.moveSpeed = 0.635;
    		  				break;
    		  			case 2:
    		  				moveSpeed = 1.8 * speed.getValDouble();
    		  				break;
    		  			default:
    		  				moveSpeed = this.lastDist - this.lastDist / (MovementUtils.getSpeedAmplifier() > 0 ? Double.parseDouble("131") : Double.parseDouble("131"));
    		  				break;
    		  			}
    		  			MovementUtils.actualSetSpeed(Math.max(moveSpeed, MovementUtils.getBaseMoveSpeed()));
    		  			//System.out.println(mc.thePlayer.posX - mc.thePlayer.lastTickPosX);
    		  			//mc.timer.timerSpeed = 1.0F;	
    		  			++stage;
    		  		}
    			    mc.thePlayer.motionY = 0;
                    if (mc.thePlayer.ticksExisted % 2 == 0) {
                        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0006185623653, mc.thePlayer.posZ);
                    } else {
                        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.0006185623653, mc.thePlayer.posZ);
                    }
    		  }
    		  if(mode.getValString().equalsIgnoreCase("Hypixel")) {
    	    	  if(hypixelMode.getValString().equalsIgnoreCase("Full Disabler") && this.disablerMode2.getValString().equalsIgnoreCase("Hover")){
    	    		if(hypixelDamaged == false) {
    	    		//  event.setY(mc.thePlayer.motionY = 1e-6);
    	    			if(time5.hasTimePassed(100)) {
    	    				MovementUtils.actualSetSpeed(0);
    	                mc.thePlayer.motionY = 0;
    	    			}
    	    		}
    	    		  if(this.time3.hasTimePassed(5100)) {
    	    			Hime.addClientChatMessage("Ready");
    	    		    time3.reset();
    	    		  }
    	    		  if(this.time4.hasTimePassed(5000)) {
    	    			   hypixelDamaged = true;
    	    			  mc.thePlayer.motionY = 0;
                          if (mc.gameSettings.keyBindJump.isKeyDown()) {
                              mc.thePlayer.motionY = this.speed.getValDouble();
                          } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                              mc.thePlayer.motionY = -this.speed.getValDouble();
                          }
                          MovementUtils.actualSetSpeed(this.speed.getValDouble());
    	    		 }
    	    	  }
    	    	  if (hypixelMode.getValString().equalsIgnoreCase("Full Disabler") && this.disablerMode2.getValString().equalsIgnoreCase("Ender Pearl")) {
    	    		  final int oldPitch = (int) mc.thePlayer.rotationPitch;
    	    		  if (mc.thePlayer.hurtTime > 0) {
                          hypixelDamaged = true;
                      }
                      if (mc.thePlayer.onGround) {
                          moveSpeed = this.speed.getValDouble();
                          mc.thePlayer.jump();
                          for(int i = 0; i < 9; i++) {
         		             final ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
         		             if(itemStack != null) {
                              if (mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemEnderPearl) {
                            	  mc.getNetHandler().addToSendQueueSilent(new C09PacketHeldItemChange(i));
                                  mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, 90, mc.thePlayer.onGround));
                                  mc.getNetHandler().addToSendQueueSilent(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                                  mc.getNetHandler().addToSendQueueSilent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                                  mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, oldPitch, mc.thePlayer.onGround));
                              }
                          }
                          }
                      }


                      if (hypixelDamaged) {
                          mc.thePlayer.motionY = 0;
                          if (mc.gameSettings.keyBindJump.isKeyDown()) {
                              mc.thePlayer.motionY = this.speed.getValDouble();
                          } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                              mc.thePlayer.motionY = -this.speed.getValDouble();
                          }
                          MovementUtils.actualSetSpeed(this.moveSpeed);
                      } else {
                          mc.thePlayer.motionY = 0;
                      }

                  }
    	    	  //TODO bow fly
    	    	  if (hypixelMode.getValString().equalsIgnoreCase("BowNew")){
    	    			if(!hypixelDamaged) {
    	    		         if(mc.thePlayer.getCurrentEquippedItem() != null) {
    	    		         for (int i = 0; i < 9; i++) {
    	    		             if (mc.thePlayer.inventory.getStackInSlot(i) == null)
    	    		                 continue;
    	    		             if (mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBow) {
    	    		          
    	    		                 mc.thePlayer.sendQueue.addToSendQueueSilent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem = i));
    	    		             }else {
    	    		            	// NotificationManager.addNotification("You must have a bow or a rod in your inventory!", 2000,  me.notification2.Notification.Mode.WARNING);
    	    		             }
    	    		         }
    	    		         }
    	    		    	}
    	    		    	  if(mc.thePlayer.getCurrentEquippedItem() != null) {
    	    		           if(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow && !this.hypixelDamaged) {
    	    		        	   final float prevPitch = mc.thePlayer.rotationPitch;
    	    		             event.setPitch(-90);
    	    		             MovementUtils.doStrafe();
    	    		             mc.thePlayer.motionX = 0;
    	    		             mc.thePlayer.motionY = 0;
    	    		             mc.thePlayer.motionZ = 0;
    	    		             mc.thePlayer.jumpMovementFactor = 0;
    	    		             mc.thePlayer.onGround = false;
    	    		             
    	    		         //    Hime.addClientChatMessage("java.lang.pointereception thingy" + Math.random());
    	    		             ++this.ticks;
    	    		             if (ticks >= 10 && ticks != 50) {
    	    		                 mc.thePlayer.sendQueue.addToSendQueueSilent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
    	    		                 mc.thePlayer.sendQueue.addToSendQueueSilent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem = prevSlot2));
    	    		                 ticks = 50;
    	    		             } else if (ticks == 1) {
    	    		                 mc.thePlayer.sendQueue.addToSendQueueSilent(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
    	    		             }
    	    		           }else if(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFishingRod && !this.hypixelDamaged) {
    	    			                  event.setPitch(-90);
    	    			                  MovementUtils.doStrafe();
    	    			                  mc.thePlayer.motionX = 0;
    	    			                  mc.thePlayer.motionY = 0;
    	    			                  mc.thePlayer.motionZ = 0;
    	    			                  mc.thePlayer.jumpMovementFactor = 0;
    	    			                  mc.thePlayer.onGround = false;
    	    			               //   Hime.addClientChatMessage("java.lang.pointereception thingy2" + Math.random());
    	    			                  ++this.ticks;
    	    			                  if (ticks >= 10 && ticks != 50) {
    	    			                    //  mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
    	    			                    //  mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem = prevSlot2));
    	    			                      ticks = 50;
    	    			                  } else if (ticks == 3) {
    	    			                	 event.setPitch(-90);
    	    			                      mc.thePlayer.sendQueue.addToSendQueueSilent(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
    	    			                  }
    	    		           }
    	    		    	  }
    	    		           if(ticks == 50 && mc.thePlayer.hurtTime > 0) {
    	    		           	this.hypixelDamaged = true;
    	    		          // 	if(this.bowjump.getValBoolean()) {
    	    		           	mc.thePlayer.motionY += 0.002;
    	    		           	mc.thePlayer.setSpeed(0.102f);
    	    		           		if(collided != true) {
    	    		                this.mc.thePlayer.jump();
    	    		                Hime.addClientChatMessage("a" + stage);
    	    		               
    	    		           		}
    	    		           	   stage++;
    	    		           		if(stage == 1) {
    	    		                 this.collided = true;
    	    		           		}
    	    		            //  }
    	    		           }
    	    		           if(this.hypixelDamaged) {
    	    		           //fly code here
    	    		        	 if(stage > 4) {
    	    		        	//	 MovementUtils.actualSetSpeed(0.6);
    	    		        	  if(mc.thePlayer.isMoving()) {
    	    		        	 /*  mc.thePlayer.motionY = 0;
    	    		                  if (mc.thePlayer.ticksExisted % 2 == 0) {
    	    		                      mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0006185623653, mc.thePlayer.posZ);
    	    		                  } else {
    	    		                      mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.0006185623653, mc.thePlayer.posZ);
    	    		                  }*/
    	    		        	  }
    	    		        	 }
    	    		        /*	if (this.mc.thePlayer.onGround) {
    	    		         		if(this.bowjump.getValBoolean()) {
    	    		               //    this.mc.thePlayer.jump();
    	    		                   //this.collided = true;
    	    		                 }
    	    		            }
    	    		         //  MovementUtils.actualSetSpeed(0.2);	
    	    		           if(this.speedchange.getValBoolean()) {
    	    		            mc.thePlayer.setSpeed((float) this.bowspeed.getValDouble());
    	    		        	//  if(collided = true)
    	    		        	  // MovementUtils.actualSetSpeed(0.2);	
    	    		           }
    	    		           if(this.ychange.getValBoolean()) {
    	    		            mc.thePlayer.motionY += this.bowyvalue.getValDouble();
    	    		           }
    	    		           mc.timer.timerSpeed = (float) this.bowtimer.getValDouble();*/
    	    		           }
    	    	  }
    	    }
        if(mode.getValString().equalsIgnoreCase("Motion")) {
           		//	NotificationManager.show(new Notification(NotificationType.WARNING, "Mineplex Teleport fly", "You have 2 sec. to fly", 2));
        	  mc.thePlayer.motionY = 0;
              if (mc.gameSettings.keyBindJump.isKeyDown()) {
                  mc.thePlayer.motionY = this.speedmotion.getValDouble();
              } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                  mc.thePlayer.motionY = -this.speedmotion.getValDouble();
              }
              MovementUtils.actualSetSpeed(this.speedmotion.getValDouble());
        }
        
        if(mode.getValString().equalsIgnoreCase("On Damage")) {
       		//	NotificationManager.show(new Notification(NotificationType.WARNING, "Mineplex Teleport fly", "You have 2 sec. to fly", 2));
        	damageHypixel();
    	  if( mc.thePlayer.hurtTime > 1) {
          mc.thePlayer.motionY = 0;
          if (mc.gameSettings.keyBindJump.isKeyDown()) {
              mc.thePlayer.motionY = this.speedmotion.getValDouble();
          } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
              mc.thePlayer.motionY = -this.speedmotion.getValDouble();
          }
          MovementUtils.actualSetSpeed(this.speedmotion.getValDouble());
    	  }
    }
        	
        if(mode.getValString().equalsIgnoreCase("Collide")) {
     //	   mc.thePlayer.motionY = 0;
     	 //  mc.thePlayer.onGround = true;
     	 //  mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
        	mc.thePlayer.boundingBox = new AxisAlignedBB(-2, -1, -2, 2, 1, 2).offset(event.getX(), event.getY(), event.getZ());
        }
        
        if(mode.getValString().equalsIgnoreCase("Minemen")) {
        	 if(mc.thePlayer.ticksExisted % 2 == 0) mc.thePlayer.jump();   
        }
        
        if(mode.getValString().equalsIgnoreCase("Redesky")) {
            mc.thePlayer.jumpMovementFactor = 0.09F;
            if(ticks < 10) {
                mc.timer.timerSpeed = 1F;
                ticks++;
                mc.thePlayer.motionY = 1F;
            } else {
                mc.timer.timerSpeed = 1F;
            }
        }
        
        if(mode.getValString().equalsIgnoreCase("RedeskySlow")) {
            mc.thePlayer.motionY = 0;
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.thePlayer.motionY = 0.74f;
            } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.thePlayer.motionY = -0.74f;
            }
            MovementUtils.actualSetSpeed(0.54);
            mc.timer.timerSpeed = 0.2f;
        }

        if(mode.getValString().equalsIgnoreCase("Vanilla")) {
            mc.thePlayer.capabilities.isFlying = true;
            mc.thePlayer.capabilities.setFlySpeed((float) this.speedvanilla.getValDouble() / 10);
        }
        if(mode.getValString().equalsIgnoreCase("OldAAC")) {
        	if (mc.theWorld != null && mc.thePlayer != null) {
                // OLD AAC
                if (mc.thePlayer.fallDistance > 0.0F)
                    mc.thePlayer.motionY = mc.thePlayer.ticksExisted % 2 == 0 ? 0.1 : 0.0;
            }
        }
        
        if(mode.getValString().equalsIgnoreCase("Viper")) {
    		mc.timer.timerSpeed = 1.0f;
    	    mc.thePlayer.capabilities.allowFlying = true;
    	    if (time.hasTimePassed(1550L)) {
    	        mc.thePlayer.motionY = 0.0;
    	        mc.thePlayer.onGround = true;
    	        mc.thePlayer.noClip = true;
    	        mc.timer.timerSpeed = 1.18f;
    	        mc.thePlayer.motionY = -0.6;
    	        time.reset();
    	    }
    	
    	    if (mc.thePlayer.isMoving()) {
    	        mc.timer.timerSpeed = 0.99f;
    	        mc.thePlayer.setSpeed((float) (this.getBaseMoveSpeed() - 0.00284));
    	        if (mc.thePlayer.hurtTime > 0) {
    	            mc.thePlayer.setSpeed((float) (MovementUtils.getSpeed() * 1.25));
    	        }
    	    }
    	    if (mc.thePlayer.ticksExisted % 2 == 0) {
    	        mc.thePlayer.onGround = false;
    	        //break Label_0310;
    	    }
    	    mc.thePlayer.motionY = -0.08;
    	    if (Keyboard.isKeyDown(57)) {
    	        mc.thePlayer.motionY = (mc.thePlayer.motionY + 0.87) * 2.85;
    	    }
     }
        if(mode.getValString().equalsIgnoreCase("Viper2")) {
        	this.setSuffix("Hypixel");
        	if(mc.thePlayer.isMoving()) {
        		mc.thePlayer.setSpeed(2F);
        		if(mc.gameSettings.keyBindJump.isKeyDown()) {
        			mc.thePlayer.motionY = 0.5F;
        		} else if(mc.gameSettings.keyBindSneak.isKeyDown()) {
        			mc.thePlayer.motionY = -0.5F;
        		}
        		
        		if(!mc.gameSettings.keyBindSneak.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown()) {
        			mc.thePlayer.motionY = 0;
        		}
        		
        	} else {
            	mc.thePlayer.setVelocity(0, 0, 0);
        	}
        }
   
        	
        if(mode.getValString().equalsIgnoreCase("MCCenteral")) {
        	
        	double value = 2.5f;
        	
        	double x = MovementUtils.getPosForSetPosX(value);
        	double z = MovementUtils.getPosForSetPosZ(value);
        	
        	if(mc.gameSettings.keyBindForward.pressed) {
        			mc.thePlayer.motionY = 0;
        		mc.thePlayer.motionX = x;
        		mc.thePlayer.motionZ = z;
        	}
        	
        }
       
        if(mode.getValString().equalsIgnoreCase("Rewinside")) {
            mc.thePlayer.setSprinting(false);
            mc.thePlayer.motionY = 0.0D;
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-10D, mc.thePlayer.posZ);
            mc.thePlayer.onGround = true;
            if (mc.thePlayer.ticksExisted % 3 == 0) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.0E-10D, mc.thePlayer.posZ, true));
            }
        }
   }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2875;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }	
    
  @Handler
    public void onPacketReceive(EventReceivePacket event) {
	  if(this.lagback.getValBoolean()) {
      if (event.getPacket() instanceof net.minecraft.network.play.server.S08PacketPlayerPosLook) {
          NotificationManager.show(new Notification(NotificationType.WARNING, "LagBack Alert", "Flight Disabled!", 2));
          this.toggle();
       }
      if(mode.getValString().equalsIgnoreCase("Redesky")){
          if(mc.thePlayer != null) {
              if(event.getPacket() instanceof C03PacketPlayer) {

              }
          }
      }
	  }
    }
    
   

    public void onDisable() {
        time2.reset();
    	mc.timer.updateTimer();
    	mc.timer.timerSpeed = 1.0F;
      super.onDisable();
      this.hypixelDamaged = false;
    	this.trys = 0;
    	mc.thePlayer.setSpeed(1F);
    	stage = 0;
    	ticks = 0;
    	 mc.thePlayer.speedInAir = (float) 0.02;
    	//if(mode.getValString().equalsIgnoreCase("Redesky"))
    	    //  mc.thePlayer.removePotionEffect(1);
        super.onDisable();
        mc.thePlayer.motionX = 0;
        time.reset();
    	mc.thePlayer.motionY = 0;
    	mc.thePlayer.motionZ = 0;
        time5.reset();
        mc.timer.timerSpeed = 1.0F;
        mc.timer.timerSpeed = 1F;
        delay = 0;
   
    	time4.reset();
    	time3.reset();
    
    	  if(mode.getValString().equalsIgnoreCase("Hypixel") && hypixelMode.getValString().equalsIgnoreCase("Blink")|| hypixelMode.getValString().equalsIgnoreCase("BlinkSlow")){
              try {
    		  for(Packet packet : packets){
                  mc.getNetHandler().addToSendQueueSilent(packet);
              }
              packets.clear();
              }catch(Exception e) {
            	  e.printStackTrace();
              }
          }
    	  if(mode.getValString().equalsIgnoreCase("Hypixel")) {
    	   if(hypixelMode.getValString().equalsIgnoreCase("Old")){
    		if(this.blink.getValBoolean()) {
    			 try {
           		  for(Packet packet : packets){
                         mc.getNetHandler().addToSendQueueSilent(packet);
                     }
                     packets.clear();
                     }catch(Exception e) {
                   	  e.printStackTrace();
                     }	 
    		}
    	   }
    	   if(hypixelMode.getValString().equalsIgnoreCase("BowNew")){
       			 try {
              		  for(Packet packet : packets){
                            mc.getNetHandler().addToSendQueueSilent(packet);
                        }
                        packets.clear();
                        }catch(Exception e) {
                      	  e.printStackTrace();
                        }	 
       	   }
    	  if(hypixelMode.getValString().equalsIgnoreCase("Full Disabler") && this.blink.getValBoolean()){
    		  try {
        		  for(Packet packet : packets){
                      mc.getNetHandler().addToSendQueueSilent(packet);
                  }
                  packets.clear();
                  }catch(Exception e) {
                	  e.printStackTrace();
                  }	 
    	   }
    	  }
            mc.thePlayer.capabilities.isFlying = false;
         
    }
    public void damagePlayerCubecraft(){
        if(mc.thePlayer.onGround) {
            double x = mc.thePlayer.posX;
            double y = mc.thePlayer.posY;
            double z = mc.thePlayer.posZ;

            for(int i = 0; i < 65; i++) {
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.049, z, false));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
            }
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
        }
    }
    
    public void VerusTestTest(){
            for (int i = 0; i < PlayerUtils.getMaxFallDist() / 0.051612F + 1.0; ++i) {
                 mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.30, mc.thePlayer.posZ, false));
                // mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 5.01E-4D, mc.thePlayer.posZ, false));
                // mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.006061691F + 6.01E-8D, mc.thePlayer.posZ, false));
            }
             mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer(true));
    }
    
    public void damageHypixel() {
        double offset = 0.060100000351667404D;
        NetHandlerPlayClient netHandler = mc.getNetHandler();
        EntityPlayerSP player = mc.thePlayer;
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;

        for(int i = 0; (double)i < (double)getMaxFallDist() / 0.05510000046342611D + 1.0D; ++i) {
           netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.060100000351667404D, z, false));
           netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 5.000000237487257E-4D, z, false));
           netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.004999999888241291D + 6.01000003516674E-8D, z, false));
        }

        netHandler.addToSendQueueSilent(new C03PacketPlayer(true));
     }
	 public static float getMaxFallDist() {
	      PotionEffect potioneffect = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.jump);
	      int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
	      return (float)(Minecraft.getMinecraft().thePlayer.getMaxFallHeight() + f);
	   }

	 public double getJumpBoostModifier(double baseJumpHeight) {
	        if (mc.thePlayer.isPotionActive(Potion.jump)) {
	            final int amplifier = mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
	            baseJumpHeight += (amplifier + 1) * 0.1f;
	        }
	        return baseJumpHeight;
	    }
	 public void damageRedesky() {
	        double offset = 0.060100000351667404D;
	        NetHandlerPlayClient netHandler = mc.getNetHandler();
	        EntityPlayerSP player = mc.thePlayer;
	        double x = player.posX;
	        double y = player.posY;
	        double z = player.posZ;

	        for(int i = 0; (double)i < (double)getMaxFallDist() / 0.05510000046342611D + 1.0D; ++i) {
	           netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.060100000351667404D, z, false));
	           netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 5.000000237487257E-4D, z, false));
	           netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.004999999888241291D + 6.01000003516674E-8D, z, false));
	        }
	 }
	 
}