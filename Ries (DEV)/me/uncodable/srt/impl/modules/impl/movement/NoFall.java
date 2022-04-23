/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  store.intent.intentguard.annotation.Native
 */
package me.uncodable.srt.impl.modules.impl.movement;

import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.block.EventAddCollision;
import me.uncodable.srt.impl.events.events.entity.EventMotionUpdate;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import me.uncodable.srt.impl.modules.api.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import store.intent.intentguard.annotation.Native;

@ModuleInfo(internalName="NoFall", name="No Fall", desc="Allows you to never take fall damage again.", category=Module.Category.MOVEMENT)
public class NoFall
extends Module {
    private static final String COMBO_BOX_SETTING_NAME = "No Fall Mode";
    private static final String GROUND_SPOOF_TRUE = "Ground Spoof True";
    private static final String GROUND_SPOOF_FALSE = "Ground Spoof False";
    private static final String GROUND_SPOOF_ALTERNATE = "Ground Spoof Alternate";
    private static final String VERUS = "Verus";
    private static final String LEGACY_NCP = "Legacy NoCheat+ Flag";
    private static final String DAMAGE = "Damage";
    private float counter = 0.0f;

    public NoFall(int key, boolean enabled) {
        super(key, enabled);
        Ries.INSTANCE.getSettingManager().addComboBox(this, "INTERNAL_GENERAL_COMBO_BOX", COMBO_BOX_SETTING_NAME, GROUND_SPOOF_TRUE, GROUND_SPOOF_FALSE, GROUND_SPOOF_ALTERNATE, DAMAGE, VERUS, LEGACY_NCP);
    }

    @Override
    public void onDisable() {
        this.counter = 0.0f;
    }

    @EventTarget(target=EventMotionUpdate.class)
    @Native
    public void onMotion(EventMotionUpdate e) {
        String mode;
        switch (mode = Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()) {
            case "Legacy NoCheat+ Flag": {
                BlockPos blockPos = new BlockPos(NoFall.MC.thePlayer.posX, NoFall.MC.thePlayer.posY - 1.0, NoFall.MC.thePlayer.posZ);
                BlockPos blockPos2 = new BlockPos(NoFall.MC.thePlayer.posX, NoFall.MC.thePlayer.posY - 2.0, NoFall.MC.thePlayer.posZ);
                BlockPos blockPos3 = new BlockPos(NoFall.MC.thePlayer.posX, NoFall.MC.thePlayer.posY - 3.0, NoFall.MC.thePlayer.posZ);
                Block block = NoFall.MC.theWorld.getBlockState(blockPos).getBlock();
                Block block2 = NoFall.MC.theWorld.getBlockState(blockPos2).getBlock();
                Block block3 = NoFall.MC.theWorld.getBlockState(blockPos3).getBlock();
                if (block == Blocks.air && block2 == Blocks.air && block3 == Blocks.air || !(NoFall.MC.thePlayer.fallDistance > 2.0f)) break;
                NoFall.MC.thePlayer.sendQueue.packetNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(NoFall.MC.thePlayer.posX, NoFall.MC.thePlayer.posY + 0.1, NoFall.MC.thePlayer.posZ, false));
                NoFall.MC.thePlayer.motionY -= 100.0;
                NoFall.MC.thePlayer.fallDistance = 0.0f;
            }
        }
        if (e.getState() == EventMotionUpdate.State.PRE) {
            switch (mode) {
                case "Ground Spoof True": {
                    if (!(NoFall.MC.thePlayer.fallDistance > 2.0f)) break;
                    e.setOnGround(true);
                    break;
                }
                case "Ground Spoof False": {
                    e.setOnGround(false);
                    break;
                }
                case "Ground Spoof Alternate": {
                    if (!(NoFall.MC.thePlayer.fallDistance > 2.0f)) break;
                    e.setOnGround(NoFall.MC.thePlayer.ticksExisted % 2 == 0);
                    break;
                }
                case "Damage": {
                    if (!(NoFall.MC.thePlayer.fallDistance > 4.0f)) break;
                    e.setOnGround(true);
                }
            }
        }
    }

    @EventTarget(target=EventAddCollision.class)
    @Native
    public void onCollision(EventAddCollision e) {
        switch (Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()) {
            case "Verus": {
                if (!(NoFall.MC.thePlayer.fallDistance > 1.5f)) break;
                if (this.counter > 3.0f) {
                    this.counter = 0.0f;
                    e.setBoundingBox(new AxisAlignedBB(-3.0, -2.0, -3.0, 3.0, 1.0, 3.0).offset(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ()));
                }
                this.counter += 0.5f;
            }
        }
    }
}

