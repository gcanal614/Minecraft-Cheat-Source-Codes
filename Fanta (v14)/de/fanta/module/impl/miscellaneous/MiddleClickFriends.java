/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package de.fanta.module.impl.miscellaneous;

import de.fanta.events.Event;
import de.fanta.module.Module;
import de.fanta.utils.ChatUtil;
import de.fanta.utils.FriendSystem;
import java.awt.Color;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;

public class MiddleClickFriends
extends Module {
    private long milliSeconds = System.currentTimeMillis();

    public MiddleClickFriends() {
        super("MCF", 0, Module.Type.Misc, Color.cyan);
    }

    @Override
    public void onEvent(Event event) {
        Entity entity;
        if (Mouse.isButtonDown((int)2) && (entity = MiddleClickFriends.mc.objectMouseOver.entityHit) != null && entity instanceof EntityPlayer && System.currentTimeMillis() - this.milliSeconds > 300L) {
            if (FriendSystem.getFriends().contains(entity.getName())) {
                FriendSystem.removeFriend(entity.getName());
                ChatUtil.sendChatMessageWithPrefix("Removed " + entity.getName() + " from your friend list!");
                this.milliSeconds = System.currentTimeMillis();
            } else {
                FriendSystem.addFriend(entity.getName());
                ChatUtil.sendChatMessageWithPrefix("Added " + entity.getName() + " to your friend list!");
                this.milliSeconds = System.currentTimeMillis();
            }
        }
    }
}

