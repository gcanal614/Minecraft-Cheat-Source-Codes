package net.minecraft.entity;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.item.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.network.*;
import net.minecraft.world.chunk.*;
import org.apache.logging.log4j.*;

public class EntityTracker
{
    private static final Logger logger;
    private final WorldServer theWorld;
    private Set trackedEntities;
    private IntHashMap trackedEntityHashTable;
    private int maxTrackingDistanceThreshold;
    
    public EntityTracker(final WorldServer p_i1516_1_) {
        this.trackedEntities = Sets.newHashSet();
        this.trackedEntityHashTable = new IntHashMap();
        this.theWorld = p_i1516_1_;
        this.maxTrackingDistanceThreshold = p_i1516_1_.func_73046_m().getConfigurationManager().getEntityViewDistance();
    }
    
    public void trackEntity(final Entity p_72786_1_) {
        if (p_72786_1_ instanceof EntityPlayerMP) {
            this.trackEntity(p_72786_1_, 512, 2);
            final EntityPlayerMP var2 = (EntityPlayerMP)p_72786_1_;
            for (final EntityTrackerEntry var4 : this.trackedEntities) {
                if (var4.trackedEntity != var2) {
                    var4.updatePlayerEntity(var2);
                }
            }
        }
        else if (p_72786_1_ instanceof EntityFishHook) {
            this.addEntityToTracker(p_72786_1_, 64, 5, true);
        }
        else if (p_72786_1_ instanceof EntityArrow) {
            this.addEntityToTracker(p_72786_1_, 64, 20, false);
        }
        else if (p_72786_1_ instanceof EntitySmallFireball) {
            this.addEntityToTracker(p_72786_1_, 64, 10, false);
        }
        else if (p_72786_1_ instanceof EntityFireball) {
            this.addEntityToTracker(p_72786_1_, 64, 10, false);
        }
        else if (p_72786_1_ instanceof EntitySnowball) {
            this.addEntityToTracker(p_72786_1_, 64, 10, true);
        }
        else if (p_72786_1_ instanceof EntityEnderPearl) {
            this.addEntityToTracker(p_72786_1_, 64, 10, true);
        }
        else if (p_72786_1_ instanceof EntityEnderEye) {
            this.addEntityToTracker(p_72786_1_, 64, 4, true);
        }
        else if (p_72786_1_ instanceof EntityEgg) {
            this.addEntityToTracker(p_72786_1_, 64, 10, true);
        }
        else if (p_72786_1_ instanceof EntityPotion) {
            this.addEntityToTracker(p_72786_1_, 64, 10, true);
        }
        else if (p_72786_1_ instanceof EntityExpBottle) {
            this.addEntityToTracker(p_72786_1_, 64, 10, true);
        }
        else if (p_72786_1_ instanceof EntityFireworkRocket) {
            this.addEntityToTracker(p_72786_1_, 64, 10, true);
        }
        else if (p_72786_1_ instanceof EntityItem) {
            this.addEntityToTracker(p_72786_1_, 64, 20, true);
        }
        else if (p_72786_1_ instanceof EntityMinecart) {
            this.addEntityToTracker(p_72786_1_, 80, 3, true);
        }
        else if (p_72786_1_ instanceof EntityBoat) {
            this.addEntityToTracker(p_72786_1_, 80, 3, true);
        }
        else if (p_72786_1_ instanceof EntitySquid) {
            this.addEntityToTracker(p_72786_1_, 64, 3, true);
        }
        else if (p_72786_1_ instanceof EntityWither) {
            this.addEntityToTracker(p_72786_1_, 80, 3, false);
        }
        else if (p_72786_1_ instanceof EntityBat) {
            this.addEntityToTracker(p_72786_1_, 80, 3, false);
        }
        else if (p_72786_1_ instanceof EntityDragon) {
            this.addEntityToTracker(p_72786_1_, 160, 3, true);
        }
        else if (p_72786_1_ instanceof IAnimals) {
            this.addEntityToTracker(p_72786_1_, 80, 3, true);
        }
        else if (p_72786_1_ instanceof EntityTNTPrimed) {
            this.addEntityToTracker(p_72786_1_, 160, 10, true);
        }
        else if (p_72786_1_ instanceof EntityFallingBlock) {
            this.addEntityToTracker(p_72786_1_, 160, 20, true);
        }
        else if (p_72786_1_ instanceof EntityHanging) {
            this.addEntityToTracker(p_72786_1_, 160, Integer.MAX_VALUE, false);
        }
        else if (p_72786_1_ instanceof EntityArmorStand) {
            this.addEntityToTracker(p_72786_1_, 160, 3, true);
        }
        else if (p_72786_1_ instanceof EntityXPOrb) {
            this.addEntityToTracker(p_72786_1_, 160, 20, true);
        }
        else if (p_72786_1_ instanceof EntityEnderCrystal) {
            this.addEntityToTracker(p_72786_1_, 256, Integer.MAX_VALUE, false);
        }
    }
    
    public void trackEntity(final Entity p_72791_1_, final int p_72791_2_, final int p_72791_3_) {
        this.addEntityToTracker(p_72791_1_, p_72791_2_, p_72791_3_, false);
    }
    
    public void addEntityToTracker(final Entity p_72785_1_, int p_72785_2_, final int p_72785_3_, final boolean p_72785_4_) {
        if (p_72785_2_ > this.maxTrackingDistanceThreshold) {
            p_72785_2_ = this.maxTrackingDistanceThreshold;
        }
        try {
            if (this.trackedEntityHashTable.containsItem(p_72785_1_.getEntityId())) {
                throw new IllegalStateException("Entity is already tracked!");
            }
            final EntityTrackerEntry var5 = new EntityTrackerEntry(p_72785_1_, p_72785_2_, p_72785_3_, p_72785_4_);
            this.trackedEntities.add(var5);
            this.trackedEntityHashTable.addKey(p_72785_1_.getEntityId(), var5);
            var5.updatePlayerEntities(this.theWorld.playerEntities);
        }
        catch (Throwable var7) {
            final CrashReport var6 = CrashReport.makeCrashReport(var7, "Adding entity to track");
            final CrashReportCategory var8 = var6.makeCategory("Entity To Track");
            var8.addCrashSection("Tracking range", p_72785_2_ + " blocks");
            var8.addCrashSectionCallable("Update interval", new Callable() {
                @Override
                public String call() {
                    String var1 = "Once per " + p_72785_3_ + " ticks";
                    if (p_72785_3_ == Integer.MAX_VALUE) {
                        var1 = "Maximum (" + var1 + ")";
                    }
                    return var1;
                }
            });
            p_72785_1_.addEntityCrashInfo(var8);
            final CrashReportCategory var9 = var6.makeCategory("Entity That Is Already Tracked");
            ((EntityTrackerEntry)this.trackedEntityHashTable.lookup(p_72785_1_.getEntityId())).trackedEntity.addEntityCrashInfo(var9);
            try {
                throw new ReportedException(var6);
            }
            catch (ReportedException var10) {
                EntityTracker.logger.error("\"Silently\" catching entity tracking error.", (Throwable)var10);
            }
        }
    }
    
    public void untrackEntity(final Entity p_72790_1_) {
        if (p_72790_1_ instanceof EntityPlayerMP) {
            final EntityPlayerMP var2 = (EntityPlayerMP)p_72790_1_;
            for (final EntityTrackerEntry var4 : this.trackedEntities) {
                var4.removeFromTrackedPlayers(var2);
            }
        }
        final EntityTrackerEntry var5 = (EntityTrackerEntry)this.trackedEntityHashTable.removeObject(p_72790_1_.getEntityId());
        if (var5 != null) {
            this.trackedEntities.remove(var5);
            var5.sendDestroyEntityPacketToTrackedPlayers();
        }
    }
    
    public void updateTrackedEntities() {
        final ArrayList var1 = Lists.newArrayList();
        for (final EntityTrackerEntry var3 : this.trackedEntities) {
            var3.updatePlayerList(this.theWorld.playerEntities);
            if (var3.playerEntitiesUpdated && var3.trackedEntity instanceof EntityPlayerMP) {
                var1.add(var3.trackedEntity);
            }
        }
        for (int var4 = 0; var4 < var1.size(); ++var4) {
            final EntityPlayerMP var5 = var1.get(var4);
            for (final EntityTrackerEntry var7 : this.trackedEntities) {
                if (var7.trackedEntity != var5) {
                    var7.updatePlayerEntity(var5);
                }
            }
        }
    }
    
    public void func_180245_a(final EntityPlayerMP p_180245_1_) {
        for (final EntityTrackerEntry var3 : this.trackedEntities) {
            if (var3.trackedEntity == p_180245_1_) {
                var3.updatePlayerEntities(this.theWorld.playerEntities);
            }
            else {
                var3.updatePlayerEntity(p_180245_1_);
            }
        }
    }
    
    public void sendToAllTrackingEntity(final Entity p_151247_1_, final Packet p_151247_2_) {
        final EntityTrackerEntry var3 = (EntityTrackerEntry)this.trackedEntityHashTable.lookup(p_151247_1_.getEntityId());
        if (var3 != null) {
            var3.func_151259_a(p_151247_2_);
        }
    }
    
    public void func_151248_b(final Entity p_151248_1_, final Packet p_151248_2_) {
        final EntityTrackerEntry var3 = (EntityTrackerEntry)this.trackedEntityHashTable.lookup(p_151248_1_.getEntityId());
        if (var3 != null) {
            var3.func_151261_b(p_151248_2_);
        }
    }
    
    public void removePlayerFromTrackers(final EntityPlayerMP p_72787_1_) {
        for (final EntityTrackerEntry var3 : this.trackedEntities) {
            var3.removeTrackedPlayerSymmetric(p_72787_1_);
        }
    }
    
    public void func_85172_a(final EntityPlayerMP p_85172_1_, final Chunk p_85172_2_) {
        for (final EntityTrackerEntry var4 : this.trackedEntities) {
            if (var4.trackedEntity != p_85172_1_ && var4.trackedEntity.chunkCoordX == p_85172_2_.xPosition && var4.trackedEntity.chunkCoordZ == p_85172_2_.zPosition) {
                var4.updatePlayerEntity(p_85172_1_);
            }
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
