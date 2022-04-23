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
import club.tifality.module.ModuleCategory;
import club.tifality.utils.Wrapper;

public final class PanicCommand
extends Command {
    @Override
    public String[] getAliases() {
        return new String[]{"panic"};
    }

    @Override
    public void execute(String[] arguments2) throws CommandExecutionException {
        if (arguments2.length == 3 && arguments2[1].equalsIgnoreCase("category")) {
            String category = arguments2[2];
            boolean foundCategory = false;
            for (ModuleCategory cate : ModuleCategory.values()) {
                if (!cate.getDisplayName().equalsIgnoreCase(category)) continue;
                for (Module mod : Tifality.getInstance().getModuleManager().getModulesForCategory(cate)) {
                    mod.setEnabled(false);
                }
                Wrapper.addChatMessage("Disabled all modules in " + cate.getDisplayName() + " category.");
                DevNotifications.getManager().post("Disabled all modules in " + cate.getDisplayName() + " category.");
                NotificationPublisher.queue("Command System", "Disabled all modules in " + cate.getDisplayName() + " category.", NotificationType.OKAY, 3000);
                foundCategory = true;
                break;
            }
            if (!foundCategory) {
                Wrapper.addChatMessage("Could not find module.");
                DevNotifications.getManager().post("Could not find module.");
                NotificationPublisher.queue("Command System", "Could not find module.", NotificationType.WARNING, 3000);
            }
        } else if (arguments2.length == 2 && arguments2[1].equalsIgnoreCase("all")) {
            for (Module module : Tifality.getInstance().getModuleManager().getModules()) {
                module.setEnabled(false);
            }
            Wrapper.addChatMessage("Disabled all modules.");
            DevNotifications.getManager().post("Disabled all modules.");
            NotificationPublisher.queue("Command System", "Disabled all modules.", NotificationType.WARNING, 3000);
        } else {
            throw new CommandExecutionException(this.getUsage());
        }
    }

    @Override
    public String getUsage() {
        return "panic category <name> | all";
    }
}

