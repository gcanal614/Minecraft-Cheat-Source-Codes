/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityEndermite
extends EntityMob {
    private int lifetime = 0;
    private boolean playerSpawned = false;

    public EntityEndermite(World worldIn) {
        super(worldIn);
        this.experienceValue = 3;
        this.setSize(0.4f, 0.3f);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, false));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>((EntityCreature)this, EntityPlayer.class, true));
    }

    @Override
    public float getEyeHeight() {
        return 0.1f;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected String getLivingSound() {
        return "mob.silverfish.say";
    }

    @Override
    protected String getHurtSound() {
        return "mob.silverfish.hit";
    }

    @Override
    protected String getDeathSound() {
        return "mob.silverfish.kill";
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound("mob.silverfish.step", 0.15f, 1.0f);
    }

    @Override
    protected Item getDropItem() {
        return null;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        this.lifetime = tagCompund.getInteger("Lifetime");
        this.playerSpawned = tagCompund.getBoolean("PlayerSpawned");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("Lifetime", this.lifetime);
        tagCompound.setBoolean("PlayerSpawned", this.playerSpawned);
    }

    @Override
    public void onUpdate() {
        this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }

    public boolean isSpawnedByPlayer() {
        return this.playerSpawned;
    }

    public void setSpawnedByPlayer(boolean spawnedByPlayer) {
        this.playerSpawned = spawnedByPlayer;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (EntityEndermite.worldObj.isRemote) {
            int i = 0;
            while (i < 2) {
                worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (rand.nextDouble() - 0.5) * (double)this.width, this.posY + rand.nextDouble() * (double)this.height, this.posZ + (rand.nextDouble() - 0.5) * (double)this.width, (rand.nextDouble() - 0.5) * 2.0, -rand.nextDouble(), (rand.nextDouble() - 0.5) * 2.0, new int[0]);
                ++i;
            }
        } else {
            if (!this.isNoDespawnRequired()) {
                ++this.lifetime;
            }
            if (this.lifetime >= 2400) {
                this.setDead();
            }
        }
    }

    @Override
    protected boolean isValidLightLevel() {
        return true;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (super.getCanSpawnHere()) {
            EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 5.0);
            return entityplayer == null;
        }
        return false;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
}

