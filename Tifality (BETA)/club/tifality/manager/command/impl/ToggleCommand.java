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
import club.tifality.module.Module;
import club.tifality.utils.Wrapper;

public final class ToggleCommand
extends Command {
    @Override
    public String[] getAliases() {
        return new String[]{"toggle", "t"};
    }

    @Override
    public void execute(String[] arguments2) throws CommandExecutionException {
        if (arguments2.length == 2) {
            String moduleName = arguments2[1];
            for (Module module : Tifality.getInstance().getModuleManager().getModules()) {
                if (!module.getLabel().replaceAll(" ", "").equalsIgnoreCase(moduleName)) continue;
                module.toggle();
                Wrapper.addChatMessage(module.getLabel() + " has been " + (module.isEnabled() ? "\u00a7AEnabled\u00a77." : "\u00a7CDisabled\u00a77."));
                DevNotifications.getManager().post(module.getLabel() + " has been " + (module.isEnabled() ? "\u00a7Aenabled\u00a77." : "\u00a7Cdisabled\u00a77."));
                NotificationPublisher.queue("Command System", module.getLabel() + " has been " + (module.isEnabled() ? "\u00a7Aenabled\u00a77." : "\u00a7Cdisabled\u00a77."), module.isEnabled() ? NotificationType.OKAY : NotificationType.WARNING, 3000);
                return;
            }
        }
        throw new CommandExecutionException(this.getUsage());
    }

    @Override
    public String getUsage() {
        return "toggle/t <module name>";
    }
}

