package wtf.astronicy.IMPL.utils.entity.impl;

import wtf.astronicy.IMPL.module.options.impl.BoolOption;
import wtf.astronicy.IMPL.utils.PlayerUtils;
import wtf.astronicy.IMPL.utils.entity.ICheck;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public final class TeamsCheck implements ICheck {
   private final BoolOption teams;

   public TeamsCheck(BoolOption teams) {
      this.teams = teams;
   }

   public boolean validate(Entity entity) {
      return !(entity instanceof EntityPlayer) || !PlayerUtils.isOnSameTeam((EntityPlayer)entity) || !this.teams.getValue();
   }
}
