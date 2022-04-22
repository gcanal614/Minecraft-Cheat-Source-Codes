package me.injusttice.neutron.impl.commands.impl;

import me.injusttice.neutron.impl.commands.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Name extends Command {

    protected Minecraft mc = Minecraft.getMinecraft();

    public Name() {
        super("Name", "Copies your username to clipboard", "name", "name");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if(args.length == 1) {
            return;
        }

        String name = mc.thePlayer.getName();
        StringSelection stringSelection = new StringSelection(name);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        mc.thePlayer.addChatMessage(new ChatComponentText("§8> §aCopied your username to the clipboard!"));
    }
}
