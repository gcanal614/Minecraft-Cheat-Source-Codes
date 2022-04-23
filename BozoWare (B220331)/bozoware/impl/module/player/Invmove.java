// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiChat;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "InvMove", moduleCategory = ModuleCategory.PLAYER)
public class Invmove extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    
    public Invmove() {
        this.onModuleDisabled = (() -> {});
        this.onModuleEnabled = (() -> {});
        EntityPlayerSP thePlayer8;
        EntityPlayerSP thePlayer4;
        EntityPlayerSP thePlayer9;
        EntityPlayerSP thePlayer5;
        EntityPlayerSP thePlayer10;
        EntityPlayerSP thePlayer6;
        EntityPlayerSP thePlayer11;
        EntityPlayerSP thePlayer7;
        this.onUpdatePositionEvent = (UpdatePositionEvent -> {
            if (Invmove.mc.currentScreen != null && !(Invmove.mc.currentScreen instanceof GuiChat)) {
                if (Keyboard.isKeyDown(17)) {
                    Invmove.mc.gameSettings.keyBindForward.pressed = true;
                }
                else {
                    Invmove.mc.gameSettings.keyBindForward.pressed = false;
                }
                if (Keyboard.isKeyDown(31)) {
                    Invmove.mc.gameSettings.keyBindBack.pressed = true;
                }
                else {
                    Invmove.mc.gameSettings.keyBindBack.pressed = false;
                }
                if (Keyboard.isKeyDown(32)) {
                    Invmove.mc.gameSettings.keyBindRight.pressed = true;
                }
                else {
                    Invmove.mc.gameSettings.keyBindRight.pressed = false;
                }
                if (Keyboard.isKeyDown(30)) {
                    Invmove.mc.gameSettings.keyBindLeft.pressed = true;
                }
                else {
                    Invmove.mc.gameSettings.keyBindLeft.pressed = false;
                }
                if (Keyboard.isKeyDown(203)) {
                    thePlayer4 = (thePlayer8 = Invmove.mc.thePlayer);
                    thePlayer8.rotationYaw -= 4.0f;
                }
                if (Keyboard.isKeyDown(205)) {
                    thePlayer5 = (thePlayer9 = Invmove.mc.thePlayer);
                    thePlayer9.rotationYaw += 4.0f;
                }
                if (Keyboard.isKeyDown(200)) {
                    thePlayer6 = (thePlayer10 = Invmove.mc.thePlayer);
                    thePlayer10.rotationPitch -= 4.0f;
                }
                if (Keyboard.isKeyDown(208)) {
                    thePlayer7 = (thePlayer11 = Invmove.mc.thePlayer);
                    thePlayer11.rotationPitch += 4.0f;
                }
                if (Invmove.mc.thePlayer.rotationPitch >= 90.0f) {
                    Invmove.mc.thePlayer.rotationPitch = 90.0f;
                }
                if (Invmove.mc.thePlayer.rotationPitch <= -90.0f) {
                    Invmove.mc.thePlayer.rotationPitch = -90.0f;
                }
                if (Keyboard.isKeyDown(57) && Invmove.mc.thePlayer.onGround && !Invmove.mc.thePlayer.isInWater()) {
                    Invmove.mc.gameSettings.keyBindJump.pressed = true;
                }
                else {
                    Invmove.mc.gameSettings.keyBindJump.pressed = false;
                }
                if (Invmove.mc.thePlayer.isInWater() && Keyboard.isKeyDown(Invmove.mc.gameSettings.keyBindJump.getKeyCode())) {
                    Invmove.mc.gameSettings.keyBindJump.pressed = true;
                }
            }
        });
    }
}
