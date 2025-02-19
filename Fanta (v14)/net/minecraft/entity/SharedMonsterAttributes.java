/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity;

import java.util.Collection;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SharedMonsterAttributes {
    private static final Logger logger = LogManager.getLogger();
    public static final IAttribute maxHealth = new RangedAttribute(null, "generic.maxHealth", 20.0, 0.0, 1024.0).setDescription("Max Health").setShouldWatch(true);
    public static final IAttribute followRange = new RangedAttribute(null, "generic.followRange", 32.0, 0.0, 2048.0).setDescription("Follow Range");
    public static final IAttribute knockbackResistance = new RangedAttribute(null, "generic.knockbackResistance", 0.0, 0.0, 1.0).setDescription("Knockback Resistance");
    public static final IAttribute movementSpeed = new RangedAttribute(null, "generic.movementSpeed", 0.7f, 0.0, 1024.0).setDescription("Movement Speed").setShouldWatch(true);
    public static final IAttribute attackDamage = new RangedAttribute(null, "generic.attackDamage", 2.0, 0.0, 2048.0);

    public static NBTTagList writeBaseAttributeMapToNBT(BaseAttributeMap p_111257_0_) {
        NBTTagList nbttaglist = new NBTTagList();
        for (IAttributeInstance iattributeinstance : p_111257_0_.getAllAttributes()) {
            nbttaglist.appendTag(SharedMonsterAttributes.writeAttributeInstanceToNBT(iattributeinstance));
        }
        return nbttaglist;
    }

    private static NBTTagCompound writeAttributeInstanceToNBT(IAttributeInstance p_111261_0_) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        IAttribute iattribute = p_111261_0_.getAttribute();
        nbttagcompound.setString("Name", iattribute.getAttributeUnlocalizedName());
        nbttagcompound.setDouble("Base", p_111261_0_.getBaseValue());
        Collection<AttributeModifier> collection = p_111261_0_.func_111122_c();
        if (collection != null && !collection.isEmpty()) {
            NBTTagList nbttaglist = new NBTTagList();
            for (AttributeModifier attributemodifier : collection) {
                if (!attributemodifier.isSaved()) continue;
                nbttaglist.appendTag(SharedMonsterAttributes.writeAttributeModifierToNBT(attributemodifier));
            }
            nbttagcompound.setTag("Modifiers", nbttaglist);
        }
        return nbttagcompound;
    }

    private static NBTTagCompound writeAttributeModifierToNBT(AttributeModifier p_111262_0_) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setString("Name", p_111262_0_.getName());
        nbttagcompound.setDouble("Amount", p_111262_0_.getAmount());
        nbttagcompound.setInteger("Operation", p_111262_0_.getOperation());
        nbttagcompound.setLong("UUIDMost", p_111262_0_.getID().getMostSignificantBits());
        nbttagcompound.setLong("UUIDLeast", p_111262_0_.getID().getLeastSignificantBits());
        return nbttagcompound;
    }

    public static void func_151475_a(BaseAttributeMap p_151475_0_, NBTTagList p_151475_1_) {
        int i = 0;
        while (i < p_151475_1_.tagCount()) {
            NBTTagCompound nbttagcompound = p_151475_1_.getCompoundTagAt(i);
            IAttributeInstance iattributeinstance = p_151475_0_.getAttributeInstanceByName(nbttagcompound.getString("Name"));
            if (iattributeinstance != null) {
                SharedMonsterAttributes.applyModifiersToAttributeInstance(iattributeinstance, nbttagcompound);
            } else {
                logger.warn("Ignoring unknown attribute '" + nbttagcompound.getString("Name") + "'");
            }
            ++i;
        }
    }

    private static void applyModifiersToAttributeInstance(IAttributeInstance p_111258_0_, NBTTagCompound p_111258_1_) {
        p_111258_0_.setBaseValue(p_111258_1_.getDouble("Base"));
        if (p_111258_1_.hasKey("Modifiers", 9)) {
            NBTTagList nbttaglist = p_111258_1_.getTagList("Modifiers", 10);
            int i = 0;
            while (i < nbttaglist.tagCount()) {
                AttributeModifier attributemodifier = SharedMonsterAttributes.readAttributeModifierFromNBT(nbttaglist.getCompoundTagAt(i));
                if (attributemodifier != null) {
                    AttributeModifier attributemodifier1 = p_111258_0_.getModifier(attributemodifier.getID());
                    if (attributemodifier1 != null) {
                        p_111258_0_.removeModifier(attributemodifier1);
                    }
                    p_111258_0_.applyModifier(attributemodifier);
                }
                ++i;
            }
        }
    }

    public static AttributeModifier readAttributeModifierFromNBT(NBTTagCompound p_111259_0_) {
        UUID uuid = new UUID(p_111259_0_.getLong("UUIDMost"), p_111259_0_.getLong("UUIDLeast"));
        try {
            return new AttributeModifier(uuid, p_111259_0_.getString("Name"), p_111259_0_.getDouble("Amount"), p_111259_0_.getInteger("Operation"));
        }
        catch (Exception exception) {
            logger.warn("Unable to create attribute: " + exception.getMessage());
            return null;
        }
    }
}

