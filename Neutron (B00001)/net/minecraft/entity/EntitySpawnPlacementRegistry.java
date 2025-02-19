package net.minecraft.entity;

import com.google.common.collect.Maps;
import java.util.HashMap;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;

public class EntitySpawnPlacementRegistry
{
    private static final HashMap<Class, EntityLiving.SpawnPlacementType> ENTITY_PLACEMENTS = Maps.<Class, EntityLiving.SpawnPlacementType>newHashMap();

    public static EntityLiving.SpawnPlacementType getPlacementForEntity(Class entityClass)
    {
        return (EntityLiving.SpawnPlacementType)ENTITY_PLACEMENTS.get(entityClass);
    }

    static
    {
        ENTITY_PLACEMENTS.put(EntityBat.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityChicken.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityCow.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityHorse.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityMooshroom.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityOcelot.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityPig.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityRabbit.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntitySheep.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntitySnowman.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntitySquid.class, EntityLiving.SpawnPlacementType.IN_WATER);
        ENTITY_PLACEMENTS.put(EntityIronGolem.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityWolf.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityVillager.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityDragon.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityWither.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityBlaze.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityCaveSpider.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityCreeper.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityEnderman.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityEndermite.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityGhast.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityGiantZombie.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityGuardian.class, EntityLiving.SpawnPlacementType.IN_WATER);
        ENTITY_PLACEMENTS.put(EntityMagmaCube.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityPigZombie.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntitySilverfish.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntitySkeleton.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntitySlime.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntitySpider.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityWitch.class, EntityLiving.SpawnPlacementType.ON_GROUND);
        ENTITY_PLACEMENTS.put(EntityZombie.class, EntityLiving.SpawnPlacementType.ON_GROUND);
    }
}
