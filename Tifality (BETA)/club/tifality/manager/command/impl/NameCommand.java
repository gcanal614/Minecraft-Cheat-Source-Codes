/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.command.impl;

import club.tifality.gui.notification.dev.DevNotifications;
import club.tifality.manager.command.Command;
import club.tifality.manager.command.CommandExecutionException;
import club.tifality.utils.Wrapper;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import net.minecraft.client.Minecraft;

public final class NameCommand
extends Command {
    @Override
    public String[] getAliases() {
        return new String[]{"name", "username", "ingame"};
    }

    @Override
    public void execute(String[] arguments2) throws CommandExecutionException {
        Wrapper.addChatMessage("Copied name to clipboard.");
        DevNotifications.getManager().post("Copied name to clipboard.");
        StringSelection stringSelection = new StringSelection(Minecraft.getMinecraft().thePlayer.getName());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
    }

    @Override
    public String getUsage() {
        return "name";
    }
}

