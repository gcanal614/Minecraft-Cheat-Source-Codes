package net.minecraft.entity;

import java.util.Collection;
import java.util.Iterator;
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
   public static final IAttribute attackDamage = new RangedAttribute((IAttribute)null, "generic.attackDamage", 2.0D, 0.0D, Double.MAX_VALUE);
   public static final IAttribute maxHealth = (new RangedAttribute((IAttribute)null, "generic.maxHealth", 20.0D, 0.0D, Double.MAX_VALUE)).setDescription("Max Health").setShouldWatch(true);
   private static final String __OBFID = "CL_00001695";
   public static final IAttribute knockbackResistance = (new RangedAttribute((IAttribute)null, "generic.knockbackResistance", 0.0D, 0.0D, 1.0D)).setDescription("Knockback Resistance");
   public static final IAttribute followRange = (new RangedAttribute((IAttribute)null, "generic.followRange", 32.0D, 0.0D, 2048.0D)).setDescription("Follow Range");
   private static final Logger logger = LogManager.getLogger();
   public static final IAttribute movementSpeed = (new RangedAttribute((IAttribute)null, "generic.movementSpeed", 0.699999988079071D, 0.0D, Double.MAX_VALUE)).setDescription("Movement Speed").setShouldWatch(true);

   private static NBTTagCompound writeAttributeInstanceToNBT(IAttributeInstance var0) {
      NBTTagCompound var1 = new NBTTagCompound();
      IAttribute var2 = var0.getAttribute();
      var1.setString("Name", var2.getAttributeUnlocalizedName());
      var1.setDouble("Base", var0.getBaseValue());
      Collection var3 = var0.func_111122_c();
      if (var3 != null && !var3.isEmpty()) {
         NBTTagList var4 = new NBTTagList();
         Iterator var5 = var3.iterator();

         while(var5.hasNext()) {
            AttributeModifier var6 = (AttributeModifier)var5.next();
            if (var6.isSaved()) {
               var4.appendTag(writeAttributeModifierToNBT(var6));
            }
         }

         var1.setTag("Modifiers", var4);
      }

      return var1;
   }

   private static void applyModifiersToAttributeInstance(IAttributeInstance var0, NBTTagCompound var1) {
      var0.setBaseValue(var1.getDouble("Base"));
      if (var1.hasKey("Modifiers", 9)) {
         NBTTagList var2 = var1.getTagList("Modifiers", 10);

         for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            AttributeModifier var4 = readAttributeModifierFromNBT(var2.getCompoundTagAt(var3));
            if (var4 != null) {
               AttributeModifier var5 = var0.getModifier(var4.getID());
               if (var5 != null) {
                  var0.removeModifier(var5);
               }

               var0.applyModifier(var4);
            }
         }
      }

   }

   private static NBTTagCompound writeAttributeModifierToNBT(AttributeModifier var0) {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.setString("Name", var0.getName());
      var1.setDouble("Amount", var0.getAmount());
      var1.setInteger("Operation", var0.getOperation());
      var1.setLong("UUIDMost", var0.getID().getMostSignificantBits());
      var1.setLong("UUIDLeast", var0.getID().getLeastSignificantBits());
      return var1;
   }

   public static void func_151475_a(BaseAttributeMap var0, NBTTagList var1) {
      for(int var2 = 0; var2 < var1.tagCount(); ++var2) {
         NBTTagCompound var3 = var1.getCompoundTagAt(var2);
         IAttributeInstance var4 = var0.getAttributeInstanceByName(var3.getString("Name"));
         if (var4 != null) {
            applyModifiersToAttributeInstance(var4, var3);
         } else {
            logger.warn(String.valueOf((new StringBuilder("Ignoring unknown attribute '")).append(var3.getString("Name")).append("'")));
         }
      }

   }

   public static AttributeModifier readAttributeModifierFromNBT(NBTTagCompound var0) {
      UUID var1 = new UUID(var0.getLong("UUIDMost"), var0.getLong("UUIDLeast"));

      try {
         return new AttributeModifier(var1, var0.getString("Name"), var0.getDouble("Amount"), var0.getInteger("Operation"));
      } catch (Exception var3) {
         logger.warn(String.valueOf((new StringBuilder("Unable to create attribute: ")).append(var3.getMessage())));
         return null;
      }
   }

   public static NBTTagList writeBaseAttributeMapToNBT(BaseAttributeMap var0) {
      NBTTagList var1 = new NBTTagList();
      Iterator var2 = var0.getAllAttributes().iterator();

      while(var2.hasNext()) {
         IAttributeInstance var3 = (IAttributeInstance)var2.next();
         var1.appendTag(writeAttributeInstanceToNBT(var3));
      }

      return var1;
   }
}
