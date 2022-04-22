package me.module.impl.player;



import me.Hime;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow extends Module{

	public Setting hypixel;
	public Setting matrix;
	
    public NoSlow() {
        super("NoSlow", 0, Category.PLAYER);
    }
    @Override
    public void setup() {
         Hime.instance.settingsManager.rSetting(hypixel = new Setting("Hypixel", this, true));
         Hime.instance.settingsManager.rSetting(matrix = new Setting("Matrix", this, false));
    }

        @Handler
        public void onPre(EventUpdate event) {
        	if(this.matrix.getValBoolean()) {
        		if(mc.thePlayer.isUsingItem()) {
        			mc.thePlayer.stopUsingItem();
        		}
        	}
           if(hypixel.getValBoolean()) {
        	if(event.isPre()) {
            if (mc.thePlayer.isMoving() && mc.thePlayer.isBlocking()) {
                mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
          }else if(event.isPost()) {
        	  if (mc.thePlayer.isMoving() && mc.thePlayer.isBlocking()) {
                   mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
        	  }
          }
         }
        }

        public boolean isMoving() {
            return mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f;
        }
    }