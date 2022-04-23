/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import club.tifality.module.ModuleManager;
import club.tifality.module.impl.player.InventoryMove;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;
import org.lwjgl.input.Keyboard;

public class MovementInputFromOptions
extends MovementInput {
    private final GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettingsIn) {
        this.gameSettings = gameSettingsIn;
    }

    @Override
    public void updatePlayerMoveState() {
        this.moveStrafe = 0.0f;
        this.moveForward = 0.0f;
        if (ModuleManager.getInstance(InventoryMove.class).isEnabled()) {
            if (!(Minecraft.getMinecraft().currentScreen instanceof GuiChat)) {
                if (InventoryMove.noMove.get().booleanValue() && Minecraft.getMinecraft().currentScreen instanceof GuiContainer) {
                    return;
                }
                if (Keyboard.isKeyDown(this.gameSettings.keyBindForward.getKeyCode())) {
                    this.moveForward += 1.0f;
                }
                if (Keyboard.isKeyDown(this.gameSettings.keyBindBack.getKeyCode())) {
                    this.moveForward -= 1.0f;
                }
                if (Keyboard.isKeyDown(this.gameSettings.keyBindLeft.getKeyCode())) {
                    this.moveStrafe += 1.0f;
                }
                if (Keyboard.isKeyDown(this.gameSettings.keyBindRight.getKeyCode())) {
                    this.moveStrafe -= 1.0f;
                }
                this.jump = Keyboard.isKeyDown(this.gameSettings.keyBindJump.getKeyCode());
            } else {
                this.jump = this.gameSettings.keyBindJump.isKeyDown();
            }
        } else {
            if (this.gameSettings.keyBindForward.isKeyDown()) {
                this.moveForward += 1.0f;
            }
            if (this.gameSettings.keyBindBack.isKeyDown()) {
                this.moveForward -= 1.0f;
            }
            if (this.gameSettings.keyBindLeft.isKeyDown()) {
                this.moveStrafe += 1.0f;
            }
            if (this.gameSettings.keyBindRight.isKeyDown()) {
                this.moveStrafe -= 1.0f;
            }
            this.jump = this.gameSettings.keyBindJump.isKeyDown();
        }
        this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
        if (this.sneak) {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3);
            this.moveForward = (float)((double)this.moveForward * 0.3);
        }
    }
}

