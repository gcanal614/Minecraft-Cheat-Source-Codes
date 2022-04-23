/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package club.tifality.module.impl.combat;

import club.tifality.Tifality;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.impl.movement.Flight;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.Representation;
import club.tifality.utils.RotationUtils;
import club.tifality.utils.movement.MovementUtils;
import club.tifality.utils.timer.TimerUtil;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(label="AutoPot", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0014\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0005H\u0002J\b\u0010\u0018\u001a\u00020\u000bH\u0002J\u0010\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u000bH\u0002J\u0018\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u000bH\u0002J\u0018\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0018\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#H\u0002J\b\u0010%\u001a\u00020&H\u0016J\b\u0010'\u001a\u00020&H\u0016J\u0010\u0010(\u001a\u00020&2\u0006\u0010)\u001a\u00020*H\u0007J\u0018\u0010+\u001a\u00020&2\u0006\u0010,\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u000bH\u0002R\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u00108F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0014\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2={"Lclub/tifality/module/impl/combat/AutoPot;", "Lclub/tifality/module/Module;", "()V", "checkValue", "Lclub/tifality/property/Property;", "", "kotlin.jvm.PlatformType", "healthValue", "Lclub/tifality/property/impl/DoubleProperty;", "jumpValue", "last", "", "potting", "predictValue", "regenValue", "rotations", "", "getRotations", "()[F", "slot", "speedValue", "timer", "Lclub/tifality/utils/timer/TimerUtil;", "checkVoid", "getSlot", "hasPot", "id", "targetSlot", "isBestPot", "potion", "Lnet/minecraft/item/ItemPotion;", "stack", "Lnet/minecraft/item/ItemStack;", "isVoid", "X", "", "Z", "onDisable", "", "onEnable", "onUpdate", "event", "Lclub/tifality/manager/event/impl/player/UpdatePositionEvent;", "swap", "currentSlot", "Client"})
public final class AutoPot
extends Module {
    private final DoubleProperty healthValue = new DoubleProperty("Health", 16.0, 1.0, 40.0, 0.5, Representation.PERCENTAGE);
    private final Property<Boolean> regenValue = new Property<Boolean>("Regen", true);
    private final Property<Boolean> speedValue = new Property<Boolean>("Speed", true);
    private final Property<Boolean> jumpValue = new Property<Boolean>("Jump", false);
    private final Property<Boolean> predictValue = new Property<Boolean>("Predict", false);
    private final Property<Boolean> checkValue = new Property<Boolean>("Check", false);
    private boolean potting;
    private int slot;
    private int last;
    private TimerUtil timer = new TimerUtil();

    @Override
    public void onEnable() {
        super.onEnable();
        this.potting = false;
        this.slot = -1;
        this.last = -1;
        this.timer.reset();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.potting = false;
    }

    @Listener
    public final void onUpdate(@NotNull UpdatePositionEvent event) {
        block17: {
            block16: {
                Intrinsics.checkNotNullParameter(event, "event");
                Tifality tifality = Tifality.INSTANCE;
                Intrinsics.checkNotNullExpressionValue(tifality, "Tifality.INSTANCE");
                Flight flight = tifality.getModuleManager().getModule(Flight.class);
                Intrinsics.checkNotNull(flight);
                if (flight.isEnabled() || this.checkVoid()) break block16;
                Boolean bl = this.checkValue.get();
                Intrinsics.checkNotNullExpressionValue(bl, "checkValue.get()");
                if (!bl.booleanValue()) break block17;
                EntityPlayerSP entityPlayerSP = Module.mc.thePlayer;
                Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
                if (!entityPlayerSP.isEating()) break block17;
            }
            return;
        }
        this.slot = this.getSlot();
        if (this.timer.hasElapsed(1000L)) {
            int speedId;
            int regenId = Potion.regeneration.getId();
            if (!Module.mc.thePlayer.isPotionActive(regenId) && !this.potting && Module.mc.thePlayer.onGround) {
                Boolean bl = this.regenValue.get();
                Intrinsics.checkNotNullExpressionValue(bl, "regenValue.get()");
                if (bl.booleanValue()) {
                    EntityPlayerSP entityPlayerSP = Module.mc.thePlayer;
                    Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
                    if ((double)entityPlayerSP.getHealth() <= ((Number)this.healthValue.getValue()).doubleValue() && this.hasPot(regenId)) {
                        int cum = this.hasPot(regenId, this.slot);
                        if (cum != -1) {
                            this.swap(cum, this.slot);
                        }
                        this.last = Module.mc.thePlayer.inventory.currentItem;
                        Module.mc.thePlayer.inventory.currentItem = this.slot;
                        Boolean bl2 = this.jumpValue.getValue();
                        Intrinsics.checkNotNullExpressionValue(bl2, "jumpValue.value");
                        event.setPitch(bl2 != false && !MovementUtils.isMoving() ? -90.0f : (MovementUtils.isMoving() ? 85.0f : 90.0f));
                        Module.mc.thePlayer.sendQueue.sendPacket(new C03PacketPlayer.C05PacketPlayerLook(this.getRotations()[0], this.getRotations()[1], Module.mc.thePlayer.onGround));
                        Boolean bl3 = this.jumpValue.getValue();
                        Intrinsics.checkNotNullExpressionValue(bl3, "jumpValue.value");
                        if (bl3.booleanValue() && !MovementUtils.isMoving()) {
                            Module.mc.thePlayer.motionY = MovementUtils.getJumpBoostModifier(0.41999998688698, true);
                            MovementUtils.setSpeed(0.0);
                        }
                        this.potting = true;
                        this.timer.reset();
                    }
                }
            }
            if (!Module.mc.thePlayer.isPotionActive(speedId = Potion.moveSpeed.getId()) && !this.potting && Module.mc.thePlayer.onGround) {
                Boolean bl = this.speedValue.get();
                Intrinsics.checkNotNullExpressionValue(bl, "speedValue.get()");
                if (bl.booleanValue() && this.hasPot(speedId)) {
                    int cum = this.hasPot(speedId, this.slot);
                    if (cum != -1) {
                        this.swap(cum, this.slot);
                    }
                    this.last = Module.mc.thePlayer.inventory.currentItem;
                    Module.mc.thePlayer.inventory.currentItem = this.slot;
                    Boolean bl4 = this.jumpValue.getValue();
                    Intrinsics.checkNotNullExpressionValue(bl4, "jumpValue.value");
                    event.setPitch(bl4 != false && !MovementUtils.isMoving() ? -90.0f : (MovementUtils.isMoving() ? 85.0f : 90.0f));
                    Module.mc.thePlayer.sendQueue.sendPacket(new C03PacketPlayer.C05PacketPlayerLook(this.getRotations()[0], this.getRotations()[1], Module.mc.thePlayer.onGround));
                    Boolean bl5 = this.jumpValue.getValue();
                    Intrinsics.checkNotNullExpressionValue(bl5, "jumpValue.value");
                    if (bl5.booleanValue() && !MovementUtils.isMoving()) {
                        Module.mc.thePlayer.motionY = MovementUtils.getJumpBoostModifier(0.41999998688698, true);
                        MovementUtils.setSpeed(0.0);
                    }
                    this.potting = true;
                    this.timer.reset();
                }
            }
        }
        if (this.potting) {
            if (Module.mc.thePlayer.inventory.getCurrentItem() != null && Module.mc.playerController.sendUseItem(Module.mc.thePlayer, Module.mc.theWorld, Module.mc.thePlayer.inventory.getCurrentItem())) {
                Module.mc.entityRenderer.itemRenderer.resetEquippedProgress2();
            }
            if (this.last != -1) {
                Module.mc.thePlayer.inventory.currentItem = this.last;
            }
            this.potting = false;
            this.last = -1;
        }
        this.setSuffix(new Supplier<String>(this){
            final /* synthetic */ AutoPot this$0;

            public final String get() {
                T t = AutoPot.access$getJumpValue$p(this.this$0).get();
                Intrinsics.checkNotNullExpressionValue(t, "jumpValue.get()");
                return (Boolean)t != false ? "Jump Only" : "Floor";
            }
            {
                this.this$0 = autoPot;
            }
        });
    }

    @NotNull
    public final float[] getRotations() {
        float[] fArray;
        double movedPosX = Module.mc.thePlayer.posX + Module.mc.thePlayer.motionX * 26.0;
        EntityPlayerSP entityPlayerSP = Module.mc.thePlayer;
        Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
        double movedPosY = entityPlayerSP.getEntityBoundingBox().minY - 3.6;
        double movedPosZ = Module.mc.thePlayer.posZ + Module.mc.thePlayer.motionZ * 26.0;
        Boolean bl = this.predictValue.get();
        Intrinsics.checkNotNullExpressionValue(bl, "predictValue.get()");
        if (bl.booleanValue()) {
            float[] fArray2 = RotationUtils.getRotationFromPosition(movedPosX, movedPosZ, movedPosY);
            fArray = fArray2;
            Intrinsics.checkNotNullExpressionValue(fArray2, "RotationUtils.getRotatio\u2026sX, movedPosZ, movedPosY)");
        } else {
            float[] fArray3 = new float[2];
            fArray3[0] = Module.mc.thePlayer.rotationYaw;
            fArray = fArray3;
            fArray3[1] = 90.0f;
        }
        return fArray;
    }

    /*
     * WARNING - void declaration
     */
    private final int hasPot(int id, int targetSlot) {
        int n = 9;
        int n2 = 44;
        while (n <= n2) {
            void i;
            Slot slot = Module.mc.thePlayer.inventoryContainer.getSlot((int)i);
            Intrinsics.checkNotNullExpressionValue(slot, "mc.thePlayer.inventoryContainer.getSlot(i)");
            if (slot.getHasStack()) {
                ItemStack is;
                Slot slot2 = Module.mc.thePlayer.inventoryContainer.getSlot((int)i);
                Intrinsics.checkNotNullExpressionValue(slot2, "mc.thePlayer.inventoryContainer.getSlot(i)");
                ItemStack itemStack = is = slot2.getStack();
                Intrinsics.checkNotNullExpressionValue(itemStack, "`is`");
                if (itemStack.getItem() instanceof ItemPotion) {
                    Item item = is.getItem();
                    if (item == null) {
                        throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemPotion");
                    }
                    ItemPotion pot = (ItemPotion)item;
                    if (!pot.getEffects(is).isEmpty()) {
                        PotionEffect effect;
                        PotionEffect potionEffect = effect = pot.getEffects(is).get(0);
                        Intrinsics.checkNotNullExpressionValue(potionEffect, "effect");
                        if (potionEffect.getPotionID() == id && ItemPotion.isSplash(is.getItemDamage()) && this.isBestPot(pot, is) && 36 + targetSlot != i) {
                            return (int)i;
                        }
                    }
                }
            }
            ++i;
        }
        return -1;
    }

    /*
     * WARNING - void declaration
     */
    private final boolean hasPot(int id) {
        int n = 9;
        int n2 = 44;
        while (n <= n2) {
            void i;
            Slot slot = Module.mc.thePlayer.inventoryContainer.getSlot((int)i);
            Intrinsics.checkNotNullExpressionValue(slot, "mc.thePlayer.inventoryContainer.getSlot(i)");
            if (slot.getHasStack()) {
                ItemStack is;
                Slot slot2 = Module.mc.thePlayer.inventoryContainer.getSlot((int)i);
                Intrinsics.checkNotNullExpressionValue(slot2, "mc.thePlayer.inventoryContainer.getSlot(i)");
                ItemStack itemStack = is = slot2.getStack();
                Intrinsics.checkNotNullExpressionValue(itemStack, "`is`");
                if (itemStack.getItem() instanceof ItemPotion) {
                    Item item = is.getItem();
                    if (item == null) {
                        throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemPotion");
                    }
                    ItemPotion pot = (ItemPotion)item;
                    if (!pot.getEffects(is).isEmpty()) {
                        PotionEffect effect;
                        PotionEffect potionEffect = effect = pot.getEffects(is).get(0);
                        Intrinsics.checkNotNullExpressionValue(potionEffect, "effect");
                        if (potionEffect.getPotionID() == id && ItemPotion.isSplash(is.getItemDamage()) && this.isBestPot(pot, is)) {
                            return true;
                        }
                    }
                }
            }
            ++i;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    private final boolean isBestPot(ItemPotion potion, ItemStack stack) {
        if (potion.getEffects(stack) == null || potion.getEffects(stack).size() != 1) {
            return false;
        }
        PotionEffect potionEffect = potion.getEffects(stack).get(0);
        if (potionEffect == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.potion.PotionEffect");
        }
        PotionEffect effect = potionEffect;
        int potionID = effect.getPotionID();
        int amplifier = effect.getAmplifier();
        int duration = effect.getDuration();
        int n = 9;
        int n2 = 44;
        while (n <= n2) {
            void i;
            Slot slot = Module.mc.thePlayer.inventoryContainer.getSlot((int)i);
            Intrinsics.checkNotNullExpressionValue(slot, "mc.thePlayer.inventoryContainer.getSlot(i)");
            if (slot.getHasStack()) {
                ItemStack is;
                Slot slot2 = Module.mc.thePlayer.inventoryContainer.getSlot((int)i);
                Intrinsics.checkNotNullExpressionValue(slot2, "mc.thePlayer.inventoryContainer.getSlot(i)");
                ItemStack itemStack = is = slot2.getStack();
                Intrinsics.checkNotNullExpressionValue(itemStack, "`is`");
                if (itemStack.getItem() instanceof ItemPotion) {
                    Item item = is.getItem();
                    if (item == null) {
                        throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemPotion");
                    }
                    ItemPotion pot = (ItemPotion)item;
                    if (pot.getEffects(is) != null) {
                        for (PotionEffect o : pot.getEffects(is)) {
                            PotionEffect effects;
                            if (o == null) {
                                throw new NullPointerException("null cannot be cast to non-null type net.minecraft.potion.PotionEffect");
                            }
                            int id = effects.getPotionID();
                            int ampl = effects.getAmplifier();
                            int dur = effects.getDuration();
                            if (id != potionID || !ItemPotion.isSplash(is.getItemDamage())) continue;
                            if (ampl > amplifier) {
                                return false;
                            }
                            if (ampl != amplifier || dur <= duration) continue;
                            return false;
                        }
                    }
                }
            }
            ++i;
        }
        return true;
    }

    /*
     * WARNING - void declaration
     */
    private final int getSlot() {
        int spoofSlot = 8;
        int n = 36;
        int n2 = 44;
        while (n <= n2) {
            void i;
            Slot slot = Module.mc.thePlayer.inventoryContainer.getSlot((int)i);
            Intrinsics.checkNotNullExpressionValue(slot, "mc.thePlayer.inventoryContainer.getSlot(i)");
            if (!slot.getHasStack()) {
                spoofSlot = i - 36;
                break;
            }
            Slot slot2 = Module.mc.thePlayer.inventoryContainer.getSlot((int)i);
            Intrinsics.checkNotNullExpressionValue(slot2, "mc.thePlayer.inventoryContainer.getSlot(i)");
            ItemStack itemStack = slot2.getStack();
            Intrinsics.checkNotNullExpressionValue(itemStack, "mc.thePlayer.inventoryContainer.getSlot(i).stack");
            if (itemStack.getItem() instanceof ItemPotion) {
                spoofSlot = i - 36;
                break;
            }
            ++i;
        }
        return spoofSlot;
    }

    private final void swap(int currentSlot, int targetSlot) {
        Module.mc.playerController.windowClick(Module.mc.thePlayer.inventoryContainer.windowId, currentSlot, targetSlot, 2, Module.mc.thePlayer);
    }

    /*
     * WARNING - void declaration
     */
    private final boolean checkVoid() {
        int n = -1;
        int n2 = 1;
        while (n <= n2) {
            void x;
            int n3 = -1;
            int n4 = 1;
            while (n3 <= n4) {
                void z;
                if (this.isVoid((double)x, (double)z)) {
                    return true;
                }
                ++z;
            }
            ++x;
        }
        return false;
    }

    private final boolean isVoid(double X, double Z) {
        Tifality tifality = Tifality.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(tifality, "Tifality.INSTANCE");
        Flight fly = tifality.getModuleManager().getModule(Flight.class);
        EntityPlayerSP entityPlayerSP = Module.mc.thePlayer;
        Intrinsics.checkNotNull(entityPlayerSP);
        EntityPlayerSP thePlayer = entityPlayerSP;
        Flight flight = fly;
        Intrinsics.checkNotNullExpressionValue(flight, "fly");
        if (flight.isEnabled()) {
            return false;
        }
        if (Module.mc.thePlayer.posY < 0.0) {
            return true;
        }
        for (double off = 0.0; off < (double)((int)thePlayer.posY + 2); off += (double)2) {
            AxisAlignedBB bb;
            Intrinsics.checkNotNullExpressionValue(thePlayer.getEntityBoundingBox().offset(X, -off, Z), "thePlayer.entityBoundingBox.offset(X, -off, Z)");
            if (Module.mc.theWorld.getCollidingBoundingBoxes(thePlayer, bb).isEmpty()) {
                continue;
            }
            off += (double)2;
            return false;
        }
        return true;
    }

    public static final /* synthetic */ Property access$getJumpValue$p(AutoPot $this) {
        return $this.jumpValue;
    }
}

