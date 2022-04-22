package me.module.impl.combat;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.Hime;
import me.event.impl.Event3D;
import me.event.impl.EventRenderHUD;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.AStarCustomPathFinder;
import me.util.RotationUtils;
import me.util.TimeUtil;
import me.util.Vec3;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class TPAura extends Module {
    public Setting maxtTargets;
    public Setting delay;
    public Setting hive;
    public TPAura(){
        super("TPAura", Keyboard.KEY_NONE, Category.COMBAT);
        Hime.instance.settingsManager.rSetting(delay = new Setting("TpAura CPS",this, 10, 1, 20, true));
        Hime.instance.settingsManager.rSetting(maxtTargets = new Setting("Max Targets",this, 1, 1, 10, true));
        Hime.instance.settingsManager.rSetting(hive = new Setting("Hive Bypass",this, false));
    }
    private ArrayList<Vec3> path = new ArrayList<>();
    private List<Vec3>[] test = new ArrayList[50];
    private List<EntityLivingBase> targets = new CopyOnWriteArrayList<>();
    private TimeUtil cps = new TimeUtil();
    public static TimeUtil timer = new TimeUtil();
    public static boolean canReach;
    int ticks = 0;
    double startX;
    double startY;
    double startZ;
    private float lastHealth = 0.0F;

    @Handler
    public void onPre(EventUpdate event){
        if(event.isPre()){
            this.targets = getTargets();
            if (hive.getValBoolean())
              this.mc.thePlayer.setPosition(this.startX, this.startY, this.startZ); 
            if (this.cps.hasTimePassed((long)(1000.0D / delay.getValDouble())) && 
              this.targets.size() > 0) {
              this.test = (List<Vec3>[])new ArrayList[50];
              for (int i = 0; i < (targets.size() > 1 ? maxtTargets.getValDouble() : targets.size()); i++) {
                EntityLivingBase T = this.targets.get(i);
                if (this.mc.thePlayer.getDistanceToEntity(T) > 300.0F)
                  return; 
                Vec3 topFrom = new Vec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
                Vec3 to = new Vec3(T.posX, T.posY, T.posZ);
                this.path = computePath(topFrom, to);
                this.test[i] = this.path;
                for (Vec3 pathElm : this.path)
                  this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true)); 
                this.mc.thePlayer.swingItem();
                this.mc.playerController.attackEntity(this.mc.thePlayer, T);
                Collections.reverse(this.path);
                for (Vec3 pathElm : this.path)
                  this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true)); 
               
                float[] rots = RotationUtils.getRotations(T);
                event.setYaw(rots[0]);
                event.setPitch(rots[1]);
              } 
              this.cps.reset();
            } 
        }
    }
    
    @Handler
    public void on3D(Event3D event){
        if(targets.size() > 0){
            for (int i = 0; i < path.size(); i++) {
                Vec3 pathElm = path.get(i);
                Vec3 pathOther = path.get(i < path.size() - 1 ? i + 1 : i);


                double x = pathElm.getX() + (pathElm.getX() - pathElm.getX()) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
                double y = pathElm.getY() + (pathElm.getY() - pathElm.getY()) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY;
                double z = pathElm.getZ() + (pathElm.getZ() - pathElm.getZ()) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;
                double x1 = pathOther.getX() + (pathOther.getX() - pathOther.getX()) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
                double y1 = pathOther.getY() + (pathOther.getY() - pathOther.getY()) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY;
                double z1 = pathOther.getZ() + (pathOther.getZ() - pathOther.getZ()) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;
                drawLine(new double[]{255, 255, 255}, x1, y1, z1, x, y, z);

            }
        }
    }
    private void drawLine(double[] color, double x, double y, double z, double playerX, double playerY, double playerZ) {


        GlStateManager.color(255, 255, 255, 255);

        GL11.glLineWidth(5);
        GL11.glBegin(1);
        GL11.glVertex3d(playerX, playerY, playerZ);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glColor4f(1,1,1,1);
    }
    double dashDistance = 5;
    
    public boolean canAttack(EntityLivingBase player) {
    	return (player != this.mc.thePlayer &&
    			!(player instanceof net.minecraft.entity.item.EntityArmorStand) 
    			&& player instanceof EntityPlayer && !Antibot.isBot((EntityPlayer)player));
    }

    private List<EntityLivingBase> getTargets() {
        List<EntityLivingBase> targets = new ArrayList<>();

        for (Object o : mc.theWorld.getLoadedEntityList()) {
            if (o instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) o;
                if (canAttack(entity)) {
                    targets.add(entity);
                }
            }
        }
        targets.sort((o1, o2) -> (int) (o1.getDistanceToEntity(mc.thePlayer) * 1000 - o2.getDistanceToEntity(mc.thePlayer) * 1000));
        return targets;
    }
    private ArrayList<Vec3> computePath(Vec3 topFrom, Vec3 to) {
        if (!canPassThrow(new BlockPos(topFrom.mc()))) {
            topFrom = topFrom.addVector(0, 1, 0);
        }
        AStarCustomPathFinder pathfinder = new AStarCustomPathFinder(topFrom, to);
        pathfinder.compute();

        int i = 0;
        Vec3 lastLoc = null;
        Vec3 lastDashLoc = null;
        ArrayList<Vec3> path = new ArrayList<Vec3>();
        ArrayList<Vec3> pathFinderPath = pathfinder.getPath();
        for (Vec3 pathElm : pathFinderPath) {
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.addVector(0.5, 0, 0.5));
                }
                path.add(pathElm.addVector(0.5, 0, 0.5));
                lastDashLoc = pathElm;
            } else {
                boolean canContinue = true;
                if (pathElm.squareDistanceTo(lastDashLoc) > dashDistance * dashDistance) {
                    canContinue = false;
                } else {
                    double smallX = Math.min(lastDashLoc.getX(), pathElm.getX());
                    double smallY = Math.min(lastDashLoc.getY(), pathElm.getY());
                    double smallZ = Math.min(lastDashLoc.getZ(), pathElm.getZ());
                    double bigX = Math.max(lastDashLoc.getX(), pathElm.getX());
                    double bigY = Math.max(lastDashLoc.getY(), pathElm.getY());
                    double bigZ = Math.max(lastDashLoc.getZ(), pathElm.getZ());
                    cordsLoop:
                    for (int x = (int) smallX; x <= bigX; x++) {
                        for (int y = (int) smallY; y <= bigY; y++) {
                            for (int z = (int) smallZ; z <= bigZ; z++) {
                                if (!AStarCustomPathFinder.checkPositionValidity(x, y, z, false)) {
                                    canContinue = false;
                                    break cordsLoop;
                                }
                            }
                        }
                    }
                }
                if (!canContinue) {
                    path.add(lastLoc.addVector(0.5, 0, 0.5));
                    lastDashLoc = lastLoc;
                }
            }
            lastLoc = pathElm;
            i++;
        }
        return path;
    }

    private boolean canPassThrow(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(new net.minecraft.util.BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock();
        return block.getMaterial() == Material.air || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine || block == Blocks.ladder || block == Blocks.water || block == Blocks.flowing_water || block == Blocks.wall_sign || block == Blocks.standing_sign;
    }

    @Handler
    public void onRenderHUD(EventRenderHUD event) {
    	  for (int i = 0; i < (targets.size() > 1 ? maxtTargets.getValDouble() : targets.size()); i++) {
              EntityLivingBase T = this.targets.get(i);
  		   GlStateManager.pushMatrix();
  		   float width = (float)(event.getWidth() / 2.0D + 100.0D);
  	        float height = (float)(event.getHeight() / 2.0D);
  	        Gui.drawRect((width - 70.0F), (height + 30.0F) - (i * 80), (width + 80.0F), (height + 105.0F) - (i * 80), (new Color(0, 0, 0, 180)).getRGB());
  	        float health = T.getHealth();
  	        float healthPercentage = health / T.getMaxHealth();
  	        float targetHealthPercentage = 0.0F;
  	        if (healthPercentage != this.lastHealth) {
  	          float diff = healthPercentage - this.lastHealth;
  	          targetHealthPercentage = this.lastHealth;
  	          this.lastHealth += diff / 8.0F;
  	        } 
  	        Color healthcolor = Color.WHITE;
  	        if (healthPercentage * 100.0F > 75.0F) {
  	          healthcolor = Color.GREEN;
  	        } else if (healthPercentage * 100.0F > 50.0F && healthPercentage * 100.0F < 75.0F) {
  	          healthcolor = Color.YELLOW;
  	        } else if (healthPercentage * 100.0F < 50.0F && healthPercentage * 100.0F > 25.0F) {
  	          healthcolor = Color.ORANGE;
  	        } else if (healthPercentage * 100.0F < 25.0F) {
  	          healthcolor = Color.RED;
  	        } 
  		   Gui.drawRect((width - 70.0F), (height + 104.0F)- (i * 80), (width - 70.0F + 149.0F * targetHealthPercentage), (height + 106.0F)- (i * 80), healthcolor.getRGB());
  	       Gui.drawRect((width - 70.0F), (height + 104.0F)- (i * 80), (width - 70.0F + 149.0F * healthPercentage), (height + 106.0F)- (i * 80), Color.GREEN.getRGB());
  	       Hime.instance.rfr.drawString(((EntityPlayer) T).getName(), (int) (width - 30), (int) (height + 40)- (i * 80), -1);
           Hime.instance.rfrs.drawString("Distance: " + Math.round(T.getDistanceToEntity(mc.thePlayer)), (int) (width + 15), (int) (height + 62)- (i * 80), -1);
  	       Hime.instance.rfrs.drawString("Health: "+ Math.round(T.getHealth()), (int) (width - 30), (int) (height + 62)- (i * 80), -1);
  	       if(mc.thePlayer.getHealth() >= T.getHealth()) {
  	    	//Hime.instance.rfr.drawString("§2You may win", 350, 170, 0xfc0303);
             }else if(mc.thePlayer.getHealth() < T.getHealth()) {
  	       // Hime.instance.rfr.drawString("You may lose", 350, 170, 0xfc0303);
  	       }
  		   for(int i2 = 3; i2 > -1; i2--) {
  		    	ItemStack itemstack = ((EntityPlayer) T).inventory.armorInventory[i2];
  		    	int yAdd =  i2 * 15;
  		    	GL11.glPushMatrix();
  				RenderHelper.enableGUIStandardItemLighting();
  				mc.getRenderItem().renderItemAndEffectIntoGUI(itemstack, (int)(width + 15) - yAdd, (int) (height + 80)- (i * 80));
  				GL11.glPopMatrix();
  		   }
  			GL11.glPushMatrix();
  			RenderHelper.enableGUIStandardItemLighting();
  		    mc.getRenderItem().renderItemAndEffectIntoGUI(((EntityPlayer) T).getCurrentEquippedItem(), (int)(int) (width + 40),  (int) (height + 80)- (i * 80));
  			GL11.glPopMatrix();
  	    
  	        GlStateManager.popMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
  		    this.drawEntityOnScreen((int) (width - 50), (int) (height + 95)- (i * 80), 28, 25, 25, T);
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
    
    public void onEnable() {
        super.onEnable();
        this.startX = this.mc.thePlayer.posX;
        this.startY = this.mc.thePlayer.posY;
        this.startZ = this.mc.thePlayer.posZ;
      }

}
