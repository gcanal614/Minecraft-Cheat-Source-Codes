package non.asset.module.impl.ghost;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import non.asset.Clarinet;
import non.asset.event.bus.Handler;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.module.impl.Combat.AntiBot;
import non.asset.utils.OFC.TimerUtil;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.NumberValue;

public class AimAssist extends Module {
	

    public static EntityLivingBase target;
    
    private List<EntityLivingBase> targets = new ArrayList<>();

    public static NumberValue<Float> reach = new NumberValue<>("Reach", 3F, 3.0F, 6.0F, 0.1F);
    
    public BooleanValue hitCheck = new BooleanValue("Swing Check",false);
    
    public static NumberValue<Long> motion = new NumberValue<>("Delay", 100L, 1L, 100L, 1L);
    
    public BooleanValue team = new BooleanValue("Team",false);
    
    private TimerUtil timer = new TimerUtil();
    
    public AimAssist() {
        super("AimAssist", Category.GHOST);
        setRenderLabel("AimAssist");
        setDescription("Auto Rotate");
    }
    
    @Override
    public void onEnable() {
        if (getMc().thePlayer == null) return;
    }

    @Override
    public void onDisable() {
        if (getMc().thePlayer == null) return;

        targets.clear();
        target = null;
        
    }
    
    @Handler
    public void onUpdate(UpdateEvent event) {
    	
    	if(!(timer.reach(motion.getValue())))
    		return;
    	

        if (event.isPre()) {
    		target = getTarget();
    		
	    	if(target != null && target instanceof EntityPlayer && target.getHealth() > 0 && !target.isDead && !target.isInvisible() && timer.hasTimerElapsed(motion.getValue(), true)) {
	    		if(hitCheck.isEnabled()) {
	    			if(mc.thePlayer.swingProgress > 0) {
	    				getRotationsToEnt(target, mc.thePlayer);
	        			mc.thePlayer.rotationYaw = getRotationsToEnt(target, mc.thePlayer)[0];
	        			mc.thePlayer.rotationPitch = getRotationsToEnt(target, mc.thePlayer)[1];
	    			}
	    		}else {
	    			mc.thePlayer.rotationYaw = getRotationsToEnt(target, mc.thePlayer)[0];
	    			mc.thePlayer.rotationPitch = getRotationsToEnt(target, mc.thePlayer)[1];
	    		}
	    	}
        }
    	
    }
    
    public boolean isTeammate(EntityPlayer target) {
        if (!team.isEnabled()) return false;
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
    
    private boolean canHit(EntityLivingBase entity, EntityPlayerSP clientPlayer) {
    	
    	if(entity == mc.thePlayer) {
    		return false;
    	}
    	
    	return entity.getUniqueID() != clientPlayer.getUniqueID() && entity.isEntityAlive() && !(entity instanceof EntityPlayer && isTeammate((EntityPlayer) entity)) && !AntiBot.getBots().contains(entity) && !Clarinet.INSTANCE.getFriendManager().isFriend(entity.getName()) && (clientPlayer.getDistanceToEntity(entity) <= reach.getValue());
    }
    
    private double yawDist(EntityLivingBase e) {
        if (e != null) {
            final Vec3 difference = e.getPositionVector().addVector(0.0, e.getEyeHeight() / 2.0f, 0.0).subtract(getMc().thePlayer.getPositionVector().addVector(0.0, getMc().thePlayer.getEyeHeight(), 0.0));
            final double d = Math.abs(getMc().thePlayer.rotationYaw - (Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0f)) % 360.0f;
            return (d > 180.0f) ? (360.0f - d) : d;
        }
        return 0;
    }
    
    private EntityLivingBase getTarget() {
        targets.clear();
        double Dist = 4;
        if (getMc().theWorld != null) {
            for (Object object : getMc().theWorld.loadedEntityList) {
                if ((object instanceof EntityLivingBase)) {
                    EntityLivingBase e = (EntityLivingBase) object;
                    if ((getMc().thePlayer.getDistanceToEntity(e) < reach.getValue())) {
                    	if(canHit(e, mc.thePlayer)) {
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
        final double differenceX = ent.posX - playerSP.posX;
        final double differenceY = ent.posY - 3.5 + ent.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
        final double differenceZ = ent.posZ - playerSP.posZ;
        final float rotationYaw = (float) (Math.atan2(differenceZ, differenceX) * 180.0D / Math.PI) - 90.0f;
        final float rotationPitch = (float) (Math.atan2(differenceY, playerSP.getDistanceToEntity(ent)) * 180.0D / Math.PI);
        final float finishedYaw = playerSP.rotationYaw + MathHelper.wrapAngleTo180_float(rotationYaw - playerSP.rotationYaw);
        final float finishedPitch = playerSP.rotationPitch + MathHelper.wrapAngleTo180_float(rotationPitch - playerSP.rotationPitch);
        return new float[]{finishedYaw, -finishedPitch};
    }
    
}