/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.combat;

import club.tifality.gui.notification.client.NotificationPublisher;
import club.tifality.gui.notification.client.NotificationType;
import club.tifality.gui.notification.dev.DevNotifications;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.packet.PacketSendEvent;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.Representation;
import club.tifality.utils.movement.MovementUtils;
import club.tifality.utils.timer.TimerUtil;
import java.util.Random;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.tuple.Pair;

@ModuleInfo(label="Heal", category=ModuleCategory.COMBAT)
public class Heal
extends Module {
    private final DoubleProperty percent = new DoubleProperty("Health Percent", 75.0, 1.0, 100.0, 1.0, Representation.PERCENTAGE);
    private final DoubleProperty min = new DoubleProperty("Min Delay", 75.0, 1.0, 5000.0, 5.0, Representation.MILLISECONDS);
    private final DoubleProperty max = new DoubleProperty("Max Delay", 125.0, 1.0, 5000.0, 5.0, Representation.MILLISECONDS);
    private final DoubleProperty regenSec = new DoubleProperty("Regen Sec", 4.5, 0.0, 10.0, 0.5, Representation.MILLISECONDS);
    private final Property<Boolean> groundCheck = new Property<Boolean>("Ground Check", true);
    private final Property<Boolean> voidCheck = new Property<Boolean>("Void Check", true);
    private final Property<Boolean> waitRegen = new Property<Boolean>("Wai tRegen", true);
    private final Property<Boolean> invCheck = new Property<Boolean>("Inv Check", true);
    private final Property<Boolean> absorpCheck = new Property<Boolean>("Absorp Check", true);
    final TimerUtil timer = new TimerUtil();
    int delay;
    boolean isDisable;

    @Override
    public void onEnable() {
        this.timer.reset();
        this.isDisable = false;
        this.delay = MathHelper.getRandomIntegerInRange(new Random(), ((Double)this.min.get()).intValue(), ((Double)this.max.get()).intValue());
    }

    @Listener
    public void onPacket(PacketSendEvent e) {
        if (e.getPacket() instanceof S02PacketChat && ((S02PacketChat)e.getPacket()).getChatComponent().getFormattedText().contains("won the game! ")) {
            NotificationPublisher.queue("Heal", "Auto Healed", NotificationType.INFO, 2000);
            DevNotifications.getManager().post("Temp Disable Heal");
            this.isDisable = true;
        }
    }

    @Listener
    public void onUpdate(UpdatePositionEvent event) {
        if (Heal.mc.thePlayer.ticksExisted <= 5 && this.isDisable) {
            this.isDisable = false;
            NotificationPublisher.queue("Heal", "Enable Heal due to World Changed or Player Respawned.", NotificationType.INFO, 3000);
            DevNotifications.getManager().post("Enable Heal due to World Changed or Player Respawned");
        }
        int absorp = MathHelper.ceiling_double_int(Heal.mc.thePlayer.getAbsorptionAmount());
        if (this.groundCheck.get() != false && !Heal.mc.thePlayer.onGround || this.voidCheck.get() != false && !MovementUtils.isBlockUnder() || this.invCheck.get() != false && Heal.mc.currentScreen instanceof GuiContainer || absorp != 0 && this.absorpCheck.get().booleanValue()) {
            return;
        }
        if (this.waitRegen.get().booleanValue() && Heal.mc.thePlayer.isPotionActive(Potion.regeneration) && (double)Heal.mc.thePlayer.getActivePotionEffect(Potion.regeneration).getDuration() > (Double)this.regenSec.get() * 20.0) {
            return;
        }
        Pair<Integer, ItemStack> pair = this.getGAppleSlot();
        if (!this.isDisable && pair != null && ((double)Heal.mc.thePlayer.getHealth() <= (Double)this.percent.get() / 100.0 * (double)Heal.mc.thePlayer.getMaxHealth() || !Heal.mc.thePlayer.isPotionActive(Potion.absorption) || absorp == 0 && Heal.mc.thePlayer.getHealth() == 20.0f && Heal.mc.thePlayer.isPotionActive(Potion.absorption)) && this.timer.hasElapsed(this.delay)) {
            NotificationPublisher.queue("Heal", "Auto Healed", NotificationType.INFO, 2000);
            DevNotifications.getManager().post("Auto Healed");
            int lastSlot = Heal.mc.thePlayer.inventory.currentItem;
            int slot = pair.getLeft();
            mc.getNetHandler().sendPacket(new C09PacketHeldItemChange(slot));
            ItemStack stack = pair.getRight();
            mc.getNetHandler().sendPacket(new C08PacketPlayerBlockPlacement(stack));
            for (int i = 0; i < 32; ++i) {
                mc.getNetHandler().sendPacket(new C03PacketPlayer());
            }
            mc.getNetHandler().sendPacket(new C09PacketHeldItemChange(lastSlot));
            Heal.mc.thePlayer.inventory.currentItem = lastSlot;
            Heal.mc.playerController.updateController();
            this.delay = MathHelper.getRandomIntegerInRange(new Random(), ((Double)this.min.get()).intValue(), ((Double)this.max.get()).intValue());
            this.timer.reset();
        }
    }

    private Pair<Integer, ItemStack> getGAppleSlot() {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = Heal.mc.thePlayer.inventory.getStackInSlot(i);
            if (stack == null || stack.getItem() != Items.golden_apple) continue;
            return Pair.of(i, stack);
        }
        return null;
    }

    public String getTag() {
        return String.format("%.2f HP", (Double)this.percent.get() / 100.0 * (double)Heal.mc.thePlayer.getMaxHealth());
    }
}

