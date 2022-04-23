/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.modules.impl.miscellaneous;

import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.entity.EventUpdate;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;

@ModuleInfo(internalName="PostCoordinates", name="Post Coordinates", desc="Allows you to instantly send coordinates to chat, instead of using your slow-ass hands.", category=Module.Category.MISCELLANEOUS, legit=true)
public class PostCoordinates
extends Module {
    public PostCoordinates(int key, boolean enabled) {
        super(key, enabled);
    }

    @Override
    public void onEnable() {
        PostCoordinates.MC.thePlayer.sendChatMessage("My Coordinates: X: " + (int)PostCoordinates.MC.thePlayer.posX + ", Y: " + (int)PostCoordinates.MC.thePlayer.posY + ", Z: " + (int)PostCoordinates.MC.thePlayer.posZ);
    }

    @EventTarget(target=EventUpdate.class)
    public void onUpdate(EventUpdate e) {
        this.toggle();
    }
}

