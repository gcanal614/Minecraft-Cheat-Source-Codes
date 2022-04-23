/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.command.impl;

import club.tifality.gui.notification.client.NotificationPublisher;
import club.tifality.gui.notification.client.NotificationType;
import club.tifality.gui.notification.dev.DevNotifications;
import club.tifality.manager.command.Command;
import club.tifality.manager.command.CommandExecutionException;
import club.tifality.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class VClipCommand
extends Command {
    @Override
    public String[] getAliases() {
        return new String[]{"vclip"};
    }

    @Override
    public void execute(String[] arguments2) throws CommandExecutionException {
        if (arguments2.length == 2) {
            try {
                double distance = Double.parseDouble(arguments2[1]);
                EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
                player.setPosition(player.posX, player.posY + distance, player.posZ);
                Wrapper.addChatMessage("VClipped " + distance + " blocks.");
                DevNotifications.getManager().post("VClipped " + distance + " blocks.");
                NotificationPublisher.queue("Command System", "VClipped " + distance + " blocks.", NotificationType.INFO, 3000);
            }
            catch (NumberFormatException e) {
                Wrapper.addChatMessage("'" + arguments2[1] + "' is not a number.");
                DevNotifications.getManager().post("'" + arguments2[1] + "' is not a number.");
                NotificationPublisher.queue("Command System", "'" + arguments2[1] + "' is not a number.", NotificationType.INFO, 3000);
            }
        } else {
            throw new CommandExecutionException(this.getUsage());
        }
    }

    @Override
    public String getUsage() {
        return "vclip <amount>";
    }
}

