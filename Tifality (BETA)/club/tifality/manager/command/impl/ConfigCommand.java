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
import club.tifality.manager.config.Config;
import club.tifality.utils.Wrapper;

public final class ConfigCommand
extends Command {
    @Override
    public String[] getAliases() {
        return new String[]{"config", "c", "preset"};
    }

    @Override
    public void execute(String[] arguments2) throws CommandExecutionException {
        if (arguments2.length >= 2) {
            String upperCaseFunction = arguments2[1].toUpperCase();
            if (arguments2.length == 3) {
                switch (upperCaseFunction) {
                    case "LOAD": {
                        if (Tifality.getInstance().getConfigManager().loadConfig(arguments2[2])) {
                            this.success("loaded", arguments2[2]);
                        } else {
                            this.fail("load", arguments2[2]);
                        }
                        return;
                    }
                    case "SAVE": {
                        if (Tifality.getInstance().getConfigManager().saveConfig(arguments2[2])) {
                            this.success("saved", arguments2[2]);
                        } else {
                            this.fail("save", arguments2[2]);
                        }
                        return;
                    }
                    case "DELETE": {
                        if (Tifality.getInstance().getConfigManager().deleteConfig(arguments2[2])) {
                            this.success("deleted", arguments2[2]);
                        } else {
                            this.fail("delete", arguments2[2]);
                        }
                        return;
                    }
                }
            } else if (arguments2.length == 2 && upperCaseFunction.equalsIgnoreCase("LIST")) {
                Wrapper.addChatMessage("Available Configs:");
                for (Config config : Tifality.getInstance().getConfigManager().getElements()) {
                    Wrapper.addChatMessage(config.getName());
                }
                return;
            }
        }
        throw new CommandExecutionException(this.getUsage());
    }

    private void success(String type2, String configName) {
        NotificationPublisher.queue("Command System", String.format("Successfully %s config: '%s'", type2, configName), NotificationType.OKAY, 3000);
        Wrapper.addChatMessage("Successfully %s config: '%s'");
        DevNotifications.getManager().post("Successfully %s config: '%s'");
    }

    private void fail(String type2, String configName) {
        Wrapper.addChatMessage("Failed to %s config: '%s'");
        DevNotifications.getManager().post("Failed to %s config: '%s'");
        NotificationPublisher.queue("Command System", String.format("Failed to %s config: '%s'", type2, configName), NotificationType.WARNING, 3000);
    }

    @Override
    public String getUsage() {
        return "config/c/preset <load/save/delete/list> <(optional)config>";
    }
}

