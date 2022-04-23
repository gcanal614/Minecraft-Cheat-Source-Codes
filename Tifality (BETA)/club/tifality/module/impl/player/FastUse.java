/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.player;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.utils.Wrapper;
import club.tifality.utils.movement.MovementUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(label="FastUse", category=ModuleCategory.PLAYER)
public final class FastUse
extends Module {
    public final EnumProperty<FastUseMode> fastUseModeEnumProperty = new EnumProperty<FastUseMode>("Mode", FastUseMode.PACKET);
    private final DoubleProperty ticks = new DoubleProperty("Ticks", 14.0, () -> this.fastUseModeEnumProperty.get() == FastUseMode.PACKET, 1.0, 20.0, 1.0);

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent e) {
        Item heldItem = Wrapper.getPlayer().getCurrentEquippedItem().getItem();
        if (e.isPre()) {
            switch ((FastUseMode)((Object)this.fastUseModeEnumProperty.getValue())) {
                case PACKET: {
                    if (!Wrapper.getPlayer().isUsingItem() || !(heldItem instanceof ItemFood) && !(heldItem instanceof ItemPotion) || Wrapper.getPlayer().getItemInUseDuration() <= ((Double)this.ticks.getValue()).intValue() || !MovementUtils.isOnGround()) break;
                    for (int i = 0; i < 10; ++i) {
                        Wrapper.sendPacketDirect(new C03PacketPlayer(true));
                    }
                    break;
                }
                case INSTANT: {
                    if (!(heldItem instanceof ItemFood) && !(heldItem instanceof ItemBucketMilk) && !(heldItem instanceof ItemPotion)) break;
                    int a = 35;
                    for (int var12 = 0; var12 < a; ++var12) {
                        mc.getNetHandler().sendPacket(new C03PacketPlayer(FastUse.mc.thePlayer.onGround));
                    }
                    FastUse.mc.playerController.onStoppedUsingItem(FastUse.mc.thePlayer);
                }
            }
        }
    }

    public FastUse() {
        this.setSuffixListener(this.fastUseModeEnumProperty);
    }

    public static enum FastUseMode {
        PACKET,
        INSTANT;

    }
}

