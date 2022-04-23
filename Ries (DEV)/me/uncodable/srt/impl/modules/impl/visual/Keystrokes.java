/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package me.uncodable.srt.impl.modules.impl.visual;

import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.render.Event2DRender;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Keyboard;

@ModuleInfo(internalName="Keystrokes", name="Keystrokes", desc="Allows you to actually know which keys you're pressing.", category=Module.Category.VISUAL, legit=true)
public class Keystrokes
extends Module {
    int getHeight;

    public Keystrokes(int key, boolean enabled) {
        super(key, enabled);
    }

    @EventTarget(target=Event2DRender.class)
    public void onRender(Event2DRender e) {
        if (!(Keystrokes.MC.currentScreen instanceof GuiChat) && !Keystrokes.MC.gameSettings.showDebugInfo) {
            this.getHeight = e.getScaledResolution().getScaledHeight();
            Gui.drawRect(29, (int)((float)this.getHeight / 2.0f - 25.0f), 49, (int)((float)this.getHeight / 2.0f - 5.0f), Keystrokes.MC.gameSettings.keyBindForward.isKeyDown() ? -855638017 : -872415232);
            Keystrokes.MC.fontRendererObj.drawString(Keyboard.getKeyName((int)Keystrokes.MC.gameSettings.keyBindForward.getKeyCode()), 36, (int)((float)this.getHeight / 2.0f - 19.0f), -855638017);
            Gui.drawRect(4, (int)((float)this.getHeight / 2.0f), 24, (int)((float)this.getHeight / 2.0f + 20.0f), Keystrokes.MC.gameSettings.keyBindLeft.isKeyDown() ? -855638017 : -872415232);
            Keystrokes.MC.fontRendererObj.drawString(Keyboard.getKeyName((int)Keystrokes.MC.gameSettings.keyBindLeft.getKeyCode()), 11, (int)((float)this.getHeight / 2.0f + 6.0f), -855638017);
            Gui.drawRect(29, (int)((float)this.getHeight / 2.0f), 49, (int)((float)this.getHeight / 2.0f + 20.0f), Keystrokes.MC.gameSettings.keyBindBack.isKeyDown() ? -855638017 : -872415232);
            Keystrokes.MC.fontRendererObj.drawString(Keyboard.getKeyName((int)Keystrokes.MC.gameSettings.keyBindBack.getKeyCode()), 36, (int)((float)this.getHeight / 2.0f + 6.0f), -855638017);
            Gui.drawRect(54, (int)((float)this.getHeight / 2.0f), 74, (int)((float)this.getHeight / 2.0f + 20.0f), Keystrokes.MC.gameSettings.keyBindRight.isKeyDown() ? -855638017 : -872415232);
            Keystrokes.MC.fontRendererObj.drawString(Keyboard.getKeyName((int)Keystrokes.MC.gameSettings.keyBindRight.getKeyCode()), 61, (int)((float)this.getHeight / 2.0f + 6.0f), -855638017);
            Gui.drawRect(4, (int)((float)this.getHeight / 2.0f + 25.0f), 37, (int)((float)this.getHeight / 2.0f + 45.0f), Keystrokes.MC.gameSettings.keyBindAttack.isKeyDown() ? -855638017 : -872415232);
            Keystrokes.MC.fontRendererObj.drawString("LMB", 12, (int)((float)this.getHeight / 2.0f + 31.0f), -855638017);
            Gui.drawRect(42, (int)((float)this.getHeight / 2.0f + 25.0f), 74, (int)((float)this.getHeight / 2.0f + 45.0f), Keystrokes.MC.gameSettings.keyBindUseItem.isKeyDown() ? -855638017 : -872415232);
            Keystrokes.MC.fontRendererObj.drawString("RMB", 49, (int)((float)this.getHeight / 2.0f + 31.0f), -855638017);
        }
    }
}

