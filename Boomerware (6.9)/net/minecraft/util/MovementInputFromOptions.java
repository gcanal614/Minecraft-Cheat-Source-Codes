package net.minecraft.util;

import org.lwjgl.input.Keyboard;

import com.boomer.client.Client;
import com.boomer.client.module.modules.player.InvWalk;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput
{
    private final GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettingsIn)
    {
        this.gameSettings = gameSettingsIn;
    }

    @Override
    public void updatePlayerMoveState()
    {
        if ((Client.INSTANCE.getModuleManager().getModuleClass(InvWalk.class).isEnabled()) && (!(Minecraft.getMinecraft().currentScreen instanceof GuiChat)))
        {
            this.moveStrafe = 0.0F;
            this.moveForward = 0.0F;
            if (Keyboard.isKeyDown(this.gameSettings.keyBindForward.getKeyCode())) {
                this.moveForward += 1.0F;
            }
            if (Keyboard.isKeyDown(this.gameSettings.keyBindBack.getKeyCode())) {
                this.moveForward -= 1.0F;
            }
            if (Keyboard.isKeyDown(this.gameSettings.keyBindLeft.getKeyCode())) {
                this.moveStrafe += 1.0F;
            }
            if (Keyboard.isKeyDown(this.gameSettings.keyBindRight.getKeyCode())) {
                this.moveStrafe -= 1.0F;
            }
            this.jump = Keyboard.isKeyDown(this.gameSettings.keyBindJump.getKeyCode());
            this.sneak = this.gameSettings.keyBindSneak.pressed;
            if (this.sneak)
            {
                this.moveStrafe = ((float)(this.moveStrafe * 0.3D));
                this.moveForward = ((float)(this.moveForward * 0.3D));
            }
        }
        else
        {
            this.moveStrafe = 0.0F;
            this.moveForward = 0.0F;
            if (this.gameSettings.keyBindForward.isKeyDown()) {
                this.moveForward += 1.0F;
            }
            if (this.gameSettings.keyBindBack.isKeyDown()) {
                this.moveForward -= 1.0F;
            }
            if (this.gameSettings.keyBindLeft.isKeyDown()) {
                this.moveStrafe += 1.0F;
            }
            if (this.gameSettings.keyBindRight.isKeyDown()) {
                this.moveStrafe -= 1.0F;
            }
            this.jump = this.gameSettings.keyBindJump.isKeyDown();
            this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
            if (this.sneak)
            {
                this.moveStrafe = ((float)(this.moveStrafe * 0.3D));
                this.moveForward = ((float)(this.moveForward * 0.3D));
            }
        }
    }
}