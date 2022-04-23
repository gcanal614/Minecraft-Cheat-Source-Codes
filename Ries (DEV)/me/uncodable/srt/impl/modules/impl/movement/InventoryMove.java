/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package me.uncodable.srt.impl.modules.impl.movement;

import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.entity.EventMotionUpdate;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import me.uncodable.srt.impl.modules.api.settings.Setting;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.lwjgl.input.Keyboard;

@ModuleInfo(internalName="InventoryMove", name="Inventory Move", desc="Allows you to move while in your inventory.\nNo, there is no Azura-like mode that contradicts the functionality of this module.", category=Module.Category.MOVEMENT)
public class InventoryMove
extends Module {
    private static final String INTERNAL_ALLOW_JUMP = "INTERNAL_ALLOW_JUMP";
    private static final String ALLOW_JUMP_SETTING_NAME = "Allow Jump";

    public InventoryMove(int key, boolean enabled) {
        super(key, enabled);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_ALLOW_JUMP, ALLOW_JUMP_SETTING_NAME);
    }

    @EventTarget(target=EventMotionUpdate.class)
    public void onMotion(EventMotionUpdate e) {
        if (InventoryMove.MC.currentScreen instanceof GuiContainer) {
            if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_ALLOW_JUMP, Setting.Type.CHECKBOX).isTicked()) {
                InventoryMove.MC.gameSettings.keyBindJump.setKeyDown(Keyboard.isKeyDown((int)InventoryMove.MC.gameSettings.keyBindJump.getKeyCode()));
            }
            InventoryMove.MC.gameSettings.keyBindRight.setKeyDown(Keyboard.isKeyDown((int)InventoryMove.MC.gameSettings.keyBindRight.getKeyCode()));
            InventoryMove.MC.gameSettings.keyBindLeft.setKeyDown(Keyboard.isKeyDown((int)InventoryMove.MC.gameSettings.keyBindLeft.getKeyCode()));
            InventoryMove.MC.gameSettings.keyBindForward.setKeyDown(Keyboard.isKeyDown((int)InventoryMove.MC.gameSettings.keyBindForward.getKeyCode()));
            InventoryMove.MC.gameSettings.keyBindBack.setKeyDown(Keyboard.isKeyDown((int)InventoryMove.MC.gameSettings.keyBindBack.getKeyCode()));
            InventoryMove.MC.gameSettings.keyBindSprint.setKeyDown(Keyboard.isKeyDown((int)InventoryMove.MC.gameSettings.keyBindSprint.getKeyCode()));
        }
    }
}

