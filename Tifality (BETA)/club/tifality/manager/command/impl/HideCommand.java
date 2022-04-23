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
import java.util.Optional;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public final class HideCommand
extends Command {
    @Override
    public String[] getAliases() {
        return new String[]{"hide", "h", "visible", "v"};
    }

    @Override
    public void execute(String[] arguments2) throws CommandExecutionException {
        if (arguments2.length == 2) {
            String arg = arguments2[1];
            if (arg.equalsIgnoreCase("clear")) {
                for (Module module : Tifality.getInstance().getModuleManager().getModules()) {
                    module.setHidden(false);
                }
                Wrapper.addChatMessage("Cleared all hidden module.");
                DevNotifications.getManager().post("Cleared all hidden module.");
                NotificationPublisher.queue("Command System", "Cleared all hidden module.", NotificationType.NOTIFY, 3000);
            } else if (arg.equalsIgnoreCase("list")) {
                Wrapper.addChatMessage("Hidden Modules");
                for (Module module : Tifality.getInstance().getModuleManager().getModules()) {
                    if (!module.isHidden()) continue;
                    Wrapper.getPlayer().addChatMessage(new ChatComponentText((Object)((Object)EnumChatFormatting.GRAY) + "- " + (Object)((Object)EnumChatFormatting.RED) + module.getLabel()));
                }
            } else {
                Optional<Module> module = Tifality.getInstance().getModuleManager().getModule(arg);
                if (module.isPresent()) {
                    Module m;
                    m.setHidden(!(m = module.get()).isHidden());
                    NotificationPublisher.queue("Command System", m.getLabel() + " is now " + (m.isHidden() ? "\u00a7Chidden\u00a77." : "\u00a7Ashown\u00a77."), NotificationType.NOTIFY, 3000);
                    Wrapper.addChatMessage(m.getLabel() + " is now " + (m.isHidden() ? "\u00a7Chidden\u00a77." : "\u00a7Ashown\u00a77."));
                    DevNotifications.getManager().post(m.getLabel() + " is now " + (m.isHidden() ? "\u00a7Chidden\u00a77." : "\u00a7Ashown\u00a77."));
                }
            }
        } else {
            throw new CommandExecutionException(this.getUsage());
        }
    }

    @Override
    public String getUsage() {
        return "hide <module> | clear | list";
    }
}

