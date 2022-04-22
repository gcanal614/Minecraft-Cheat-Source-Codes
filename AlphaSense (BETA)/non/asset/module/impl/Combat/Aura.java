package non.asset.module.impl.Combat;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import non.asset.Clarinet;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.event.impl.render.Render2DEvent;
import non.asset.event.impl.render.Render3DEvent;
import non.asset.module.Module;
import non.asset.module.impl.movement.Scaffold;
import non.asset.module.impl.visuals.TargetHUD;
import non.asset.utils.RenderUtil;
import non.asset.utils.OFC.CombatUtil;
import non.asset.utils.OFC.MathUtils;
import non.asset.utils.OFC.TimerUtil;
import non.asset.utils.font.MCFontRenderer;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.EnumValue;
import non.asset.utils.value.impl.NumberValue;
import non.asset.utils.value.impl.RangedValue;

public class Aura extends Module {
    public static EntityLivingBase target;
    
    private List<EntityLivingBase> targets = new ArrayList<>();

    //private EnumValue<sortmode> sortMode = new EnumValue<>("Sort Mode", sortmode.FOV);
    
    private double health = 0;
    private EnumValue<mode> Mode = new EnumValue<>("Mode", mode.SINGLE);
    private NumberValue<Integer> smooth = new NumberValue<>("Smooth Value", 100, 50, 100, 10, Mode, "smooth");
    private NumberValue<Integer> multitargets = new NumberValue<>("MultiTargets", 3, 1, 10, 1, Mode, "multi");
    private NumberValue<Integer> multifov = new NumberValue<>("MultiFOV", 75, 1, 180, 1, Mode, "multi");
    private RangedValue<Integer> cps = new RangedValue<>("CPS", 1, 20, 1, 7, 11);
    private NumberValue<Float> range = new NumberValue<>("Range", 4F, 1.0F, 7.0F, 0.1F);
    //private NumberValue<Float> blockrange = new NumberValue<>("Block Range", 5.0F, 1.0F, 15.0F, 0.1F);
    private NumberValue<Integer> maxTargets = new NumberValue<>("Max Targets", 3, 1, 5, 1);
    private NumberValue<Integer> switchSpeed = new NumberValue<>("Switch Speed", 300, 100, 1000, 50);
    private BooleanValue players = new BooleanValue("Players", "Target Players", true);
    private BooleanValue animals = new BooleanValue("Animals", "Target Animals", false);
    private BooleanValue monsters = new BooleanValue("Monsters", "Target Monsters", false);
    private BooleanValue invisibles = new BooleanValue("Invisibles", "Target Invisibles", false);
    private BooleanValue autoblock = new BooleanValue("AutoBlock", "Automatically Block", true);
    private BooleanValue teams = new BooleanValue("Teams", "Teams Mode", false);
    private BooleanValue throughwalls = new BooleanValue("Through Walls", "Attack Through Walls", true);
    private BooleanValue abck = new BooleanValue("Antibot check", "Attack Through Walls", true);
    private TimerUtil timerUtil = new TimerUtil();
    private TimerUtil switchTimer = new TimerUtil();
    private long time;
    private boolean groundTicks;
    private float[] serverAngles = new float[2];
    private float[] prevRotations = new float[2];
    private int switchI;
    private float oldYaw;
    public static MCFontRenderer otherfont = new MCFontRenderer(new Font("Tahoma", Font.PLAIN, 12), true, true);
	public float x = 0;
    public float y = 0;
    public float dsX = 0;
    public float dsY = 0;
    public float dsIDK = 0;
    private boolean confirm = false;
    public Aura() {
        super("Aura", Category.COMBAT);
        setRenderLabel("Aura");
    }


    private enum sortmode {
        FOV, HEALTH, DISTANCE
    }

    private enum mode {
        SINGLE, SWITCH, SMOOTH, AAC
    }
    
    @Override
    public void onDisable() {
    	confirm = false;
        dsX = 0;
        dsY = 0;
        dsIDK = 0;
    	x = 0;
        y = 0;
        getMc().timer.timerSpeed = 1f;
        switchI = 0;
        target = null;
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
    	
        setSuffix(StringUtils.capitalize(Mode.getValue().name().toLowerCase()));
        long ping = getMc().getCurrentServerData() == null ? 0 : Math.min(50, Math.max(getMc().getCurrentServerData().pingToServer, 110));
        int pingDelay = Math.round(ping / 10);
        
        boolean cant = AutoPot.doSoup || (abck.isEnabled() && isBot(target) && AntiBot.getBots().contains(target)) || AutoApple.doingStuff || AutoPot.healing || getMc().thePlayer.isSpectator() || Clarinet.INSTANCE.getModuleManager().getModuleClass(Scaffold.class).isEnabled();
        
        if (cant) return;
        
        if(AutoApple.doingStuff) return;
        
        if (event.isPre()) {
            event.setPitch(getMc().thePlayer.rotationPitch);
        }
        if (event.isPre()) {
        	if (target != null) {
            	if(canBlock()) {
            		if(!mc.thePlayer.isBlocking()) {
            			getMc().playerController.sendUseItem(getMc().thePlayer, getMc().theWorld, getMc().thePlayer.getHeldItem());
            		}
            	}
        	}
        }
        switch (Mode.getValue()) {
            case SINGLE:
                target = getTarget();
                if (event.isPre()) {
                    if (target != null) {
                    	//final float[] rots = getRotationsToEnt(target, getMc().thePlayer);
                        //event.setYaw(rots[0]);
                        //event.setPitch(rots[1]);
                        final float[] dstAngle = getRotationsToEnt(target, getMc().thePlayer);
                        final float[] srcAngle = new float[]{serverAngles[0], serverAngles[1]};
                        serverAngles = smoothAngle(dstAngle, srcAngle, 45);
                    	event.setYaw(serverAngles[0]);
                    	event.setPitch(serverAngles[1]);
                    }else {
                    	timerUtil.reset();
                    }
                }else {
                    if (target != null) {
                    	

                        if (event.isPre()) {
                            if (target != null) {
                		        if(mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.contains("redesky")) {
                		            mc.playerController.attackEntity(mc.thePlayer, target);
                		        }
                            }
                        }
                        
                        if (timerUtil.sleep(1000 / getCPS())) {
                        	attackEntity(target, false);
                        }
                    }else{
                    	timerUtil.reset();
                    }
                }
                break;
            case SWITCH:
                if (event.isPre()) {
                    if (target != null) {
                        final float[] rots = getRotationsToEnt(target, getMc().thePlayer);
                        event.setYaw(rots[0]);
                        event.setPitch(rots[1]);
                    }
                }
                if (!event.isPre()) {
                    final ArrayList<EntityLivingBase> closeEntitys = new ArrayList<>();
                    getMc().theWorld.getLoadedEntityList().stream().filter(entity -> entity instanceof EntityLivingBase).filter(entity -> canHit((EntityLivingBase) entity, getMc().thePlayer, false)).forEach(potentialTarget -> {
                        if (closeEntitys.size() < maxTargets.getValue()) {
                            closeEntitys.add((EntityLivingBase) potentialTarget);
                        }
                    });
                    if (switchTimer.sleep(switchSpeed.getValue()) && !closeEntitys.isEmpty()) {
                        if (switchI + 1 > closeEntitys.size() - 1 || closeEntitys.size() < 2) {
                            switchI = 0;
                        } else {
                            switchI++;
                        }
                    }
                    if (!closeEntitys.isEmpty()) target = closeEntitys.get(Math.min(switchI, closeEntitys.size() - 1));
                    
                    if (target != null) {
                        if (!canHit(target, getMc().thePlayer, false)) {
                        	target = null;
                        }
                    }
                    if (target != null && getMc().thePlayer != null) {
                        final float[] rots = getRotationsToEnt(target, getMc().thePlayer);
                        event.setYaw(rots[0]);
                        event.setPitch(rots[1]);
                        if (timerUtil.sleep(1000 / getCPS())) {
                        	attackEntity(target, false);
                        }
                    }else {
                        timerUtil.reset();
                    }
                }
                break;
            case SMOOTH:
                target = getTarget();
                if (event.isPre()) {
                    if (target != null) {
                        final float[] dstAngle = getRotationsToEnt(target, getMc().thePlayer);
                        final float[] srcAngle = new float[]{serverAngles[0], serverAngles[1]};
                        serverAngles = smoothAngle(dstAngle, srcAngle, smooth.getValue());
                        
                    	event.setYaw(serverAngles[0]);
                    	event.setPitch(serverAngles[1]);
                        if (getDistance(prevRotations) < 16) {
                        	if (timerUtil.sleep(1000 / getCPS())) {
                        		attackEntity(target, false);
                        	}
                        }
                    }else {
                        serverAngles[0] = (getMc().thePlayer.rotationYaw);
                        serverAngles[1] = (getMc().thePlayer.rotationPitch);
                        timerUtil.reset();
                    }
                }
                break;
            case AAC:
            	target = getTarget();
                if (event.isPre()) {
                    if (target != null) {
                        final float[] rots = getRotationsToEnt(target, getMc().thePlayer);
                        float sens = CombatUtil.getSensitivityMultiplier();
                        float yawRna = rots[0] + MathUtils.getRandomInRange(-0.23F, 0.235F);
                        float pitchRna = rots[1] + MathUtils.getRandomInRange(-0.23F, 0.235F);
                        float yawFilter = (Math.round(yawRna / sens) * sens);
                        float pitchFilter = (Math.round(pitchRna / sens) * sens);
                        if (Math.abs(pitchFilter) == Math.round(Math.abs(pitchFilter))) {
                        	pitchFilter += (Math.round(Math.random() * 2 / sens) * sens);
                        }
                        if (Math.abs(pitchFilter) > 90) {
                        	pitchFilter = 90;
                        }
                    	event.setYaw(yawFilter);
                    	event.setPitch(pitchFilter);
                    	if (timerUtil.sleep(1000 / getCPS())) { 
                    		attackEntity(target, false);
                    	}
                    }
                }
            	break;
		default:
			break;
        }
    }

    @Handler
    public void onRender2D(Render2DEvent event) {
    	if(target == null || target.isDead) {
        	health = 0;
        	return;
    	}
    	
        y = (event.getScaledResolution().getScaledHeight() >> 1) + 4.5f;
        x = (event.getScaledResolution().getScaledWidth() >> 1) + 4.5f;
        
	    if (Clarinet.INSTANCE.getModuleManager().getModuleClass(TargetHUD.class).isEnabled()) {
	        if (getMc().thePlayer != null && target instanceof EntityPlayer) {
	            NetworkPlayerInfo networkPlayerInfo = getMc().getNetHandler().getPlayerInfo(target.getUniqueID());
	            final String ping = "Ping: " + (Objects.isNull(networkPlayerInfo) ? "0ms" : networkPlayerInfo.getResponseTime() + "ms");
	            final String playerName = "Name: " + net.minecraft.util.StringUtils.stripControlCodes(target.getName());
	            RenderUtil.drawRoundedRect(x, y, 140, 45, 7, new Color(0, 0, 0, 90).getRGB());
	            RenderUtil.drawRect(x, y, 45, 45, new Color(0, 0, 0).getRGB());
	            otherfont.drawStringWithShadow(playerName, x + 46.5, y + 4, -1);
	            GlStateManager.pushMatrix();
	            drawFace(x + 0.5, y + 0.5, 8, 8, 8, 8, 44, 44, 64, 64, (AbstractClientPlayer) target);
	            GlStateManager.popMatrix();
	            double inc = 91 / target.getMaxHealth();
	            double end = inc * (Math.min(target.getHealth(), target.getMaxHealth()));
	            if(health < end) {
	            	health += 1.2;
	            }
	            if(health > end) {
	            	health -= 1.2;
	            }
	            RenderUtil.drawRoundedRect(x + 46.5, y + 35.5, health, 7, 0, getHealthColor(target));
	            mc.fontRendererObj.drawString("" + MathUtils.round((Float.isNaN(target.getHealth()) ? 20 : target.getHealth()) / 2, 2), (int) 45 + (x), 15 + y, getHealthColor(target));
		    }
        }
    }

    @Handler
    public void onRender3D(Render3DEvent event) {
        
    }

    @Handler
    public void onPacket(PacketEvent event) {
        final Criticals criticals = (Criticals) Clarinet.INSTANCE.getModuleManager().getModule("criticals");
        if (event.isSending() && (event.getPacket() instanceof C03PacketPlayer)) {
            if (groundTicks) {
                event.setCanceled(true);
                groundTicks = false;
            }
        }
        if (event.isSending() && event.getPacket() instanceof C03PacketPlayer) {
            prevRotations[0] = ((C03PacketPlayer) event.getPacket()).getYaw();
            prevRotations[1] = ((C03PacketPlayer) event.getPacket()).getPitch();
        }
        if(event.getPacket() instanceof C05PacketPlayerLook) {
    		C05PacketPlayerLook ajsdijsod = (C05PacketPlayerLook)event.getPacket();
    		ajsdijsod.setPitch(getRotationsToEnt(target, mc.thePlayer)[1]);    
    		ajsdijsod.setYaw(getRotationsToEnt(target, mc.thePlayer)[0]);    
    	}
        if (event.isSending() && (event.getPacket() instanceof C0APacketAnimation)) {
            if (criticals.isEnabled() && target != null)
                crit();
        }
    }
    
    private List<EntityLivingBase> getMultiTargets() {
        final List<EntityLivingBase> entities = new ArrayList<>();
        int targets = 0;
        for (Entity entity : getMc().theWorld.getLoadedEntityList()) {
            if (targets >= multitargets.getValue()) {
                break;
            }
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase living = (EntityLivingBase) entity;
                if (canHit(living, getMc().thePlayer, false) && isWithinFOV(living, oldYaw, multifov.getValue())) {
                    entities.add(living);
                    ++targets;
                }
            }
        }
        return entities;
    }

    private EntityLivingBase findMostCrowdedEntity() {
        List<EntityLivingBase> entities = new ArrayList();
        for (Entity entity : getMc().theWorld.getLoadedEntityList()) {
            if (entity instanceof EntityLivingBase) {
                entities.add((EntityLivingBase) entity);
            }
        }
        EntityLivingBase best = null;
        int numBestEntities = -1;
        for (EntityLivingBase e : entities) {
            if (canHit(e, getMc().thePlayer, false)) {
                int closeEntities = 0;
                final float yaw = getRotationsToEnt(e, getMc().thePlayer)[0];
                for (EntityLivingBase e1 : entities) {
                    if (canHit(e1, getMc().thePlayer, false) && isWithinFOV(e1, yaw, multifov.getValue())) {
                        ++closeEntities;
                    }
                }
                if (closeEntities > numBestEntities) {
                    numBestEntities = closeEntities;
                    best = e;
                }
            }
        }
        return best;
    }
    
    private float[] getRotations(EntityLivingBase e, EntityPlayerSP p, int speed) {
        final float[] dstAngle = getRotationsToEnt(e, p);
        final float[] srcAngle = new float[]{serverAngles[0], serverAngles[1]};
        serverAngles = smoothAngle(dstAngle, srcAngle, speed);
    	return new float[]{serverAngles[0], serverAngles[1]};
    }

    private boolean isWithinFOV(EntityLivingBase entity, final float yaw, final double fov) {
        final float[] rotations = getRotationsToEnt(entity, getMc().thePlayer);
        final float yawDifference = getYawDifference(yaw % 360.0f, rotations[0]);
        return yawDifference < fov && yawDifference > -fov;
    }

    private float getYawDifference(final float currentYaw, final float neededYaw) {
        float yawDifference = neededYaw - currentYaw;
        if (yawDifference > 180.0f) {
            yawDifference = -(360.0f - neededYaw + currentYaw);
        } else if (yawDifference < -180.0f) {
            yawDifference = 360.0f - currentYaw + neededYaw;
        }
        return yawDifference;
    }


    public boolean isTeammate(EntityPlayer target) {
        if (!teams.isEnabled()) return false;
        boolean teamChecks = false;
        EnumChatFormatting myCol = null;
        EnumChatFormatting enemyCol = null;
        if (target != null) {
            for (EnumChatFormatting col : EnumChatFormatting.values()) {
                if (col == EnumChatFormatting.RESET)
                    continue;
                if (getMc().thePlayer.getDisplayName().getFormattedText().contains(col.toString()) && myCol == null) {
                    myCol = col;
                }
                if (target.getDisplayName().getFormattedText().contains(col.toString()) && enemyCol == null) {
                    enemyCol = col;
                }
            }
            try {
                if (myCol != null && enemyCol != null) {
                    teamChecks = myCol != enemyCol;
                } else {
                    if (getMc().thePlayer.getTeam() != null) {
                        teamChecks = !getMc().thePlayer.isOnSameTeam(target);
                    } else {
                        if (getMc().thePlayer.inventory.armorInventory[3].getItem() instanceof ItemBlock) {
                            teamChecks = !ItemStack.areItemStacksEqual(getMc().thePlayer.inventory.armorInventory[3], target.inventory.armorInventory[3]);
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }
        return teamChecks;
    }

    private void drawFace(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer target) {
        try {
            ResourceLocation skin = target.getLocationSkin();
            getMc().getTextureManager().bindTexture(skin);
            GL11.glEnable(GL11.GL_BLEND);

            double x1 = x + width;
            double y1 = y + height;

            x1 *= 2;
            y1 *= 2;
            
            GL11.glColor4f(1, 1, 1, 1);
            
            Gui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
            GL11.glDisable(GL11.GL_BLEND);
        } catch (Exception ignored) {
        }
    }
    private int getHealthColor(EntityLivingBase player) {
        float f = player.getHealth();
        float f1 = player.getMaxHealth();
        float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
        return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 0.75F) | 0xFF000000;
    }
    public static int getMaxMinColor(float currently, float max) {
        float f = currently;
        float f1 = max;
        float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
        return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 0.75F) | 0xFF000000;
    }
    private List<EntityLivingBase> loadedLivingLowTicks() {
        List<EntityLivingBase> toreturn = new ArrayList();
        for (Entity entity : getMc().theWorld.getLoadedEntityList()) {
            if (entity instanceof EntityLivingBase) {
                toreturn.add((EntityLivingBase) entity);
            }
        }
        toreturn.sort(Comparator.comparingInt(e -> e.auraticks));
        return toreturn;
    }

    private boolean nearbyTargets(boolean block) {
        for (Object e : getMc().theWorld.loadedEntityList) {
            if (e instanceof EntityLivingBase && canHit((EntityLivingBase) e, getMc().thePlayer, block)) {
                return true;
            }
        }
        return false;
    }

    private boolean canBlock() {
        return autoblock.isEnabled() && getMc().thePlayer.getHeldItem() != null && getMc().thePlayer.getHeldItem().getItem() instanceof ItemSword;
    }


    private void crit() {
        final float[] cuscusKindSus = {0.0624f, 0.0f, 1.13E-4F, 0.0f};
        final double[] NoHatAnyMore = {0.062f + 1.0E-5F, 0.001f + 1.0E-5F, 0.062f + 1.0E-5F, 0.051f};
        final Criticals criticals = (Criticals) Clarinet.INSTANCE.getModuleManager().getModule("criticals");
        if (!(MathUtils.getBlockUnderPlayer(getMc().thePlayer, 0.06) instanceof BlockStairs) && canCrit() && !(MathUtils.getBlockUnderPlayer(getMc().thePlayer, 0.06) instanceof BlockSlab)) {
        
            if (canCrit() && target.hurtResistantTime <= 13) {
                for (double offset : cuscusKindSus) {
                    getMc().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(getMc().thePlayer.posX, getMc().thePlayer.posY + offset, getMc().thePlayer.posZ, false));
                }
            }
            
        }
    }

    private float[] smoothAngle(float[] dst, float[] src, int smooth) {
        float[] smoothedAngle = new float[2];
        smoothedAngle[0] = (src[0] - dst[0]);
        smoothedAngle[1] = (src[1] - dst[1]);
        smoothedAngle = MathUtils.constrainAngle(smoothedAngle);
        smoothedAngle[0] = (src[0] - smoothedAngle[0] / smooth * 20);
        smoothedAngle[1] = (src[1] - smoothedAngle[1] / smooth * 20);
        return smoothedAngle;
    }

    private float getDistance(float[] original) {
        final float yaw = MathHelper.wrapAngleTo180_float(serverAngles[0]) - MathHelper.wrapAngleTo180_float(original[0]);
        final float pitch = MathHelper.wrapAngleTo180_float(serverAngles[1]) - MathHelper.wrapAngleTo180_float(original[1]);
        return (float) Math.sqrt(yaw * yaw + pitch * pitch);
    }
    private void attackEntity(Entity entity, boolean dura) {
        if (canBlock()) {
        	if(!(getMc().playerController.syncItem())) {
        		getMc().playerController.syncCurrentPlayItem();
        	}
        }
        
        if (dura) {
            getMc().thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
            mc.playerController.attackEntity(mc.thePlayer, entity);
        } else {
            getMc().thePlayer.swingItem();
            mc.playerController.attackEntity(mc.thePlayer, entity);
        }
    }

    private boolean canCrit() {
        return getMc().thePlayer.onGround && !Clarinet.INSTANCE.getModuleManager().getModule("speed").isEnabled() && !getMc().gameSettings.keyBindJump.isKeyDown() && getMc().thePlayer.fallDistance == 0;
    }

    private int newgetCPS() {
        double range = MathUtils.getRandomInRange(cps.getLeftVal(), cps.getRightVal());
        range = 20 / range;
        if (getMc().thePlayer.ticksExisted % 3 != 0) {
            range += Math.round(MathUtils.getRandomInRange(-1.75, 1.75));
        }
        if (getMc().thePlayer.ticksExisted % 27 == 0) {
            range += MathUtils.getRandomInRange(1, 6);
        }
        range = Math.round(Math.max(range, 1));
        int result = (int) range * 50;
        return result;
    }

    private int getCPS() {
        return MathUtils.getRandomInRange(cps.getLeftVal(), cps.getRightVal());
    }

    private void swap(final int slot, final int hotbarNum) {
        getMc().playerController.windowClick(getMc().thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, getMc().thePlayer);
    }


    private double yawDist(EntityLivingBase e) {
        if (e != null) {
            final Vec3 difference = e.getPositionVector().addVector(0.0, e.getEyeHeight() / 2.0f, 0.0).subtract(getMc().thePlayer.getPositionVector().addVector(0.0, getMc().thePlayer.getEyeHeight(), 0.0));
            final double d = Math.abs(getMc().thePlayer.rotationYaw - (Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0f)) % 360.0f;
            return (d > 180.0f) ? (360.0f - d) : d;
        }
        return 0;
    }
    
    private boolean canHit(EntityLivingBase entity, EntityPlayerSP clientPlayer, boolean b) {
    	
    	if(entity == mc.thePlayer) {
    		return false;
    	}
    	
    	return entity.getUniqueID() != clientPlayer.getUniqueID() && entity.isEntityAlive() && !(entity instanceof EntityPlayer && isTeammate((EntityPlayer) entity)) && !AntiBot.getBots().contains(entity) && !Clarinet.INSTANCE.getFriendManager().isFriend(entity.getName()) && !(entity.isInvisible() && !invisibles.isEnabled()) && (clientPlayer.getDistanceToEntity(entity) <= (b ? range.getValue() : range.getValue())) && (entity instanceof EntityPlayer && players.isEnabled() || (entity instanceof EntityMob || entity instanceof EntityGolem) && monsters.isEnabled() || ((entity instanceof EntityVillager || entity instanceof EntityAnimal) && animals.isEnabled()));
    }


    private EntityLivingBase getTarget() {
        targets.clear();
        double Dist = Double.MAX_VALUE;
        if (getMc().theWorld != null) {
            for (Object object : getMc().theWorld.loadedEntityList) {
                if ((object instanceof EntityLivingBase)) {
                    EntityLivingBase e = (EntityLivingBase) object;
                    if ((getMc().thePlayer.getDistanceToEntity(e) < Dist)) {
                        if (canHit(e, getMc().thePlayer, false)) {
                            targets.add(e);
                        }
                    }
                }
            }
        }
        if (targets.isEmpty()) return null;
        
        targets.sort(Comparator.comparingDouble(target -> getMc().thePlayer.getDistanceToEntity(target)));
        
        return targets.get(0);
    }
    private boolean inside() {
        for (int x = MathHelper.floor_double(getMc().thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(getMc().thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int y = MathHelper.floor_double(getMc().thePlayer.getEntityBoundingBox().minY); y < MathHelper.floor_double(getMc().thePlayer.getEntityBoundingBox().maxY) + 1; y++) {
                for (int z = MathHelper.floor_double(getMc().thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(getMc().thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                    Block block = getMc().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if ((block != null) && (!(block instanceof BlockAir))) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(getMc().theWorld, new BlockPos(x, y, z), getMc().theWorld.getBlockState(new BlockPos(x, y, z)));
                        if ((block instanceof BlockHopper)) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if ((boundingBox != null) && (getMc().thePlayer.getEntityBoundingBox().intersectsWith(boundingBox))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private float[] getRotationsToEnt(Entity ent, EntityPlayerSP playerSP) {
    	if(ent == null) return new float[]{playerSP.rotationYaw, -playerSP.rotationPitch};
        final double differenceX = ent.posX - playerSP.posX;
        final double differenceY = (ent.posY + ent.height) - (playerSP.posY + playerSP.height);
        final double differenceZ = ent.posZ - playerSP.posZ;
        final float rotationYaw = (float) (Math.atan2(differenceZ, differenceX) * 180.0D / Math.PI) - 90.0f;
        final float rotationPitch = (float) (Math.atan2(differenceY, playerSP.getDistanceToEntity(ent)) * 180.0D / Math.PI);
        final float finishedYaw = playerSP.rotationYaw + MathHelper.wrapAngleTo180_float(rotationYaw - playerSP.rotationYaw);
        final float finishedPitch = playerSP.rotationPitch + MathHelper.wrapAngleTo180_float(rotationPitch - playerSP.rotationPitch);
        return new float[]{finishedYaw, -finishedPitch};
    }
    
    public static boolean isBot(EntityLivingBase entity) {
    	
    	if(entity == null) {
	    	if((!AntiBot.isOnTab(target)) || (target.getDisplayName().toString().contains("[NPC]"))) {
	    		return true;
	    	}
    	}
    	
    	return false;
    }
}