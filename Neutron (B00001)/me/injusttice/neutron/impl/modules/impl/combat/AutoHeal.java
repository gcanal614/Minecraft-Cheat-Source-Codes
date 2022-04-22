package me.injusttice.neutron.impl.modules.impl.combat;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.network.PacketUtil;
import me.injusttice.neutron.utils.player.TimerHelper;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.EnumChatFormatting;

public class AutoHeal extends Module {

    public ModeSet mode = new ModeSet("Mode", "Packet Gapple", new String[] { "Packet Gapple", "Golden Head" });
    public DoubleSet healthToHeal = new DoubleSet("Health", 15.0D, 2.0D, 20.0D, 1.0D);
    public DoubleSet timeToHealAgain = new DoubleSet("Delay", 500.0D, 250.0D, 3000.0D, 10.0D, "ms");

    TimerHelper timer;

    public AutoHeal() {
        super("AutoHeal", 0, Category.COMBAT);
        this.timer = new TimerHelper();
        addSettings(mode, healthToHeal, timeToHealAgain);
    }

    @EventTarget
    public void onPre(EventMotion e) {
        setDisplayName("Auto Heal §7" + String.valueOf(healthToHeal.getValue()));
        if (!canHeal())
            return;
        if (shouldHeal()) {
            int a;
            switch (this.mode.getMode()) {
                case "Packet Gapple":
                    for (a = 0; a < 9; a++) {
                        if (this.mc.thePlayer.inventory.getStackInSlot(a) != null) {
                            boolean shouldChange = (this.mc.thePlayer.inventory.getStackInSlot(a).getItem() == Items.golden_apple);
                            if (shouldChange) {
                                PacketUtil.sendPacket(new C09PacketHeldItemChange(a));
                                PacketUtil.sendPacket(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getStackInSlot(a)));
                                for (int repeatPacket = 0; repeatPacket < 35; repeatPacket++)
                                    PacketUtil.sendPacket(new C03PacketPlayer());
                                PacketUtil.sendPacket(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
                                break;
                            }
                        }
                    }
                    break;
                case "Golden Head":
                    for (a = 0; a < 9; a++) {
                        if (this.mc.thePlayer.inventory.getStackInSlot(a) != null) {
                            boolean isSafeToSpoof = EnumChatFormatting.getTextWithoutFormattingCodes(this.mc.thePlayer.inventory.getStackInSlot(a).getDisplayName()).equalsIgnoreCase("golden head");
                            if (isSafeToSpoof) {
                                PacketUtil.sendPacket(new C09PacketHeldItemChange(a));
                                PacketUtil.sendPacket(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getStackInSlot(a)));
                                PacketUtil.sendPacket(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
                            }
                        }
                    }
                    break;
            }
            this.timer.reset();
        }
    }

    private boolean shouldHeal() {
        return (this.mc.thePlayer.getHealth() < this.healthToHeal.getValue() && this.timer.timeElapsed((long)this.timeToHealAgain.getValue()));
    }

    private boolean canHeal() {
        int a;
        switch (this.mode.getMode()) {
            case "Packet Gapple":
                for (a = 0; a < 9; a++) {
                    if (this.mc.thePlayer.inventory.getStackInSlot(a) != null) {
                        boolean isSafeToSpoof = (this.mc.thePlayer.inventory.getStackInSlot(a).getItem() == Items.golden_apple);
                        if (isSafeToSpoof)
                            return true;
                    }
                }
                break;
            case "Golden Head":
                for (a = 0; a < 9; a++) {
                    if (this.mc.thePlayer.inventory.getStackInSlot(a) != null) {
                        boolean isSafeToSpoof = EnumChatFormatting.getTextWithoutFormattingCodes(this.mc.thePlayer.inventory.getStackInSlot(a).getDisplayName()).equalsIgnoreCase("golden head");
                        if (isSafeToSpoof)
                            return true;
                    }
                }
                break;
        }
        return false;
    }
}
