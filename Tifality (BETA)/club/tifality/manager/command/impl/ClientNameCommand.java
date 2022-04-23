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
import club.tifality.module.impl.render.hud.Hud;
import club.tifality.utils.Wrapper;

public final class ClientNameCommand
extends Command {
    @Override
    public String[] getAliases() {
        return new String[]{"clientname", "rename"};
    }

    @Override
    public void execute(String[] arguments2) throws CommandExecutionException {
        StringBuilder sb;
        Hud hud = Tifality.getInstance().getModuleManager().getModule(Hud.class);
        if (arguments2.length >= 2) {
            sb = new StringBuilder();
            for (int i = 1; i < arguments2.length; ++i) {
                sb.append(arguments2[i]).append(' ');
            }
        } else {
            throw new CommandExecutionException(this.getUsage());
        }
        NotificationPublisher.queue("Command System", "Client name has been set to " + sb.toString() + ".", NotificationType.OKAY, 3000);
        Wrapper.addChatMessage("client name has been set to " + sb.toString());
        DevNotifications.getManager().post("client name has been set to " + sb.toString());
        hud.watermarkText.setValue(sb.toString());
    }

    @Override
    public String getUsage() {
        return "clientname <name>";
    }
}

