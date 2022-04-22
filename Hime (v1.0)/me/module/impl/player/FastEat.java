package me.module.impl.player;



import me.Hime;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastEat extends Module{

	public Setting amount;
	
    public FastEat() {
        super("FastEat", 0, Category.PLAYER);
        
        Hime.instance.settingsManager.rSetting(amount  = new Setting("Packet Amount", this, 15, 1, 25, true));
    }
  

        @Handler
        public void onPre(EventUpdate event) {
        	this.setSuffix(this.amount.getValInt() + "");
        	if(mc.thePlayer.getItemInUse() != null) {
        	 if (this.mc.thePlayer.getItemInUse().getItem() instanceof ItemFood || this.mc.thePlayer.getItemInUse().getItem() instanceof ItemBow) {
        	 for (int i = 0; i < this.amount.getValDouble(); i++) {
        	   mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true)); 
        	 }
          }
         }
        }


        public boolean isEating() {
            return mc.thePlayer.isEating();
        }
    }