package non.asset.module.impl.player;

import java.awt.Color;
import java.util.Collection;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import non.asset.Clarinet;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.module.Module;
import non.asset.utils.value.impl.BooleanValue;

public class AutoTool extends Module {

    private BooleanValue mouseCheck = new BooleanValue("Verify",true);
    public AutoTool() {
        super("AutoTool", Category.PLAYER);
        setRenderLabel("AutoTool");
        setDescription("Selects the best tool when mining.");
    }

    @Handler
    public void onPacket(PacketEvent event) {
        if (event.isSending()) {
        	
        	int heldItem = getMc().thePlayer.inventory.currentItem;
        	
            if (event.getPacket() instanceof C07PacketPlayerDigging) {
                C07PacketPlayerDigging packetPlayerDigging = (C07PacketPlayerDigging) event.getPacket();
                if ((packetPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK)) {
                    if ((getMc().objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK || !mouseCheck.isEnabled()) && !getMc().thePlayer.capabilities.isCreativeMode) {
                        BlockPos blockPosHit = mouseCheck.isEnabled() ? getMc().objectMouseOver.getBlockPos() : packetPlayerDigging.getPosition();
                        if (blockPosHit != null || !mouseCheck.isEnabled()) {
                        	getMc().thePlayer.inventory.currentItem = getBestTool(blockPosHit);
                        	getMc().playerController.updateController();
                        }
                    }
                }
            }
        }
    }
    private int getBestTool(BlockPos pos) {
        final Block block = getMc().theWorld.getBlockState(pos).getBlock();
        int slot = 0;
        float dmg = 0.1F;
        for (int index = 36; index < 45; index++) {
            final ItemStack itemStack = getMc().thePlayer.inventoryContainer
                    .getSlot(index).getStack();
            if (itemStack != null
                    && block != null
                    && itemStack.getItem().getStrVsBlock(itemStack, block) > dmg) {
                slot = index - 36;
                dmg = itemStack.getItem().getStrVsBlock(itemStack, block);
            }
        }
        if (dmg > 0.1F) {
            return slot;
        }
        return getMc().thePlayer.inventory.currentItem;
    }
}
