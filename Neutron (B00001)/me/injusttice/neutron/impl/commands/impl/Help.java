package me.injusttice.neutron.impl.commands.impl;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.impl.commands.Command;

public class Help extends Command {

    public Help() {
        super("Help", "?", " ", "help");
    }

    @Override
    public void onCommand(String[] args, String command) {
        NeutronMain.addChatMessage("§7§m----------------------------------------");
        NeutronMain.addChatMessage("§c§lAstomero §7- §f" + NeutronMain.client_build);
        NeutronMain.addChatMessage("§7§oDeveloped by Initial");
        NeutronMain.addChatMessage(" ");
        NeutronMain.addChatMessage("§c§lCommands:");
        NeutronMain.addChatMessage("§c.hide §7- Hides a module by name");
        NeutronMain.addChatMessage("§c.vclip §7- Clips you down or up");
        NeutronMain.addChatMessage("§c.bind §7- Binds a module");
        NeutronMain.addChatMessage("§c.rename §7- Changes the client name");
        NeutronMain.addChatMessage("§c.name §7- Copies your Minecraft username to clipboard");
        NeutronMain.addChatMessage("§c.toggle §7- Toggles a module");
        NeutronMain.addChatMessage("§c.hclip §7- Clips you horizontal");
        NeutronMain.addChatMessage("§7§m----------------------------------------");
    }
}