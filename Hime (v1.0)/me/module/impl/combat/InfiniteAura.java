package me.module.impl.combat;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import me.Hime;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.event.impl.Event3D;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.RotationUtils;
import me.util.TimeUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Vec3;

public class InfiniteAura extends Module {
    public Setting hive;
    public Setting delay;
    public Setting range;
    private float lastHealth = 0.0F;
    EntityLivingBase target;
    public InfiniteAura(){
        super("InfiniteAura", Keyboard.KEY_NONE, Category.COMBAT);
        Hime.instance.settingsManager.rSetting(delay = new Setting("InfAura CPS",this, 5, 1, 20, true));
        
        Hime.instance.settingsManager.rSetting(range = new Setting("InfAura Range",this, 100, 1, 300, true));

        Hime.instance.settingsManager.rSetting(hive = new Setting("Hive",this, false));
    }
    
    double startX;
    
    double startY;
    
    double startZ;
    private ArrayList<Vec3> path = new ArrayList<>();
    private List<Vec3>[] test = new ArrayList[50];
    private List<EntityLivingBase> targets = new CopyOnWriteArrayList<>();
    private TimeUtil cps = new TimeUtil();
    public static TimeUtil timer = new TimeUtil();
    public static boolean canReach;
    int ticks = 0;

    @Handler
    public void onPre(EventUpdate event){
     this.target = this.getClosest(range.getValDouble());

   if (hive.getValBoolean())
    this.mc.thePlayer.setPosition(this.startX, this.startY, this.startZ); 
    	
   if (this.cps.hasTimePassed((long)(1000.0D / delay.getValDouble())) && 
		      this.target != null) {
	   
	   if (this.mc.thePlayer.getDistanceToEntity(target) > this.range.getValDouble())
	          return; 
	   
	   this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(target.posX, target.posY, target.posZ, true)); 
       
	   this.mc.thePlayer.swingItem();
       this.mc.playerController.attackEntity(this.mc.thePlayer, target);
	   
       float[] rots = RotationUtils.getRotations(target);
       event.setYaw(rots[0]);
       event.setPitch(rots[1]);
       
	   cps.reset();
	   }
    }
    @Handler
    public void on3D(Event3D event){
		if(target != null) {
			double x = target.lastTickPosX + (target.posX - target.lastTickPosX) * this.mc.timer.elapsedPartialTicks
			- this.mc.getRenderManager().renderPosX;
			double y = target.lastTickPosY + (target.posY - target.lastTickPosY) * this.mc.timer.elapsedPartialTicks
			- this.mc.getRenderManager().renderPosY;
			double z = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * this.mc.timer.elapsedPartialTicks
			- this.mc.getRenderManager().renderPosZ;

	
			GL11.glPushMatrix();
			GL11.glTranslated(x, y - 0.2D, z);
			GL11.glScalef(0.03F, 0.03F, 0.03F);


			GL11.glRotated(-this.mc.getRenderManager().playerViewY, 0.0D, 1.0D, 0.0D);
			GlStateManager.disableDepth();
			
			Gui.drawRect(-20, -1, -21, 75, Color.BLACK.hashCode());


			Gui.drawRect(20, -1, 21, 75, Color.BLACK.hashCode());
			//Gui.drawRect(21, 0, 25, 74, getRainbow(6000, -15));
			

			Gui.drawRect(-20, -1, 21, 0, Color.BLACK.hashCode());
			//Gui.drawRect(-21, 0, 24, 4, getRainbow(6000, -15));
			

			Gui.drawRect(-20, 74, 21, 75, Color.BLACK.hashCode());
			//Gui.drawRect(-21, 71, 25, 74, getRainbow(6000, -15));

			GlStateManager.enableDepth();
			GL11.glPopMatrix();	    
		}
    }

    double dashDistance = 5;
    public boolean canAttack(EntityLivingBase player) {
    	return (player != this.mc.thePlayer &&
    			!(player instanceof net.minecraft.entity.item.EntityArmorStand) 
    			&& player instanceof EntityPlayer && !Antibot.isBot((EntityPlayer)player));
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
    

    public void onEnable() {
        super.onEnable();
        this.startX = this.mc.thePlayer.posX;
        this.startY = this.mc.thePlayer.posY;
        this.startZ = this.mc.thePlayer.posZ;
      }



}
