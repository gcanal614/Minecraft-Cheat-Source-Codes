/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package club.tifality.module.impl.combat;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.render.Render3DEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.utils.timer.TimerUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(label="AutoClick", category=ModuleCategory.COMBAT)
@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0010\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000f\u001a\u0010\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2={"Lclub/tifality/module/impl/combat/AutoClicker;", "Lclub/tifality/module/Module;", "()V", "leftDelay", "", "leftLastSwing", "leftValue", "Lclub/tifality/property/Property;", "", "kotlin.jvm.PlatformType", "maxCPSValue", "Lclub/tifality/property/impl/DoubleProperty;", "minCPSValue", "rightDelay", "rightLastSwing", "rightValue", "onRender", "", "event", "Lclub/tifality/manager/event/impl/render/Render3DEvent;", "Client"})
public final class AutoClicker
extends Module {
    private final DoubleProperty maxCPSValue = new DoubleProperty("Min APS", 8.0, 1.0, 30.0, 1.0);
    private final DoubleProperty minCPSValue = new DoubleProperty("Max APS", 16.0, 1.0, 30.0, 1.0);
    private final Property<Boolean> rightValue = new Property<Boolean>("Right", true);
    private final Property<Boolean> leftValue = new Property<Boolean>("Left", true);
    private long rightDelay = TimerUtil.randomClickDelay((int)((Number)this.minCPSValue.get()).doubleValue(), (int)((Number)this.maxCPSValue.get()).doubleValue());
    private long rightLastSwing;
    private long leftDelay = TimerUtil.randomClickDelay((int)((Number)this.minCPSValue.get()).doubleValue(), (int)((Number)this.maxCPSValue.get()).doubleValue());
    private long leftLastSwing;

    @Listener
    public final void onRender(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        KeyBinding keyBinding = Module.mc.gameSettings.keyBindAttack;
        Intrinsics.checkNotNullExpressionValue(keyBinding, "mc.gameSettings.keyBindAttack");
        if (keyBinding.isKeyDown()) {
            Boolean bl = this.leftValue.get();
            Intrinsics.checkNotNullExpressionValue(bl, "leftValue.get()");
            if (bl.booleanValue() && System.currentTimeMillis() - this.leftLastSwing >= this.leftDelay && Module.mc.playerController.curBlockDamageMP == 0.0f) {
                KeyBinding keyBinding2 = Module.mc.gameSettings.keyBindAttack;
                Intrinsics.checkNotNullExpressionValue(keyBinding2, "mc.gameSettings.keyBindAttack");
                KeyBinding.onTick(keyBinding2.getKeyCode());
                this.leftLastSwing = System.currentTimeMillis();
                this.leftDelay = TimerUtil.randomClickDelay((int)((Number)this.minCPSValue.get()).doubleValue(), (int)((Number)this.maxCPSValue.get()).doubleValue());
            }
        }
        KeyBinding keyBinding3 = Module.mc.gameSettings.keyBindUseItem;
        Intrinsics.checkNotNullExpressionValue(keyBinding3, "mc.gameSettings.keyBindUseItem");
        if (keyBinding3.isKeyDown()) {
            EntityPlayerSP entityPlayerSP = Module.mc.thePlayer;
            Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
            if (!entityPlayerSP.isUsingItem()) {
                Boolean bl = this.rightValue.get();
                Intrinsics.checkNotNullExpressionValue(bl, "rightValue.get()");
                if (bl.booleanValue() && System.currentTimeMillis() - this.rightLastSwing >= this.rightDelay) {
                    KeyBinding keyBinding4 = Module.mc.gameSettings.keyBindUseItem;
                    Intrinsics.checkNotNullExpressionValue(keyBinding4, "mc.gameSettings.keyBindUseItem");
                    KeyBinding.onTick(keyBinding4.getKeyCode());
                    this.rightLastSwing = System.currentTimeMillis();
                    this.rightDelay = TimerUtil.randomClickDelay((int)((Number)this.minCPSValue.get()).doubleValue(), (int)((Number)this.maxCPSValue.get()).doubleValue());
                }
            }
        }
    }
}

