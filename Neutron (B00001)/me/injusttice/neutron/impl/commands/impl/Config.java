package me.injusttice.neutron.impl.commands.impl;

import java.io.File;
import java.util.Objects;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.impl.commands.Command;
import net.minecraft.util.EnumChatFormatting;

public class Config extends Command {
    
    public Config() {
        super("Config", "Loads / Saves a config.", "config");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length > 1) {
            String type = args[0];
            String name = args[1];
            switch (type) {
                case "save":
                    NeutronMain.addChatMessage("Saved the config " + EnumChatFormatting.RED + name + EnumChatFormatting.WHITE + ".");
                    try {
                        NeutronMain.instance.configManager.save(name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "load":
                    NeutronMain.addChatMessage("Loaded the config " + EnumChatFormatting.RED + name + EnumChatFormatting.WHITE + ".");
                    try {
                        NeutronMain.instance.configManager.load(name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "delete":
                    try {
                        NeutronMain.instance.configManager.delete(name);
                    } catch (Exception e) {
                        NeutronMain.addChatMessage("Java just killed itself, contact da owner");
                    } finally {
                        NeutronMain.addChatMessage("Deleted config " + name + "!");
                    }
                    break;
            }
        } else {
            try {
                String type = args[0];
                if (type.equalsIgnoreCase("list")) {
                    NeutronMain.addChatMessage("Config List:");
                    try {
                        for (File archivogay : (File[])Objects.<File[]>requireNonNull(((File)Objects.<File>requireNonNull(NeutronMain.instance.dir)).listFiles())) {
                            String fileName = archivogay.getName().substring(0, archivogay.getName().length() - 5);
                            NeutronMain.addChatMessage(fileName);
                        }
                    } catch (NullPointerException exc) {
                        NeutronMain.addChatMessage("No configs saved.");
                    }
                }
            } catch (Exception e) {
                NeutronMain.addChatMessage("Exception.");
            }
        }
    }
}