package me.injusttice.neutron.impl.modules.impl.combat;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.ModuleCategory;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.network.PacketUtil;
import me.injusttice.neutron.utils.player.TimerHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;

public class AutoPot extends Module {

  public ModeSet mode = new ModeSet("Mode", "Silent", new String[] { "Silent", "Real", "Tick" });
  DoubleSet healthSet = new DoubleSet("Regen HP", 18.0D, 2.0D, 20.0D, 1.0D, " hp");
  DoubleSet delaySet = new DoubleSet("Delay", 700.0D, 200.0D, 4000.0D, 5.0D, " ms");
  ModuleCategory potionsCat = new ModuleCategory("Potions...");

  public BooleanSet speedPotionSet = new BooleanSet("Speed", true);
  public BooleanSet regenPotionSet = new BooleanSet("Regen", true);
  public BooleanSet healingPotionSet = new BooleanSet("Healing", true);

  int oldSlot;

  int stage;

  TimerHelper throwAgainTimer;

  public AutoPot() {
    super("AutoPot", 0, Category.COMBAT);
    oldSlot = 0;
    stage = 0;
    throwAgainTimer = new TimerHelper();
    addSettings(mode, healthSet, delaySet, potionsCat);
    potionsCat.addCatSettings(speedPotionSet, regenPotionSet, healingPotionSet);
  }

  @EventTarget
  public void onPre(EventMotion e) {
    this.setDisplayName("Auto Pot");
    int potState = -1;
    for (int a = 0; a < 9; a++) {
      if (mc.thePlayer.inventory.getStackInSlot(a) != null)
        if (mc.thePlayer.inventory.getStackInSlot(a).getItem() instanceof net.minecraft.item.ItemPotion) {
          potState = a;
          break;
        }
    }
    if (potState == -1)
      return;
    ItemStack pot = mc.thePlayer.inventory.getStackInSlot(potState);
    if (isHealingPot(pot) && healingPotionSet.isEnabled() &&
            mc.thePlayer.getHealth() < healthSet.getValue() && throwAgainTimer.timeElapsed((long)delaySet.getValue())) {
      e.setPitch(90.0F);
      stage = 1;
      throwAgainTimer.reset();
    }
    if (isSwiftnessPot(pot) && speedPotionSet.isEnabled() &&
            !mc.thePlayer.isPotionActive(Potion.moveSpeed) && throwAgainTimer.timeElapsed((long)delaySet.getValue())) {
      e.setPitch(90.0F);
      stage = 1;
      throwAgainTimer.reset();
    }
    if (isRegenPot(pot) && regenPotionSet.isEnabled() &&
            mc.thePlayer.getHealth() < healthSet.getValue() && !mc.thePlayer.isPotionActive(Potion.regeneration) && throwAgainTimer.timeElapsed((long)delaySet.getValue())) {
      e.setPitch(90.0F);
      stage = 1;
      throwAgainTimer.reset();
    }
    if (stage >= 1)
      switch (stage) {
        case 1:
          e.setPitch(90.0F);
          if (mode.is("Real")) {
            oldSlot = mc.thePlayer.inventory.currentItem;
            mc.thePlayer.inventory.currentItem = potState;
          }
          stage++;
          break;
        case 2:
          e.setPitch(90.0F);
          if (mode.is("Silent")) {
            PacketUtil.sendPacket(new C09PacketHeldItemChange(potState));
            PacketUtil.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getStackInSlot(potState)));
            PacketUtil.sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
          }
          if (mode.is("Tick")) {
            PacketUtil.sendPacket(new C09PacketHeldItemChange(potState));
            PacketUtil.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getStackInSlot(potState)));
          }
          if (mode.is("Real"))
            PacketUtil.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getStackInSlot(potState)));
          stage++;
          break;
        case 3:
          e.setPitch(90.0F);
          if (mode.is("Tick"))
            PacketUtil.sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
          if (mode.is("Real"))
            mc.thePlayer.inventory.currentItem = oldSlot;
          stage = 0;
          break;
      }
  }

  private boolean isHealingPot(ItemStack i) {
    return EnumChatFormatting.getTextWithoutFormattingCodes(i.getDisplayName().toLowerCase()).contains("healing");
  }

  private boolean isSwiftnessPot(ItemStack i) {
    return (EnumChatFormatting.getTextWithoutFormattingCodes(i.getDisplayName().toLowerCase()).contains("swiftness") || EnumChatFormatting.getTextWithoutFormattingCodes(i.getDisplayName().toLowerCase()).contains("speed"));
  }

  private boolean isRegenPot(ItemStack i) {
    return EnumChatFormatting.getTextWithoutFormattingCodes(i.getDisplayName().toLowerCase()).contains("regeneration");
  }

  private boolean isSplash(ItemStack i) {
    return i.getDisplayName().toLowerCase().contains("splash");
  }
}
