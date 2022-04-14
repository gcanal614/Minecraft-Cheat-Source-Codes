package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CombatTracker {
   private final EntityLivingBase fighter;
   private String field_94551_f;
   private int field_94555_c;
   private static final String __OBFID = "CL_00001520";
   private boolean field_94553_e;
   private int field_152775_d;
   private int field_152776_e;
   private boolean field_94552_d;
   private final List combatEntries = Lists.newArrayList();

   public void func_94549_h() {
      int var1 = this.field_94552_d ? 300 : 100;
      if (this.field_94553_e && (!this.fighter.isEntityAlive() || this.fighter.ticksExisted - this.field_94555_c > var1)) {
         boolean var2 = this.field_94552_d;
         this.field_94553_e = false;
         this.field_94552_d = false;
         this.field_152776_e = this.fighter.ticksExisted;
         if (var2) {
            this.fighter.func_152112_bu();
         }

         this.combatEntries.clear();
      }

   }

   public int func_180134_f() {
      return this.field_94552_d ? this.fighter.ticksExisted - this.field_152775_d : this.field_152776_e - this.field_152775_d;
   }

   private CombatEntry func_94544_f() {
      CombatEntry var1 = null;
      CombatEntry var2 = null;
      byte var3 = 0;
      float var4 = 0.0F;

      for(int var5 = 0; var5 < this.combatEntries.size(); ++var5) {
         CombatEntry var6 = (CombatEntry)this.combatEntries.get(var5);
         CombatEntry var7 = var5 > 0 ? (CombatEntry)this.combatEntries.get(var5 - 1) : null;
         if ((var6.getDamageSrc() == DamageSource.fall || var6.getDamageSrc() == DamageSource.outOfWorld) && var6.func_94561_i() > 0.0F && (var1 == null || var6.func_94561_i() > var4)) {
            if (var5 > 0) {
               var1 = var7;
            } else {
               var1 = var6;
            }

            var4 = var6.func_94561_i();
         }

         if (var6.func_94562_g() != null && (var2 == null || var6.func_94563_c() > (float)var3)) {
            var2 = var6;
         }
      }

      if (var4 > 5.0F && var1 != null) {
         return var1;
      } else if (var3 > 5 && var2 != null) {
         return var2;
      } else {
         return null;
      }
   }

   public IChatComponent func_151521_b() {
      if (this.combatEntries.size() == 0) {
         return new ChatComponentTranslation("death.attack.generic", new Object[]{this.fighter.getDisplayName()});
      } else {
         CombatEntry var1 = this.func_94544_f();
         CombatEntry var2 = (CombatEntry)this.combatEntries.get(this.combatEntries.size() - 1);
         IChatComponent var3 = var2.func_151522_h();
         Entity var4 = var2.getDamageSrc().getEntity();
         Object var5;
         if (var1 != null && var2.getDamageSrc() == DamageSource.fall) {
            IChatComponent var6 = var1.func_151522_h();
            if (var1.getDamageSrc() != DamageSource.fall && var1.getDamageSrc() != DamageSource.outOfWorld) {
               if (var6 != null && (var3 == null || !var6.equals(var3))) {
                  Entity var9 = var1.getDamageSrc().getEntity();
                  ItemStack var8 = var9 instanceof EntityLivingBase ? ((EntityLivingBase)var9).getHeldItem() : null;
                  if (var8 != null && var8.hasDisplayName()) {
                     var5 = new ChatComponentTranslation("death.fell.assist.item", new Object[]{this.fighter.getDisplayName(), var6, var8.getChatComponent()});
                  } else {
                     var5 = new ChatComponentTranslation("death.fell.assist", new Object[]{this.fighter.getDisplayName(), var6});
                  }
               } else if (var3 != null) {
                  ItemStack var7 = var4 instanceof EntityLivingBase ? ((EntityLivingBase)var4).getHeldItem() : null;
                  if (var7 != null && var7.hasDisplayName()) {
                     var5 = new ChatComponentTranslation("death.fell.finish.item", new Object[]{this.fighter.getDisplayName(), var3, var7.getChatComponent()});
                  } else {
                     var5 = new ChatComponentTranslation("death.fell.finish", new Object[]{this.fighter.getDisplayName(), var3});
                  }
               } else {
                  var5 = new ChatComponentTranslation("death.fell.killer", new Object[]{this.fighter.getDisplayName()});
               }
            } else {
               var5 = new ChatComponentTranslation(String.valueOf((new StringBuilder("death.fell.accident.")).append(this.func_94548_b(var1))), new Object[]{this.fighter.getDisplayName()});
            }
         } else {
            var5 = var2.getDamageSrc().getDeathMessage(this.fighter);
         }

         return (IChatComponent)var5;
      }
   }

   public CombatTracker(EntityLivingBase var1) {
      this.fighter = var1;
   }

   public void func_94547_a(DamageSource var1, float var2, float var3) {
      this.func_94549_h();
      this.func_94545_a();
      CombatEntry var4 = new CombatEntry(var1, this.fighter.ticksExisted, var2, var3, this.field_94551_f, this.fighter.fallDistance);
      this.combatEntries.add(var4);
      this.field_94555_c = this.fighter.ticksExisted;
      this.field_94553_e = true;
      if (var4.func_94559_f() && !this.field_94552_d && this.fighter.isEntityAlive()) {
         this.field_94552_d = true;
         this.field_152775_d = this.fighter.ticksExisted;
         this.field_152776_e = this.field_152775_d;
         this.fighter.func_152111_bt();
      }

   }

   public void func_94545_a() {
      this.func_94542_g();
      if (this.fighter.isOnLadder()) {
         Block var1 = this.fighter.worldObj.getBlockState(new BlockPos(this.fighter.posX, this.fighter.getEntityBoundingBox().minY, this.fighter.posZ)).getBlock();
         if (var1 == Blocks.ladder) {
            this.field_94551_f = "ladder";
         } else if (var1 == Blocks.vine) {
            this.field_94551_f = "vines";
         }
      } else if (this.fighter.isInWater()) {
         this.field_94551_f = "water";
      }

   }

   public EntityLivingBase func_180135_h() {
      return this.fighter;
   }

   private String func_94548_b(CombatEntry var1) {
      return var1.func_94562_g() == null ? "generic" : var1.func_94562_g();
   }

   private void func_94542_g() {
      this.field_94551_f = null;
   }

   public EntityLivingBase func_94550_c() {
      EntityLivingBase var1 = null;
      EntityPlayer var2 = null;
      float var3 = 0.0F;
      float var4 = 0.0F;
      Iterator var5 = this.combatEntries.iterator();

      while(true) {
         CombatEntry var6;
         do {
            do {
               if (!var5.hasNext()) {
                  if (var2 != null && var4 >= var3 / 3.0F) {
                     return var2;
                  }

                  return var1;
               }

               var6 = (CombatEntry)var5.next();
               if (var6.getDamageSrc().getEntity() instanceof EntityPlayer && (var2 == null || var6.func_94563_c() > var4)) {
                  var4 = var6.func_94563_c();
                  var2 = (EntityPlayer)var6.getDamageSrc().getEntity();
               }
            } while(!(var6.getDamageSrc().getEntity() instanceof EntityLivingBase));
         } while(var1 != null && !(var6.func_94563_c() > var3));

         var3 = var6.func_94563_c();
         var1 = (EntityLivingBase)var6.getDamageSrc().getEntity();
      }
   }
}
