package me.injusttice.neutron.impl.modules.impl.player;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventUpdate;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FastMine extends Module {

    public FastMine() {
        super("FastMine", 0, Category.PLAYER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.thePlayer.removePotionEffect(Potion.digSpeed.getId());
    }

    @EventTarget
    public void onEvent(EventUpdate event) {
        if(!this.isToggled()) return;
        this.setDisplayName("Fast Mine");
        if(mc.thePlayer != null && mc.theWorld != null) {
            mc.playerController.blockHitDelay = 0;
            boolean item = mc.thePlayer.getCurrentEquippedItem() == null;
            mc.thePlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 1000, item ? 3 : 0));
        }
    }
}