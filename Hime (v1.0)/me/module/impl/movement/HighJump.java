package me.module.impl.movement;

import java.util.ArrayList;

import me.Hime;
import org.lwjgl.input.Keyboard;

import me.event.impl.EventUpdate;
import me.event.impl.MoveEvent;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.MovementUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;



public class HighJump extends Module{
public Setting mode;
public Setting mot;
public boolean canHighjump = false;
public boolean doSlow = false;
double moveSpeed = 0;

int jumpCount;
	public HighJump() {
		super("HighJump", Keyboard.KEY_NONE, Category.MOVEMENT);
	}

	  @Override
	  public void setup() {
	      ArrayList<String> options = new ArrayList<String>();
	      options.add("Vanilla");
	      options.add("Hive");
	      options.add("Hypixel");
	      options.add("Redesky");
	      Hime.instance.settingsManager.rSetting(mode = new Setting("Jump Mode", this, "Vanilla", options));
	      Hime.instance.settingsManager.rSetting(mot = new Setting("HighJump Motion", this, 3, 0, 10, false));
	      //Hime.instance.settingsManager.rSetting(dist = new Setting("HighJump Fall Distance", this, 20, 1, 40, false));
	  }
	public void onDisable() {
		canHighjump = false;
		doSlow = false;
		moveSpeed = 0;
		 mc.timer.timerSpeed = 1.0F;
		 jumpCount = 0;
	    super.onDisable();
	  }
	  
	  @Handler
	  public void onUpdate(EventUpdate event) {
		  this.setSuffix(mode.getValString());
	    	    if(mode.getValString().equalsIgnoreCase("Hive")) {
	    	    	if(mc.thePlayer.onGround)
	    	    		mc.thePlayer.motionY += 6;
	    	    }
	    	    if(mode.getValString().equalsIgnoreCase("Hypixel")) {
	    	    	if (!this.isBlockUnder() && mc.thePlayer.fallDistance > mot.getValDouble() * 5) {
	                    canHighjump = true;
	                    mc.thePlayer.fallDistance = 0;
	                    mc.thePlayer.motionY = this.mot.getValDouble();
	                    jumpCount++;
	                }
	                if (mc.thePlayer.onGround) {
	                    jumpCount = 0;
	                }
	    	    }
	         if(mode.getValString().equalsIgnoreCase("Vanilla")) {
	        	if(mc.thePlayer.onGround)
	    		 mc.thePlayer.motionY = 0.7;
	         }
	  }
	  
	  @Handler
	  public void onMove(MoveEvent event) {
		if(mode.getValString().equalsIgnoreCase("Hypixel")) {
		  if (!mc.thePlayer.onGround && !this.isBlockUnder() && mc.thePlayer.hurtTime != 0) {
              if (canHighjump) {
                  moveSpeed = 0.625 - 1.0E-4D;
                  doSlow = true;
              }
              if (doSlow) {
                  moveSpeed -= (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.66 : 0.6334) * (moveSpeed - MovementUtils.getBaseMoveSpeed());
                  doSlow = false;
              } else {
                  moveSpeed -= moveSpeed / 159;
              }
       //    Hime.addClientChatMessage("Highjump Move: " + moveSpeed);
              MovementUtils.setSpeed(event, moveSpeed);
          } 
		}
	  }
	  
		public static boolean isBlockUnder() {
	        if(Minecraft.getMinecraft().thePlayer.posY < 0)
	            return false;
	        for(int off = 0; off < (int)Minecraft.getMinecraft().thePlayer.posY+2; off += 2){
	            AxisAlignedBB bb = Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0, -off, 0);
	            if(!Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(Minecraft.getMinecraft().thePlayer, bb).isEmpty()){
	                return true;
	            }
	        }
	        return false;
	    }

}
