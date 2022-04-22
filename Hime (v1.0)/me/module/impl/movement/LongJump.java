package me.module.impl.movement;

import java.util.ArrayList;

import me.Hime;
import org.lwjgl.input.Keyboard;

import me.event.impl.EventUpdate;
import me.event.impl.LivingUpdateEvent;
import me.event.impl.MoveEvent;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.MovementUtils;
import me.util.PlayerUtils;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class LongJump extends Module {
    public LongJump() {
        super("LongJump", Keyboard.KEY_L, Category.MOVEMENT);
    }
    private boolean doJump;
	private double moveSpeed;
	private double lastDist;
	private double G8Gay;
	private double moveSpeed2;
	private double Z;
	private int ticks;
	private int stage = 0;
	 private float air;
	    double motionY;
	    boolean collided;
	
	double speed = 0.0D;
	private boolean hasJumped;
	public boolean hypixelDamaged = false;
    
	public String mode;
	public Setting ee;
	
	public Setting bowjump;
	public Setting bowMultiplier;

    private double z;
    private double O;
	
    @Override
    public void setup() {
    	 ArrayList<String> options = new ArrayList<String>();
         options.add("WatchdogBow");
         this.addModes("Mode", "WatchdogBow");
         this.mode = this.getModes("Mode");
         Hime.instance.settingsManager.rSetting(ee = new Setting("Hypixel Damage Start", this, false));
    }
    
    protected boolean boosted = false;
    int prevSlot2;
    protected double startY = 0;
    protected double motionVa = 2.8;

    private boolean J;
    
    @Override
    public void onEnable() {
    super.onEnable();
    lastDist = 0;
    stage = 0;
    ticks = 0;
    prevSlot2 = mc.thePlayer.inventory.currentItem;
    motionY = mc.thePlayer.motionY;
    air = 0;
    collided = false;
    mc.thePlayer.jump();
    }
    @Handler
    public void onUpdate(EventUpdate event) {
        if (event.isPre()) {
                    mc.timer.timerSpeed = 0.9F;
        }
    }
    
    @Handler
	public void onLivingUpdate(LivingUpdateEvent event) {

	}
    
    public void damageNew() {
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ;
        		
	    double fallDistance = 4.5000; // fall distance is changeable based on how much damage you want
	    while (fallDistance > 0) {
	        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.0624986421, z, false));
	        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.0000013579, z, false));
	        fallDistance -= 0.0624986421;
	    }
	    mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
    }
    
    @Handler
	public void onMove(MoveEvent event) {
        if(this.mc.thePlayer.onGround){
            if(this.J){
                event.actualSetSpeedY(this.mc.thePlayer.motionY = this.mc.thePlayer.getBaseMotionY());
                this.moveSpeed *= 2.139999980926514D;
            }

            this.moveSpeed = this.mc.thePlayer.getBySprinting() * 2.0D;
        }
        if(this.J) {
            this.moveSpeed = this.O - 0.66D * (this.O - this.z);
        }
        this.moveSpeed -= this.O / 24.0D;
        if(!this.mc.thePlayer.isPotionActive(Potion.jump) && this.mc.thePlayer.motionY < 0.0D) {
            this.moveSpeed = this.z;
            if(this.mc.thePlayer.ticksExisted % 2 == 0 && (double)this.mc.thePlayer.fallDistance < 0.45D) {
                this.moveSpeed = this.z * 1.2D;
                this.mc.thePlayer.motionY = 0.0D;
            }
        }


        this.moveSpeed = Math.max(this.moveSpeed, this.z);
        MovementUtils.setSpeed(event, this.moveSpeed);
	}
  
    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2875;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }	

    public void onDisable() {
     mc.thePlayer.speedInAir = (float) 0.02;
     this.boosted = false;
     this.hypixelDamaged = false;
      this.mc.timer.timerSpeed = 1.0F;
     this.motionVa = 2.8D;
     ticks = 0;
     super.onDisable();
    }
}
