/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.command.impl;

import club.tifality.Tifality;
import club.tifality.gui.notification.client.NotificationPublisher;
import club.tifality.gui.notification.client.NotificationType;
import club.tifality.gui.notification.dev.DevNotifications;
import club.tifality.manager.command.Command;
import club.tifality.manager.command.CommandExecutionException;
import club.tifality.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class FriendCommand
extends Command {
    @Override
    public String[] getAliases() {
        return new String[]{"friend", "f", "fr"};
    }

    @Override
    public void execute(String[] arguments2) throws CommandExecutionException {
        if (arguments2.length > 2) {
            if (arguments2[1].equalsIgnoreCase("add")) {
                for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                    String name;
                    if (!(entity instanceof EntityPlayer) || !(name = entity.getName()).equalsIgnoreCase(arguments2[1])) continue;
                    Wrapper.addChatMessage("Added friend " + arguments2[1]);
                    DevNotifications.getManager().post("Added friend " + arguments2[1]);
                    NotificationPublisher.queue("Command System", "Added friend " + arguments2[1], NotificationType.INFO, 3000);
                    Tifality.getInstance().getFriendManager().isFriend(name);
                }
            } else if (arguments2[1].equalsIgnoreCase("remove")) {
                if (Tifality.getInstance().getFriendManager().isFriend(arguments2[1])) {
                    Tifality.getInstance().getFriendManager().unfriend(arguments2[1]);
                    Wrapper.addChatMessage("Removed friend " + arguments2[1]);
                    DevNotifications.getManager().post("Removed friend " + arguments2[1]);
                    NotificationPublisher.queue("Command System", "Removed friend " + arguments2[1], NotificationType.INFO, 3000);
                } else {
                    Wrapper.addChatMessage("That player didn't exist.");
                    DevNotifications.getManager().post("That player didn't exist.");
                    NotificationPublisher.queue("Command System", "That player didn't exist.", NotificationType.INFO, 3000);
                }
            }
        } else {
            throw new CommandExecutionException(this.getUsage());
        }
    }

    @Override
    public String getUsage() {
        return "friend add | remove <name>";
    }
}

