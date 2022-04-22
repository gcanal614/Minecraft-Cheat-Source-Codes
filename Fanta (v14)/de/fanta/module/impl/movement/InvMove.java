/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 */
package de.fanta.module.impl.movement;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventReceivedPacket;
import de.fanta.events.listeners.EventTick;
import de.fanta.module.Module;
import java.awt.Color;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.Packet;
import org.lwjgl.opengl.Display;

public class InvMove
extends Module {
    public InvMove() {
        super("InvMove", 0, Module.Type.Player, Color.YELLOW);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventTick) {
            Packet packet = EventReceivedPacket.INSTANCE.getPacket();
        }
        if (event instanceof EventTick && event.isPre()) {
            if (InvMove.mc.currentScreen instanceof GuiChat) {
                return;
            }
            InvMove.mc.gameSettings.keyBindForward.pressed = GameSettings.isKeyDown(InvMove.mc.gameSettings.keyBindForward);
            InvMove.mc.gameSettings.keyBindBack.pressed = GameSettings.isKeyDown(InvMove.mc.gameSettings.keyBindBack);
            InvMove.mc.gameSettings.keyBindRight.pressed = GameSettings.isKeyDown(InvMove.mc.gameSettings.keyBindRight);
            InvMove.mc.gameSettings.keyBindLeft.pressed = GameSettings.isKeyDown(InvMove.mc.gameSettings.keyBindLeft);
            InvMove.mc.gameSettings.keyBindSprint.pressed = GameSettings.isKeyDown(InvMove.mc.gameSettings.keyBindSprint);
            InvMove.mc.gameSettings.keyBindJump.pressed = GameSettings.isKeyDown(InvMove.mc.gameSettings.keyBindJump);
        }
    }

    public void setIngameFocus() {
        if (Display.isActive() && !InvMove.mc.inGameHasFocus) {
            InvMove.mc.inGameHasFocus = true;
            InvMove.mc.mouseHelper.grabMouseCursor();
            InvMove.mc.leftClickCounter = 10000;
        }
    }
}

