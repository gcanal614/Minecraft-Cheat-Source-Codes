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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public final class BindCommand
extends Command {
    @Override
    public String[] getAliases() {
        return new String[]{"bind", "b"};
    }

    @Override
    public void execute(String[] arguments2) throws CommandExecutionException {
        if (arguments2.length == 3) {
            String moduleName = arguments2[1];
            String keyName = arguments2[2];
            boolean foundModule = false;
            for (Module module : Tifality.getInstance().getModuleManager().getModules()) {
                if (!module.getLabel().equalsIgnoreCase(moduleName)) continue;
                module.setKey(Keyboard.getKeyIndex(keyName.toUpperCase()));
                Wrapper.addChatMessage("Set " + module.getLabel() + " to " + Keyboard.getKeyName(module.getKey()) + ".");
                DevNotifications.getManager().post("Set " + module.getLabel() + " to " + Keyboard.getKeyName(module.getKey()) + ".");
                NotificationPublisher.queue("Command System", "Set " + module.getLabel() + " to " + Keyboard.getKeyName(module.getKey()) + ".", NotificationType.OKAY, 3000);
                foundModule = true;
                break;
            }
            if (!foundModule) {
                Wrapper.addChatMessage("Cound not find module.");
                DevNotifications.getManager().post("Cound not find module.");
                NotificationPublisher.queue("Command System", "Cound not find module.", NotificationType.WARNING, 3000);
            }
        } else if (arguments2.length == 2) {
            if (arguments2[1].equalsIgnoreCase("clear")) {
                for (Module module : Tifality.getInstance().getModuleManager().getModules()) {
                    module.setKey(0);
                    Wrapper.addChatMessage("Cleared all binds.");
                    DevNotifications.getManager().post("Cleared all binds.");
                    NotificationPublisher.queue("Command System", "Cleared all binds.", NotificationType.OKAY, 3000);
                }
            } else if (arguments2[1].equalsIgnoreCase("list")) {
                Wrapper.addChatMessage("Binds");
                for (Module module : Tifality.getInstance().getModuleManager().getModules()) {
                    if (module.getKey() == 0) continue;
                    Wrapper.getPlayer().addChatMessage(new ChatComponentText((Object)((Object)EnumChatFormatting.GRAY) + "- " + (Object)((Object)EnumChatFormatting.RED) + module.getLabel() + ": " + Keyboard.getKeyName(module.getKey())));
                }
            }
        } else {
            throw new CommandExecutionException(this.getUsage());
        }
    }

    @Override
    public String getUsage() {
        return "bind <module> <key> | clear | list";
    }
}

