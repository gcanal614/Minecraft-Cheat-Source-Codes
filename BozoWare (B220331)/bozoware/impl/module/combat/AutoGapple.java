// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.combat;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemAppleGold;
import bozoware.base.util.Wrapper;
import net.minecraft.potion.Potion;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Auto Gapple", moduleCategory = ModuleCategory.COMBAT)
public class AutoGapple extends Module
{
    private final ValueProperty<Integer> health;
    private final BooleanProperty eat_while_attacking;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    public boolean eating;
    public int slotBefore;
    
    public AutoGapple() {
        this.health = new ValueProperty<Integer>("HP", 5, 1, 20, this);
        this.eat_while_attacking = new BooleanProperty("Eat while Attacking", true, this);
        final int gcount;
        final int HP;
        this.onUpdatePositionEvent = (e -> {
            gcount = this.getGappleCount();
            this.setModuleSuffix(String.valueOf(gcount));
            if (!this.eating) {
                this.slotBefore = AutoGapple.mc.thePlayer.inventory.currentItem;
            }
            HP = (int)AutoGapple.mc.thePlayer.getHealth();
            if (this.eating && AutoGapple.mc.thePlayer.isPotionActive(Potion.regeneration)) {
                AutoGapple.mc.thePlayer.inventory.currentItem = this.slotBefore;
                AutoGapple.mc.gameSettings.keyBindUseItem.pressed = false;
                this.eating = false;
            }
            if (HP < this.health.getPropertyValue() && !AutoGapple.mc.thePlayer.isPotionActive(Potion.regeneration)) {
                if (this.eat_while_attacking.getPropertyValue()) {
                    if (this.getGappleSlot() != -1) {
                        this.eating = true;
                        AutoGapple.mc.thePlayer.inventory.currentItem = this.getGappleSlot();
                        AutoGapple.mc.gameSettings.keyBindUseItem.pressed = true;
                    }
                }
                else if (Aura.getInstance().getTarget() == null && this.getGappleSlot() != -1) {
                    AutoGapple.mc.thePlayer.inventory.currentItem = this.getGappleSlot();
                    AutoGapple.mc.gameSettings.keyBindUseItem.pressed = true;
                    this.eating = true;
                }
            }
        });
    }
    
    private int getGappleCount() {
        int gappleCount = 0;
        for (int i = 9; i < 45; ++i) {
            final ItemStack stack = Wrapper.getPlayer().inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemAppleGold) {
                gappleCount += stack.stackSize;
            }
        }
        return gappleCount;
    }
    
    private int getGappleSlot() {
        for (int i = 36; i < 45; ++i) {
            final ItemStack itemStack = AutoGapple.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemAppleGold) {
                return i - 36;
            }
        }
        return -1;
    }
}
